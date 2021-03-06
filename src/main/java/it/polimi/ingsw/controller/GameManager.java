package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.AlreadyPresentRemoteViewOfPlayerException;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.RemoteView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game manager is the controller's most important class. It manages
 * the message incoming from every RemoteView, and update the model
 * based on these message.
 */
public class GameManager implements Observer<MessageToServer>, ControllableByClientMessage {

    private final GameLobby lobby;
    private boolean isThreePlayersGameDecided = false;

    private final Game gameModel;
    private GodPhaseManager godPhaseManager;
    private final Map<PlayerIndex, RemoteView> remoteViews;
    //TurnManager instance
    private TurnManager turnManager;

    public GameManager(Game game) {
        remoteViews = new HashMap<>();
        lobby = new GameLobby();
        gameModel = game;
    }

    public Board getBoard() {
        return gameModel.getBoard();
    }

    public int getPlayerNum(){return lobby.getNumberOfPlayerInLobby();}


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
        remoteView.putMessage(new ConnectionPlayerIndexMessage(index));
        gameModel.addObserver(remoteViews.get(index));
    }

    /**
     * Remove a RemoteView which is disconnected, and destroy relation between this RemoteView and
     * gameModel
     *
     * @param index PlayerIndex of RemoteView which is being deleted
     */
    public void deleteRemoteView(PlayerIndex index) {
        if (remoteViews.containsKey(index)) {
            gameModel.removeObserver(remoteViews.get(index));
            remoteViews.get(index).disconnect();
            remoteViews.remove(index);
        }
    }

    @Override
    public void update(MessageToServer message) throws NullPointerException {
        if (message == null) throw new NullPointerException("message");
        System.out.println("Message " + message.getType() + " receive from " + message.getClient());
        message.execute(this);
    }

    /**
     * Add a player in the lobby. Respond with an error message
     * if there are problems
     *
     * @param message input message. Must be corrected
     */
    @Override
    public void handleNicknameMessage(NicknameMessage message) {

        PlayerIndex clientIndex = message.getClient();
        String name = message.getNickname();

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
                    "You already set your name " + name,
                    TypeMessage.ALREADY_SET_NICKNAME
            );
            return;
        }

        lobby.addPlayer(clientIndex, name);

        if ((isThreePlayersGameDecided || clientIndex==PlayerIndex.PLAYER0) && lobby.isFull()) {
            for(Map.Entry<PlayerIndex, String> entry : lobby.getLobbyPlayers().entrySet()){
                gameModel.addPlayer(entry.getKey(), entry.getValue());
            }
            godPhaseManager = new GodPhaseManager(gameModel);
            gameModel.setCurrentState(GameState.GOD_PLAYER_CHOOSE_CARDS);
        }
    }

    /**
     * This method set in lobby the players number of this match. Respond with an error message
     * if there are problems
     *
     * @param message input message. Must be corrected
     */
    @Override
    public void handleIsThreePlayersGameMessage(TypeMatchMessage message) {

        PlayerIndex clientIndex = message.getClient();
        boolean isThreePlayerGame = message.isThreePlayersMatch();

        if (!clientIndex.equals(PlayerIndex.PLAYER0)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't choose the number of players",
                    TypeMessage.CANT_CHOOSE_PLAYERS_NUMBER
            );
            return;
        }

        lobby.setThreePlayersGameDecided(true);
        lobby.setThreePlayersGame(isThreePlayerGame);
        if(!isThreePlayerGame && remoteViews.size() > 2 && lobby.getLobbyPlayers().size() == 2){
            deleteRemoteView(PlayerIndex.PLAYER2);
            lobby.removeFromLobby(PlayerIndex.PLAYER2);
        }

        this.isThreePlayersGameDecided = true;
        gameModel.setNumPlayer(isThreePlayerGame);
    }

    /**
     * This method communicates with model and set the cards chosen
     * by player god like. Respond with an error message if there
     * are problems
     *
     * @param message input message. Must be corrected
     */
    @Override
    public void handleGodLikeChoseMessage(GodLikeChoseMessage message) {

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
        if (message.getGodNames().size() != gameModel.getNumPlayer()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You must choose " + gameModel.getNumPlayer() + " cards",
                    TypeMessage.WRONG_NUMBER_CARDS
            );
            return;
        }
        godNames.forEach(name -> godPhaseManager.godLikeChooseCards(name));
        gameModel.setGodsChosenByGodLike(godPhaseManager.getGodsChosen());

        gameModel.setCurrentState(GameState.SELECT_CARD);
    }


    /**
     * This method communicates with model and set the card of the current player
     * It responds with an error message if the operation is not possible
     *
     * @param message is the input message sent from the current player
     */
    @Override
    public void handleSelectCardMessage(PlayerSelectGodMessage message) {

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

        godPhaseManager.playerChooseGod(message.getGodName());

        if (godPhaseManager.isFinishSelectCardPhase())
            gameModel.setCurrentState(GameState.GOD_PLAYER_CHOOSE_FIRST_PLAYER);
    }

    /**
     * This method communicates with model and set the first player of the game
     * It responds with an error message if the operation is not possible
     *
     * @param message is input message which contains the index of the first player chosen
     */
    @Override
    public void handleGodLikeChooseFirstPlayerMessage(GodLikeChooseFirstPlayerMessage message) {

        PlayerIndex clientIndex = message.getClient();

        if (isNotCurrentGameState(GameState.GOD_PLAYER_CHOOSE_FIRST_PLAYER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't select now the first player",
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
        godPhaseManager.godLikeChooseFirstPlayer(message.getPlayerFirst());

        gameModel.setCurrentState(GameState.PUT_WORKER);
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
    @Override
    public void handlePutWorkerMessage(PutWorkerMessage message) {

        PlayerIndex clientIndex = message.getClient();

        if (isNotCurrentGameState(GameState.PUT_WORKER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't put worker now",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }

        if (isNotMessageSentByCurrentPlayer(message)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn to put the worker",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }

        if (message.getPositions().stream().anyMatch(pos -> !godPhaseManager.canPutWorker(pos))) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You selected an invalid position",
                    TypeMessage.CELL_OCCUPIED
            );
            return;
        }

        godPhaseManager.puttingWorkerInBoard(message.getPositionOne(), message.getPositionTwo());

        if (godPhaseManager.getPlayersWithWorkerPut() == gameModel.getNumPlayer()) {
            gameModel.setCurrentState(GameState.MOVE);
            //Create an instance of TurnManager and start the first turn
            this.turnManager = new TurnManager(this.gameModel);
            this.turnManager.startTurn();
        }
    }

    /**
     * This method is used to move a worker on the board
     * It responds with error if:
     * It's not the right state of the game
     * It's not the turn of the player
     * The player has selected a wrong worker cell
     * The player has selected an illegal move
     */
    @Override
    public void handleMoveMessage(MoveMessage message) {

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPosition();
        Position movePos = message.getMovePosition();

        //Move allowed only in states MOVE and INITPOWER
        if (isNotCurrentGameState(GameState.MOVE) && isNotCurrentGameState(GameState.INITPOWER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't move a worker now!",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }
        if(isNotMessageSentByCurrentPlayer(message)){
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }
        //Send an error if the position given is not a worker of current player
        if(!gameModel.getBoard().workerPositions(clientIndex).contains(workerPos)){
            respondErrorToRemoteView(
                    clientIndex,
                    "You select a wrong position",
                    TypeMessage.ERROR
            );
            return;
        }
        //Send an error if the movement is not possible
        if(!turnManager.isValidMovement(workerPos, movePos)){
            respondErrorToRemoteView(
                    clientIndex,
                    "You select an illegal movement",
                    TypeMessage.ERROR
            );
            return;
        }

        this.turnManager.moveWorker(workerPos, movePos);
        //Set state
        gameModel.setCurrentState(GameState.BUILD);

        //Check if player has lost with the movement
        if(!gameModel.canPlayerBuildWithSelectedWorker()){
            removeLoser(clientIndex);
        }

        //Check if player is the winner
        if (turnManager.hasWonWithMovement()) {
            gameModel.setCurrentState(GameState.MATCH_ENDED);
            respondMessageToAll(
                    gameModel.getCurrentPlayerNick() + " has won the game!",
                    TypeMessage.WINNER
            );
        }
    }

    /**
     * This method is used to build on the board
     * It responds with error if:
     * It's not the right state of the game
     * It's not the turn of the player
     * The player has selected an illegal build
     */
    @Override
    public void handleBuildMessage(BuildMessage message) {

        PlayerIndex clientIndex = message.getClient();
        Position buildPos = message.getBuildPosition();

        //Move allowed only in states BUILD and SECOND_MOVE
        if (isNotCurrentGameState(GameState.BUILD) && isNotCurrentGameState(GameState.SECOND_MOVE)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't build now!",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }
        if (isNotMessageSentByCurrentPlayer(message)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }

        //Send an error if the build is not possible
        if (!turnManager.isValidBuilding(buildPos)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You select an illegal building action",
                    TypeMessage.ERROR
            );
            return;
        }

        this.turnManager.buildWorker(buildPos);

        //Set state
        gameModel.setCurrentState(GameState.ENDPHASE);

    }

    /**
     * Method used to use a god power
     * It responds with error if:
     * It is the wrong game state
     * It is not the turn of the player
     * Player selects an invalid power use
     * Player did not select a valid position of the worker
     */
    @Override
    public void handleUsePowerMessage(UsePowerMessage message) {

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPosition();
        Position powerPos = message.getPowerPosition();

        //Check that the player can use the power in this state
        if (isNotCurrentGameState(gameModel.getCurrentPlayerPowerState())) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You cannot use power in this turn phase!",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }
        if(isNotMessageSentByCurrentPlayer(message)){
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn!",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }
        //Check if player can use the power
        if(!turnManager.isValidUseOfPower(workerPos, powerPos)){
            respondErrorToRemoteView(
                    clientIndex,
                    "You select an illegal power action!",
                    TypeMessage.ERROR
            );
            return;
        }

        this.turnManager.usePowerWorker(workerPos, powerPos);

        //Set the new state, it depends on the god of current player
        gameModel.setCurrentState(gameModel.getCurrentPlayerNextState());

        //Check if the power made a player the winner
        if(gameModel.getCurrentPlayerGodName().equals("Apollo") ||
                gameModel.getCurrentPlayerGodName().equals("Artemis") ||
                gameModel.getCurrentPlayerGodName().equals("Athena") ||
                gameModel.getCurrentPlayerGodName().equals("Minotaur") ||
                gameModel.getCurrentPlayerGodName().equals("Pan") ||
                gameModel.getCurrentPlayerGodName().equals("Triton")){

            if(this.turnManager.hasWonWithMovement()){
                gameModel.setCurrentState(GameState.MATCH_ENDED);
                respondMessageToAll(
                        gameModel.getCurrentPlayerNick() + " has won the game!",
                        TypeMessage.WINNER
                );
            }
        }
    }

    /**
     * Method used to end the turn of the current player
     * It start the turn of the next player and it change the state of th game
     */
    @Override
    public void handleEndTurnMessage(EndTurnMessage message) {

        PlayerIndex clientIndex = message.getClient();

        //only in ENDPHASE and BUILDPOWER state is allowed to end the current turn
        if (isNotCurrentGameState(GameState.ENDPHASE) && isNotCurrentGameState(GameState.BUILDPOWER)) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You cannot end the turn now!",
                    TypeMessage.ERROR
            );
            return;
        }
        //only current player can end his turn
        if(isNotMessageSentByCurrentPlayer(message)){
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn!",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }

        //setup the new turn
        this.turnManager.endTurn();
        this.turnManager.startTurn();
        gameModel.setCurrentState(GameState.MOVE);

        //If current player has been blocked, he loses
        if (!this.turnManager.canCurrentPlayerMoveAWorker()) {
            removeLoser(gameModel.getCurrentPlayerIndex());
        }
    }

    @Override
    public void handleCloseConnectionMessage(CloseConnectionMessage message) {
        //if (gameModel.getCurrentState() != GameState.START_GAME)
            gameModel.delete(message.getClient());
            lobby.removeFromLobby(message.getClient());
    }

    /**
     * Method called when a player has lost
     * It remove his workers and start the turn of the next player
     * If the remains only one player he wins the game
     */
    private void removeLoser(PlayerIndex loserIndex) {
        //remove loser player
        gameModel.removeCurrentPlayer();

        //notify loser payer
        if(gameModel.getNumGamingPlayer() > 1){
            respondMessageToAll(
                    gameModel.getNickname(loserIndex) + " has lost!",
                    TypeMessage.LOSER
            );
        }

        //if only 1 player remains he wins the game!
        if(gameModel.getNumGamingPlayer() == 1){
            //Set state
            gameModel.endTurn();
            gameModel.setCurrentState(GameState.MATCH_ENDED);
            respondMessageToAll(
                    gameModel.getNickname(gameModel.getCurrentPlayerIndex()) + " has won the game!",
                    TypeMessage.WINNER
            );
        }
        else {
            //Set state and start new turn of other player
            gameModel.setCurrentState(GameState.MOVE);
            this.turnManager.endTurn();
            this.turnManager.startTurn();
        }

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
        for(PlayerIndex client : remoteViews.keySet()) {
            if (client.equals(clientIndex)) {
                remoteViews.get(client).putMessage(
                        new ErrorMessage(
                                clientIndex,
                                specificType,
                                text
                        )
                );
            }
        }
    }

    /**
     * Send a message to all the players connected
     *
     * @param text          message text
     * @param specificType  type of Message
     */
    private void respondMessageToAll(String text, TypeMessage specificType){
        for(PlayerIndex client : remoteViews.keySet()){
            remoteViews.get(client).putMessage(
                    new OkMessage(
                            client,
                            specificType,
                            text
                    )
            );
        }
    }


}
