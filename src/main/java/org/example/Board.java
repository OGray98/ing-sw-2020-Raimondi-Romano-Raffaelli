package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int NUM_ROW = 5;
    private static final int NUM_COLUMNS = 5;
    private static final int capacityPlayerPosition = 6;
    private Cell[][] map;
    private Map<Position, PlayerIndex> playerPosition;
    private boolean cantGoUp;


    public Board() {
        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++)
                this.map[i][j] = new Cell(i, j);
        }
        //Entry of playerPosition are added when someone call putWorker
        this.playerPosition = new HashMap<>(capacityPlayerPosition);
        cantGoUp = false;
    }

    /*Returns true if the cell in Position cellPosition is empty
    * Returns false if the cell in Position cellPosition cointains a dome or a player
    * Throws NullPointerException if cellPosition is null */
    public boolean isFreeCell(Position cellPosition) throws NullPointerException {
        if (cellPosition == null)
            throw new NullPointerException("cellPosition");
        if (map[cellPosition.row][cellPosition.col].hasDome())
            return false;

        for (Position position : playerPosition.keySet()) {
            if (position.equals(cellPosition))
                return false;
        }
        return true;
    }

    /*Given the Position centralPosition returns a List<Cell> that contains all the cells adjacent to centralPosition
    * Throws NullPointerException if centralPosition is null */
    public List<Cell> getAdjacentCells(Position centralPosition) throws NullPointerException {
        if (centralPosition == null)
            throw new NullPointerException("centralPosition");

        List<Cell> adjacentCells = new ArrayList<>();
        for (int r = centralPosition.row - 1; r <= centralPosition.row + 1; r++) {
            for (int c = centralPosition.col - 1; c <= centralPosition.col + 1; c++) {
                if (r >= 0 && r <= 4 && c >= 0 && c <= 4 && (r != centralPosition.row || c != centralPosition.col))
                    adjacentCells.add(new Cell(this.map[r][c]));
            }
        }
        return adjacentCells;
    }

    /* Returns the Cell in Position position on the board
    * Throws NullPointerException if position is null */
    public Cell getCell(Position position) throws NullPointerException {
        if (position == null)
            throw new NullPointerException("Position");
        return new Cell(this.map[position.row][position.col]);
    }

    /*Returns the Map<Position, PlayerIndex> that contains all the pairs<Position, PlayerIndex> adjacent to the Position centralPosition
    * Throws NullPointerException  if centralPosition is null */
    public Map<Position, PlayerIndex> getAdjacentPlayers(Position centralPosition) throws NullPointerException {
        if (centralPosition == null)
            throw new NullPointerException("centralPosition");
        HashMap<Position, PlayerIndex> adjacentPlayers = new HashMap<>(capacityPlayerPosition);
        for (Map.Entry<Position, PlayerIndex> entry : this.playerPosition.entrySet()) {
            if (entry.getKey().isAdjacent(centralPosition))
                adjacentPlayers.put(entry.getKey(), entry.getValue());
        }
        return adjacentPlayers;
    }

    /*Given oldPosition and newPosition move the worker contained in Position oldPosition to the Position newPosition
    * Throws NotPresentWorkerException if in oldPosition there is not any worker
    * Throws NullPointerException if oldPosition or newPosition is null*/
    //TODO: controllare qui se la newposition non è vuota? direi di no
    public void changeWorkerPosition(Position oldPosition, Position newPosition) throws NotPresentWorkerException, NullPointerException {
        if (oldPosition == null)
            throw new NullPointerException("oldPosition");
        if (newPosition == null)
            throw new NullPointerException("newPosition");

        for (Map.Entry<Position, PlayerIndex> entry : this.playerPosition.entrySet()) {
            if (entry.getKey().equals(oldPosition)) {
                this.playerPosition.remove(oldPosition);
                this.playerPosition.put(newPosition, entry.getValue());
                return;
            }
        }

        throw new NotPresentWorkerException(oldPosition.row, oldPosition.col);
    }

    /*Increment the level of the Cell in Position buildPosition
    * Throws NullPointerException if buildPosition is null
    * Throws InvalidIncrementLevelException if building is not allowed*/
    public void constructBlock(Position buildPosition) throws NullPointerException, InvalidIncrementLevelException {
        if (buildPosition == null)
            throw new NullPointerException("buildPosition");

        this.map[buildPosition.row][buildPosition.col].incrementLevel();
    }

    /*Given the Position putPosition and the PlayerIndex playerIndex put a worker of playerIndex on the board on the Cell in position putPosition
    * Throws NullPointerException if putPosition is null
    * Throws InvelidPutWorkerException if there are already two workers of the given player on the board */
    //TODO: controllare qui se la putPosition non è vuota? direi di no
    public void putWorker(Position putPosition, PlayerIndex playerIndex) throws NullPointerException, InvalidPutWorkerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");

        int cont = 0;

        for (Map.Entry<Position, PlayerIndex> entry : this.playerPosition.entrySet()) {
            if (entry.getValue() == playerIndex) {
                cont++;
                if (cont > 1)
                    throw new InvalidPutWorkerException(putPosition.row, putPosition.col, playerIndex);
            }
        }

        this.playerPosition.put(putPosition, playerIndex);
    }

    /*Returns the PlayerIndex of the worker in Position position
    * Throws NullPointerException if position is null
    * Throws NotPresentWorkerException if there is no worker in the Position position */
    public PlayerIndex getOccupiedPlayer(Position position) throws NullPointerException, NotPresentWorkerException {
        if (position == null)
            throw new NullPointerException("position");

        for (Map.Entry<Position, PlayerIndex> entry : this.playerPosition.entrySet()) {
            if (entry.getKey().equals(position))
                return entry.getValue();
        }

        throw new NotPresentWorkerException(position.row, position.col);
    }

    /*Method that returns the positions of the workers of player playerToCheck
     * It will be used by Game to check if all the workers of a player are blocked */
    public List<Position> workerPositions(PlayerIndex playerToCheck) throws MissingWorkerException{

        List<Position> playerWorkersPositions = new ArrayList<>();

        for(Position p : this.playerPosition.keySet()){
            if(this.playerPosition.get(p).equals(playerToCheck)){
                playerWorkersPositions.add(p);
            }
        }

        if(playerWorkersPositions.size() != 2) throw new MissingWorkerException(playerWorkersPositions.size());

        return playerWorkersPositions;
    }
}