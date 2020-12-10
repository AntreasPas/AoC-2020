package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static int getSumOfCountsPart1(List<String> input) {
        int count = 0;
        Set<Character> groupResponses = new HashSet<>();
        for (String line : input) {
            if (line.isEmpty()) {
                count += groupResponses.size();
                groupResponses.clear();
            } else {
                groupResponses.addAll(line.chars().mapToObj(i -> (char) i).collect(Collectors.toUnmodifiableSet()));
            }
        }
        count += groupResponses.size();
        return count;
    }

    private static int getSumOfCountsPart2(List<String> input) {
        int count = 0;
        Set<Character> groupResponses = new HashSet<>();
        boolean inGroup = false;
        for (String line : input) {
            if (line.isEmpty()) {
                count += groupResponses.size();
                groupResponses.clear();
                inGroup = false;
            } else if (!inGroup) {
                groupResponses.addAll(line.chars().mapToObj(i -> (char) i).collect(Collectors.toUnmodifiableSet()));
                inGroup = true;
            } else {
                groupResponses.retainAll(line.chars().mapToObj(i -> (char) i).collect(Collectors.toUnmodifiableSet()));
            }
        }
        count += groupResponses.size();
        return count;
    }

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Path.of("./src/day06/input.txt"));
        System.out.println("----- DAY 6 - PART 1 -----");
        System.out.printf("Sum of counts %d%n", getSumOfCountsPart1(input));
        System.out.println("----- DAY 6 - PART 2 -----");
        System.out.printf("Sum of counts %d%n", getSumOfCountsPart2(input));
    }

}
