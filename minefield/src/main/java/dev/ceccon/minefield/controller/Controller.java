package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

import java.util.Random;

public class Controller implements PlayerActionHandler {

    private static final int TOTAL_FLAGS = 10;

    private int numRows = 15;
    private int numCols = 25;

    private int totalFlags;
    private int remainingFlags;
    private int score = 0;

    private boolean playing = true;

    private IOEngine ioEngine;

    public Controller() {
        this.totalFlags = TOTAL_FLAGS;
        this.remainingFlags = TOTAL_FLAGS;
        this.ioEngine = IOEngineFactory.buildEngine(numRows, numCols, totalFlags, this, IOEngines.DEFAULT_ENGINE);
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
        remainingFlags = TOTAL_FLAGS;
        score = 0;
        playing = true;
        ioEngine.restartUI();
    }
}
