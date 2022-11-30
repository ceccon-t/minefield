package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.controller.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;
import dev.ceccon.minefield.view.swing.language.Languages;

import javax.swing.*;

public class MenuBar extends JMenuBar implements LanguageObserver {

    private PlayerActionHandler actionHandler;
    private LanguageProvider languageProvider;

    // Keep reference of items with labels to allow changing language.
    // File Menu
    private JMenu fileMenu;
    private JMenuItem exitItem;
    private static final String MENU_BAR_FILE_ID = "MENU_BAR_FILE";
    private static final String MENU_BAR_FILE_EXIT_ID = "MENU_BAR_FILE_EXIT";

    // Difficulty
    private JMenu difficultyMenu;
    private JMenuItem beginnerItem;
    private JMenuItem intermediateItem;
    private JMenuItem expertItem;
    private static final String MENU_BAR_DIFFICULTY_ID = "MENU_BAR_DIFFICULTY";
    private static final String MENU_BAR_DIFFICULTY_BEGINNER_ID = "MENU_BAR_DIFFICULTY_BEGINNER";
    private static final String MENU_BAR_DIFFICULTY_INTERMEDIATE_ID = "MENU_BAR_DIFFICULTY_INTERMEDIATE";
    private static final String MENU_BAR_DIFFICULTY_EXPERT_ID = "MENU_BAR_DIFFICULTY_EXPERT";

    // Language Menu
    private JMenu languageMenu;
    private JMenuItem englishItem;
    private JMenuItem portugueseItem;
    private static final String MENU_BAR_LANGUAGE_ID = "MENU_BAR_LANGUAGE";
    private static final String MENU_BAR_LANGUAGE_ENGLISH_ID = "MENU_BAR_LANGUAGE_ENGLISH";
    private static final String MENU_BAR_LANGUAGE_PORTUGUESE_ID = "MENU_BAR_LANGUAGE_PORTUGUESE";

    public MenuBar(PlayerActionHandler actionHandler, LanguageProvider languageProvider) {
        this.actionHandler = actionHandler;
        this.languageProvider = languageProvider;

        add(createFileMenu());
        add(createDifficultyMenu());
        add(createLanguageMenu());

        languageProvider.registerObserver(this);
    }

    private JMenu createFileMenu() {
        fileMenu = new JMenu(languageProvider.getString(MENU_BAR_FILE_ID));
        exitItem = new JMenuItem(languageProvider.getString(MENU_BAR_FILE_EXIT_ID));

        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createDifficultyMenu() {
        difficultyMenu = new JMenu(languageProvider.getString(MENU_BAR_DIFFICULTY_ID));

        beginnerItem = new JMenuItem(languageProvider.getString(MENU_BAR_DIFFICULTY_BEGINNER_ID));
        beginnerItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.BEGINNER));

        intermediateItem = new JMenuItem(languageProvider.getString(MENU_BAR_DIFFICULTY_INTERMEDIATE_ID));
        intermediateItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.INTERMEDIATE));

        expertItem = new JMenuItem(languageProvider.getString(MENU_BAR_DIFFICULTY_EXPERT_ID));
        expertItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.EXPERT));

        difficultyMenu.add(beginnerItem);
        difficultyMenu.add(intermediateItem);
        difficultyMenu.add(expertItem);

        return difficultyMenu;
    }

    private JMenu createLanguageMenu() {
        languageMenu = new JMenu(languageProvider.getString(MENU_BAR_LANGUAGE_ID));

        englishItem = new JMenuItem(languageProvider.getString(MENU_BAR_LANGUAGE_ENGLISH_ID));
        englishItem.addActionListener(e -> languageProvider.changeLanguage(Languages.ENGLISH));
        portugueseItem = new JMenuItem(languageProvider.getString(MENU_BAR_LANGUAGE_PORTUGUESE_ID));
        portugueseItem.addActionListener(e -> languageProvider.changeLanguage(Languages.PORTUGUESE));

        languageMenu.add(englishItem);
        languageMenu.add(portugueseItem);

        return languageMenu;
    }

    @Override
    public void onLanguageChanged() {
        // File Menu
        fileMenu.setText(languageProvider.getString(MENU_BAR_FILE_ID));
        exitItem.setText(languageProvider.getString(MENU_BAR_FILE_EXIT_ID));

        // Difficulty Menu
        difficultyMenu.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_ID));
        beginnerItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_BEGINNER_ID));
        intermediateItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_INTERMEDIATE_ID));
        expertItem.setText(languageProvider.getString(MENU_BAR_DIFFICULTY_EXPERT_ID));

        // Language Menu
        languageMenu.setText(languageProvider.getString(MENU_BAR_LANGUAGE_ID));
        englishItem.setText(languageProvider.getString(MENU_BAR_LANGUAGE_ENGLISH_ID));
        portugueseItem.setText(languageProvider.getString(MENU_BAR_LANGUAGE_PORTUGUESE_ID));
    }
}
