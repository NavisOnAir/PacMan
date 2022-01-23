package object;

import java.awt.Color;
import java.awt.Graphics2D;

import main.Game;

public class Object {
    
    public int x, y;
    public int speed;

    // rotation states
	public final int up = 0;
	public final int right = 1;
	public final int down = 2;
	public final int left = 3;

	public int rotation;

    public Game game;

    public Object(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, game.tileSize, game.tileSize);
    }
}
