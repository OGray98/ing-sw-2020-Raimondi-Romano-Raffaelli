package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByViewMessage;

public class StubControllableByViewMessage implements ControllableByViewMessage {

    public static final int REM_P = 1;
    public static final int UP_C = 2;
    public static final int UP_I = 5;
    public static final int PUT = 6;
    public static final int MOVE = 7;
    public static final int BUILD = 8;
    public static final int UP_A = 10;
    public static final int UP_L = 13;
    public static final int INF = 14;
    public static final int EN = 3;

    public int n;

    @Override
    public void updatePutWorker(PutWorkerMessage message) {
        n = PUT;
    }

    @Override
    public void updateMoveWorker(MoveMessage message) {
        n = MOVE;
    }

    @Override
    public void updateBuild(BuildViewMessage message) {
        n = BUILD;
    }

    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {
        n = UP_C;
    }

    @Override
    public void updateActions(PositionMessage message) {
        n = UP_A;
    }

    @Override
    public void updateClientIndex(ConnectionPlayerIndex message) {
        n = UP_I;
    }

    @Override
    public void updateRemovePlayer(RemovePlayerMessage message) {
        n = REM_P;
    }

    @Override
    public void showWinner(InformationMessage message) {
        n = INF;
    }

    @Override
    public void showLoser(InformationMessage message) {
        n = UP_L;
    }

    @Override
    public void showPowerButton(boolean isOn) {

    }

    @Override
    public void showEndTurnButton(boolean isOn) {

    }

    @Override
    public void reinsertNickname() {
        n = EN;
    }
}
