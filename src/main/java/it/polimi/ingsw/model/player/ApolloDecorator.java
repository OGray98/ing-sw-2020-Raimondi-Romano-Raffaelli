package it.polimi.ingsw.model.player;


import it.polimi.ingsw.exceptions.InvalidPositionException;
import it.polimi.ingsw.exceptions.NotSelectedWorkerException;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.CellOccupation;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.God;

import java.util.List;


public class ApolloDecorator extends PlayerMoveDecorator {

     private God godName;
     private String godDescription;

    public ApolloDecorator(){
        this.godName = God.APOLLO;
        this.godDescription = God.APOLLO.GetGodDescription();

    }

    public ApolloDecorator(PlayerInterface player){
        super(player);

    }


    @Override
    public boolean canMove(Position moveToCheck) throws InvalidPositionException, IllegalArgumentException, NotSelectedWorkerException{
        if (moveToCheck.col > 4 || moveToCheck.row > 4 || moveToCheck.col < 0 || moveToCheck.row < 0)
            throw new InvalidPositionException(moveToCheck.row, moveToCheck.col);

        if(player.getSelectedWorker() == -1) throw new NotSelectedWorkerException();

        //workerLevel is the level of the Cell where the selected worker is before move
        int workerLevel = player.getBoard().getCell(player.getWorker(player.getSelectedWorker()).getPositionOccupied()).getLevel();
        if(workerLevel < 0 || workerLevel > 3) throw new IllegalArgumentException("Impossible value of worker level!");

        //adjacentCells is the list of cells adjacent to the selected worker
        List<Cell> adjacentCells = player.getBoard().getAdjacentCells(player.getWorker(player.getSelectedWorker()).getPositionOccupied());

        for (Cell cell : adjacentCells) {
            if (cell.getPosition().equals(moveToCheck)) {
                if(cell.getOccupation() == CellOccupation.PLAYER1 || cell.getOccupation() == CellOccupation.PLAYER2 || cell.getOccupation() == CellOccupation.PLAYER3 ){
                    //notify();
                    return false;
                }
                if (cell.getOccupation() != CellOccupation.EMPTY) return false;
                return (cell.getLevel() - workerLevel) <= 1;
            }
        }
        return false;
    }


    @Override
    public boolean canUsePower(Position pos) {
        int indexWorker = player.getSelectedWorker();
        Position workerPosition = player.getWorkerPositionOccupied(indexWorker);
        if(player.getBoard().getCell(pos).getOccupation() != player.getBoard().getCell(workerPosition).getOccupation()
           /*&& player.getBoard().getCell(pos).getOccupation() != CellOccupation.DOME && player.getBoard().getCell(pos).getOccupation() != CellOccupation.EMPTY*/
        && ((player.getBoard().getCell(pos).getLevel() - player.getBoard().getCell(workerPosition).getLevel()) <= 1))
            return true;
       /* else if(player.getBoard().getCell(pos).getOccupation() == CellOccupation.DOME || player.getBoard().getCell(pos).getOccupation() == CellOccupation.EMPTY)
            throw new NotSelectedWorkerException(); // create exception NotSelectedOpponentWorkerException....*/
        else if(player.getBoard().getCell(pos).getLevel() - player.getBoard().getCell(workerPosition).getLevel() > 1)
            throw new InvalidPositionException(pos.row,pos.col); // create exception InvalidMoveLevelException...
        else if(player.getBoard().getCell(pos).getOccupation() == player.getBoard().getCell(workerPosition).getOccupation())
            throw new NotSelectedWorkerException(); // create exception NotSelectedOpponentWorkerException....
        return false;
    }



    @Override
    public void usePower(Position pos) {
        int indexWorker = player.getSelectedWorker();
        Position workerPosition = player.getWorkerPositionOccupied(indexWorker);
        player.getBoard().UpdateBoardSwapPlayer(workerPosition,pos);


    }

    public God getGodName(){
        return this.godName;
    }

    public String getGodDescription(){
        return this.godDescription;
    }
}
