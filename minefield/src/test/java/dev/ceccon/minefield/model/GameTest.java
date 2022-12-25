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

    @Test
    void opensEntireUninhibitedArea() {
        int numRows = 3;
        int numColumns = 3;
        int totalFlags = 0;
        int openX = 1;
        int openY = 1;

        Game game = new Game(numRows, numColumns, totalFlags);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                assertEquals(CellState.HIDDEN, game.getField().getCell(i, j).getState(), "Test expected all cells to start hidden");
            }
        }

        Set<Cell> allOpen = game.attemptOpeningFromCell(openX, openY);

        // Checks underlying state
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                assertEquals(CellState.OPEN, game.getField().getCell(i, j).getState(), "Cell in uninhibited area should have been open automatically");
            }
        }

        // Checks return from method
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                assertTrue(allOpen.contains(game.getField().getCell(i, j)), "Cell opened automatically should have been present on returned collection");
            }
        }

    }

    @Test
    void openingStopsAtMine() {
        int numRows = 5;
        int numColumns = 6;
        int totalFlags = 1;
        int openX = 2;
        int openY = 1;
        int mineX = 2;
        int mineY = 3;

        Game game = new Game(numRows, numColumns, totalFlags);

        game.getField().setMineOn(mineX, mineY);

        Set<Cell> allOpen = game.attemptOpeningFromCell(openX, openY);

        assertEquals(CellState.HIDDEN, game.getField().getCell(mineX, mineY).getState(), "Should not have opened cell with mine");

        assertFalse(allOpen.contains(game.getField().getCell(mineX, mineY)), "Should not have returned cell with mine along with opened ones");

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == mineX && j == mineY) continue;

                Cell current = game.getField().getCell(i, j);
                assertEquals(CellState.OPEN, current.getState(), "Cell in uninhibited area should have been open automatically");
                assertTrue(allOpen.contains(current), "Cell opened automatically should have been present on returned collection");
            }
        }
    }

    @Test
    void openingStopsAtFlag() {
        int numRows = 5;
        int numColumns = 6;
        int totalFlags = 1;
        int openX = 2;
        int openY = 1;
        int flagX = 2;
        int flagY = 3;

        Game game = new Game(numRows, numColumns, totalFlags);

        game.attemptFlaggingCell(flagX, flagY);

        Set<Cell> allOpen = game.attemptOpeningFromCell(openX, openY);

        assertEquals(CellState.FLAGGED, game.getField().getCell(flagX, flagY).getState(), "Should not have opened cell with flag");

        assertFalse(allOpen.contains(game.getField().getCell(flagX, flagY)), "Should not have returned cell with flag along with opened ones");

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (i == flagX && j == flagY) continue;

                Cell current = game.getField().getCell(i, j);
                assertEquals(CellState.OPEN, current.getState(), "Cell in uninhibited area should have been open automatically");
                assertTrue(allOpen.contains(current), "Cell opened automatically should have been present on returned collection");
            }
        }
    }

    @Test
    void openingStopsAtBorderOfMine() {
        int numRows = 4;
        int numColumns = 4;
        int totalFlags = 1;
        int openX = 2;
        int openY = 0;
        int mineX = 2;
        int mineY = 2;
        int[] openCellsXs = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 3, 3};
        int[] openCellsYs = {0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 0, 1};
        int[] hiddenCellsXs = {2, 2, 3, 3};
        int[] hiddenCellsYs = {2, 3, 2, 3};

        //  Models board below, with O = Open, X = "clicked", M = Mine (must remain hidden), H = Hidden
        //  -----------------
        //  | O | O | O | O |
        //  | O | O | O | O |
        //  | X | O | M | H |
        //  | O | O | H | H |
        //  -----------------

        Game game = new Game(numRows, numColumns, totalFlags);

        game.getField().setMineOn(mineX, mineY);

        Set<Cell> allOpen = game.attemptOpeningFromCell(openX, openY);

        // Check cells that should be open
        for (int i = 0; i < openCellsXs.length; i++) {
            Cell current = game.getField().getCell(openCellsXs[i], openCellsYs[i]);
            assertEquals(CellState.OPEN, current.getState(), "Cell in uninhibited area should have been open automatically");
            assertTrue(allOpen.contains(current), "Cell opened automatically should have been present on returned collection");
        }

        // Check cells that should be hidden
        for (int i = 0; i < hiddenCellsXs.length; i++) {
            Cell current = game.getField().getCell(hiddenCellsXs[i], hiddenCellsYs[i]);
            assertEquals(CellState.HIDDEN, current.getState(), "Cell should not have been opened automatically");
            assertFalse(allOpen.contains(current), "Should not have returned hidden cell along with opened ones");
        }
    }

}