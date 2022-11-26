package dev.ceccon.minefield.model;

import dev.ceccon.minefield.constants.Difficulty;

public class DifficultyConfiguration {

    private Difficulty difficulty;
    private int numRows;
    private int numColumns;
    private int totalFlags;

    private DifficultyConfiguration(Difficulty difficulty, int numRows, int numColumns, int totalFlags) {
        this.difficulty = difficulty;
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.totalFlags = totalFlags;
    }

    public static DifficultyConfiguration create(Difficulty difficulty) {
        DifficultyConfiguration config;
        switch(difficulty) {
            case BEGINNER:
                config = new DifficultyConfiguration(difficulty, 10, 15, 15);
                break;
            case INTERMEDIATE:
                config = new DifficultyConfiguration(difficulty, 15, 20, 50);
                break;
            case EXPERT:
            default:
                config = new DifficultyConfiguration(difficulty, 15, 25, 90);
                break;
        }
        return config;
    }

    public Difficulty difficulty() {
        return difficulty;
    }

    public int rows() {
        return numRows;
    }

    public int columns() {
        return numColumns;
    }

    public int totalFlags() {
        return totalFlags;
    }
}
