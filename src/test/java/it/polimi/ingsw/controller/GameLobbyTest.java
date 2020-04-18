package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.GameLobby;
import it.polimi.ingsw.exception.MaxPlayersException;
import it.polimi.ingsw.exception.NameAlreadyTakenException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameLobbyTest {

    private static GameLobby lobby2p;
    private static GameLobby lobby3p;

    @Before
    public void init(){
        lobby2p = new GameLobby(false);
        lobby3p = new GameLobby(true);
    }

    @Test
    public void addPlayerTest2Player(){
        lobby2p.addPlayer("Jack");
        assertEquals(lobby2p.getLobbyPlayers().size(), 1);
        try{
            lobby2p.addPlayer("Jack");
        }
        catch (NameAlreadyTakenException e){
            assertEquals("This name is not available!", e.getMessage());
        }
        assertEquals(lobby2p.getLobbyPlayers().size(), 1);
        lobby2p.addPlayer("John");
        assertEquals(lobby2p.getLobbyPlayers().size(), 2);
        try{
            lobby2p.addPlayer("Bob");
        }
        catch(MaxPlayersException e){
            assertEquals("Max number of players reached! Impossible to add other players!", e.getMessage());
        }
    }

    @Test
    public void addPlayerTest3Player(){
        lobby3p.addPlayer("Jack");
        assertEquals(lobby3p.getLobbyPlayers().size(), 1);
        try{
            lobby3p.addPlayer("Jack");
        }
        catch (NameAlreadyTakenException e){
            assertEquals("This name is not available!", e.getMessage());
        }
        assertEquals(lobby3p.getLobbyPlayers().size(), 1);
        lobby3p.addPlayer("John");
        assertEquals(lobby3p.getLobbyPlayers().size(), 2);
        lobby3p.addPlayer("Al");
        assertEquals(lobby3p.getLobbyPlayers().size(), 3);
        try{
            lobby3p.addPlayer("Bob");
        }
        catch(MaxPlayersException e){
            assertEquals("Max number of players reached! Impossible to add other players!", e.getMessage());
        }
    }
}
