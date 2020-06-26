package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * General message used to send information both to the view and to the client
 * */
public class InformationMessage extends StringMessage implements MessageToClient, MessageToView {

    private final TypeMessage specificType;

    public InformationMessage(PlayerIndex client, TypeMessage type, TypeMessage specificType, String string) {
        super(client, type, string);
        this.specificType = specificType;
    }

    public TypeMessage getSpecificType() {
        return specificType;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {

    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {

    }
}
