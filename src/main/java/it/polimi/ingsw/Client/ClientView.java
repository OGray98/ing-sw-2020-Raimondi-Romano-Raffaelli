package it.polimi.ingsw.Client;

import it.polimi.ingsw.message.ConnectionPlayerIndexMessage;
import it.polimi.ingsw.message.MessageToView;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.View;

import java.util.List;

public abstract class ClientView extends View implements Observer<MessageToView>, ControllableByViewMessage {

    protected final ViewModelInterface clientModel;

    public ClientView(ViewModelInterface clientModel) {
        super();
        this.clientModel = clientModel;
    }

    /**
     * When the client receives an error it send it to the view
     * @param error is the error message
     */
    public abstract void receiveErrorMessage(String error);

    /**
     * Method that initialize the view
     * it prepares the representation of the game in the client side
     * */
    public abstract void init();

    /**
     * Show to the view the list of gods that the godLike player has to choose
     * @param gods is the list of gods available
     * */
    public abstract void showGodLikeChoice(List<String> gods);

    /**
     * Show to the view the list of gods that a player (not the godLike) can choose
     * @param godLikeGods is the list of gods available
     * */
    public abstract void showGodToSelect(List<String> godLikeGods);

    /**
     * Show to the godLike the selection panel to choose who will be the first player of the game
     * */
    public abstract void showGodLikeChooseFirstPlayer();

    /**
     * Method that show the current player to the view
     * @param currentPlayer is the index of current player
     * */
    public abstract void showCurrentPlayer(PlayerIndex currentPlayer);

    /**
     * Method that notify with a message the view the state of the turn
     * @param state is the new state of the game
     * */
    public abstract void changeState(String state);

    /**
     * Method used in cli to receive cell in input
     * */
    public abstract void receiveInputCli();

    //method from Observer
    @Override
    public void update(MessageToView message) {
        if (message == null)
            throw new NullPointerException("message");
        message.execute(this);
    }

    /**
     * Show to the view the input panel to insert the ip of the server
     * @param message is the message shown to the view
     * @return the string to print in the input panel
     * */
    public abstract String showSelectIP(String message);

    /**
     * Generic method to show a message to the view
     * @param message is the message that has to be shown
     * */
    public abstract void showMessage(String message);

    /**
     * Show the input request for the nickname
     * If player index is PLAYER0 it shows also the selection of the number of players of the game
     */
    public abstract void showGetNickname();

    @Override
    public void updateClientIndex(ConnectionPlayerIndexMessage message) {
        super.setPlayer(message.getPlayerIndex());
    }

    /**
     * Method used in gui to update and delete the old possible moves
     * */
    public abstract void removeActionsFromView();

    /**
     * Method used to show to the view the cells where a player can make an action
     * @param possiblePosition is the list of positions to show
     * @param isPowerCells indicates if the cells are related to the normal moves or power moves
     * */
    public abstract void showActionPositions(List<Position> possiblePosition, boolean isPowerCells);

    /**
     * Method used to deactivate the god power after using it
     * called in ClientManager after sending the UsePowerMessage
     * */
    public abstract void deactivatePower();
}
