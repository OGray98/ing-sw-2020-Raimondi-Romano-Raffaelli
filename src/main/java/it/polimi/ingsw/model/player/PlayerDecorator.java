package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.WorkerNotPresentException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.deck.God;
import it.polimi.ingsw.model.board.Position;

import java.util.List;

public abstract class PlayerDecorator implements PlayerInterface {

    PlayerInterface player;

    public PlayerDecorator(PlayerInterface player){ this.player = player;}

    @Override
    public String getNickName() {
        return player.getNickName();
    }

    @Override
    public God getGodChosen() throws NullPointerException {
        return player.getGodChosen();
    }

    @Override
    public Worker getWorker(int workerIndex) throws InvalidIndexWorkerException {
        return player.getWorker(workerIndex);
    }

    @Override
    public int getWorkerIndex(Position position) throws WorkerNotPresentException, InvalidPositionException {
        return player.getWorkerIndex(position);
    }

    @Override
    public Position getWorkerPositionOccupied(int workerIndex) throws InvalidIndexPlayerException {
        return player.getWorkerPositionOccupied(workerIndex);
    }

    @Override
    public void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException {
        player.putWorker(startingCellPosition,workerIndex);
    }

    @Override
    public void moveWorker(Position newCellPosition, int workerIndex) throws InvalidIndexWorkerException {
        player.moveWorker(newCellPosition,workerIndex);
    }

    @Override
    public boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws InvalidPositionException, NullPointerException, IllegalArgumentException {
        return player.canMove(adjacentCells,moveToCheck,workerLevel);
    }

    @Override
    public void buildWorker(Position toBuildPosition) {
        player.buildWorker(toBuildPosition);
    }

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException {
        return player.canBuild(adjacentCells, buildingPosition);
    }

    @Override
    public void chooseGodPower(God godChosen) throws IllegalArgumentException {
        player.chooseGodPower(godChosen);
    }

    @Override
    public boolean canUsePower(Position pos) {
        return player.canUsePower(pos);
    }

    @Override
    public void usePower(Position pos) {
        player.usePower(pos);
    }

    @Override
    public Board getBoard() {
        return player.getBoard();
    }

    @Override
    public boolean hasWin(int workerIndex) throws NullPointerException{
        return player.hasWin(workerIndex);
    }

    @Override
    public List<Integer> blockedWorkers() throws NullPointerException{
        return player.blockedWorkers();
    }

    @Override
    public boolean isBlockedBuilding(int workerIndex){
        return player.isBlockedBuilding(workerIndex);
    }
}
