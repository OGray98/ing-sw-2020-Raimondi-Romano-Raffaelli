package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class OkMessage extends InformationMessage {

    public OkMessage(PlayerIndex client, TypeMessage specificOkType, String okMessage) {
        super(client, TypeMessage.OK, specificOkType, okMessage);
    }

    public String getErrorMessage() {
        return super.getString();
    }

    public TypeMessage getSpecificErrorType() {
        return super.getSpecificType();
    }
}
