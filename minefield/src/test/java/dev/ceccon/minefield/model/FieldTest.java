package dev.ceccon.minefield.model;

import dev.ceccon.minefield.constants.CellState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void fieldInitializesCellsWithCorrectXandY() {
        String description = "Cells on field should have consistent x and y";

        int rows = 3;
        int columns = 3;

        Field field = new Field(rows, columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = field.getCell(i, j);
                assertEquals(i, cell.getX(), description);
                assertEquals(j, cell.getY(), description);
            }
        }
    }

    @Test
    void fieldGetsAllAdjacentsForInnerCell() {
        String description = "Should return 8 adjacents for cell not touching borders";

        int rows = 3;
        int columns = 3;
        // Position (1,1) does not touch any border on a 3x3 zero-indexed grid
        int xInMiddle = 1;
        int yInMiddle = 1;

        Field field = new Field(rows, columns);

        List<Cell> adjacents = field.getAllAdjacentCells(xInMiddle, yInMiddle);

        assertEquals(8, adjacents.size(), description);
    }

    @Test
    void fieldRespectsBordersWhenGettingAdjacents() {
        String description = "Should skip correctly when cell touches a border. Error on: ";

        int rows = 3;
        int columns = 3;
        int expectedWithOneBorder = 5;
        int inTopBorder = 0;
        int inLeftBorder = 0;
        int inMiddle = 1;
        int inRightBorder = 2;
        int inBottomBorder = 2;

        Field field = new Field(rows, columns);

        assertEquals(expectedWithOneBorder,
                field.getAllAdjacentCells(inTopBorder, inMiddle).size(),
                description + "top border");

        assertEquals(expectedWithOneBorder,
                field.getAllAdjacentCells(inBottomBorder, inMiddle).size(),
                description + "bottom border");

        assertEquals(expectedWithOneBorder,
                field.getAllAdjacentCells(inMiddle, inLeftBorder).size(),
                description + "left border");

        assertEquals(expectedWithOneBorder,
                field.getAllAdjacentCells(inMiddle, inRightBorder).size(),
                description + "right border");
    }

    @Test
    void fieldRespectCornersWhenGettingAdjacents() {
        String description = "Should skip correctly when cell touches two borders (corner). Error on: ";

        int rows = 3;
        int columns = 3;
        int expectedWithTwoBorders = 3;
        int inTopBorder = 0;
        int inLeftBorder = 0;
        int inRightBorder = 2;
        int inBottomBorder = 2;

        Field field = new Field(rows, columns);

        assertEquals(expectedWithTwoBorders,
                field.getAllAdjacentCells(inTopBorder, inLeftBorder).size(),
                description + "top left corner");

        assertEquals(expectedWithTwoBorders,
                field.getAllAdjacentCells(inBottomBorder, inLeftBorder).size(),
                description + "bottom left corner");

        assertEquals(expectedWithTwoBorders,
                field.getAllAdjacentCells(inTopBorder, inRightBorder).size(),
                description + "top right corner");

        assertEquals(expectedWithTwoBorders,
                field.getAllAdjacentCells(inTopBorder, inRightBorder).size(),
                description + "bottom right border");
    }

    @Test
    void fieldSetsMineOnRightCell() {
        String description = "Should set mine only on cell specified";

        int rows = 3;
        int columns = 3;
        Integer mineX = 1;
        Integer mineY = 2;

        Field field = new Field(rows, columns);

        field.setMineOn(mineX, mineY);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = field.getCell(i, j);

                if (i == mineX && j == mineY) assertTrue(cell.isMine(), description);
                else assertFalse(cell.isMine(), description);
            }
        }
    }

    @Test
    void fieldInformsAdjancentCellsOfMine() {
        String description = "Should update adjacent mines value on all adjacent cells";

        int rows = 3;
        int columns = 3;
        Integer mineX = 1;
        Integer mineY = 1;

        Field field = new Field(rows, columns);

        field.setMineOn(mineX, mineY);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = field.getCell(i, j);

                if (i == mineX && j == mineY) assertEquals(0, cell.getAdjacentMines(), description);
                else assertEquals(1, cell.getAdjacentMines(), description);
            }
        }
    }

    @Test
    void fieldReturnsMineOnFirstCell() {
        String description = "Should be able to return first cell (top-left corner) if it has mine";

        int rows = 3;
        int columns = 3;
        Integer mineX = 0;
        Integer mineY = 0;

        Field field = new Field(rows, columns);
        field.setMineOn(mineX, mineY);

        List<Cell> withMines = field.getAllCellsContainingMines();

        assertEquals(1, withMines.size(), description);

        Cell mined = withMines.get(0);

        assertEquals(mineX, mined.getX(), description);
        assertEquals(mineY, mined.getY(), description);
    }

    @Test
    void fieldReturnsMineOnLastCell() {
        String description = "Should be able to return last cell (bottom-right corner) if it has mine";

        int rows = 3;
        int columns = 3;
        Integer mineX = 2;
        Integer mineY = 2;

        Field field = new Field(rows, columns);
        field.setMineOn(mineX, mineY);

        List<Cell> withMines = field.getAllCellsContainingMines();

        assertEquals(1, withMines.size(), description);

        Cell mined = withMines.get(0);

        assertEquals(mineX, mined.getX(), description);
        assertEquals(mineY, mined.getY(), description);
    }

    @Test
    void fieldReturnsMultipleCellsWithMines() {
        String description = "Should be able to return all cells that contain mines";

        int rows = 3;
        int columns = 3;
        Integer[] mineXs = {0, 1, 1, 2};
        Integer[] mineYs = {2, 0, 2, 1};

        Field field = new Field(rows, columns);
        field.setMineOn(mineXs[0], mineYs[0]);
        field.setMineOn(mineXs[1], mineYs[1]);
        field.setMineOn(mineXs[2], mineYs[2]);
        field.setMineOn(mineXs[3], mineYs[3]);

        List<Cell> withMines = field.getAllCellsContainingMines();

        assertEquals(mineXs.length, withMines.size(), description);

        // for each x,y position where mines were set, finds the cell with that position on returned list
        for (int i = 0; i < mineXs.length; i++) {
            int current = i;
            assertEquals(1,
                    withMines.stream().filter(c -> (c.getX() == mineXs[current] && c.getY() == mineYs[current])).count(),
                    description);
        }

    }

    @Test
    void hasAnyHiddenCellIdentifiesHidden() {
        String description = "When field has a hidden cell, should return true when asked if has any hidden.";

        int rows = 1;
        int cols = 1;
        Field field = new Field(rows, cols);

        field.getCell(0, 0).setState(CellState.HIDDEN);

        assertTrue(field.hasAnyHiddenCell(), description);
    }

    @Test
    void hasAnyHiddenCellDoesNotTreatOpenAsHidden() {
        String description = "When field has only open cells, should return false when asked if has any hidden.";

        int rows = 1;
        int cols = 1;
        Field field = new Field(rows, cols);

        field.getCell(0, 0).setState(CellState.OPEN);

        assertFalse(field.hasAnyHiddenCell(), description);
    }

    @Test
    void hasAnyHiddenCellDoesNotTreatFlaggedAsHidden() {
        String description = "When field has only flagged cells, should return false when asked if has any hidden.";

        int rows = 1;
        int cols = 1;
        Field field = new Field(rows, cols);

        field.getCell(0, 0).setState(CellState.FLAGGED);

        assertFalse(field.hasAnyHiddenCell(), description);
    }

    @Test
    void hasAnyHiddenCellHandlesNonTrivialCases() {
        String description = "When field has cells of various states, it should identify correctly if any is hidden";

        int rows = 2;
        int cols = 2;
        Field field = new Field(rows, cols);

        field.getCell(0, 0).setState(CellState.OPEN);
        field.getCell(0, 1).setState(CellState.FLAGGED);
        field.getCell(1, 0).setState(CellState.FLAGGED);
        field.getCell(1, 1).setState(CellState.HIDDEN);

        assertTrue(field.hasAnyHiddenCell(), description);

        field.getCell(1, 1).setState(CellState.OPEN);

        assertFalse(field.hasAnyHiddenCell(), description);
    }
}
