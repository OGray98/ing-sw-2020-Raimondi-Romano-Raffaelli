package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.BoardChange;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.player.AtlasDecorator;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasDecoratorTest {

    private static CardInterface atlasPlayer;
    private static PlayerInterface playerint;
    private static Board board;
    private static Position workerPos;
    private static Position posTrue;
    private static Position posFalse;

    @Before
    public void init(){
        atlasPlayer = new AtlasDecorator();
        board = new Board();
        workerPos = new Position(1,1);
        posTrue = new Position(2,2);
        posFalse = new Position(3,3);
        playerint = atlasPlayer.setPlayer(new Player("jack", PlayerIndex.PLAYER1));
    }

    @Test
    public void setChosenGodTest(){
        atlasPlayer.setChosenGod(true);
        assertTrue(atlasPlayer.getBoolChosenGod());
    }

    @Test
    public void getPowerListDimensionTest(){
        assertEquals(1,playerint.getPowerListDimension());
    }

    @Test
    public void activePowerAfterBuilFalseTest(){
        playerint.activePowerAfterBuild();
        assertFalse(playerint.getActivePower());
    }

    @Test
    public void canBuildAtlasTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER1);
        playerint.setStartingWorkerSituation(board.getCell(workerPos), false);

        assertTrue(playerint.canBuild(board.getAdjacentPlayers(workerPos), board.getCell(posTrue)));
        assertFalse(playerint.canBuild(board.getAdjacentPlayers(workerPos), board.getCell(posFalse)));

        assertTrue(playerint.getActivePower());
    }

    @Test
    public void usePowerAtlasTest(){
        board.putWorker(workerPos, PlayerIndex.PLAYER1);
        playerint.setStartingWorkerSituation(board.getCell(workerPos), false);

        BoardChange powerResult = playerint.usePower(board.getCell(posTrue));

        try{
            powerResult.getCantGoUp();
        }
        catch(NullPointerException e){
            assertEquals("cantGoUp", e.getMessage());
        }

        assertEquals(playerint.usePower(board.getCell(posTrue)).getBuildType(), BuildType.DOME);
        assertEquals(playerint.usePower(board.getCell(posTrue)).getPositionBuild(), posTrue);

        try{
            powerResult.getChanges();
        }
        catch(NullPointerException e){
            assertEquals("playerChanges", e.getMessage());
        }

        assertFalse(playerint.getActivePower());
    }

}
