package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.view.swing.graphical.BoardPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellPanel extends JPanel {

    private static final int CELL_WIDTH = 25;
    private static final int CELL_HEIGHT = 25;

    private BoardPanel board;

    private int x;
    private int y;
    private JLabel labelComponent;

    private Color defaultColor;
    private Border defaultBorder;

    public CellPanel(int x, int y, String label, BoardPanel board) {
        this.x = x;
        this.y = y;
        this.board = board;

        labelComponent = new JLabel(label);
        add(labelComponent);

        setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));

        this.defaultColor = getBackground();
        this.defaultBorder = BorderFactory.createRaisedBevelBorder();
        setBorder(defaultBorder);

        registerClickListener();
    }

    private void setLabelText(String label) {
        labelComponent.setText(label);
    }

    private void registerClickListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int buttonPressed = e.getButton();

                switch(buttonPressed) {
                    // TODO: Create enum to map button numbers semantically
                    case 1:
                    case 3:
                        board.cellClickedWith(x, y, buttonPressed);
                        break;
                    default:
                        System.out.println("Could not recognize button clicked, identifier: " + buttonPressed);

                }
            }
        });
    }

    public void displayHidden() {
        setBackground(defaultColor);
        setBorder(defaultBorder);
        setLabelText(" ");
    }

    public void displayOpen(Integer numAdjacentMines) {
        setBackground(defaultColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        if (numAdjacentMines > 0) {
            setLabelText(numAdjacentMines.toString());
        }
    }

    public void displayMine() {
        setBackground(Color.RED);
        setBorder(defaultBorder);
    }

    public void displayFlagged() {
        setBackground(Color.YELLOW);
        setBorder(defaultBorder);
        labelComponent.setText("!");
    }

}
