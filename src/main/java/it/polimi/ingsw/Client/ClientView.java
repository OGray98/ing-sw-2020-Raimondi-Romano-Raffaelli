package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.ConnectionPlayerIndex;
import it.polimi.ingsw.utils.MessageToView;
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
     */
    public abstract void receiveErrorMessage(String error);

    public abstract void init();

    public abstract void showGodLikeChoice(List<String> gods);

    public abstract void showGodToSelect(List<String> godLikeGods);

    public abstract void showGodLikeChooseFirstPlayer();



    //method from Observer
    @Override
    public void update(MessageToView message) {
        if (message == null)
            throw new NullPointerException("message");
        message.execute(this);
    }

    public abstract String showSelectIP(String message);

    public abstract void showMessage(String message);

    /**
     * A seconda del playerIndex fa selezionare il nickname e se sei il
     * player0 anche il numero di giocatori
     */
    public abstract void showGetNickname();

    @Override
    public void updateClientIndex(ConnectionPlayerIndex message) {
        super.setPlayer(message.getPlayerIndex());
    }

    //TODO: serve per rimuovere le celle illuminate
    public abstract void removeActionsFromView();

    public abstract void showActionPositions(List<Position> possiblePosition);

    public abstract void showActionPositionsPower(List<Position> possiblePosition);

    /**
     * Method used to deactivate the god power after using it
     * called in ClientManager after sending the UsePowerMessage
     * */
    public abstract void deactivatePower();
}
