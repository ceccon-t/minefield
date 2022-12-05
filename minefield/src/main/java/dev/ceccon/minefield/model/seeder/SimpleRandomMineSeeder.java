package dev.ceccon.minefield.model.seeder;

import dev.ceccon.minefield.controller.difficulty.DifficultyConfiguration;
import dev.ceccon.minefield.model.Cell;
import dev.ceccon.minefield.model.Field;

import java.util.Random;

public class SimpleRandomMineSeeder implements MineSeeder {

    private Random randomGenerator;

    public SimpleRandomMineSeeder() {
        this.randomGenerator = new Random();
    }

    @Override
    public void seedMines(Field field, DifficultyConfiguration difficultyConfig) {
        int rows = difficultyConfig.rows();
        int columns = difficultyConfig.columns();;

        Integer remaining = difficultyConfig.totalFlags();

        while (remaining > 0) {
            Integer randomRow = randomGenerator.nextInt(rows);
            Integer randomColumn = randomGenerator.nextInt(columns);
            Cell cell = field.getCell(randomRow, randomColumn);
            if (!cell.isMine()) {
                field.setMineOn(randomRow, randomColumn);
                remaining--;
            }
        }

    }

}
