package graphics.animation;

import main.Game;

public class AnimationController {

    Game game;
    Object parent;
    
    Animation[] animations;

    public AnimationController(Object parent, Game game) {
        this.game = game;
        this.parent = parent;

    }

    public void update() { // needs to be called every frame
        for (int i = 0; i < animations.length; i++) {
            animations[i].update();
        }
    }

    public void add(String name, int duration, String[] spritePaths) {
        this.animations = new Animation[animations.length + 1];

        // add animation
        this.animations[animations.length - 1] = new Animation(game, name, duration, spritePaths);
    }

    public int getAnimationIndexByName(String name) { // returns -1 on name not matching
        for (int i = 0; i < animations.length; i++) {
            if (animations[i].name == name) {
                return i;
            }
        }
        return -1;
    }
}
