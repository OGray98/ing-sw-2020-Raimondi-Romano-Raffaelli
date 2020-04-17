package it.polimi.ingsw;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    //Check correctness of Constructor
    @Test
    public void isConstructorCorrected() {
        Position p;
        int r, c;

        r = -1;
        c = 0;
        try {
            new Position(r, c);
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [" + r + "][" + c + "]", e.getMessage());
        }

        r = 3;
        c = 4;
        p = new Position(r, c);

        assertSame(r, p.row);
        assertSame(c, p.col);
    }

    //Check correctness of isAdjacent
    @Test
    public void isIsAdjacentCorrected() {
        Position p = null;
        Position centralPos = new Position(1, 1);

        try {
            centralPos.isAdjacent(p);
        } catch (NullPointerException e) {
            assertEquals("Position", e.getMessage());
        }

        p = new Position(1, 2);
        assertTrue(centralPos.isAdjacent(p));

        p = new Position(2, 3);
        assertFalse(centralPos.isAdjacent(p));
    }

    //Check correctness of equals
    @Test
    public void isEqualsCorrected() {
        Position p1 = null;
        Position p2 = new Position(1, 1);
        assertNotEquals(p2, p1);

        p1 = p2;
        assertEquals(p2, p1);

        p1 = new Position(1, 3);
        assertNotEquals(p1, p2);

        assertNotEquals(p2, 5);

        p1 = new Position(1, 1);
        assertEquals(p1, p2);
    }

    //Check correctness of toString
    @Test
    public void isToStringCorrected() {
        int r = 2, c = 4;
        Position p1 = new Position(r, c);

        assertEquals("Position{row=" + r + ", col=" + c + '}', p1.toString());
    }
}
