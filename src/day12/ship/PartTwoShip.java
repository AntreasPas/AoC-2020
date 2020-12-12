package day12.ship;

import day12.Action;
import day12.Instruction;

public class PartTwoShip extends Ship {

    private int waypointX = 10, waypointY = 1;

    private void moveWaypoint(Instruction instruction) {
        switch (instruction.getAction()) {
            case NORTH:
                waypointY += instruction.getValue();
                break;
            case SOUTH:
                waypointY -= instruction.getValue();
                break;
            case WEST:
                waypointX -= instruction.getValue();
                break;
            case EAST:
                waypointX += instruction.getValue();
                break;
            case RIGHT:
                int rotateRightAmount = instruction.getValue() / 90;
                for (int i = 0; i < rotateRightAmount; i++) {
                    int currentX = waypointX;
                    this.waypointX = waypointY;
                    this.waypointY = -currentX;
                }
                break;
            case LEFT:
                int rotateLeftAmount = instruction.getValue() / 90;
                for (int i = 0; i < rotateLeftAmount; i++) {
                    int currentX = waypointX;
                    this.waypointX = -waypointY;
                    this.waypointY = currentX;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void applyInstruction(Instruction instruction) {
        moveWaypoint(instruction);
        if (instruction.getAction().equals(Action.FORWARD)) {
            for (int i = 0; i < instruction.getValue(); i++) {
                this.x += waypointX;
                this.y += waypointY;
            }
        }
    }
}
