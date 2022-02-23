package main;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PacMan");
        window.setBackground(Color.black);

        Game game = new Game(30, 20);
        game.setupGame();

        // final int width = game.getWidth();
        // final int height = game.getHeight() + 29; // +29 to fit with topbar (topbar with exit button is 29 pixel tall)

        // add components to window
        window.add(game);

        // window.setSize(width, height);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // starting game thread
        game.startGameThread();
    }
    
}