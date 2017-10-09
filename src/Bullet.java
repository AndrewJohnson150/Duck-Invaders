import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Bullet implements ItemInterface {
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	private int direction;
	
	private ImageIcon bulletImage;
	private JLabel bulletJLabel;
	private JFrame bulletJFrame;
	
	public Bullet(JFrame passedInJFrame, Player p,  int vel) {
		velocity = vel;
		
		
		bulletJFrame = passedInJFrame;
	    bulletJLabel = new JLabel();
	    bulletJLabel.setBounds (10, 10, 10, 10); // arbitrary, will change later
	    bulletJFrame.getContentPane().add(bulletJLabel);
	    bulletJLabel.setVisible(false);
	    bulletJLabel.setVisible(true);
	    
	    bulletImage = new ImageIcon ("Images/bullet.png"); //change
	    Image i = Invader_GUI.getScaledImage(bulletImage.getImage(), 10, 50);
	    bulletImage = new ImageIcon(i);
		xPos = p.getX()+p.imageWidth()/2 - imageWidth()/2;
		yPos = p.getY();
	}
	
	public int imageWidth() {
		return bulletImage.getIconWidth();
	}

	public int imageHeight() {
		return bulletImage.getIconHeight();
	}
	
	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}
	
	public int[] getLoc() {
		return new int[]{xPos,yPos};
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
		yPos-=velocity;
		draw();
	}
	
    protected void draw(){ 
        bulletJLabel.setIcon(bulletImage);
        bulletJLabel.setBounds(xPos,yPos,bulletImage.getIconWidth(),bulletImage.getIconHeight());  
        bulletJLabel.setVisible(true);
    }
    
    protected void erase(){
        bulletJLabel.setVisible(false);
    }
	

}
