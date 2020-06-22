package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * NicknameMessage extends Message and represent an exchanged Message containing the nickname
 * of a player
 */
public class NicknameMessage extends StringMessage implements MessageToServer, MessageToClient {

    public NicknameMessage(PlayerIndex client, String nickname) {
        super(client, TypeMessage.NICKNAME, nickname);
    }

    public String getNickname() {
        return super.getString();
    }

    @Override
    public void execute(ControllableByClientMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.handleNicknameMessage(this);
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateNickname(this);
    }
}