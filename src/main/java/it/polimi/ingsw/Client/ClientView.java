package it.polimi.ingsw.Client;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

import javax.swing.*;

public abstract class ClientView implements Observer<MessageToView>, ControllableByViewMessage{
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
