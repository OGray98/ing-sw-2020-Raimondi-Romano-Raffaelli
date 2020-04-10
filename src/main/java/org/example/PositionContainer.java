package org.example;

public class PositionContainer {
    private Position[] pos = new Position[2];

    public PositionContainer() {
        pos[0] = null;
        pos[1] = null;
    }

    public PositionContainer(Position newPosition) {
        pos[0] = newPosition;
        pos[1] = new Position(0, 0);
    }

    /*
     * Method that insert a new Position. The old occupiedPosition become oldPosition and the old oldPosition is discarded
     * It requires a Position, which is the Position to add
     */
    public void put(Position newPosition) {
        pos[1] = pos[0];
        pos[0] = newPosition;
    }

    public Position getOldPosition() {
        return pos[1];
    }

    public Position getOccupiedPosition() {
        return pos[0];
    }

}
