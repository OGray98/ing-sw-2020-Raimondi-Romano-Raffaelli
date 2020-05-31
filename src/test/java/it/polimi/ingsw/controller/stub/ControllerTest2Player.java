package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.stub.StubObservableClientConnection;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ControllerTest2Player {
    private StubObservableClientConnection obs1;
    private StubObservableClientConnection obs2;
    private Game game = new Game();
    private final GameManager gameManager = new GameManager(game);
    private RemoteView remoteView1;
    private RemoteView remoteView2;

    @Test
    public void simulation(){

        obs1 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER0,"Creed"));
        obs2 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER1,"Jack"));

        remoteView1 = new RemoteView(PlayerIndex.PLAYER0,obs1);
        remoteView2 = new RemoteView(PlayerIndex.PLAYER1,obs2);

        remoteView1.addObserver(gameManager);
        remoteView2.addObserver(gameManager);

        gameManager.addRemoteView(PlayerIndex.PLAYER0,remoteView1);



        List<String> names = new ArrayList<>(List.of("Creed", "Jack"));
        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0,false));
        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0,names.get(0)));
        NicknameMessage namePlayer0 = (NicknameMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("Creed",namePlayer0.getNickname());

        gameManager.addRemoteView(PlayerIndex.PLAYER1,remoteView2);
        obs2.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER1,true));
        ErrorMessage notGodLike = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("You can't choose the number of players",notGodLike.getErrorMessage());
        assertEquals(notGodLike.getSpecificErrorType(),TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER);
        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));
        NicknameMessage namePlayer1 = (NicknameMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("Jack",namePlayer1.getNickname());

        List<String> godChosen = new ArrayList<>(List.of("Demeter","Atlas"));
        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0,godChosen));
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1,"Atlas"));
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0,PlayerIndex.PLAYER1));
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1,new Position(1,3),new Position(1,2)));
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0,new Position(0,0),new Position(0,4)));

        //Game start

        //obs2.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER1,new Position(1,3)));
        obs2.setMsg(new MoveMessage(PlayerIndex.PLAYER1,new Position(1,3),new Position(0,3)));
        obs2.setMsg(new UsePowerMessage(PlayerIndex.PLAYER1,new Position(0,3),new Position(1,4)));
        BuildPowerMessage atlasBuild = (BuildPowerMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(atlasBuild.getBuildType(), BuildType.DOME);
        obs2.setMsg(new EndTurnMessage(PlayerIndex.PLAYER1));

        //obs1.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER0,new Position(0,0)));
        obs1.setMsg(new MoveMessage(PlayerIndex.PLAYER0,new Position(0,0),new Position(1,0)));
        obs1.setMsg(new BuildMessage(PlayerIndex.PLAYER0,new Position(0,1)));
        obs1.setMsg(new EndTurnMessage(PlayerIndex.PLAYER0));

        //obs2.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER1,new Position(0,3)));
        obs2.setMsg(new MoveMessage(PlayerIndex.PLAYER1,new Position(0,3),new Position(1,3)));
        obs2.setMsg(new UsePowerMessage(PlayerIndex.PLAYER1,new Position(1,3),new Position(0,3)));
        obs2.setMsg(new EndTurnMessage(PlayerIndex.PLAYER1));

        //obs1.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER0,new Position(1,0)));
        obs1.setMsg(new MoveMessage(PlayerIndex.PLAYER0,new Position(1,0),new Position(0,0)));
        obs1.setMsg(new BuildMessage(PlayerIndex.PLAYER0,new Position(0,1)));
        obs1.setMsg(new EndTurnMessage(PlayerIndex.PLAYER0));

        //obs2.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER1,new Position(1,2)));
        obs2.setMsg(new MoveMessage(PlayerIndex.PLAYER1,new Position(1,2),new Position(1,1)));
        obs2.setMsg(new UsePowerMessage(PlayerIndex.PLAYER1,new Position(1,1),new Position(1,0)));
        obs2.setMsg(new EndTurnMessage(PlayerIndex.PLAYER1));

        UpdateStateMessage stateFinal = (UpdateStateMessage) obs1.getMesRemoteToView().get(1);
        assertEquals(stateFinal.getGameState(),GameState.MOVE);

        LoserMessage okLoser0 = (LoserMessage) obs1.getMesRemoteToView().get(16);
        assertEquals(okLoser0.getType(), TypeMessage.LOSER);

        OkMessage okLoserPlayer0 = (OkMessage) obs1.getMesRemoteToView().get(14);
        assertEquals(okLoserPlayer0.getErrorMessage(),"You have lost!");

        UpdateStateMessage end2 = (UpdateStateMessage) obs2.getMesRemoteToView().get(7);
        assertEquals(end2.getGameState(),GameState.MATCH_ENDED);

        OkMessage end1 = (OkMessage) obs2.getMesRemoteToView().get(6);
        assertEquals(end1.getErrorMessage(),"You win!");

        obs1 = null;
        obs2 = null;
        remoteView2 = null;
        remoteView1 = null;
        game = null;

        /*
        p0  2   0   D   p0
        D   p1  0   p1  D
        0   0   0   0   0
        0   0   0   0   0
        0   0   0   0   0
         */






    }

}
