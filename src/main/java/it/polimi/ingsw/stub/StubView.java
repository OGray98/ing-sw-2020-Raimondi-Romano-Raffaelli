package it.polimi.ingsw.stub;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.utils.ActionMessage;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

public class StubView extends ClientView {

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
}
