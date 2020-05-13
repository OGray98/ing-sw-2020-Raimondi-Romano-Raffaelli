package it.polimi.ingsw.utils;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * PlayerChooseGodMessage extends Message and represent an exchanged Message containing the god card
 * selected by a player
 */
public class PlayerSelectGodMessage extends StringMessage implements MessageToServer, MessageToClient {


    public PlayerSelectGodMessage(PlayerIndex client, String godName) {
        super(client, TypeMessage.SELECT_CARD, godName);
        if (!Deck.isCorrectedName(godName))
            throw new WrongGodNameException(godName);
    }

    public String getGodName() {
        return super.getString();
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleSelectCardMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateSelectedCard(this);
    }
}
