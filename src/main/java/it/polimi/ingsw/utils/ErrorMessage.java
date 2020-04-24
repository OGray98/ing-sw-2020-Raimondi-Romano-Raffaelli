package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class ErrorMessage extends StringMessage {

    private final TypeMessage specificErrorType;

    public ErrorMessage(PlayerIndex client, String errorMessage, TypeMessage specificErrorType) {
        super(client, TypeMessage.ERROR, errorMessage);
        this.specificErrorType = specificErrorType;
    }

    public String getErrorMessage() {
        return super.getString();
    }

    public TypeMessage getSpecificErrorType() {
        return specificErrorType;
    }
}
