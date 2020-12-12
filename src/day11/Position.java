package day11;

import java.util.Arrays;

public enum Position {

    FLOOR('.'), EMPTY_SEAT('L'), OCCUPIED_SEAT('#');
    private final char value;

    Position(char c) {
        this.value = c;
    }

    public static Position getPosition(char c) {
        return Arrays.stream(values())
                .filter(v -> c == v.value)
                .findFirst()
                .orElseThrow();
    }

    public char getValue() {
        return this.value;
    }

    public boolean isOccupiedSeat() {
        return this.equals(OCCUPIED_SEAT);
    }

    public boolean isEmptySeat() {
        return this.equals(EMPTY_SEAT);
    }

    public boolean isSeat() {
        return this.isEmptySeat() || this.isOccupiedSeat();
    }
}
