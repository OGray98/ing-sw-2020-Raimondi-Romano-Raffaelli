package org.example;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class BoardChangesTest {
    private static BoardChange boardChange;
    private static PlayerIndex playerIndex;
    private static boolean cantGoUp;
    private static Position buildPosition;
    private static BuildType buildType;
    private static Position oldPos;
    private static Position newPos;


    @Test
    public void isConstructorWithCantGoUpCorrected() {
        cantGoUp = false;
        boardChange = new BoardChange(cantGoUp);

        try {
            boardChange.getChanges();
        } catch (NullPointerException e) {
            assertEquals("playerChanges", e.getMessage());
        }

        try {
            boardChange.getPositionBuild();
        } catch (NullPointerException e) {
            assertEquals("positionBuild", e.getMessage());
        }
        assertEquals(BuildType.LEVEL, boardChange.getBuildType());
        assertFalse(boardChange.getCantGoUp());

        assertFalse(boardChange.isCantGoUpNull());
        assertTrue(boardChange.isPlayerChangesNull());
        assertTrue(boardChange.isPositionBuildNull());
    }

    @Test
    public void isConstructorWithBuildPositionCorrected() {
        buildPosition = new Position(1, 2);
        buildType = BuildType.LEVEL;
        boardChange = new BoardChange(buildPosition, buildType);

        try {
            boardChange.getChanges();
        } catch (NullPointerException e) {
            assertEquals("playerChanges", e.getMessage());
        }

        assertEquals(buildPosition, boardChange.getPositionBuild());

        assertEquals(buildType, boardChange.getBuildType());
        try {
            boardChange.getCantGoUp();
        } catch (NullPointerException e) {
            assertEquals("cantGoUp", e.getMessage());
        }

        try {
            boardChange = new BoardChange(null, buildType);
        } catch (NullPointerException e) {
            assertEquals("buildPosition", e.getMessage());
        }

        assertTrue(boardChange.isCantGoUpNull());
        assertTrue(boardChange.isPlayerChangesNull());
        assertFalse(boardChange.isPositionBuildNull());

        try {
            new BoardChange(null, BuildType.LEVEL);
        } catch (NullPointerException e) {
            assertEquals("buildPosition", e.getMessage());
        }
    }

    @Test
    public void isConstructorWithPlayerChangesCorrected() {
        playerIndex = PlayerIndex.PLAYER0;
        oldPos = new Position(1, 2);
        newPos = new Position(2, 4);

        boardChange = new BoardChange(oldPos, newPos, playerIndex);
        Map<PositionContainer, PlayerIndex> changesPlayer = boardChange.getChanges();

        PositionContainer posCont = new PositionContainer(oldPos);
        posCont.put(newPos);

        assertEquals(1, changesPlayer.size());
        boolean thereIs = false;
        for (Map.Entry<PositionContainer, PlayerIndex> entry : changesPlayer.entrySet()) {
            if (entry.getKey().equals(posCont)) {
                thereIs = true;
                break;
            }
        }
        assertTrue(thereIs);

        try {
            boardChange.getCantGoUp();
        } catch (NullPointerException e) {
            assertEquals("cantGoUp", e.getMessage());
        }

        try {
            boardChange.getPositionBuild();
        } catch (NullPointerException e) {
            assertEquals("positionBuild", e.getMessage());
        }

        assertEquals(BuildType.LEVEL, boardChange.getBuildType());

        try {
            boardChange = new BoardChange(null, newPos, playerIndex);
        } catch (NullPointerException e) {
            assertEquals("oldPosition", e.getMessage());
        }

        try {
            boardChange = new BoardChange(oldPos, null, playerIndex);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }

        assertTrue(boardChange.isCantGoUpNull());
        assertFalse(boardChange.isPlayerChangesNull());
        assertTrue(boardChange.isPositionBuildNull());

        try {
            new BoardChange(null, newPos, PlayerIndex.PLAYER2);
        } catch (NullPointerException e) {
            assertEquals("oldPosition", e.getMessage());
        }

        try {
            new BoardChange(newPos, null, PlayerIndex.PLAYER2);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }
    }

    @Test
    public void isAddPlayerChangesCorrected() {
        playerIndex = PlayerIndex.PLAYER0;
        oldPos = new Position(1, 2);
        newPos = new Position(2, 4);

        PlayerIndex index = PlayerIndex.PLAYER1;
        Position oldPos2 = new Position(1, 4);
        Position newPos2 = new Position(0, 3);
        boardChange = new BoardChange(false);
        boardChange.addPlayerChanges(oldPos, newPos, playerIndex);
        boardChange.addPlayerChanges(oldPos2, newPos2, index);

        Map<PositionContainer, PlayerIndex> changesPlayer = boardChange.getChanges();

        PositionContainer posCont2 = new PositionContainer(oldPos2);
        posCont2.put(newPos2);

        assertEquals(2, changesPlayer.size());
        boolean thereIs = false;
        for (Map.Entry<PositionContainer, PlayerIndex> entry : changesPlayer.entrySet()) {
            if (entry.getKey().equals(posCont2)) {
                thereIs = true;
                break;
            }
        }
        assertTrue(thereIs);

        try {
            boardChange.addPlayerChanges(oldPos2, newPos2, index);
        } catch (SameMovementException e) {
            assertEquals("There is more thaan one movement with" + posCont2.toString(), e.getMessage());
        }
        try {
            boardChange.addPlayerChanges(oldPos, null, playerIndex);
        } catch (NullPointerException e) {
            assertEquals("newPosition", e.getMessage());
        }
        try {
            boardChange.addPlayerChanges(null, newPos, playerIndex);
        } catch (NullPointerException e) {
            assertEquals("oldPosition", e.getMessage());
        }
    }

}

