package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.InvalidIncrementLevelException;

public class Cell {

    private CellOccupation occupation;
    private int level;

    private Position position;

    //Create a deep copy of cell
    public Cell(Cell cell) throws NullPointerException{
        if(cell == null)
            throw new NullPointerException();
        this.occupation = cell.occupation;
        this.level = cell.level;
        this.position = new Position(cell.position.row, cell.position.col);
    }

    public Cell(int row, int col) throws IllegalArgumentException{
        if(row < 0 || row > 4 || col <0 || col > 4)
            throw new IllegalArgumentException("There can't be a cell in [" + row + "][" + col + "]");
        this.occupation = CellOccupation.EMPTY;
        this.level = 0;
        position = new Position(row, col);
    }

    public boolean isOccupied(){
        return this.occupation != CellOccupation.EMPTY;
    }

    //If cell isn't occupied build another level, else throw exception
    public void incrementLevel() throws InvalidIncrementLevelException {
        if(this.isOccupied()) throw new InvalidIncrementLevelException(position.row, position.col);

        if(this.level < 3)
            this.level++;

        else if(this.level == 3)
            this.occupation = CellOccupation.DOME;
    }

    public int getLevel() {
        return this.level;
    }

    public CellOccupation getOccupation() {
        return this.occupation;
    }

    public void setOccupation(CellOccupation occupation) {
        this.occupation = occupation;
    }

    public Position getPosition() {
        return this.position;
    }

}