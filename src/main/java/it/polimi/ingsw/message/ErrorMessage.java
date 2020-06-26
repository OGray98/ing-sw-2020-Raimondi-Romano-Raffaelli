package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

/**
 * Message used to notify an error to the view
 * */
public class ErrorMessage extends InformationMessage implements MessageToView {

    public ErrorMessage(PlayerIndex client, TypeMessage specificErrorType, String errorMessage) {
        super(client, TypeMessage.ERROR, specificErrorType, errorMessage);
    }

    public String getErrorMessage() {
        return super.getString();
    }

    public TypeMessage getSpecificErrorType() {
        return super.getSpecificType();
    }

    @Override
    public void execute(ControllableByServerMessage controllable){
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.showInformationMessage(this);
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        if(getSpecificErrorType() == TypeMessage.NICKNAME){
            controllable.reinsertNickname();
        }
    }
}
