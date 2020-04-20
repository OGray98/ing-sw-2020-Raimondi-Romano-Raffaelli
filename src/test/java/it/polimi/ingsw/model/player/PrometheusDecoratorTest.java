package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import it.polimi.ingsw.model.player.PrometheusDecorator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusDecoratorTest {

    private static Deck deck;
    private static CardInterface cardPrometheus;
    private static PlayerInterface playerPrometheus;
    private static Board board;
    private static Position workerPosition;
    private static Position towerOnePosition;
    private static Position towerTwoPosition;


    @Before
    public void init(){
         deck = new Deck(3);
         cardPrometheus = deck.getGodCard("Prometheus");
         playerPrometheus = cardPrometheus.setPlayer(new Player("Jack", PlayerIndex.PLAYER0));
         board = new Board();
         workerPosition = new Position(1,1);
         towerOnePosition = new Position(0,0);
         towerTwoPosition = new Position(0,2);

    }

    /*Check if the card is no more available because it has been chosen*/
    @Test
    public void setChosenGodTest(){
        cardPrometheus.setChosenGod(true);
        assertTrue(cardPrometheus.getBoolChosenGod());
    }

   @Test
    public void moveTest(){
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);
        playerPrometheus.move(board.getCell(towerOnePosition));
        assertFalse(playerPrometheus.getCantGoUp());
    }

    /*Check usePower() exceptions and correctness*/
    @Test
    public void usePowerTest(){
        board.constructBlock(towerTwoPosition);
        board.putWorker(workerPosition,PlayerIndex.PLAYER0);
        playerPrometheus.setStartingWorkerSituation(board.getCell(workerPosition),false);

        BoardChange boardChange = playerPrometheus.usePower(board.getCell(towerOnePosition));
        assertEquals(towerOnePosition,boardChange.getPositionBuild());
        assertTrue(playerPrometheus.getCantGoUp());

        assertFalse(playerPrometheus.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(towerTwoPosition)));
        board.updateAfterPower(boardChange);
        assertEquals(1,board.getCell(towerOnePosition).getLevel());
        playerPrometheus.move(board.getCell(workerPosition));
        assertFalse(playerPrometheus.getCantGoUp());

        assertTrue(playerPrometheus.canMove(board.getAdjacentPlayers(workerPosition),board.getCell(towerTwoPosition)));

    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerPrometheus.getPowerListDimension());
    }
}