package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import listener.MouseHandler;
import listener.KeyHandler;
import object.PacMan;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    public final int pauseState = 2;
    public final int wonState = 3;
    public final int levelSelectState = 4;

    // create game tile array
    public int[][] gameTiles;

    // tile decoding
    public int tileEmpty = 0;
    public int tileWall = 1;
    public int tileGhost = 2;
    public int tileCoin = 3;
    public int tilePacMan = 4;
    public int tileDoor = 5;
    public int tileFruit = 6;
    public int tilePowerPill = 7;

    // counters
    public int pointCounter;
    public int secondsPlayed;

    // level select
    public String levelSelected = "level1.lvl";

    // fps
    int FPS = 60;
    int currentTPS = 0;
    int currentFPS = 0;
    int currentFrame = 0;

    // debug strings
    public String debugStandardString = "[DEBUG][" + System.nanoTime()/1000000000 + "] ";

    // instance creation
    public Ui ui = new Ui(this);
    LevelData lvlDat = new LevelData();
    Utils utils = new Utils();
    public Timer timer = new Timer();

    // event listener
    KeyHandler keyHand = new KeyHandler();
    MouseHandler mouseHand = new MouseHandler(this);

    // game thread
    Thread gameThread;

    // moving objects
    PacMan pacMan = new PacMan(this, keyHand);



    public Game(int col, int row) {
        this.maxScreenCol = col; // 30
        this.maxScreenRow = row; // 20
        this.screenWidth = tileSize * maxScreenCol; // 1440
        this.screenHight = tileSize * maxScreenRow; // 960

        this.pointCounter = 0;

        this.setPreferredSize(new Dimension(screenWidth, screenHight));
        this.setBackground(Color.black);

        this.addKeyListener(keyHand);
        this.addMouseListener(mouseHand);

        this.setFocusable(true);       
    }

    public void setupGame() {
        // set default gamestate
        this.gameState = titelState;

        // load field array
        // level must contain coins, without gameState will change to wonState!!!
        String levelString = utils.getFileAsString("levels/" + this.levelSelected);
        if (levelString != utils.errorState) {
            this.gameTiles = utils.stringTo2DArray(levelString);
            System.out.println("No Error");
        } else {
            try {
                String levelData = utils.getStringFrom2DArray(lvlDat.levelOne);
            BufferedWriter h = new BufferedWriter(
                new FileWriter("levels/" + this.levelSelected));
            h.write(levelData);

            h.close();
            this.gameTiles = lvlDat.levelOne;

            } catch(IOException g) {
                g.printStackTrace();
            }
        }
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
        this.timer.start();

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

                // current Frame
                this.currentFrame++;
            }

            

            if (timer >= 1000000000) {
                // System.out.println("FPS:" + drawCount);
                this.currentTPS = updateCount;
                this.currentFPS = drawCount;
                this.currentFrame = 0;
                updateCount = 0;
                drawCount = 0;
                timer = 0;
            }
            
        }
    }
    
    // update method
    public void update() {
        // player
        if (gameState == ingameState) {
            pacMan.move();

            // checks if pacman won
            boolean pacmanWon = true;
            for (int i = 0; i < gameTiles.length; i++) {
                for (int j = 0; j < gameTiles[0].length; j++) {
                    int gameTile = gameTiles[i][j];
                    if (gameTile == tileCoin) {
                        pacmanWon = false;
                        break;
                    }
                }
            }
            if (pacmanWon) gameState = wonState;
        }

    }

    // drawing method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Ingame
        if (gameState == ingameState) {
            
            // Ingame ui
            ui.drawIngame(g2);

            // player
            pacMan.draw(g2);
        
        }

        // Title screen
        if (gameState == titelState) {
            // title ui
            ui.drawTitle(g2);
        }

        // Pause screen
        if (gameState == pauseState) {
            ui.drawIngame(g2);
            pacMan.draw(g2);
            ui.drawPause(g2);
        }

        // Level select screen
        if (gameState == levelSelectState) {
            ui.drawLevelSelection(g2);
        }


        g2.dispose();

    }

    // get methods
    public int getWidth() {
        return screenWidth + 6; // adjusting width to fit better
    }

    public int getHight() {
        return screenHight + 29; // adjusting hight to fit better
    }

    public int getTile(int width, int hight) {
        return gameTiles[hight][width];
    }
    
}
