package day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static String applyMask(int value, String mask) {
        StringBuilder builder = new StringBuilder(
                String.format("%36s", Integer.toBinaryString(value)).replace(' ', '0')
        );
        for (int i = 0; i < mask.length(); i++)
            if (mask.charAt(i) != 'X') builder.setCharAt(i, mask.charAt(i));
        return builder.toString();
    }

    public static long part1(List<String> input) {
        // Address -> Binary Value
        Map<Integer, String> map = new HashMap<>();
        String mask = null;
        for (String instruction : input) {
            String[] parts = instruction.split(" = ");
            if ("mask".equals(parts[0])) {
                mask = parts[1];
            } else {
                int address = Integer
                        .parseInt(parts[0].replaceAll("mem\\[", "").replaceAll("]", ""));
                int value = Integer.parseInt(parts[1]);
                assert mask != null;
                map.put(address, applyMask(value, mask));
            }
        }
        return map.values().stream().map(str -> Long.parseLong(str, 2)).reduce(0L, Long::sum);
    }

    public static long part2(List<String> input) {
        // Binary Address -> Value
        Map<String, Integer> map = new HashMap<>();
        String mask = null;
        for (String instruction : input) {
            String[] parts = instruction.split(" = ");
            if ("mask".equals(parts[0])) {
                mask = parts[1];
            } else {
                int address = Integer
                        .parseInt(parts[0].replaceAll("mem\\[", "").replaceAll("]", ""));
                int value = Integer.parseInt(parts[1]);
                assert mask != null;
                createDecodeAddresses(address, mask).forEach(decodedAddress -> map.put(decodedAddress, value));
            }
        }
        return map.values().stream().map(Integer::longValue).reduce(0L, Long::sum);
    }

    public static List<String> createDecodeAddresses(int address, String mask) {
        String binaryAddress = String.format("%36s", Integer.toBinaryString(address)).replace(' ', '0');
        List<StringBuilder> builders = new ArrayList<>();
        builders.add(new StringBuilder());
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == 'X') {
                builders = builders
                        .stream()
                        .flatMap(builder -> Stream.of(
                                new StringBuilder(builder).append('0'),
                                new StringBuilder(builder).append('1'))
                        ).collect(Collectors.toList());
            } else if (mask.charAt(i) == '1') {
                builders.forEach(builder -> builder.append('1'));
            } else {
                for (StringBuilder builder : builders) builder.append(binaryAddress.charAt(i));
            }
        }
        return builders.stream().map(StringBuilder::toString).collect(Collectors.toUnmodifiableList());
    }


    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("./src/day14/input.txt"));
        System.out.println("----- DAY 13 - PART 1 -----");
        System.out.printf("Sum of memory values: %d%n", part1(input));
        System.out.println("----- DAY 13 - PART 2 -----");
        System.out.printf("Sum of memory values: %d%n", part2(input));
    }
}
