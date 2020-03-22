package it.polimi.ingsw;

public class Player implements PlayerInterface {

    private String nickName;
    private Worker[] workers;
    private God godChosen; //final perchè non può essere modificato durante la partita
    private int playerNumber;

    public Player(String nickName, int playerNumber){
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
    public Worker getWorker(int workerIndex) throws ArrayIndexOutOfBoundsException{
        if(workerIndex < 0 || workerIndex > 1) throw new ArrayIndexOutOfBoundsException();

        return this.workers[workerIndex];
    }

    @Override
    public Position getWorkerPosition(int workerIndex) throws ArrayIndexOutOfBoundsException {
        if(workerIndex < 0 || workerIndex > 1) throw new ArrayIndexOutOfBoundsException();

        return this.workers[workerIndex].getCellOccupied().getPosition();
    }

    //TODO: mettere una Exception appropriata
    @Override
    public God getGodChosen() throws Exception{
        if(this.godChosen == null) throw new Exception();
        else
            return this.godChosen;
    }

    //TODO: mettere una Exception appropriata
    @Override
    public void putWorker(Cell startingCell, int workerIndex) throws ArrayIndexOutOfBoundsException {
        if(workerIndex < 0 || workerIndex > 1) throw new ArrayIndexOutOfBoundsException();

        this.workers[workerIndex].setCellOccupied(startingCell);
    }

    //TODO: mettere una Exception appropriata
    @Override
    public void moveWorker(Board map, Position newCellPosition, int workerIndex) throws ArrayIndexOutOfBoundsException {
        if(workerIndex < 0 || workerIndex > 1) throw new ArrayIndexOutOfBoundsException();

        this.workers[workerIndex].move(map.getCell(newCellPosition.row, newCellPosition.col));

    }

    @Override
    public void build(Board map, Position newBuildingPosition, int workerIndex) throws ArrayIndexOutOfBoundsException{
        if(workerIndex < 0 || workerIndex > 1) throw new ArrayIndexOutOfBoundsException();

        this.workers[workerIndex].build(map.getCell(newBuildingPosition.row, newBuildingPosition.col));
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
