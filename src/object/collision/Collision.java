package object.collision;

public interface Collision {

    public abstract void collisionEnter(Collider col);

    public abstract void collisionExit(Collider col);
    
}
