package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PacMan extends Object {

	boolean vunerability;

	public BufferedImage picStill, picUp, picDown, picLeft, picRight;

	public PacMan(Game game) {
		super(game);

		setDefaults();
		getPlayerImage();
	}

	public void setDefaults() {
		x = 100;
		y = 100;
		speed = 3; // 3 * 60 per second
		rotation = right;
	}

	public void getPlayerImage() {
		// load sprites
		try { 
			picDown = ImageIO.read(getClass().getResourceAsStream("/pacman/PacMan_open_down.png"));
			picUp = ImageIO.read(getClass().getResourceAsStream("/pacman/PacMan_open_up.png"));
			picRight = ImageIO.read(getClass().getResourceAsStream("/pacman/PacMan_open_right.png"));
			picLeft = ImageIO.read(getClass().getResourceAsStream("/pacman/PacMan_open_left.png"));
			picStill = ImageIO.read(getClass().getResourceAsStream("/pacman/PacMan_closed.png"));

		} catch(IOException e) {
			e.printStackTrace();
		} 
	}

	public void move() {
		switch(rotation) {
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

	public void draw(Graphics2D g2) {
        BufferedImage image = picRight;

		switch(rotation) {
			case up:
				image = picUp;
				break;
			case right:
				image = picRight;
				break;
			case down:
				image = picDown;
				break;
			case left:
				image = picLeft;
				break;
			default:
				break;
		}
		g2.drawImage(image, x, y,game.tileSize, game.tileSize, null);
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
