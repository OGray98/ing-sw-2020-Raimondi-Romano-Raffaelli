package it.polimi.ingsw.controller;

import it.polimi.ingsw.utils.*;

public interface MessageControllable {

    /**
     * Handle a TypeMatchMessage
     *
     * @param message input message. Must be corrected
     */
    void handleNicknameMessage(NicknameMessage message);

    /**
     * Handle a TypeMatchMessage
     *
     * @param message input message. Must be corrected
     */
    void handleIsThreePlayersGameMessage(TypeMatchMessage message);

    /**
     * Handle a GodLikeChoseMessage
     *
     * @param message input message. Must be corrected
     */
    void handleGodLikeChoseMessage(GodLikeChoseMessage message);


    /**
     * Handle a PlayerSelectGodMessage
     *
     * @param message is the input message sent from the current player
     */
    void handleSelectCardMessage(PlayerSelectGodMessage message);

    /**
     * Handle a GodLikeChooseFirstPlayerMessage
     *
     * @param message is input message which contains the index of the first player chosen
     */
    void handleGodLikeChooseFirstPlayerMessage(GodLikeChooseFirstPlayerMessage message);

    /**
     * Handle a PutWorkerMessage
     *
     * @param message is the input message of type PutWorkerMessage
     */
    void handlePutWorkerMessage(PutWorkerMessage message);

    /**
     * Handle a SelectWorkerMessage
     *
     * @param message is the input message of type SelectWorkerMessage
     */
    void handleSelectWorkerMessage(SelectWorkerMessage message);

    /**
     * Handle a MoveMessage
     *
     * @param message is the input message of type MoveMessage
     */
    void handleMoveMessage(MoveMessage message);

    /**
     * Handle a BuildMessage
     *
     * @param message is the input message of type BuildMessage
     */
    void handleBuildMessage(BuildMessage message);

    /**
     * Handle a UsePowerMessage
     *
     * @param message is the input message of type UsePowerMessage
     */
    void handleUsePowerMessage(UsePowerMessage message);

    /**
     * Handle a EndTurnMessage
     *
     * @param message is the input message of type EndTurnMessage
     */
    void handleEndTurnMessage(EndTurnMessage message);
}
