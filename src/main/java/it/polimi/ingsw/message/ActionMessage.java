package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.List;

/**
 * Message used to notify the list of actions possible
 * */
public class ActionMessage extends Message implements MessageToClient {

    private final Position workerPos;
    private final List<Position> possiblePosition;
    private final ActionType actionType;

    public ActionMessage(PlayerIndex client, Position workersPos, List<Position> actionPositions, ActionType actionType) {
        super(client, TypeMessage.ACTION_MESSAGE);
        if (workersPos == null) throw new NullPointerException("workersPos");
        if (actionPositions == null) throw new NullPointerException("actionPositions");

        this.workerPos = workersPos;
        this.possiblePosition = actionPositions;
        this.actionType = actionType;


    }

    public Position getWorkerPos() {
        return workerPos;
    }

    public List<Position> getPossiblePosition() {
        return possiblePosition;
    }

    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public void execute(ControllableByServerMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateAction(this);
    }

}
