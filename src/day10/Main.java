package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static long countPathsToEnd(int[] input, long[] memo, int currentIndex) {
        int lastIndex = input.length - 1;
        long count = 0;
        // Reached final adapter
        if (currentIndex == lastIndex) {
            count = 1;
        }
        // Already calculated
        else if (memo[currentIndex] > 0) {
            count = memo[currentIndex];
        }
        // Calculate and memoize
        else {
            int upToIndex = Math.min(currentIndex + 3, lastIndex);
            // Loop to upToIndex, or break if iteration adapter joltage is > 3 than current adapter
            for (int i = currentIndex + 1; i <= upToIndex && input[i] <= input[currentIndex] + 3; i++)
                count += countPathsToEnd(input, memo, i);
            memo[currentIndex] = count;
        }
        return count;
    }

    private static long part2(int[] input) {
        return countPathsToEnd(input, new long[input.length], 0);
    }

    private static int part1(int[] input) {
        int joltDiff1Count = 0, joltDiff3Count = 0;
        for (int i = 0; i < input.length - 1; i++) {
            int diff = input[i + 1] - input[i];
            if (diff == 1) joltDiff1Count++;
            if (diff == 3) joltDiff3Count++;
        }
        return joltDiff1Count * joltDiff3Count;
    }

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.lines(Path.of("./src/day10/input.txt"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        // Add outlet
        input.add(0);
        // Add device built-in adapter - always +3 of highest adapter
        List<Integer> sortedInput = input.stream().sorted().collect(Collectors.toList());
        sortedInput.add(sortedInput.get(sortedInput.size() - 1) + 3);
        // Convert to int array for easier parsing
        int[] sortedInputArr = sortedInput.stream().mapToInt(Integer::intValue).toArray();

        System.out.println("----- DAY 10 - PART 1 -----");
        System.out.printf("Number of 1-jolt differences multiplied by the number of 3-jolt differences: %d%n",
                part1(sortedInputArr));
        System.out.println("----- DAY 10 - PART 2 -----");
        System.out.printf("Total number of distinct ways you can arrange the adapters: %d%n",
                part2(sortedInputArr));
    }

}
