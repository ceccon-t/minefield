package dev.ceccon.minefield.view.swing;

import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.controller.PlayerActionHandler;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int BOARD_OUTER_BORDER_THICKNESS = 5;

    PlayerActionHandler actionHandler;
    private CellPanel[][] cells;

    public BoardPanel(int rows, int cols, PlayerActionHandler actionHandler) {
        this.cells = new CellPanel[rows][cols];
        this.actionHandler = actionHandler;

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;

        for (int i = 0; i < rows; i++) {
            constraints.gridx = 0;
            for (int j = 0; j < cols; j++) {
                CellPanel cell = new CellPanel(i, j, " ", this);
                add(cell, constraints);
                this.cells[i][j] = cell;
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK, BOARD_OUTER_BORDER_THICKNESS));
    }

    public void cellClickedWith(int x, int y, int buttonNumber) {
        switch (buttonNumber) {
            case 1:
                actionHandler.handlePlayerActionOnCell(PlayerAction.ACTION_OPEN, x, y);
                break;
            case 3:
                actionHandler.handlePlayerActionOnCell(PlayerAction.ACTION_FLAG, x, y);
                break;
        }
    }

    public void displayAsHidden(int x, int y) {
        CellPanel cell = cells[x][y];
        cell.displayHidden();
    }

    public void displayAsOpen(int x, int y, int numAdjacentMines) {
        CellPanel cell = cells[x][y];
        cell.displayOpen(numAdjacentMines);
    }

    public void displayAsFlagged(int x, int y) {
        CellPanel cell = cells[x][y];
        cell.displayFlagged();
    }

    public void displayAsMine(int x, int y) {
        CellPanel cell = cells[x][y];
        cell.displayMine();
    }
}
