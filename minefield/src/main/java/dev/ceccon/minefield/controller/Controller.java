package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.view.*;

public class Controller {

    private TitleContainerPanel titleContainerPanel;
    private BoardContainerPanel boardContainerPanel;
    private ControlContainerPanel controlContainerPanel;
    private MainPanel mainPanel;
    private MainFrame mainFrame;

    public Controller() {
        titleContainerPanel = new TitleContainerPanel();
        boardContainerPanel = new BoardContainerPanel();
        controlContainerPanel = new ControlContainerPanel();

        mainPanel = new MainPanel(titleContainerPanel, boardContainerPanel, controlContainerPanel);

        mainFrame = new MainFrame();
        mainFrame.setContentPane(mainPanel);
    }
}
