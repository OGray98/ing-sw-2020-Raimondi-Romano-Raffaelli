package it.polimi.ingsw.Client;

public interface ClientView {
    /**
     * When the client receives an error it send it to the view
     * */
    void receiveErrorMessage(String error);
}
