package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import graphics.ui.Ui;
import listener.MouseHandler;
import listener.KeyHandler;
import object.Ghosts.Ghost;
import object.PacMan.PacMan;
import object.collision.Collider;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Objects;

// implements runnable as interface for the game thread
public class Game extends JPanel implements Runnable{

    // screen settings
    private final int origTileSize = 16;
    private final int scale = 3;

    // debug
    public boolean isDebugMode = false; // change to true to enable debug information

    // tile size in pixel for one tile
    public final int tileSize = origTileSize * scale; // 48

    // dimensions in pixel of the jpanel
    private final int screenWidth;
    private final int screenHeight;

    // game states to define the current state of the game
    private int gameState;
    private final int titleState = 0;
    private final int ingameState = 1;
    private final int pauseState = 2;
    private final int wonState = 3;
    private final int levelSelectState = 4;
    private final int looseState = 5;
    private final int respawnState = 6;

    // has started a game
    private boolean isGameStarted;

    // create game tile array containing a encoded integer value for each tile in labyrinth
    public int[][] gameTiles;
    // array containing all ghosts
    public Ghost[] ghostArray;

    // tile encoding
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

    // count the time in 1 / FPS seconds to be able to set a respawn time
    public int respawnTimer;

    // level select as string of the currently selected level file path
    public String levelSelected = "level1.lvl";

    // frames per second
    public final int FPS = 60;
    // ticks per second to be able to differentiate between drawing and moving objects currently equal to FPS
    public int currentTPS = 0;
    public int currentFPS = 0;
    // counts the current frame of a second
    public int currentFrame = 0;

    // debug strings for a general debugging output in the console if debug is enabled, not used yet
    public String debugStandardString = "[DEBUG][" + System.nanoTime()/1000000000 + "] ";

    // collision management
    private Collider[] colliders = new Collider[0];
    // different names to tell wich collider is hit
    public final String colliderGhostName = "Ghost";
    public final String colliderPacManName = "PacMan";

    // instance creation
    public Ui ui = new Ui(this);
    private final LevelData lvlDat = new LevelData();
    public Utils utils = new Utils();
    public Timer timer = new Timer();
    public PacMan pacMan;

    // event listener
    KeyHandler keyHand = new KeyHandler();
    MouseHandler mouseHand = new MouseHandler(this);

    // game thread
    Thread gameThread;


    // constructor
    public Game(int col, int row) { // col: 30; row: 20;
        // default settings
        this.screenWidth = tileSize * col; // 1440
        this.screenHeight = tileSize * row; // 960
        isGameStarted = false;

        // jpanel settings
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        // add input management classes
        this.addKeyListener(keyHand);
        this.addMouseListener(mouseHand);

        // if you can tap into window
        this.setFocusable(true);       
    }

    // initialize the game and setup all required variables
    public void setupGame() {
        // set default gamestate
        this.gameState = titleState;

        // set all counters to 0
        this.pointCounter = 0;
        timer.reset();

        if (!isGameStarted) {
            resetGame();
        }
    }

    // starts the game
    public void startGame() {
        resetGame();
        switchIngame();
        // resets timer
        timer.reset();
        // start timer
        timer.start();
        // set game-started boolean to true to show a level is saved and can be loaded
        isGameStarted = true;
    }

    // resume on the saved game, only one can be saved and will be deleted if game is started via start
    public void resumeGame() {
        switchIngame();
        timer.resume();
    }

