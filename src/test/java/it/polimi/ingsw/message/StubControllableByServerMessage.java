package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.utils.*;

public class StubControllableByServerMessage implements ControllableByServerMessage {

    public static final int NICK = 1;
    public static final int UP_CUR_P = 2;
    public static final int GL_CARD = 3;
    public static final int SEL_C = 4;
    public static final int UP_I = 5;
    public static final int PUT = 6;
    public static final int MOVE = 7;
    public static final int BUILD = 8;
    public static final int UP_S = 9;
    public static final int UP_A = 10;
    public static final int C_CON = 11;
    public static final int UP_BP = 12;
    public static final int UP_L = 13;
    public static final int INF = 14;

    public int n;


    @Override
    public void updateNickname(NicknameMessage message) {
        n = NICK;
    }

    @Override
    public void updateCurrentPlayer(CurrentPlayerMessage message) {
        n = UP_CUR_P;
    }

    @Override
    public void updateIndex(ConnectionPlayerIndex message) {
        n = UP_I;
    }

    @Override
    public void updateState(UpdateStateMessage message) {
        n = UP_S;
    }

    @Override
    public void updateAction(ActionMessage message) {
        n = UP_A;
    }

    @Override
    public void updateGodCards(GodLikeChoseMessage message) {
        n = GL_CARD;
    }

    @Override
    public void updateSelectedCard(PlayerSelectGodMessage message) {
        n = SEL_C;
    }

    @Override
    public void updatePutWorkerMessage(PutWorkerMessage message) {
        n = PUT;
    }

    @Override
    public void updateMoveMessage(MoveMessage message) {
        n = MOVE;
    }

    @Override
    public void updateBuildMessage(BuildMessage message) {
        n = BUILD;
    }

    @Override
    public void updateBuildPowerMessage(BuildPowerMessage message) {
        n = UP_BP;
    }

    @Override
    public void updateLoserMessage(LoserMessage message) {
        n = UP_L;
    }

    @Override
    public void updateCloseConnectionMessage(CloseConnectionMessage message) {
        n = C_CON;
    }

    @Override
    public void showInformationMessage(InformationMessage message) {
        n = INF;
    }
}
