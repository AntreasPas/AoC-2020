package day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class Main {

    private static int calculateAccumulatorIfTerminates(Instruction[] instructions) {
        int accumulator = 0;
        Set<Integer> processedIndexes = new HashSet<>();
        for (int i = 0; i < instructions.length; i++) {
            if (processedIndexes.contains(i))
                return Integer.MIN_VALUE;
            Instruction currentInstruction = instructions[i];
            switch (currentInstruction.getType()) {
                case NO_OP:
                    processedIndexes.add(i);
                    break;
                case ACCUMULATOR:
                    accumulator += currentInstruction.getValue();
                    processedIndexes.add(i);
                    break;
                case JUMP:
                    processedIndexes.add(i);
                    i += currentInstruction.getValue() - 1; // -1 because of loop increment
                    break;
            }
        }
        return accumulator;
    }

    private static int part2(Instruction[] instructions) {
        for (Instruction instruction : instructions) {
            if (!instruction.getType().equals(InstructionType.ACCUMULATOR)) {
                InstructionType oldInstructionType = instruction.getType();
                InstructionType newInstructionType = oldInstructionType.equals(InstructionType.NO_OP)
                        ? InstructionType.JUMP
                        : InstructionType.NO_OP;
                instruction.setType(newInstructionType);
                int accumulator = calculateAccumulatorIfTerminates(instructions);
                if (accumulator != Integer.MIN_VALUE)
                    return accumulator;
                else
                    instruction.setType(oldInstructionType);
            }
        }
        throw new RuntimeException("Unable to find a terminating scenario");
    }

    private static int part1(Instruction[] instructions) {
        int accumulator = 0;
        Set<Integer> processedIndexes = new HashSet<>();
        for (int i = 0; i < instructions.length; i++) {
            if (processedIndexes.contains(i))
                break;
            Instruction currentInstruction = instructions[i];
            switch (currentInstruction.getType()) {
                case NO_OP:
                    processedIndexes.add(i);
                    break;
                case ACCUMULATOR:
                    accumulator += currentInstruction.getValue();
                    processedIndexes.add(i);
                    break;
                case JUMP:
                    processedIndexes.add(i);
                    i += currentInstruction.getValue() - 1; // -1 because of loop increment
                    break;
            }
        }
        return accumulator;
    }

    public static void main(String[] args) throws IOException {
        Instruction[] instructions = Files
                .lines(Path.of("./src/day08/input.txt"))
                .map(Instruction::new)
                .toArray(Instruction[]::new);
        System.out.println("----- DAY 8 - PART 1 -----");
        System.out.printf("Accumulator value before second loop: %d%n", part1(instructions));
        System.out.println("----- DAY 8 - PART 2 -----");
        System.out.printf("Accumulator value after termination: %d%n", part2(instructions));

    }


}

