package it.polimi.ingsw;


import java.util.List;
import java.util.Random;

public class Game {

    private static  Game game;
    private static final Random rand = new Random();
    private boolean gamestarted;
    private Board board;
    private Deck deck;
    private List<Player> player;
    private int IndexPlayer;


    private Game(List<Player> player){
        this.player = player;
        deck = new Deck(player.size());
        board = new Board();
        gamestarted = false;
        IndexPlayer = 0;

    }

    public static Game getInstance(List<Player> player){
        if(game == null){
            game = new Game(player);
        }
        //if(gamestarted){
           // throw new ("Game already started"); // create exception game full
        //}
        if(player == null){
            throw new NullPointerException("Player null");
        }
        if(player.size() < 2 || player.size() > 3){
            throw new ArrayIndexOutOfBoundsException(); // create exception maxplayerlobby

        }
        return game;

    }

    public void loadGame(){

    }

    public void saveGame(){

    }

    public boolean IsGameStarted(){
        return gamestarted;
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
        return player;
    }

    public void setPlayer(List<Player> player) {
        this.player = player;
    }


    public int getIndexPlayer() {
        return IndexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        IndexPlayer = indexPlayer;
    }
}
