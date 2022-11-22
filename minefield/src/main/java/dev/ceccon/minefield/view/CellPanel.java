package dev.ceccon.minefield.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class CellPanel extends JPanel {

    private static final int CELL_WIDTH = 25;
    private static final int CELL_HEIGHT = 25;

    private BoardPanel board;

    private int x;
    private int y;
    private JLabel labelComponent;

    public CellPanel(int x, int y, String label, BoardPanel board) {
        this.x = x;
        this.y = y;
        this.board = board;

        labelComponent = new JLabel(label);
        add(labelComponent);

        setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));

        setBorder(BorderFactory.createRaisedBevelBorder());

        registerClickListener();
    }

    public void setLabelText(String label) {
        labelComponent.setText(label);
    }

    private void registerClickListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int buttonPressed = e.getButton();

                System.out.println("Button " + buttonPressed + " clicked on cell (" + x + ", " + y + ").");
                switch(buttonPressed) {
                    // TODO: Create enum to map button numbers semantically
                    case 1:
                    case 3:
                        board.cellCickedWith(x, y, buttonPressed);
                    default:
                        System.out.println("Could not recognize button clicked, identifier: " + buttonPressed);

                }
            }
        });
    }

    public void markOpen() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        Integer randomNumberForDebug = (new Random()).nextInt(9);
        if (randomNumberForDebug > 0) {
            setLabelText(randomNumberForDebug.toString());
        }
    }

    public void markMine() {
        setBackground(Color.RED);
    }

    public void markFlagged() {
        labelComponent.setText("!");
        setBackground(Color.YELLOW);
    }

}
