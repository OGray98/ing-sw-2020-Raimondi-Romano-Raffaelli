package it.polimi.ingsw.Client;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.MessageToView;
import it.polimi.ingsw.view.View;

public abstract class ClientView extends View implements Observer<MessageToView>, ControllableByViewMessage {

    private final ViewModelInterface clientModel;

    public ClientView(ViewModelInterface clientModel) {
        super();
        this.clientModel = clientModel;
    }

    /**
     * When the client receives an error it send it to the view
     */
    public abstract void receiveErrorMessage(String error);

    public abstract void init();


    //method from Observer
    @Override
    public void update(MessageToView message) {
        if (message == null)
            throw new NullPointerException("message");
        message.execute(this);
    }
}
