package dev.ceccon.minefield.view.swing.graphical.menu;

import dev.ceccon.minefield.view.swing.language.LanguageObserver;
import dev.ceccon.minefield.view.swing.language.LanguageProvider;
import dev.ceccon.minefield.view.swing.language.Languages;

import javax.swing.*;

public class LanguageMenu extends JMenu implements LanguageObserver {

    private static final String MENU_BAR_LANGUAGE_ID = "MENU_BAR_LANGUAGE";
    private static final String MENU_BAR_LANGUAGE_ENGLISH_ID = "MENU_BAR_LANGUAGE_ENGLISH";
    private static final String MENU_BAR_LANGUAGE_PORTUGUESE_ID = "MENU_BAR_LANGUAGE_PORTUGUESE";

    private JMenuItem englishItem;
    private JMenuItem portugueseItem;

    private LanguageProvider languageProvider;

    public LanguageMenu(LanguageProvider languageProvider) {
        this.languageProvider = languageProvider;
        languageProvider.registerObserver(this);

        englishItem = new JMenuItem();
        englishItem.addActionListener(e -> languageProvider.changeLanguage(Languages.ENGLISH));
        portugueseItem = new JMenuItem();
        portugueseItem.addActionListener(e -> languageProvider.changeLanguage(Languages.PORTUGUESE));

        setAllTexts();

        add(englishItem);
        add(portugueseItem);
    }

    private void setAllTexts() {
        setText(languageProvider.getString(MENU_BAR_LANGUAGE_ID));
        englishItem.setText(languageProvider.getString(MENU_BAR_LANGUAGE_ENGLISH_ID));
        portugueseItem.setText(languageProvider.getString(MENU_BAR_LANGUAGE_PORTUGUESE_ID));
    }

    @Override
    public void onLanguageChanged() {
        setAllTexts();
    }
}
