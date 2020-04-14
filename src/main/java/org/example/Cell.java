package org.example;

/**
 * Cell is a class which form a space in board
 */
public class Cell {

    private final Position position;
    private int level;
    private boolean hasDome;

    /**
     * Construct a cell with passed parameter
     *
     * @param row the number of the row
     * @param col the number of the column
     * @throws InvalidPositionException if the passed parameter are not in range from 0 to 4
     */
    public Cell(int row, int col) throws InvalidPositionException {
        this.level = 0;
        hasDome = false;
        position = new Position(row, col);
    }

    /**
     * Returns a deep copy of the Cell cell
     *
     * @param cell cell which you want to copy
     * @throws NullPointerException if cell is null
     */
    public Cell(Cell cell) throws NullPointerException {
        if (cell == null)
            throw new NullPointerException("cell");
        this.level = cell.level;
        this.hasDome = cell.hasDome;
        this.position = new Position(cell.position.row, cell.position.col);
    }

    /**
     * Increment level (during a building action) by 1 or put a dome on a third level
     *
     * @throws InvalidIncrementLevelException if the cell already has a dome
     */
    public void incrementLevel() throws InvalidIncrementLevelException {
        if (hasDome) throw new InvalidIncrementLevelException(position.row, position.col);

        if (this.level < 3)
            this.level++;

        else if (this.level == 3)
            hasDome = true;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean hasDome() {
        return hasDome;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return level == cell.level &&
                hasDome == cell.hasDome &&
                position.equals(cell.position);
    }

}
