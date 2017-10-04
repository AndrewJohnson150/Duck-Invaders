import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Player implements ItemInterface {
	private int xPos;
	private int yPos;
	private int health;
	private int velocity;
	private int direction;
	
	private ImageIcon playerImage;
	private JLabel playerJLabel;
	private JFrame playerJFrame;
	
	public Player(JFrame passedInJFrame, int startX, int startY, int h, int vel, int dir) {
		xPos = startX;
		yPos = startY;
		health = h;
		velocity = vel;
		direction = dir;
	

	    playerJFrame = passedInJFrame;
	    playerJLabel = new JLabel();
	    playerJLabel.setBounds (10, 10, 10, 10); // arbitrary, will change later
	    playerJFrame.getContentPane().add(playerJLabel);
	    playerJLabel.setVisible(false);
	    playerJLabel.setVisible(true);
	    
	    playerImage = new ImageIcon ("playerImage.png");
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
	
	public void move() {
		if (direction==UP) {
			yPos+=velocity;
		}
		else if (direction==DOWN) {
			yPos-=velocity;
		}
		else if (direction==LEFT) {
			xPos-=velocity;
		}
		else if (direction==RIGHT) {
			xPos+=velocity;
		}
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
