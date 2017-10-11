
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class Invader_GUI extends TimerTask implements KeyListener{
	//private static final long serialVersionUID = 1L;
	//private JPanel myContent;
	private Container gameContentPane;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	private static final int PLAYER_VELOCITY = WIDTH/200;
	private static final int BULLET_VELOCITY = WIDTH/100;	
	private static final int ENEMY_TIMER = 10;	
	private static final int NUM_STARTING_ROWS = 3;
	private static final int NUM_STARTING_COLS = 3*NUM_STARTING_ROWS;
	private static final int ENEMY_HEALTH = 1;
	private static final int ENEMY_VELOCITY = WIDTH/300;
	private static final int GAME_PACE = 10;
	private static final int gameWindowX = 0;
	private static final int gameWindowY = 0;
	
	public static void main(String[] args) {
		Invader_GUI myGame = new Invader_GUI();
	}
	
	//https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
	public static Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	private boolean canShoot = true;
	private int enemyTimer = ENEMY_TIMER;
	private boolean spacePressed = false;
	private boolean arrowPressed = false;
	private KeyEvent lastArrowPressed = null;
	

	private JFrame frame;
	private Player player;
	private EnemyGroup ducks;
	private BulletManager playerBullets;
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
        frame.addKeyListener(this);
        frame.setResizable(false);
        
		int startX = WIDTH / 2;
		int startY = (int)(((double)9/10)*HEIGHT);
		player = new Player(frame,startX,startY,1,PLAYER_VELOCITY,RIGHT);
		player.draw();
		
		ducks = new EnemyGroup(frame,NUM_STARTING_COLS, NUM_STARTING_ROWS, ENEMY_VELOCITY, ENEMY_HEALTH);
		
		playerBullets = new BulletManager(frame,HEIGHT,BULLET_VELOCITY);
		
		gameTimer = new java.util.Timer();
		gameTimer.schedule(this, 0, GAME_PACE);
	}
	

	
	public void run() {
		enemyTimer += GAME_PACE;
		if (enemyTimer > ENEMY_TIMER) {
			enemyTimer = 0;
			ducks.move();
		}
		
		playerBullets.move();
		
		checkForCollision();
		
		if (spacePressed) {
			playerBullets.shoot(player);
		}
		if (arrowPressed) {
			player.move(lastArrowPressed, WIDTH);
		}
	}			
	
	public void checkForCollision() {
		int[] bulletLocation = playerBullets.bulletLocation();
		if (bulletLocation!=null) {
			int bulletX = bulletLocation[0];
			int bulletY = bulletLocation[1];
			//we can optimize by checking if bullet is in the bounds of the enemy group
		
			//make map
			int[][] duckMap = ducks.getDuckMap();
			
			if (duckMap[bulletX][bulletY]!=0) {
				int duckIndexX = 0;
				int duckIndexY = 0;
				
				if (duckMap[bulletX][bulletY] != -1) { 
					duckIndexX = duckMap[bulletX][bulletY]/1000;
					duckIndexY = duckMap[bulletX][bulletY]%1000;
				}
				ducks.registerCollision(duckIndexX,duckIndexY);
				playerBullets.hitRegister();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode()==KeyEvent.VK_SPACE && canShoot) {
			playerBullets.shoot(player);
			spacePressed = true;
		}
		
		else if (key.getKeyCode() != KeyEvent.VK_SPACE) {
			arrowPressed = true;
			lastArrowPressed = key;
			player.move(key, WIDTH);
		}
		
		/*if (!arrowPressed) {
			player.move(key,WIDTH);	
		}*/
	}

	@Override
	public void keyReleased(KeyEvent key) {
		if (key.getKeyCode()==KeyEvent.VK_SPACE)
			spacePressed = false;
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT ) {
			arrowPressed = false;
	    } else if (key.getKeyCode() == KeyEvent.VK_LEFT ) {
	    	arrowPressed = false;
	    } else if (key.getKeyCode() == KeyEvent.VK_UP ) {
	    	arrowPressed = false;
	    } else if (key.getKeyCode() == KeyEvent.VK_DOWN ) {
	    	arrowPressed = false;
	    }
	}

	@Override
	public void keyTyped(KeyEvent key) {
	}
}
