package graphics.animation;

import main.Game;

public class AnimationController {

    Game game;
    Object parent;
    
    Animation[] animations = new Animation[0];

    public AnimationController(Object parent, Game game) {
        this.game = game;
        this.parent = parent;

    }

    public void update() { // needs to be called every frame
        for (int i = 0; i < animations.length; i++) {
            try {
                animations[i].update();
            } catch (Exception e) {
                System.out.println("<<< No Animation >>>" + i);
            }
        }
    }

    public void add(String name, int duration, String[] spritePaths) {
        this.animations = new Animation[animations.length + 1];

        // add animation
        this.animations[animations.length - 1] = new Animation(game, name, duration, spritePaths);
        System.out.println("Added anim at: " + (animations.length - 1));
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
