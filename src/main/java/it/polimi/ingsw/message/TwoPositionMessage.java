package it.polimi.ingsw.message;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;

import java.util.ArrayList;
import java.util.List;


/**
 * TwoPositionMessage extends Message and represent a message with
 * 2 Position
 */
public abstract class TwoPositionMessage extends Message {

    private final List<Position> positions;

    public TwoPositionMessage(PlayerIndex client, TypeMessage type, Position pos1, Position pos2) throws NullPointerException {
        super(client, type);
        if (pos1 == null)
            throw new NullPointerException("pos1");
        if (pos2 == null)
            throw new NullPointerException("pos2");
        positions = new ArrayList<>(2);
        positions.add(0, pos1);
        positions.add(1, pos2);

    }

    public List<Position> getPositions() {
        return positions;
    }
}
