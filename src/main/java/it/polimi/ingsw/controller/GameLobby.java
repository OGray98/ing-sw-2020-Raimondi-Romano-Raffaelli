package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.exception.NameAlreadyTakenException;

import java.util.ArrayList;

public class GameLobby {

    private ArrayList<String> lobbyPlayers;
    private boolean threePlayersGame;

    public GameLobby(){
        this.lobbyPlayers = new ArrayList<>();
    }

    public void setThreePlayersGame(boolean threePlayersGame){
        this.threePlayersGame = threePlayersGame;
    }

    public void addPlayer(String playerNick){
        if(isFull()) throw new MaxPlayersException();
        if(isNameAlreadyTaken(playerNick)) throw new NameAlreadyTakenException();

        this.lobbyPlayers.add(playerNick);
    }

    public ArrayList<String> getLobbyPlayers(){
        return this.lobbyPlayers;
    }

    public boolean isNameAlreadyTaken(String nickName){
        for(String s : this.lobbyPlayers){
            if(nickName.equals(s)){
                return true;
            }
        }
        return false;
    }

    public boolean isFull(){
        if(this.threePlayersGame){
            return this.lobbyPlayers.size() == 3;
        }
        return this.lobbyPlayers.size() == 2;
    }
}
