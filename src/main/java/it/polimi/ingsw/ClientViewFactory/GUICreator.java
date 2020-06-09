package it.polimi.ingsw.ClientViewFactory;

import it.polimi.ingsw.Client.ClientView;
import it.polimi.ingsw.Client.ViewModelInterface;
import it.polimi.ingsw.view.GUI.GUI;

public class GUICreator extends ClientViewCreator {
    @Override
    public ClientView createView(ViewModelInterface clientModel) {
        return new GUI(clientModel);
    }
}
