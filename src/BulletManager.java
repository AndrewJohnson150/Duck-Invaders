import java.util.ArrayList;
import java.util.List;

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
	
	public void shoot(ItemInterface p) {
		if (numActiveBullets<maxNumBullets) {
			System.out.println("shooting bullet: " + numActiveBullets);
			bullets[getEmptyBulletIndex()] = new Bullet(frame,p,bulletVelocity); 
			numActiveBullets++;
		}
	}
	
	public void hitRegister(int xLocationOfBullet, int yLocationOfBullet) {
		
		for (int i = 0; i<maxNumBullets;i++) {
			if (bullets[i]!=null)
				if (bullets[i].getX() == xLocationOfBullet && bullets[i].getY() == yLocationOfBullet) {
					System.out.println("Hit was registered");
					bullets[i].erase();
					bullets[i] = null;
					numActiveBullets--;
					break;
				}
		}
	}
	
	public List<int[]> bulletLocation() {
		List<int[]> locations = new ArrayList<int[]>();
		for (int i = 0; i<maxNumBullets;i++) {
			if (bullets[i]!=null)
				locations.add(bullets[i].getLoc());
		}
		return locations;
	}
	
	public void move() {
		for (int i = 0; i<maxNumBullets; i++) {
			if (bullets[i]!=null) {
				bullets[i].move();
				int yLoc = bullets[i].getY();
				if (yLoc<= 0 || yLoc>=Invader_GUI.HEIGHT) {
					bullets[i].erase();
					bullets[i] = null;
					numActiveBullets--;
					System.out.println(numActiveBullets + " bullet past boundaries");
					
				}
			}
		}
	}
	
	private int getEmptyBulletIndex() {
		for(int i = 0; i<maxNumBullets;i++) {
			if (bullets[i]==null) {
				return i;
			}
		}
		System.out.println("No empty bullet, returning -1");
		return -1;
	}
}

