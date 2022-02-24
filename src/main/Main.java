package main;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();

        // default window settings
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PacMan");
        window.setBackground(Color.black);

        // initialize game object
        Game game = new Game(30, 20);
        game.setupGame();

        // add components to window
        window.add(game);

        // resize window to fit to jpanel of game object
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // starting game thread
        game.startGameThread();
    }
    
}