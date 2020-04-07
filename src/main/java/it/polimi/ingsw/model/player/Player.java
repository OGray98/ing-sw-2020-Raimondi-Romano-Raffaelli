package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.*;
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
    //Number of the player in the match
    private int playerNumber;
    //Index of worker selected for a operation inside a turn
    private int selectedWorker;
    //True if player can't move up in his turn (for Athena power or Prometheus power)
    private boolean cantMoveUp;

    public Player(Board board, String nickName, int playerNumber) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.nickName = nickName;
        this.workers = new Worker[2];
        //Initialize workers with two Worker with playerNumber
        this.workers[0] = new Worker(playerNumber);
        this.workers[1] = new Worker(playerNumber);
        //Initialize selectedWorker as -1, this value will block operations if user doesn't select any worker
        //Every turn end it will be set as -1 cause the user in the next turn will select a new worker
        this.selectedWorker = -1;
        this.cantMoveUp = false;
    }

    @Override
    public Board getBoard(){
        return this.board;
    }
    
    @Override
    public int getSelectedWorker(){
        return this.selectedWorker;
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
    public void setSelectedWorker(int selectedWorker) throws InvalidIndexWorkerException{
        if (selectedWorker < 0 || selectedWorker > 1) throw new InvalidIndexWorkerException(selectedWorker);
        this.selectedWorker = selectedWorker;
    }

    @Override
    public void setCantMoveUp(boolean value){
        this.cantMoveUp = value;
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
    public void moveWorker(Position newPosition) throws InvalidPositionException, NotSelectedWorkerException{
        if (newPosition.col > 4 || newPosition.row > 4 || newPosition.col < 0 || newPosition.row < 0)
            throw new InvalidPositionException(newPosition.row, newPosition.col);
        if(this.selectedWorker == -1) throw new NotSelectedWorkerException();

        this.workers[this.selectedWorker].move(newPosition);
        this.board.updateBoardMove(this.workers[this.selectedWorker].getOldPosition(), this.workers[this.selectedWorker].getPositionOccupied(),this.playerNumber);
    }

    @Override
    public boolean canMove(Position moveToCheck) throws InvalidPositionException, IllegalArgumentException, NotSelectedWorkerException{
        if (moveToCheck.col > 4 || moveToCheck.row > 4 || moveToCheck.col < 0 || moveToCheck.row < 0)
            throw new InvalidPositionException(moveToCheck.row, moveToCheck.col);

        if(this.selectedWorker == -1) throw new NotSelectedWorkerException();

        //workerLevel is the level of the Cell where the selected worker is before move
        int workerLevel = this.board.getCell(this.workers[this.selectedWorker].getPositionOccupied()).getLevel();
        if(workerLevel < 0 || workerLevel > 3) throw new IllegalArgumentException("Impossible value of worker level!");

        //adjacentCells is the list of cells adjacent to the selected worker
        List<Cell> adjacentCells = this.board.getAdjacentCells(this.workers[this.selectedWorker].getPositionOccupied());

        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(moveToCheck)) {
                if (cell.getOccupation() != CellOccupation.EMPTY) return false;
                //if cantMoveUp is true, moves on upper levels are blocked
                if(this.cantMoveUp){
                    if(this.board.getCell(moveToCheck).getLevel() >
                            this.board.getCell(this.workers[this.selectedWorker].getPositionOccupied()).getLevel()){
                        return false;
                    }
                }
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
    public boolean canBuild(Position buildingPosition) throws InvalidPositionException, NotSelectedWorkerException{
        if (buildingPosition.col > 4 || buildingPosition.row > 4 || buildingPosition.col < 0 || buildingPosition.row < 0)
            throw new InvalidPositionException(buildingPosition.row, buildingPosition.col);

        if(this.selectedWorker == -1) throw new NotSelectedWorkerException();

        List<Cell> adjacentCells = this.board.getAdjacentCells(this.workers[this.selectedWorker].getPositionOccupied());

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
    public boolean hasWin() throws NullPointerException, NotSelectedWorkerException{
        if(this.workers[this.selectedWorker].getOldPosition() == null
                || this.workers[this.selectedWorker].getPositionOccupied() == null)
            throw new NullPointerException("Worker number " + this.selectedWorker + " has never been moved or other unknown problem");

        if(this.selectedWorker == -1) throw new NotSelectedWorkerException();

        return this.board.getCell(this.workers[this.selectedWorker].getOldPosition()).getLevel() == 2
                && this.board.getCell(this.workers[this.selectedWorker].getPositionOccupied()).getLevel() == 3;
    }

    @Override
    public List<Integer> blockedWorkers() throws NullPointerException{

        if(this.workers[0].getPositionOccupied() == null
                || this.workers[1].getPositionOccupied() == null)
            throw new NullPointerException("Worker/s not in any position yet!");

        List<Integer> blockedWorkersList = new ArrayList<>();

        boolean blockedWorker0 = true;
        boolean blockedWorker1 = true;
        List<Cell> adjCellsWorker0 = this.board.getAdjacentCells(this.workers[0].getPositionOccupied());
        List<Cell> adjCellsWorker1 = this.board.getAdjacentCells(this.workers[1].getPositionOccupied());

        for(Cell c : adjCellsWorker0){
            if(this.canMoveIndex(c.getPosition(), 0)){
                blockedWorker0 = false;
                break;
            }
        }

        if(blockedWorker0){
            blockedWorkersList.add(0);
        }

        for(Cell c : adjCellsWorker1){
            if(this.canMoveIndex(c.getPosition(), 1)){
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
    public boolean isBlockedBuilding() throws NullPointerException, NotSelectedWorkerException{

        if(this.workers[this.selectedWorker].getPositionOccupied() == null)
            throw new NullPointerException("Worker/s not in any position yet!");

        if(this.selectedWorker == -1) throw new NotSelectedWorkerException();

        boolean blockedBuilding = true;
        List<Cell> adjCellsWorker = this.board.getAdjacentCells(this.workers[this.selectedWorker].getPositionOccupied());

        for(Cell c : adjCellsWorker){
            if(this.canBuild(c.getPosition())){
                blockedBuilding = false;
                break;
            }
        }

        return blockedBuilding;
    }

    /*Utility method for blockedWorkers()
     * It is the same as canMove() but it requires the index of the worker
     * it is private because it is used only by blockedWorkers() method, inside the same instance of Player */
    private boolean canMoveIndex(Position moveToCheck, int workerIndex) throws IllegalArgumentException{
        if (moveToCheck.col > 4 || moveToCheck.row > 4 || moveToCheck.col < 0 || moveToCheck.row < 0)
            throw new InvalidPositionException(moveToCheck.row, moveToCheck.col);

        //workerLevel is the level of the Cell where the selected worker is before move
        int workerLevel = this.board.getCell(this.workers[workerIndex].getPositionOccupied()).getLevel();
        if(workerLevel < 0 || workerLevel > 3) throw new IllegalArgumentException("Impossible value of worker level!");

        //adjacentCells is the list of cells adjacent to the selected worker
        List<Cell> adjacentCells = this.board.getAdjacentCells(this.workers[workerIndex].getPositionOccupied());

        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(moveToCheck)) {
                if (cell.getOccupation() != CellOccupation.EMPTY) return false;
                return (cell.getLevel() - workerLevel) <= 1;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Player number: "
                + this.playerNumber
                +"\nNickname: "
                + this.nickName;
    }
}
