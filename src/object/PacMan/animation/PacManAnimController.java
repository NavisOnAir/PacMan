package object.PacMan.animation;

import graphics.animation.Animation;
import graphics.animation.AnimationController;
import main.Game;
import object.Object;

public class PacManAnimController extends AnimationController {

    final int animUp = 0;
    final int animRight = 1;
    final int animDown = 2;
    final int animLeft = 3; 

    // anim duration
    final double animDur = 1;

    public PacManAnimController(Object parent, Game game) {
        super(parent, game);

        // up
        sprites = new String[]{"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_up.png"};
        addAnimation("moveUp", animDur, sprites);

        // right
        sprites = new String[]{"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_right.png"};
        addAnimation("moveRight", animDur, sprites);

        // down
        sprites = new String[]{"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_down.png"};
        addAnimation("moveDown", animDur, sprites);

        // left
        sprites = new String[]{"/sprites/pacman/PacMan_closed.png", "/sprites/pacman/PacMan_open_left.png"};
        addAnimation("moveLeft", animDur, sprites);

    }

    @Override
    public void update() {
        int objectState = parent.objectState;
        if (objectState == parent.stateMoveUp) {
            currentAnimationIndex = animUp;
        }
        if (objectState == parent.stateMoveRight) {
            currentAnimationIndex = animRight;
        }
        if (objectState == parent.stateMoveDown) {
            currentAnimationIndex = animDown;
        }
        if (objectState == parent.stateMoveLeft) {
            currentAnimationIndex = animLeft;
        }

        switchAnimation(currentAnimationIndex);
        
        super.update();
    }

    @Override
    public void switchAnimation(int index) {
        Animation lastAnim = currentAnimation;
        currentAnimationIndex = index;
        currentAnimation = animationList.get(currentAnimationIndex);
        currentAnimation.currentFrame = lastAnim.currentFrame;
        currentAnimation.currentSprite = lastAnim.currentSprite;
    }

}
