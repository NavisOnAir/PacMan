package object.collision;

import object.Object;

import java.awt.Graphics2D;
import java.awt.Color;

public class Collider implements Collision {

    int x, y;
    public Object parent;
    public Box box;
    public String name;

    // debug
    Color color = Color.white;

    public Collider(Object parent, String name) {
        this.parent = parent;
        this.box = new Box(parent.x, parent.y, parent.game.tileSize, parent.game.tileSize);
        this.x = parent.x;
        this.y = parent.y;
        this.name = name;

        // add collider to game collider array
        this.parent.game.addCollider(this);
    }

    public int getWidth() {
        return box.width;
    }

    public int getHeight() {
        return box.height;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.drawRect(box.x, box.y, box.width, box.height);
    }

    public void update() {
        this.x = parent.x;
        this.y = parent.y;
        this.box.x = x;
        this.box.y = y;
    }

    @Override
    public void collisionEnter(Collider col) {
        this.color = Color.red;
    }

    @Override
    public void collisionExit(Collider col) {
        this.color = Color.white;
    }
    
}
