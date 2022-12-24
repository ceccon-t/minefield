package dev.ceccon.minefield.model;

import java.util.*;

public class Game {

    private enum GameResult { ONGOING, VICTORY, DEFEAT};

    private Field field;
    private Integer remainingFlags;
    private Integer timeElapsed;
    private boolean playing;
    private GameResult result;

    public Game(Integer numRows, Integer numColumns, Integer totalFlags) {
        this.field = new Field(numRows, numColumns);
        this.remainingFlags = totalFlags;
        this.playing = true;
        this.result = GameResult.ONGOING;
        this.timeElapsed = 0;
    }

    public Field getField() {
        return field;
    }

    public Integer getRemainingFlags() {
        return remainingFlags;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean p) {
        playing = p;
    }

    public void tickTime() {
        timeElapsed += 1;
    }

    public Integer getTimeElapsed() {
        return timeElapsed;
    }

    public List<Cell> getAllMines() {
        return field.getAllCellsContainingMines();
    }

    public boolean didPlayerWin() {
        return result.equals(GameResult.VICTORY);
    }

    public boolean didPlayerLose() {
        return result.equals(GameResult.DEFEAT);
    }

    private Set<Cell> openClearingAround(Cell start) {
        // TODO: Add tests for this method
        Queue<Cell> toOpen = new LinkedList<>();
        Set<Cell> processed = new HashSet<>();

        toOpen.add(start);

        do {
            Cell current = toOpen.remove();
            if (processed.contains(current)) continue;
            processed.add(current);

            if (current.getState().equals(CellState.FLAGGED)) continue;

            current.setState(CellState.OPEN);

            if (current.getAdjacentMines() == 0) {
                toOpen.addAll(field.getAllAdjacentCells(current.getX(), current.getY()));
            }
        } while(!toOpen.isEmpty());

        return processed;
    }

    public Set<Cell> attemptOpeningFromCell(int x, int y) {
        Set<Cell> allOpen = new HashSet<>();

        Cell start = field.getCell(x, y);
        if (start.isMine()) {
            playing = false;
            result = GameResult.DEFEAT;
            return allOpen;
        }

        if (start.getState().equals(CellState.FLAGGED)) {
            remainingFlags += 1;
            start.setState(CellState.HIDDEN); // "Unflagging" so it is processed correctly below
        }

        allOpen = openClearingAround(start);

        if (!field.hasAnyHiddenCell()) {
            playing = false;
            result = GameResult.VICTORY;
        }

        return allOpen;
    }

    public boolean attemptFlaggingCell(int x, int y) {
        boolean changedState = false;
        Cell cell = field.getCell(x, y);

        if (cell.getState().equals(CellState.HIDDEN)) {
            if (remainingFlags < 1) return changedState;

            remainingFlags -= 1;
            cell.setState(CellState.FLAGGED);
            changedState = true;

            if (!field.hasAnyHiddenCell()) {
                playing = false;
                result = GameResult.VICTORY;
            }

        } else if (cell.getState().equals(CellState.FLAGGED)) {

            remainingFlags += 1;
            cell.setState(CellState.HIDDEN);
            changedState = true;

        }

        return changedState;
    }

}
