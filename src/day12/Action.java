package day12;

import java.util.Arrays;

public enum Action {
    NORTH('N'), SOUTH('S'), EAST('E'), WEST('W'), LEFT('L'), RIGHT('R'), FORWARD('F');
    private final char value;

    Action(char c) {
        this.value = c;
    }

    public static Action getAction(char c) {
        return Arrays.stream(values())
                .filter(v -> c == v.value)
                .findFirst()
                .orElseThrow();
    }
}
