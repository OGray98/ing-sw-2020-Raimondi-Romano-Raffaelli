package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.controller.GameState;

import java.util.HashMap;
import java.util.Map;

public class ActionMessageIndex {

    private final Map<GameState, Integer> actionNum;
    private final GameState godPowerState;

    public ActionMessageIndex(GameState godPowerState) {
        this.godPowerState = godPowerState;
        actionNum = new HashMap<>(
                Map.of(
                        GameState.MOVE, 2,
                        GameState.INITPOWER, 1,
                        GameState.BUILD, 1,
                        GameState.SECOND_MOVE, 1,
                        GameState.ENDPHASE, 0,
                        GameState.BUILDPOWER, 0)
        );
    }

    public int getActionNum(GameState clientState){
        if(clientState == godPowerState){
            if(clientState == GameState.MOVE){
                return actionNum.get(clientState) + 2;
            }
            else return actionNum.get(clientState) + 1;
        }
        return actionNum.get(clientState);
    }
}
