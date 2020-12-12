package day12;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    private static int getDirectionIndex(Direction direction) {
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            if (directions[i].equals(direction)) return i;
        }
        throw new RuntimeException("Direction not recognised");
    }

    public static Direction getNewDirection(Direction currentDirection, Instruction instruction) {
        Action instructionAction = instruction.getAction();
        if (!instructionAction.equals(Action.LEFT) && !instructionAction.equals(Action.RIGHT))
            return currentDirection;

        int directionIndex = getDirectionIndex(currentDirection);
        int directionIncrement = instruction.getValue() / 90;
        Direction[] directions = Direction.values();
        if (instructionAction.equals(Action.LEFT)) {
            int newDirectionIndex = directionIndex - directionIncrement;
            if (newDirectionIndex < 0)
                newDirectionIndex += directions.length;
            return directions[newDirectionIndex];
        } else {
            return directions[(directionIndex + directionIncrement) % directions.length];
        }
    }

    public int getXMovement(Action action) {
        switch (action) {
            case EAST:
                return 1;
            case WEST:
                return -1;
            case FORWARD:
                switch (this) {
                    case EAST:
                        return 1;
                    case WEST:
                        return -1;
                    default:
                        return 0;

                }
            default:
                return 0;
        }
    }

    public int getYMovement(Action action) {
        switch (action) {
            case NORTH:
                return 1;
            case SOUTH:
                return -1;
            case FORWARD:
                switch (this) {
                    case NORTH:
                        return 1;
                    case SOUTH:
                        return -1;
                    default:
                        return 0;

                }
            default:
                return 0;
        }
    }
}
