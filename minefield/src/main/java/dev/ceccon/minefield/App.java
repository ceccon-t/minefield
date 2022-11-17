package dev.ceccon.minefield;

import dev.ceccon.minefield.controller.Controller;

import javax.swing.*;

public class App {
    public static void main( String[] args ) {
        System.out.println( "MINEFIELD - Starting game" );
        SwingUtilities.invokeLater(Controller::new);
    }
}
