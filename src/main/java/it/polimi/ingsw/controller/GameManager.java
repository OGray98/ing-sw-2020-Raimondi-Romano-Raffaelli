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
    private final Map<PlayerIndex, RemoteView> remoteViews;

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

    /**
     * This method communicates with model and set the cards chosen
     * by player god like. Respond with an error message if there
     * are problems
     *
     * @param message input message. Must be corrected
     */
    private void handleGodLikeChoseMessage(GodLikeChoseMessage message) {

        PlayerIndex clientIndex = message.getClient();
        List<String> godNames = message.getGodNames();

        if (!clientIndex.equals(godPhaseManager.getGodLikePlayerIndex())) {
            respondToRemoteView(
                    clientIndex,
                    "You aren't player god like",
                    TypeMessage.NOT_BE_GOD_LIKE
            );
            return;
        }
        if (message.getGodNames().size() != gameInstance.getPlayers().size()) {
            respondToRemoteView(
                    clientIndex,
                    "You must choose " + gameInstance.getPlayers().size() + " cards",
                    TypeMessage.WRONG_NUMBER_CARDS
            );
            return;
        }
        godNames.forEach(name -> godPhaseManager.godLikeChooseCards(name));
        gameInstance.setGodsChosenByGodLike(godPhaseManager.getGodsChosen());
    }

    /**
     * This method set in lobby the players number of this match. Respond with an error message
     * if there are problems
     *
     * @param message input message. Must be corrected
     */
    private void handleIsThreePlayersGameMessage(TypeMatchMessage message) {

        PlayerIndex clientIndex = message.getClient();
        boolean isThreePlayerGame = message.isThreePlayersMatch();

        if (!clientIndex.equals(PlayerIndex.PLAYER0)) {
            respondToRemoteView(
                    clientIndex,
                    "You can't choose the number of players",
                    TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER
            );
            return;
        }

        lobby.setThreePlayersGame(message.isThreePlayersMatch());
    }

    /**
     * Add a player in the lobby. Respond with an error message
     * if there are problems
     *
     * @param message input message. Must be corrected
     */
    private void handleNicknameMessage(NicknameMessage message) {

        PlayerIndex clientIndex = message.getClient();
        String name = message.getNickname();

        if (lobby.isNameAlreadyTaken(name)) {
            respondToRemoteView(
                    clientIndex,
                    "There already is a player named " + name,
                    TypeMessage.NICKNAME
            );
            return;
        }

        if (lobby.isPlayerAlreadyInLobby(clientIndex)) {
            respondToRemoteView(
                    clientIndex,
                    "You already set your name" + name,
                    TypeMessage.ALREADY_SET_NICKNAME
            );
            return;
        }

        lobby.addPlayer(clientIndex, name);

        if (lobby.isFull()) {
            List<PlayerInterface> players = new ArrayList<>(lobby.getLobbyPlayers().size());
            lobby.getLobbyPlayers().forEach(
                    (index, nickname) -> players.add(new Player(nickname, index))
            );
            gameInstance = Game.getInstance(players);
            godPhaseManager = new GodPhaseManager(gameInstance);
        }
    }

    /**
     * Check if the message is sent by the current player
     *
     * @param msg input message. Must be corrected
     * @return true iff message is sent by the current player
     */
    private boolean isMessageSentByCurrentPlayer(Message msg) {
        return msg.getClient().equals(gameInstance.getCurrentPlayerIndex());
    }

    /**
     * Send an error message at the RemoteView of clientIndex
     *
     * @param clientIndex  receiver
     * @param text         message text
     * @param specificType specific type of this error message
     */
    private void respondToRemoteView(PlayerIndex clientIndex, String text, TypeMessage specificType) {
        remoteViews.get(clientIndex).putMessage(
                new ErrorMessage(
                        clientIndex,
                        text,
                        specificType
                )
        );
    }
}
