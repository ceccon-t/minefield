package dev.ceccon.minefield.view;

import dev.ceccon.minefield.controller.action.PlayerActionHandler;
import dev.ceccon.minefield.view.swing.SwingIOEngine;

public class IOEngineFactory {

    public static IOEngine buildEngine(Integer totalRows, Integer totalColumns, Integer totalFlags, PlayerActionHandler controller, IOEngines desiredEngine) {
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
