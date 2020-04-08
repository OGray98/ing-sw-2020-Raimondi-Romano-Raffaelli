package org.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class PositionTest {

    private Position p;

    @Test
    public void isConstructorCorrected() {
        int r, c;

        r = -1;
        c = 0;
        try {
            p = new Position(r, c);
        } catch (InvalidPositionException e) {
            assertEquals("You cannot have a position in : [" + r + "][" + c + "]", e.getMessage());
        }

        r = 3;
        c = 4;
        p = new Position(r, c);

        assertSame(r, p.row);
        assertSame(c, p.col);
    }
}
