package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.CellState;
import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.model.Cell;
import dev.ceccon.minefield.model.Field;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

import java.util.Random;

public class Controller implements PlayerActionHandler {

    private static final int TOTAL_FLAGS = 10;

    private int numRows;
    private int numCols;

    private int totalFlags;
    private int remainingFlags;
    private int score = 0;

    private boolean playing = true;

    private Difficulty currentDifficulty = Difficulty.INTERMEDIATE;

    private Field field;

    private IOEngine ioEngine;

    public Controller() {
        configureForCurrentDifficulty();
        remainingFlags = totalFlags;

        createField();

        this.ioEngine = IOEngineFactory.buildEngine(numRows, numCols, totalFlags, this, IOEngines.DEFAULT_ENGINE);
    }

    private void createField() {
        this.field = new Field(numRows, numCols);
        seedFieldWithMines();
    }

    private void seedFieldWithMines() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (i == j) field.setMineOn(i, j);  // Diagonal mines for debug
            }
        }
    }

    private void configureForCurrentDifficulty() {
        switch (currentDifficulty) {
            case BEGINNER:
                numRows = 10;
                numCols = 15;
                totalFlags = 15;
                break;
            case INTERMEDIATE:
                numRows = 15;
                numCols = 20;
                totalFlags = 50;
                break;
            case EXPERT:
                numRows = 15;
                numCols = 25;
                totalFlags = 90;
                break;
        }
    }

    private void updateCellDisplay(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();

        switch (cell.getState()) {
            case HIDDEN:
                ioEngine.displayAsHidden(x, y);
                break;
            case OPEN:
                ioEngine.displayAsOpen(x, y, cell.getAdjacentMines());
                break;
            case FLAGGED:
                ioEngine.displayAsFlagged(x, y);
        }
    }

    private void showAllMines() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Cell cell = field.getCell(i, j);
                if (cell.isMine()) ioEngine.displayAsMine(i, j);
            }
        }
    }

    @Override
    public void handlePlayerActionOnCell(PlayerAction action, int x, int y) {
        if (!playing) return;

        Cell cell = field.getCell(x, y);
        switch (action) {
            case ACTION_OPEN:
                if (cell.isMine()) {
                    showAllMines();
                    ioEngine.displayDefeatMessage();
                    playing = false;
                    return;
                }
                // TODO: handle checking if player won
                // TODO: handle opening flagged cell
                cell.setState(CellState.OPEN);
                updateCellDisplay(field.getCell(x, y));
                break;
            case ACTION_FLAG:
                if (remainingFlags < 1) return;
                if (cell.getState().equals(CellState.FLAGGED)) {
                    remainingFlags++;
                    cell.setState(CellState.HIDDEN);
                    updateCellDisplay(cell);
                    ioEngine.displayRemainingFlagsMessage(remainingFlags, totalFlags);
                } else if (cell.getState().equals(CellState.HIDDEN)) {
                    remainingFlags--;
                    cell.setState(CellState.FLAGGED);
                    updateCellDisplay(cell);
                    ioEngine.displayRemainingFlagsMessage(remainingFlags, totalFlags);
                }
                break;
        }
    }

    @Override
    public void handlePlayerRestart() {
        restart();
    }

    private void restart() {
        remainingFlags = totalFlags;
        score = 0;
        playing = true;

        createField();

        ioEngine.displayRemainingFlagsMessage(remainingFlags, totalFlags);
        ioEngine.restartUI();
    }

    @Override
    public void handlePlayerChangeDifficulty(Difficulty difficulty) {
        if (difficulty == currentDifficulty) return;

        currentDifficulty = difficulty;
        configureForCurrentDifficulty();

        ioEngine.changeBoardSize(numRows, numCols);
        restart();
    }


}
