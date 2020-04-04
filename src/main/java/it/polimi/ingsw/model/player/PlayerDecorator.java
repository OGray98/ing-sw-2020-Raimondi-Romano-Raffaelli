package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.deck.God;
import it.polimi.ingsw.model.board.Position;

import java.util.List;

public abstract class PlayerDecorator implements PlayerInterface {

    PlayerInterface player;

    public PlayerDecorator(PlayerInterface player){ this.player = player;}

    @Override
    public Board getBoard() {
        return player.getBoard();
    }

    @Override
    public int getSelectedWorker(){
        return player.getSelectedWorker();
    }

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
    public void setSelectedWorker(int selectedWorker){
        player.setSelectedWorker(selectedWorker);
    }

    @Override
    public void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException {
        player.putWorker(startingCellPosition,workerIndex);
    }

    @Override
    public void moveWorker(Position newCellPosition) throws InvalidPositionException, NotSelectedWorkerException {
        player.moveWorker(newCellPosition);
    }

    @Override
    public boolean canMove(Position moveToCheck) throws InvalidPositionException, IllegalArgumentException, NotSelectedWorkerException {
        return player.canMove(moveToCheck);
    }

    @Override
    public void buildWorker(Position toBuildPosition) {
        player.buildWorker(toBuildPosition);
    }

    @Override
    public boolean canBuild(Position buildingPosition) throws InvalidPositionException, NotSelectedWorkerException {
        return player.canBuild(buildingPosition);
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
    public boolean hasWin() throws NullPointerException, NotSelectedWorkerException{
    return player.hasWin();
}

    @Override
    public List<Integer> blockedWorkers() throws NullPointerException{
        return player.blockedWorkers();
    }

    @Override
    public boolean isBlockedBuilding() throws NullPointerException, NotSelectedWorkerException{
        return player.isBlockedBuilding();
    }
}
