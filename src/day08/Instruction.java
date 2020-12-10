package day08;

public class Instruction {

    private InstructionType type;
    private final int value;

    public Instruction(String input) {
        String[] tokens = input.split(" ");
        this.type = InstructionType.parseType(tokens[0]);
        this.value = Integer.parseInt(tokens[1]);
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public InstructionType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
