package dev.ceccon.minefield.view.swing.language;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class LanguageProvider {

    private static final String PROPERTIES_FILE_NAME = "SwingUILabels";

    private ResourceBundle mappings;
    private Set<LanguageObserver> observers;

    public LanguageProvider() {
        mappings = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
        observers = new HashSet<>();
    }

    public String getString(String s) {
        return mappings.getString(s);
    }

    public void changeLanguage(Languages newLanguage) {
        mappings = ResourceBundle.getBundle(
                PROPERTIES_FILE_NAME,
                Locale.forLanguageTag(convertToLanguageTag(newLanguage)));
        notifyObservers();
    }

    public void registerObserver(LanguageObserver newObserver) {
        observers.add(newObserver);
    }

    public void removeObserver(LanguageObserver oldObserver) {
        observers.remove(oldObserver);
    }

    public void notifyObservers() {
        for (LanguageObserver observer : observers) {
            observer.onLanguageChanged();
        }
    }

    private String convertToLanguageTag(Languages language) {
        String tag = "en";

        switch (language) {
            case ENGLISH:  // same as default, but choosing to make all options explicit
                tag = "en";
                break;
            case PORTUGUESE:
                tag = "pt";
                break;
        }

        return tag;
    }

}
