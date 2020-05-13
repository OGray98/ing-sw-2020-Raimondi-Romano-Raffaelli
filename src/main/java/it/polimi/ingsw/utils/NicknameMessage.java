package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.MessageControllable;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * NicknameMessage extends Message and represent an exchanged Message containing the nickname
 * of a player
 */
public class NicknameMessage extends StringMessage {

    public NicknameMessage(PlayerIndex client, String nickname) {
        super(client, TypeMessage.NICKNAME, nickname);
    }

    public String getNickname() {
        return super.getString();
    }

    @Override
    public void execute(MessageControllable controllable) throws NullPointerException {
        super.execute(controllable);
        controllable.handleNicknameMessage(this);
    }
}