package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import listener.MouseHandler;
import listener.KeyHandler;
import object.PacMan;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Game extends JPanel implements Runnable{

    // screen settings
    final int origTileSize = 16;
    final int scale = 3;

    public final int tileSize = origTileSize * scale; // 48
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
    public Ui ui = new Ui(this);

    KeyHandler keyHand = new KeyHandler();
    MouseHandler mouseHand = new MouseHandler(this);

    Thread gameThread;
    PacMan pacMan = new PacMan(this, keyHand);

    public Game(int col, int row) {
        maxScreenCol = col; // 30
        maxScreenRow = row; // 20
        screenWidth = tileSize * maxScreenCol; // 1440
        screenHight = tileSize * maxScreenRow; // 960

        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.black);

        this.addKeyListener(keyHand);
        this.addMouseListener(mouseHand);

        this.setFocusable(true);

        
    }

    public void setupGame() {
        gameState = titelState;

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
                // DRAW
                repaint();
                drawCount++;
            }

            

            if (timer >= 1000000000) {
                // System.out.println("FPS:" + drawCount);
                this.currentTPS = updateCount;
                this.currentFPS = drawCount;
                updateCount = 0;
                drawCount = 0;
                timer = 0;
            }
            
        }
    }
    
    // update method
    public void update() {

        // player
        pacMan.move();

    }

    // drawing method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ingame
        if (gameState == ingameState) {

            // player
            pacMan.draw(g2);
            
            // Ingame ui
            ui.drawIngame(g2);
        
        }

        // Title screen
        if (gameState == titelState) {
            // title ui
            ui.drawTitle(g2);
        }


        g2.dispose();

    }

    // get methods
    public int getWidth() {
        return screenWidth;
    }

    public int getHight() {
        return screenHight;
    }
    
}
