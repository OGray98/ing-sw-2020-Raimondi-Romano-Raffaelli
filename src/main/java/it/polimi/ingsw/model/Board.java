package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int NUM_ROW = 5;
    private static final int NUM_COLUMNS = 5;
    private Cell[][] map;


    public Board() {
        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++)
                this.map[i][j] = new Cell(i, j);
        }
    }

    //Return a List which contains copy of all adjacentCell
    public List<Cell> getAdjacentCells(Position centralPosition) throws InvalidPositionException, NullPointerException {
        if (centralPosition == null)
            throw new NullPointerException("centralPosition");
        if (centralPosition.isIllegal())
            throw new InvalidPositionException(centralPosition.row, centralPosition.col);

        List<Cell> adjacentCells = new ArrayList<>();
        for (int r = centralPosition.row - 1; r <= centralPosition.row + 1; r++) {
            for (int c = centralPosition.col - 1; c <= centralPosition.col + 1; c++) {
                if (r >= 0 && r <= 4 && c >= 0 && c <= 4 && (r != centralPosition.row || c != centralPosition.col))
                    adjacentCells.add(new Cell(this.map[r][c]));
            }
        }
        return adjacentCells;
    }

    public Cell getCell(Position position) throws InvalidPositionException, NullPointerException {
        if (position == null)
            throw new NullPointerException("Position");
        if (position.isIllegal())
            throw new InvalidPositionException(position.row, position.col);
        return new Cell(this.map[position.row][position.col]);
    }

    //Update board
    public void updateBoard(Position oldPosition, Position newPosition, int indexPlayer) throws NullPointerException, InvalidPositionException, InvalidIndexPlayerException {
        if (oldPosition == null)
            throw new NullPointerException("OldPosition");
        if (newPosition == null)
            throw new NullPointerException("NewPosition");
        if (oldPosition.isIllegal())
            throw new InvalidPositionException(oldPosition.row, oldPosition.col);
        if (newPosition.isIllegal())
            throw new InvalidPositionException(newPosition.row, newPosition.col);
        if (this.map[newPosition.row][newPosition.col].isOccupied())
            throw new InvalidPositionException(newPosition.row, newPosition.col); //TODO: fare nuova eccezione OccupiedCellException?

        this.map[oldPosition.row][oldPosition.col].setOccupation(CellOccupation.EMPTY);

        setWorkerPosition(newPosition, indexPlayer);
    }

    //Put worker in board when game start
    public void putWorker(Position position, int indexPlayer) throws InvalidPositionException, NullPointerException, InvalidIndexPlayerException {
        if (position == null)
            throw new NullPointerException("position");
        if (position.isIllegal())
            throw new InvalidPositionException(position.row, position.col);
        if (this.map[position.row][position.col].isOccupied())
            throw new InvalidPositionException(position.row, position.col); //TODO: fare nuova eccezione OccupiedCellException?
        setWorkerPosition(position, indexPlayer);
    }

    private void setWorkerPosition(Position position, int indexPlayer) throws InvalidIndexPlayerException, InvalidPositionException {
        if (position == null)
            throw new NullPointerException("position");
        if (position.isIllegal())
            throw new InvalidPositionException(position.row, position.col);
        //TODO: fare nuova eccezione OccupiedCellException? Qua serve metterla visto che nella putWorker e updateeBoard controllo già?
        if (this.map[position.row][position.col].isOccupied())
            throw new InvalidPositionException(position.row, position.col);
        switch (indexPlayer) {
            case 0:
                this.map[position.row][position.col].setOccupation(CellOccupation.PLAYER1);
                break;
            case 1:
                this.map[position.row][position.col].setOccupation(CellOccupation.PLAYER2);
                break;
            case 2:
                this.map[position.row][position.col].setOccupation(CellOccupation.PLAYER3);
                break;
            default:
                throw new InvalidIndexPlayerException(indexPlayer);
        }
    }
}