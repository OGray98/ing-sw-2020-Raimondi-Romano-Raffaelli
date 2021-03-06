package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.NotAdjacentBuildingException;
import it.polimi.ingsw.exception.NotAdjacentMovementException;
import it.polimi.ingsw.exception.NotPresentWorkerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;

import java.util.ArrayList;
import java.util.List;


/**
 * TurnManager is a class that manage turn of every Player and update the Board and the Players according to input from view
 * It is used by GameManager to call blocks of methods of the model
 */
public class TurnManager {

    private final Game gameInstance;
    private List<Position> currentPlayerWorkersPosition;
    private List<Position> movableWorkersPosition;
    private Position workerMovedPosition;

    public TurnManager(Game gameInstance) throws NullPointerException {
        if (gameInstance == null)
            throw new NullPointerException("gameInstance");
        this.gameInstance = gameInstance;
        currentPlayerWorkersPosition = new ArrayList<>();
        movableWorkersPosition = new ArrayList<>();
    }

    /**
     * Start turn in gameInstance and set currentPlayerWorkersPosition
     */
    public void startTurn() {
        gameInstance.startTurn();
        currentPlayerWorkersPosition = gameInstance.getCurrentPlayerWorkersPosition();
        workerMovedPosition = null;
        movableWorkersPosition = gameInstance.canPlayerMoveAWorker();
    }

    /**
     * @return true if the current Player can move at least one worker
     */
    public boolean canCurrentPlayerMoveAWorker() {
        return gameInstance.canPlayerMoveAWorker().size() > 0;
    }

