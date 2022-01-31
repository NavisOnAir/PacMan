package object;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Game;

public class Object {

    public Object() {

    }
    
    // stearing, positioning
    public int x, y;
    public int speed;
    public int nextTile;

    // properties
    public boolean isVunerable = false;

    // animation
    public int spriteNumber = 0;
    public int spriteCounter = 0;

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

    public Object(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, game.tileSize, game.tileSize);

        if (game.isDebugMode) {
            drawDebug(g2);
        }
    }

    // debug methods
    public void drawDebug(Graphics2D g2) {
        // debug
		if (game.isDebugMode) {
			
			int[] debugLineCords = getCordsInRotation();
			int[] centeredCords = getCenteredCords();
			g2.setColor(Color.red);
			g2.drawLine(centeredCords[0], centeredCords[1], debugLineCords[0], debugLineCords[1]);
		
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

    public int[] getCenteredCords() {
        int centeredX = this.x + Math.round(game.tileSize / 2);
        int centeredY = this.y + Math.round(game.tileSize / 2);
        int[] centeredCords = {centeredX, centeredY};
        return centeredCords;
    }
}
