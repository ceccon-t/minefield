package dev.ceccon.minefield.view;

import javax.swing.*;
import java.awt.*;

public class ControlContainerPanel extends JPanel {

    public ControlContainerPanel() {
        JButton newGameButton = new JButton("New Game");
        JLabel flagsLabel = new JLabel("Flags: 15/20");
        JLabel scoreLabel = new JLabel("500");

        setLayout(new BorderLayout());
        add(newGameButton, BorderLayout.NORTH);
        add(flagsLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);
    }
}
