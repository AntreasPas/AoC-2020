package day8;

import java.util.Arrays;

public enum InstructionType {
    NO_OP("nop"), ACCUMULATOR("acc"), JUMP("jmp");

    private final String value;

    InstructionType(String value) {
        this.value = value;
    }

    public static InstructionType parseType(String value) {
        return Arrays.stream(InstructionType.values())
                .filter(v -> v.value.equals(value))
                .findFirst()
                .orElseThrow();
    }
}
