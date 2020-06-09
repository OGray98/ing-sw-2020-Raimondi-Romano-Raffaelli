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
    private static boolean isFirst = true;


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
        //System.out.println("Receive message: " + message.getType());
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

                if(this.workersToPut.size() == 2){
                    this.workersToPut.clear();
                    this.workersToPut.add(putMsg.getPosition());
                    break;
                }

                if(this.workersToPut.size() == 0)
                    this.workersToPut.add(putMsg.getPosition());
                else if(this.workersToPut.size() == 1){
                    if(this.workersToPut.get(0).equals(putMsg.getPosition())){
                        clientView.showMessage("You can't put two workers on the same cell");
                        this.workersToPut.clear();
                        break;
                    }
                    this.workersToPut.add(putMsg.getPosition());
                    sendToServer(new PutWorkerMessage(putMsg.getClient(), this.workersToPut.get(0), putMsg.getPosition()));
                }
                break;
            case MOVE:
                PositionMessage moveMsg = (PositionMessage) message;

                //case when player selects a worker
                if(clientModel.getPlayerIndexPosition(moveMsg.getClient()).contains(moveMsg.getPosition())){
                    clientModel.setSelectedWorkerPos(moveMsg.getPosition());
                    break;
                }

                //case when there isn't a selected worker
                if(!clientModel.isThereASelectedWorker()){
                    break;
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
                        clientView.removeActionsFromView();
                        sendToServer(new MoveMessage(moveMsg.getClient(), clientModel.getSelectedWorkerPos(), moveMsg.getPosition()));
                    }
                    else{
                        //check if it is a correct position
                        if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(moveMsg.getPosition()))
                            break;
                        clientView.removeActionsFromView();
                        clientView.deactivatePower();
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
                clientView.removeActionsFromView();
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
                    clientView.removeActionsFromView();
                    sendToServer(new BuildMessage(buildMsg.getClient(), buildMsg.getPosition()));
                }
                else{
                    if(!clientModel.getActionPositions(clientModel.getSelectedWorkerPos(), ActionType.POWER).contains(buildMsg.getPosition()))
                        break;
                    clientView.removeActionsFromView();
                    clientView.deactivatePower();
                    sendToServer(new UsePowerMessage(buildMsg.getClient(), clientModel.getSelectedWorkerPos(), buildMsg.getPosition()));
                }
                break;
            case ENDPHASE:
                //case where player wants to end turn
                if(message.getType() == TypeMessage.END_TURN){
                    clientModel.clearActionLists();
                    sendToServer(message);
                    break;
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
                    clientView.removeActionsFromView();
                    clientView.deactivatePower();
                    sendToServer(new UsePowerMessage(buildPowerMsg.getClient(), clientModel.getSelectedWorkerPos(), buildPowerMsg.getPosition()));
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
                    clientView.removeActionsFromView();
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
        //System.out.println("Message sent: " + message.getType());
    }

    @Override
    public void updateNickname(NicknameMessage message) {
        clientModel.addNickname(message.getClient(), message.getNickname());

    }

    @Override
    public void updateCurrentPlayer(CurrentPlayerMessage message) {
        clientModel.setAmICurrentPlayer(message.getCurrentPlayerIndex() == clientModel.getPlayerIndex());
        //Show current player to the view
        clientView.showCurrentPlayer(message.getCurrentPlayerIndex());

        if(clientModel.getCurrentState() == GameState.PUT_WORKER && this.clientModel.getPlayerIndex() == message.getClient()){
            clientView.showMessage("Select two cells where put your workers");
        }


        //TODO: brutto da fare meglio se si riesce!
        if(clientModel.getCurrentState() == GameState.SELECT_CARD && message.getClient()!=PlayerIndex.PLAYER0){
            showGodSelect(message);
        }
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

        if(clientModel.getCurrentState() == GameState.PUT_WORKER && this.clientModel.getPlayerIndex() == message.getClient()){
            clientView.showMessage("Select two cells where put your workers");
        }

        if(clientModel.getCurrentState() == GameState.MOVE && this.clientModel.getPlayerIndex() == message.getClient()) {
            clientView.changeState("SELECT AND MOVE!!");
        }else if(clientModel.getCurrentState() == GameState.MOVE && this.clientModel.getPlayerIndex() != message.getClient()){
            clientView.changeState(clientModel.getNickname(message.getClient()) + " IS MOVING");
        }

        if(clientModel.getCurrentState() == GameState.BUILD && this.clientModel.getPlayerIndex() == message.getClient()) {
            clientView.changeState("BUILD ON CELL");
        }else if(clientModel.getCurrentState() == GameState.BUILD && this.clientModel.getPlayerIndex() != message.getClient()){
            clientView.changeState(clientModel.getNickname(message.getClient()) + " IS BUILDING");
        }

        if(clientModel.getCurrentState() == GameState.ENDPHASE && this.clientModel.getPlayerIndex() == message.getClient()) {
            clientView.changeState("END YOUR TURN");
        }else if(clientModel.getCurrentState() == GameState.ENDPHASE && this.clientModel.getPlayerIndex() != message.getClient()){
            clientView.changeState(clientModel.getNickname(message.getClient()) + " IS ENDING");
        }


        if(message.getGameState() == clientModel.getPowerGodState() && message.getClient().equals(clientModel.getPlayerIndex())){
            clientView.showPowerButton(true);
        }else{
            clientView.showPowerButton(false);
        }

        if((message.getGameState() == GameState.ENDPHASE || message.getGameState() == GameState.BUILDPOWER) && message.getClient().equals(clientModel.getPlayerIndex())){
            clientView.showEndTurnButton(true);
        }

        switch (currentState) {
            case GOD_PLAYER_CHOOSE_CARDS:

                if (this.clientModel.getPlayerIndex() == PlayerIndex.PLAYER0)
                    this.clientView.showGodLikeChoice(this.clientModel.getGods());
                else
                    this.clientView.showMessage("Player God like is choosing cards");
                break;
            case SELECT_CARD:
                showGodSelect(message);
                break;
            case GOD_PLAYER_CHOOSE_FIRST_PLAYER:
                if(this.clientModel.getPlayerIndex() == PlayerIndex.PLAYER0)
                    this.clientView.showGodLikeChooseFirstPlayer();
                else
                    this.clientView.showMessage("Player God Like is choosing first player");
                break;
            default:
                break;
        }
    }

    @Override
    public void updateAction(ActionMessage message){
        if (clientModel.isAmICurrentPlayer())
            clientModel.setActionPositions(message);
        else{
            //TODO: vanno rimosse le celle vecchie, ma non sempre
            /*clientView.removeActionsFromView();
            //show new action cells
            clientView.showActionPositions(message.getPossiblePosition(),false);*/
        }
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
        this.clientModel.movePlayer(message);
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

    @Override
    public void updateCloseConnectionMessage(CloseConnectionMessage message) {
        this.clientView.showMessage("Player " + this.clientModel.getNickname(message.getClient()) + "has closed connection!"
                + "\nGame is finished!");
        //TODO chiedere al client se vuole fare un'altra partita
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }


    public void showGodSelect(Message message) {
        if (this.clientModel.getPlayerIndex().equals(message.getClient())) {
            this.clientView.showGodToSelect(this.clientModel.getChosenGodsByGodLike());
        }
        else{
            this.clientView.showMessage(this.clientModel.getNickname(message.getClient()) + " is choosing his god card");
        }
    }

    @Override
    public void showInformationMessage(InformationMessage message){
        clientModel.notifyInformationMessage(message);
    }

}
