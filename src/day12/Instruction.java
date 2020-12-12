package day12;

public class Instruction {
    private final Action action;
    private final int value;

    public Instruction(String input) {
        this.action = Action.getAction(input.charAt(0));
        this.value = Integer.parseInt(input.substring(1));
    }

    public Action getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }
}
