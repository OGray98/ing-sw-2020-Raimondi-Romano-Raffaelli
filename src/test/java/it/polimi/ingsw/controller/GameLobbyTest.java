package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.exception.NameAlreadyTakenException;
import it.polimi.ingsw.model.player.PlayerIndex;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameLobbyTest {

    private static GameLobby lobby2p;
    private static GameLobby lobby3p;

    @Before
    public void init(){
        lobby2p = new GameLobby();
        lobby3p = new GameLobby();
    }

    @Test
    public void isPlayerAlreadyInLobbyTest(){
        lobby2p.setThreePlayersGame(false);

        assertFalse(lobby2p.isPlayerAlreadyInLobby(PlayerIndex.PLAYER0));
        lobby2p.addPlayer(PlayerIndex.PLAYER0, "Jack");
        assertTrue(lobby2p.isPlayerAlreadyInLobby(PlayerIndex.PLAYER0));
    }

    @Test
    public void addPlayerTest2Player(){
        lobby2p.setThreePlayersGame(false);

        lobby2p.addPlayer(PlayerIndex.PLAYER0, "Jack");
        assertEquals(lobby2p.getLobbyPlayers().size(), 1);
        try{
            lobby2p.addPlayer(PlayerIndex.PLAYER1, "Jack");
        }
        catch (NameAlreadyTakenException e){
            assertEquals("This name is not available!", e.getMessage());
        }
        assertEquals(lobby2p.getLobbyPlayers().size(), 1);
        lobby2p.addPlayer(PlayerIndex.PLAYER1, "John");
        assertEquals(lobby2p.getLobbyPlayers().size(), 2);
        try{
            lobby2p.addPlayer(PlayerIndex.PLAYER2, "Bob");
        }
        catch(MaxPlayersException e){
            assertEquals("Max number of players reached! Impossible to add other players!", e.getMessage());
        }
    }

    @Test
    public void addPlayerTest3Player(){
        lobby3p.setThreePlayersGame(true);

        lobby3p.addPlayer(PlayerIndex.PLAYER0, "Jack");
        assertEquals(lobby3p.getLobbyPlayers().size(), 1);
        try{
            lobby3p.addPlayer(PlayerIndex.PLAYER2, "Jack");
        }
        catch (NameAlreadyTakenException e){
            assertEquals("This name is not available!", e.getMessage());
        }
        assertEquals(lobby3p.getLobbyPlayers().size(), 1);
        lobby3p.addPlayer(PlayerIndex.PLAYER1, "John");

        //Check exception when is given a PlayerIndex already in lobby
        try{
            lobby3p.addPlayer(PlayerIndex.PLAYER1, "Alfred");
        }
        catch (IllegalArgumentException e){
            assertEquals("Player PLAYER1 is already in the lobby", e.getMessage());
        }

        assertEquals(lobby3p.getLobbyPlayers().size(), 2);
        lobby3p.addPlayer(PlayerIndex.PLAYER2, "Al");
        assertEquals(lobby3p.getLobbyPlayers().size(), 3);
        try{
            lobby3p.addPlayer(PlayerIndex.PLAYER2, "Bob");
        }
        catch(MaxPlayersException e){
            assertEquals("Max number of players reached! Impossible to add other players!", e.getMessage());
        }
    }
}
