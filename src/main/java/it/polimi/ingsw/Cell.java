package it.polimi.ingsw;

public class Cell {

    private CellOccupation occupation;
    private int level;

    public Cell(Cell cell){
        this.occupation = cell.occupation;
        this.level = cell.level;
    }

    public Cell(){
        this.occupation = CellOccupation.EMPTY;
        this.level = 0;
    }

    public boolean isOccupied(){
        if(this.occupation != CellOccupation.EMPTY)
            return true;
        return false;
    }

    public void incrementLevel() throws Exception{
        if(this.isOccupied() == true) throw new Exception();
        else if(this.level < 3){
            this.level++;
        }
        else if(this.level == 3) {
            this.occupation = CellOccupation.DOME;
        }
    }

    public int getLevel() {
        return level;
    }

    public CellOccupation getOccupation() {
        return occupation;
    }

    public void setOccupation(CellOccupation occupation) {
        this.occupation = occupation;
    }

    public Cell cellClone(){
        return new Cell(this);
    }
}
