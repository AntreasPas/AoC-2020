package day12.ship;

import day12.Instruction;

public abstract class Ship {
    protected int x = 0, y = 0;

    public abstract void applyInstruction(Instruction instruction);

    public int getManhattanDistance() {
        return Math.abs(this.x) + Math.abs(this.y);
    }
}
