package object;

import java.awt.Color;
import java.awt.Graphics2D;

import graphics.animation.AnimationController;
import main.Game;
import object.collision.Collider;

public class Object {
    // object stages
	public final int stateIdle = 0;
	public final int stateMoveUp = 1;
	public final int stateMoveRight = 2;
	public final int stateMoveDown = 3;
	public final int stateMoveLeft = 4;
    
    // stearing, positioning
    public int startX, startY;
    public int x, y;
    public double speed;
    public int nextTile;
    public int step;

    // properties
    public boolean isVunerable = false;
    public boolean isEmpowered = false;

    // animation
    public int spriteNumber = 0;
    public int spriteCounter = 0;
    public int objectState = 0;
    public AnimationController animControll;

    // rotation states
	public final int up = 0;
	public final int right = 1;
	public final int down = 2;
	public final int left = 3;

    // rotations
	public int rotation;
    public int nextRotation;

    // instances
    public Game game;
    public Collider collider;

    // constructor
    public Object(Game game) {
        this.game = game;
    }

    // default draw method draws a white rectangle if not overriden
    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, game.tileSize, game.tileSize);

        // debug mode
        if (game.isDebugMode) {
            drawDebug(g2);
        }
    }

    // debug methods
    // draws white outline of collider and redline in viewing rotation
    public void drawDebug(Graphics2D g2) {
        // orientation / direction as red line
		int[] debugLineCords = getCordsInRotation();
		int[] centeredCords = getCenteredCords();
		g2.setColor(Color.red);
		g2.drawLine(centeredCords[0], centeredCords[1], debugLineCords[0], debugLineCords[1]);

        // collider as white box
        g2.setColor(Color.white);
        if (collider != null) {
            collider.draw(g2);
		
		}
    }

    // set rotation to input
    public void setRotation(int rot) {
		this.rotation = rot;
	}

    // get debug rotation line cords
    public int[] getCordsInRotation() {
        int[] centeredCords = getCenteredCords();
        int x = centeredCords[0];
        int y = centeredCords[1];
        int[] extCords = new int[2];
        if (this.rotation == up) {
            extCords[0] = x;
            extCords[1] = y - game.tileSize;
        }
        if (this.rotation == right) {
            extCords[0] = x + game.tileSize;
            extCords[1] = y;
        }
        if (this.rotation == down) {
            extCords[0] = x;
            extCords[1] = y + game.tileSize;
        }
        if (this.rotation == left) {
            extCords[0] = x - game.tileSize;
            extCords[1] = y;
        }
        return extCords;
    }

    // method called to test step if step size multiplied for one tile modulo tilesize matches 0, importend for patch tracking note: could be moved to object class
    public boolean approveStepSize() {
		for (int i = 0; i <= game.tileSize; i++) {
			if ((step * i) % game.tileSize == 0) {
                if (game.isDebugMode) {
				    System.out.println("<<< Working with stepsize of: " + step + " >>>");
                }
				return true;
			}
		}
		return false;
	}

    // reset coordinates to start coordinates
    public void dieEvent() {
		this.x = startX;
		this.y = startY;
	}

    // returns window coordinates in the middle of the object
    public int[] getCenteredCords() {
        int centeredX = this.x + Math.round(game.tileSize / 2);
        int centeredY = this.y + Math.round(game.tileSize / 2);
        int[] centeredCords = {centeredX, centeredY};
        return centeredCords;
    }

    // called on collision enter
    public void collisionEnter(Collider col) {
        this.collider.collisionEnter(col);
    }

    // called on collision exit
    public void collisionExit(Collider col) {
        this.collider.collisionExit(col);
    }
}
