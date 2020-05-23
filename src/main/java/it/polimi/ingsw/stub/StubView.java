package it.polimi.ingsw.stub;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.*;

public class StubView extends ClientView {

    public StubView(PlayerIndex ind) {
        super(ind);
    }

    @Override
    public void updatePutWorker(PutWorkerMessage message) {

    }

    @Override
    public void updateMoveWorker(MoveMessage message) {

    }

    @Override
    public void updateBuild(BuildViewMessage message) {

    }

    @Override
    public void updateActionView(ActionMessage message) {

    }

    @Override
    public void updateSelectedCardView(PlayerSelectGodMessage message) {

    }
}
