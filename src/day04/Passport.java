package day04;

import java.util.*;
import java.util.regex.Pattern;

public class Passport {
    private static String[] REQUIRED_FIELDS = new String[]{
            "byr",
            "iyr",
            "eyr",
            "hgt",
            "hcl",
            "ecl",
            "pid",
    };
    private static final Set<String> VALID_EYE_COLOURS = Collections.unmodifiableSet(
            Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    );
    private final Map<String, String> map = new HashMap<>();


    public void addFields(String[] fields) {
        Arrays.stream(fields).map(field -> field.split(":")).forEach(field -> map.put(field[0], field[1]));
    }

    public boolean isValidPart1() {
        return REQUIRED_FIELDS.length ==
                Arrays.stream(REQUIRED_FIELDS)
                        .filter(map::containsKey)
                        .count();
    }

    public boolean isValidPart2() {
        return isValidPart1()
                && validateNumberInRange(map.get("byr"), 1920, 2002)
                && validateNumberInRange(map.get("iyr"), 2010, 2020)
                && validateNumberInRange(map.get("eyr"), 2020, 2030)
                && validateHeight()
                && validateRegex(map.get("hcl"), "^#([0-9]|[a-f]){6}$")
                && validateEyeColour()
                && validateRegex(map.get("pid"), "^\\d{9}$");

    }

    private boolean validateEyeColour() {
        return VALID_EYE_COLOURS.contains(map.get("ecl"));
    }

    private boolean validateRegex(String field, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(field).matches();
    }

    private boolean validateHeight() {
        String height = map.get("hgt");
        if (height.endsWith("cm")) {
            height = height.replace("cm", "");
            return validateNumberInRange(height, 150, 193);
        } else if (height.endsWith("in")) {
            height = height.replace("in", "");
            return validateNumberInRange(height, 59, 76);
        } else {
            return false;
        }
    }

    private boolean validateNumberInRange(String field, int min, int max) {
        try {
            int value = Integer.parseInt(field);
            return min <= value && value <= max;
        } catch (Exception e) {
            return false;
        }
    }
}
