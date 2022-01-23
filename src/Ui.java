import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Ui {
    
    Game game;

    public Ui(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D g2) {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20));
        g2.setColor(Color.white);
        g2.drawString("TPS:" + Integer.toString(game.currentTPS), 10, 30);

        g2.drawString("FPS:" + Integer.toString(game.currentFPS), 10, 55);
    }
    
}
