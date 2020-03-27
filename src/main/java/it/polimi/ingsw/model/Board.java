package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int NUM_ROW = 5;
    private static final int NUM_COLUMNS = 5;
    private Cell[][] map;


    public Board(){

        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for(int i = 0; i < NUM_ROW; i++){
            for(int j = 0; j < NUM_COLUMNS; j++)
                this.map[i][j] = new Cell(i, j);
        }
    }

    //Return a List which contains copy of all adjacentCell
    public List<Cell> getAdjacentCells(int row, int col) throws IllegalArgumentException{
        if(row > 4 || col > 4 || row < 0 || col < 0) throw new IllegalArgumentException("There can't be a cell in [" + row + "][" + col + "]");

        List<Cell> adjacentCells = new ArrayList<>();
        for(int r = row-1; r <= row+1; r++) {
            for(int c = col-1; c <= col+1; c++) {
                if(r >= 0 && r <= 4 && c >= 0 && c <= 4 && (r != row || c != col))
                    adjacentCells.add(this.map[r][c]);
            }
        }
        return adjacentCells;
    }

    public Cell getCell(int row, int col) throws IllegalArgumentException{
        if(row > 4 || col > 4 || row < 0 || col < 0) throw new IllegalArgumentException("There can't be a cell in [" + row + "][" + col + "]");
        return this.map[row][col];
    }
}