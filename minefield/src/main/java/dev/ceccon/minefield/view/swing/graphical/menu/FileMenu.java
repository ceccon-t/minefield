package dev.ceccon.minefield.view.swing.graphical.menu;

import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;

import javax.swing.*;

public class FileMenu extends JMenu implements LanguageObserver {

    private static final String MENU_BAR_FILE_ID = "MENU_BAR_FILE";
    private static final String MENU_BAR_FILE_EXIT_ID = "MENU_BAR_FILE_EXIT";

    private LanguageProvider languageProvider;
    private JMenuItem exitItem;

    public FileMenu(LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;
        languageProvider.registerObserver(this);

        this.exitItem = new JMenuItem();

        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        setAllTexts();

        add(exitItem);
    }

    @Override
    public void onLanguageChanged() {
        setAllTexts();
    }

    private void setAllTexts() {
        setText(languageProvider.getString(MENU_BAR_FILE_ID));
        exitItem.setText(languageProvider.getString(MENU_BAR_FILE_EXIT_ID));
    }

}
