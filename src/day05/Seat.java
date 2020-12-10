package day05;

public class Seat {

    private static final int MIN_COLUMN = 0, MAX_COLUMN = 7, MIN_ROW = 0, MAX_ROW = 127;

    private final int row, column;

    public Seat(String seatInput) {
        char[] chars = seatInput.toCharArray();
        int currentMinRow = MIN_ROW,
                currentMaxRow = MAX_ROW,
                currentMinColumn = MIN_COLUMN,
                currentMaxColumn = MAX_COLUMN;
        boolean useMaxRow = true, useMaxColumn = true;
        for (char c : chars) {
            switch (c) {
                case 'F':
                    currentMaxRow -= Math.rint(((double) currentMaxRow - currentMinRow) / 2);
                    useMaxRow = false;
                    break;
                case 'B':
                    currentMinRow += Math.rint(((double) currentMaxRow - currentMinRow) / 2);
                    useMaxRow = true;
                    break;
                case 'L':
                    currentMaxColumn -= Math.rint(((double) currentMaxColumn - currentMinColumn) / 2);
                    useMaxColumn = false;
                    break;
                case 'R':
                    currentMinColumn += Math.rint(((double) currentMaxColumn - currentMinColumn) / 2);
                    useMaxColumn = true;
                    break;
            }
        }

        this.row = useMaxRow ? currentMaxRow : currentMinRow;
        this.column = useMaxColumn ? currentMaxColumn : currentMinColumn;
    }

    public Integer getSeatId() {
        return row * 8 + column;
    }
}
