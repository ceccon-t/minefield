package dev.ceccon.minefield.view.swing.graphical;

import javax.swing.*;
import java.awt.*;

public class TitleContainerPanel extends JPanel {

    public TitleContainerPanel() {
        JLabel titleLabel = new JLabel("MINEFIELD");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 68));
        add(titleLabel);
    }
}