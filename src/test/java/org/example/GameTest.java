package org.example;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class GameTest {

    private static Game game;
    private static List<PlayerInterface> players;
    private static Board board;
    private static Position firstPos;
    private static Map<PlayerIndex, CardInterface> playersCards;
    private static Deck deck;

    @BeforeClass
    public static void init() {
        players = new ArrayList<>(List.of(
                new Player("p_apo", PlayerIndex.PLAYER0),
                new Player("p_dem", PlayerIndex.PLAYER1),
                new Player("p_pro", PlayerIndex.PLAYER2)
        ));
        game = Game.getInstance(players);
        board = new Board();
        firstPos = new Position(1, 1);
        playersCards = new HashMap<>(3);
        deck = new Deck(3);
        board = new Board();
/*
        playersCards.put(PlayerIndex.PLAYER0, game.)
        game.initGame();
*/
    }

    @Test
    public void isCorrectedConstructor() {

        try {
            Game.getInstance(players);
        } catch (AlreadyPresentGameException e) {
            assertEquals("There already is a game instance", e.getMessage());
        }

        assertSame(game, Game.getInstance());
    }

    /*@Test
    public void isCorrectedConstructorNull(){
        try{
            Game.getInstance(null);
        }catch(NullPointerException e){
            assertEquals("players",e.getMessage());
        }
    }*/

    @Test
    public void isPutWorkerCorrected() {

        try {
            game.putWorker(null);
        } catch (NullPointerException e) {
            assertEquals("putPosition", e.getMessage());
        }
    }

    @Test
    public void getCardsTest(){
        assertEquals(9,game.getCards().size());
        Map<String,String> decks = game.getCards();
        for(String godName : decks.keySet()){
            assertEquals(decks.get(godName),deck.getGodCard(godName).getGodDescription());
        }
    }
/*
    @Test
    public void setChosenGodTest(){
        try{
            game.setGodsChosenByGodLike(null);
        }catch(NullPointerException e){
            assertEquals("godNames",e.getMessage());
        }
    }



    @Test
    public void putWorkerTest(){
        try{
            game.putWorker(null);
        }catch (NullPointerException e){
            assertEquals("putPosition",e.getMessage());
        }

    }

*/

}
