package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

public class InformationMessage extends StringMessage {

    private final TypeMessage specificType;

    public InformationMessage(PlayerIndex client, TypeMessage type, TypeMessage specificType, String string) {
        super(client, type, string);
        this.specificType = specificType;
    }

    public TypeMessage getSpecificType() {
        return specificType;
    }
}
