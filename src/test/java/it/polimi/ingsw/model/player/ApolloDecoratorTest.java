package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.ApolloDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ApolloDecoratorTest {

    CardInterface cardApollo;
    PlayerInterface playerApollo;
    PlayerInterface playerOpponent;
    private static Board board;
    private static Position workerPosition;
    private static Position workerOpponentPosition;
    private static Position secondWorkerPosition;
    private static Position thirdWorkerPosition;
    private static Position secondOpponentWorkerPosition;
    private static Position towerWorkerTwo;
    private static Position towerWorkerOne;
    PlayerInterface otherPlayerOpponent;
    private static Deck deck;

    @Before
    public void init(){
        deck = new Deck();
        cardApollo = deck.getGodCard("Apollo");
        board = new Board();
        playerApollo = cardApollo.setPlayer(new Player("jack", PlayerIndex.PLAYER0));
        playerOpponent = new Player("Rocky",PlayerIndex.PLAYER1);
        workerPosition = new Position(1,1);
        workerOpponentPosition = new Position(1,2);
        secondWorkerPosition = new Position(0,0);
        thirdWorkerPosition = new Position(1,0);
        otherPlayerOpponent = new Player("Creed",PlayerIndex.PLAYER2);
        secondOpponentWorkerPosition = new Position(2,0);
        towerWorkerTwo = new Position(2,1);
        towerWorkerOne = new Position(0,2);

    }

    @Test
    public void setChosenGodTest(){
        cardApollo.setChosenGod(true);
        assertTrue(cardApollo.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerApollo.getPowerListDimension());
    }

    @Test
    public void canUsePowerTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        board.putWorker(secondWorkerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(secondWorkerPosition),false);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);

        //Verify some case where apollo can move in free adjacent cells
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(new Position(3,3)));
        assertFalse(playerApollo.canUsePower(powers,board.getAdjacentPlayers(workerPosition)));
        List<Cell> powers1 = new ArrayList<>();
        powers1.add(board.getCell(new Position(0,1)));
        assertFalse(playerApollo.canUsePower(powers1,board.getAdjacentPlayers(workerPosition)));
        List<Cell> powers2 = new ArrayList<>();
        powers2.add(board.getCell(secondWorkerPosition));
        //Case when Apollo selected other worker of his team
        assertFalse(playerApollo.canUsePower(powers2,board.getAdjacentPlayers(workerPosition)));
        //Case when Apollo selected enemy worker
        List<Cell> powers3 = new ArrayList<>();
        powers3.add(board.getCell(workerOpponentPosition));
        assertTrue(playerApollo.canUsePower(powers3,board.getAdjacentPlayers(workerPosition)));


        //Case when the enemy worker is more then one level high
        board.constructBlock(secondOpponentWorkerPosition);
        board.constructBlock(secondOpponentWorkerPosition);
        board.putWorker(secondOpponentWorkerPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(secondOpponentWorkerPosition),false);
        List<Cell> powers4 = new ArrayList<>();
        powers4.add(board.getCell(secondOpponentWorkerPosition));
        assertFalse(playerApollo.canUsePower(powers4,board.getAdjacentPlayers(workerPosition)));

        //Case when Apollo is high more then two level from enemy worker
        board.constructBlock(towerWorkerTwo);
        board.constructBlock(towerWorkerTwo);
        board.constructBlock(towerWorkerOne);
        board.changeWorkerPosition(workerPosition,towerWorkerOne);
        playerApollo.setWorkerSituation(board.getCell(workerPosition),board.getCell(towerWorkerOne),false);
        board.changeWorkerPosition(towerWorkerOne,towerWorkerTwo);
        playerApollo.setWorkerSituation(board.getCell(towerWorkerOne),board.getCell(towerWorkerTwo),false);

        assertTrue(playerApollo.canUsePower(powers3,board.getAdjacentPlayers(towerWorkerTwo)));


    }

    @Test
    public void canUsePowerTestPart2(){

        //Case when the worker enemy is high one level from Apollo's worker
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(thirdWorkerPosition);
        board.putWorker(thirdWorkerPosition,PlayerIndex.PLAYER2);
        otherPlayerOpponent.setStartingWorkerSituation(board.getCell(thirdWorkerPosition),false);
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(thirdWorkerPosition));
        assertTrue(playerApollo.canUsePower(powers,board.getAdjacentPlayers(workerPosition)));


    }



    @Test
    public void usePowerTest(){

        //Case when Apollo can change his position with enemy worker position
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerOpponentPosition));
        assertTrue(playerApollo.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        BoardChange boardChange = playerApollo.usePower(board.getCell(workerOpponentPosition));
        board.updateAfterPower(boardChange);
        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(workerOpponentPosition));
        assertEquals(PlayerIndex.PLAYER1,board.getOccupiedPlayer(workerPosition));
    }

    @Test
    public void usePowerTestPart2(){

        //Case when Apollo his high more level then enemy worker and he can change his position with enemy worker position
        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerOpponentPosition));
        assertTrue(playerApollo.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        BoardChange boardChange = playerApollo.usePower(board.getCell(workerOpponentPosition));
        board.updateAfterPower(boardChange);
        assertEquals(PlayerIndex.PLAYER0,board.getOccupiedPlayer(workerOpponentPosition));
        assertEquals(PlayerIndex.PLAYER1,board.getOccupiedPlayer(workerPosition));
    }

    @Test
    public void checkWinAfterPowerMoveTest(){

        //Check if Apollo win using his power move
        board.constructBlock(workerPosition);
        board.constructBlock(workerPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerApollo.setStartingWorkerSituation(board.getCell(workerPosition),false);
        board.constructBlock(workerOpponentPosition);
        board.constructBlock(workerOpponentPosition);
        board.constructBlock(workerOpponentPosition);
        board.putWorker(workerOpponentPosition,PlayerIndex.PLAYER1);
        playerOpponent.setStartingWorkerSituation(board.getCell(workerOpponentPosition),false);
        List<Cell> power = new ArrayList<>();
        power.add(board.getCell(workerOpponentPosition));
        assertTrue(playerApollo.canUsePower(power,board.getAdjacentPlayers(workerPosition)));
        playerApollo.usePower(board.getCell(workerOpponentPosition));
        assertTrue(playerApollo.hasWin());


    }


}