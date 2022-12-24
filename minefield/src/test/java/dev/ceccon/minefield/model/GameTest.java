package dev.ceccon.minefield.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

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

    @Test
    void canOpenHiddenCell() {
        int cellX = 0;
        int cellY = 0;

        Game game = new Game(2, 2, 2);

        Cell cell = game.getField().getCell(cellX, cellY);
        assertEquals(CellState.HIDDEN, cell.getState(), "Test was expecting cell to start hidden");

        Set<Cell> allOpen = game.attemptOpeningFromCell(cellX, cellY);

        cell = game.getField().getCell(cellX, cellY);
        assertEquals(CellState.OPEN, cell.getState(), "Should open hidden cell that had no mines on");

        assertTrue(allOpen.contains(cell), "Should return opened cell");
    }

    @Test
    void openingFlaggedCellIncreasesRemainingFlags() {
        int cellX = 1;
        int cellY = 0;
        int mineX = 0;
        int mineY = 0;
        int totalFlags = 1;

        Game game = new Game(2, 2, totalFlags);

        // Setting a nearby mine
        game.getField().setMineOn(mineX, mineY);

        game.attemptFlaggingCell(cellX, cellY);

        int remainingAfterFlagging = game.getRemainingFlags();
        assertEquals(totalFlags-1, remainingAfterFlagging, "Should have decreased flags after flagging");

        game.attemptOpeningFromCell(cellX, cellY);

        int remainingAfterOpening = game.getRemainingFlags();
        assertEquals(remainingAfterFlagging+1, remainingAfterOpening, "Should have increased remaining flags after opening a flagged cell");
    }

    @Test
    void openingFinalCellLeadsToWin() {
        int cellX = 1;
        int cellY = 0;

        Game game = new Game(2, 2, 0);

        // Make all other cells open behind the scenes
        game.getField().getCell(0, 0).setState(CellState.OPEN);
        game.getField().getCell(0, 1).setState(CellState.OPEN);
        game.getField().getCell(1, 1).setState(CellState.OPEN);

        assertTrue(game.isPlaying(), "Game is still playing while there are cells not deal with");
        assertFalse(game.didPlayerWin(), "Player should not have won before dealing with all cells");

        game.attemptOpeningFromCell(cellX, cellY);

        assertFalse(game.isPlaying(), "Game should stop playing after opening last cell");
        assertTrue(game.didPlayerWin(), "Player should have won after opening last cell not dealt with");
    }

    @Test
    void openingCellWithMineLeadsToDefeat() {
        int mineX = 0;
        int mineY = 0;
        int totalFlags = 1;

        Game game = new Game(2, 2, totalFlags);

        game.getField().setMineOn(mineX, mineY);

        assertTrue(game.isPlaying(), "Test expected game to be playing before opening cell with mine");
        assertFalse(game.didPlayerLose(), "Test did not expect player to have already lost before opening cell with mine");

        game.attemptOpeningFromCell(mineX, mineY);

        assertFalse(game.isPlaying(), "Game should no longer be playing after opening cell with mine");
        assertTrue(game.didPlayerLose(), "Player should have lost after opening cell with mine");
    }

}