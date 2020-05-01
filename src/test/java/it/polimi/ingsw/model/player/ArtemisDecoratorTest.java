package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.ArtemisDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ArtemisDecoratorTest {

    private static CardInterface cardArtemis;
    private static PlayerInterface playerArtemis;
    private static Board board;
    private static Position workerPos;
    private static Position firstMovePosition;
    private static Position secondMovePosition;
    private static Deck deck;

    @Before
    public void init(){
        deck = new Deck();
        board = new Board();
        cardArtemis = deck.getGodCard("Artemis");
        playerArtemis = cardArtemis.setPlayer(new Player("Jack", PlayerIndex.PLAYER2));
        workerPos = new Position(1,1);
        firstMovePosition = new Position(2,2);
        secondMovePosition = new Position(1,3);
    }

    @Test
    public void setChosenGodTest(){
        cardArtemis.setChosenGod(true);
        assertTrue(cardArtemis.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerArtemis.getPowerListDimension());
    }




    @Test
    public void canUsePowerArtemisTest(){

        //Verify if Artemis canUsePower want to move on different cell after his normal move
        board.putWorker(workerPos, playerArtemis.getPlayerNum());
        playerArtemis.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(board.getOccupiedPlayer(workerPos), PlayerIndex.PLAYER2);

        board.changeWorkerPosition(workerPos, firstMovePosition);
        playerArtemis.move(board.getCell(firstMovePosition));

        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(workerPos));
        assertFalse(playerArtemis.canUsePower(powers, board.getAdjacentPlayers(firstMovePosition)));

        List<Cell> powers1 = new ArrayList<>();
        powers1.add(board.getCell(firstMovePosition));
        assertFalse(playerArtemis.canUsePower(powers1, board.getAdjacentPlayers(firstMovePosition)));

        List<Cell> powers2 = new ArrayList<>();
        powers2.add(board.getCell(secondMovePosition));
        assertTrue(playerArtemis.canUsePower(powers2, board.getAdjacentPlayers(firstMovePosition)));
    }

    @Test
    public void usePowerArtemisTest(){

        //Verify if Artemis UsePower work, testing if on the board artemis was moved after usePower
        board.putWorker(workerPos, playerArtemis.getPlayerNum());
        playerArtemis.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(board.getOccupiedPlayer(workerPos), PlayerIndex.PLAYER2);

        board.changeWorkerPosition(workerPos, firstMovePosition);
        playerArtemis.move(board.getCell(firstMovePosition));

        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(secondMovePosition));
        assertTrue(playerArtemis.canUsePower(powers, board.getAdjacentPlayers(firstMovePosition)));

        BoardChange powerResult = playerArtemis.usePower(board.getCell(secondMovePosition));

        try{
            powerResult.getCantGoUp();
        }
        catch(NullPointerException e){
            assertEquals("cantGoUp", e.getMessage());
        }

        assertEquals(powerResult.getChanges().size(), 1);
        PositionContainer positions = new PositionContainer(firstMovePosition);
        positions.put(secondMovePosition);
        boolean thereIs = false;
        for (Map.Entry<PositionContainer, PlayerIndex> entry : powerResult.getChanges().entrySet()) {
            if (entry.getKey().equals(positions)) {
                thereIs = true;
            }
        }
        assertTrue(thereIs);
        assertTrue(powerResult.getChanges().containsValue(playerArtemis.getPlayerNum()));

    }

    @Test
    public void checkWinAfterPowerMoveTest(){

        //Check the case when artemis win with his power move
        board.constructBlock(workerPos);
        board.putWorker(workerPos,PlayerIndex.PLAYER2);
        playerArtemis.setStartingWorkerSituation(board.getCell(workerPos),false);
        Position towerLvl2 = new Position(1,2);
        Position towerLvl3 = new Position(1,3);
        board.constructBlock(towerLvl2);
        board.constructBlock(towerLvl2);
        board.constructBlock(towerLvl3);
        board.constructBlock(towerLvl3);
        board.constructBlock(towerLvl3);
        assertTrue(playerArtemis.canMove(board.getAdjacentPlayers(workerPos),board.getCell(towerLvl2)));
        playerArtemis.move(board.getCell(towerLvl2));
        board.changeWorkerPosition(workerPos,towerLvl2);
        List<Cell> powers = new ArrayList<>();
        powers.add(board.getCell(towerLvl3));
        assertTrue(playerArtemis.canUsePower(powers,board.getAdjacentPlayers(towerLvl2)));
        playerArtemis.usePower(board.getCell(towerLvl3));
        assertTrue(playerArtemis.hasWin());


    }

}
