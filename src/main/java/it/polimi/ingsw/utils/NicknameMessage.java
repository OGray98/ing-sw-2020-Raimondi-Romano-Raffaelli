package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * NicknameMessage extends Message and represent an exchanged Message containing the nickname
 * of a player
 */
public class NicknameMessage extends Message {

    private final String nickname;

    public NicknameMessage(PlayerIndex client, String nickname) {
        super(client, TypeMessage.NICKNAME);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
