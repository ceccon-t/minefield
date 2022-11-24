package dev.ceccon.minefield.view.swing.graphical;

import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.controller.PlayerActionHandler;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    private PlayerActionHandler actionHandler;

    public MenuBar(PlayerActionHandler actionHandler) {
        this.actionHandler = actionHandler;

        add(createFileMenu());
        add(createDifficultyMenu());
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");

        exitItem.addActionListener(e -> {
            System.exit(0);
        });

        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu createDifficultyMenu() {
        JMenu difficultyMenu = new JMenu("Difficulty");

        JMenuItem beginnerItem = new JMenuItem("Beginner");
        beginnerItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.BEGINNER));

        JMenuItem intermediateItem = new JMenuItem("Intermediate");
        intermediateItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.INTERMEDIATE));

        JMenuItem expertItem = new JMenuItem("Expert");
        expertItem.addActionListener(e -> actionHandler.handlePlayerChangeDifficulty(Difficulty.EXPERT));

        difficultyMenu.add(beginnerItem);
        difficultyMenu.add(intermediateItem);
        difficultyMenu.add(expertItem);

        return difficultyMenu;
    }
}
