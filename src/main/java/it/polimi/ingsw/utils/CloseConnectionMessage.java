package it.polimi.ingsw.utils;

public class CloseConnectionMessage extends Message{

    public CloseConnectionMessage(){
        super(null,TypeMessage.CLOSE_CONNECTION);
    }

    @Override
    public String toString() {
        return "Connection closed";
    }
}
