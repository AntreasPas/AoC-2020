package day11;

import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SeatingSystem {

    private Position[][] grid;
    private int iteration = 0;

    public SeatingSystem(Stream<String> input) {
        this.grid = input
                .map(line -> line
                        .chars()
                        .mapToObj(c -> (char) c)
                        .map(Position::getPosition)
                        .toArray(Position[]::new))
                .toArray(Position[][]::new);
    }

    private SeatingSystem(Position[][] grid) {
        this.grid = grid;
    }

    public SeatingSystem getCopy() {
        return new SeatingSystem(this.grid);
    }

    private int getCountAdjacentOccupiedSeats(Position[][] grid, int cellRow, int cellColumn) {
        int count = 0;
        // Set neighbour search range and handle boundaries
        int startRow = cellRow == 0 ? 0 : cellRow - 1;
        int endRow = cellRow + 1 == grid.length ? cellRow : cellRow + 1;
        int startColumn = cellColumn == 0 ? 0 : cellColumn - 1;
        int endColumn = cellColumn + 1 == grid[cellRow].length ? cellColumn : cellColumn + 1;

        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                if (i == cellRow && j == cellColumn) continue; // Skip self
                count += grid[i][j].isOccupiedSeat() ? 1 : 0;
            }
        }
        return count;
    }

    private Position getNextPositionPart1(Position currentPosition, int adjacentOccupiedSeatsCount) {
        Position nextPosition;
        if (currentPosition.isEmptySeat() && adjacentOccupiedSeatsCount == 0)
            nextPosition = Position.OCCUPIED_SEAT;
        else if (currentPosition.isOccupiedSeat() && adjacentOccupiedSeatsCount >= 4)
            nextPosition = Position.EMPTY_SEAT;
        else
            nextPosition = currentPosition;
        return nextPosition;
    }

    public boolean playPart1() {
        boolean changed = false;
        Position[][] next = new Position[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Position currentPosition = grid[i][j];
                int adjacentOccupiedSeatsCount = getCountAdjacentOccupiedSeats(grid, i, j);
                Position nextPosition = getNextPositionPart1(currentPosition, adjacentOccupiedSeatsCount);
                next[i][j] = nextPosition;
                if (!changed && !currentPosition.equals(nextPosition))
                    changed = true;
            }
        }
        this.grid = next;
        this.iteration++;
        return changed;
    }


    private int getCountVisibleOccupiedSeats(Position[][] grid, int i, int j) {
        int count = 0;

        // 8 directions
        count += getCountOccupiedSeatInDirection(grid, i, j, -1, 0);
        count += getCountOccupiedSeatInDirection(grid, i, j, -1, 1);
        count += getCountOccupiedSeatInDirection(grid, i, j, 0, 1);
        count += getCountOccupiedSeatInDirection(grid, i, j, 1, 1);
        count += getCountOccupiedSeatInDirection(grid, i, j, 1, 0);
        count += getCountOccupiedSeatInDirection(grid, i, j, 1, -1);
        count += getCountOccupiedSeatInDirection(grid, i, j, 0, -1);
        count += getCountOccupiedSeatInDirection(grid, i, j, -1, -1);

        return count;
    }

    private int getCountOccupiedSeatInDirection(Position[][] grid,
                                                int cellRow,
                                                int cellColumn,
                                                int rowIncrement,
                                                int columnIncrement
    ) {
        for (int i = cellRow + rowIncrement, j = cellColumn + columnIncrement;
             i < grid.length && i >= 0 && j < grid[0].length && j >= 0;
             i += rowIncrement, j += columnIncrement
        ) {
            if (grid[i][j].isSeat())
                return grid[i][j].isOccupiedSeat() ? 1 : 0;
        }
        return 0;
    }

    private Position getNextPositionPart2(Position currentPosition, int visibleOccupiedSeatsCount) {
        Position nextPosition;
        if (currentPosition.isEmptySeat() && visibleOccupiedSeatsCount == 0)
            nextPosition = Position.OCCUPIED_SEAT;
        else if (currentPosition.isOccupiedSeat() && visibleOccupiedSeatsCount >= 5)
            nextPosition = Position.EMPTY_SEAT;
        else
            nextPosition = currentPosition;
        return nextPosition;
    }

    public boolean playPart2() {
        boolean changed = false;
        Position[][] next = new Position[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Position currentPosition = grid[i][j];
                int adjacentOccupiedSeatsCount = getCountVisibleOccupiedSeats(grid, i, j);
                Position nextPosition = getNextPositionPart2(currentPosition, adjacentOccupiedSeatsCount);
                next[i][j] = nextPosition;
                if (!changed && !currentPosition.equals(nextPosition))
                    changed = true;
            }
        }
        this.grid = next;
        this.iteration++;
        return changed;
    }


    public long getCountOfOccupiedSeats() {
        return Arrays.stream(grid)
                .map(line -> Arrays.stream(line)
                        .filter(Position::isOccupiedSeat)
                        .count()
                ).reduce(0L, Long::sum);
    }

    @Override
    public String toString() {
        return String.format("Game State - Tick #%d", iteration)
                .concat(System.lineSeparator())
                .concat(Arrays.stream(grid)
                        .map(row -> Arrays.stream(row)
                                .map(Position::getValue)
                                .collect(Collector.of(StringBuilder::new,
                                        StringBuilder::append,
                                        StringBuilder::append,
                                        StringBuilder::toString))
                        ).collect(Collectors.joining(System.lineSeparator()))
                );
    }


}
