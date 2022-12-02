package dev.ceccon.minefield.view.swing.graphical.menu;

import dev.ceccon.minefield.controller.action.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private PlayerActionHandler actionHandler;
    private LanguageProvider languageProvider;

    public MenuBar(PlayerActionHandler actionHandler, LanguageProvider languageProvider) {
        this.actionHandler = actionHandler;
        this.languageProvider = languageProvider;

        add(createFileMenu());
        add(createDifficultyMenu());
        add(createLanguageMenu());
    }

    private JMenu createFileMenu() {
        return new FileMenu(languageProvider);
    }

    private JMenu createDifficultyMenu() {
        return new DifficultyMenu(actionHandler, languageProvider);
    }

    private JMenu createLanguageMenu() {
        return new LanguageMenu(languageProvider);
    }

}
