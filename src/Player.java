import java.awt.Image;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Player implements ItemInterface {
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	
	private ImageIcon playerImage;
	private JLabel playerJLabel;
	private JFrame playerJFrame;
	
	public Player(JFrame passedInJFrame, int startX, int startY, int h, int vel) {
		xPos = startX;
		yPos = startY;
		health = h;
		velocity = vel;
	

	    playerJFrame = passedInJFrame;
	    playerJLabel = new JLabel();
	    playerJLabel.setBounds (10, 10, 10, 10); // arbitrary, will change later
	    playerJFrame.getContentPane().add(playerJLabel);
	    playerJLabel.setVisible(false);
	    playerJLabel.setVisible(true);

	    URL url = Enemy.class.getResource(
                "/images/playerImage.png");
	    playerImage = new ImageIcon (url);
	    Image i = Invader_GUI.getScaledImage(playerImage.getImage(), 50, 50);
	    playerImage = new ImageIcon(i);
	}
	
	public int imageWidth() {
		return playerImage.getIconWidth();
	}

	public int imageHeight() {
		return playerImage.getIconHeight();
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
	
	public void move(KeyEvent e) {
		int xBoundR = (Invader_GUI.WIDTH- playerImage.getIconWidth());
		int xBoundL = 0;
			
		if (xPos >=  xBoundL && e.getKeyCode() == (KeyEvent.VK_LEFT) ) {
			xPos -= velocity;
		}

		if (xPos <= xBoundR && e.getKeyCode() == (KeyEvent.VK_RIGHT) ) {
			xPos += velocity;
		}
		
		draw();
		
	}
	
    protected void draw(){ 
        playerJLabel.setIcon(playerImage);
        playerJLabel.setBounds(xPos,yPos,playerImage.getIconWidth(),playerImage.getIconHeight());  
        playerJLabel.setVisible(true);
    }
    
    protected void erase(){
        playerJLabel.setVisible(false);
    }
	

}
