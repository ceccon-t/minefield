# Minefield - Architecture

## Structure

The code is structured in an MVC fashion, with the classes that model the core of the entities of the game inside the `model` package, the classes that handle the graphical display under `view` package and the interaction between them handled by the classes in `controller` package. Class `App` is the entry point of the system, but all the initialization happens inside the constructor of class `Controller`, which it queues up right away.

The logic for the UI and for the core rules of the game interact through two interfaces: `IOEngine` and `PlayerActionHandler` (under `view` and `controller` packages, respectively). 

### View
Any "frontend" system for the game must implement the `IOEngine` interface and register itself on `IOEngines` and `IOEngineFactory` - as long as semantically correct implementations for all methods of that interface are provided, the game should work for any kind of UI. Currently, the only implementation is for a graphical desktop application, using the Swing framework, and it is under the `view.swing` package.

UI engines should rely on an implementation of `PlayerActionHandler` in order to communicate any action back to the core logic of the game, such as opening/flagging cells, restarting the game, changing the difficulty, so on. It is their responsibility to convert concrete interactions (i.e., a right-button click on a grid square) to semantic actions (i.e., "flagging" a cell) and to call the appropriate handler method of the interface.

### Core
More info soon...


## Libraries and Frameworks

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[JUnit](https://junit.org/junit5/docs/current/user-guide/) for automated tests.

