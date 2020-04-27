package it.polimi.ingsw.utils;

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
}