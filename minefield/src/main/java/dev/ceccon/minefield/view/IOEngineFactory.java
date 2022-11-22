package dev.ceccon.minefield.view;

import dev.ceccon.minefield.controller.Controller;
import dev.ceccon.minefield.view.swing.SwingIOEngine;

public class IOEngineFactory {

    public static IOEngine createEngine(Integer totalRows, Integer totalColumns, Integer totalFlags, Controller controller, IOEngines desiredEngine) {
        IOEngine engine;

        switch (desiredEngine) {
            case SWING_ENGINE:
            case DEFAULT_ENGINE:
            default:
                engine = new SwingIOEngine(totalRows, totalColumns, totalFlags, controller);
        }

        return engine;
    }
}
