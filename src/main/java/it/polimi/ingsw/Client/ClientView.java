package it.polimi.ingsw.Client;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.*;

public abstract class ClientView implements Observer<Message>{
    /**
     * When the client receives an error it send it to the view
     * */
    public void receiveErrorMessage(String error){}

    /**
     * Method that update the view when player put a worker
     * Implented in GUI or CLI
     * */
    public void updatePutWorker(PutWorkerMessage message){}

    public void updateMoveWorker(MoveMessage message){}

    public void updateBuild(BuildViewMessage message){}

    //method from Observer
    @Override
    public void update(Message message) {
        if(message == null)
            throw new NullPointerException("message");
        switch(message.getType()){
            case PUT_WORKER:
                updatePutWorker((PutWorkerMessage) message);
                break;
            case MOVE:
                updateMoveWorker((MoveMessage)message);
                break;
            case BUILD:
                updateBuild((BuildViewMessage) message);
                break;
        }
    }
}
