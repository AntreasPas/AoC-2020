package day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static Set<Long> getSumsOfNumbers(Long[] input) {
        Set<Long> sums = new HashSet<>();
        for (Long i : input) {
            for (Long j : input) {
                if (!i.equals(j))
                    sums.add(i + j);
            }
        }
        return sums;
    }

    private static long part1(Long[] input, int preamble) {
        for (int i = preamble; i < input.length; i++) {
            Set<Long> sumsOfPreviousNumbers = getSumsOfNumbers(Arrays.copyOfRange(input, i - preamble, i));
            if (!sumsOfPreviousNumbers.contains(input[i]))
                return input[i];
        }
        throw new RuntimeException(String.format("Have not encountered a number which is not a sum of the previous %d", preamble));
    }

    private static long part2(Long[] input, long part1Result) {
        for (int i = 0; i < input.length - 1; i++) {
            long sum = input[i];
            List<Long> continuousRange = new ArrayList<>();
            continuousRange.add(sum);
            for (int j = i + 1; j < input.length - 1 && sum < part1Result; j++) {
                sum += input[j];
                continuousRange.add(input[j]);
                if (sum == part1Result) {
                    LongSummaryStatistics stats = continuousRange
                            .stream()
                            .collect(Collectors.summarizingLong(Long::longValue));
                    return stats.getMax() + stats.getMin();
                }
            }
        }
        throw new RuntimeException(String.format("Have not encountered a a contiguous set of at least two numbers in input which sum to %d", part1Result));
    }

    public static void main(String[] args) throws IOException {
        Long[] input = Files.lines(Path.of("./src/day09/input.txt")).map(Long::parseLong).toArray(Long[]::new);
        System.out.println("----- DAY 9 - PART 1 -----");
        long part1Result = part1(input, 25);
        System.out.printf("First number encountered that is not the sum of two of the %d numbers before it: %d%n",
                25, part1Result);
        System.out.println("----- DAY 9 - PART 2 -----");
        System.out.printf("Encryption weakness in XMAS-encrypted list of numbers: %d%n", part2(input, part1Result));
    }


}
