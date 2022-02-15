package graphics.animation;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.Game;

import java.util.ArrayList;
import java.awt.Graphics2D;

public class Animation {

    Game game;

    // attributes
    public String name;
    double duration; // in seconds

    // counters
    public int currentFrame = 0; // frame counter to indicate a sprite switch
    public int currentSprite = 0;
    int spriteSwitchFrame;

    // sprites
    ArrayList<BufferedImage> spriteList = new ArrayList<BufferedImage>();

    public Animation(Game game, String name, double duration, String[] spritePaths) {
        this.game = game;
        this.name = name;
        this.duration = duration; // in seconds

        // calculate the frame to switch between sprites
        this.spriteSwitchFrame = (int) (duration * game.FPS / spritePaths.length);
        if (spriteSwitchFrame == 0) {
            this.spriteSwitchFrame = -1;
        }
        if (spriteSwitchFrame <= 0) {
            System.out.println("<<< Error Sprite switch counter in Animation is 0 or less >>>");
        }

        // fetch sprites
        loadSprites(spritePaths);

    }

    void loadSprites(String[] spritePaths) {
        for (int i = 0; i < spritePaths.length; i++) {
            try {
                this.spriteList.add(ImageIO.read(getClass().getResourceAsStream(spritePaths[i])));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("<<< Failed to load sprite in: " + spritePaths[i] + " >>>");
            }
        }
    }

    // must be called every frame
    public void update() {

        // switch sprite after spriteSwitchFrame reached
        if (currentFrame % spriteSwitchFrame == 0) {
            if (currentSprite == spriteList.size() - 1) {
                currentSprite = 0;
            } else {
                currentSprite++;
            }
        }
        currentFrame++;
    }

    // draws sprite on screen
    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(spriteList.get(currentSprite), x, y, game.tileSize, game.tileSize, null);

    }
    
}