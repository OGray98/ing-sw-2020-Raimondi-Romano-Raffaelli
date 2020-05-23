package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.view.View;

import javax.swing.*;

public abstract class ClientView extends View implements Observer<MessageToView>, ControllableByViewMessage{

    public ClientView(PlayerIndex player) {
        super(player);
    }

    /**
     * When the client receives an error it send it to the view
     * */
    public void receiveErrorMessage(String error){}


    //method from Observer
    @Override
    public void update(MessageToView message) {
        if(message == null)
            throw new NullPointerException("message");
        message.execute(this);
    }
}
