package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.AlreadyPresentRemoteViewOfPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Position;
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

    /*
        TODO SUPER MEGA IMPORTANTE, PASSARE TRA MODEL E REMOTEVIEW TUTTO GAME??
        MOTIVI: LE VIEW HANNO BISOGNO DI SAPERE PIU' O MENO TUTTO, TRA CUI CURRENTSTATE,
        CURRENTPLAYER ECCETERA.
        SE PASSO ALLA REMOTEVIEW TUTTO QUESTO E' INUTILE FARE GLI UPDATE STATE MESSAGE QUI NEL CONTROLLER
        -> IMPLEMENTAZIONE MIGLIORE PERCHE' I CSMBIAENTI DEL MODEL DEVONO ESSERE NOTIFICATI DA LUI ALLA VIEW.
     */

    private final GameLobby lobby;

    private Game gameModel;
    private GodPhaseManager godPhaseManager;
    private final Map<PlayerIndex, RemoteView> remoteViews;
    //TurnManager instance
    private TurnManager turnManager;

    public GameManager() {
        remoteViews = new HashMap<>();
        lobby = new GameLobby();
    }

    //TODO DA TOGLIERE ASSOLUTAMENTE, MESSO SOLO MOMENTANEAMENTE PER VEDERE SE FUNZIONANO I TEST
    public Game getGameModel() {
        return gameModel;
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
            case GODLIKE_CHOOSE_FIRST_PLAYER:
                handleGodLikeChooseFirstPlayerMessage((GodLikeChooseFirstPlayerMessage) message);
                break;
            case PUT_WORKER:
                handlePutWorkerMessage((PutWorkerMessage) message);
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

    /**
     * Add a player in the lobby. Respond with an error message
     * if there are problems
     *
     * @param message input message. Must be corrected
     */
    private void handleNicknameMessage(NicknameMessage message) {

        PlayerIndex clientIndex = message.getClient();
        String name = message.getNickname();

        if (lobby.isFull()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't join the lobby because is already full",
                    TypeMessage.WRONG_GAME_STATE
            );
        }

        if (lobby.isNameAlreadyTaken(name)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "There already is a player named " + name,
                    TypeMessage.NICKNAME
            );
            return;
        }

        if (lobby.isPlayerAlreadyInLobby(clientIndex)) {
            respondErrorToRemoteView(
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
            gameModel = new Game(players);
            godPhaseManager = new GodPhaseManager(gameModel);

            sendNewStateToAll(TypeMessage.GODLIKE_CHOOSE_CARDS);

        }

        respondOkToRemoteView(clientIndex, "Nickname entered correctly", TypeMessage.NICKNAME);
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

        if (lobby.isFull()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't set now the number of players",
                    TypeMessage.WRONG_GAME_STATE
            );
        }

        if (!clientIndex.equals(PlayerIndex.PLAYER0)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't choose the number of players",
                    TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER
            );
            return;
        }

        lobby.setThreePlayersGame(isThreePlayerGame);

        respondOkToRemoteView(clientIndex, "Number of players entered correctly", TypeMessage.IS_THREE_PLAYERS_GAME);

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

        if (isNotCurrentGameState(GameState.GOD_PLAYER_CHOOSE_CARDS)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't choose now the cards to play with",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }

        if (!clientIndex.equals(godPhaseManager.getGodLikePlayerIndex())) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You aren't player god like",
                    TypeMessage.NOT_BE_GOD_LIKE
            );
            return;
        }
        if (message.getGodNames().size() != gameModel.getPlayers().size()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You must choose " + gameModel.getPlayers().size() + " cards",
                    TypeMessage.WRONG_NUMBER_CARDS
            );
            return;
        }
        godNames.forEach(name -> godPhaseManager.godLikeChooseCards(name));
        gameModel.setGodsChosenByGodLike(godPhaseManager.getGodsChosen());
        respondOkToRemoteView(clientIndex, "Cards chosen correctly", TypeMessage.GODLIKE_CHOOSE_CARDS);

        gameModel.setCurrentState(GameState.SELECT_CARD);
        sendNewStateToAll(TypeMessage.SELECT_CARD);
    }


    /**
     * This method communicates with model and set the card of the current player
     * It responds with an error message if the operation is not possible
     *
     * @param message is the input message sent from the current player
     */
    private void handleSelectCardMessage(PlayerSelectGodMessage message) {

        PlayerIndex clientIndex = message.getClient();

        if (isNotCurrentGameState(GameState.SELECT_CARD)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't select now a card",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }

        if (isNotMessageSentByCurrentPlayer(message)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn to choose the god",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }
        //Godlike can't choose the card
        if (clientIndex.equals(godPhaseManager.getGodLikePlayerIndex())) {
            respondErrorToRemoteView(
                    clientIndex,
                    "Godlike player can not choose a card",
                    TypeMessage.ERROR
            );
            return;
        }

        godPhaseManager.playerChooseGod(message.getGodName());
        //possibile problema: quando ultimo giocatore sceglie poi non sarà il turno del godlike. RISOLTO?
        respondOkToRemoteView(clientIndex, "Card selected correctly", TypeMessage.SELECT_CARD);

        if (godPhaseManager.isFinishSelectCardPhase()) {
            gameModel.setCurrentState(GameState.GOD_PLAYER_CHOOSE_FIRST_PLAYER);
            sendNewStateToAll(TypeMessage.GODLIKE_CHOOSE_FIRST_PLAYER);
        }
    }

    /**
     * This method communicates with model and set the first player of the game
     * It responds with an error message if the operation is not possible
     *
     * @param message is input message which contains the index of the first player chosen
     */
    private void handleGodLikeChooseFirstPlayerMessage(GodLikeChooseFirstPlayerMessage message) {

        PlayerIndex clientIndex = message.getClient();

        if (isNotCurrentGameState(GameState.GOD_PLAYER_CHOOSE_FIRST_PLAYER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't select now the first player",
                    TypeMessage.WRONG_GAME_STATE
            );
        }

        if (!clientIndex.equals(godPhaseManager.getGodLikePlayerIndex())) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You aren't player god like",
                    TypeMessage.NOT_BE_GOD_LIKE
            );
            return;
        }
        godPhaseManager.godLikeChooseFirstPlayer(message.getPlayerFirst());
        respondOkToRemoteView(clientIndex, "First player chosen correctly", TypeMessage.GODLIKE_CHOOSE_FIRST_PLAYER);

        gameModel.setCurrentState(GameState.PUT_WORKER);
        sendNewStateToAll(TypeMessage.PUT_WORKER);
    }

    /**
     * This method communicates with model and put the two workers of the current player on the board
     * It responds with an error message if:
     * It is not the turn of the player
     * Player selects occupied cells
     * Player already has workers on the board
     *
     * @param message is the input message of type PutWorkerMessage
     */
    private void handlePutWorkerMessage(PutWorkerMessage message) {

        PlayerIndex clientIndex = message.getClient();

        if (isNotCurrentGameState(GameState.PUT_WORKER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't put worker now",
                    TypeMessage.WRONG_GAME_STATE
            );
        }

        if (isNotMessageSentByCurrentPlayer(message)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn to put the worker",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }
        /*controllo abbastanza inutile che lancia eccezione
        if(gameInstance.getBoard().workerPositions(clientIndex).size() != 0){
            respondToRemoteView(
                    clientIndex,
                    "Workers already on the map",
                    TypeMessage.ERROR
            );
        }*/
        if (message.getPositions().stream().anyMatch(pos -> !godPhaseManager.canPutWorker(pos))) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You selected an invalid position",
                    TypeMessage.CELL_OCCUPIED
            );
            return;
        }

        respondOkToRemoteView(clientIndex, "Workers put correctly", TypeMessage.PUT_WORKER);

        if (godPhaseManager.getPlayersWithWorkerPut() == gameModel.getPlayers().size()) {
            gameModel.setCurrentState(GameState.INITURN);
            sendNewStateToAll(TypeMessage.MOVE);
        }
    }

    private void handleMoveMessage(MoveMessage message) {

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPosition();
        Position movePos = message.getMovePosition();
    }


    /**
     * Check if the message is sent by the current player
     *
     * @param msg input message. Must be corrected
     * @return true iff message is sent by the current player
     */
    private boolean isNotMessageSentByCurrentPlayer(Message msg) {
        return !msg.getClient().equals(gameModel.getCurrentPlayerIndex());
    }


    /**
     * Return true iff gameState is the same as gameModel's GameState
     *
     * @param gameState GameState to confront
     * @return true iff gameState is the same as gameModel's GameState
     */
    private boolean isNotCurrentGameState(GameState gameState) {
        return !gameModel.getCurrentState().equals(gameState);
    }

    /**
     * Send an errorMessage at the RemoteView of clientIndex
     *
     * @param clientIndex  receiver
     * @param text         message text
     * @param specificType specific type of this errorMessage
     */
    private void respondErrorToRemoteView(PlayerIndex clientIndex, String text, TypeMessage specificType) {
        remoteViews.get(clientIndex).putMessage(
                new ErrorMessage(
                        clientIndex,
                        specificType,
                        text
                )
        );
    }

    /**
     * Send an okMessage at the RemoteView of clientIndex
     *
     * @param clientIndex  receiver
     * @param text         message text
     * @param specificType specific type of this okMessage
     */
    private void respondOkToRemoteView(PlayerIndex clientIndex, String text, TypeMessage specificType) {
        remoteViews.get(clientIndex).putMessage(
                new OkMessage(
                        clientIndex,
                        specificType,
                        text
                )
        );
    }

    /**
     * Send an UpdateStateMessage to every RemoteView
     *
     * @param state new game state
     */
    private void sendNewStateToAll(TypeMessage state) {
        remoteViews.forEach(
                (clientIndex, remView) -> remView.putMessage(
                        new UpdateStateMessage(
                                clientIndex,
                                state,
                                "The new game state is " + state
                        )
                )
        );
    }


}
