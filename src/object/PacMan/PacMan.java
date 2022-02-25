package object.PacMan;

import main.Game;
import object.Object;
import object.PacMan.animation.PacManAnimController;
import object.collision.Collider;

import java.awt.Graphics2D;

import listener.KeyHandler;

public class PacMan extends Object {

	// variables
	boolean vunerability;
	public int lifes;
	public double empEnterTime;

	// instances
	KeyHandler keyHand;

	public PacMan(Game game, KeyHandler keyHand2, int x, int y) {
		super(game);
		this.keyHand = keyHand2;

		// used to identify the next tile in front
		this.nextTile = -1;

		// lifes
		this.lifes = 3;

		// starting position
		this.startX = x * game.tileSize;
		this.startY = y * game.tileSize;
		this.x = x * game.tileSize;
		this.y = y * game.tileSize;

		// movement viriables
		speed = 4; // 3 * 60 per second
		rotation = right;
		this.step = (int) (game.tileSize * speed / game.FPS);
		this.isVunerable = true;
		
		// test step if step size multiplied for one tile modulo tilesize matches 0, importend for patch tracking note: could be moved to object class
		boolean stepApproved = approveStepSize();
		if (!stepApproved) {
			if (step != 0){
				this.step--;
			} else {
				this.step = 1;
			}
		}

		// add collision box
		int offset = 3;
		this.collider = new Collider(this, game.colliderPacManName);
		collider.changeBoxSize(offset * game.getScale(), offset * game.getScale(), game.tileSize - offset * 2 * game.getScale());

		// animation controller
		this.animControll = new PacManAnimController(this, game);

	}

	// called on death
	@Override
	public void dieEvent() {
		this.x = startX;
		this.y = startY;
		this.rotation = right;
		game.gameState = game.respawnState;
	}

	// called to move pacman
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

			// get next tile in front of pacman
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
			// coin or empty or powerpill tile
			if (this.nextTile == game.tileEmpty || this.nextTile == game.tileCoin || this.nextTile == game.tilePowerPill) {
				this.setRotation(this.nextRotation);
			}

			// only coin
			if (this.nextTile == game.tileCoin) {
				game.pointCounter += 100;
			}
			
		}

		// movement rotates pacman
		if (this.x % game.tileSize == 0 && this.y % game.tileSize == 0) {
			// calculate matching index in gameField
			int indexWidth = x / game.tileSize;
			int indexHight = y / game.tileSize;

			if (rotation == up) {
				this.nextTile = game.getTile(indexWidth, indexHight - 1);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight - 1][indexWidth] = game.tileEmpty;
					game.pointCounter++;
				}
				if (this.nextTile == game.tilePowerPill) {
					game.gameTiles[indexHight - 1][indexWidth] = game.tileEmpty;
				}
			}
			if (rotation == right) {
				this.nextTile = game.getTile(indexWidth + 1, indexHight);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight][indexWidth + 1] = game.tileEmpty;
					game.pointCounter++;
				}
				if (this.nextTile == game.tilePowerPill) {
					game.gameTiles[indexHight][indexWidth + 1] = game.tileEmpty;
				}
			}
			if (rotation == down) {
				this.nextTile = game.getTile(indexWidth, indexHight + 1);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight + 1][indexWidth] = game.tileEmpty;
					game.pointCounter++;
				}
				if (this.nextTile == game.tilePowerPill) {
					game.gameTiles[indexHight + 1][indexWidth] = game.tileEmpty;
				}
			}
			if (rotation == left) {
				this.nextTile = game.getTile(indexWidth - 1, indexHight);
				if (this.nextTile == game.tileCoin) {
					game.gameTiles[indexHight][indexWidth - 1] = game.tileEmpty;
					game.pointCounter++;
				}
				if (this.nextTile == game.tilePowerPill) {
					game.gameTiles[indexHight][indexWidth - 1] = game.tileEmpty;
				}
			}
		}

		// moves pacman forward
		if (this.nextTile == game.tileEmpty || this.nextTile == game.tileCoin || this.nextTile == game.tilePowerPill) {
			switch(this.rotation) {
				case up:
					this.y -= step;
					this.objectState = stateMoveUp;
					break;
				case right:
					this.x += step;
					this.objectState = stateMoveRight;
					break;
				case down:
					this.y += step;
					this.objectState = stateMoveDown;
					break;
				case left:
					this.x -= step;
					this.objectState = stateMoveLeft;
					break;
				default:
					break;
			}
		}

		// power pill
		if (this.nextTile == game.tilePowerPill) {
			empowered();
			game.pointCounter += 1001;
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

		// anim update
		animControll.update();

	}

	// called to empowere pacman
	public void empowered() {
		empEnterTime = game.timer.getTimeInSeconds();
		game.pacManEmpowered();
		this.isEmpowered = true;
	}

	// draw sprites
	@Override
	public void draw(Graphics2D g2) {

		// animation
        animControll.draw(g2);

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

	// called on collision enter
	@Override
	public void collisionEnter(Collider col) {
		if (isVunerable) {
			// go into if statement if other collider is ghost
			if (col.name == game.colliderGhostName) {
				if (lifes > 1) {
					this.lifes--;
					dieEvent();
				} else {
					game.gameState = game.looseState;
				}
			}
		} else {
			// deletes ghost if eaten
			if (col.name == game.colliderGhostName) {
				col.parent.dieEvent();
				game.pointCounter += 5;
			}
		}
	}

}
