package dev.ceccon.minefield.view.swing;

import dev.ceccon.minefield.controller.PlayerActionHandler;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.swing.graphical.*;
import dev.ceccon.minefield.view.swing.graphical.board.BoardContainerPanel;
import dev.ceccon.minefield.view.swing.graphical.board.BoardPanel;
import dev.ceccon.minefield.view.swing.graphical.control.ControlContainerPanel;
import dev.ceccon.minefield.view.swing.graphical.menu.MenuBar;
import dev.ceccon.minefield.view.swing.graphical.title.TitleContainerPanel;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;

public class SwingIOEngine implements IOEngine {

    // Game title should not change regardless of language
    private static final String GAME_TITLE = "Minefield";

    private PlayerActionHandler actionHandler;
    private LanguageProvider languageProvider;

    // Graphical elements
    private TitleContainerPanel titleContainerPanel;
    private BoardPanel boardPanel;
    private BoardContainerPanel boardContainerPanel;
    private ControlContainerPanel controlContainerPanel;
    private MainPanel mainPanel;
    private JMenuBar menuBar;
    private MainFrame mainFrame;

    private int totalRows;
    private int totalCols;
    private int totalFlags;

    public SwingIOEngine(Integer totalRows, Integer totalCols, Integer totalFlags, PlayerActionHandler actionHandler) {
        this.actionHandler = actionHandler;
        this.totalRows = totalRows;
        this.totalCols = totalCols;
        this.totalFlags = totalFlags;

        languageProvider = new LanguageProvider();

        mainFrame = new MainFrame(GAME_TITLE);
        mainFrame.setVisible(true);
        initializeGraphicalElements();

    }

    private void initializeGraphicalElements() {
        titleContainerPanel = new TitleContainerPanel(GAME_TITLE);
        boardPanel = new BoardPanel(totalRows, totalCols, actionHandler);
        boardContainerPanel = new BoardContainerPanel(boardPanel);
        controlContainerPanel = new ControlContainerPanel(totalFlags, totalFlags, 0, actionHandler, languageProvider);

        mainPanel = new MainPanel(titleContainerPanel, boardContainerPanel, controlContainerPanel);

        menuBar = new MenuBar(actionHandler, languageProvider);

        mainFrame.setSize(800,625);
        mainFrame.setJMenuBar(menuBar);
        mainFrame.setContentPane(mainPanel);
    }

    @Override
    public void displayAsHidden(Integer x, Integer y) {
        boardPanel.displayAsHidden(x, y);
    }

    @Override
    public void displayAsOpen(Integer x, Integer y, Integer numAdjacentMines) {
        boardPanel.displayAsOpen(x, y, numAdjacentMines);
    }

    @Override
    public void displayAsFlagged(Integer x, Integer y) {
        boardPanel.displayAsFlagged(x, y);
    }

    @Override
    public void displayAsMine(Integer x, Integer y) {
        boardPanel.displayAsMine(x, y);
    }

    @Override
    public void displayRemainingFlagsMessage(Integer remaining, Integer total) {
        controlContainerPanel.setRemainingFlagsDisplay(remaining, total);
    }

    @Override
    public void displayVictoryMessage() {
        controlContainerPanel.displayVictoryMessage();
    }

    @Override
    public void displayDefeatMessage() {
        controlContainerPanel.displayDefeatMessage();
    }

    @Override
    public void updateScoreDisplay(Integer score) {
        controlContainerPanel.updateScoreDisplay(score);
    }

    @Override
    public void restartUI() {
        boardPanel.hideAll();
        updateScoreDisplay(0);
    }

    @Override
    public void changeBoardSize(Integer rows, Integer columns) {
        boardPanel.setBoardSize(rows, columns);
    }

}
