package org.example;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
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
        /*
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

    /*
    @Test
    public void isCorrectedConstructor() {

        try {
            Game.getInstance(players);
        } catch (AlreadyPresentGameException e) {
            assertEquals("There already is a game instance", e.getMessage());
        }

        assertSame(game, Game.getInstance());
    }


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
        game.chooseFirstPlayer(PlayerIndex.PLAYER1);
        assertEquals(PlayerIndex.PLAYER1,game.getPlayers().get(0).getPlayerNum());
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
*/


    /*@Test
    public void startTurnTest(){
    }*/

    /*@Test
    public void totalTest() {
        //god Like sceglie le carte
        List<String> gods = new ArrayList<>(List.of("Pan", "Artemis", "Demeter"));
        game.setGodsChosenByGodLike(gods);
        assertTrue(game.getDeck().getGodCard("Artemis").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Pan").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Demeter").getBoolChosenGod());

        for (int i = 0; i < gods.size(); i++) {
            game.setPlayerCard(gods.get(i));
            assertEquals(gods.get(i), game.getPlayers().get(i).getGodName());
        }

        game.chooseFirstPlayer(PlayerIndex.PLAYER1);
        assertEquals(PlayerIndex.PLAYER1, game.getPlayers().get(0).getPlayerNum());
        assertEquals(PlayerIndex.PLAYER2, game.getPlayers().get(1).getPlayerNum());
        assertEquals(PlayerIndex.PLAYER0, game.getPlayers().get(2).getPlayerNum());

        List<Position> pos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 3),
                new Position(1, 1),
                new Position(1, 3),
                new Position(2, 0),
                new Position(2, 4)
        ));

        for (Position p : pos)
            game.putWorker(p);
        for (int i = 0; i < pos.size(); i++)
            assertEquals(game.getPlayers().get(i / 2).getPlayerNum(), game.getBoard().getOccupiedPlayer(pos.get(i)));

        List<Position> posMosse = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 0),
                new Position(2, 1),
                new Position(1, 1),
                new Position(3, 0),
                new Position(4, 1)
        ));

        Position powerPos = new Position(0, 2);
        Position powerPos2 = new Position(2, 2);

        int cont = 0;
        while (cont < 3) {
            game.startTurn();
            game.setStartingWorker(pos.get(cont * 2));
            game.canMoveWorker(posMosse.get(cont * 2));
            game.moveWorker(posMosse.get(cont * 2));
            assertEquals(game.getPlayers().get(cont).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(cont * 2)));
            assertFalse(game.hasWonCurrentPlayer());
            //Se sono artemide attivo il potere
            if (game.getPlayers().get(cont).getGodName().equals("Artemis")) {

                game.canUsePowerWorker(powerPos);
                game.usePowerWorker(powerPos);
                assertEquals(game.getPlayers().get(cont).getPlayerNum(), game.getBoard().getOccupiedPlayer(powerPos));
            }
            game.canBuild(posMosse.get(cont * 2 + 1));
            game.build(posMosse.get(cont * 2 + 1));
            assertEquals(1, game.getBoard().getCell(posMosse.get(cont * 2 + 1)).getLevel());
            if (game.getPlayers().get(cont).getGodName().equals("Demeter")) {

                game.canUsePowerWorker(powerPos2);
                game.usePowerWorker(powerPos2);
                assertEquals(1, game.getBoard().getCell(powerPos2).getLevel());
            }
            game.endTurn();
            cont++;
        }

        game.startTurn();
        game.setStartingWorker(powerPos);
    }*/

    @Test
    public void totalTestHephaestusMinotaurAthena(){
        //god Like sceglie le carte
        List<String> gods = new ArrayList<>(List.of("Hephaestus", "Minotaur", "Athena"));
        game.setGodsChosenByGodLike(gods);
        assertTrue(game.getDeck().getGodCard("Hephaestus").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Minotaur").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Athena").getBoolChosenGod());

        for (int i = 0; i < gods.size(); i++) {
            game.setPlayerCard(gods.get(i));
            assertEquals(gods.get(i), game.getPlayers().get(i).getGodName());
        }

        game.chooseFirstPlayer(PlayerIndex.PLAYER0);
        assertEquals(PlayerIndex.PLAYER1, game.getPlayers().get(1).getPlayerNum());
        assertEquals(PlayerIndex.PLAYER2, game.getPlayers().get(2).getPlayerNum());
        assertEquals(PlayerIndex.PLAYER0, game.getPlayers().get(0).getPlayerNum());

        List<Position> pos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 3),
                new Position(1, 1),
                new Position(1, 4),
                new Position(2, 0),
                new Position(1,2)
        ));

        for (Position p : pos)
            game.putWorker(p);
        for (int i = 0; i < pos.size(); i++)
            assertEquals(game.getPlayers().get(i / 2).getPlayerNum(), game.getBoard().getOccupiedPlayer(pos.get(i)));

        List<Position> posMosse = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 0),
                new Position(1, 2),
                new Position(1, 1),
                new Position(3, 0),
                new Position(4, 1)
        ));

        Position powerPos = new Position(0,0);
        Position powerPos2 = new Position(1,2);

        int cont = 0;
        while(cont < 3 ){
            game.startTurn();
            game.setStartingWorker(pos.get(cont * 2));
            game.canMoveWorker(posMosse.get(cont * 2));
            //If Minotaur actives the power
            if (game.getPlayers().get(cont).getGodName().equals("Minotaur")){
                assertFalse(game.canMoveWorker(posMosse.get(cont*2)));
                assertTrue(game.getPlayers().get(cont).getActivePower());
                assertTrue(game.canUsePowerWorker(powerPos2));
                game.usePowerWorker(powerPos2);
                assertEquals(game.getPlayers().get(cont).getPlayerNum(), game.getBoard().getOccupiedPlayer(powerPos2));
                assertEquals(game.getPlayers().get(2).getPlayerNum(), game.getBoard().getOccupiedPlayer(new Position(1,3)));
            }
            game.moveWorker(posMosse.get(cont * 2));
            //If Athena actives the power
            if (game.getPlayers().get(cont).getGodName().equals("Athena")){
                assertTrue(game.canUsePowerWorker(posMosse.get(4)));
                game.usePowerWorker(posMosse.get(4));
            }
            assertEquals(game.getPlayers().get(cont).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(cont * 2)));
            assertFalse(game.hasWonCurrentPlayer());
            game.canBuild(posMosse.get(cont * 2 + 1));
            game.build(posMosse.get(cont * 2 + 1));
            //If Hephaestus actives the power
            if (game.getPlayers().get(cont).getGodName().equals("Hephaestus")){
                assertTrue(game.canUsePowerWorker(powerPos));
                game.usePowerWorker(powerPos);
                assertEquals(game.getBoard().getCell(powerPos).getLevel(), 2);
            }
            //assertEquals(1, game.getBoard().getCell(posMosse.get(cont * 2 + 1)).getLevel());
            game.endTurn();
            cont++;
        }
        //Test Athena counter:
        game.startTurn();
        game.setStartingWorker(posMosse.get(0));
        assertEquals(game.getPlayers().get(0).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(0)));
        assertTrue(game.getPlayers().get(0).getCantGoUp());
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(pos.get(3));
        assertEquals(game.getPlayers().get(1).getPlayerNum(), game.getBoard().getOccupiedPlayer(pos.get(3)));
        assertTrue(game.getPlayers().get(1).getCantGoUp());
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(posMosse.get(4));
        assertEquals(game.getPlayers().get(2).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(4)));
        assertFalse(game.getPlayers().get(2).getCantGoUp());
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(posMosse.get(0));
        assertEquals(game.getPlayers().get(0).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(0)));
        assertFalse(game.getPlayers().get(0).getCantGoUp());
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(pos.get(3));
        assertEquals(game.getPlayers().get(1).getPlayerNum(), game.getBoard().getOccupiedPlayer(pos.get(3)));
        assertFalse(game.getPlayers().get(1).getCantGoUp());
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(posMosse.get(4));
        assertEquals(game.getPlayers().get(2).getPlayerNum(), game.getBoard().getOccupiedPlayer(posMosse.get(4)));
        assertFalse(game.getPlayers().get(2).getCantGoUp());
    }







}
