package object.PacMan;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import object.Object;
import object.collision.Collider;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import listener.KeyHandler;

public class PacMan extends Object {

	boolean vunerability;
	KeyHandler keyHand;

	public BufferedImage picStill, picUp, picDown, picLeft, picRight;

	public PacMan(Game game, KeyHandler keyHand2, int x, int y) {
		super(game);
		this.keyHand = keyHand2;

		this.nextTile = -1;

		// starting position
		this.x = x * game.tileSize;
		this.y = y * game.tileSize;
		speed = 3; // 3 * 60 per second
		rotation = right;

		// add collision box
		this.collider = new Collider(this, game.colliderPacManName);

		// default methods
		getPlayerImage();
	}

	public void getPlayerImage() {
		// load sprites
		try { 
			picDown = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_down.png"));
			picUp = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_up.png"));
			picRight = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_right.png"));
			picLeft = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_left.png"));
			picStill = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_closed.png"));

		} catch(IOException e) {
			e.printStackTrace();
		} 
	}

	public void move() {

		// key handling
		if (keyHand.arrowUp) {
            this.nextRotation = this.up;
        } else if (keyHand.arrowRight) {
            this.nextRotation = this.right;
        } else if (keyHand.arrowDown) {
            this.nextRotation = this.down;
        } else if (keyHand.arrowLeft) {
            this.nextRotation = this.left;
        }

		// passable tile
		if (this.x % game.tileSize == 0 && this.y % game.tileSize == 0) {

			// get next tile
			int indexWidth = x / game.tileSize;
			int indexHight = y / game.tileSize;

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
			if (this.nextTile == game.tileEmpty || this.nextTile == game.tileCoin) {
				this.setRotation(this.nextRotation);
			}
			
		}

		// movement
		if (this.x % game.tileSize == 0 && this.y % game.tileSize == 0) {
			int indexWidth = x / game.tileSize;
			int indexHight = y / game.tileSize;

			if (rotation == up) {
				this.nextTile = game.getTile(indexWidth, indexHight - 1);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight - 1][indexWidth] = game.tileEmpty;
					game.pointCounter++;
				}
			}
			if (rotation == right) {
				this.nextTile = game.getTile(indexWidth + 1, indexHight);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight][indexWidth + 1] = game.tileEmpty;
					game.pointCounter++;
				}
			}
			if (rotation == down) {
				this.nextTile = game.getTile(indexWidth, indexHight + 1);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight + 1][indexWidth] = game.tileEmpty;
					game.pointCounter++;
				}
			}
			if (rotation == left) {
				this.nextTile = game.getTile(indexWidth - 1, indexHight);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight][indexWidth - 1] = game.tileEmpty;
					game.pointCounter++;
				}
			}
		}

		if (this.nextTile == game.tileEmpty || this.nextTile == game.tileCoin) {
			switch(this.rotation) {
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
				default:
					break;
			}
		}
		
		// animation
		spriteCounter++;
		if (spriteCounter >= 20) {
			if (spriteNumber == 0) {
				spriteNumber = 1;
			} else if (spriteNumber == 1) {
				spriteNumber = 0;
			}
			spriteCounter = 0;
		}

	}

	@Override
	public void draw(Graphics2D g2) {

		// animation
        BufferedImage image = picRight;

		switch(rotation) {
			case up:
				if (spriteNumber == 0) {
					image = picUp;
				}
				if (spriteNumber == 1) {
					image = picStill;
				}
				break;
			case right:
				if (spriteNumber == 0) {
					image = picRight;
				}
				if (spriteNumber == 1) {
					image = picStill;
				}
				break;
			case down:
				if (spriteNumber == 0) {
					image = picDown;
				}
				if (spriteNumber == 1) {
					image = picStill;
				}
				break;
			case left:
				if (spriteNumber == 0) {
					image = picLeft;
				}
				if (spriteNumber == 1) {
					image = picStill;
				}
				break;
			default:
				break;
		}
		g2.drawImage(image, x, y, game.tileSize, game.tileSize, null);

		// debug
		if (game.isDebugMode) {
			this.drawDebug(g2);
		}
    }
	

	public int getx() {
		return this.x;
	}
	public int gety() {
		return this.y;
	}

	@Override
	public void collisionEnter(Collider col) {
		game.gameState = game.looseState;
	}

}