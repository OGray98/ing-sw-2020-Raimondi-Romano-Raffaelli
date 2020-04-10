package org.example;

import org.junit.Before;

import static org.junit.Assert.*;

public class PanDecoratorTest {

    private static CardInterface cardPan;
    private static PlayerInterface playerPan;
    private static Board board;

    @Before
    public void init(){
        cardPan = new PanDecorator();
        board = new Board();
        playerPan = cardPan.setPlayer(new Player("jack"));

    }


}