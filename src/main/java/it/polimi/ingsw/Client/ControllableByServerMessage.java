package it.polimi.ingsw.Client;

import it.polimi.ingsw.message.*;

public interface ControllableByServerMessage {

    void updateNickname(NicknameMessage message);

    void updateCurrentPlayer(CurrentPlayerMessage message);

    void updateIndex(ConnectionPlayerIndex message);

    void updateState(UpdateStateMessage message);

    void updateAction(ActionMessage message);

    void updateGodCards(GodLikeChoseMessage message);

    void updateSelectedCard(PlayerSelectGodMessage message);

    void updatePutWorkerMessage(PutWorkerMessage message);

    void updateMoveMessage(MoveMessage message);

    void updateBuildMessage(BuildMessage message);

    void updateBuildPowerMessage(BuildPowerMessage message);

    void updateLoserMessage(LoserMessage message);

    void updateCloseConnectionMessage(CloseConnectionMessage message);

    void showInformationMessage(InformationMessage message);

}
