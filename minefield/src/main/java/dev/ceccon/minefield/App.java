package dev.ceccon.minefield;

import dev.ceccon.minefield.controller.Controller;
import dev.ceccon.minefield.controller.difficulty.Difficulty;
import dev.ceccon.minefield.controller.difficulty.DifficultyConfiguration;
import dev.ceccon.minefield.view.IOEngine;
import dev.ceccon.minefield.view.IOEngineFactory;
import dev.ceccon.minefield.view.IOEngines;

import javax.swing.*;

public class App {
    public static void main( String[] args ) {
        System.out.println( "MINEFIELD - Starting game" );
        SwingUtilities.invokeLater(() -> App.initializeWithSwing());
    }

    private static void initializeWithSwing() {
        DifficultyConfiguration difficultyConfig = DifficultyConfiguration.create(Difficulty.INTERMEDIATE);
        Controller controller = new Controller(difficultyConfig);

        IOEngine ioEngine = IOEngineFactory.buildEngine(difficultyConfig.rows(),
                difficultyConfig.columns(),
                difficultyConfig.totalFlags(),
                controller,
                IOEngines.SWING_ENGINE);

        controller.setIoEngine(ioEngine);

        controller.initialize();
    }
}
