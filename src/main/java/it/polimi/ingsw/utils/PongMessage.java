package it.polimi.ingsw.utils;

public class PongMessage extends Message {

    public PongMessage(){
        super(null,TypeMessage.PONG);
    }

    @Override
    public String toString() {
        return "Pong message from client";
    }
}
