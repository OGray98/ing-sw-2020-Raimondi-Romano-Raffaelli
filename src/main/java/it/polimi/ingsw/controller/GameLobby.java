package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.exception.NameAlreadyTakenException;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.HashMap;

/**
 * Class that supports the initial phase of the game
 * It is a lobby for the players that are connected
 * It is used to access the model only when all players are connected and ready to play
 * */
public class GameLobby {

    private final HashMap<PlayerIndex, String> lobbyPlayers;
    private boolean threePlayersGame;
    private boolean threePlayersGameDecided = false;

    public int getNumberOfPlayerInLobby(){
       if(!threePlayersGame){
           return 2;
       }
       return 3;
    }

    public GameLobby() {
        this.lobbyPlayers = new HashMap<>();
    }

    public void setThreePlayersGame(boolean threePlayersGame) {
        this.threePlayersGame = threePlayersGame;
    }

    public String getNickname(PlayerIndex index) {
        return this.lobbyPlayers.get(index);
    }

    /**
     * Add a player in the lobby
     *
     * @param playerIndex index of the player to add
     * @param playerNick  nickName of the player to add
     * @throws MaxPlayersException       if lobby is already full
     * @throws NameAlreadyTakenException if the nickname chosen is not available
     * @throws IllegalArgumentException  if the player is already in the lobby
     */
    public void addPlayer(PlayerIndex playerIndex, String playerNick) {
        if(isFull() && threePlayersGameDecided) throw new MaxPlayersException();
        if(isNameAlreadyTaken(playerNick)) throw new NameAlreadyTakenException();
        if(isPlayerAlreadyInLobby(playerIndex)) throw new IllegalArgumentException("Player " + playerIndex + " is already in the lobby");

        if(lobbyPlayers.size() == 3) return;
        this.lobbyPlayers.put(playerIndex, playerNick);
    }

    public HashMap<PlayerIndex, String> getLobbyPlayers(){
        return this.lobbyPlayers;
    }

    /**
     * @return true iff the nick given is already chosen by other plaeyrs
     * @param nickName is the nickname to check
     * */
    public boolean isNameAlreadyTaken(String nickName){
        for(String s : this.lobbyPlayers.values()){
            if(nickName.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return true iff the player given is already in the lobby
     * @param playerIndex is the index of the player to check
     * */
    public boolean isPlayerAlreadyInLobby(PlayerIndex playerIndex){
        return this.lobbyPlayers.containsKey(playerIndex);
    }

    /**
     * Check if lobby is full
     * @return the state of the lobby
     * */
    public boolean isFull(){
        if(this.threePlayersGame){
            return this.lobbyPlayers.size() == 3;
        }
        return this.lobbyPlayers.size() == 2;
    }

    /**
     * Method that set the number of players for this game chosen by the godLike
     * */
    public void setThreePlayersGameDecided(boolean threePlayersGameDecided) {
        this.threePlayersGameDecided = threePlayersGameDecided;
    }

    /**
     * Method that remove a player from the lobby
     * */
    public void removeFromLobby(PlayerIndex playerToRemove){
        lobbyPlayers.remove(playerToRemove);
    }
}
