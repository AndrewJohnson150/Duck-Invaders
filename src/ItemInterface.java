
public interface ItemInterface {
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public int getX();
	public int getY();
	public int getHealth();
	public int getVelocity();
	public void setX(int x);
	public void setY(int y);
	public void loseHealth();
}
