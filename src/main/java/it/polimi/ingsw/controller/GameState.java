package it.polimi.ingsw.controller;

public enum GameState {
    GOD_PLAYER_CHOOSE_CARDS, SELECT_CARD, GOD_PLAYER_CHOOSE_FIRST_PLAYER, PUT_WORKER, INITURN, CANMOVE, MOVE, INITPOWER, CHECKWIN, CANBUILD, BUILD, CANUSEPOWER, USEPOWER, CHECKIFUSEPOWER, ENDPHASE, BUILDPOWER, NULL

    /*INITPOWER is a state used by Prometheus after the power, it must be considered like a MOVE state, it permits only the call of moveWorker()
     * BUILDPOWER is a state used by building gods ( Demeter, Hephaestus), it must be considered like a ENDPHASE state, it permits only the call of endTurn()*/
}
