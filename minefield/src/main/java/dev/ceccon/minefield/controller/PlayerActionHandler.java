package dev.ceccon.minefield.controller;

import dev.ceccon.minefield.constants.Difficulty;
import dev.ceccon.minefield.constants.PlayerAction;

public interface PlayerActionHandler {

    void handlePlayerActionOnCell(PlayerAction action, int x, int y);

    void handlePlayerRestart();

    void handlePlayerChangeDifficulty(Difficulty difficulty);

}
