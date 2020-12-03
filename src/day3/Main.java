package day3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("./src/day3/input.txt"));
        // Converting to 2D char array will make traversal easier
        char[][] matrix = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            matrix[i] = lines.get(i).toCharArray();
        }
        System.out.println("----- DAY 3 - PART 1 -----");
        System.out.printf("Trees hit: %d%n", traverseSlope(matrix, 3, 1));
        System.out.println("----- DAY 3 - PART 2 -----");
        BigInteger result = calculatePart2Result(
                traverseSlope(matrix, 1, 1),
                traverseSlope(matrix, 3, 1),
                traverseSlope(matrix, 5, 1),
                traverseSlope(matrix, 7, 1),
                traverseSlope(matrix, 1, 2)
        );
        System.out.printf("%d%n", result);
    }

    private static BigInteger calculatePart2Result(Integer... acc) {
        return Arrays.stream(acc)
                .map(i -> new BigInteger(String.valueOf(i)))
                .reduce(BigInteger.ONE, BigInteger::multiply);
    }

    private static Integer traverseSlope(char[][] matrix, int xStep, int yStep) {
        int treesCount = 0, x = 0;
        for (int y = 0; y < matrix.length; y += yStep) {
            if (matrix[y][x] == '#') treesCount++;
            x += xStep;
            if (x >= matrix[y].length) x -= matrix[y].length;
        }
        return treesCount;
    }
}
