package it.polimi.ingsw.Client;

import it.polimi.ingsw.message.*;

public interface ControllableByViewMessage {

    /**
     * Method that update the view when player put a worker
     * Implented in GUI or CLI
     * */
    public void updatePutWorker(PutWorkerMessage message);

    public void updateMoveWorker(MoveMessage message);

    public void updateBuild(BuildViewMessage message);

    public void updateSelectedCardView(PlayerSelectGodMessage message);

    public void updateActions(PositionMessage message);

    void updateClientIndex(ConnectionPlayerIndex message);

    /**
     * Method that remove a loser player from the board
     * */
    void updateRemovePlayer(RemovePlayerMessage message);

    /**
     * Method that will show the winner at the end of the game
     * */
    void showWinner(InformationMessage message);

    /**
     * Method that will show the loser during a game
     * */
    void showLoser(InformationMessage message);

    /**
     * Method that show clearly the possibility of use a god power in the current turn phase
     * */
    void showPowerButton(boolean isOn);

    /**
     * Method that show clearly the possibility to end the turn
     * */
    void showEndTurnButton(boolean isOn);

    /**
     * When a player insert a nickname already taken he must reinsert it
     * */
    void reinsertNickname();
}
