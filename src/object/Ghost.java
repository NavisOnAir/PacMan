package object;

import main.Game;

import java.awt.Color;

public class Ghost extends Object {

    Color color;

    public Ghost(Game game, int x, int y) {
        super(game);
        this.x = x * game.tileSize;
        this.y = y * game.tileSize;
    }  
    
}
