package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;

public abstract class ClientViewCreator {

    public abstract ClientView createView(ViewModelInterface clientModel);

}
