package dev.ceccon.minefield.view;

import javax.swing.*;
import java.awt.*;

public class BoardContainerPanel extends JPanel {

    public BoardContainerPanel() {
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(0, 440));
        boardPanel.setBackground(Color.RED);
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
    }
}
