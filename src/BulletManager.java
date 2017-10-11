import javax.swing.JFrame;

public class BulletManager {
	private JFrame frame;
	private Bullet bullet;
	private int bulletVelocity;
	
	public BulletManager(JFrame passedInFrame, int screenHeight, int bVelocity) {
		frame = passedInFrame;
		bulletVelocity = bVelocity;
	}
	
	public void shoot(Player p) {
		if (bullet==null)
			bullet = new Bullet(frame,p,bulletVelocity);
	}
	
	public void hitRegister() {
		bullet.erase();
		bullet = null;
	}
	
	public int[] bulletLocation() {
		if (bullet!=null)
			return bullet.getLoc();
		
		return null;
	}
	
	public void move() {
		if (bullet!=null) {
			bullet.move();
			if (bullet.getY() < 0) {
				bullet.erase();
				bullet = null;
			}
		}
	}
}

