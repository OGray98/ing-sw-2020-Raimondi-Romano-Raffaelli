package it.polimi.ingsw;

import it.polimi.ingsw.Client.ClientManager;
import it.polimi.ingsw.Client.ClientModel;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.utils.ConnectionPlayerIndex;
import it.polimi.ingsw.utils.CurrentPlayerMessage;
import it.polimi.ingsw.utils.NicknameMessage;
import org.junit.Before;
import org.junit.Test;

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
        assertNull(clientModel.getPlayerNickname());
        clientManager.updateClient(new NicknameMessage(PlayerIndex.PLAYER0, "Jack"));
        assertEquals(clientModel.getPlayerNickname(), "Jack");

        //Testing updateCurrentPlayer
        clientManager.updateClient(new CurrentPlayerMessage(PlayerIndex.PLAYER0));
    }
}
