package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    private static void part1(List<Integer> input) {
        System.out.println("----- DAY 1 - PART 1 -----");
        Set<Integer> set = new HashSet<>();
        for (Integer number : input) {
            Integer secondNumberCandidate = 2020 - number;
            if (set.contains(secondNumberCandidate)) {
                System.out.printf("%d + %d = 2020%n", number, secondNumberCandidate);
                System.out.printf("%d * %d = %d%n", number, secondNumberCandidate, number * secondNumberCandidate);
                return;
            }
            set.add(number);
        }
    }

    private static void part2(List<Integer> input) {
        System.out.println("----- DAY 2 - PART 2 -----");
        Collections.sort(input);
        for (int i = 0; i < input.size() - 1; i++) {
            int left = i + 1;
            int right = input.size() - 1;

            while (left < right) {
                if (input.get(left) + input.get(i) + input.get(right) == 2020) {
                    System.out.printf("%d + %d + %d = 2020%n", input.get(left), input.get(i), input.get(right));
                    System.out.printf("%d * %d * %d = %d%n", input.get(left), input.get(i), input.get(right),
                            input.get(left) * input.get(i) * input.get(right));
                    return;
                } else if (input.get(left) + input.get(i) + input.get(right) < 2020)
                    left++;
                else
                    right--;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<Integer> numbers = Files
                .lines(Path.of("./src/day1/input.txt"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        part1(numbers);
        part2(numbers);
    }
}
