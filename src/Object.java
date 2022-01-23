import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Object {
    
    public int x, y;
    public int speed;

    // rotation states
	final int up = 0;
	final int right = 1;
	final int down = 2;
	final int left = 3;

	public int rotation;

    public BufferedImage picStill, picUp, picDown, picLeft, picRight;

    Game game;

    public Object(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, game.tileSize, game.tileSize);
    }
}
