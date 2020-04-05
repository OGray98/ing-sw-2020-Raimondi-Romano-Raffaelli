package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.board.Board;

import it.polimi.ingsw.model.board.Position;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

public class AtlasDecoratorTest {

    PlayerInterface player;
    private static Position posTrue;
    private static Board board;
    private static Position posWorker;
    private static Position posFalse;



    @Before
    public void setUp() {

        board = new Board();
        posWorker = new Position(1,1);
        player = new AtlasDecorator(new Player(board,"rock",1));
        posTrue = new Position(2,2);
        posFalse = new Position(3,3);

    }

    @Test
    // verify that the atlasDecorator method return the correct boolean
    public void canBuildAtlasTest() {

        player.putWorker(posWorker,0);
        player.setSelectedWorker(0);

        assertTrue(player.canBuild(posTrue));
        assertFalse(player.canBuild(posFalse));

    }


}