package dev.ceccon.minefield.model;

import dev.ceccon.minefield.constants.CellState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void getX() {
        String description = "Cell should return correct x on getter";

        Integer x = 3;
        Cell c = new Cell(x, 0);

        assertEquals(x, c.getX(), description);
    }

    @Test
    void getY() {
        String description = "Cell should return correct y on getter";

        Integer y = 4;
        Cell c = new Cell(0, y);

        assertEquals(y, c.getY(), description);
    }

    @Test
    void state() {
        String description = "Cell should allow to set state and return correctly";

        CellState state = CellState.FLAGGED;
        Cell c = new Cell(0, 0);

        c.setState(state);

        assertEquals(state, c.getState(), description);
    }

    @Test
    void isolatedCellHasNoAdjacentMines() {
        String description = "Cell by itself should have no adjacent mines";

        Cell c = new Cell(0, 0);

        assertEquals(0, c.getAdjacentMines(), description);
    }

    @Test
    void cellIncrementAdjacentMines() {
        String description = "Cell should increment adjacent mines by 1";

        Cell c = new Cell(0, 0);

        Integer initialAdjacentMines = c.getAdjacentMines();

        c.incrementAdjacentMines();

        Integer finalAdjacentMines = c.getAdjacentMines();

        assertEquals(initialAdjacentMines + 1, finalAdjacentMines, description);
    }

    @Test
    void cellCanBeSetAsMine() {
        String description = "Cell should be able to be set as mine";

        Cell c = new Cell(0, 0);

        assertFalse(c.isMine(), "Cell should start without mine");

        c.setMine();

        assertTrue(c.isMine(), description);
    }

    @Test
    void cellsAreEqualIfSameXandY() {
        String description = "Cells should be considered equal if they have same x and y values";

        Integer x1 = 3;
        Integer x2 = 3;
        Integer y1 = 5;
        Integer y2 = 5;

        Cell c1 = new Cell(x1, y1);
        Cell c2 = new Cell(x2, y2);

        assertTrue(c1.equals(c2), description);
        assertTrue(c2.equals(c1), description);

        Integer x3 = 3;
        Integer y3 = 7;
        Cell c3 = new Cell(x3, y3);

        assertFalse(c1.equals(c3), description);
        assertFalse(c2.equals(c3), description);
        assertFalse(c3.equals(c1), description);
        assertFalse(c3.equals(c2), description);
    }
}