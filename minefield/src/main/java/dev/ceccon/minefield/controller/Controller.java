package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.CellState;
import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.view.*;

public class Controller {

    // Graphical elements
    private TitleContainerPanel titleContainerPanel;
    private BoardPanel boardPanel;
    private BoardContainerPanel boardContainerPanel;
    private ControlContainerPanel controlContainerPanel;
    private MainPanel mainPanel;
    private MainFrame mainFrame;

    private int numRows = 15;
    private int numCols = 25;

    private int totalFlags = 10;
    private int remainingFlags = 10;
    private int score = 42;

    private boolean playing = true;

    public Controller() {
        titleContainerPanel = new TitleContainerPanel();
        boardPanel = new BoardPanel(numRows, numCols, this);
        boardContainerPanel = new BoardContainerPanel(boardPanel);
        controlContainerPanel = new ControlContainerPanel(remainingFlags, totalFlags, score);

        mainPanel = new MainPanel(titleContainerPanel, boardContainerPanel, controlContainerPanel);

        mainFrame = new MainFrame();
        mainFrame.setContentPane(mainPanel);
    }

    public void handlePlayerActionOn(PlayerAction action, int x, int y) {
        if (!playing) return;
        switch (action) {
            case ACTION_OPEN:
                score += 10;
                controlContainerPanel.updateScoreDisplay(score);
                if (x == 3 && y == 3) {
                    playing = false;
                    controlContainerPanel.displayDefeatMessage();
                    return;
                }
                if (score > 100) {
                    playing = false;
                    controlContainerPanel.displayVictoryMessage();
                    return;
                }
                boardPanel.setCellState(x, y, CellState.OPEN);
                break;
            case ACTION_FLAG:
                if (remainingFlags < 1) return;
                remainingFlags--;
                controlContainerPanel.setRemainingFlagsDisplay(remainingFlags, totalFlags);
                boardPanel.setCellState(x, y, CellState.FLAGGED);
                break;
        }
    }
}