    // load new game
    public void resetGame() {
        // load field array
        // load level from path in levelSelected
        String levelString;
        if (!new File("levels/").exists()) {
            // creates the directory for level files
            File dir = new File("levels/");
            dir.mkdir();

            // load a default level
            LevelData data = new LevelData();
            levelString = utils.getStringFrom2DArray(data.levelOne);

            // create default level level-file
            try {
                File levelFile = new File("levels/level1.lvl");
                levelFile.createNewFile();
                FileWriter writer = new FileWriter(levelFile, false);
                writer.write(levelString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            levelString = utils.getFileAsString("levels/" + levelSelected);
        }

        // resets points counter
        pointCounter = 0;

        // load level from path
            /*
            equivalent to levelString != utils.errorState
             */
        if (!Objects.equals(levelString, utils.errorState)) {
            // assigns 2d array to gametiles 2d array
            this.gameTiles = utils.stringTo2DArray(levelString);

            // load default hardcoded level if levelString is error-state wich is a constant in utils class
        } else {
            try {
                String levelData = utils.getStringFrom2DArray(lvlDat.levelOne);
                BufferedWriter h = new BufferedWriter(
                        new FileWriter("levels/" + levelSelected)
                );
                h.write(levelData);
                h.close();
                // assigns 2d array of hardcoded level to gametiles 2d array
                this.gameTiles = lvlDat.levelOne;

            } catch (IOException e) {
                e.printStackTrace();
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

    // starts game thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // game loop
    @Override
    public void run() {

        // time in nanoseconds for one interval
        double drawInterval = (double) 1000000000 / FPS;
        // value between zero and one to indicate if time for the current frame is passed
        double delta = 0;
        // time in nanoseconds
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        // counter to count frames in ticks
        int updateCount = 0;
        int drawCount = 0;
        // start game timer
        this.timer.start();

        // runs a loop that ends if game thread is terminated
        while (gameThread != null) {

            // calculate passed time
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            // runs every 1 / FPS second currently every 1/60 second
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

            
            // runs every second once
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
    
    // update method called every tick
    public void update() {

        // back to main menu when esc pressed
        if (keyHand.esc) {
            timer.pause();
            switchTitle();

        }
        
        // ingame
        if (gameState == ingameState) {

            // move player
            pacMan.move();

            // checks if pacman dies
            if (pacMan.lifes <= 0) {
                switchLoose();
            }

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

            // pacman empowered
            if (pacMan.isEmpowered) {
                if (timer.getTimeInSeconds() >= pacMan.empEnterTime + 10) {
                    pacManNotEmpowered();
                }
            }

            // ghost
            for (Ghost ghost : this.ghostArray) {
                ghost.move();
            }

            // colliders
            for (Collider col : colliders) {
                col.update();

                // check collision
                for (Collider collidedTo : colliders) {
                    if (col != collidedTo) {
                        boolean isCollision = utils.checkCollision(col, collidedTo);
                        if (isCollision) {
                            col.parent.collisionEnter(collidedTo);
                        } else {
                            col.parent.collisionExit(collidedTo);
                        }
                    }
                }
            }

            // switch debug mode when q is pressed
            if (keyHand.q) {
                if (!isDebugMode) {
                    isDebugMode = true;
                } else {
                    isDebugMode = false;
                }
            }
        }

        // respawn
        if (gameState == respawnState) {
            if (respawnTimer <= FPS * 2) {
                respawnTimer++;
            } else {
                gameState = ingameState;

                // die event for all ghosts
                for (Ghost ghost :ghostArray) {
                    ghost.dieEvent();
                }

                // reset respawn timer
                this.respawnTimer = 0;
            }
        }


    }

    // drawing method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // transform graphics to graphics2d overall referenced as g2
        Graphics2D g2 = (Graphics2D) g;

        // Ingame
        if (gameState == ingameState) {
            
            // Ingame ui
            ui.drawIngame(g2);

            // player
            pacMan.draw(g2);

            // enemies
            for (Ghost ghost : this.ghostArray) {
                // only draws ghost if alive
                if (ghost.lifeState == ghost.aliveState) {
                    ghost.draw(g2);
                }
            }
        
        }

        // Title screen
        if (gameState == titleState) {
            // title ui
            ui.drawTitle(g2);
        }

        // Pause screen
        if (gameState == pauseState) {
            ui.drawIngame(g2);
            pacMan.draw(g2);
            ui.drawPause(g2);

            // enemies
            for (Ghost ghost : this.ghostArray) {
                ghost.draw(g2);
            }
        }

        // Won screen
        if (gameState == wonState) {
            if (!timer.getStopped()) {
                timer.pause();
            }
            ui.drawWonScreen(g2);
        }

        // Level select screen
        if (gameState == levelSelectState) {
            ui.drawLevelSelection(g2);
        }

        // Respawn screen
        if (gameState == respawnState) {
            ui.drawIngame(g2);
            // enemies
            for (Ghost ghost : this.ghostArray) {
                ghost.draw(g2);
            }
        }

        // Die screen
        if (gameState == looseState) {
            ui.drawDieScreen(g2);
        }

        // delete g2
        g2.dispose();

    }

    // get methods
    public int getWidth() {
        return screenWidth;
    }

    public int getHeight() {
        return screenHeight;
    }

    public int getGameState() {
        return gameState;
    }

    public int getTitleState() {
        return titleState;
    }

    public int getIngameState() {
        return ingameState;
    }

    public int getPauseState() {
        return pauseState;
    }

    public int getWonState() {
        return wonState;
    }

    public int getLevelSelectState() {
        return levelSelectState;
    }

    public int getLooseState() {
        return looseState;
    }

    public int getRespawnState() {
        return respawnState;
    }

    public boolean getStartGame() {
        return isGameStarted;
    }


    // get tile at width height
    public int getTile(int width, int height) {
        // fixing width and height less than 0
        if (width < 0) {
            width = gameTiles[0].length - 1;
        }
        if (height < 0) {
            height = gameTiles.length - 1;
        }
        return gameTiles[height][width];
    }

    public int getScale() {
        return scale;
    }

    // switch game stages
    public void switchTitle() {
        gameState = titleState;
    }

    public void switchIngame() {
        gameState = ingameState;
    }

    public void switchPause() {
        timer.pause();
        gameState = pauseState;
    }

    public void switchWon() {
        gameState = wonState;
    }

    public void switchLevelSelect() {
        gameState = levelSelectState;
    }

    public void switchLoose() {
        pacMan.dieEvent();
        gameState = looseState;
        timer.pause();
        isGameStarted = false;

    }

    public void switchRespawn() {
        gameState = respawnState;
    }

    // add methods
    public void addCollider(Collider col) {
        int newColLength = colliders.length + 1;
        // create new array with a by 1 greater size than previously
        Collider[] newColliders = new Collider[newColLength];
        // add all current colliders to new array
        System.arraycopy(colliders, 0, newColliders, 0, colliders.length);
        /* equivalent to:

        for (int i = 0; i < colliders.length; i++) {
            newColliders[i] = colliders[i];
        }

         */
        // add new collider at the back of the new array
        newColliders[newColLength - 1] = col;
        // assign the new collider array to the old one
        this.colliders = newColliders;
    }

    // events
    // called when power pill consumed
    public void pacManEmpowered() {
        pacMan.isVunerable = false;
        // loops through every ghost
        for (Ghost ghost : ghostArray) {
            ghost.isVunerable = true;
            ghost.step = 1; // potential error if speed less than 2
            ghost.pacManEmpowered();
            
            // invert direction of every ghost
            if (ghost.rotation == ghost.up) {
                ghost.rotation = ghost.down;
            }

            if (ghost.rotation == ghost.right) {
                ghost.rotation = ghost.left;
            }

            if (ghost.rotation == ghost.down) {
                ghost.rotation = ghost.up;
            }

            if (ghost.rotation == ghost.left) {
                ghost.rotation = ghost.right;
            }
                
        }
    }

    // called when pacman exits empowered state
    private void pacManNotEmpowered() {
        pacMan.isVunerable = true;
        pacMan.isEmpowered = false;
        for (Ghost ghost : ghostArray) {
            ghost.isVunerable = false;

            // secures that ghost cords match the modulo tilesize operation to secure ghost not moving through walls
            int currentX = ghost.x;
            int currentY = ghost.y;
            currentX -= currentX % tileSize;
            currentY -= currentY % tileSize;
            ghost.x = currentX;
            ghost.y = currentY;

            ghost.step = ghost.lastStep;
            ghost.exitPacManEmpowered();
        }
    }
}
