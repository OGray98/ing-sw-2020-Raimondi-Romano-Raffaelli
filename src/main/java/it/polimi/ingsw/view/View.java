package it.polimi.ingsw.view;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utils.Message;

/**
 * View is an abstract class which represents an abstraction of View
 * in the pattern MVC.
 */
public abstract class View extends Observable<Message> {

    private final PlayerIndex player;

    public View(PlayerIndex player) {
        this.player = player;
    }

    public PlayerIndex getPlayer() {
        return player;
    }

    /**
     * If client of msg is correlated to player, it forward msg to controller
     *
     * @param msg message sent by client to be forwarded to the controller
     * @throws NullPointerException if msg is null
     */
    public void handleMessage(Message msg) throws NullPointerException {
        if (msg == null)
            throw new NullPointerException("msg");
        if (msg.getClient().compareTo(player) == 0)
            notify(msg);
    }


}
