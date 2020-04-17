package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.deck.CardInterface;
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

    @Before
    public void init(){
        board = new Board();
        cardArtemis = new ArtemisDecorator();
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
    public void canBuildSetFalsePower(){
        board.putWorker(workerPos, playerArtemis.getPlayerNum());
        playerArtemis.setStartingWorkerSituation(board.getCell(workerPos), false);
        playerArtemis.canBuild(board.getAdjacentPlayers(workerPos),new Cell(1,2));
        assertFalse(playerArtemis.getActivePower());
    }


    @Test
    public void canUsePowerArtemisTest(){
        board.putWorker(workerPos, playerArtemis.getPlayerNum());
        playerArtemis.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertEquals(board.getOccupiedPlayer(workerPos), PlayerIndex.PLAYER2);

        board.changeWorkerPosition(workerPos, firstMovePosition);
        playerArtemis.move(board.getCell(firstMovePosition));

        assertTrue(playerArtemis.getActivePower());

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
        assertFalse(playerArtemis.getActivePower());
    }

}
