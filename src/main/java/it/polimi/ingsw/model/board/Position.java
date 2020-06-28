package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exception.InvalidPositionException;

import java.io.Serializable;

/**
 * Position is a pair of Integer which is used to indicate the position on the board
 */
public class Position implements Serializable {
    private static final long serialVersionUID = -1220918273625120376L;
    /**
     * number of the position's row on the board
     * */
    public Integer row;
    /**
     * number of the position's column on the board
     * */
    public Integer col;

    /**
     * Construct a Position with passed value
     *
     * @param row the number of the row
     * @param col the number of the column
     * @throws InvalidPositionException if the passed parameter are not in range from 0 to 4
     */
    public Position(int row, int col) throws InvalidPositionException {
        if (row < 0 || row > 4 || col < 0 || col > 4)
            throw new InvalidPositionException(row, col);
        this.row = row;
        this.col = col;
    }

    /**
     * Method that check if this position is adjacent to passed position
     *
     * @param position position to check if is adjacent
     * @return true iff the passed position is adjacent to this position
     * @throws NullPointerException if passed position is null
     */
    public boolean isAdjacent(Position position) throws NullPointerException {
        if (position == null)
            throw new NullPointerException("Position");

        return position.col >= this.col - 1 && position.col <= this.col + 1
                && position.row >= this.row - 1 && position.row <= this.row + 1
                && !this.equals(position);
    }

    public boolean isPerimeterPosition(){
        return row == 0 || row == 4 || col == 0 || col == 4;
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

    @Override
    public int hashCode() {
        return (row + "," + col).hashCode();
    }
}