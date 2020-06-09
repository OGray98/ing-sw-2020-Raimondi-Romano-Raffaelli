package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.view.CLI.CLI;

public class CLICreator extends ClientViewCreator {
    @Override
    public ClientView createView(ViewModelInterface clientModel) {
        return new CLI(clientModel);
    }
}
