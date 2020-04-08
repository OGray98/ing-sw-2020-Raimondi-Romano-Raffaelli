package org.example;

public class PositionContainer {
    private Position[] pos = new Position[2];

    public PositionContainer() {
        pos[0] = new Position(0, 0);
    }

    public PositionContainer(Position newPosition) {
        pos[0] = newPosition;
        pos[1] = new Position(0, 0);
    }

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
