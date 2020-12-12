package day12.ship;

import day12.Direction;
import day12.Instruction;

public class PartOneShip extends Ship {

    private Direction direction = Direction.EAST;

    @Override
    public void applyInstruction(Instruction instruction) {
        this.direction = Direction.getNewDirection(direction, instruction);
        this.x += direction.getXMovement(instruction.getAction()) * instruction.getValue();
        this.y += direction.getYMovement(instruction.getAction()) * instruction.getValue();
    }

}
