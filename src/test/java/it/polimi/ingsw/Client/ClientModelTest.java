package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.InvalidPutWorkerException;
import it.polimi.ingsw.message.MoveMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ClientModelTest {

    private ClientModel clientModel;


    @Before
    public void init(){
        clientModel = new ClientModel();
        StubView gui = new StubView(PlayerIndex.PLAYER0, clientModel);
        clientModel.addObserver(gui);
    }

    @Test
    public void testBoardInit(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                assertEquals(0,clientModel.getLevelPosition(new Position(i,j)));
            }
        }
    }

    @Test
    public void testNickname(){
        try{
            clientModel.addNickname(PlayerIndex.PLAYER0, null);
        }catch (NullPointerException e){
            assertEquals("nickname",e.getMessage());
        }
        clientModel.addNickname(PlayerIndex.PLAYER0, "Jack");
        assertTrue(clientModel.nicknameIsPresent("Jack"));
        assertFalse(clientModel.nicknameIsPresent("Pluto"));
        clientModel.addNickname(PlayerIndex.PLAYER1, "Creed");
        assertTrue(clientModel.nicknameIsPresent("Creed"));
    }

    @Test
    public void incrementLevel(){
        try{
            clientModel.incrementLevel(null);
        }catch (NullPointerException e){
            assertEquals("pos",e.getMessage());
        }
        clientModel.incrementLevel(new Position(1,1));
        assertEquals(1,clientModel.getLevelPosition(new Position(1,1)));
    }

    @Test
    public void addDome(){
        try{
            clientModel.addDome(null);
        }catch (NullPointerException e){
            assertEquals("pos",e.getMessage());
        }
        clientModel.addDome(new Position(1,2));
        assertTrue(clientModel.thePositionContainDome(new Position(1,2)));
        assertFalse(clientModel.thePositionContainDome(new Position(4,4)));
    }

    @Test
    public void putWorkerTest(){
        try{
            clientModel.putWorker(PlayerIndex.PLAYER0,null, null);
        }catch (NullPointerException e){
            assertEquals("pos1",e.getMessage());
        }
        try{
            clientModel.putWorker(PlayerIndex.PLAYER0,new Position(1,1), null);
        }catch (NullPointerException e){
            assertEquals("pos2",e.getMessage());
        }
        clientModel.putWorker(PlayerIndex.PLAYER0,new Position(3,3), new Position(3,4));

        try{
            clientModel.putWorker(PlayerIndex.PLAYER0,new Position(2,2), new Position(3,2));
        }catch (InvalidPutWorkerException e){
            assertEquals( "PLAYER0 cannot put a worker in : [2][2], because he already have 2 worker in your board",e.getMessage());
        }
    }

    @Test
    public void godChosenTest(){
        try{
            clientModel.addGodChosenByGodLike(null);
        }catch (NullPointerException e){
            assertEquals("name",e.getMessage());
        }
    }

    @Test
    public void moveWorkerTest(){
        try{
            clientModel.movePlayer(new MoveMessage(PlayerIndex.PLAYER0,null,new Position(2,2)));
        }catch (NullPointerException e){
            assertEquals("pos1",e.getMessage());
        }
        try{
            clientModel.movePlayer(new MoveMessage(PlayerIndex.PLAYER0,new Position(2,2),null));
        }catch (NullPointerException e){
            assertEquals("pos2",e.getMessage());
        }

        try{
            clientModel.movePlayer(new MoveMessage(PlayerIndex.PLAYER0, new Position(2,2),new Position(2,2)));
        }catch (IllegalArgumentException e){
            assertNull(e.getMessage());
        }

        clientModel.putWorker(PlayerIndex.PLAYER0,new Position(3,3), new Position(3,4));
        assertEquals(new Position(3,3),clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).get(0));
        assertEquals(new Position(3,4),clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).get(1));

        clientModel.movePlayer(new MoveMessage(PlayerIndex.PLAYER0,new Position(3,3),new Position(2,3)));
        assertEquals(new Position(2,3),clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).get(1));

    }

    @Test
    public void getSetTest(){
        clientModel.setCurrentState(GameState.PUT_WORKER);
        assertEquals(GameState.PUT_WORKER, clientModel.getCurrentState());
        clientModel.setPlayerNickname("Lock");
        assertEquals("Lock", clientModel.getPlayerNickname());
        clientModel.setPlayerIndex(PlayerIndex.PLAYER0);
        assertEquals(PlayerIndex.PLAYER0, clientModel.getPlayerIndex());
        clientModel.setAmICurrentPlayer(true);
        assertTrue(clientModel.isAmICurrentPlayer());
    }

    @After
    public void delete() {
        clientModel = null;
    }
}