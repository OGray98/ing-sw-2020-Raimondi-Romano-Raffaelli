package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class UpdateStateMessage extends InformationMessage {

    public UpdateStateMessage(PlayerIndex client, TypeMessage specificType, String string) {
        super(client, TypeMessage.UPDATE_STATE, specificType, string);
    }

    public TypeMessage getSentState() {
        return super.getSpecificType();
    }
}
