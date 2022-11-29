# Minefield - Architecture

## Structure

### Project meta files
The main folder of the project is named `minefield`, and it iis present at the root of the repository. Also at the root, `README.md` gives a short presentation at a project level, while this `architecture.md` file should quickly put any developer up to speed as to how the code is laid out. 

### Code overview
The code is structured in an MVC fashion, with the classes that model the core of the entities of the game inside the `model` package, the classes that handle the graphical display under `view` package and the interaction between them handled by the classes in `controller` package. Class `App` is the entry point of the system, but all the initialization happens inside the constructor of class `Controller`, which it queues up right away.

The logic for the UI and for the core rules of the game interact through two interfaces: `IOEngine` and `PlayerActionHandler` (under `view` and `controller` packages, respectively). 

### Model
`Cell` is the basic unit of action of the game, its responsibility is to know whether they contain a mine or not, how many mines are adjacent to them, and its current state (open, flagged, hidden). `Field` is responsible for storing a grid of Cells and handling the logic that deals with the relationship between Cells - that is, providing all the adjacent cells for a given one, informing all adjacent cells that a mine has been set near them, so on.

There can be many algorithms to populate a Field with mines (although currently only a simple random one is implemented), and any alternatives should implement the `MineSeeder` interface. When implementing one, it is important to make sure to use the `Field` class service to set mines, as it guarantees that adjacent cells are informed so that they always know how many mines are adjacent to them.

`DifficultyConfiguration` is a single source of truth for the size of the grid and how many mines there are for any given difficulty.

### View
Any "frontend" system for the game must implement the `IOEngine` interface and register itself on `IOEngines` and `IOEngineFactory` - as long as semantically correct implementations for all methods of that interface are provided, the game should work for any kind of UI. Currently, the only implementation is for a graphical desktop application, using the Swing framework, and its code can be found under the `view.swing` package.

UI engines should rely on an implementation of `PlayerActionHandler` in order to communicate any action back to the core logic of the game, such as opening/flagging cells, restarting the game, changing the difficulty, so on. It is their responsibility to convert concrete interactions (i.e., a right-button click on a grid square) to semantic actions (i.e., "flagging" a cell) and to call the appropriate handler method of the interface.

### Controller
More info soon...

## Automated Tests

Tests are written with JUnit 5, and the source files can be found under `minefield/src/test` hierarchy. The bulk of the tests cover the classes that implement the core logic of the game, so those under the `model` package.

To execute all tests, run `mvn test` on the main folder of the application.

## Libraries and Frameworks

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[JUnit](https://junit.org/junit5/docs/current/user-guide/) for automated tests.

