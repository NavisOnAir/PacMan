package object.Ghosts.animation;

import graphics.animation.AnimationController;
import main.Game;
import object.Object;
import object.Ghosts.Ghost;

public class GhostAnimController extends AnimationController {

    Ghost ghost;

    // anim const
    public final int colorRed = 0;
    public final int colorGreen = 1;
    public final int colorBlue = 2;
    public final int colorYellow = 3;


    public GhostAnimController(Object parent, Game game, Ghost ghost) {
        super(parent, game);
        this.ghost = ghost;
        
        // red
        sprites = new String[]{"/sprites/ghost/ghost_red_one.png", "/sprites/ghost/ghost_red_two.png"};
        addAnimation("moveRed", 1, sprites);

        // green
        sprites = new String[]{"/sprites/ghost/ghost_green_one.png", "/sprites/ghost/ghost_green_two.png"};
        addAnimation("moveGreen", 1, sprites);

        // blue
        sprites = new String[]{"/sprites/ghost/ghost_blue_one.png", "/sprites/ghost/ghost_blue_two.png"};
        addAnimation("moveBlue", 1, sprites);

        // yellow
        sprites = new String[]{"/sprites/ghost/ghost_yellow_one.png", "/sprites/ghost/ghost_yellow_two.png"};
        addAnimation("moveYellow", 1, sprites);

        // set animation for color
        if (ghost.color == ghost.colorRed) {
            switchAnimation(colorRed);
        }

        if (ghost.color == ghost.colorGreen) {
            switchAnimation(colorGreen);
        }

        if (ghost.color == ghost.colorBlue) {
            switchAnimation(colorBlue);
        }

        if (ghost.color == ghost.colorYellow) {
            switchAnimation(colorYellow);
        }
    }
    
}
