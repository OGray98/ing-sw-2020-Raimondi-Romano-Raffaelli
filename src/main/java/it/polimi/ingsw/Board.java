package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int NUM_ROW = 5;
    private static final int NUM_COLUMNS = 5;
    private Cell[][] map;

    public Board(){
        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for(int i = 0; i < NUM_ROW; i++){
            for(int j = 0; j < NUM_COLUMNS; j++){
                this.map[i][j] = new Cell();
            }
        }
    }

    public List<Cell> getAdjacentCells(int row, int col) throws IllegalArgumentException{

        if(row > 4 || col > 4 || row < 0 || col < 0) throw new IllegalArgumentException();

        List<Cell> adjacentCells = new ArrayList<Cell>();

        boolean[][] adMatrix = new boolean[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                adMatrix[i][j]=true;
            }
        }
        adMatrix[1][1]=false;
        if(row == 0){
            adMatrix[0][0] = false;
            adMatrix[0][1] = false;
            adMatrix[0][2] = false;}
        if(col == 0){
            adMatrix[0][0] = false;
            adMatrix[1][0] = false;
            adMatrix[2][0] = false;}
        if(row == 5){
            adMatrix[2][0] = false;
            adMatrix[2][1] = false;
            adMatrix[2][2] = false;}
        if(col == 5){
            adMatrix[0][2] = false;
            adMatrix[1][2] = false;
            adMatrix[2][2] = false;}
        for(int i=0; i<3; i++){
            for(int j=0; i<3; j++){
                if(adMatrix[i][j]){
                    adjacentCells.add(this.map[row+i-1][col+j-1].cellClone());
                }
            }
        }
        return adjacentCells;
    }
}
