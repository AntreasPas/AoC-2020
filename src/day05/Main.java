package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static int findMySeat(List<Integer> seatIds) {
        for (int i = 0; i < seatIds.size() - 1; i++) {
            if (seatIds.get(i) + 1 != seatIds.get(i + 1))
                return seatIds.get(i) + 1;
        }
        throw new RuntimeException("Your seat has not been found.");
    }

    public static void main(String[] args) throws IOException {
        List<Integer> seatIds = Files
                .lines(Path.of("./src/day05/input.txt"))
                .map(Seat::new)
                .map(Seat::getSeatId)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("----- DAY 5 - PART 1 -----");
        System.out.printf("Highest seat ID: %d%n", seatIds.get(seatIds.size() - 1));
        System.out.println("----- DAY 5 - PART 2 -----");
        System.out.printf("My seat ID: %d%n", findMySeat(seatIds));
    }
}
