package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.utils.*;

import java.util.List;

public class CLI extends ClientView {

    public CLI(ViewModelInterface clientModel) {
        super(clientModel);
    }

    @Override
    public void receiveErrorMessage(String error) {

    }

    @Override
    public void init() {

    }

    @Override
    public void showGod(List<String> gods) {

    }

    @Override
    public void removeActionsFromView(List<Position> list) {

    }


    @Override
    public String showSelectIP(String message) {
        return null;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showGetNickname() {

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
    public void updateActions(PositionMessage message) {

    }
}
