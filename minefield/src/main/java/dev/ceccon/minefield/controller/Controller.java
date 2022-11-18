package dev.ceccon.minefield.controller;

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

    public Controller() {
        titleContainerPanel = new TitleContainerPanel();
        boardPanel = new BoardPanel(numRows, numCols);
        boardContainerPanel = new BoardContainerPanel(boardPanel);
        controlContainerPanel = new ControlContainerPanel();

        mainPanel = new MainPanel(titleContainerPanel, boardContainerPanel, controlContainerPanel);

        mainFrame = new MainFrame();
        mainFrame.setContentPane(mainPanel);
    }
}
