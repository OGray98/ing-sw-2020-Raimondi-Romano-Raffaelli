package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;

import java.util.List;

public class Player implements PlayerInterface {

    private String nickName;
    private Worker[] workers;
    private God godChosen;
    private int playerNumber;

    public Player(String nickName, int playerNumber) {
        this.playerNumber = playerNumber;
        this.nickName = nickName;
        this.workers = new Worker[2];
        //Initialize workers with two Worker with CellOccupation related to playerNumber
        this.workers[0] = new Worker(CellOccupation.values()[playerNumber]);
        this.workers[1] = new Worker(CellOccupation.values()[playerNumber]);
    }

    @Override
    public String getNickName() {
        return this.nickName;
    }

    @Override
    public Worker getWorker(int workerIndex) throws InvalidIndexPlayerException {
        if(workerIndex < 0 || workerIndex > 1) throw new InvalidIndexPlayerException(workerIndex);

        return this.workers[workerIndex];
    }

    @Override
    public Position getWorkerPosition(int workerIndex) throws InvalidIndexPlayerException {
        if(workerIndex < 0 || workerIndex > 1) throw new InvalidIndexPlayerException(workerIndex);

        return this.workers[workerIndex].getPositionOccupied();
    }

    @Override
    public God getGodChosen() throws NullPointerException {
        if (this.godChosen == null) throw new NullPointerException("This player hasn't choose any God yet!");
        else
            return this.godChosen;
    }

    @Override
    public void putWorker(Position startingPosition, int workerIndex) throws InvalidIndexPlayerException, IllegalArgumentException {
        if (workerIndex < 0 || workerIndex > 1) throw new InvalidIndexPlayerException(workerIndex);
        if (startingPosition.col > 4 || startingPosition.row > 4 || startingPosition.col < 0 || startingPosition.row < 0)
            throw new IllegalArgumentException("Not valid position!");

        this.workers[workerIndex].setPositionOccupied(startingPosition);
    }

    @Override
    public void moveWorker(Position newPosition, int workerIndex) throws InvalidIndexPlayerException, IllegalArgumentException {
        if (workerIndex < 0 || workerIndex > 1) throw new InvalidIndexPlayerException(workerIndex);
        if (newPosition.col > 4 || newPosition.row > 4 || newPosition.col < 0 || newPosition.row < 0)
            throw new IllegalArgumentException("Not valid position!");

        this.workers[workerIndex].move(newPosition);
    }

    @Override
    public boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws NullPointerException, IllegalArgumentException{
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");
        if(workerLevel < 0 || workerLevel > 3) throw new IllegalArgumentException("Impossible value of worker level!");
        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(moveToCheck)) {
                if (cell.getOccupation() != CellOccupation.EMPTY) return false;
                if ((cell.getLevel() - workerLevel) > 1) return false;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws NullPointerException{
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");

        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(buildingPosition)) {
                if (cell.getOccupation() == CellOccupation.EMPTY) return true;
            }
        }
        return false;
    }

    @Override
    public void chooseGodPower(God godChosen){
        this.godChosen = godChosen;
    }

    @Override
    public String toString(){
        return "Player number: "
                + this.playerNumber
                +"\nNickname: "
                + this.nickName;
    }
}
