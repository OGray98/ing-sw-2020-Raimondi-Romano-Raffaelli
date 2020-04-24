package it.polimi.ingsw.utils;

import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PlayerChooseGodMessage extends Message and represent an exchanged Message containing the god card
 * selected by a player
 */
public class PlayerSelectGodMessage extends Message {

    private final String godName;

    public PlayerSelectGodMessage(PlayerIndex client, String godName) {
        super(client, TypeMessage.SELECT_CARD);
        if (godName == null)
            throw new NullPointerException("godName");
        if (!Deck.isCorrectedName(godName))
            throw new WrongGodNameException(godName);
        this.godName = godName;
    }

    public String getGodName() {
        return godName;
    }
}
