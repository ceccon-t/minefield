package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.controller.PlayerActionHandler;

import javax.swing.*;
import java.awt.*;

public class ControlContainerPanel extends JPanel {

    private static final String FLAGS_PREFIX = "Flags";
    private static final String VICTORY_MESSAGE_TEXT = "You won!";
    private static final String DEFEAT_MESSAGE_TEXT = "You lost!";

    JButton newGameButton;
    JLabel currentStateLabel;
    JLabel scoreLabel;

    public ControlContainerPanel(Integer initialRemainingFlags, Integer initialTotalFlags, Integer initialScore, PlayerActionHandler actionHandler) {
        this.newGameButton = new JButton("New Game");
        this.currentStateLabel = new JLabel(buildFlagsDisplayText(initialRemainingFlags, initialTotalFlags));
        this.scoreLabel = new JLabel(initialScore.toString());

        setLayout(new BorderLayout());

        add(newGameButton, BorderLayout.NORTH);
        add(currentStateLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);

        newGameButton.addActionListener(e -> {
            actionHandler.handlePlayerRestart();
        });
    }

    public void setRemainingFlagsDisplay(Integer remainingFlags, Integer totalFlags) {
        currentStateLabel.setText(buildFlagsDisplayText(remainingFlags, totalFlags));
    }

    private String buildFlagsDisplayText(Integer remaining, Integer total) {
        return FLAGS_PREFIX + ": " + remaining + "/" + total;
    }

    public void displayVictoryMessage() {
        currentStateLabel.setText(VICTORY_MESSAGE_TEXT);
    }

    public void displayDefeatMessage() {
        currentStateLabel.setText(DEFEAT_MESSAGE_TEXT);
    }

    public void updateScoreDisplay(Integer score) {
        scoreLabel.setText(score.toString());
    }


}
