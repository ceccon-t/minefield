package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.constants.PlayerAction;
import dev.ceccon.minefield.controller.PlayerActionHandler;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int BOARD_OUTER_BORDER_THICKNESS = 5;

    PlayerActionHandler actionHandler;
    private int totalRows;
    private int totalCols;
    private CellPanel[][] cells;

    public BoardPanel(int rows, int cols, PlayerActionHandler actionHandler) {
        this.actionHandler = actionHandler;
        this.totalRows = rows;
        this.totalCols = cols;
        this.cells = new CellPanel[rows][cols];

        setLayout(new GridBagLayout());

        generateCells();
        addCellsToView();

        setBorder(BorderFactory.createLineBorder(Color.BLACK, BOARD_OUTER_BORDER_THICKNESS));
    }

    private void generateCells() {
        cells = new CellPanel[totalRows][totalCols];
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                CellPanel cell = new CellPanel(i, j, " ", this);
                cells[i][j] = cell;
            }
       }
    }

    private void addCellsToView() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        for (int i = 0; i < totalRows; i++) {
            constraints.gridx = 0;
            for (int j = 0; j < totalCols; j++) {
                add(cells[i][j], constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }
    }

    private void removeCellsFromView() {
        removeAll();
    }

    public void setBoardSize(int rows, int cols) {
        this.totalRows = rows;
        this.totalCols = cols;
        removeCellsFromView();
        generateCells();
        addCellsToView();
        revalidate();
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

    public void hideAll() {
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                displayAsHidden(i, j);
            }
        }
    }
}
