package dev.ceccon.minefield.view.swing.graphical;

import javax.swing.*;

public class MainPanel extends JPanel {

    public MainPanel(JPanel titleContainerPanel, JPanel boardContainerPanel, JPanel controlContainerPanel) {

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(titleContainerPanel);
        add(boardContainerPanel);
        add(controlContainerPanel);

    }
}