    /**
     * Return a list which contains Position where the worker can move
     *
     * @param workerPos Position where is located the worker that player want to move
     * @return List of Position that contains Position where the worker can move
     * @throws NullPointerException      if workerPos is null
     * @throws NotPresentWorkerException if in workerPos there isn't any worker of current Player
     */
    public List<Position> movementPositions(Position workerPos) throws NullPointerException, NotPresentWorkerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (!currentPlayerWorkersPosition.contains(workerPos))
            throw new NotPresentWorkerException(workerPos.row, workerPos.col, gameInstance.getCurrentPlayerIndex());
        if (!movableWorkersPosition.contains(workerPos))
            return new ArrayList<>();
        return gameInstance.getMovePositions(workerPos);
    }

    /**
     * Method that check if a move from workerPos to movePos is valid
     *
     * @param workerPos Worker's Position before movement
     * @param movePos   Worker's Position after movement
     * @return true iff the movement is valid
     * @throws NullPointerException if workerPos or movePos are null
     */
    public boolean isValidMovement(Position workerPos, Position movePos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (movePos == null)
            throw new NullPointerException("movePos");

        gameInstance.setStartingWorker(workerPos);
        return gameInstance.canMoveWorker(movePos);
    }


    /**
     * Update model with worker's movement from workerPos to movePos.
     *
     * @param workerPos Worker's Position before movement. The worker must be able to move in movePos
     * @param movePos   Worker's Position after movement. The worker must be able to move in movePos
     * @throws NullPointerException         if workerPos or movePos are null
     * @throws NotPresentWorkerException    if in workerPos there isn't any worker of current Player
     * @throws NotAdjacentMovementException workerPos isn't adjacent to movePos
     */
    public void moveWorker(Position workerPos, Position movePos) throws NullPointerException, NotPresentWorkerException, NotAdjacentMovementException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (movePos == null)
            throw new NullPointerException("movePos");
        if (!gameInstance.getCurrentPlayerWorkersPosition().contains(workerPos))
            throw new NotPresentWorkerException(workerPos.row, workerPos.col, gameInstance.getCurrentPlayerIndex());
        if (!movePos.isAdjacent(workerPos))
            throw new NotAdjacentMovementException(workerPos.row, workerPos.col, movePos.row, movePos.col);

        gameInstance.setStartingWorker(workerPos);
        gameInstance.moveWorker(movePos);
        currentPlayerWorkersPosition = gameInstance.getCurrentPlayerWorkersPosition();
        movableWorkersPosition = gameInstance.canPlayerMoveAWorker();
        workerMovedPosition = movePos;

    }

    /**
     * Check if player has won with the last movement of his worker
     *
     * @return true iff player has won with the last movement of his worker
     */
    public boolean hasWonWithMovement() {

        return gameInstance.hasWonCurrentPlayer();
    }

    /**
     * @return true iff the worker moved can build in at least one adjacent Position
     */
    public boolean canMovedWorkerBuildInAPosition() {
        return gameInstance.canPlayerBuildWithSelectedWorker();
    }

    /**
     * Return a list which contains Position where the worker moved can build
     *
     * @return List of Position that contains Position where the moved worker can build
     */
    public List<Position> buildPositions() throws NullPointerException {
        if (workerMovedPosition == null)
            throw new NullPointerException("workerMovedPosition");
        return gameInstance.getBuildPositions(workerMovedPosition);
    }

    /**
     * Method that check if moved worker can build in buildPosition
     *
     * @param buildPosition Position where Player want to build
     * @return true iff the building is valid
     * @throws NullPointerException if workerMovedPosition or buildPosition are null
     */
    public boolean isValidBuilding(Position buildPosition) throws NullPointerException {
        if (buildPosition == null)
            throw new NullPointerException("buildPosition");
        if (workerMovedPosition == null)
            throw new NullPointerException("workerMovedPosition");

        return gameInstance.canBuild(buildPosition);
    }

    /**
     * Update model with worker's building in buildPosition
     *
     * @param buildPosition Position where Player want to build. It must be a position where worker can build.
     * @throws NullPointerException         if workerPos or movePos are null
     * @throws NotPresentWorkerException    if in workerPos there isn't any worker of current Player
     * @throws NotAdjacentMovementException workerPos isn't adjacent to movePos
     */
    public void buildWorker(Position buildPosition) throws NullPointerException, NotPresentWorkerException, NotAdjacentMovementException {
        if (buildPosition == null)
            throw new NullPointerException("buildPosition");
        if (workerMovedPosition == null)
            throw new NullPointerException("workerMovedPosition");
        if (!workerMovedPosition.isAdjacent(buildPosition))
            throw new NotAdjacentBuildingException(workerMovedPosition.row, workerMovedPosition.col, buildPosition.row, buildPosition.col);

        gameInstance.build(buildPosition);
    }

    /**
     * Return a list which contains Position where the worker can use his power
     *
     * @param workerPos Position where is located the worker that player want to use his power
     * @return List of Position that contains Position where the worker can use his power
     * @throws NullPointerException      if workerPos is null
     * @throws NotPresentWorkerException if in workerPos there isn't any worker of current Player
     */
    public List<Position> powerPositions(Position workerPos) throws NullPointerException, NotPresentWorkerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (!movableWorkersPosition.contains(workerPos))
            throw new NotPresentWorkerException(workerPos.row, workerPos.col, gameInstance.getCurrentPlayerIndex());
        return gameInstance.getPowerPositions(workerPos);
    }

    /**
     * Method that check if a use of power from worker in workerPos to powerPos is valid
     *
     * @param workerPos Worker's Position before power
     * @param powerPos   Position where player want to use the power
     * @return true iff the use of power is valid
     * @throws NullPointerException if workerPos or powerPos are null
     */
    public boolean isValidUseOfPower(Position workerPos, Position powerPos) throws NullPointerException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (powerPos == null)
            throw new NullPointerException("powerPos");
        if (!currentPlayerWorkersPosition.contains(workerPos))
            return false;

        gameInstance.setStartingWorker(workerPos);
        return gameInstance.canUsePowerWorker(powerPos);
    }

    /**
     * Update model when a player's worker use power from workerPos to powerPos
     *
     * @param workerPos Worker's Position before use of power
     * @param powerPos   Position where player want to use the power
     * @throws NullPointerException         if workerPos or powerPos are null
     * @throws NotPresentWorkerException    if in workerPos there isn't any worker of current Player
     * @throws NotAdjacentMovementException powerPos isn't adjacent to movePos
     */
    public void usePowerWorker(Position workerPos, Position powerPos) throws NullPointerException, NotPresentWorkerException, NotAdjacentMovementException {
        if (workerPos == null)
            throw new NullPointerException("workerPos");
        if (powerPos == null)
            throw new NullPointerException("powerPos");
        if (!movableWorkersPosition.contains(workerPos))
            throw new NotPresentWorkerException(workerPos.row, workerPos.col, gameInstance.getCurrentPlayerIndex());
        if (!powerPos.isAdjacent(workerPos) && !powerPos.equals(workerPos))
            throw new NotAdjacentMovementException(workerPos.row, workerPos.col, powerPos.row, powerPos.col);

        gameInstance.usePowerWorker(powerPos);

        if (gameInstance.getCurrentPlayerWorkersPosition().contains(powerPos)){
            currentPlayerWorkersPosition.remove(workerPos);
            currentPlayerWorkersPosition.add(powerPos);
            movableWorkersPosition = gameInstance.canPlayerMoveAWorker();
            workerMovedPosition = powerPos;
        }
        else
            workerMovedPosition = workerPos;
    }

    /**
     * Method that calls Game::endTurn()
     * */
    public void endTurn() {
        gameInstance.endTurn();
    }


}
