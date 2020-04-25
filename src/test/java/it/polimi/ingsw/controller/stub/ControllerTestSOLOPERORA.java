package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.stub.StubObservableMessageReceiver;
import it.polimi.ingsw.utils.NicknameMessage;
import it.polimi.ingsw.utils.TypeMatchMessage;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ControllerTestSOLOPERORA {
    private final GameManager gameManager = new GameManager();
    private StubObservableMessageReceiver obs1;
    private StubObservableMessageReceiver obs2;
    private StubObservableMessageReceiver obs3;
    private Game gameInstance;
    private RemoteView remoteView1;
    private RemoteView remoteView2;
    private RemoteView remoteView3;

    @Test
    public void isCorr() {
        obs1 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER0, "Pasquale"));
        obs2 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER1, "Tony"));
        obs3 = new StubObservableMessageReceiver(new NicknameMessage(PlayerIndex.PLAYER2, "PiccoloPietro"));
        remoteView1 = new RemoteView(PlayerIndex.PLAYER0, obs1);
        remoteView2 = new RemoteView(PlayerIndex.PLAYER1, obs2);
        remoteView3 = new RemoteView(PlayerIndex.PLAYER2, obs3);
        remoteView1.addObserver(gameManager);
        remoteView2.addObserver(gameManager);
        remoteView3.addObserver(gameManager);
        gameManager.addRemoteView(PlayerIndex.PLAYER0, remoteView1);
        gameManager.addRemoteView(PlayerIndex.PLAYER1, remoteView2);
        gameManager.addRemoteView(PlayerIndex.PLAYER2, remoteView3);

        List<String> names = new ArrayList<>(List.of("Tony", "Pasquale", "PiccoloPietro"));

        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0, names.get(0)));
        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0, true));
        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));
        obs3.setMsg(new NicknameMessage(PlayerIndex.PLAYER2, names.get(2)));

        gameInstance = Game.getInstance();
        for (int i = 0; i < 3; i++)
            assertEquals(names.get(i), gameInstance.getPlayers().get(i).getNickname());

    }
}
