package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidIndexPlayerException;
import it.polimi.ingsw.exceptions.InvalidIndexWorkerException;
import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.WorkerNotPresentException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {

    private final Board board;
    private String nickName;
    private Worker[] workers;
    private God godChosen;
    private int playerNumber;

    public Player(Board board, String nickName, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.nickName = nickName;
        this.workers = new Worker[2];
        //Initialize workers with two Worker with CellOccupation related to playerNumber
        this.workers[0] = new Worker(playerNumber);
        this.workers[1] = new Worker(playerNumber);
    }

    public Board getBoard(){
        return this.board;
    }

    @Override
    public String getNickName() {
        return this.nickName;
    }

    @Override
    public Worker getWorker(int workerIndex) throws InvalidIndexWorkerException {
        if(workerIndex < 0 || workerIndex > 1) throw new InvalidIndexWorkerException(workerIndex);

        return this.workers[workerIndex];
    }

    @Override
    public Position getWorkerPositionOccupied(int workerIndex) throws InvalidIndexPlayerException {
        if(workerIndex < 0 || workerIndex > 1) throw new InvalidIndexPlayerException(workerIndex);

        return this.workers[workerIndex].getPositionOccupied();
    }

    @Override
    public int getWorkerIndex(Position position) throws WorkerNotPresentException, InvalidPositionException{
        if(position.col > 4 || position.row > 4 || position.col < 0 || position.row < 0)
            throw new InvalidPositionException(position.row, position.col);

        if(this.workers[0].getPositionOccupied().equals(position)){
            return 0;
        }
        else if(this.workers[1].getPositionOccupied().equals(position)){
            return 1;
        }
        else{
            throw new WorkerNotPresentException(position.row, position.col);
        }
    }

    @Override
    public God getGodChosen() throws NullPointerException {
        if (this.godChosen == null) throw new NullPointerException("This player hasn't choose any God yet!");
        else
            return this.godChosen;
    }

    @Override
    public void putWorker(Position startingCellPosition, int workerIndex) throws InvalidIndexWorkerException, InvalidPositionException {
        if (workerIndex < 0 || workerIndex > 1) throw new InvalidIndexWorkerException(workerIndex);
        if (startingCellPosition.col > 4 || startingCellPosition.row > 4 || startingCellPosition.col < 0 || startingCellPosition.row < 0)
            throw new InvalidPositionException(startingCellPosition.row, startingCellPosition.col);

        this.workers[workerIndex].move(startingCellPosition);
        this.board.putWorker(startingCellPosition, this.playerNumber);
    }

    @Override
    public void moveWorker(Position newPosition, int workerIndex) throws InvalidIndexWorkerException {
        if (workerIndex < 0 || workerIndex > 1) throw new InvalidIndexWorkerException(workerIndex);

        this.workers[workerIndex].move(newPosition);
        this.board.updateBoardMove(this.workers[workerIndex].getOldPosition(), this.workers[workerIndex].getPositionOccupied(),this.playerNumber);
    }

    @Override
    public boolean canMove(List<Cell> adjacentCells, Position moveToCheck, int workerLevel) throws InvalidPositionException, NullPointerException, IllegalArgumentException{
        if (moveToCheck.col > 4 || moveToCheck.row > 4 || moveToCheck.col < 0 || moveToCheck.row < 0)
            throw new InvalidPositionException(moveToCheck.row, moveToCheck.col);
        if(adjacentCells == null) throw new NullPointerException("adjacentCells is null!");
        if(workerLevel < 0 || workerLevel > 3) throw new IllegalArgumentException("Impossible value of worker level!");
        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(moveToCheck)) {
                if (cell.getOccupation() != CellOccupation.EMPTY) return false;
                return (cell.getLevel() - workerLevel) <= 1;
            }
        }
        return false;
    }

    @Override
    public void buildWorker(Position toBuildPosition){
        this.board.updateBoardBuild(toBuildPosition);
    }

    @Override
    public boolean canBuild(List<Cell> adjacentCells, Position buildingPosition) throws InvalidPositionException, NullPointerException{
        if (buildingPosition.col > 4 || buildingPosition.row > 4 || buildingPosition.col < 0 || buildingPosition.row < 0)
            throw new InvalidPositionException(buildingPosition.row, buildingPosition.col);

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
    public boolean canUsePower(Position pos) {
        return false;
    }

    @Override
    public void usePower(Position pos){

    }

    @Override
    public boolean hasWin(int workerIndex) throws NullPointerException{
        if(this.workers[workerIndex].getOldPosition() == null
                || this.workers[workerIndex].getPositionOccupied() == null)
            throw new NullPointerException("Worker number " +workerIndex + " has never been moved or other unknown problem");

        return this.board.getCell(this.workers[workerIndex].getOldPosition()).getLevel() == 2
                && this.board.getCell(this.workers[workerIndex].getPositionOccupied()).getLevel() == 3;
    }

    @Override
    public List<Integer> blockedWorkers() throws NullPointerException{

        if(this.workers[0].getPositionOccupied() == null
                || this.workers[1].getPositionOccupied() == null)
            throw new NullPointerException("Worker not in any position yet!");

        List<Integer> blockedWorkersList = new ArrayList<>();

        boolean blockedWorker0 = true;
        boolean blockedWorker1 = true;
        List<Cell> adjCellsWorker0 = this.board.getAdjacentCells(this.workers[0].getPositionOccupied());
        List<Cell> adjCellsWorker1 = this.board.getAdjacentCells(this.workers[1].getPositionOccupied());

        for(Cell c : adjCellsWorker0){
            if(this.canMove(adjCellsWorker0, c.getPosition(), this.board.getCell(this.workers[0].getPositionOccupied()).getLevel())){
                blockedWorker0 = false;
                break;
            }
        }

        if(blockedWorker0){
            blockedWorkersList.add(0);
        }

        for(Cell c : adjCellsWorker1){
            if(this.canMove(adjCellsWorker1, c.getPosition(), this.board.getCell(this.workers[1].getPositionOccupied()).getLevel())){
                blockedWorker1 = false;
                break;
            }
        }

        if(blockedWorker1){
            blockedWorkersList.add(1);
        }

        return blockedWorkersList;
    }

    @Override
    public String toString(){
        return "Player number: "
                + this.playerNumber
                +"\nNickname: "
                + this.nickName;
    }
}
