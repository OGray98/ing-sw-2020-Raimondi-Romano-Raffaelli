package it.polimi.ingsw.message;

import it.polimi.ingsw.Client.ControllableByServerMessage;
import it.polimi.ingsw.Client.ControllableByViewMessage;
import it.polimi.ingsw.model.player.PlayerIndex;

public class OkMessage extends InformationMessage implements MessageToView{

    public OkMessage(PlayerIndex client, TypeMessage specificOkType, String okMessage) {
        super(client, TypeMessage.OK, specificOkType, okMessage);
    }

    public String getErrorMessage() {
        return super.getString();
    }

    @Override
    public void execute(ControllableByServerMessage controllable){
        if (controllable == null) throw new NullPointerException("controllable");
        controllable.showInformationMessage(this);
    }

    @Override
    public void execute(ControllableByViewMessage controllable) throws NullPointerException {
        if (controllable == null) throw new NullPointerException("controllable");
        if(this.getSpecificType() == TypeMessage.WINNER){
            controllable.showWinner(this);
        }
        if(this.getSpecificType() == TypeMessage.LOSER){
            controllable.showLoser(this);
        }
    }
}
