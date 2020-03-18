package it.polimi.ingsw;

public class Position {
    public Integer row;
    public Integer col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Position){
            return this.row.equals(((Position) obj).row) && this.col.equals(((Position) obj).col);
        }
        return false;
    }
}