package it.polimi.ingsw.exception;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.player.PlayerIndex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExceptionsTest {

    @Test
    public void invalidIncLevelException() {
        assertEquals(
                "You cannot build in cell : [" + 2 + "][" + 3 + "]",
                new InvalidIncrementLevelException(2, 3).getMessage()
        );
    }

    @Test
    public void invalidPlayerIndexException() {
        assertEquals(
                "There isn't a player with PlayerIndex " + PlayerIndex.PLAYER0,
                new InvalidPlayerIndexException(PlayerIndex.PLAYER0).getMessage()
        );
    }

    @Test
    public void invalidStateExceptionException() {
        assertEquals(
                "You are in " + GameState.MOVE + ", not in " + GameState.START_GAME,
                new InvalidStateException(GameState.START_GAME, GameState.MOVE).getMessage()
        );
    }

    @Test
    public void wrongAssociationViewPlayerException() {
        assertEquals(
                "This is remote view of " + PlayerIndex.PLAYER0 + ", not of" + PlayerIndex.PLAYER1,
                new WrongAssociationViewPlayerException(PlayerIndex.PLAYER0, PlayerIndex.PLAYER1).getMessage()
        );
    }


}
