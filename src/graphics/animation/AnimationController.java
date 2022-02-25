package graphics.animation;

import main.Game;
import object.Object;

import java.util.ArrayList;
import java.awt.Graphics2D;

public class AnimationController {

    // dependencies
    public Game game;
    public Object parent;

    // animation handling
    public ArrayList<Animation> animationList = new ArrayList<Animation>(); // list of all animation instances for one animation controller
    public int currentAnimationIndex = 0;
    public Animation currentAnimation;
    public String[] sprites; // stores sprites to be loaded needed if this class is extended

    // constructor
    public AnimationController(Object parent, Game game) {
        this.game = game;
        this.parent = parent;

        // set default animation
        String[] spritePaths = {"/sprites/default/missingSprite.png"}; // load a default missing sprite immage to display a sprite on error
        this.currentAnimation = new Animation(game, "MissingSprite", 1, spritePaths);
    }

    public void update() { // needs to be called every frame

        // calls update method of current animation
        currentAnimation.update();
        
    }

    // creates an animation and adds it to animation list
    public void addAnimation(String name, double duration, String[] spritePaths) {
        // create new animation instance
        Animation anim = new Animation(game, name, duration, spritePaths);

        // add animation to animationlist
        animationList.add(anim);

        // debug message
        if (game.isDebugMode) {
            System.out.println("Added animation '" + name + "' at: " + (animationList.size() - 1));
        }
        
    }

    // returns the index in animation list of the animation with matching name
    public int getAnimationIndexByName(String name) { // returns -1 on name not matching
        int index = -1;
        for (int i = 0; i < animationList.size(); i++) {
            Animation anim = animationList.get(i);
            if (anim.name == name) {
                index = i;
                break;
            }
        }
        return index;
    }

    // draws current loaded animation on parent object coordinates
    public void draw(Graphics2D g2) {
        currentAnimation.draw(g2, parent.x, parent.y);
    }

    // switch to animation stored on animation list with index given
    public void switchAnimation(int index) {
        // set new animation index and set new current animation
        currentAnimationIndex = index;
        currentAnimation = animationList.get(currentAnimationIndex);
    }
}
