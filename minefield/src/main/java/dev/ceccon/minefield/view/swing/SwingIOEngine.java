package dev.ceccon.minefield.view.swing;

import dev.ceccon.minefield.controller.Controller;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.swing.*;

public class SwingIOEngine implements IOEngine {

    Controller controller;

    // Graphical elements
    private TitleContainerPanel titleContainerPanel;
    private BoardPanel boardPanel;
    private BoardContainerPanel boardContainerPanel;
    private ControlContainerPanel controlContainerPanel;
    private MainPanel mainPanel;
    private MainFrame mainFrame;

    public SwingIOEngine(Integer totalRows, Integer totalCols, Integer totalFlags, Controller controller) {
        this.controller = controller;
        titleContainerPanel = new TitleContainerPanel();
        boardPanel = new BoardPanel(totalRows, totalCols, controller);
        boardContainerPanel = new BoardContainerPanel(boardPanel);
        controlContainerPanel = new ControlContainerPanel(totalFlags, totalFlags, 0);

        mainPanel = new MainPanel(titleContainerPanel, boardContainerPanel, controlContainerPanel);

        mainFrame = new MainFrame("Minefield");
        mainFrame.setSize(800,625);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }

    @Override
    public void displayAsHidden(Integer x, Integer y) {
        boardPanel.displayAsHidden(x, y);
    }

    @Override
    public void displayAsOpen(Integer x, Integer y) {
        boardPanel.displayAsOpen(x, y);
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
    public void setTextOnCell(Integer x, Integer y, String text) {
        boardPanel.setCellLabel(x, y, text);
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
}
