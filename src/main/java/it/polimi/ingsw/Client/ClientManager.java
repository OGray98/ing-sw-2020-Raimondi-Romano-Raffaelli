package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.board.BuildType;
import it.polimi.ingsw.network.ServerConnection;
import it.polimi.ingsw.utils.*;

public class ClientManager implements ControllableByServerMessage {

    private final ServerConnection serverConnection;
    private final ClientModel clientModel;
    //private final ClientView clientView;

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
     * Method that receives input from user and send the message to the server
     *
     * @param message is the message to send
     */
    public void sendToServer(MessageToServer message) {
        if (message == null)
            throw new NullPointerException("message");
        serverConnection.sendToServer(message);
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
    }

    @Override
    public void updateState(UpdateStateMessage message) {
        clientModel.setCurrentState(message.getGameState());
    }

    @Override
    public void updateAction(ActionMessage message){
        clientModel.setActionPositions(message.getActionType(), message.getPossiblePosition());
    }

    @Override
    public void updateGodCards(GodLikeChoseMessage message){
        for (String god : message.getGodNames()) {
            clientModel.addGodChosenByGodLike(god);
        }
    }

    @Override
    public void updateSelectedCard(PlayerSelectGodMessage message) {
        clientModel.setGodChosenByPlayer(message.getClient(), message.getGodName());
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
    public void updateLoserMessage(LoserMessage message){
        //TODO: metodo lose in clientmodel
        //clientModel.lose(message.getLoserPlayer());
    }
}
