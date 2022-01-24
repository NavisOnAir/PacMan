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

        final int width = game.getWidth();
        final int hight = game.getHight();

        // add components to window
        window.add(game);

        window.setSize(width, hight);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.startGameThread();
    }
    
}
