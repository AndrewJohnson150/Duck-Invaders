
import java.awt.Color;
import java.awt.Container;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Invader_GUI extends TimerTask{
	private static final long serialVersionUID = 1L;
	private JPanel myContent;
	private Container gameContentPane;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private final int WIDTH = 900;
	private final int HEIGHT = 900;
	private final int gameWindowX = 100;
	private final int gameWindowY = 100;
	private JFrame frame;
	private Player player;
    
	private static final int GAME_PACE = 350;
	private Timer gameTimer;
	
	public Invader_GUI() {
		String gameTitle = "Duck Invaders";
		frame = new JFrame(gameTitle);
		frame.setSize(WIDTH, HEIGHT);
		
		frame.setLocation(gameWindowX, gameWindowY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gameContentPane = frame.getContentPane();
        gameContentPane.setLayout(null); // not need layout, will use absolute system
        gameContentPane.setBackground(Color.gray);
        
        
		int startX = WIDTH / 2;
		int startY = (int)(((double)6/8)*HEIGHT);
		player = new Player(frame,startX,startY,1,0,RIGHT);
		player.draw();
		
		gameTimer = new java.util.Timer();
		gameTimer.schedule(this, 0, GAME_PACE);
	}
	
	public static void main(String[] args) {
		Invader_GUI myGame = new Invader_GUI();

	}
	
	public void run() {
		
	}

}
