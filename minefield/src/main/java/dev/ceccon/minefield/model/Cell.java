package dev.ceccon.minefield.model;

import java.util.Objects;

public class Cell {

    private Integer x;
    private Integer y;
    private CellState state;
    private boolean hasMine;
    private Integer adjacentMines;

    public Cell(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.state = CellState.HIDDEN;
        this.adjacentMines = 0;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState newState) {
        this.state = newState;
    }

    public void setMine() {
        this.hasMine = true;
    }

    public boolean isMine() {
        return hasMine;
    }

    public Integer getAdjacentMines() {
        return adjacentMines;
    }

    public void incrementAdjacentMines() {
        this.adjacentMines += 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell other = (Cell) o;
        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
