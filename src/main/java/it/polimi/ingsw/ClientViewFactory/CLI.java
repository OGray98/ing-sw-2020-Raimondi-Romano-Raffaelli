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
    public void showGodLikeChoice(List<String> gods) {

    }

    @Override
    public void showGodToSelect(List<String> godLikeGods) {
        
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
    public void removeActionsFromView() {

    }

    @Override
    public void showActionPositions(List<Position> possiblePosition) {

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

    @Override
    public void showGodLikeChooseFirstPlayer() {

    }

    @Override
    public void deactivatePower(){

    }
}
