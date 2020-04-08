package org.example;

public class Position {
    public Integer row;
    public Integer col;

    public Position(int row, int col) throws InvalidPositionException {
        if (row < 0 || row > 4 || col < 0 || col > 4)
            throw new InvalidPositionException(row, col);
        this.row = row;
        this.col = col;
    }

    public boolean isAdjacent(Position position) throws NullPointerException {
        if (position == null)
            throw new NullPointerException("Position");

        return position.col >= this.col - 1 && position.col <= this.col + 1
                && position.row >= this.row - 1 && position.row <= this.row + 1
                && this.equals(position);
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