package dev.ceccon.minefield.controller.seeder;

import dev.ceccon.minefield.controller.difficulty.DifficultyConfiguration;
import dev.ceccon.minefield.model.Field;

public interface MineSeeder {

    void seedMines(Field field, DifficultyConfiguration difficultyConfig);

}
