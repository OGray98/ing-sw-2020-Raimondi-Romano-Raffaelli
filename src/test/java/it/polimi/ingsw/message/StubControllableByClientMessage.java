package it.polimi.ingsw.message;

import it.polimi.ingsw.controller.ControllableByClientMessage;
import it.polimi.ingsw.utils.*;

public class StubControllableByClientMessage implements ControllableByClientMessage {

    public static final int NICK = 1;
    public static final int THREE_P = 2;
    public static final int GL_CARD = 3;
    public static final int SEL_C = 4;
    public static final int GL_P = 5;
    public static final int PUT = 6;
    public static final int MOVE = 7;
    public static final int BUILD = 8;
    public static final int USE_PO = 9;
    public static final int E_TURN = 10;
    public static final int C_CON = 11;

    public int n;

    @Override
    public void handleNicknameMessage(NicknameMessage message) {
        n = NICK;
    }

    @Override
    public void handleIsThreePlayersGameMessage(TypeMatchMessage message) {
        n = THREE_P;
    }

    @Override
    public void handleGodLikeChoseMessage(GodLikeChoseMessage message) {
        n = GL_CARD;
    }

    @Override
    public void handleSelectCardMessage(PlayerSelectGodMessage message) {
        n = SEL_C;
    }

    @Override
    public void handleGodLikeChooseFirstPlayerMessage(GodLikeChooseFirstPlayerMessage message) {
        n = GL_P;
    }

    @Override
    public void handlePutWorkerMessage(PutWorkerMessage message) {
        n = PUT;
    }

    @Override
    public void handleMoveMessage(MoveMessage message) {
        n = MOVE;
    }

    @Override
    public void handleBuildMessage(BuildMessage message) {
        n = BUILD;
    }

    @Override
    public void handleUsePowerMessage(UsePowerMessage message) {
        n = USE_PO;
    }

    @Override
    public void handleEndTurnMessage(EndTurnMessage message) {
        n = E_TURN;
    }

    @Override
    public void handleCloseConnectionMessage(CloseConnectionMessage message) {
        n = C_CON;
    }
}
