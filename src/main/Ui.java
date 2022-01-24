package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Ui {
    
    Game game;
    JButton startButton = new JButton();
    JButton stopButton = new JButton();

    public Ui(Game game) {
        this.game = game;

        // edit start button
        this.startButton.setText("Start");
        this.startButton.setBounds(game.screenWidth - 200, game.screenHight - 150, 150, 50);
        this.startButton.setVisible(false);
        this.startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        });
        game.add(startButton);

        // edit stop button
        this.stopButton.setText("Stop");
        this.stopButton.setBounds(50, game.screenHight + 150, 150, 50);
        this.stopButton.setVisible(false);
        game.add(stopButton);
    }

    public void draw(Graphics2D g2) {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("TPS:" + Integer.toString(game.currentTPS), 10, 30);

        g2.drawString("FPS:" + Integer.toString(game.currentFPS), 10, 55);

        // draw text for button
        g2.drawString("Start", game.screenWidth - 200, game.screenHight - 150);
        this.startButton.setVisible(true);
        this.stopButton.setVisible(true);

    }
    
}
