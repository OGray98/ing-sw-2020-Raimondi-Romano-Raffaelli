package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.board.PositionContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionContainerTest {

    private static PositionContainer positionContainer;
    private static Position pos1;
    private static Position pos2;

    @Before
    public void init() {
        pos1 = new Position(1, 2);
        pos2 = new Position(3, 4);
    }

    @Test
    public void isConstructorWithPositionCorrected() {
        positionContainer = new PositionContainer(pos1);
        assertEquals(pos1, positionContainer.getOccupiedPosition());
        assertEquals(new Position(0, 0), positionContainer.getOldPosition());

        try {
            positionContainer = new PositionContainer(null);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }

    }

    @Test
    public void isPutCorrected() {

        positionContainer = new PositionContainer(pos1);

        assertEquals(pos1, positionContainer.getOccupiedPosition());
        assertEquals(new Position(0, 0), positionContainer.getOldPosition());

        PositionContainer posCont = new PositionContainer(pos1);
        assertEquals(posCont, positionContainer);


        positionContainer.put(pos2);
        assertEquals(pos2, positionContainer.getOccupiedPosition());
        assertEquals(pos1, positionContainer.getOldPosition());


        try {
            positionContainer.put(null);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }
    }

    @Test
    public void isToStringCorrected() {
        positionContainer = new PositionContainer(pos2);
        positionContainer.put(pos1);

        assertEquals("PositionContainer{ oldPosition = " + pos2 + ", newPosition = " + pos1 + "}",
                positionContainer.toString());
    }

}
