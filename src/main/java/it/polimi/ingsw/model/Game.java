package it.polimi.ingsw.model;


import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private static Game game;
    private static final Random rand = new Random();
    private boolean gameStarted;
    private Board board;
    private Deck deck;
    private List<Player> players;
    private int indexPlayer;


    private Game(List<Player> players) {
        this.players = new ArrayList<>(players);
        deck = new Deck(players.size());
        board = new Board();
        gameStarted = false;
        indexPlayer = 0;
    }

    public static Game getInstance(List<Player> players) throws IllegalArgumentException, NullPointerException {
        if (players.size() < 2 || players.size() > 3)
            throw new IllegalArgumentException("Players size can be 2 or 3, you passed list with size = " + players.size());
        if (players == null)
            throw new NullPointerException();


        if (game == null)
            game = new Game(players);
        return game;
    }

    public void loadGame() {

    }

    public void saveGame(){

    }

    public boolean IsGameStarted(){
        return gameStarted;
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public List<Player> getPlayer() {
        return players;
    }

    public void setPlayer(List<Player> players) {
        this.players = players;
    }


    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }
}
