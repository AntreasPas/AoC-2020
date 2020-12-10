package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("./src/day02/input.txt"));
        System.out.println("----- DAY 2 - PART 1 -----");
        System.out.printf("%d valid passwords%n", lines.stream().filter(Main::isPasswordValidPart1).count());
        System.out.println("----- DAY 2 - PART 2 -----");
        System.out.printf("%d valid passwords%n", lines.stream().filter(Main::isPasswordValidPart2).count());
    }

    private static boolean isPasswordValidPart1(String line) {
        String[] tokens = line.split(" ");
        String[] minMax = tokens[0].split("-");
        int minOccurrences = Integer.parseInt(minMax[0]);
        int maxOccurrences = Integer.parseInt(minMax[1]);
        char c = tokens[1].replace(":", "").charAt(0);
        long occurrences = tokens[2].chars().filter(ch -> ch == c).count();
        return minOccurrences <= occurrences && occurrences <= maxOccurrences;
    }

    private static boolean isPasswordValidPart2(String line) {
        String[] tokens = line.split(" ");
        String[] positions = tokens[0].split("-");
        int firstPosition = Integer.parseInt(positions[0]) - 1;
        int secondPosition = Integer.parseInt(positions[1]) - 1;
        char c = tokens[1].replace(":", "").charAt(0);
        return c == tokens[2].charAt(firstPosition) ^ c == tokens[2].charAt(secondPosition);
    }
}
