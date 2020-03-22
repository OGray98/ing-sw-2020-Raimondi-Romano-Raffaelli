package it.polimi.ingsw;

import java.util.Objects;

public class Position {
    public Integer row;
    public Integer col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row.equals(position.row) &&
                col.equals(position.col);
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}