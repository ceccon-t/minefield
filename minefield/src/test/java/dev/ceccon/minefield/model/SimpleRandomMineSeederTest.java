package dev.ceccon.minefield.model;

import dev.ceccon.minefield.controller.difficulty.Difficulty;
import dev.ceccon.minefield.controller.difficulty.DifficultyConfiguration;
import dev.ceccon.minefield.model.seeder.MineSeeder;
import dev.ceccon.minefield.model.seeder.SimpleRandomMineSeeder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRandomMineSeederTest {

    @ParameterizedTest
    @EnumSource(Difficulty.class)
    void simpleRandomSeedsRightAmountForDifficulty(Difficulty difficulty) {
        String description = "SimpleRandomMineSeeder should seed as many mines as difficulty determines";
        MineSeeder seeder = new SimpleRandomMineSeeder();
        DifficultyConfiguration difficultyConfig = DifficultyConfiguration.create(difficulty);
        Field field = new Field(difficultyConfig.rows(), difficultyConfig.columns());

        seeder.seedMines(field, difficultyConfig);

        Integer expectedMines = difficultyConfig.totalFlags();

        Integer minesOnField = 0;
        for (int i = 0; i < difficultyConfig.rows(); i++) {
            for (int j = 0; j < difficultyConfig.columns(); j++) {
                if (field.getCell(i, j).isMine()) minesOnField++;
            }
        }

        assertEquals(expectedMines, minesOnField, description);
    }

}