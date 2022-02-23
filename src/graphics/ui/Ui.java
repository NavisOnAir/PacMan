package graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import main.Game;
import object.Object;

public class Ui {
    
    // instances
    Game game;
    Object object = new Object(game);

    // utility
    FontRenderContext frc = new FontRenderContext(null, true, true);

    // start button cords
    public int startButtonX;
    public int startButtonY;

    // stop button cords
    public int stopButtonX;
    public int stopButtonY;

    // level select button cords/levels
    public int levelSelectButtonX;
    public int levelSelectButtonY;

    public int[] levelButtonX = new int[10];
    public int[] levelButtonY = new int[10];
    public String[] levelStrings = new String[10];
 
    // tile images
    public BufferedImage sprEmpty, sprWall, sprCoin, sprDoor, sprPowerPill;

    // standard font
    public Font defaultFont = new Font("Courier", Font.PLAIN, 20);

    // display blinking
    private boolean isTimer = false;
    private boolean isLifes = false;

    public Ui(Game game) {
        this.game = game;
    }

    // ui`s
    // drawing ingame ui with tiles and palyer
    public void drawIngame(Graphics2D g2) {

        // tiles drawing
        try { 
			sprEmpty = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/empty.png"));
            sprWall = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/wall.png"));
            sprCoin = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/coin.png"));
            sprDoor = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/door.png"));
            sprPowerPill = ImageIO.read(getClass().getResourceAsStream("/sprites/tiles/powerpill_1.png"));

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
                if (tileCode == game.tilePowerPill) {
                    g2.drawImage(sprPowerPill, j * game.tileSize, i * game.tileSize, game.tileSize, game.tileSize, null);
                }
            }
        }

        // interface drawing
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("TPS:" + Integer.toString(game.currentTPS), 10, 30);

        g2.drawString("FPS:" + Integer.toString(game.currentFPS), 10, 55);

        // stop button
        String str = "Stop";
        float fontSize = 30f; // changing this needs to change it in mousehandler too
        int sX = getXForCenteredText(defaultFont.deriveFont(fontSize), str);
        int sY = game.getHeight();

        this.stopButtonX = sX;
        this.stopButtonY = sY;

        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));
        g2.drawString(str, sX, sY);

        // timer
        long currentTimePassed = game.timer.getTimeInNanoSeconds();
        double currentSeconds = (double) currentTimePassed / 1000000000;
        DecimalFormat f = new DecimalFormat("##.00");
        String strTimer = "Time: " + f.format(currentSeconds);
        int tX = 50;
        int tY = game.getHeight();

        // life counter
        String strLifes = "Lifes: " + game.pacMan.lifes;
        int lcX = game.getWidth() - 150;
        int lcY = game.getHeight();
        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));

        // point counters
        String strPoints = "Points: " + game.pointCounter;
        int pcX = Math.round(game.getWidth() * 1 / 4);
        int pcY = game.getHeight();
        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));

        // blinking ui (timer, lifes, point counter)
        if (game.gameState == game.ingameState) {
            g2.drawString(strTimer, tX, tY);
            g2.drawString(strLifes, lcX, lcY);
            g2.drawString(strPoints, pcX, pcY);
        } else if (game.gameState == game.pauseState || game.gameState == game.respawnState) {
            if (game.currentFrame % 30 == 0) {
                this.isTimer = !this.isTimer;
                this.isLifes = !this.isLifes;
            }
            if (this.isTimer) {
                g2.drawString(strTimer, tX, tY);
                g2.drawString(strLifes, lcX, lcY);
                g2.drawString(strPoints, pcX, pcY);
            }
        }


    }

    // title screen
    public void drawTitle(Graphics2D g2) {

        // start buttom
        String str = "Start";
        float fontSize = 40f; // changing this needs to change it in mousehandler too
        int sX = getXForCenteredText(defaultFont.deriveFont(fontSize), str);
        int sY = Math.round(game.getHeight() - game.getHeight() * 0.7f);

        this.startButtonX = sX;
        this.startButtonY = sY;

        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));
        g2.drawString(str, sX, sY);

        // level select button
        str = "Select Level";
        int lsX = getXForCenteredText(defaultFont.deriveFont(fontSize), str);
        int lsY = Math.round(game.getHeight() - game.getHeight() * 0.6f);

        this.levelSelectButtonX = lsX;
        this.levelSelectButtonY = lsY;

        g2.drawString(str, lsX, lsY);

        // level selected display
        fontSize = 20f; // changing this needs to change it in mousehandler too
        str = "Level: " + game.levelSelected;
        int llX = Math.round(game.getWidth() - game.getWidth() * 0.3f);
        int llY = game.getHeight();

        g2.setFont(defaultFont.deriveFont(fontSize));
        g2.drawString(str, llX, llY);

    }

    // pause screen
    public void drawPause(Graphics2D g2) {

        // start button
        String str = "Start";
        float fontSize = 50f; // changing this needs to change it in mousehandler too
        int sX = getXForCenteredText(defaultFont.deriveFont(fontSize), str);
        int sY = Math.round(game.getHeight() - game.getHeight() * 0.3f);
        

        this.startButtonX = sX;
        this.startButtonY = sY;

        // start button background
        int rectWidth = (int) Math.round(defaultFont.deriveFont(fontSize).getStringBounds(str, frc).getWidth());
        int rectHeight = (int) Math.round(defaultFont.deriveFont(fontSize).getStringBounds(str, frc).getHeight() / 2);
        g2.setColor(Color.black);
        g2.fillRect(sX, sY - rectHeight, rectWidth, rectHeight);

        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));
        g2.drawString(str, sX, sY);
    }

    // won screen
    public void drawWonScreen(Graphics2D g2) {
        // letter size
        float letterSize = 30f;
        String points = "Your Points: " + Integer.toString(game.pointCounter);
        String time = String.valueOf(game.timer.getTimeInSeconds()) + " s";

        // position of strings
        int pointsStrX = getXForCenteredText(defaultFont.deriveFont(letterSize), points);
        int pointsStrY = Math.round(game.getHeight() * 0.3f);
        int timeStrX = getXForCenteredText(defaultFont.deriveFont(letterSize), time);
        int timeStrY = Math.round(game.getHeight() * 0.4f);

        // set drawing settings
        g2.setFont(defaultFont.deriveFont(letterSize));
        g2.setColor(Color.white);

        // draw strings
        g2.drawString(points, pointsStrX, pointsStrY);
        g2.drawString(time, timeStrX, timeStrY);
    }

    // level select screen
    public void drawLevelSelection(Graphics2D g2) {
        // drawing variables
        String[] levels = game.utils.getFilesInDirectory("levels/");
        int x = 50;
        int y = 50;
        int step = Math.round((game.getHeight() - y * 2) / 10);
        int j = 0;
        float fontSize = 30f; // changing this needs to change it in mousehandler too

        g2.setColor(Color.white);
        g2.setFont(defaultFont.deriveFont(fontSize));

        for (int i = y; i < y * step * 10; i += step) {
            if (j >= levels.length || j >= 10) {
                return;
            }
            this.levelButtonX[j] = x;
            this.levelButtonY[j] = y;
            this.levelStrings[j] = levels[j];
            g2.drawString(levels[j], x, y);
            y += step;
            j++;
        }
    }



    // ui methods
    public int getXForCenteredText(Font font, String str) {
        FontRenderContext frc = new FontRenderContext(null, true, true);
        Rectangle2D r2D = font.getStringBounds(str, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rX = (int) Math.round(r2D.getX());
        int x = (game.getWidth() / 2) - (rWidth / 2) - rX;
        
        return x;
    }
    
}
