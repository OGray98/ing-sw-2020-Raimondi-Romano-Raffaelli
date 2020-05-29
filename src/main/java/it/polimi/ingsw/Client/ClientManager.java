package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.network.ServerConnection;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

import java.util.ArrayList;
import java.util.List;

public class ClientManager implements ControllableByServerMessage, Observer<MessageToServer> {

    private final ServerConnection serverConnection;
    private final ClientModel clientModel;

    private ClientView clientView;

    private List<Position> workersToPut = new ArrayList<>();

    public ClientManager(ServerConnection serverConnection, ClientModel clientModel) {
        this.serverConnection = serverConnection;
        this.clientModel = clientModel;
    }

    /**
     * Method that receives notifies from the model and modifies the client representation of the model
     *
     * @param message used to take the value to use to modify the model client
     */
    public void updateClient(MessageToClient message) {
        if (message == null)
            throw new NullPointerException("message");
        message.execute(this);
    }

    /**
     * Method that receives a message from the client view and send it to the server
     * @param message is the message received from View
     * the message sent will be related to the GameState of the turn
     * */
    @Override
    public void update(MessageToServer message) {

        switch(clientModel.getCurrentState()){
            case START_GAME:
                sendToServer(message);
                break;
            case GOD_PLAYER_CHOOSE_CARDS:
                sendToServer(message);
                break;
            case SELECT_CARD:
                sendToServer(message);
                break;
            case GOD_PLAYER_CHOOSE_FIRST_PLAYER:
                sendToServer(message);
                break;
            case PUT_WORKER:
                PositionMessage putMsg = (PositionMessage) message;

                if(this.workersToPut.size() == 0)
                    this.workersToPut.add(putMsg.getPosition());
                else{
                    sendToServer(new PutWorkerMessage(putMsg.getClient(), this.workersToPut.get(0), putMsg.getPosition()));
                }
                break;
            case MOVE:
                PositionMessage moveMsg = (PositionMessage) message;

                //case when player selects a worker
                if(clientModel.getPlayerIndexPosition(moveMsg.getClient()).contains(moveMsg.getPosition())){
                    clientModel.setSelectedWorkerPos(moveMsg.getPosition());
                }
                //case when player want to move or use a power
                else{

                    try{
                        clientModel.getSelectedWorkerPos();
                    }
                    catch(NullPointerException e){
                        break;
                    }

                    if(!moveMsg.isUsingPower()){
                        //check if it is a correct position
                        if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE).contains(moveMsg.getPosition()))
                            break;
                        clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE));
                        sendToServer(new MoveMessage(moveMsg.getClient(), clientModel.getSelectedWorkerPos(), moveMsg.getPosition()));
                    }
                    else{
                        //check if it is a correct position
                        if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(moveMsg.getPosition()))
                            break;
                        clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER));
                        sendToServer(new UsePowerMessage(moveMsg.getClient(), clientModel.getSelectedWorkerPos(), moveMsg.getPosition()));
                    }
                }
                break;
            case INITPOWER:
                PositionMessage initPowMoveMsg = (PositionMessage) message;

                try{
                    clientModel.getSelectedWorkerPos();
                }
                catch(NullPointerException e){
                    break;
                }

                //check if it is a correct position
                if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE).contains(initPowMoveMsg.getPosition()))
                    break;
                clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD));
                sendToServer(new MoveMessage(initPowMoveMsg.getClient(), clientModel.getSelectedWorkerPos(), initPowMoveMsg.getPosition()));
                break;
            case BUILD:
                PositionMessage buildMsg = (PositionMessage) message;

                try{
                    clientModel.getSelectedWorkerPos();
                }
                catch(NullPointerException e){
                    break;
                }

                if(!buildMsg.isUsingPower()){
                    if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD).contains(buildMsg.getPosition()))
                        break;
                    clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD));
                    sendToServer(new BuildMessage(buildMsg.getClient(), buildMsg.getPosition()));
                }
                else{
                    if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(buildMsg.getPosition()))
                        break;
                    clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD));
                    sendToServer(new UsePowerMessage(buildMsg.getClient(), clientModel.getSelectedWorkerPos(), buildMsg.getPosition()));
                }
                break;
            case ENDPHASE:
                //case where player wants to end turn
                if(message.getType() == TypeMessage.END_TURN){
                    clientModel.clearActionLists();
                    sendToServer(message);
                }
                PositionMessage buildPowerMsg = (PositionMessage) message;

                try{
                    clientModel.getSelectedWorkerPos();
                }
                catch(NullPointerException e){
                    break;
                }

                if(buildPowerMsg.isUsingPower()){
                    //check if it is a correct position
                    if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(buildPowerMsg.getPosition()))
                        break;
                    clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD));
                    sendToServer(new UsePowerMessage(buildPowerMsg.getClient(), clientModel.getSelectedWorkerPos(),buildPowerMsg.getPosition()));
                }
                break;
            case BUILDPOWER:
                //Here will arrive the endTurn message
                sendToServer(message);
                clientModel.clearActionLists();
                break;
            case SECOND_MOVE:
                PositionMessage secondMoveMsg = (PositionMessage) message;

                try{
                    clientModel.getSelectedWorkerPos();
                }
                catch(NullPointerException e){
                    break;
                }

                if(!secondMoveMsg.isUsingPower()){
                    if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.MOVE).contains(secondMoveMsg.getPosition()))
                        break;
                    clientView.removeActionsFromView(clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.BUILD));
                    sendToServer(new BuildMessage(secondMoveMsg.getClient(), secondMoveMsg.getPosition()));
                }
                break;
            case MATCH_ENDED:
                break;
        }
    }

    /**
     * Method that receives input from user and send the message to the server
     *
     * @param message is the message to send
     */
    public void sendToServer(MessageToServer message) {
        if (message == null)
            throw new NullPointerException("message");
        serverConnection.sendToServer(message);
        System.out.println("Message receive: " + message.getType());
    }

    @Override
    public void updateNickname(NicknameMessage message) {
        clientModel.addNickname(message.getClient(), message.getNickname());
    }

    @Override
    public void updateCurrentPlayer(CurrentPlayerMessage message) {
        clientModel.setAmICurrentPlayer(message.getCurrentPlayerIndex() == clientModel.getPlayerIndex());
    }

    @Override
    public void updateIndex(ConnectionPlayerIndex message) {
        clientModel.setPlayerIndex(message.getPlayerIndex());
        clientView.showGetNickname();
    }

    @Override
    public void updateState(UpdateStateMessage message) {
        GameState currentState = message.getGameState();
        clientModel.setCurrentState(currentState);

        switch (currentState) {
            case GOD_PLAYER_CHOOSE_CARDS:
                if (this.clientModel.getPlayerIndex() == PlayerIndex.PLAYER0)
                    this.clientView.showGodLikeChoice(this.clientModel.getGods());
                else
                    this.clientView.showMessage("Player God like is choosing cards");
                break;
            case SELECT_CARD:
                if (this.clientModel.getPlayerIndex() == PlayerIndex.PLAYER1)
                    this.clientView.showGodToSelect(this.clientModel.getChosenGodsByGodLike());
                else
                    this.clientView.showMessage(this.clientModel.getNickname(PlayerIndex.PLAYER1) +
                            "is choosing his god card");
                break;
            case GOD_PLAYER_CHOOSE_FIRST_PLAYER:
                if(this.clientModel.getPlayerIndex() == PlayerIndex.PLAYER0)
                    this.clientView.showGodLikeChooseFirstPlayer();
                break;
            default:
                break;
        }
    }

    @Override
    public void updateAction(ActionMessage message){
        clientModel.setActionPositions(message);
    }

    @Override
    public void updateGodCards(GodLikeChoseMessage message){
        for (String god : message.getGodNames()) {
            clientModel.addGodChosenByGodLike(god);
        }
    }

    @Override
    public void updateSelectedCard(PlayerSelectGodMessage message) {
        clientModel.setGodChosenByPlayer(message);
    }

    @Override
    public void updatePutWorkerMessage(PutWorkerMessage message) {
        clientModel.putWorker(message.getClient(), message.getPositionOne(), message.getPositionTwo());
    }

    @Override
    public void updateMoveMessage(MoveMessage message) {
        clientModel.movePlayer(message);
    }

    @Override
    public void updateBuildMessage(BuildMessage message) {
        clientModel.incrementLevel(message.getBuildPosition());
    }

    @Override
    public void updateBuildPowerMessage(BuildPowerMessage message) {
        if (message.getBuildType() == BuildType.DOME) {
            clientModel.addDome(message.getBuildPosition());
        } else
            clientModel.incrementLevel(message.getBuildPosition());
    }

    @Override
    public void updateLoserMessage(LoserMessage message) {
        clientModel.playerLose(message.getLoserPlayer());
        //TODO notificare la view della sconfitta
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }

}
