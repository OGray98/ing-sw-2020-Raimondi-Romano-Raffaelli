package it.polimi.ingsw.controller;

/**
 * Enum that contains all the possible state during a game
 * It is used to separate differen turn phases
 * */
public enum GameState {
    START_GAME, GOD_PLAYER_CHOOSE_CARDS, SELECT_CARD, GOD_PLAYER_CHOOSE_FIRST_PLAYER, PUT_WORKER, MOVE, INITPOWER, BUILD, ENDPHASE, BUILDPOWER, SECOND_MOVE, MATCH_ENDED, NULL

    /* INITPOWER is a state used by Prometheus after the power, it must be considered like a MOVE state, it permits only the call of moveWorker()
     * BUILDPOWER is a state used by building gods ( Demeter, Hephaestus), it must be considered like a ENDPHASE state, it permits only the call of endTurn()*/
    /* MATCH_ENDED is the state when  a player has won, the game is finished*/
}
