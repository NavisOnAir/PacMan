package object;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Game;

public class Object {
    
    // stearing, positioning
    public int x, y;
    public int speed;
    public int nextTile;

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
    }
}
