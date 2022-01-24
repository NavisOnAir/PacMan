package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import javax.swing.JButton;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ui {
    
    Game game;
    JButton startButton = new JButton();
    JButton stopButton = new JButton();

    public int startButtonX;
    public int startButtonY;

    // tile images
    public BufferedImage sprEmpty, sprWall, sprCoin, sprDoor;

    // standard font
    public Font defaultFont = new Font("Courier", Font.PLAIN, 20);

    public Ui(Game game) {
        this.game = game;
    }

    public void drawIngame(Graphics2D g2) {

        // tiles drawing
        try { 
			sprEmpty = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/empty.png"));
            sprWall = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/wall.png"));
            sprCoin = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/coin.png"));
            sprDoor = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/door.png"));

		} catch(IOException e) {
			e.printStackTrace();
		} 

        for (int i = 0; i < game.gameTiles.length; i++) {
            for (int j = 0; j < game.gameTiles[0].length; j++) {
                int tileCode = game.gameTiles[i][j];
                if (tileCode == game.tileEmpty) {
                    g2.drawImage(sprEmpty, j * game.tileSize, i * game.tileSize, game.tileSize, game.tileSize, null);
                }
                if (tileCode == game.tileWall) {
                    g2.drawImage(sprWall, j * game.tileSize, i * game.tileSize, game.tileSize, game.tileSize, null);
                }
                if (tileCode == game.tileCoin) {
                    g2.drawImage(sprCoin, j * game.tileSize, i * game.tileSize, game.tileSize, game.tileSize, null);
                }
                if (tileCode == game.tileDoor) {
                    g2.drawImage(sprDoor, j * game.tileSize, i * game.tileSize, game.tileSize, game.tileSize, null);
                }
            }
        }

        // overlay ui drawing
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("TPS:" + Integer.toString(game.currentTPS), 10, 30);

        g2.drawString("FPS:" + Integer.toString(game.currentFPS), 10, 55);


    }

    public void drawTitle(Graphics2D g2) {

        // Start text
        String str = "Start";
        int sX = getXForCenteredText(defaultFont.deriveFont(40f), str);
        int sY = Math.round(game.screenHight - game.screenHight * 0.7f);

        this.startButtonX = sX;
        this.startButtonY = sY;

        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(40f));
        g2.drawString(str, sX, sY);
        this.startButton.setVisible(true);
        this.stopButton.setVisible(true);

    }

    public int getXForCenteredText(Font font, String str) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = font.getStringBounds(str, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rX = (int) Math.round(r2D.getX());
        int x = (game.screenWidth / 2) - (rWidth / 2) - rX;
        
        return x;
    }
    
}
