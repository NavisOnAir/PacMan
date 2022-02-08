package graphics.animation;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import main.Game;

import java.io.IOException;
import java.awt.Graphics2D;

public class Animation {

    Game game;

    // attributes
    public String name;
    int duration; // in times per second

    // counters
    int currentFrame = 0; // frame counter to indicate a sprite switch
    int currentSprite = 0;

    // sprites
    BufferedImage[] sprites;

    public Animation(Game game, String name, int duration, String[] spritePaths) {
        this.game = game;
        this.name = name;
        this.duration = duration;

        // fetch sprites
        loadSprites(spritePaths);

    }

    void loadSprites(String[] spritePaths) {
        this.sprites = new BufferedImage[spritePaths.length];

        for (int i = 0; i < spritePaths.length; i++) {
            try {
                sprites[i] = ImageIO.read(getClass().getResourceAsStream(spritePaths[i]));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("<<< Failed to load sprite in: " + spritePaths[i] + " >>>");
            }
        }
    }

    // must be called every frame
    public void update() {
        if (currentFrame >= Math.round(game.FPS / duration)) {
            this.currentSprite++;

            // checks if currentsprite isnt out of bounds of sprites[]
            if (currentSprite == sprites.length) {
                this.currentSprite = 0;
            }
            this.currentFrame = 0;
        }
        this.currentFrame++;

    }

    // draws sprite on screen
    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(sprites[currentSprite], x, y, game.tileSize, game.tileSize, null);

    }
    
}