import java.awt.Dimension;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

    // screen settings
    final int origTileSize = 16;
    final int scale = 3;

    final int tileSize = origTileSize * scale; // 48
    int maxScreenCol;
    int maxScreenRow;
    int screenWidth;
    int screenHight;

    public GamePanel(int col, int row) {
        maxScreenCol = col; // 30
        maxScreenRow = row; // 20
        screenWidth = tileSize * maxScreenCol; // 1440
        screenHight = tileSize * maxScreenRow; // 960

        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        
    }
    
}
