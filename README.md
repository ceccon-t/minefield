# Minefield

![Build status](https://github.com/ceccon-t/minefield/actions/workflows/main-workflow.yml/badge.svg "Build status")

![New Game on Intermediate](https://raw.githubusercontent.com/ceccon-t/minefield/main/images/minefield_sc0.png "New Game on Intermediate")

## Description

Clone of the classic Minesweeper game.

Given a field composed of a grid of cells, the goal of the player is to open the cells that they consider safe, and flag those that they think might contain mines. The player has as many flags to use as there are mines, so that if they place one in an incorrect cell, they will not be able to cover all mines. If the player attempts to open a cell that contains a mine, they lose. If the player is able to explore all of the cells, by opening and flagging them correctly, they win. The amount of seconds elapsed since the game started is displayed as well, allowing the player to check how quickly they were able to finish.

There are three difficulty modes (Beginner, Intermediate and Expert), each having a specific grid size and number of mines.

Also, there are two available languages: English and Portuguese.

Difficulty and language can be changed through the menu bar in the application.

![Defeat on Expert](https://raw.githubusercontent.com/ceccon-t/minefield/main/images/minefield_sc1.png "Defeat on Expert")

![Victory on Beginner](https://raw.githubusercontent.com/ceccon-t/minefield/main/images/minefield_sc2.png "Victory on Beginner")

## How to play

Left-click to open cells.

Right-click to flag cells.

Clicking on "New game" or changing the difficulty restarts the game.

## How to run 

The game is written in Java, so you will need to have the Java runtime installed. Assuming it has alread been installed, either download the Jar file from the latest entry in the [Releases](https://github.com/ceccon-t/minefield/releases) section in this repository or build the project following the instructions below, and execute it. 

## How to build the project

This is a simple Maven project, so the easiest way to build it is running `mvn clean package` in the minefield folder (assuming Maven is installed - if not, check the section about libraries and frameworks on `architecture.md` to get a link to its site and install from there). A jar file containing everything the application needs to run will be created at `minefield/target/Minefield.jar`.

## How to run the automated tests

The simplest way is to run `mvn test` on the main folder of the application.

## More info

To get a short intro to how the code is organized, you can check `architecture.md`.
