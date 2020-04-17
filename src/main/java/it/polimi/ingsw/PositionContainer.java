package it.polimi.ingsw;

public class PositionContainer {
    private Position[] pos = new Position[2];

    public PositionContainer(Position newPosition) throws NullPointerException {
        if (newPosition == null)
            throw new NullPointerException("newPosition");
        pos[0] = newPosition;
        pos[1] = new Position(0, 0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionContainer that = (PositionContainer) o;
        return this.pos[0].equals(that.pos[0]) && this.pos[1].equals(that.pos[1]);
    }

    @Override
    public String toString() {
        return "PositionContainer{ oldPosition = " + pos[1] +
                ", newPosition = " + pos[0] + "}";
    }
}
