package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exception.*;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Board is a group of Cell, which represent the game table.
 * This class allows to change the state of the board (workers position, builds, ...)
 */
public class Board {

    public static final int NUM_ROW = 5;
    public static final int NUM_COLUMNS = 5;
    private static final int capacityPlayerPosition = 3;
    private final Cell[][] map;
    private final Map<PlayerIndex, List<Position>> playerPosition;


    public Board() {
        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++)
                this.map[i][j] = new Cell(i, j);
        }
        //Entry of playerPosition are added when someone call putWorker
        this.playerPosition = new HashMap<>(capacityPlayerPosition);
    }

    public Board(Board that) throws NullPointerException {
        if (that == null)
            throw new NullPointerException("that");
        this.map = new Cell[NUM_ROW][NUM_COLUMNS];
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++)
                this.map[i][j] = that.getCell(new Position(i, j));
        }
        //Entry of playerPosition are added when someone call putWorker
        this.playerPosition = new HashMap<>(capacityPlayerPosition);
        that.playerPosition.forEach((key, value) -> this.playerPosition.put(key, new ArrayList<>(value)));
    }

    public void removePlayerWorkers(PlayerIndex playerIndex) {
        if (!this.playerPosition.containsKey(playerIndex))
            throw new IllegalArgumentException("There isn't " + playerIndex + " on the board");
        this.playerPosition.remove(playerIndex);
    }

    /**
     * Check if selected Cell is free
     *
     * @param cellPosition Cell's Position which you want check
     * @return true iff in Cell at cellPosition there isn't workers or a dome
     * @throws NullPointerException if cellPosition is null
     */
    public boolean isFreeCell(Position cellPosition) throws NullPointerException {
        if (cellPosition == null)
            throw new NullPointerException("cellPosition");
        if (map[cellPosition.row][cellPosition.col].hasDome())
            return false;

        return playerPosition.values().stream().noneMatch(positions -> positions.contains(cellPosition));
    }

    /**
     * Given the Position centralPosition returns a List of Cell that contains
     * all the cells adjacent to centralPosition
     *
     * @param centralPosition Position at the center of the adjacent Cell
     * @return returns a List of Cell that contains all the cells adjacent to centralPosition
     * @throws NullPointerException if centralPosition is null
     */
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

    /**
     * Given the Position centralPosition returns a List of Cell that contains
     * all the cells adjacent to centralPosition and the central position too
     *
     * @param centralPosition Position at the center of the adjacent Cell
     * @return returns a List of Cell that contains all the cells adjacent to centralPosition
     * @throws NullPointerException if centralPosition is null
     */
    public List<Cell> getAdjacentAndCentralCells(Position centralPosition) throws NullPointerException {
        if (centralPosition == null)
            throw new NullPointerException("centralPosition");

        List<Cell> adjacentCells = new ArrayList<>();
        for (int r = centralPosition.row - 1; r <= centralPosition.row + 1; r++) {
            for (int c = centralPosition.col - 1; c <= centralPosition.col + 1; c++) {
                if (r >= 0 && r <= 4 && c >= 0 && c <= 4)
                    adjacentCells.add(new Cell(this.map[r][c]));
            }
        }
        return adjacentCells;
    }

    /**
     * @return the Cell in Position position on the board
     * @param position position of the cell to get
     * @throws  NullPointerException if position is null
     * */
    public Cell getCell(Position position) throws NullPointerException {
        if (position == null)
            throw new NullPointerException("Position");
        return new Cell(this.map[position.row][position.col]);
    }

    public void setCell(Cell cell) throws NullPointerException {
        if (cell == null)
            throw new NullPointerException("cell");

        this.map[cell.getPosition().row][cell.getPosition().col] = new Cell(cell);

    }

    /**
     * @return the Map (@Position, @PlayerIndex) that contains all the pairs (@Position, @PlayerIndex) adjacent to the Position centralPosition
     * @param centralPosition position of the cell of which the method returns the adjacent players
     * @throws  NullPointerException  if centralPosition is null
     * */
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

    /**
     * @return the Map (@Position, @PlayerIndex) that contains all the pairs (@Position, @PlayerIndex) for the occupied cell
     * which their position is in
     * @param positions contains the Positions to valuate
     * @throws  NullPointerException  if positions is null
     * */
    public Map<Position, PlayerIndex> getPlayersOccupations(List<Position> positions) throws NullPointerException {
        if (positions == null)
            throw new NullPointerException("positions");
        HashMap<Position, PlayerIndex> adjacentPlayers = new HashMap<>();
        for (Position pos : positions) {
            for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
                if (entry.getValue().contains(pos))
                    adjacentPlayers.put(pos, entry.getKey());
            }
        }
        return adjacentPlayers;
    }

    /**
     * Given
     * @param oldPosition and
     * @param newPosition
     * move the worker contained in Position oldPosition to the Position newPosition
     * @throws  NotPresentWorkerException if in oldPosition there is not any worker
     * @throws  NullPointerException if oldPosition or newPosition is null
     * */
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


    /**
     * Increment the level of the Cell in Position
     * @param buildPosition is Position where user wants to build
     * @throws  NullPointerException if buildPosition is null
     * @throws  InvalidIncrementLevelException if building is not allowed
     * */
    public void constructBlock(Position buildPosition) throws NullPointerException, InvalidIncrementLevelException {
        if (buildPosition == null)
            throw new NullPointerException("buildPosition");

        this.map[buildPosition.row][buildPosition.col].incrementLevel();
    }

    /**
     * Given the Position putPosition and the PlayerIndex playerIndex put a worker of
     * @param playerIndex on the board on the Cell in position
     * @param putPosition is Position where player wants to put worker
     * @throws  NullPointerException if putPosition is null
     * @throws  InvalidPutWorkerException if there are already two workers of the given player on the board
     * */
    public void putWorker(Position putPosition, PlayerIndex playerIndex) throws NullPointerException, InvalidPutWorkerException {
        if (putPosition == null)
            throw new NullPointerException("putPosition");

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getKey() == playerIndex) {
                if (entry.getValue().size() == 1) {
                    List<Position> list = new ArrayList<>(entry.getValue());
                    list.add(putPosition);
                    this.playerPosition.put(playerIndex, list);
                    //notify(new BoardChange(new Position(0, 0), putPosition, playerIndex));
                    return;
                } else
                    throw new InvalidPutWorkerException(putPosition.row, putPosition.col, playerIndex);
            }
        }
        List<Position> list = new ArrayList<>();
        list.add(putPosition);
        this.playerPosition.put(playerIndex, list);
        //notify(new BoardChange(new Position(0, 0), putPosition, playerIndex));
    }

    /**
     * @return the PlayerIndex of the worker in Position
     * @param position is the Position to valuate
     * @throws  NullPointerException if position is null
     * @throws  NotPresentWorkerException if there is no worker in the Position position
     * */
    public PlayerIndex getOccupiedPlayer(Position position) throws NullPointerException, NotPresentWorkerException {
        if (position == null)
            throw new NullPointerException("position");

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getValue().contains(position))
                return entry.getKey();
        }

        throw new NotPresentWorkerException(position.row, position.col);
    }

    /**
     * @return the positions of the workers of player
     * @param playerToCheck
     * It will be used by Game to check if all the workers of a player are blocked
     * @throws MissingWorkerException if the player has a number != 2 of workers
     * */
    public List<Position> workerPositions(PlayerIndex playerToCheck) throws MissingWorkerException {

        List<Position> playerWorkersPositions = new ArrayList<>();

        for (Map.Entry<PlayerIndex, List<Position>> entry : this.playerPosition.entrySet()) {
            if (entry.getKey() == playerToCheck)
                playerWorkersPositions.addAll(entry.getValue());
        }

        if (playerWorkersPositions.size() != 2) throw new MissingWorkerException(playerWorkersPositions.size());
        return playerWorkersPositions;
    }


    /**
     *  Method used after PlayerInterface.usePower() to update the board with the new changes.
     * Depending on the content of
     * @param boardChange, it can update playerPosition, modifies map or set cantGoUp
     * Requires BoardChange not null, with the changes to update
     * @throws NullPointerException if BoardChange is null
     */
    public void updateAfterPower(BoardChange boardChange) throws NullPointerException {
        if (boardChange == null)
            throw new NullPointerException("boardChange");
        if (!boardChange.isPlayerChangesNull())
            updateAfterPowerMove(boardChange.getChanges());
        if (!boardChange.isPositionBuildNull())
            updateAfterPowerBuild(boardChange.getPositionBuild(), boardChange.getBuildType());
    }

    /**
     *  Method called by this.updateAfterPower() to update the playerPosition.
     * @param changes is a Map<@PositionContainer, @PlayerIndex> not null, with the changes to update
     * @throws NullPointerException if changes is null
     * @throws NotPresentWorkerException if a player of the Map is not found
     */
    private void updateAfterPowerMove(Map<PositionContainer, PlayerIndex> changes) throws NullPointerException, NotPresentWorkerException {
        boolean isFound = false;

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

    /**
     *  Method called by this.updateAfterPower() to update map.
     * @param positionBuild is not null, with the Position to update
     * @param buildType indicates if build a normal level or a dome
     * @throws NullPointerException if positionBuild is null
     * @throws InvalidBuildDomeException if the cell to build has a dome
     */
    private void updateAfterPowerBuild(Position positionBuild, BuildType buildType) throws NullPointerException, InvalidBuildDomeException {
        if (buildType == BuildType.LEVEL)
            constructBlock(positionBuild);
        else {
            if (this.map[positionBuild.row][positionBuild.col].hasDome())
                throw new InvalidBuildDomeException(positionBuild.row, positionBuild.col);
            map[positionBuild.row][positionBuild.col].setHasDome(true);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        for (int i = 0; i < NUM_ROW; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++)
                if (!this.map[i][j].equals(board.map[i][j]))
                    return false;
        }
        return this.playerPosition.equals(board.playerPosition);
    }
}