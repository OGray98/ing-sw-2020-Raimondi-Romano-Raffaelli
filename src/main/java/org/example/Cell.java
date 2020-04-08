package org.example;

public class Cell {

    private final Position position;
    private int level;
    private boolean hasDome;

    public Cell(int row, int col) throws IllegalArgumentException {
        if (row < 0 || row > 4 || col < 0 || col > 4)
            throw new InvalidPositionException(row, col);
        this.level = 0;
        hasDome = false;
        position = new Position(row, col);
    }

    //Create a deep copy of cell
    public Cell(Cell cell) throws NullPointerException {
        if (cell == null)
            throw new NullPointerException();
        this.level = cell.level;
        this.hasDome = cell.hasDome;
        this.position = new Position(cell.position.row, cell.position.col);
    }

    //If cell isn't occupied build another level, else throw exception
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

    public void setOccupation(boolean hasDome) {
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
