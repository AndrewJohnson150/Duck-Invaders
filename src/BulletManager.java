import javax.swing.JFrame;

public class BulletManager {
	private JFrame frame;
	private Bullet[] bullets;
	private int bulletVelocity;
	private int maxNumBullets;
	private int numActiveBullets;
	
	public BulletManager(JFrame passedInFrame, int screenHeight, int bVelocity, int MaxNumberOfBullets) {
		frame = passedInFrame;
		bulletVelocity = bVelocity;
		maxNumBullets = MaxNumberOfBullets;
		numActiveBullets = 0;
		bullets = new Bullet[MaxNumberOfBullets];
	}
	
	public void shoot(Player p) {
		if (numActiveBullets<maxNumBullets) {
			bullets[numActiveBullets] = new Bullet(frame,p,bulletVelocity);
			numActiveBullets++;
		}
	}
	
	public void hitRegister(int xLocationOfBullet) {
		numActiveBullets--;
		for (Bullet b : bullets) {
			if (b.getX() == xLocationOfBullet) {
				b.erase();
				b = null;
				break;
			}
		}
	}
	
	public int[][] bulletLocation() {
		int[][] locations = new int[numActiveBullets][2];
		if (numActiveBullets>0)
			for (int i = 0; i<numActiveBullets;i++) {
				locations[i] = bullets[i].getLoc();
			}
		return null;
	}
	
	public void move() {
		for (Bullet b : bullets)
			if (b!=null) {
				b.move();
				if (b.getY() < 0) {
					b.erase();
					b = null;
				}
			}
	}
}

