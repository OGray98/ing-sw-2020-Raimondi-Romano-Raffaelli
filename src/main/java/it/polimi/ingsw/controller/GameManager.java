package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.AlreadyPresentRemoteViewOfPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game manager is the controller's most important class. It manage
 * the message incoming from every RemoteView, and update the model
 * based on these message.
 */
public class GameManager implements Observer<Message> {

    private final GameLobby lobby;
    private Game gameInstance;
    private GodPhaseManager godPhaseManager;
    private Map<PlayerIndex, RemoteView> remoteViews;

    public GameManager() {
        remoteViews = new HashMap<>();
        lobby = new GameLobby();
    }

    /**
     * Add a RemoteView associated with PlayerIndex of client
     *
     * @param index      PlayerIndex of Client
     * @param remoteView Associated RemoteView
     * @throws NullPointerException                      if remoteView is null
     * @throws AlreadyPresentRemoteViewOfPlayerException if there already is a RemoteView associated with index.
     */
    public void addRemoteView(PlayerIndex index, RemoteView remoteView) throws NullPointerException, AlreadyPresentRemoteViewOfPlayerException {
        if (remoteView == null)
            throw new NullPointerException("remoteView");
        if (remoteViews.containsKey(index))
            throw new AlreadyPresentRemoteViewOfPlayerException(index);
        remoteViews.put(index, remoteView);
    }

    @Override
    public void update(Message message) throws NullPointerException {
        if (message == null)
            throw new NullPointerException("message");
        switch (message.getType()) {
            case NICKNAME:
                handleNicknameMessage((NicknameMessage) message);
                break;
            case IS_THREE_PLAYERS_GAME:
                handleIsThreePlayersGameMessage((TypeMatchMessage) message);
                break;
            case GODLIKE_CHOOSE_CARDS:
                handleGodLikeChoseMessage((GodLikeChoseMessage) message);
                break;
            case SELECT_CARD:
                handleSelectCardMessage((PlayerSelectGodMessage) message);
                break;
            case MOVE:
                break;
            case USE_POWER:
                break;
            case BUILD:
                break;
            default:
                //Error message
                break;
        }
    }

    private void handleSelectCardMessage(PlayerSelectGodMessage message) {
        if (!isMessageSentByCurrentPlayer(message)) {
            //TODO rispondi errore
            return;
        }
        godPhaseManager.playerChooseGod(message.getGodName());
    }

    private void handleGodLikeChoseMessage(GodLikeChoseMessage message) {

        PlayerIndex clientIndex = message.getClient();
        List<String> godNames = message.getGodNames();

        if (!clientIndex.equals(godPhaseManager.getGodLikePlayerIndex())) {
            remoteViews.get(clientIndex).putMessage(
                    new ErrorMessage(
                            clientIndex,
                            "You aren't player god like",
                            TypeMessage.NOT_BE_GOD_LIKE
                    )
            );
            return;
        }
        if (message.getGodNames().size() != gameInstance.getPlayers().size()) {
            remoteViews.get(clientIndex).putMessage(
                    new ErrorMessage(
                            clientIndex,
                            "You must choose " + gameInstance.getPlayers().size() + " cards",
                            TypeMessage.WRONG_NUMBER_CARDS
                    )
            );
            return;
        }
        godNames.forEach(name -> godPhaseManager.godLikeChooseCards(name));
        gameInstance.setGodsChosenByGodLike(godPhaseManager.getGodsChosen());
    }

    private void handleIsThreePlayersGameMessage(TypeMatchMessage message) {

        PlayerIndex clientIndex = message.getClient();
        boolean isThreePlayerGame = message.isThreePlayersMatch();

        if (!clientIndex.equals(PlayerIndex.PLAYER0)) {
            remoteViews.get(clientIndex).putMessage(
                    new ErrorMessage(
                            clientIndex,
                            "You can't choose the number of players",
                            TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER
                    )
            );
            return;
        }

        lobby.setThreePlayersGame(message.isThreePlayersMatch());
    }

    private void handleNicknameMessage(NicknameMessage message) {

        PlayerIndex clientIndex = message.getClient();
        String name = message.getNickname();

        if (lobby.isNameAlreadyTaken(name)) {
            remoteViews.get(clientIndex).putMessage(
                    new ErrorMessage(
                            clientIndex,
                            "There already is a player named " + name,
                            TypeMessage.NICKNAME
                    )
            );
            return;
        }

        /*
        if(lobby.isAlreadyPresent(clientIndex)){
            remoteViews.get(clientIndex).putMessage(
                    new ErrorMessage(
                            message.getClient(),
                            "You already set your name" + name,
                            TypeMessage.ALREADY_SET_NICKNAME
                    )
            );
            return;
        }
        */

        lobby.addPlayer(name);

        if (lobby.isFull()) {
            List<PlayerInterface> players = new ArrayList<>(lobby.getLobbyPlayers().size());
            for (int i = 0; i < lobby.getLobbyPlayers().size(); i++)
                players.add(new Player(lobby.getLobbyPlayers().get(i), PlayerIndex.values()[i]));
            gameInstance = Game.getInstance(players);
            godPhaseManager = new GodPhaseManager(gameInstance);
        }
    }

    private boolean isMessageSentByCurrentPlayer(Message msg) {
        return msg.getClient().equals(gameInstance.getCurrentPlayerIndex());
    }
}
