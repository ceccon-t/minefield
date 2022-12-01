package dev.ceccon.minefield.view.swing.graphical.menu;

import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.controller.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;

public class DifficultyMenu extends JMenu implements LanguageObserver {

    private static final String MENU_BAR_DIFFICULTY_ID = "MENU_BAR_DIFFICULTY";
    private static final String MENU_BAR_DIFFICULTY_BEGINNER_ID = "MENU_BAR_DIFFICULTY_BEGINNER";
    private static final String MENU_BAR_DIFFICULTY_INTERMEDIATE_ID = "MENU_BAR_DIFFICULTY_INTERMEDIATE";
    private static final String MENU_BAR_DIFFICULTY_EXPERT_ID = "MENU_BAR_DIFFICULTY_EXPERT";

    private PlayerActionHandler actionHandler;
    private LanguageProvider languageProvider;

    private JMenuItem beginnerItem;
    private JMenuItem intermediateItem;
    private JMenuItem expertItem;

    public DifficultyMenu(PlayerActionHandler actionHandler, LanguageProvider languageProvider) {
        this.actionHandler = actionHandler;
        this.languageProvider = languageProvider;
        languageProvider.registerObserver(this);

        beginnerItem = new JMenuItem();
        beginnerItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.BEGINNER));

        intermediateItem = new JMenuItem();
        intermediateItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.INTERMEDIATE));

        expertItem = new JMenuItem();
        expertItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.EXPERT));

        setAllTexts();

        add(beginnerItem);
        add(intermediateItem);
        add(expertItem);
    }

    private void setAllTexts() {
        setText(languageProvider.getString(MENU_BAR_DIFFICULTY_ID));
        beginnerItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_BEGINNER_ID));
        intermediateItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_INTERMEDIATE_ID));
        expertItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_EXPERT_ID));
    }

    @Override
    public void onLanguageChanged() {
        setAllTexts();
    }
}
