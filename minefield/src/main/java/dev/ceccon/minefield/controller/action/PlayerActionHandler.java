package dev.ceccon.minefield.controller.action;

import dev.ceccon.minefield.controller.difficulty.Difficulty;
import dev.ceccon.minefield.controller.action.PlayerAction;

public interface PlayerActionHandler {

    void handlePlayerActionOnCell(PlayerAction action, int x, int y);

    void handlePlayerRestart();

    void handlePlayerChangeDifficulty(Difficulty difficulty);

}
