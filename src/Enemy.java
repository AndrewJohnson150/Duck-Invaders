import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Enemy implements ItemInterface {
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	private int direction;
	
	private ImageIcon enemyImage;
	private JLabel enemyJLabel;
	private JFrame enemyJFrame;
	
	public Enemy(JFrame passedInJFrame, int startX, int startY, int h, int vel, int dir) {
		xPos = startX;
		yPos = startY;
		health = h;
		velocity = vel;
		direction = dir;
	

	    enemyJFrame = passedInJFrame;
	    enemyJLabel = new JLabel();
	    enemyJLabel.setBounds (10, 10, 10, 10); // arbitrary, will change later
	    enemyJFrame.getContentPane().add(enemyJLabel);
	    enemyJLabel.setVisible(false);
	    enemyJLabel.setVisible(true);
	    
	    setImage();
	   /* enemyImage = new ImageIcon ("Images/duck1.png"); //change
	    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), 75, 75);
	    enemyImage = new ImageIcon(i);*/
	}
	
	private void setImage() {
		if(direction == RIGHT) {
		 	enemyImage = new ImageIcon ("Images/duck1.png"); //change
		    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), 75, 75);
		    enemyImage = new ImageIcon(i);
		}
		else {
			enemyImage = new ImageIcon ("Images/duck2.png"); //change
		    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), 75, 75);
		    enemyImage = new ImageIcon(i);
		}
	}
	public int imageWidth() {
		return enemyImage.getIconWidth();
	}

	public int imageHeight() {
		return enemyImage.getIconHeight();
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
	
	public void setDirection(int d) {
		direction = d;
	}
	
	public void goDown() {
		yPos+=5*velocity; //might want to change
	}
	
	public void move() {
		//int xBoundR = (screenWidth - playerImage.getIconWidth());
		//int xBoundL = 0;
		if (direction==RIGHT)
			xPos += velocity;
		else
			xPos -= velocity;
		
		draw();
	}
	
    protected void draw(){ 
        enemyJLabel.setIcon(enemyImage);
        enemyJLabel.setBounds(xPos,yPos,enemyImage.getIconWidth(),enemyImage.getIconHeight());  
        enemyJLabel.setVisible(true);
        setImage();
    }
    
    protected void erase(){
        enemyJLabel.setVisible(false);
    }
}
