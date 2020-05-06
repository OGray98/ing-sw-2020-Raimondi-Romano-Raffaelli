package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.ClientManager;
import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClientManagerTest {

    private static Client client;
    private static ClientManager clientManager;
    private static ClientModel clientModel;

    @Before
    public void init(){
        client = new Client("127.0.0.1",12345);
        clientModel = new ClientModel();
        clientManager = new ClientManager(client, clientModel);
    }

    @Test
    public void updateClientTest(){

        //Testing updateIndex()
        assertNull(clientModel.getPlayerIndex());
        clientManager.updateClient(new ConnectionPlayerIndex(PlayerIndex.PLAYER0));
        assertEquals(clientModel.getPlayerIndex(), PlayerIndex.PLAYER0);

        //Testing updateNickname()
        clientManager.updateClient(new NicknameMessage(PlayerIndex.PLAYER1, "Rock"));
        assertEquals(clientModel.getNicknames().size(), 1);
        assertNull(clientModel.getPlayerNickname());
        clientManager.updateClient(new NicknameMessage(PlayerIndex.PLAYER0, "Jack"));
        assertEquals(clientModel.getNicknames().size(), 2);
        assertEquals(clientModel.getPlayerNickname(), "Jack");

        //Testing updateCurrentPlayer()
        clientManager.updateClient(new CurrentPlayerMessage(PlayerIndex.PLAYER1));
        assertFalse(clientModel.isAmICurrentPlayer());
        clientManager.updateClient(new CurrentPlayerMessage(PlayerIndex.PLAYER0));
        assertTrue(clientModel.isAmICurrentPlayer());

        //Testing updateState()
        clientManager.updateClient(new UpdateStateMessage(PlayerIndex.PLAYER0, GameState.MOVE));
        assertEquals(clientModel.getCurrentState(), GameState.MOVE);

        //Testing updateGodCards()
        List<String> godnames = new ArrayList<>();
        godnames.add("Apollo");
        godnames.add("Demeter");
        clientManager.updateClient(new GodLikeChoseMessage(PlayerIndex.PLAYER0, godnames));
        assertEquals(clientModel.getChosenGods().size(),2);

        //Testing updateSelectedCard()
        clientManager.updateCurrentPlayer(new CurrentPlayerMessage(PlayerIndex.PLAYER1));
        clientManager.updateClient(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Demeter"));
        assertNull(clientModel.getClientGod());
        clientManager.updateCurrentPlayer(new CurrentPlayerMessage(PlayerIndex.PLAYER0));
        clientManager.updateClient(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Demeter"));
        assertEquals(clientModel.getClientGod(), "Demeter");

        //Testing updatePutWorkerMessage()
        clientManager.updateClient(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,0), new Position(0,1)));
        assertEquals(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).size(),2);
        assertTrue(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).contains(new Position(0,0)));
        assertTrue(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).contains(new Position(0,1)));

        //Testing updateMoveMessage()
        clientManager.updateClient(new MoveMessage(PlayerIndex.PLAYER0, new Position(0,0), new Position(1,0)));
        assertEquals(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).size(),2);
        assertTrue(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).contains(new Position(1,0)));
        assertTrue(clientModel.getPlayerIndexPosition(PlayerIndex.PLAYER0).contains(new Position(0,1)));

        //Testing updateBuildMessage()
        clientManager.updateClient(new BuildMessage(PlayerIndex.PLAYER0, new Position(1,1)));
        assertEquals(clientModel.getLevelPosition(new Position(1,1)), 1);

        //Testing updateBuildPowerMessage()
        assertEquals(clientModel.getDomesPositions().size(), 0);
        clientManager.updateClient(new BuildPowerMessage(PlayerIndex.PLAYER0, new Position(2,0), BuildType.DOME));
        assertEquals(clientModel.getDomesPositions().size(), 1);
        assertTrue(clientModel.getDomesPositions().contains(new Position(2,0)));
        clientManager.updateClient(new BuildPowerMessage(PlayerIndex.PLAYER0, new Position(2,1), BuildType.LEVEL));
        assertEquals(clientModel.getLevelPosition(new Position(2,1)), 1);

        //Testing updateAction()
        List<Position> movePos = new ArrayList<>();
        movePos.add(new Position(0,0));
        movePos.add(new Position(1,1));
        clientManager.updateClient(new ActionMessage(PlayerIndex.PLAYER0, new Position(1,0), movePos, ActionType.MOVE));
        assertEquals(clientModel.getActionPositions(ActionType.MOVE).size(), 2);
        assertEquals(clientModel.getActionPositions(ActionType.POWER).size(), 0);
        clientManager.updateClient(new ActionMessage(PlayerIndex.PLAYER0, new Position(1,0), movePos, ActionType.POWER));
        assertEquals(clientModel.getActionPositions(ActionType.MOVE).size(), 2);
        assertEquals(clientModel.getActionPositions(ActionType.POWER).size(), 2);
    }
}
