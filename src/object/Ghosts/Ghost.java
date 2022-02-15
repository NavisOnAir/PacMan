package object.Ghosts;

import main.Game;
import object.Object;
import object.Ghosts.animation.GhostAnimController;
import object.collision.Collider;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Ghost extends Object {

    // sprites
    BufferedImage spriteRedOne, spriteRedTwo, spriteBlueOne, spriteBlueTwo, spriteYellowOne, spriteYellowTwo, spriteGreenOne, spriteGreenTwo;
    public int color;

    // color constants
    public final int colorRed = 0;
    public final int colorGreen = 1;
    public final int colorBlue = 2;
    public final int colorYellow = 3;

    // timer
    int releaseTime;

    // life state
    public int lifeState;
    public final int aliveState = 0;
    public final int deadState = 1;

    // empowered
    public int lastStep;

    public Ghost(Game game, int x, int y, int releaseTime, int color) {
        super(game);
        this.startX = x * game.tileSize;
        this.startY = y * game.tileSize;
        this.x = x * game.tileSize;
        this.y = y * game.tileSize;
        this.releaseTime = releaseTime;
        this.rotation = this.up;
        this.nextRotation = this.down;
        this.color = color;

        // movement viriables
		speed = 4; // 3 * 60 per second
		rotation = right;
		this.step = (int) (game.tileSize * speed / game.FPS);
        this.lastStep = step;
        this.lifeState = aliveState;
		
		// test step
		boolean stepApproved = approveStepSize();
		if (!stepApproved) {
			if (step != 0){
				this.step--;
			} else {
				this.step = 1;
			}
		}

        // add collider
        int offset = 2;
        this.collider = new Collider(this, game.colliderGhostName);
        this.collider.changeBoxSize(offset * game.scale, offset * game.scale, game.tileSize - offset * 2 * game.scale);

        // animation
        this.animControll = new GhostAnimController(this, game, this);
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
                        this.y -= step;
                        break;
                    case right:
                        this.x += step;
                        break;
                    case down:
                        this.y += step;
                        break;
                    case left:
                        this.x -= step;
                        break;
                }
            } else {
                this.rotation = nextRotation;
            }
        }

        // animation
        animControll.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        // animation
        animControll.draw(g2);

        // debug
        if (game.isDebugMode) {
            drawDebug(g2);
        }
    }

    private boolean randomPathFinding() { // false = left; true = right
        int randomValue = (int) (Math.random() * 2);
        if (randomValue == 1) {
            return true;
        } else {
            return false;
        }
    }

    // events
    public void dieEvent() {
        this.x = startX;
        this.y = startY;
        this.releaseTime = (int) game.timer.getTimeInSeconds() + 20;
        
    }
}
