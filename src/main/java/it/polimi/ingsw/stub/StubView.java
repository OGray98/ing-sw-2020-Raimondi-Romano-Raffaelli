package it.polimi.ingsw.stub;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.model.player.PlayerIndex;
import it.polimi.ingsw.utils.BuildViewMessage;
import it.polimi.ingsw.utils.MoveMessage;
import it.polimi.ingsw.utils.PlayerSelectGodMessage;
import it.polimi.ingsw.utils.PutWorkerMessage;

public class StubView extends ClientView {

    public StubView(PlayerIndex ind, ViewModelInterface model) {
        super(model);
        super.setPlayer(ind);
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
    public void updateSelectedCardView(PlayerSelectGodMessage message) {

    }

    @Override
    public void receiveErrorMessage(String error) {

    }

    @Override
    public void init() {

    }
}
