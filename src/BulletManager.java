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
			bullets[getEmptyBulletIndex()] = new Bullet(frame,p,bulletVelocity); 
			numActiveBullets++;
		}
	}
	
	public void hitRegister(int xLocationOfBullet, int yLocationOfBullet) {
		
		for (int i = 0; i<maxNumBullets;i++) {
			if (bullets[i]!=null) {
				int bulletX = bullets[i].getX();
				int bulletY = bullets[i].getY();
				int imWidth = bullets[i].imageWidth();
				int imHeight = bullets[i].imageHeight();
				
				int[] topRight = {bulletX+imWidth,bulletY};
				int[] topLeft = {bulletX,bulletY};
				int[] botRight = {bulletX+imWidth,bulletY+imHeight};
				int[] botLeft= {bulletX,bulletY+imHeight};
				
				if ((bulletX == topLeft[0] && bulletY == topLeft[1]) ||
					(bulletX == topRight[0] && bulletY == topRight[1]) ||
					(bulletX == botRight[0] && bulletY == botRight[1]) ||
					(bulletX == botLeft[0] && bulletY == botLeft[1])) {
					bullets[i].erase();
					bullets[i] = null;
					numActiveBullets--;
					break;
				}
			}
		}
	}
	
	public List<int[]> bulletLocation() {
		List<int[]> locations = new ArrayList<int[]>();
		for (int i = 0; i<maxNumBullets;i++) {
			if (bullets[i]!=null) {
				int[][] locs = bullets[i].getLoc();
				for (int[] loc : locs) {
					locations.add(loc);
				}
			}
				
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
		return -1;
	}
}

