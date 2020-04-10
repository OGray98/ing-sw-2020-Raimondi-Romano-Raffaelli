package org.example;

public class PositionContainer {
    private Position[] pos = new Position[2];

    public PositionContainer() {
        pos[0] = null;
        pos[1] = null;
    }

    public PositionContainer(Position newPosition) throws NullPointerException {
        if (newPosition == null)
            throw new NullPointerException("newPosition");
        pos[0] = newPosition;
        pos[1] = null;
    }

    /*
     * Method that insert a new Position. The old occupiedPosition become oldPosition and the old oldPosition is discarded
     * It requires a Position, which is the Position to add
     * Throws NullPointerException if newPosition is null
     */
    public void put(Position newPosition) throws NullPointerException {
        if (newPosition == null)
            throw new NullPointerException("newPosition");
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
