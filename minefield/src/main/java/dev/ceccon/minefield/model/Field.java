package dev.ceccon.minefield.model;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private Integer rows;
    private Integer columns;
    private Cell[][] cells;

    public Field(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        buildCellMatrix();
    }

    private void buildCellMatrix() {
        this.cells = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(Integer x, Integer y) {
        return cells[x][y];
    }

    public List<Cell> getAllAdjacentCells(Integer x, Integer y) {
        List<Cell> adjacents = new ArrayList<>();

        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (
                    (i == x && j == y)   // same cell
                    || (i < 0 || i >= rows) // outside of vertical border
                    || (j < 0 || j >= columns) // outside of horizontal border
                ) continue;

                adjacents.add(cells[i][j]);
            }
        }

        return adjacents;
    }

    public void setMineOn(Integer x, Integer y) {
        Cell cell = cells[x][y];
        cell.setMine();
        for (Cell adjacent : getAllAdjacentCells(x, y)) adjacent.incrementAdjacentMines();
    }

    public List<Cell> getAllCellsContainingMines() {
        List<Cell> withMines = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j].isMine()) withMines.add(cells[i][j]);
            }
        }

        return withMines;
    }

    public boolean hasAnyHiddenCell() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j].getState().equals(CellState.HIDDEN)) return true;
            }
        }
        return false;
    }

}
