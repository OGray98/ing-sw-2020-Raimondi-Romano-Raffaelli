package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.List;

/**
 * Message sent after player lose and need to remove his workers from the board
 */
public class RemovePlayerMessage extends Message implements MessageToView {

    private final List<Position> removePositions;

    public RemovePlayerMessage(PlayerIndex client, List<Position> removePositions) {
        super(client, TypeMessage.LOSER);
        if (removePositions == null) throw new NullPointerException("removePositions");

        this.removePositions = removePositions;
    }

    public List<Position> getRemovePositions() {
        return removePositions;
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.updateRemovePlayer(this);
    }
}
