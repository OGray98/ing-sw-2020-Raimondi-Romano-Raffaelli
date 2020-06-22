package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.exception.WrongGodNameException;
import it.polimi.ingsw.model.deck.Deck;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.List;

/**
 * GodLikeChoseMessage extends Message and represent an exchanged Message containing the god cards
 * chosen by God Player Like
 */
public class GodLikeChoseMessage extends Message implements MessageToServer, MessageToClient {

    private final List<String> godNames;

    public GodLikeChoseMessage(PlayerIndex client, List<String> godNames) {
        super(client, TypeMessage.GODLIKE_CHOOSE_CARDS);
        if (godNames == null)
            throw new NullPointerException("godNames");
        if (godNames.stream().filter(Deck::isCorrectedName).count() != godNames.size())
            throw new WrongGodNameException("godNames");
        this.godNames = godNames;
    }

    public List<String> getGodNames() {
        return godNames;
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleGodLikeChoseMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateGodCards(this);
    }
}
