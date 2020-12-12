package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static long part2(SeatingSystem seatingSystem) {
        boolean done = false;
        while (!done) {
            done = !seatingSystem.playPart2();
        }
        return seatingSystem.getCountOfOccupiedSeats();
    }

    private static long part1(SeatingSystem seatingSystem) {
        boolean done = false;
        while (!done) {
            done = !seatingSystem.playPart1();
        }
        return seatingSystem.getCountOfOccupiedSeats();
    }

    public static void main(String[] args) throws IOException {
        SeatingSystem seatingSystem = new SeatingSystem(Files.lines(Path.of("./src/day11/input.txt")));
        System.out.println("----- DAY 11 - PART 1 -----");
        System.out.printf("Seats occupied: %d%n",
                part1(seatingSystem.getCopy()));
        System.out.println("----- DAY 11 - PART 2 -----");
        System.out.printf("Seats occupied: %d%n",
                part2(seatingSystem.getCopy()));
    }

}
