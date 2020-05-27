package it.polimi.ingsw.Client;

import it.polimi.ingsw.controller.GameState;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.ActionType;

import java.util.List;

/**
 * Interface that allows View (CLI/GUI) to access to some getter of the ClientModel
 */
public interface ViewModelInterface {

    List<String> getGods();

    List<Position> getActionPositions(Position workerPos, ActionType type);

    Position getSelectedWorkerPos();

    GameState getCurrentState();

    GameState getPowerGodState();

    PlayerIndex getPlayerIndex();
}
