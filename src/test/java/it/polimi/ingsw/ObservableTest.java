package it.polimi.ingsw;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.TypeMatchMessage;
import it.polimi.ingsw.view.ObservableStringOnlyForCheck;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ObservableTest {

    private Board board;
    private RemoteView remoteView;

    @Before
    public void init() {
        board = new Board();
        remoteView = new RemoteView(PlayerIndex.PLAYER0, new ObservableStringOnlyForCheck(new TypeMatchMessage(PlayerIndex.PLAYER0, true)));
        board.addObserver(remoteView);
    }


    @Test
    public void testNotify() {
        assertEquals(board, RemoteView.getBoard());
        board.putWorker(new Position(1, 2), PlayerIndex.PLAYER0);
        board.putWorker(new Position(1, 3), PlayerIndex.PLAYER0);
        assertEquals(board, RemoteView.getBoard());
        board.constructBlock(new Position(2, 3));
        assertEquals(board, RemoteView.getBoard());
        board.changeWorkerPosition(new Position(1, 2), new Position(2, 2));
        assertEquals(board, RemoteView.getBoard());
    }

    @Test
    public void removeObserver() {
        board.removeObserver(remoteView);
        board.constructBlock(new Position(2, 3));
        assertNotEquals(board, RemoteView.getBoard());
    }
}