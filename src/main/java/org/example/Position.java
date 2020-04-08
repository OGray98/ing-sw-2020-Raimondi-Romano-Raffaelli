package org.example;

public class Position {
    public Integer row;
    public Integer col;

    public Position(int row, int col) {
        if (row < 0 || row > 4 || col < 0 || col > 4)
            throw new InvalidPositionException(row, col);
        this.row = row;
        this.col = col;
    }

    public boolean isIllegal() {
        return row < 0 || row > 4 || col < 0 || col > 4;
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