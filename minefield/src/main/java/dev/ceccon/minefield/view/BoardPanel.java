package dev.ceccon.minefield.view;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private static final int BOARD_OUTER_BORDER_THICKNESS = 5;

    public BoardPanel(int rows, int cols) {
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;

        for (int i = 0; i < rows; i++) {
            constraints.gridx = 0;
            for (int j = 0; j < cols; j++) {
                add(new CellPanel(i, j, " "), constraints);
                constraints.gridx++;
            }
            constraints.gridy++;
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK, BOARD_OUTER_BORDER_THICKNESS));
    }
}
