import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Game extends JPanel implements Runnable{

    // screen settings
    final int origTileSize = 16;
    final int scale = 3;

    final int tileSize = origTileSize * scale; // 48
    int maxScreenCol;
    int maxScreenRow;
    int screenWidth;
    int screenHight;

    // game states
    public int gameState;
    public final int titelState = 0;
    public final int ingameState = 1;

    // create game tile array
    int[][] gameTiles;

    // fps
    int FPS = 60;
    int currentTPS = 0;
    int currentFPS = 0;

    // instance creation
    KeyHandler keyHand = new KeyHandler();
    Thread gameThread;
    PacMan pacMan = new PacMan(this);
    public Ui ui = new Ui(this);

    public Game(int col, int row) {
        maxScreenCol = col; // 30
        maxScreenRow = row; // 20
        screenWidth = tileSize * maxScreenCol; // 1440
        screenHight = tileSize * maxScreenRow; // 960

        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.black);
        this.addKeyListener(keyHand);
        this.setFocusable(true);

        
    }

    public void setupGame() {
        gameState = ingameState;

        // load field array
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // game loop
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int updateCount = 0;
        int drawCount = 0;

        while (gameThread != null) {

            // calculate passed time
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                // UPDATE
                update();

                delta--;
                updateCount++;
            }

            // DRAW
            repaint();
            drawCount++;

            if (timer >= 1000000000) {
                // System.out.println("FPS:" + drawCount);
                this.currentTPS = updateCount;
                this.currentFPS = drawCount;
                updateCount = 0;
                timer = 0;
            }
            
        }
    }
    
    // update method
    public void update() {

        // player
        if (keyHand.arrowUp) {
            pacMan.setRotation(pacMan.up);
        } else if (keyHand.arrowRight) {
            pacMan.setRotation(pacMan.right);
        } else if (keyHand.arrowDown) {
            pacMan.setRotation(pacMan.down);
        } else if (keyHand.arrowLeft) {
            pacMan.setRotation(pacMan.left);
        }
        pacMan.move();

    }

    // drawing method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // player
        pacMan.draw(g2);
        
        // UI
        //ui.draw(g2);
        // FPS
        ui.draw(g2);


        g2.dispose();

    }

    // drawing methods
    public void drawTitleMenu(Graphics2D g2) {
        float fontSize = 40f;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, fontSize));

        String text = "Start";
        int x = getXForCenteredText(text, fontSize);
        int y = tileSize * 3;

        g2.drawString(text, x, y);
    }

    // get methods
    public int getWidth() {
        return screenWidth;
    }

    public int getHight() {
        return screenHight;
    }

    public int getXForCenteredText(String text, float size) {
        return Math.round(getWidth() / 2) - Math.round((text.length() * size) / 2);
    }
    
}
