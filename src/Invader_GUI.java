
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Invader_GUI extends JPanel{
	//private static final Color BLACK = new Color(0,0,0);
	//private static final Color RED = new Color(255,0,0);
	//private static final Color WHITE = new Color(255,255,255);
	//private static final Color CYAN = new Color(0,255,255);
	private static final long serialVersionUID = 1L;
	private JPanel myContent;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private final int WIDTH = 900;
	private final int HEIGHT = 900;
	private final int gameWindowX = 100;
	private final int gameWindowY = 100;
	private JFrame frame;
	private Player user;
	
	public Invader_GUI() {
		//int startX = WIDTH / 2;
		//int startY = (7/8)*HEIGHT;
		//user = new Player(startX,startY,1,0,RIGHT);
		String gameTitle = "Duck Invaders";
		frame = new JFrame(gameTitle);
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setLocation(gameWindowX, gameWindowY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.add(frame);
        this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Invader_GUI myGame = new Invader_GUI();

	}

}
