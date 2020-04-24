package it.polimi.ingsw.view;

import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;

public class ObservableStringOnlyForCheck extends Observable<Message> {
    public Message msg;

    public ObservableStringOnlyForCheck(Message m) {
        msg = m;
        notify(msg);
    }

    public void setMsg(Message m) {
        msg = m;
        notify(msg);
    }


}
