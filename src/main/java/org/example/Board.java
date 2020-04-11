package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    //TODO: questa rep passa i test, vedere se non si crea casino a fare la Map.put dentro ad un for each
    private static final int NUM_ROW = 5;
    private static final int NUM_COLUMNS = 5;
    private static final int capacityPlayerPosition = 3;
    private Cell[][] map;
    private Map<PlayerIndex, List<Position>> playerPosition;
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

        for (List<Position> positions : playerPosition.values())
            if (positions.contains(cellPosition))
                return false;
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
        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            for (Position position : entry.getValue()) {
                if (position.isAdjacent(centralPosition))
                    adjacentPlayers.put(position, entry.getKey());
            }
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

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            List<Position> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(oldPosition)) {
                    list.remove(oldPosition);
                    List<Position> newPositions = new ArrayList<>(list);
                    newPositions.add(newPosition);
                    this.playerPosition.put(entry.getKey(), newPositions);
                    return;
                }
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

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getKey() == playerIndex) {
                if (entry.getValue().size() == 1) {
                    List<Position> list = new ArrayList<>(entry.getValue());
                    list.add(putPosition);
                    this.playerPosition.put(playerIndex, list);
                    return;
                } else
                    throw new InvalidPutWorkerException(putPosition.row, putPosition.col, playerIndex);
            }
        }
        List<Position> list = new ArrayList<>();
        list.add(putPosition);
        this.playerPosition.put(playerIndex, list);
    }

    /*Returns the PlayerIndex of the worker in Position position
     * Throws NullPointerException if position is null
     * Throws NotPresentWorkerException if there is no worker in the Position position */
    public PlayerIndex getOccupiedPlayer(Position position) throws NullPointerException, NotPresentWorkerException {
        if (position == null)
            throw new NullPointerException("position");

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getValue().contains(position))
                return entry.getKey();
        }

        throw new NotPresentWorkerException(position.row, position.col);
    }

    /*Method that returns the positions of the workers of player playerToCheck
     * It will be used by Game to check if all the workers of a player are blocked */
    public List<Position> workerPositions(PlayerIndex playerToCheck) throws MissingWorkerException {

        List<Position> playerWorkersPositions = new ArrayList<>();

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getKey() == playerToCheck)
                playerWorkersPositions.addAll(entry.getValue());
        }

        if (playerWorkersPositions.size() != 2) throw new MissingWorkerException(playerWorkersPositions.size());
        return playerWorkersPositions;
    }

    /* Method used after PlayerInterface.usePower() to update the board with the new changes.
     * Depending on the content of boardChange, it can update playerPosition, modifies map or set cantGoUp
     * Requires BoardChange not null, with the changes to update
     */
    public void updateAfterPower(BoardChange boardChange) throws NullPointerException {
        if (boardChange == null)
            throw new NullPointerException("boardChange");
        if (!boardChange.isCantGoUpNull())
            this.cantGoUp = boardChange.getCantGoUp();
        if (!boardChange.isPlayerChangesNull())
            updateAfterPowerMove(boardChange.getChanges());
        if (!boardChange.isPositionBuildNull())
            updateAfterPowerBuild(boardChange.getPositionBuild(), boardChange.getBuildType());
    }

    /* Method called by this.updateAfterPower() to update the playerPosition.
     * Requires Map<PositionContainer, PlayerIndex> not null, with the changes to update
     */
    private void updateAfterPowerMove(Map<PositionContainer, PlayerIndex> changes) throws NullPointerException, NotPresentWorkerException {
        if (changes == null)
            throw new NullPointerException("changes");
        boolean isFound = false;

        //TODO: correct this
        for (Map.Entry<PositionContainer, PlayerIndex> entryChange : changes.entrySet()) {
            for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
                if (entry.getValue().contains(entryChange.getKey().getOldPosition()) &&
                        entryChange.getValue() == entry.getKey()) {
                    List<Position> positions = new ArrayList<>(entry.getValue());
                    positions.remove(entryChange.getKey().getOldPosition());
                    positions.add(entryChange.getKey().getOccupiedPosition());
                    this.playerPosition.put(entry.getKey(), positions);
                    isFound = true;
                }
            }
            if (!isFound)
                throw new NotPresentWorkerException(entryChange.getKey().getOldPosition().row,
                        entryChange.getKey().getOldPosition().col, entryChange.getValue());
        }
    }

    /* Method called by this.updateAfterPower() to update map.
     * Requires Position not null, with the Position to update
     * Requires BuildType, which indicate if build a normal level or a dome
     */
    private void updateAfterPowerBuild(Position positionBuild, BuildType buildType) throws NullPointerException, InvalidBuildDomeException {
        if (positionBuild == null)
            throw new NullPointerException("positionBuild");
        if (buildType == BuildType.LEVEL)
            constructBlock(positionBuild);
        else {
            if (this.map[positionBuild.row][positionBuild.col].hasDome())
                throw new InvalidBuildDomeException(positionBuild.row, positionBuild.col);
            map[positionBuild.row][positionBuild.col].setHasDome(true);
        }
    }

    public boolean isCantGoUp() {
        return cantGoUp;
    }

}