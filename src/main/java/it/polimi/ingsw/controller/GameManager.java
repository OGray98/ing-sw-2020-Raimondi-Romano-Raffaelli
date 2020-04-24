package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.model.player.PlayerInterface;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * Game manager is the controller's most important class. It manage
 * the message incoming from every RemoteView, and update the model
 * based on these message.
 */
public class GameManager implements Observer<Message> {

    private final GameLobby lobby;
    private Game gameInstance;
    private GodPhaseManager godPhaseManager;
    private List<RemoteView> remoteViews;

    public GameManager() {
        remoteViews = new ArrayList<>();
        lobby = new GameLobby();
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
        if (message.getClient().compareTo(PlayerIndex.values()[godPhaseManager.getGodLikePlayerIndex()]) != 0) {
            //TODO rispondi errore non sei godLike
            return;
        }
        if (message.getGodNames().size() != gameInstance.getPlayers().size()) {
            //TODO rispondi errore
            return;
        }
        message.getGodNames().forEach(name -> godPhaseManager.godLikeChooseCards(name));
        gameInstance.setGodsChosenByGodLike(godPhaseManager.getGodsChosen());
    }

    private boolean isMessageSentByCurrentPlayer(Message msg) {
        return msg.getClient().equals(gameInstance.getCurrentPlayerIndex());
    }

    private void handleIsThreePlayersGameMessage(TypeMatchMessage message) {
        lobby.setThreePlayersGame(message.isThreePlayersMatch());
    }

    private void handleNicknameMessage(NicknameMessage message) {
        String name = message.getNickname();
        //Todo se il nome è già stato preso diglielo
        lobby.addPlayer(name);

        if (lobby.isFull()) {
            List<PlayerInterface> players = new ArrayList<>(lobby.getLobbyPlayers().size());
            for (int i = 0; i < lobby.getLobbyPlayers().size(); i++)
                players.add(new Player(lobby.getLobbyPlayers().get(i), PlayerIndex.values()[i]));
            gameInstance = Game.getInstance(players);
            godPhaseManager = new GodPhaseManager(gameInstance);
        }
    }
}
