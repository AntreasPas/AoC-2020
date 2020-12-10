package day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static List<Passport> readPassports()
            throws IOException {
        List<Passport> passports = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new FileReader("./src/day04/input.txt"))) {
            String line;
            Passport passport = new Passport();
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    passports.add(passport);
                    passport = new Passport();
                } else {
                    passport.addFields(line.split(" "));
                }
            }
            passports.add(passport);
        }
        return passports;
    }

    public static void main(String[] args) throws IOException {
        List<Passport> passports = readPassports();
        System.out.println("----- DAY 4 - PART 1 -----");
        System.out.printf("Valid passports: %d%n", passports.stream().filter(Passport::isValidPart1).count());
        System.out.println("----- DAY 4 - PART 2 -----");
        System.out.printf("Valid passports: %d%n", passports.stream().filter(Passport::isValidPart2).count());
    }
}
