package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Position;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AthenaDecoratorTest {

    private static Board board;
    private static PlayerInterface athenaPlayer;
    private static Position workerPosition;
    private static Player enemy1;
    private static Player enemy2;

    @Before
    public void init(){
        board = new Board();
        workerPosition = new Position(2,2);
        List<Player> enemyPlayers = new ArrayList<>();
        enemy1 = new Player(board, "enemy1", 0);
        enemy2 = new Player(board, "enemy2", 2);
        enemyPlayers.add(enemy1);
        enemyPlayers.add(enemy2);
        athenaPlayer = new AthenaDecorator(new Player(board, "Athena", 1), enemyPlayers);
    }

    @Test
    public void athenaMoveWorkerTest(){
        athenaPlayer.putWorker(workerPosition, 1);
        athenaPlayer.setSelectedWorker(1);
        //set enemy players workers
        Position enemy1start = new Position(0,4);
        Position enemy2start = new Position(1,2);
        enemy1.putWorker(enemy1start, 0);
        enemy1.setSelectedWorker(0);
        enemy2.putWorker(enemy2start, 1);
        enemy2.setSelectedWorker(1);
        Position enemy1lvlup = new Position(1,4);
        Position enemy2lvlup = new Position(1,1);
        Position enemy1samelvl = new Position(0,3);
        Position enemy2samelvl = new Position(2,1);
        board.updateBoardBuild(enemy1lvlup);
        board.updateBoardBuild(enemy2lvlup);

        /* starting board (A is athena, e1 is enemy1, e2 is enemy2):
        *    0     0     0     0     0-e1
        *    0     1     0-e2  0     1
        *    0     0     0-A   0     0
        *    0     0     0     0     0
        *    0     0     0     0     0
        * */

        //move on the same level
        Position pos23 = new Position(2,3);
        assertTrue(athenaPlayer.canMove(pos23));
        athenaPlayer.moveWorker(pos23);

        //enemy players can move up
        assertTrue(enemy1.canMove(enemy1samelvl));
        assertTrue(enemy1.canMove(enemy1lvlup));
        assertTrue(enemy2.canMove(enemy2samelvl));
        assertTrue(enemy2.canMove(enemy2lvlup));

        //increment level of pos22 to make a move to a level up for athena
        board.updateBoardBuild(workerPosition);
        //athena make a move to a level up
        athenaPlayer.moveWorker(workerPosition);

        //enemy player can not move up now
        assertTrue(enemy1.canMove(enemy1samelvl));
        assertFalse(enemy1.canMove(enemy1lvlup));
        assertTrue(enemy2.canMove(enemy2samelvl));
        assertFalse(enemy2.canMove(enemy2lvlup));

        //athena make a new move up
        athenaPlayer.moveWorker(pos23);
        athenaPlayer.moveWorker(workerPosition);

        //check if players are still blocked
        assertTrue(enemy1.canMove(enemy1samelvl));
        assertFalse(enemy1.canMove(enemy1lvlup));
        assertTrue(enemy2.canMove(enemy2samelvl));
        assertFalse(enemy2.canMove(enemy2lvlup));

        //athena make a move (not a level up one)
        athenaPlayer.moveWorker(pos23);

        //check if players are now free to move up
        assertTrue(enemy1.canMove(enemy1samelvl));
        assertTrue(enemy1.canMove(enemy1lvlup));
        assertTrue(enemy2.canMove(enemy2samelvl));
        assertTrue(enemy2.canMove(enemy2lvlup));
    }

    @Test
    public void blockedWorkersTest(){
        athenaPlayer.putWorker(new Position(4,0), 0);
        athenaPlayer.putWorker(new Position(4,3), 1);
        athenaPlayer.setSelectedWorker(1);
        //set enemy players workers
        Position enemy1start = new Position(0,4);
        Position enemy2start = new Position(1,2);
        enemy1.putWorker(enemy1start, 0);
        enemy1.setSelectedWorker(0);
        enemy2.putWorker(enemy2start, 1);
        enemy2.setSelectedWorker(1);
        Position enemy1lvlup = new Position(1,4);
        Position enemy2lvlup = new Position(1,1);
        Position enemy1samelvl = new Position(0,3);
        Position enemy2samelvl = new Position(2,1);
        board.updateBoardBuild(enemy1lvlup);
        board.updateBoardBuild(enemy2lvlup);
        Position pos44 = new Position(4,4);
        board.updateBoardBuild(pos44);

        /* starting board (A is athena, e1 is enemy1, e2 is enemy2):
         *    0     0     0     0     0-e1
         *    0     1     0-e2  0     1
         *    0     0     0     0     0
         *    0     0     0     0     0
         *    A     0     0     A     1
         * */

        //athena make a move to a level up
        athenaPlayer.moveWorker(pos44);
        assertFalse(enemy1.canMove(enemy1lvlup));
        assertFalse(enemy2.canMove(enemy2lvlup));

        //block athena workers and activate a lose condition for the athena player
        //block worker 1
        board.UpdateBoardBuildDome(new Position(4,3));
        board.UpdateBoardBuildDome(new Position(3,3));
        board.UpdateBoardBuildDome(new Position(3,4));

        //block worker 0
        board.UpdateBoardBuildDome(new Position(4,1));
        board.UpdateBoardBuildDome(new Position(3,0));
        board.UpdateBoardBuildDome(new Position(3,1));

        assertEquals(athenaPlayer.blockedWorkers().size(), 2);

        //check that other players, who where blocked are now free
        assertTrue(enemy1.canMove(enemy1samelvl));
        assertTrue(enemy1.canMove(enemy1lvlup));
        assertTrue(enemy2.canMove(enemy2samelvl));
        assertTrue(enemy2.canMove(enemy2lvlup));
    }
}
