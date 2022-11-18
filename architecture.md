# Minefield - Architecture

## Structure

The code is structured in an MVC fashion, with the classes that model the core of the entities of the game inside the `model` package, the classes that handle the graphical display under `view` package and the interaction between them handled by the classes in `controller` package. Class `App` is the entry point of the system, but all the initialization happens inside the constructor of class `Controller`, which it queues up right away.

## Libraries and Frameworks

[Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) as a build automation tool.

[Swing](https://docs.oracle.com/javase/tutorial/uiswing/) as the GUI framework.

[JUnit](https://github.com/junit-team/junit4/wiki/Getting-started) for automated tests.

