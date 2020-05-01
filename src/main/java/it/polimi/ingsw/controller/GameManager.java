package it.polimi.ingsw.controller;

import it.polimi.ingsw.exception.AlreadyPresentRemoteViewOfPlayerException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.deck.Deck;
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

    private Game gameModel;
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
            case SELECT_WORKER:
                handleSelectWorkerMessage((SelectWorkerMessage) message);
                break;
            case MOVE:
                handleMoveMessage((MoveMessage) message);
                break;
            case USE_POWER:
                handleUsePowerMessage((UsePowerMessage) message);
                break;
            case BUILD:
                handleBuildMessage((BuildMessage) message);
                break;
            case END_TURN:
                handleEndTurnMessage((EndTurnMessage) message);
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
            return;
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
        gameModel.addPlayer(clientIndex, name);

        if (lobby.isFull()) {
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
    private void handleIsThreePlayersGameMessage(TypeMatchMessage message) {

        PlayerIndex clientIndex = message.getClient();
        boolean isThreePlayerGame = message.isThreePlayersMatch();

        if (lobby.isFull()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't set now the number of players",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
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

        if(isThreePlayerGame){
            gameModel.setNumPlayer(true);
        }
        else{
            gameModel.setNumPlayer(false);
        }
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

        if (godNames.stream().filter(Deck::isCorrectedName).count() != godNames.size()) {
            respondErrorToRemoteView(
                    clientIndex,
                    "You insert wrong names",
                    TypeMessage.WRONG_GOD_NAME
            );
            return;
        }

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

        if (godPhaseManager.isFinishSelectCardPhase())
            gameModel.setCurrentState(GameState.GOD_PLAYER_CHOOSE_FIRST_PLAYER);
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
    private void handlePutWorkerMessage(PutWorkerMessage message) {

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
            gameModel.setCurrentState(GameState.INITURN);
            //Create an instance of TurnManager and start the first turn
            this.turnManager = new TurnManager(this.gameModel);
            this.turnManager.startTurn();
        }
    }

    /**
     * This method is used to select the worker that user wants to use
     * it will notify the positions where the player can move or use a power
     * */
    private void handleSelectWorkerMessage(SelectWorkerMessage message){

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPos();

        if(isNotMessageSentByCurrentPlayer(message)){
            respondErrorToRemoteView(
                    clientIndex,
                    "Not your turn",
                    TypeMessage.NOT_YOUR_TURN
            );
            return;
        }
        if(isNotCurrentGameState(GameState.INITURN)){
            respondErrorToRemoteView(
                    clientIndex,
                    "You can't move a worker now!",
                    TypeMessage.WRONG_GAME_STATE
            );
            return;
        }

        //notify delle celle di entrambi i worker
        gameModel.setCurrentState(GameState.MOVE);
    }

    /**
     * This method is used to move a worker on the board
     * It responds with error if:
     * It's not the right state of the game
     * It's not the turn of the player
     * The player has selected a wrong worker cell
     * The player has selected an illegal move
     * */
    private void handleMoveMessage(MoveMessage message) {

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPosition();
        Position movePos = message.getMovePosition();

        //Move allowed only in states MOVE and INITPOWER
        if(isNotCurrentGameState(GameState.MOVE) && isNotCurrentGameState(GameState.INITPOWER)){
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
    private void handleBuildMessage(BuildMessage message) {

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
     * */
    private void handleUsePowerMessage(UsePowerMessage message){

        PlayerIndex clientIndex = message.getClient();
        Position workerPos = message.getWorkerPosition();
        Position powerPos = message.getPowerPosition();

        //Check that the player can use the power in this state
        if(!isNotCurrentGameState(gameModel.getCurrentPlayerPowerState())){
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
        //Send an error if the position given is not a worker of current player
        if(!gameModel.getBoard().workerPositions(clientIndex).contains(workerPos)){
            respondErrorToRemoteView(
                    clientIndex,
                    "You select a wrong position",
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
                gameModel.getCurrentPlayerGodName().equals("Pan")){

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
     * */
    private void handleEndTurnMessage(EndTurnMessage message){

        PlayerIndex clientIndex = message.getClient();

        //only in ENDPHASE and BUILDPOWER state is allowed to end the current turn
        if(isNotCurrentGameState(GameState.ENDPHASE) && isNotCurrentGameState(GameState.BUILDPOWER)){
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
        gameModel.setCurrentState(GameState.INITURN);

        //If current player has been blocked, he loses
        if(!this.turnManager.canCurrentPlayerMoveAWorker()){
            removeLoser(gameModel.getCurrentPlayerIndex());
        }
    }

    /**
     * Method called when a player has lost
     * It remove his workers and start the turn of the next player
     * If the remains only one player he wins the game
     * */
    private void removeLoser(PlayerIndex loserIndex){
        //notify loser payer
        respondOkToRemoteView(
                loserIndex,
                "You have lost!",
                TypeMessage.LOSER
        );
        //remove loser player
        gameModel.removeCurrentPlayer();
        //Set state and start new turn of other player
        gameModel.setCurrentState(GameState.MOVE);
        this.turnManager.endTurn();
        this.turnManager.startTurn();
        //if only 1 player remains he wins the game!
        if(gameModel.getNumPlayer() == 1){
            respondOkToRemoteView(
                    gameModel.getCurrentPlayerIndex(),
                    "You win!",
                    TypeMessage.WINNER
            );
            //Set state
            gameModel.setCurrentState(GameState.MATCH_ENDED);
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
