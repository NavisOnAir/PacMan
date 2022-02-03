package object;

import main.Game;
import object.collision.Collider;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Ghost extends Object {

    Color color;

    // sprites
    BufferedImage spriteRedOne, spriteRedTwo, spriteBlueOne, spriteBlueTwo, spriteYellowOne, spriteYellowTwo, spriteGreenOne, spriteGreenTwo;

    // timer
    int releaseTime;

    public Ghost(Game game, int x, int y, int releaseTime) {
        super(game);
        this.x = x * game.tileSize;
        this.y = y * game.tileSize;
        this.releaseTime = releaseTime;
        this.rotation = this.up;
        this.nextRotation = this.down;
        this.speed = 3;

        // add collider
        this.collider = new Collider(this, game.colliderGhostName);

        loadSprites();
    } 

    public void move() {

        // movement algorithm
        if (nextRotation == rotation) {
            boolean direction = randomPathFinding(); // false = left; true = right
            switch (rotation) {
                case up:
                    if (direction) {
                        this.nextRotation = right;
                    } else {
                        this.nextRotation = left;
                    }
                    break;
                case right:
                    if (direction) {
                        this.nextRotation = down;
                    } else {
                        this.nextRotation = up;
                    }
                    break;
                case down:
                    if (direction) {
                        this.nextRotation = left;
                    } else {
                        this.nextRotation = right;
                    }
                    break;
                case left:
                    if (direction) {
                        this.nextRotation = up;
                    } else {
                        this.nextRotation = down;
                    }
                    break;
            }
        }

        // tile checking
        if (game.timer.getTimeInSeconds() >= releaseTime) {
             // current position in tile grid
            int indexWidth = x / game.tileSize;
            int indexHight = y / game.tileSize;

            if (this.x % game.tileSize == 0 && this.y % game.tileSize == 0) {
                // get next tile in nextRotation

                if (nextRotation == up) {
                    this.nextTile = game.getTile(indexWidth, indexHight - 1);
                }
                if (nextRotation == right) {
                    this.nextTile = game.getTile(indexWidth + 1, indexHight);
                }
                if (nextRotation == down) {
                    this.nextTile = game.getTile(indexWidth, indexHight + 1);
                }
                if (nextRotation == left) {
                    this.nextTile = game.getTile(indexWidth - 1, indexHight);
                }
    
                // tile specific operations
                if (this.nextTile != game.tileWall) {
                    this.setRotation(this.nextRotation);
                }

                // get tile in rotation
                if (rotation == up) {
                    this.nextTile = game.getTile(indexWidth, indexHight - 1);
                }
                if (rotation == right) {
                    this.nextTile = game.getTile(indexWidth + 1, indexHight);
                }
                if (rotation == down) {
                    this.nextTile = game.getTile(indexWidth, indexHight + 1);
                }
                if (rotation == left) {
                    this.nextTile = game.getTile(indexWidth - 1, indexHight);
                }
            }

            if (this.nextTile != game.tileWall) {
                // moving object
                switch (this.rotation) {
                    case up:
                        this.y -= speed;
                        break;
                    case right:
                        this.x += speed;
                        break;
                    case down:
                        this.y += speed;
                        break;
                    case left:
                        this.x -= speed;
                        break;
                }
            } else {
                this.rotation = nextRotation;
            }
        }
    }

    public void loadSprites() {
        try {
            spriteRedOne = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_red_one.png"));
            spriteRedTwo = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_red_two.png"));
            spriteBlueOne = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_blue_one.png"));
            spriteBlueTwo = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_blue_two.png"));
            spriteGreenOne = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_green_one.png"));
            spriteGreenTwo = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_green_two.png"));
            spriteYellowOne = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_yellow_one.png"));
            spriteYellowTwo = ImageIO.read(getClass().getResourceAsStream("/sprites/ghost/ghost_yellow_two.png"));
        } catch(IOException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void draw(Graphics2D g2) {

    }

    private boolean randomPathFinding() { // false = left; true = right
        int randomValue = (int) (Math.random() * 2);
        if (randomValue == 1) {
            return true;
        } else {
            return false;
        }
    }
    
}
