package dev.ceccon.minefield.view.swing.graphical.control;

import dev.ceccon.minefield.controller.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;
import java.awt.*;

public class ControlContainerPanel extends JPanel implements LanguageObserver {

    private static final String FLAGS_MESSAGE_PREFIX_ID = "FLAGS_LABEL_PREFIX";
    private Integer currentRemainingFlags;
    private Integer currentTotalFlags;
    private static final String VICTORY_MESSAGE_ID = "VICTORY_MESSAGE";
    private static final String DEFEAT_MESSAGE_ID = "DEFEAT_MESSAGE";

    private static final String NEW_GAME_BUTTON_LABEL_ID = "NEW_GAME_BUTTON";


    private LanguageProvider languageProvider;

    JButton newGameButton;
    JLabel currentStateLabel;
    JLabel scoreLabel;

    private String idStateMessageDisplayed;

    public ControlContainerPanel(Integer initialRemainingFlags, Integer initialTotalFlags, Integer initialScore, PlayerActionHandler actionHandler, LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;

        this.newGameButton = new JButton(languageProvider.getString(NEW_GAME_BUTTON_LABEL_ID));
        this.currentStateLabel = new JLabel(buildFlagsDisplayText(initialRemainingFlags, initialTotalFlags));
        this.scoreLabel = new JLabel(initialScore.toString());

        idStateMessageDisplayed = FLAGS_MESSAGE_PREFIX_ID;

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
        idStateMessageDisplayed = FLAGS_MESSAGE_PREFIX_ID;
    }

    private String buildFlagsDisplayText(Integer remaining, Integer total) {
        currentRemainingFlags = remaining;
        currentTotalFlags = total;
        return languageProvider.getString(FLAGS_MESSAGE_PREFIX_ID) + remaining + "/" + total;
    }

    public void displayVictoryMessage() {
        currentStateLabel.setText(languageProvider.getString(VICTORY_MESSAGE_ID));
        idStateMessageDisplayed = VICTORY_MESSAGE_ID;
    }

    public void displayDefeatMessage() {
        currentStateLabel.setText(languageProvider.getString(DEFEAT_MESSAGE_ID));
        idStateMessageDisplayed = DEFEAT_MESSAGE_ID;
    }

    public void updateScoreDisplay(Integer score) {
        scoreLabel.setText(score.toString());
    }

    private void updateStateMessageLanguage() {
        switch(idStateMessageDisplayed) {
            case FLAGS_MESSAGE_PREFIX_ID:
                setRemainingFlagsDisplay(currentRemainingFlags, currentTotalFlags);
                break;
            case VICTORY_MESSAGE_ID:
                displayVictoryMessage();
                break;
            case DEFEAT_MESSAGE_ID:
                displayDefeatMessage();
                break;
        }
    }

    @Override
    public void onLanguageChanged() {
        newGameButton.setText(languageProvider.getString(NEW_GAME_BUTTON_LABEL_ID));
        updateStateMessageLanguage();
    }
}
