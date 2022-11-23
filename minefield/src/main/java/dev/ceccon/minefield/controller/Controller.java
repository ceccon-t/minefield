package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

public class Controller {

    private int numRows = 15;
    private int numCols = 25;

    private int totalFlags = 10;
    private int remainingFlags = 10;
    private int score = 42;

    private boolean playing = true;

    private IOEngine ioEngine;

    public Controller() {
        this.ioEngine = IOEngineFactory.buildEngine(numRows, numCols, totalFlags, this, IOEngines.DEFAULT_ENGINE);
    }

    public void handlePlayerActionOn(PlayerAction action, int x, int y) {
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
                ioEngine.displayAsOpen(x, y);
                break;
            case ACTION_FLAG:
                if (remainingFlags < 1) return;
                remainingFlags--;
                ioEngine.displayRemainingFlagsMessage(remainingFlags, totalFlags);
                ioEngine.displayAsFlagged(x, y);
                break;
        }
    }
}
