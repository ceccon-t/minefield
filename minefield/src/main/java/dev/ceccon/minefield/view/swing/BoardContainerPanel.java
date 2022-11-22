package dev.ceccon.minefield.view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BoardContainerPanel extends JPanel {

    private static final int PADDING_TOP = 15;
    private static final int PADDING_LEFT = 0;
    private static final int PADDING_BOTTOM = 15;
    private static final int PADDING_RIGHT = 0;
    private static final int PREFERRED_WIDTH = 0;  // No preference
    private static final int PREFERRED_HEIGHT = 440;

    public BoardContainerPanel(JPanel boardPanel) {
        add(boardPanel);

        includePadding();

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
    }

    private void includePadding() {
        setBorder(new EmptyBorder(PADDING_TOP, PADDING_LEFT, PADDING_BOTTOM, PADDING_RIGHT));
    }
}
