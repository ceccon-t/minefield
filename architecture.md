# Minefield - Architecture

## Structure

### Project meta files
The main folder of the project is named `minefield`, and it is present at the root of the repository. Also at the root, `README.md` gives a short presentation at a project level, while this `architecture.md` file should quickly put any developer up to speed as to how the code is laid out. 

### Code overview
The code is structured in an MVC fashion, with the classes that model the core of the entities of the game inside the `model` package, the classes that handle the graphical display under `view` package and the interaction between them handled by the classes in `controller` package. Class `App` is the entry point of the system and does the basic initialization of the desired UI (only Swing available for now) and the Controller, before handing it over for it to start the game.

The logic for the UI and for the core rules of the game interact through two interfaces: `IOEngine` and `PlayerActionHandler` (under `view` and `controller` packages, respectively). 

### Model
`Cell` is the basic unit of action of the game, its responsibility is to know whether they contain a mine or not, how many mines are adjacent to them, and their current state (open, flagged, hidden). `Field` is responsible for storing a grid of Cells and handling the logic that deals with the relationship between Cells - that is, providing all the adjacent cells for a given one, informing all adjacent cells that a mine has been set near them, so on. `Game` uses these classes to deal with the core rules of the game, such as detecting end game situations, keeping track of the time and applying players' attempts to open and flag cells. In order to keep track of the time the class exposes a `tickTime()` method, which should be called by the controller so as to dettach it from the details of managing timers or clocks in code.

There can be many algorithms to populate a Field with mines (although currently only a simple random one is implemented), and any alternatives should implement the `MineSeeder` interface. When implementing one, it is important to make sure to use the Field class service to set mines, as it guarantees that adjacent cells are informed so that they always know how many mines are adjacent to them.

### View
Any "frontend" system for the game must implement the `IOEngine` interface and register itself on `IOEngines` and `IOEngineFactory` - as long as semantically correct implementations for all methods of that interface are provided, the game should work for any kind of UI. 

UI engines should rely on an implementation of `PlayerActionHandler` in order to communicate any action back to the core logic of the game, such as opening/flagging cells, restarting the game, changing the difficulty, so on. It is their responsibility to convert concrete interactions (i.e., a right-button click on a grid square) to semantic actions (i.e., "flagging" a cell) and to call the appropriate handler method of the interface.

#### View - Swing
Currently, the only implementation is for a graphical desktop application, using the Swing framework, and its code can be found under the `view.swing` package. It consists mostly of classes that extend JPanel to implement the graphical elements, and to trigger player actions when they receive some input.

Classes under the package `graphical.menu` handle most of the configuration of the game, such as changing difficulty or the language. In order to provide more than one language, the Observer pattern is used, with a `LanguageProvider` object acting as the subject and any class that is responsible for displaying any text in the screen acting as an observer and being informed whenever the language changes. 

### Controller
Class `Controller` is responsible for creating and setting up games, as well as handling the interaction between the view and the core logic of the game. For the latter, it implements the `PlayerActionHandler` interface of the package `controller.action`, on which any view engine should depend.

One point that might be improved in the future is that currently Controller uses a Swing Timer to act as clock, instead of some other agnostic device. The basic timers offered by core Java seem to be an overkill of complexity, so a simpler, while still agnostic, solution would be preferable.

`DifficultyConfiguration` is a single source of truth for the size of the grid and how many mines there are for any given difficulty.

## Automated Tests

Tests are written with JUnit 5, and the source files can be found under `minefield/src/test` hierarchy. The bulk of the tests cover the classes that implement the core logic of the game, so those under the `model` package.

To execute all tests, run `mvn test` on the main folder of the application.

## Continuous Integration

The project uses Github Actions to run all automated tests whenever a new commit enters either the `main` or `dev` branches. The script that defines the workflow can be found under `.github/workflows/main-workflow.yml`.

If any test is broken, the build fails and a red failure sign is displayed near the hash of the commit in the repository. If all tests pass, a green success sign is displayed instead. A badge with the status of the last build for the main branch is also displayed in the Readme of the project.

## Libraries and Frameworks

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[JUnit](https://junit.org/junit5/docs/current/user-guide/) for automated tests.

[Github Actions](https://docs.github.com/en/actions/learn-github-actions) for continuous integration.

