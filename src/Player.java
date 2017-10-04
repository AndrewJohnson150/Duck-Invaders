
public class Player implements ItemInterface {
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	private int direction;
	
	public Player(int startX, int startY, int h, int vel, int dir) {
		xPos = startX;
		yPos = startY;
		health = h;
		velocity = vel;
		direction = dir;
	}
	
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getHealth() {
		return health;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setX(int x) {
		xPos = x;
	}

	public void setY(int y) {
		yPos = y;
	}

	public void loseHealth() {
		health--;
	}
	
	public void move() {
		if (direction==UP) {
			yPos+=velocity;
		}
		else if (direction==DOWN) {
			yPos-=velocity;
		}
		else if (direction==LEFT) {
			xPos-=velocity;
		}
		else if (direction==RIGHT) {
			xPos+=velocity;
		}
	}
	

}
