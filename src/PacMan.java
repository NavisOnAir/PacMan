
public class PacMan extends Object {
	// rotation states
	final int up = 0;
	final int right = 1;
	final int down = 2;
	final int left = 3;

	int rotation;

	boolean vunerability;

	Game game;

	public PacMan(Game game) {
		super(game);
		
		setDefaults();
	}

	public void setDefaults() {
		x = 100;
		y = 100;
		speed = 3; // 3 * 60 per second
		rotation = right;
	}

	public void move() {
		switch(this.rotation) {
			case up:
				this.y -= speed;
				break;
			case right:
				this.x += speed;
				break;
			case down:
				this.y += speed;
				break;
			case left:
				this.x -= speed;
				break;
			default:
				break;
		}
	}
	

	public int getx() {
		return this.x;
	}
	public int gety() {
		return this.y;
	}

	public void setRotation(int rot) {
		this.rotation = rot;
	}
}
