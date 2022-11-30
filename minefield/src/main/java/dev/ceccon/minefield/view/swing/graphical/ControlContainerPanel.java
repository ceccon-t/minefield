package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.controller.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;
import java.awt.*;

public class ControlContainerPanel extends JPanel implements LanguageObserver {

    private static final String FLAGS_LABEL_PREFIX_ID = "FLAGS_LABEL_PREFIX";
    private Integer lastFlagsRemaining;
    private Integer lastFlagsTotal;
    private static final String VICTORY_MESSAGE_ID = "VICTORY_MESSAGE";
    private static final String DEFEAT_MESSAGE_ID = "DEFEAT_MESSAGE";

    private static final String NEW_GAME_BUTTON_LABEL_ID = "NEW_GAME_BUTTON";

    private LanguageProvider languageProvider;

    JButton newGameButton;
    JLabel currentStateLabel;
    JLabel scoreLabel;

    public ControlContainerPanel(Integer initialRemainingFlags, Integer initialTotalFlags, Integer initialScore, PlayerActionHandler actionHandler, LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;

        this.newGameButton = new JButton(languageProvider.getString(NEW_GAME_BUTTON_LABEL_ID));
        this.currentStateLabel = new JLabel(buildFlagsDisplayText(initialRemainingFlags, initialTotalFlags));
        this.scoreLabel = new JLabel(initialScore.toString());

        setLayout(new BorderLayout());

        add(newGameButton, BorderLayout.NORTH);
        add(currentStateLabel, BorderLayout.WEST);
        add(scoreLabel, BorderLayout.EAST);

        newGameButton.addActionListener(e -> {
            actionHandler.handlePlayerRestart();
        });

        languageProvider.registerObserver(this);
    }

    public void setRemainingFlagsDisplay(Integer remainingFlags, Integer totalFlags) {
        currentStateLabel.setText(buildFlagsDisplayText(remainingFlags, totalFlags));
    }

    private String buildFlagsDisplayText(Integer remaining, Integer total) {
        lastFlagsRemaining = remaining;
        lastFlagsTotal = total;
        return languageProvider.getString(FLAGS_LABEL_PREFIX_ID) + remaining + "/" + total;
    }

    public void displayVictoryMessage() {
        currentStateLabel.setText(languageProvider.getString(VICTORY_MESSAGE_ID));
    }

    public void displayDefeatMessage() {
        currentStateLabel.setText(languageProvider.getString(DEFEAT_MESSAGE_ID));
    }

    public void updateScoreDisplay(Integer score) {
        scoreLabel.setText(score.toString());
    }

    @Override
    public void onLanguageChanged() {
        newGameButton.setText(languageProvider.getString(NEW_GAME_BUTTON_LABEL_ID));
        setRemainingFlagsDisplay(lastFlagsRemaining, lastFlagsTotal); // TODO: Change to use currently displayed message (victory/defeat/state)
    }
}
