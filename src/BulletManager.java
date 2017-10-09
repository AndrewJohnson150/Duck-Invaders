import java.util.LinkedList;

import javax.swing.JFrame;

public class BulletManager {
	private JFrame frame;
	private LinkedList<Bullet> bullets;
	private int bulletVelocity;
	
	public BulletManager(JFrame passedInFrame, int screenHeight, int bVelocity) {
		frame = passedInFrame;
		bullets = new LinkedList<Bullet>();
		bulletVelocity = bVelocity;
	}
	
	public void shoot(Player p) {
		bullets.add(new Bullet(frame,p.getX(),p.getY(),bulletVelocity));
	}
	
	public void hitRegister(int i) {
		
	}
	
	public int[][] bulletLocations() {
		int[][] orderedPairs = new int[bullets.size()][2];
		
		for (int i = 0; i<bullets.size();i++) {
			orderedPairs[i] = bullets.get(i).getLoc();
		}
		
		return orderedPairs;
	}
	
	public void move() {
		for (int i = 0; i<bullets.size();i++) {
			if (bullets.get(i).getY() < 0) {
				bullets.get(i).erase();
				bullets.remove(i);
			}
		}
		moveAll();
	}
	
	public void moveAll() {
		for (Bullet b : bullets) {
			b.move();
		}
	}
}

