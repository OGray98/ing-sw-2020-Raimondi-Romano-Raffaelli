package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PlayerChooseGodMessage extends Message and represent an exchanged Message containing the god card
 * selected by a player
 */
public class PlayerSelectGodMessage extends StringMessage {


    public PlayerSelectGodMessage(PlayerIndex client, String godName) {
        super(client, TypeMessage.SELECT_CARD, godName);
        if (!Deck.isCorrectedName(godName))
            throw new WrongGodNameException(godName);
    }

    public String getGodName() {
        return super.getString();
    }

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleSelectCardMessage(this);
    }
}
