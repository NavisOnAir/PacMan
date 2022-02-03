package object.PacMan;

import graphics.animation.AnimationController;
import main.Game;

public class PacManAnim extends AnimationController {

    public PacManAnim(Object parent, Game game) {
        super(parent, game);

        String[] pathsDown = {"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_down.png"};
        String[] pathsRight = {"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_right.png"};
        String[] pathsUp = {"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_up.png"};
        String[] pathsLeft = {"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_left.png"};

        add("down", 3, pathsDown);
        add("right", 3, pathsRight);
        add("up", 3, pathsUp);
        add("left", 3, pathsLeft);
    }

    /*
        picDown = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_down.png"));
			picUp = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_up.png"));
			picRight = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_right.png"));
			picLeft = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_open_left.png"));
			picStill = ImageIO.read(getClass().getResourceAsStream("/sprites/pacman/PacMan_closed.png"));
    */
    
}
