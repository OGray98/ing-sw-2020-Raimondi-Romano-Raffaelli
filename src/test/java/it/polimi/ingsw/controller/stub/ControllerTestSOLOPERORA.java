package it.polimi.ingsw.controller.stub;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.exception.AlreadyPresentRemoteViewOfPlayerException;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.stub.StubObservableClientConnection;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTestSOLOPERORA {
    private StubObservableClientConnection obs1;
    private StubObservableClientConnection obs2;
    private StubObservableClientConnection obs3;
    private StubObservableClientConnection obs4;
    private Game game = new Game();
    private final GameManager gameManager = new GameManager(game);
    private RemoteView remoteView1;
    private RemoteView remoteView2;
    private RemoteView remoteView3;


   @Test
    public void isCorr() {

        obs1 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER0, "Pasquale"));
        obs2 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER1, "Tony"));
        obs3 = new StubObservableClientConnection(new NicknameMessage(PlayerIndex.PLAYER2, "PiccoloPietro"));

        game.addObserver(obs1);
        game.addObserver(obs2);
        game.addObserver(obs3);

        remoteView1 = new RemoteView(PlayerIndex.PLAYER0, obs1);
        remoteView2 = new RemoteView(PlayerIndex.PLAYER1, obs2);
        remoteView3 = new RemoteView(PlayerIndex.PLAYER2, obs3);

        remoteView1.addObserver(gameManager);
        remoteView2.addObserver(gameManager);
        remoteView3.addObserver(gameManager);

        try{
            gameManager.addRemoteView(PlayerIndex.PLAYER0,null);
        } catch (NullPointerException e){
            assertEquals("remoteView",e.getMessage());
        }
        gameManager.addRemoteView(PlayerIndex.PLAYER0, remoteView1);
        try{
            gameManager.addRemoteView(PlayerIndex.PLAYER0,remoteView1);
        }catch (AlreadyPresentRemoteViewOfPlayerException e){
            assertEquals("There already is the remote view of PLAYER0",e.getMessage());
        }
        gameManager.addRemoteView(PlayerIndex.PLAYER1, remoteView2);
        gameManager.addRemoteView(PlayerIndex.PLAYER2, remoteView3);

        List<String> names = new ArrayList<>(List.of("Tony", "Pasquale", "PiccoloPietro"));
        obs2.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER1,true));
        ErrorMessage notGodLike = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("You can't choose the number of players",notGodLike.getErrorMessage());
        assertEquals(notGodLike.getSpecificErrorType(),TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER);

        obs1.setMsg(new TypeMatchMessage(PlayerIndex.PLAYER0, true));
        obs1.setMsg(new NicknameMessage(PlayerIndex.PLAYER0, names.get(0)));

        assertEquals(obs1.getMesRemoteToView().size(), 1);
        NicknameMessage nick = (NicknameMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(nick.getNickname(), "Tony");

        try{
            obs2.update(null);
        }catch (NullPointerException e){
            assertEquals("message",e.getMessage());
        }
        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(0)));
        ErrorMessage errorNick = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(errorNick.getErrorMessage(),"There already is a player named " + names.get(0));


        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1, names.get(1)));
        assertEquals(obs2.getMesRemoteToView().size(), 1);
        NicknameMessage nick2 = (NicknameMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(nick2.getNickname(), "Pasquale");

        obs2.setMsg(new NicknameMessage(PlayerIndex.PLAYER1,names.get(2)));
        ErrorMessage errorPlayer = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(errorPlayer.getErrorMessage(),"You already set your name " + names.get(2));


        obs3.setMsg(new NicknameMessage(PlayerIndex.PLAYER2, names.get(2)));

        NicknameMessage nick3 = (NicknameMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(nick3.getNickname(), "PiccoloPietro");

        List<String> godChosen = new ArrayList<>(List.of("Demeter", "Athena", "Atlas"));
        List<String> godChosenOut = new ArrayList<>(List.of("Demeter", "Athena", "Atlas","Pan"));
       //Not god like choose
        obs2.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER1,godChosen));
        ErrorMessage notBeGodLike = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("You aren't player god like",notBeGodLike.getErrorMessage());

        //Cards chosen > number of players
        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0,godChosenOut));
        ErrorMessage outCards = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You must choose 3 cards",outCards.getErrorMessage());

        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0, godChosen));

        GodLikeChoseMessage gods = (GodLikeChoseMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(gods.getGodNames(), godChosen);

        //Check some god phase messages
        //God choose cards

        //Verify if the state chooseFirstPlayer is after the currentState(ChooseGod)
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0,PlayerIndex.PLAYER1));
        ErrorMessage notStateCorrect = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You can't select now the first player",notStateCorrect.getErrorMessage());

        obs3.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2,"Athena"));
        ErrorMessage NotYourTurn = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("Not your turn to choose the god",NotYourTurn.getErrorMessage());
        try{
            obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Apollo"));
        }
        catch(WrongGodNameException e){
            assertEquals("There isn't a god named Apollo", e.getMessage());
        }
        obs2.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER1, "Athena"));
        assertEquals(obs2.getMesRemoteToView().size(), 1);

        PlayerSelectGodMessage god1 = (PlayerSelectGodMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(god1.getGodName(), "Athena");

        obs3.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER2, "Demeter"));


        PlayerSelectGodMessage god2 = (PlayerSelectGodMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(god2.getGodName(), "Demeter");

        //Verify if this state is != PutWorker phase
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1,new Position(0,0),new Position(1,0)));
        ErrorMessage notPutWorkerPhase = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("You can't put worker now",notPutWorkerPhase.getErrorMessage());

        //Verify if only GodLike choose first player
        obs2.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER1,PlayerIndex.PLAYER2));
        ErrorMessage notYouGodLike = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("You aren't player god like",notYouGodLike.getErrorMessage());

        //godlike chooses first player
        obs1.setMsg(new GodLikeChooseFirstPlayerMessage(PlayerIndex.PLAYER0, PlayerIndex.PLAYER2));

        GodLikeChooseFirstPlayerMessage firstP = (GodLikeChooseFirstPlayerMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(firstP.getPlayerFirst(), PlayerIndex.PLAYER2);

        //Test if is not the selectWorkerPhase
        obs3.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER2,new Position(0,0)));
        ErrorMessage notSelectWorkerPhase = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You can't move a worker now!",notSelectWorkerPhase.getErrorMessage());

        //Testing put worker phase
        obs3.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER2, new Position(0,0), new Position(0,1)));

        PutWorkerMessage put1 = (PutWorkerMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(put1.getPositionOne(), new Position(0,0));
        assertEquals(put1.getPositionTwo(), new Position(0,1));

        //if a player wants to put workers in occupied cell it can't do it
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,0)));
        ErrorMessage invalidPosition = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You selected an invalid position",invalidPosition.getErrorMessage());

        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3), new Position(0,4)));
        PutWorkerMessage put2 = (PutWorkerMessage) obs1.getMesRemoteToView().get(0);
        assertEquals(put2.getPositionOne(), new Position(0,3));
        assertEquals(put2.getPositionTwo(), new Position(0,4));

        //a player try to put workers when it is not his turn
        obs1.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER0, new Position(3,3), new Position(3,4)));
        ErrorMessage notTurnWorker = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("Not your turn to put the worker",notTurnWorker.getErrorMessage());

        //last player put workers
        obs2.setMsg(new PutWorkerMessage(PlayerIndex.PLAYER1, new Position(1,3), new Position(1,4)));
        PutWorkerMessage put3 = (PutWorkerMessage) obs2.getMesRemoteToView().get(0);
        assertEquals(put3.getPositionOne(), new Position(1,3));
        assertEquals(put3.getPositionTwo(), new Position(1,4));

        //Check that the new state is INITURN
        UpdateStateMessage initState = (UpdateStateMessage) obs2.getMesRemoteToView().get(1);
        assertEquals(initState.getGameState(), GameState.INITURN);

        //Check that player can't choose card duringINIT TURN
        obs1.setMsg(new PlayerSelectGodMessage(PlayerIndex.PLAYER0,"Athena"));
        ErrorMessage notSelectTurn = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You can't select now a card",notSelectTurn.getErrorMessage());

        /*Initial board:
         *
         * P2    P2    0    P0   P0
         * 0     0     0    P1   P1
         * 0     0     0    0    0
         * 0     0     0    0    0
         * 0     0     0    0    0
         * */

        //Testing move phase
        //can't move in an enemy turn
        obs1.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER0, new Position(0,3)));
        ErrorMessage selectNotTurn = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("Not your turn",selectNotTurn.getErrorMessage());
        obs1.setMsg(new MoveMessage(PlayerIndex.PLAYER0, new Position(0, 3), new Position(1, 2)));
        ErrorMessage notYourMoveTurn = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You can't move a worker now!",notYourMoveTurn.getErrorMessage());

        obs1.setMsg(new GodLikeChoseMessage(PlayerIndex.PLAYER0,godChosen));
        ErrorMessage notChooseGameMoment = (ErrorMessage) obs1.getMesRemoteToView().get(0);
        assertEquals("You can't choose now the cards to play with",notChooseGameMoment.getErrorMessage());

        //can't move if workerPos does not contains player worker
        obs3.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER2, new Position(0,1)));
        ActionMessage select1 = (ActionMessage) obs3.getMesRemoteToView().get(2);
        assertEquals(select1.getWorkerPos(), new Position(0,1));
        //Check that the new state is MOVE
        UpdateStateMessage moveState = (UpdateStateMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(moveState.getGameState(), GameState.MOVE);

        obs2.setMsg(new MoveMessage(PlayerIndex.PLAYER1,new Position(2,3),new Position(2,2)));
        ErrorMessage IsntYourTurnMove = (ErrorMessage)  obs2.getMesRemoteToView().get(0);
        assertEquals("Not your turn",IsntYourTurnMove.getErrorMessage());

        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(4, 4), new Position(1, 1)));
        assertEquals(obs3.getMesRemoteToView().size(),1);

        //player can't move if the movement is not valid
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(2, 2)));
        ErrorMessage invalidMove = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You select an illegal movement",invalidMove.getErrorMessage());

        //Can't move if the workerPosition given is wrong
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2,new Position(2,2),new Position(2,1)));
        ErrorMessage positionNotWorker = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You select a wrong position",positionNotWorker.getErrorMessage());

        //Test if during the move phase player can't build
        obs3.setMsg(new BuildMessage(PlayerIndex.PLAYER2,new Position(0,1)));
        ErrorMessage notBuildPhase = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You can't build now!",notBuildPhase.getErrorMessage());

       //Test if is not end turn phase
       obs3.setMsg(new EndTurnMessage(PlayerIndex.PLAYER2));
       ErrorMessage endTurnNotPhase = (ErrorMessage) obs3.getMesRemoteToView().get(0);
       assertEquals("You cannot end the turn now!",endTurnNotPhase.getErrorMessage());

        //Normal move
        obs3.setMsg(new MoveMessage(PlayerIndex.PLAYER2, new Position(0, 0), new Position(1, 0)));
        MoveMessage move1 = (MoveMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(move1.getWorkerPosition(), new Position(0,0));
        assertEquals(move1.getMovePosition(), new Position(1,0));
        //Check that the new state is BUILD
        UpdateStateMessage buildState = (UpdateStateMessage) obs3.getMesRemoteToView().get(1);
        assertEquals(buildState.getGameState(), GameState.BUILD);

        //Testing build phase
        //an player cannot build in an enemy turn
        obs2.setMsg(new BuildMessage(PlayerIndex.PLAYER1, new Position(2,3)));
        ErrorMessage notYourBuildTurn = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("Not your turn",notYourBuildTurn.getErrorMessage());

        //cannot build in a not valid build position
        obs3.setMsg(new BuildMessage(PlayerIndex.PLAYER2, new Position(0,1)));
        ErrorMessage buildPositionInvalid = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You select an illegal building action",buildPositionInvalid.getErrorMessage());

        //check that player can not build with his other worker
        obs3.setMsg(new BuildMessage(PlayerIndex.PLAYER2, new Position(0,2)));
        assertEquals(obs2.getMesRemoteToView().size(),1);

        //Test if is not power phase
        obs3.setMsg(new UsePowerMessage(PlayerIndex.PLAYER2,new Position(1,2),new Position(1,3)));
        ErrorMessage powerNotPhase = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You cannot use power in this turn phase!",powerNotPhase.getErrorMessage());

        //Normal build
        obs3.setMsg(new BuildMessage(PlayerIndex.PLAYER2, new Position(1,1)));
        BuildMessage build1 = (BuildMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(build1.getBuildPosition(), new Position(1,1));
        //Check that the state is ENDPHASE
        UpdateStateMessage endPhaseState = (UpdateStateMessage) obs3.getMesRemoteToView().get(1);
        assertEquals(endPhaseState.getGameState(), GameState.ENDPHASE);

        //Testing usePower for Demeter
        //a player cannot use power in an enemy turn
        obs2.setMsg(new UsePowerMessage(PlayerIndex.PLAYER1, new Position(1,4), new Position(2,4)));
        ErrorMessage notYourPowerTurn = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("Not your turn!",notYourPowerTurn.getErrorMessage());

        //cannot use power in a not valid power position (Demeter can't use the power on the same cell where she built in the same turn)
        obs3.setMsg(new UsePowerMessage(PlayerIndex.PLAYER2, new Position(1,0), new Position(1,1)));
        ErrorMessage invalidPowerAction = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You select an illegal power action!",invalidPowerAction.getErrorMessage());

        //cannot use power with an invalid worker position
        obs3.setMsg(new UsePowerMessage(PlayerIndex.PLAYER2, new Position(1,0), new Position(1,1)));
        ErrorMessage workerPowerNotChoose = (ErrorMessage) obs3.getMesRemoteToView().get(0);
        assertEquals("You select an illegal power action!",workerPowerNotChoose.getErrorMessage());


        //Normal use of power
        obs3.setMsg(new UsePowerMessage(PlayerIndex.PLAYER2, new Position(1,0), new Position(2,1)));
        BuildPowerMessage power1 = (BuildPowerMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(power1.getBuildPosition(), new Position(2,1));
        assertEquals(power1.getBuildType(), BuildType.LEVEL);

        UpdateStateMessage builPowState = (UpdateStateMessage) obs3.getMesRemoteToView().get(1);
        assertEquals(builPowState.getGameState(), GameState.BUILDPOWER);

        //Testing endturn
        //a player cannot end the turn of other players
        obs2.setMsg(new EndTurnMessage(PlayerIndex.PLAYER1));
        ErrorMessage turnEndNot = (ErrorMessage) obs2.getMesRemoteToView().get(0);
        assertEquals("Not your turn!",turnEndNot.getErrorMessage());

        //Normal endTurn
        obs3.setMsg(new EndTurnMessage(PlayerIndex.PLAYER2));
        UpdateStateMessage init = (UpdateStateMessage) obs3.getMesRemoteToView().get(0);
        assertEquals(init.getGameState(), GameState.INITURN);

        //after the end of the turn the next player is able to play
        obs1.setMsg(new SelectWorkerMessage(PlayerIndex.PLAYER0, new Position(0,4)));
        ActionMessage select2 = (ActionMessage) obs1.getMesRemoteToView().get(2);
        assertEquals(select2.getWorkerPos(), new Position(0,4));

    //TODO: rimangono da fare alcuni test di casi particolari(vittorie, sconfitte)


    }

}
