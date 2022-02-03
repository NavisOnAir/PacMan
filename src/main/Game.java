package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import graphics.ui.Ui;
import listener.MouseHandler;
import listener.KeyHandler;
import object.Ghost;
import object.PacMan;
import object.collision.Collider;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Game extends JPanel implements Runnable{

    // screen settings
    final int origTileSize = 16;
    final int scale = 3;
    public boolean isDebugMode = true;

    public final int tileSize = origTileSize * scale; // 48
    int maxScreenCol;
    int maxScreenRow;
    public int screenWidth;
    public int screenHight;

    // game states
    public int gameState;
    public final int titelState = 0;
    public final int ingameState = 1;
    public final int pauseState = 2;
    public final int wonState = 3;
    public final int levelSelectState = 4;
    public final int looseState = 5;

    // create game tile array
    public int[][] gameTiles;
    public Ghost[] ghostArray;

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
    public int currentTPS = 0;
    public int currentFPS = 0;
    public int currentFrame = 0;

    // debug strings
    public String debugStandardString = "[DEBUG][" + System.nanoTime()/1000000000 + "] ";

    // collision management
    private Collider[] colliders = new Collider[0];
    public final String colliderGhostName = "Ghost";
    public final String colliderPacManName = "PacMan";

    // instance creation
    public Ui ui = new Ui(this);
    LevelData lvlDat = new LevelData();
    public Utils utils = new Utils();
    public Timer timer = new Timer();

    // event listener
    KeyHandler keyHand = new KeyHandler();
    MouseHandler mouseHand = new MouseHandler(this);

    // game thread
    Thread gameThread;

    // moving objects
    PacMan pacMan;;



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

        // get starting positions
        int[][] pacManStartCords = utils.getXAndYIn2DIntArray(gameTiles, tilePacMan);
        int[][] ghostsStartCords = utils.getXAndYIn2DIntArray(gameTiles, tileGhost);

        // create pacman instance
        this.pacMan = new PacMan(this, this.keyHand, pacManStartCords[0][0], pacManStartCords[0][1]);

        // set tile @ pacman start cords to empty
        this.gameTiles[pacManStartCords[0][1]][pacManStartCords[0][0]] = tileEmpty;

        // set length of ghost array to number of ghosts
        this.ghostArray = new Ghost[ghostsStartCords.length];
        for (int i = 0; i < ghostsStartCords.length; i++) {
            // creating ghost instances
            ghostArray[i] = new Ghost(this, ghostsStartCords[i][0], ghostsStartCords[i][1], i * 10, i);

            // set tile @ ghost start cords to empty
            this.gameTiles[ghostsStartCords[i][1]][ghostsStartCords[i][0]] = tileEmpty;
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
        
        // ingame
        if (gameState == ingameState) {
            // move player
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

            // ghost
            for (Ghost ghost : this.ghostArray) {
                ghost.move();
            }

            // colliders
            for (Collider col : colliders) {
                col.update();

                // check collision
                for (Collider collidetTo : colliders) {
                    if (col != collidetTo) {
                        boolean isCollision = utils.checkCollision(col, collidetTo);
                        if (isCollision) {
                            col.parent.collisionEnter(collidetTo);
                        } else {
                            col.parent.collisionExit(collidetTo);
                        }
                    }
                }
            }
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

            // enemies
            for (Ghost ghost : this.ghostArray) {
                ghost.draw(g2);
            }
        
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
        // fixing width and hight less than 0
        if (width < 0) {
            width = gameTiles[0].length - 1;
        }
        if (hight < 0) {
            hight = gameTiles.length - 1;
        }
        return gameTiles[hight][width];
    }

    // add methods
    public void addCollider(Collider col) {
        int newColLength = colliders.length + 1;
        Collider[] newColliders = new Collider[newColLength];
        for (int i = 0; i < colliders.length; i++) {
            newColliders[i] = colliders[i];
        }
        newColliders[newColLength - 1] = col;
        this.colliders = newColliders;
    }

    // events
    public PacMan pacManMoved() {
        return this.pacMan;
    }
    
}
