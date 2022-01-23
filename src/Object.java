import java.awt.Color;
import java.awt.Graphics2D;

public class Object {
    
    public int x, y;
    public int speed;

    Game game;

    public Object(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fillRect(this.x, this.y, game.tileSize, game.tileSize);
    }
}
