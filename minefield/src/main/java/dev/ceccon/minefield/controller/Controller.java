package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.constants.PlayerAction;
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

    private IOEngine ioEngine;

    public Controller() {
        configureForCurrentDifficulty();
        remainingFlags = totalFlags;

        this.ioEngine = IOEngineFactory.buildEngine(numRows, numCols, totalFlags, this, IOEngines.DEFAULT_ENGINE);
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

    @Override
    public void handlePlayerActionOnCell(PlayerAction action, int x, int y) {
        if (!playing) return;
        switch (action) {
            case ACTION_OPEN:
                score += 10;
                ioEngine.updateScoreDisplay(score);
                if (x == 3 && y == 3) {
                    playing = false;
                    ioEngine.displayDefeatMessage();
                    return;
                }
                if (score > 100) {
                    playing = false;
                    ioEngine.displayVictoryMessage();
                    return;
                }
                Integer randomNumberForDebug = (new Random()).nextInt(9);
                ioEngine.displayAsOpen(x, y, randomNumberForDebug);
                break;
            case ACTION_FLAG:
                if (remainingFlags < 1) return;
                remainingFlags--;
                ioEngine.displayRemainingFlagsMessage(remainingFlags, totalFlags);
                ioEngine.displayAsFlagged(x, y);
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
