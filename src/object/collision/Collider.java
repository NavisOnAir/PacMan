package object.collision;

import object.Object;

import java.awt.Graphics2D;

public class Collider {

    int x, y;
    Object parent;
    Box box;

    public Collider(Object parent) {
        this.parent = parent;
        this.box = new Box(parent.x, parent.y, parent.game.tileSize, parent.game.tileSize);
        this.x = parent.x;
        this.y = parent.y;

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
        g2.drawRect(box.x, box.y, box.width, box.height);
    }

    public void update() {
        this.x = parent.x;
        this.y = parent.y;
        this.box.x = x;
        this.box.y = y;
    }
    
}
