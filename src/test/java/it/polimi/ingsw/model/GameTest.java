package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.CardInterface;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GameTest {

    private static Game game;
    private static List<PlayerInterface> players;
    private static Board board;
    private static Position firstPlayerFirstWorkerPos;
    private static Position firstPlayerSecondWorkerPos;
    private static Position secondPlayerFirstWorker;
    private static Position secondPlayerSecondWorker;
    private static Position lastPlayerFirstWorker;
    private static Position lastPlayerSecondWorker;


    @Before
    public void init() {
        players = new ArrayList<>(3);
        players.add(new Player("Jack", PlayerIndex.PLAYER0));
        players.add(new Player("Rock", PlayerIndex.PLAYER1));
        players.add(new Player("Creed", PlayerIndex.PLAYER2));
        game = new Game();
        game.setNumPlayer(true);
        try{
            game.addPlayer(PlayerIndex.PLAYER0,null);
        }catch (NullPointerException e){
            assertEquals("nickname",e.getMessage());
        }
        players.forEach(player -> game.addPlayer(player.getPlayerNum(), player.getNickname()));
        board = new Board();
    }

    @Test
    public void isGetNumPlayerCorrected() {
        assertEquals(3, game.getNumPlayer());
    }

    @Test
    public void isAddPlayerCorrected() {
        Game gameInstance = new Game();
        gameInstance.setNumPlayer(false);

        gameInstance.addPlayer(PlayerIndex.PLAYER0, "Paolo");
        assertEquals(1, gameInstance.getCurrentNumberOfPlayers());
        assertTrue(gameInstance.getPlayersNames().containsKey(PlayerIndex.PLAYER0));
        assertEquals(1, gameInstance.getPlayersNames().size());
        assertEquals("Paolo", gameInstance.getPlayersNames().get(PlayerIndex.PLAYER0));

        try {
            gameInstance.addPlayer(PlayerIndex.PLAYER2, "Pietro");
        } catch (IllegalArgumentException e) {
            assertEquals("You can't have PLAYER2 in two players game", e.getMessage());
        }

        gameInstance.addPlayer(PlayerIndex.PLAYER1, "Pietro");
        assertEquals(2, gameInstance.getCurrentNumberOfPlayers());
        assertTrue(gameInstance.getPlayersNames().containsKey(PlayerIndex.PLAYER0));
        assertTrue(gameInstance.getPlayersNames().containsKey(PlayerIndex.PLAYER1));
        assertEquals(2, gameInstance.getPlayersNames().size());
        assertEquals("Paolo", gameInstance.getPlayersNames().get(PlayerIndex.PLAYER0));
        assertEquals("Pietro", gameInstance.getPlayersNames().get(PlayerIndex.PLAYER1));
        assertEquals(2,gameInstance.getNumGamingPlayer());

        try {
            gameInstance.addPlayer(PlayerIndex.PLAYER1, "Nicola");
        } catch (MaxPlayersException e) {
            assertEquals(new MaxPlayersException().getMessage(), e.getMessage());
        }

    }


    // Simulation test general, god tested Prometheus

    @Test
    public void getCardsTest() {
        assertEquals(14, game.getCards().size());
        Map<String, String> decks = game.getCards();
        for (String godName : decks.keySet()) {
            assertEquals(decks.get(godName), game.getDeck().getGodCard(godName).getGodDescription());
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
    public void setPlayerCardsTest() {
        try {
            game.setPlayerCard(null);
        } catch (NullPointerException e) {
            assertEquals("godName", e.getMessage());
        }
        List<String> threeGods = new ArrayList<>(3);
        threeGods.add("Prometheus");
        threeGods.add("Pan");
        threeGods.add("Atlas");
        game.setGodsChosenByGodLike(threeGods);
        game.setPlayerCard("Prometheus");
        assertEquals(PlayerIndex.PLAYER0, game.getSortedIndexes().get(0));
        game.setPlayerCard("Pan");
        assertEquals(PlayerIndex.PLAYER1, game.getSortedIndexes().get(1));
        game.setPlayerCard("Atlas");
        assertEquals(PlayerIndex.PLAYER2, game.getSortedIndexes().get(2));
    }
    @Test
    public void putWorkerTest() {
        try{
            game.canPutWorker(null);
        }catch (NullPointerException e){
            assertEquals("putPosition",e.getMessage());
        }
        try {
            game.putWorker(null);
        } catch (NullPointerException e) {
            assertEquals("putPosition", e.getMessage());
        }
        game.putWorker(new Position(2, 2));
        assertEquals(PlayerIndex.PLAYER0, game.getBoard().getOccupiedPlayer(new Position(2, 2)));

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

    // Test game, gods: Pan, Artemis, Demeter
    @Test
    public void gameTestPanArtemisDemeter() {
        //god Like sceglie le carte
        List<String> gods = new ArrayList<>(List.of("Pan", "Artemis", "Demeter"));
        game.setGodsChosenByGodLike(gods);
        assertTrue(game.getDeck().getGodCard("Demeter").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Pan").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Artemis").getBoolChosenGod());

        Collections.rotate(gods, -1);
        game.setGodsChosenByGodLike(gods);
        for (String god : gods) game.setPlayerCard(god);

        Collections.rotate(gods, 1);
        game.getGodNames().forEach((index, name) -> assertEquals(gods.get(index.ordinal()), name));


        game.chooseFirstPlayer(PlayerIndex.PLAYER2);

        List<Position> pos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 3),
                new Position(1, 1),
                new Position(4, 2),
                new Position(2, 0),
                new Position(2, 4)
        ));

        for (Position p : pos) {
            assertTrue(game.canPutWorker(p));
            game.putWorker(p);
        }
        for (int i = 0; i < pos.size(); i++)
            assertEquals(game.getSortedIndexes().get(i / 2), game.getBoard().getOccupiedPlayer(pos.get(i)));

        List<Position> posMosse = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 0),
                new Position(2, 1),
                new Position(1, 1),
                new Position(3, 0),
                new Position(4, 1)
        ));


        Position powerPos = new Position(3, 1);
        Position powerPos2 = new Position(2, 2);
        Position towerLevel1 = new Position(4, 0);
        Position towerLevel2 = new Position(3, 1);
        game.build(towerLevel1);
        game.build(towerLevel2);

        int cont = 0;
        while (cont < 3) {
            game.startTurn();
            game.setStartingWorker(pos.get(cont * 2));
            game.canMoveWorker(posMosse.get(cont * 2));
            game.moveWorker(posMosse.get(cont * 2));
            assertEquals(game.getSortedIndexes().get(cont), game.getBoard().getOccupiedPlayer(posMosse.get(cont * 2)));
            assertFalse(game.hasWonCurrentPlayer());
            //Se sono artemide attivo il potere
            if (game.getCurrentPlayerGodName().equals("Artemis")) {

                assertTrue(game.canUsePowerWorker(powerPos));
                assertEquals(game.getCurrentPlayerPowerState(), GameState.BUILD);
                game.usePowerWorker(powerPos);
                assertEquals(game.getCurrentPlayerNextState(), GameState.SECOND_MOVE);
                assertEquals(game.getSortedIndexes().get(cont), game.getBoard().getOccupiedPlayer(powerPos));
            }
            game.canBuild(posMosse.get(cont * 2 + 1));
            assertEquals(game.getCurrentState(),GameState.START_GAME);
            game.build(posMosse.get(cont * 2 + 1));
            assertEquals(1, game.getBoard().getCell(posMosse.get(cont * 2 + 1)).getLevel());
            if (game.getCurrentPlayerGodName().equals("Demeter")) {

                game.canUsePowerWorker(powerPos2);
                game.usePowerWorker(powerPos2);
                assertEquals(1, game.getBoard().getCell(powerPos2).getLevel());

            }
            game.endTurn();
            cont++;
        }


        game.startTurn();
        game.endTurn();
        game.startTurn();
        game.endTurn();
        game.startTurn();
        game.setStartingWorker(new Position(3, 1));
        assertEquals(PlayerIndex.PLAYER1, game.getBoard().getOccupiedPlayer(new Position(3, 1)));
        assertTrue(game.canMoveWorker(towerLevel1));
        game.moveWorker(towerLevel1);
        assertTrue(game.canMoveWorker(towerLevel2));
        game.moveWorker(towerLevel2);
        assertFalse(game.canMoveWorker(new Position(4, 2)));
        assertFalse(game.canUsePowerWorker(new Position(4, 2)));

    }


    //Test game, Gods: Atlas, Apollo, Prometheus
    @Test
    public void gameTestAtlasApolloPrometheus() {
        List<String> gods = new ArrayList<>(List.of("Atlas", "Apollo", "Prometheus"));
        game.setGodsChosenByGodLike(gods);
        assertTrue(game.getDeck().getGodCard("Atlas").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Apollo").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Prometheus").getBoolChosenGod());
        Collections.rotate(gods, -1);
        game.setGodsChosenByGodLike(gods);
        for (String god : gods) game.setPlayerCard(god);
        Collections.rotate(gods, 1);
        game.getGodNames().forEach((index, name) -> assertEquals(gods.get(index.ordinal()), name));

        game.chooseFirstPlayer(PlayerIndex.PLAYER0);
        assertEquals(new ArrayList<>(List.of(PlayerIndex.PLAYER0, PlayerIndex.PLAYER1, PlayerIndex.PLAYER2)),
                game.getSortedIndexes());
        List<Position> pos = new ArrayList<>(List.of(
                new Position(0, 0),
                new Position(0, 2),
                new Position(0, 3),
                new Position(1, 3),
                new Position(4, 0),
                new Position(3, 3)
        ));

        for (Position p : pos)
            game.putWorker(p);

        for (int i = 0; i < pos.size(); i++)
            assertEquals(game.getSortedIndexes().get(i / 2), game.getBoard().getOccupiedPlayer(pos.get(i)));

        Position powerPrometheus = new Position(4, 1);

        game.startTurn(); // Atlas turn
        assertTrue(game.canPlayerMoveAWorker().size() > 0);
        game.setStartingWorker(pos.get(0));
        assertTrue(game.canMoveWorker(new Position(1, 0)));
        game.moveWorker(new Position(1, 0));
        assertEquals(PlayerIndex.PLAYER0, game.getBoard().getOccupiedPlayer(new Position(1, 0)));
        assertTrue(game.canBuild(new Position(1, 1)));
        assertTrue(game.canUsePowerWorker(new Position(1,1)));
        game.usePowerWorker(new Position(1, 1));
        assertTrue(game.getBoard().getCell(new Position(1, 1)).hasDome());
        game.endTurn();

        game.startTurn(); // Apollo turn
        assertTrue(game.canPlayerMoveAWorker().size() > 0);
        game.setStartingWorker(pos.get(2));
        assertFalse(game.canMoveWorker(pos.get(1)));
        assertTrue(game.canUsePowerWorker(pos.get(1)));
        game.usePowerWorker(pos.get(1));
        assertEquals(PlayerIndex.PLAYER0, game.getBoard().getOccupiedPlayer(pos.get(2)));
        assertEquals(PlayerIndex.PLAYER1, game.getBoard().getOccupiedPlayer(pos.get(1)));
        assertTrue(game.canPlayerBuildWithSelectedWorker());
        assertTrue(game.canBuild(new Position(1, 2)));
        game.build(new Position(1, 2));
        assertEquals(1, game.getBoard().getCell(new Position(1, 2)).getLevel());
        game.endTurn();

        game.startTurn(); // Prometheus turn
        game.setStartingWorker(pos.get(4));
        assertTrue(game.canUsePowerWorker(powerPrometheus));
        game.usePowerWorker(powerPrometheus);
        assertEquals(1,game.getBoard().getCell(powerPrometheus).getLevel());
        assertTrue(game.canMoveWorker(new Position(3, 0)));
        game.moveWorker(new Position(3, 0));
        assertTrue(game.canBuild(new Position(3, 1)));
        game.build(new Position(3, 1));
        assertEquals(1, game.getBoard().getCell(new Position(3, 1)).getLevel());
        game.endTurn();
        game.startTurn();
        game.endTurn();

        game.startTurn(); // Apollo turn
        game.setStartingWorker(new Position(1, 3));
        assertFalse(game.canMoveWorker(new Position(0, 2)));
        game.endTurn();

        game.startTurn(); // Prometheus turn
        game.setStartingWorker(new Position(3, 3));
        game.build(new Position(3, 4));
        assertTrue(game.canUsePowerWorker(new Position(4, 3)));
        game.usePowerWorker(new Position(4, 3));
        assertFalse(game.canMoveWorker(new Position(3, 4)));
    }

    @Test
    public void gameTestHephaestusMinotaurAthena() {
        //god Like sceglie le carte
        List<String> gods = new ArrayList<>(List.of("Hephaestus", "Minotaur", "Athena"));
        game.setGodsChosenByGodLike(gods);
        assertTrue(game.getDeck().getGodCard("Hephaestus").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Minotaur").getBoolChosenGod());
        assertTrue(game.getDeck().getGodCard("Athena").getBoolChosenGod());

        Collections.rotate(gods, -1);
        for (String god : gods) game.setPlayerCard(god);
        Collections.rotate(gods, 1);
        game.getGodNames().forEach((index, name) -> assertEquals(gods.get(index.ordinal()), name));

        game.chooseFirstPlayer(PlayerIndex.PLAYER1);
        List<Position> pos = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 3),
                new Position(2, 0),
                new Position(1, 2),
                new Position(1, 1),
                new Position(1, 4)
        ));

        for (Position p : pos)
            game.putWorker(p);
        for (int i = 0; i < pos.size(); i++)
            assertEquals(game.getSortedIndexes().get(i / 2), game.getBoard().getOccupiedPlayer(pos.get(i)));

        List<Position> posMosse = new ArrayList<>(List.of(
                new Position(0, 1),
                new Position(0, 0),
                new Position(3, 0),
                new Position(4, 1),
                new Position(1, 2),
                new Position(1, 1)
        ));

        Position powerPos = new Position(0, 2);
        Position powerPos2 = new Position(1, 2);
        Position powerPosAthena = new Position(3, 1);

        int cont = 0;
        while (cont < 3) {
            game.startTurn();
            game.setStartingWorker(pos.get(cont * 2));
            game.canMoveWorker(posMosse.get(cont * 2));
            //If Minotaur actives the power
            if (game.getCurrentPlayerGodName().equals("Minotaur")) {
                assertFalse(game.canMoveWorker(powerPos2));
                assertTrue(game.canUsePowerWorker(powerPos2));
                game.usePowerWorker(powerPos2);
                assertEquals(game.getSortedIndexes().get(cont), game.getBoard().getOccupiedPlayer(powerPos2));
                assertEquals(game.getSortedIndexes().get(1), game.getBoard().getOccupiedPlayer(new Position(2, 3)));
            }
            game.moveWorker(posMosse.get(cont * 2));
            //If Athena actives the power
            if (game.getCurrentPlayerGodName().equals("Athena")) {
                game.build(powerPosAthena);
                assertFalse(game.canMoveWorker(powerPosAthena));
                assertTrue(game.canUsePowerWorker(powerPosAthena));
                game.usePowerWorker(powerPosAthena);
            }
            assertFalse(game.hasWonCurrentPlayer());
            if (game.getCurrentPlayerGodName().equals("Hephaestus")) {
                assertTrue(game.canBuild(powerPos));
                game.build(powerPos);
                assertTrue(game.canUsePowerWorker(powerPos));
                game.usePowerWorker(powerPos);
                assertEquals(game.getBoard().getCell(powerPos).getLevel(), 2);
            } else {
                game.canBuild(posMosse.get(cont * 2 + 1));
                game.build(posMosse.get(cont * 2 + 1));
            }
            game.endTurn();
            cont++;
        }
        //Test Athena counter:
        game.startTurn();
        game.setStartingWorker(posMosse.get(0));
        assertEquals(game.getSortedIndexes().get(0), game.getBoard().getOccupiedPlayer(posMosse.get(0)));
        game.endTurn();
        game.startTurn();
    }

    @Test
    public void canPlayerMoveAWorkerTest() {

        //First test: generic Player, check if canPlayerMoveAWorker() works
        firstPlayerFirstWorkerPos = new Position(1, 1);
        firstPlayerSecondWorkerPos = new Position(3, 3);

        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        game.endTurn();
        game.endTurn();

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        //Block worker in firstPlayerFirstWorkerPos position
        for (Cell c : board.getAdjacentCells(firstPlayerFirstWorkerPos)) {
            game.build(c.getPosition());
            game.build(c.getPosition());
        }

        assertEquals(game.canPlayerMoveAWorker().size(), 1);
        assertEquals(game.canPlayerMoveAWorker().get(0), firstPlayerSecondWorkerPos);

        //Block worker in firstPlayerSecondWorkerPos position
        for (Cell c : board.getAdjacentCells(firstPlayerSecondWorkerPos)) {
            game.build(c.getPosition());
            game.build(c.getPosition());
        }

        assertEquals(game.canPlayerMoveAWorker().size(), 0);

        game.endTurn();

        //Second test: check if Apollo is not blocked if the only cell where he can move is occupied by an enemy

        secondPlayerFirstWorker = new Position(4, 0);
        secondPlayerSecondWorker = new Position(0, 4);

        List<String> gods = new ArrayList<>();
        gods.add("Apollo");
        gods.add("Athena");
        gods.add("Pan");

        game.setGodsChosenByGodLike(gods);

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.setPlayerCard("Apollo");

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.putWorker(secondPlayerFirstWorker);
        game.putWorker(secondPlayerSecondWorker);

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        //Case where Apollo can only move in a cell occupied by an enemy
        game.build(new Position(0, 3));
        game.build(new Position(0, 3));
        game.build(new Position(1, 4));
        game.build(new Position(1, 4));

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        //Put an enemy near Apollo
        game.putWorker(new Position(1, 3));

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        //Check Apollo is still free
        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        //Block the other worker of Apollo
        for (Cell c : board.getAdjacentCells(secondPlayerFirstWorker)) {
            game.build(c.getPosition());
            game.build(c.getPosition());
        }

        //Check that now only one worker of Apollo is free
        assertEquals(game.canPlayerMoveAWorker().size(), 1);
        assertEquals(game.canPlayerMoveAWorker().get(0), secondPlayerSecondWorker);
    }

    @Test
    public void canPlayerMoveAWorkerTestPart2() {

        firstPlayerFirstWorkerPos = new Position(1, 1);
        firstPlayerSecondWorkerPos = new Position(3, 3);

        List<String> gods = new ArrayList<>();
        gods.add("Minotaur");
        gods.add("Athena");
        gods.add("Pan");

        game.setGodsChosenByGodLike(gods);
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        //Check that Athena uses her dedicated canMoveWithPower() method
        game.setPlayerCard("Athena");

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        //Build tower level one near Athena
        for (Cell c : board.getAdjacentCells(firstPlayerSecondWorkerPos)) {
            game.build(c.getPosition());
        }

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        //Block with tower level two the same worker
        for (Cell c : board.getAdjacentCells(firstPlayerSecondWorkerPos)) {
            game.build(c.getPosition());
        }

        assertEquals(game.canPlayerMoveAWorker().size(), 1);

        //Test Minotaur
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        game.setPlayerCard("Minotaur");

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);

        //put an enemy of Minotaur on the board
        game.putWorker(new Position(3, 0));
        game.putWorker(new Position(1, 0));

        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        //place Minotaur workers
        secondPlayerFirstWorker = new Position(4, 0);
        secondPlayerSecondWorker = new Position(0, 4);

        game.putWorker(secondPlayerFirstWorker);
        game.putWorker(secondPlayerSecondWorker);

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        game.setStartingWorker(secondPlayerFirstWorker);

        assertEquals(game.canPlayerMoveAWorker().size(), 2);

        game.build(new Position(4, 1));
        game.build(new Position(4, 1));
        game.build(new Position(3, 1));
        game.build(new Position(3, 1));

        //Minotaur can move only in the cell with the enemy
        assertEquals(game.canPlayerMoveAWorker().size(), 1);

        //Put an other worker behind the enemy to block Minotaur power
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);

        game.setStartingWorker(new Position(1, 0));

        game.moveWorker(new Position(2, 0));

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        game.setStartingWorker(secondPlayerFirstWorker);

        //Now Minotaur cant use his power, so his worker is blocked
        assertEquals(game.canPlayerMoveAWorker().size(), 1);
        assertEquals(game.canPlayerMoveAWorker().get(0), secondPlayerSecondWorker);
    }

    @Test
    public void getMovePositionsTest() {
        firstPlayerFirstWorkerPos = new Position(1, 1);
        firstPlayerSecondWorkerPos = new Position(3, 3);

        List<String> gods = new ArrayList<>();
        gods.add("Minotaur");
        gods.add("Athena");
        gods.add("Pan");

        game.setGodsChosenByGodLike(gods);
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        //Check some moves for Minotaur
        game.setPlayerCard("Minotaur");

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        Position blockedPos = new Position(2, 0);

        game.putWorker(new Position(1, 2));
        game.putWorker(blockedPos);

        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.setStartingWorker(firstPlayerSecondWorkerPos);

        assertEquals(game.getMovePositions(firstPlayerSecondWorkerPos).size(), 8);

        game.setStartingWorker(firstPlayerFirstWorkerPos);
        assertEquals(game.getMovePositions(firstPlayerFirstWorkerPos).size(), 7);
        assertFalse(game.getMovePositions(firstPlayerFirstWorkerPos).contains(blockedPos));

        //Block the power of Minotaur
        Position domePos = new Position(1, 3);

        game.build(domePos);
        game.build(domePos);
        game.build(domePos);
        game.build(domePos);

        assertEquals(game.getMovePositions(firstPlayerFirstWorkerPos).size(), 6);
        assertFalse(game.getMovePositions(firstPlayerFirstWorkerPos).contains(domePos));

        //block some cells with buildings
        Position lvl2pos = new Position(0, 0);

        game.build(lvl2pos);
        game.build(lvl2pos);

        assertEquals(game.getMovePositions(firstPlayerFirstWorkerPos).size(), 5);
        assertFalse(game.getMovePositions(firstPlayerFirstWorkerPos).contains(lvl2pos));

        Position lvl3pos = new Position(2, 2);

        game.build(lvl3pos);
        game.build(lvl3pos);
        game.build(lvl3pos);

        assertEquals(game.getMovePositions(firstPlayerFirstWorkerPos).size(), 4);
        assertFalse(game.getMovePositions(firstPlayerFirstWorkerPos).contains(lvl3pos));
    }

    @Test
    public void getBuildPositionsTest() {
        firstPlayerFirstWorkerPos = new Position(1, 1);
        firstPlayerSecondWorkerPos = new Position(3, 3);

        List<String> gods = new ArrayList<>();
        gods.add("Minotaur");
        gods.add("Athena");
        gods.add("Pan");

        game.setGodsChosenByGodLike(gods);
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.setPlayerCard("Pan");

        game.putWorker(new Position(2, 2));
        game.putWorker(new Position(2, 0));

        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        game.endTurn();
        game.endTurn();

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.setStartingWorker(firstPlayerFirstWorkerPos);

        assertEquals(game.getBuildPositions(firstPlayerFirstWorkerPos).size(), 6);

        Position lvl2Pos = new Position(0, 1);

        game.build(lvl2Pos);
        game.build(lvl2Pos);

        assertEquals(game.getBuildPositions(firstPlayerFirstWorkerPos).size(), 6);
        assertTrue(game.getBuildPositions(firstPlayerFirstWorkerPos).contains(lvl2Pos));

        Position domePos = new Position(0, 2);

        game.build(domePos);
        game.build(domePos);
        game.build(domePos);
        game.build(domePos);

        assertEquals(game.getBuildPositions(firstPlayerFirstWorkerPos).size(), 5);
        assertFalse(game.getBuildPositions(firstPlayerFirstWorkerPos).contains(domePos));
    }

    @Test
    public void getPowerPositionsTest() {
        firstPlayerFirstWorkerPos = new Position(1, 1);
        firstPlayerSecondWorkerPos = new Position(3, 3);

        secondPlayerFirstWorker = new Position(1, 2);
        secondPlayerSecondWorker = new Position(2, 0);

        lastPlayerFirstWorker = new Position(2, 1);
        lastPlayerSecondWorker = new Position(2, 2);

        List<String> gods = new ArrayList<>();
        gods.add("Minotaur");
        gods.add("Apollo");
        gods.add("Athena");

        game.setGodsChosenByGodLike(gods);


        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);
        game.setPlayerCard("Minotaur");

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);
        game.setPlayerCard("Apollo");

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);
        game.setPlayerCard("Athena");

        game.chooseFirstPlayer(PlayerIndex.PLAYER1);

        //Put all the workers on the map
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.putWorker(firstPlayerFirstWorkerPos);
        game.putWorker(firstPlayerSecondWorkerPos);

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        game.putWorker(secondPlayerFirstWorker);
        game.putWorker(secondPlayerSecondWorker);

        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);

        game.putWorker(lastPlayerFirstWorker);
        game.putWorker(lastPlayerSecondWorker);

        //Check power positions for minotaur
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER1);

        game.setStartingWorker(firstPlayerFirstWorkerPos);

        assertEquals(game.getPowerPositions(firstPlayerFirstWorkerPos).size(), 2);
        assertTrue(game.getPowerPositions(firstPlayerFirstWorkerPos).contains(secondPlayerFirstWorker));
        assertTrue(game.getPowerPositions(firstPlayerFirstWorkerPos).contains(lastPlayerFirstWorker));

        game.endTurn();

        //Check power positions for apollo
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER2);

        game.setStartingWorker(secondPlayerSecondWorker);

        assertEquals(game.getPowerPositions(secondPlayerSecondWorker).size(), 2);
        assertTrue(game.getPowerPositions(secondPlayerSecondWorker).contains(firstPlayerFirstWorkerPos));
        assertTrue(game.getPowerPositions(secondPlayerSecondWorker).contains(lastPlayerFirstWorker));

        game.endTurn();

        //Check power positions for athena
        assertEquals(game.getCurrentPlayerIndex(), PlayerIndex.PLAYER0);

        game.setStartingWorker(lastPlayerFirstWorker);

        assertEquals(game.getPowerPositions(lastPlayerFirstWorker).size(), 0);

        Position lvlupPos1 = new Position(3, 0);

        game.build(lvlupPos1);

        assertEquals(game.getPowerPositions(lastPlayerFirstWorker).size(), 1);
        assertTrue(game.getPowerPositions(lastPlayerFirstWorker).contains(lvlupPos1));

        game.setStartingWorker(lastPlayerSecondWorker);

        assertEquals(game.getPowerPositions(lastPlayerSecondWorker).size(), 0);

        Position lvlupPos2 = new Position(3, 1);

        game.build(lvlupPos2);

        assertEquals(game.getPowerPositions(lastPlayerSecondWorker).size(), 1);
        assertTrue(game.getPowerPositions(lastPlayerFirstWorker).contains(lvlupPos2));

        game.setStartingWorker(lastPlayerFirstWorker);

        assertEquals(game.getPowerPositions(lastPlayerFirstWorker).size(), 2);
        assertTrue(game.getPowerPositions(lastPlayerFirstWorker).contains(lvlupPos1));
        assertTrue(game.getPowerPositions(lastPlayerFirstWorker).contains(lvlupPos2));
    }

    @After
    public void delete() {
        players = null;
        game = null;
        board = null;
    }

}
