package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.CellState;
import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.model.*;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

import java.util.*;

public class Controller implements PlayerActionHandler {

    private static final int TOTAL_FLAGS = 10;

    private int remainingFlags;
    private int score = 0;

    private boolean playing = true;

    private Difficulty currentDifficulty = Difficulty.BEGINNER;
    private DifficultyConfiguration difficultyConfig;

    private MineSeeder mineSeeder;

    private Field field;

    private IOEngine ioEngine;

    public Controller() {
        configureForCurrentDifficulty();
        remainingFlags = difficultyConfig.totalFlags();
        mineSeeder = new SimpleRandomMineSeeder();

        createField();

        this.ioEngine = IOEngineFactory.buildEngine(difficultyConfig.rows(),
                difficultyConfig.columns(),
                difficultyConfig.totalFlags(),
                this,
                IOEngines.DEFAULT_ENGINE);
    }

    private void createField() {
        this.field = new Field(difficultyConfig.rows(), difficultyConfig.columns());
        seedFieldWithMines();
    }

    private void seedFieldWithMines() {
        mineSeeder.seedMines(field, difficultyConfig);
    }

    private void configureForCurrentDifficulty() {
        difficultyConfig = DifficultyConfiguration.create(currentDifficulty);
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
        for (int i = 0; i < difficultyConfig.rows(); i++) {
            for (int j = 0; j < difficultyConfig.columns(); j++) {
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
                    processDefeat();
                    return;
                }
                if (cell.getState().equals(CellState.FLAGGED)) {
                    remainingFlags++;
                    ioEngine.displayRemainingFlagsMessage(remainingFlags, difficultyConfig.totalFlags());
                }
                openClearingAround(cell);
                if (playerWon()) {
                    processVictory();
                }
                break;
            case ACTION_FLAG:
                if (remainingFlags < 1) return;
                if (cell.getState().equals(CellState.FLAGGED)) {
                    remainingFlags++;
                    cell.setState(CellState.HIDDEN);
                    updateCellDisplay(cell);
                    ioEngine.displayRemainingFlagsMessage(remainingFlags, difficultyConfig.totalFlags());
                } else if (cell.getState().equals(CellState.HIDDEN)) {
                    remainingFlags--;
                    cell.setState(CellState.FLAGGED);
                    updateCellDisplay(cell);
                    ioEngine.displayRemainingFlagsMessage(remainingFlags, difficultyConfig.totalFlags());
                    if (playerWon()) {
                        processVictory();
                    }
                }
                break;
        }
    }

    @Override
    public void handlePlayerRestart() {
        restart();
    }

    private void restart() {
        remainingFlags = difficultyConfig.totalFlags();
        score = 0;
        playing = true;

        createField();

        ioEngine.displayRemainingFlagsMessage(remainingFlags, difficultyConfig.totalFlags());
        ioEngine.restartUI();
    }

    private void processDefeat() {
        showAllMines();
        ioEngine.displayDefeatMessage();
        playing = false;
    }

    private boolean playerWon() {
        for (int i = 0; i < difficultyConfig.rows(); i++) {
            for (int j = 0; j < difficultyConfig.columns(); j++) {
                Cell cell = field.getCell(i, j);
                if (cell.getState().equals(CellState.HIDDEN)) return false;
            }
        }
        return true;
    }

    private void processVictory() {
        ioEngine.displayVictoryMessage();
        playing = false;
    }

    private void openClearingAround(Cell start) {
        Queue<Cell> toOpen = new LinkedList<>();
        Set<Cell> processed = new HashSet<>();

        toOpen.add(start);

        do {
            Cell current = toOpen.remove();
            if (processed.contains(current)) continue;
            processed.add(current);

            current.setState(CellState.OPEN);

            if (current.getAdjacentMines() == 0) {
                toOpen.addAll(field.getAllAdjacentCells(current.getX(), current.getY()));
            }
        } while(!toOpen.isEmpty());

        for (Cell c : processed) {
            updateCellDisplay(c);
        }

    }

    @Override
    public void handlePlayerChangeDifficulty(Difficulty difficulty) {
        if (difficulty == currentDifficulty) return;

        currentDifficulty = difficulty;
        configureForCurrentDifficulty();

        ioEngine.changeBoardSize(difficultyConfig.rows(), difficultyConfig.columns());
        restart();
    }


}
