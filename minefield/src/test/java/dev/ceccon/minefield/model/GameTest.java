package dev.ceccon.minefield.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void canFlagAndUnflagCell() {
        int flagX = 0;
        int flagY = 0;

        Game game = new Game(2, 2, 2);

        int initialFlags = game.getRemainingFlags();
        boolean flaggingChangedState = game.attemptFlaggingCell(flagX, flagY);

        assertTrue(flaggingChangedState, "Flagging hidden cell should change its state");

        int remainingFlags = game.getRemainingFlags();

        assertEquals(initialFlags-1, remainingFlags, "Should decrement number of remaining flags after successful flagging.");

        boolean unflaggingChangedState = game.attemptFlaggingCell(flagX, flagY);

        assertTrue(unflaggingChangedState, "Unflagging flagged cell should change its state");

        remainingFlags = game.getRemainingFlags();

        assertEquals(initialFlags, remainingFlags, "Should increase remaining flags after unflagging");

    }

    @Test
    void cannotFlagOpenCell() {
        int flagX = 0;
        int flagY = 0;

        Game game = new Game(2, 2, 2);

        int initialFlags = game.getRemainingFlags();

        Field field = game.getField();
        field.getCell(flagX, flagY).setState(CellState.OPEN);

        boolean flaggingChangedState = game.attemptFlaggingCell(flagX, flagY);

        assertFalse(flaggingChangedState, "Should not allow to flag open cell");

        int remainingFlags = game.getRemainingFlags();

        assertEquals(initialFlags, remainingFlags, "Should not decrement remaining flags if unsuccessful flagging");

    }

    @Test
    void cannotFlagIfNoRemainingFlags() {
        int flagX = 0;
        int flagY = 0;
        int initialFlags = 0;

        Game game = new Game(2, 2, initialFlags);

        boolean flaggingChangedStatus = game.attemptFlaggingCell(flagX, flagY);

        assertFalse(flaggingChangedStatus, "Should not change status of cell if no remaining flags");

        int remainingFlags = game.getRemainingFlags();

        assertEquals(initialFlags, remainingFlags, "Should not change number of remaining flags with unsuccessful flagging");
    }

    @Test
    void canWinByFlaggingFinalCell() {
        Game game = new Game(2, 2, 4);

        game.attemptFlaggingCell(0, 0);
        game.attemptFlaggingCell(0, 1);
        game.attemptFlaggingCell(1, 0);

        boolean playing = game.isPlaying();
        assertTrue(playing, "Game keeps playing until all cells are dealt with");
        boolean playerWon = game.didPlayerWin();
        assertFalse(playerWon, "Player should not have won until all cells are dealt with");

        game.attemptFlaggingCell(1, 1);

        playing = game.isPlaying();
        assertFalse(playing, "Game should no longer be playing after all cells dealt with");
        playerWon = game.didPlayerWin();
        assertTrue(playerWon, "Player should have won after dealing with final cell");

        boolean playerLost = game.didPlayerLose();
        assertFalse(playerLost, "Player should not have lost if successfully handled all cells");

    }

}