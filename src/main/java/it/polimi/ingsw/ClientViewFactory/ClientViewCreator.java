package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;

public abstract class ClientViewCreator {

    /**
     * @return the correct type of view gui or cli()
     * @param clientModel indicates the type of ClientView wanted
     * */
    public abstract ClientView createView(ViewModelInterface clientModel);

}
