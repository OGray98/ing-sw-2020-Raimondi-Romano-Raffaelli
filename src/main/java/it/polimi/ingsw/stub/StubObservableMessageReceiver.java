package it.polimi.ingsw.stub;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;

public class StubObservableMessageReceiver extends Observable<Message> {
    public Message msg;

    public StubObservableMessageReceiver(Message m) {
        msg = m;
        notify(msg);
    }

    public void setMsg(Message m) {
        msg = m;
        notify(msg);
    }


}
