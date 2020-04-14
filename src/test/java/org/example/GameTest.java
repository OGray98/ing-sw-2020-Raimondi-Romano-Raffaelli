package org.example;

import com.sun.security.auth.NTUserPrincipal;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameTest {

    private static Game game;
    private static List<PlayerInterface> players;
    private static Board board;
    private static Position firstPlayerFirstWorkerPos;
    private static Position firstPlayerSecondWorkerPos;
    private static Map<PlayerIndex, CardInterface> playersCards;
    private static PlayerInterface currentPlayer;
    private static Position secondPos;

    private static Position secondPlayerFirstWorker;
    private static Position secondPlayerSecondWorker;
    private static Position lastPlayerFirstWorker;
    private static Position lastPlayerSecondWorker;


    @BeforeClass
    public static void init() {
        players = new ArrayList<>(3);
        players.add(new Player("Jack",PlayerIndex.PLAYER0));
        players.add(new Player("Rock",PlayerIndex.PLAYER1));
        players.add(new Player("Creed",PlayerIndex.PLAYER2));
        game = Game.getInstance(players);
        board = new Board();
        firstPlayerFirstWorkerPos = new Position(1, 1);
        playersCards = new HashMap<>(3);
        board = new Board();
        currentPlayer = players.get(0);
        secondPos = new Position(3,3);
        firstPlayerSecondWorkerPos = new Position(4,0);

        game.chooseFirstPlayer(PlayerIndex.PLAYER0);
        // Prometheus
        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        //Pan
        secondPlayerFirstWorker = new Position(4,2);
        secondPlayerSecondWorker = new Position(4,4);
        game.putWorker(secondPlayerFirstWorker);
        game.putWorker(secondPlayerSecondWorker);

        //Atlas
        lastPlayerFirstWorker = new Position(2,0);
        lastPlayerSecondWorker = new Position(3,0);
        game.putWorker(lastPlayerFirstWorker);
        game.putWorker(lastPlayerSecondWorker);

        game.startTurn();

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
    public void getCardsTest(){
        assertEquals(9,game.getCards().size());
        Map<String,String> decks = game.getCards();
        for(String godName : decks.keySet()){
            assertEquals(decks.get(godName),game.getDeck().getGodCard(godName).getGodDescription());
        }
    }

    @Test
    public void setChosenByGodLikeTest(){
        try{
            game.setGodsChosenByGodLike(null);
        }catch(NullPointerException e){
            assertEquals("godNames",e.getMessage());
        }


        List<String> threeGodTwoEquals = new ArrayList<>(3);
        threeGodTwoEquals.add("Atlas");
        threeGodTwoEquals.add("Apollo");
        threeGodTwoEquals.add("Apollo");
        try{
            game.setGodsChosenByGodLike(threeGodTwoEquals);
        }catch (IllegalArgumentException e){
            assertEquals("There are two element with name " + threeGodTwoEquals.get(1),e.getMessage());
        }
        List<String> threeGods = new ArrayList<>(3);
        threeGods.add("Pan");
        threeGods.add("Artemis");
        threeGods.add("Demeter");
        game.setGodsChosenByGodLike(threeGods);
        assertTrue(game.getDeck().getGodCard("Pan").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Artemis").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Demeter").getBoolChosenGod());
    }

    @Test
    public void setPlayerCardsTest(){
        try{
            game.setPlayerCard(null);
        } catch (NullPointerException e){
            assertEquals("godName",e.getMessage());
        }

        game.setPlayerCard("Prometheus");
        assertEquals(PlayerIndex.PLAYER0,game.getPlayers().get(0).getPlayerNum());
        game.setPlayerCard("Pan");
        assertEquals(PlayerIndex.PLAYER1,game.getPlayers().get(1).getPlayerNum());
        game.setPlayerCard("Atlas");
        assertEquals(PlayerIndex.PLAYER2,game.getPlayers().get(2).getPlayerNum());
        assertEquals(1,game.getPlayers().get(2).getPowerListDimension());// test if the method decorate correctly

    }

    @Test
    public void setFirstPlayerTest(){
        game.chooseFirstPlayer(PlayerIndex.PLAYER0);
        assertEquals(PlayerIndex.PLAYER0,game.getPlayers().get(0).getPlayerNum());
        // player0 -> player0
        // player1 -> player2
        // player2 -> player1
    }


    @Test
    public void putWorkerTest(){
        try{
            game.putWorker(null);
        }catch (NullPointerException e){
            assertEquals("putPosition",e.getMessage());
        }
        //game.putWorker(firstPos);
        assertEquals(PlayerIndex.PLAYER0,game.getBoard().getOccupiedPlayer(firstPlayerFirstWorkerPos));
    }
    @Test
    public void canMoveWorkerTest(){
        //game.putWorker(firstPos);
        try{
            game.canMoveWorker(null);
        }catch (NullPointerException e){
            assertEquals("movePos",e.getMessage());
        }
        assertFalse(game.canMoveWorker(secondPos));
        assertTrue(game.canMoveWorker(new Position(1,1)));
    }

    @Test
    public void moveWorkerTest(){
        //game.putWorker(firstPos);
        try{
            game.moveWorker(null);
        }catch (NullPointerException e){
            assertEquals("movePos",e.getMessage());
        }
        Position pos = new Position(1,2);
        game.moveWorker(pos);
        assertEquals(PlayerIndex.PLAYER0,game.getBoard().getOccupiedPlayer(pos));
    }

    @Test
    public void canBuildTest(){
        //game.putWorker(firstPos);
        try{
            game.canBuild(null);
        }catch (NullPointerException e){
            assertEquals("buildPos",e.getMessage());
        }
        Position pos = new Position(0,1);
        Position posDome = new Position(1,0);
        assertTrue(game.canBuild(pos));
        game.getBoard().constructBlock(posDome);
        game.getBoard().constructBlock(posDome);
        game.getBoard().constructBlock(posDome);
        game.getBoard().constructBlock(posDome);
        assertFalse(game.canBuild(posDome));
    }

    @Test
    public void buildTest(){
        //game.putWorker(firstPlayerFirstWorkerPos);
        try{
            game.build(null);
        }catch (NullPointerException e){
            assertEquals("buildPos",e.getMessage());
        }
        Position pos = new Position(1,2);
        game.build(pos);
        assertEquals(1,game.getBoard().getCell(pos).getLevel());
        game.build(pos);
        assertEquals(2,game.getBoard().getCell(pos).getLevel());
        game.build(pos);
        assertEquals(3,game.getBoard().getCell(pos).getLevel());
        game.build(pos);
        assertTrue(game.getBoard().getCell(pos).hasDome());
    }


    @Test
    public void canUsePowerTest(){
       /* game.setPlayerCard("Prometheus");
        game.setPlayerCard("Pan");
        game.setPlayerCard("Atlas");
        game.putWorker(firstPos);*/

        System.out.println(game.getPlayers().get(0).getPlayerNum());
        try{
            game.canUsePowerWorker(null);
        }catch (NullPointerException e){
            assertEquals("powerPos",e.getMessage());
        }
        Position pos = new Position(3,4);
        assertFalse(game.canUsePowerWorker(pos)); // verify Prometheus' canUsePower
        assertTrue(game.canUsePowerWorker(new Position(1,3)));


    }

    @Test
    public void usePowerTest(){
       /* game.setPlayerCard("Prometheus");
        game.setPlayerCard("Pan");
        game.setPlayerCard("Atlas");
        game.putWorker(firstPos);*/
        try{
            game.usePowerWorker(null);
        }catch (NullPointerException e){
            assertEquals("powerPos",e.getMessage());
        }
        Position pos = new Position(0,1);
        game.usePowerWorker(pos);
        assertEquals(1,game.getBoard().getCell(pos).getLevel());

    }

    @Test
    public void hasWonCurrentPlayerTest(){
        //game.putWorker(firstPos);
        game.moveWorker(new Position(1,2));
        assertFalse(game.hasWonCurrentPlayer());
        Position towerLevel1 = new Position(1,3);
        game.build(towerLevel1);
        game.moveWorker(towerLevel1);
        assertFalse(game.hasWonCurrentPlayer());
        Position towerLevel2 = new Position(1,4);
        game.build(towerLevel2);
        game.build(towerLevel2);
        game.moveWorker(towerLevel2);
        assertFalse(game.hasWonCurrentPlayer());
        Position towerLevel3 = new Position(2,4);
        game.build(towerLevel3);
        game.build(towerLevel3);
        game.build(towerLevel3);
        game.moveWorker(towerLevel3);
        assertTrue(game.hasWonCurrentPlayer());
    }



    /*@Test
    public void startTurnTest(){
    }*/









}
