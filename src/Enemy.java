import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Enemy implements ItemInterface {
	
	private static final int IMAGE_SIZE = Invader_GUI.WIDTH/35;
	
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	private int direction;
	
	private ImageIcon enemyImage;
	private JLabel enemyJLabel;
	private JFrame enemyJFrame;
	
	private int hitTimer = 0;
	private boolean dying = false;
	
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
	
	/**
	 * sets the image of the duck. If going to the right, use the right-facing sprite.
	 * Otherwise, use the left-facing duck sprite.
	 */
	private void setImage() {
		System.out.println(health);
		if(health <= 0) {
			enemyImage = new ImageIcon ("Images/falling.png"); //change
		    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), IMAGE_SIZE, IMAGE_SIZE);
		    enemyImage = new ImageIcon(i);
		}
		else {
			if(hitTimer > 1) {
	    		enemyImage = new ImageIcon ("Images/hit.png"); //change
	    	    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), IMAGE_SIZE, IMAGE_SIZE);
	    	    enemyImage = new ImageIcon(i);
	    		hitTimer--;  
	    	}
			else {
				if(direction == RIGHT) {
				 	enemyImage = new ImageIcon ("Images/duck1.png"); //change
				    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), IMAGE_SIZE, IMAGE_SIZE);
				    enemyImage = new ImageIcon(i);
				}
				else {
					enemyImage = new ImageIcon ("Images/duck2.png"); //change
				    Image i = Invader_GUI.getScaledImage(enemyImage.getImage(), IMAGE_SIZE, IMAGE_SIZE);
				    enemyImage = new ImageIcon(i);
				}
			}
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
		hitTimer = 8;
		health--;
		if(health == 0)
			dying = true;
	}
	
	public void setDirection(int d) {
		direction = d;
	}
	
	/** 
	 * this method makes the duck go down
	 */
	public void goDown() {
		yPos+=5*velocity; //might want to change
	}

	/**
	 * moves the duck in its designated direction by number of pixels as designated in velocity
	 */
	public void move() {
		if (direction==RIGHT)
			xPos += velocity;
		else
			xPos -= velocity;
	}
	
	/**
	 * draws the image of the enemy starting at (xPos,yPos)
	 */
    protected void draw(){ 
		enemyJLabel.setIcon(enemyImage);
        enemyJLabel.setBounds(xPos,yPos,enemyImage.getIconWidth(),enemyImage.getIconHeight());  
        enemyJLabel.setVisible(true);
        setImage();
    }
    
    public void moveDead() {
    	if(dying) {
			yPos += velocity;
			draw();
    	}
		if(yPos > Invader_GUI.HEIGHT-Invader_GUI.HEIGHT/4) {
			dying = false;
			erase();
		}
	}
    
    /**
     * erases the image of the duck
     */
    protected void erase(){
        enemyJLabel.setVisible(false);
    }
}
