package it.polimi.ingsw.Client;

import it.polimi.ingsw.message.*;

public interface ControllableByViewMessage {

    /**
     * Method that update the view when player put a worker
     * Implented in GUI or CLI
     * @param message is the message received from the server
     * */
    public void updatePutWorker(PutWorkerMessage message);

    /**
     * Method that update the view when a player move a worker
     * @param message is the message received from the server
     * */
    public void updateMoveWorker(MoveMessage message);

    /**
     * Method that update the view when a player builds
     * @param message is the message received from the server
     * */
    public void updateBuild(BuildViewMessage message);

    /**
     * Method that update the view when a player select his god
     * @param message is the message received from the server
     * */
    public void updateSelectedCardView(PlayerSelectGodMessage message);

    /**
     * Method that update the view when possible moves have to be shown
     * @param message is the message received from the server
     * */
    public void updateActions(PositionMessage message);

    /**
     * Method that update the view when a player connects
     * @param message is the message received from the server
     * */
    void updateClientIndex(ConnectionPlayerIndex message);

    /**
     * Method that remove a loser player from the board
     * @param message is the message received from the server
     * */
    void updateRemovePlayer(RemovePlayerMessage message);

    /**
     * Method that will show the winner at the end of the game
     * @param message is the message received from the server
     * */
    void showWinner(InformationMessage message);

    /**
     * Method that will show the loser during a game
     * @param message is the message received from the server
     * */
    void showLoser(InformationMessage message);

    /**
     * Method that show clearly the possibility of use a god power in the current turn phase
     * @param isOn indicates the state of the button
     * */
    void showPowerButton(boolean isOn);

    /**
     * Method that show clearly the possibility to end the turn
     * @param isOn indicates the state of the button
     * */
    void showEndTurnButton(boolean isOn);

    /**
     * When a player insert a nickname already taken he must reinsert it
     * */
    void reinsertNickname();
}
