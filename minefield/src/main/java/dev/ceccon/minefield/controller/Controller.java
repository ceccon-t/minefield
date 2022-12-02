package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.controller.action.PlayerActionHandler;
import dev.ceccon.minefield.controller.difficulty.Difficulty;
import dev.ceccon.minefield.controller.action.PlayerAction;
import dev.ceccon.minefield.controller.difficulty.DifficultyConfiguration;
import dev.ceccon.minefield.controller.seeder.MineSeeder;
import dev.ceccon.minefield.controller.seeder.SimpleRandomMineSeeder;
import dev.ceccon.minefield.model.*;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Controller implements PlayerActionHandler {

    private static final int ONE_SECOND_IN_MS = 1000;

    private Timer scoreTimer;

    private Difficulty currentDifficulty = Difficulty.INTERMEDIATE;
    private DifficultyConfiguration difficultyConfig;

    private MineSeeder mineSeeder;

    private Game game;

    private IOEngine ioEngine;

    public Controller() {
        configureForCurrentDifficulty();
        mineSeeder = new SimpleRandomMineSeeder();

        this.game = createGame();

        this.ioEngine = IOEngineFactory.buildEngine(difficultyConfig.rows(),
                difficultyConfig.columns(),
                difficultyConfig.totalFlags(),
                this,
                IOEngines.DEFAULT_ENGINE);

        scoreTimer = createScoreTimer(ONE_SECOND_IN_MS);
        scoreTimer.start();
    }

    private void configureForCurrentDifficulty() {
        difficultyConfig = DifficultyConfiguration.create(currentDifficulty);
    }

    private Game createGame() {
        Game newGame = new Game(difficultyConfig.rows(), difficultyConfig.columns(), difficultyConfig.totalFlags());
        mineSeeder.seedMines(newGame.getField(), difficultyConfig);
        return newGame;
    }

    private Timer createScoreTimer(int interval) {
        return new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (game.isPlaying()) {
                    game.tickTime();
                    ioEngine.updateScoreDisplay(game.getTimeElapsed());
                }
            }
        });
    }

    private void stopGame() {
        game.setPlaying(false);
        scoreTimer.stop();
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
        for (Cell c : game.getAllMines()) {
            ioEngine.displayAsMine(c.getX(), c.getY());
        }
    }

    @Override
    public void handlePlayerActionOnCell(PlayerAction action, int x, int y) {
        if (!game.isPlaying()) return;

        switch (action) {
            case ACTION_OPEN:
                Set<Cell> allOpen = game.attemptOpeningFromCell(x, y);

                for (Cell open : allOpen) {
                    updateCellDisplay(open);
                }

                if (!game.isPlaying()) {
                    processEndGame();
                } else {
                    ioEngine.displayRemainingFlagsMessage(game.getRemainingFlags(), difficultyConfig.totalFlags());
                }

                break;

            case ACTION_FLAG:
                boolean changedState = game.attemptFlaggingCell(x, y);

                if (changedState) {
                    updateCellDisplay(game.getField().getCell(x, y));
                    if (!game.isPlaying()) {
                        processEndGame();
                    } else {
                        ioEngine.displayRemainingFlagsMessage(game.getRemainingFlags(), difficultyConfig.totalFlags());
                    }
                }

                break;
        }
    }

    private void restart() {
        scoreTimer.restart();

        game = createGame();

        ioEngine.displayRemainingFlagsMessage(game.getRemainingFlags(), difficultyConfig.totalFlags());
        ioEngine.restartUI();
    }

    @Override
    public void handlePlayerRestart() {
        restart();
    }

    private void processDefeat() {
        showAllMines();
        ioEngine.displayDefeatMessage();
        stopGame();
    }

    private void processVictory() {
        ioEngine.displayVictoryMessage();
        stopGame();
    }

    private void processEndGame() {
        if (game.didPlayerWin()) {
            processVictory();
        } else if (game.didPlayerLose()) {
            processDefeat();
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
