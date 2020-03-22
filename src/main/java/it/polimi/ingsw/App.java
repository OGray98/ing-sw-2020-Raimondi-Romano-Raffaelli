package it.polimi.ingsw;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Board b = new Board();
        List<Cell> list = b.getAdjacentCells(0, 0);
    }
}
