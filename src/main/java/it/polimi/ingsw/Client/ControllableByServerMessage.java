package it.polimi.ingsw.Client;

import it.polimi.ingsw.message.*;

public interface ControllableByServerMessage {

    /**
     * Method that update the client model with a new nickname received
     * @param message is the message that contains the new nickname
     * */
    void updateNickname(NicknameMessage message);

    /**
     * Method that update the client model with the new current player
     * @param message is the message that contains the new current player
     * */
    void updateCurrentPlayer(CurrentPlayerMessage message);

    /**
     * Method that update the client model with a new player connected
     * @param message is the message that contains the new player index
     * */
    void updateIndex(ConnectionPlayerIndex message);

    /**
     * Method that update the client model with the new current state
     * @param message is the message that contains the new state of the game
     * */
    void updateState(UpdateStateMessage message);

    /**
     * Method that update the client model with the list of possible actions to show to the current player
     * @param message is the message that contains the list of possible moves
     * */
    void updateAction(ActionMessage message);

    /**
     * Method that update the client model with the cards that the godLike player has chosen
     * @param message is the message that contains the gods chosen
     * */
    void updateGodCards(GodLikeChoseMessage message);

    /**
     * Method that update the client model with the god that a player has selected
     * @param message is the message that contains the god selected and the player who select it
     * */
    void updateSelectedCard(PlayerSelectGodMessage message);

    /**
     * Method that update the client model when a player put his two workers on the board
     * @param message is the message that contains the positions to put the workers
     * */
    void updatePutWorkerMessage(PutWorkerMessage message);

    /**
     * Method that update the client model when player moves his worker
     * @param message is the message that contains the information to move the worker
     * */
    void updateMoveMessage(MoveMessage message);

    /**
     * Method that update the client model when a player build during his turn
     * @param message is the message that contains the position to build
     * */
    void updateBuildMessage(BuildMessage message);

    /**
     * Method that update the client model when a player build with a god power
     * @param message is the message that contains the position to build and the level of the building
     * */
    void updateBuildPowerMessage(BuildPowerMessage message);

    /**
     * Method that update the client model when a player lose
     * loser player is deleted from the view
     * @param message is the message that contains the index of the loser player
     * */
    void updateLoserMessage(LoserMessage message);

    /**
     * Method that update the client model when a player disconnects
     * the game ends after this event
     * @param message is the message that contains the index of the player disconnected
     * */
    void updateCloseConnectionMessage(CloseConnectionMessage message);

    /**
     * Method that update the view and show an InformationMessage
     * @param message is the message to show
     * */
    void showInformationMessage(InformationMessage message);

}
