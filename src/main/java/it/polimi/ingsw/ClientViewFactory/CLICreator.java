package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;

public class CLICreator extends ClientViewCreator {
    @Override
    public ClientView createView(ViewModelInterface clientModel) {
        return new CLI(clientModel);
    }
}
