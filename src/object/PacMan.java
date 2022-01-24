package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import listener.KeyHandler;

public class PacMan extends Object {

	boolean vunerability;
	KeyHandler keyHand;

	public BufferedImage picStill, picUp, picDown, picLeft, picRight;

	public PacMan(Game game, KeyHandler keyHand2) {
		super(game);
		this.keyHand = keyHand2;

		this.nextTile = -1;

		setDefaults();
		getPlayerImage();
	}

	public void setDefaults() {
		x = 48;
		y = 48;
		speed = 2; // 2 * 60 per second
		rotation = right;
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

	public void draw(Graphics2D g2) {
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
    }
	

	public int getx() {
		return this.x;
	}
	public int gety() {
		return this.y;
	}

	public void setRotation(int rot) {
		this.rotation = rot;
	}
}
