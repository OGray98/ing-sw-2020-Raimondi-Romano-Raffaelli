package it.polimi.ingsw.Client;

import it.polimi.ingsw.ClientViewFactory.CLICreator;
import it.polimi.ingsw.ClientViewFactory.GUICreator;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ViewCreatorTest {

    private final ViewModelInterface modelInterface = new ClientModel();

    @Test
    public void CLICreatorTest() {
        CLICreator creator = new CLICreator();
        assertNotNull(creator.createView(modelInterface));
    }

    @Test
    public void GUICreatorTest() {
        GUICreator creator = new GUICreator();
        assertNotNull(creator.createView(modelInterface));
    }
}
