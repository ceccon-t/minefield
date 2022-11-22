package dev.ceccon.minefield.view;

import dev.ceccon.minefield.constants.CellState;
import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int BOARD_OUTER_BORDER_THICKNESS = 5;

    Controller controller;
    private CellPanel[][] cells;

    public BoardPanel(int rows, int cols, Controller controller) {
        this.cells = new CellPanel[rows][cols];
        this.controller = controller;

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

    public void cellCickedWith(int x, int y, int buttonNumber) {
        switch (buttonNumber) {
            case 1:
                controller.handlePlayerActionOn(PlayerAction.ACTION_OPEN, x, y);
                break;
            case 3:
                controller.handlePlayerActionOn(PlayerAction.ACTION_FLAG, x, y);
                break;
        }
    }

    public void setCellLabel(int x, int y, String label) {
        CellPanel cell = cells[x][y];
        cell.setLabelText(label);
    }

    public void setCellState(int x, int y, CellState state) {
        CellPanel cell = cells[x][y];
        switch (state) {
            case OPEN:
                cell.markOpen();
                break;
            case FLAGGED:
                cell.markFlagged();
                break;
            case MINE:
                cell.markMine();
                break;
        }
    }
}
