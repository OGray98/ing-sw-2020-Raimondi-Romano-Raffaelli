package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PositionContainerTest {

    private static PositionContainer positionContainer;
    private static Position pos1;
    private static Position pos2;

    @Before
    public void init() {
        positionContainer = new PositionContainer();
        pos1 = new Position(1, 2);
        pos2 = new Position(3, 4);
    }

    @Test
    public void isConstructorWithPositionCorrected() {
        positionContainer = new PositionContainer(pos1);
        assertEquals(pos1, positionContainer.getOccupiedPosition());
        assertNull(positionContainer.getOldPosition());

        try {
            positionContainer = new PositionContainer(null);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }

    }

    @Test
    public void isPutCorrected() {


        assertNull(positionContainer.getOldPosition());
        assertNull(positionContainer.getOccupiedPosition());

        positionContainer.put(pos1);

        assertEquals(pos1, positionContainer.getOccupiedPosition());
        assertNull(positionContainer.getOldPosition());

        positionContainer.put(pos2);
        assertEquals(pos2, positionContainer.getOccupiedPosition());
        assertEquals(pos1, positionContainer.getOldPosition());

        try {
            positionContainer.put(null);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }
    }

}
