package dev.ceccon.minefield.view;

public interface IOEngine {

    void displayAsHidden(Integer x, Integer y);

    void displayAsOpen(Integer x, Integer y, Integer numAdjacentMines);

    void displayAsFlagged(Integer x, Integer y);

    void displayAsMine(Integer x, Integer y);

    void displayRemainingFlagsMessage(Integer remaining, Integer total);

    void displayVictoryMessage();

    void displayDefeatMessage();

    void updateScoreDisplay(Integer score);

    void restartUI();

}
