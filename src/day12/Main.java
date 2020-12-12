package day12;

import day12.ship.PartOneShip;
import day12.ship.PartTwoShip;
import day12.ship.Ship;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static int applyInstructionsAndGetManhattanDistance(Ship ship, List<Instruction> instructions) {
        instructions.forEach(ship::applyInstruction);
        return ship.getManhattanDistance();
    }

    public static void main(String[] args) throws IOException {
        List<Instruction> instructions = Files
                .lines(Path.of("./src/day12/input.txt"))
                .map(Instruction::new)
                .collect(Collectors.toUnmodifiableList());
        System.out.println("----- DAY 12 - PART 1 -----");
        System.out.printf("Manhattan distance: %d%n", applyInstructionsAndGetManhattanDistance(
                new PartOneShip(),
                instructions)
        );
        System.out.println("----- DAY 12 - PART 2 -----");
        System.out.printf("Manhattan distance: %d%n", applyInstructionsAndGetManhattanDistance(
                new PartTwoShip(),
                instructions)
        );
    }

}
