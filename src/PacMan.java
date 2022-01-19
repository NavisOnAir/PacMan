
public class PacMan {
	boolean vunerability;
	int speed;
	int rotation;
	int x, y;
	public PacMan(int[] startCords) {
		x = startCords[1];
		y = startCords[0];
	}
	

	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	
	public void move(int direction) { //0 = up, 1 = right, 2 = down, 3 = left
		switch(direction) {
		case 0:
			x--;
			break;
		case 1:
			y++;
			break;
		case 2:
			x++;
			break;
		case 3:
			y--;
			break;
		default:
		}
	}
}
