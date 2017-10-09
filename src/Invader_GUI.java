
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
import javax.swing.JPanel;

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

	private static final int BULLET_VELOCITY = WIDTH/100;	
	private static final int BULLET_TIMER = 500;	
	private static final int ENEMY_TIMER = 500;	
	private static final int NUM_STARTING_ROWS = 4;
	private static final int NUM_STARTING_COLS = 3*NUM_STARTING_ROWS;
	private static final int ENEMY_HEALTH = 1;
	private static final int ENEMY_VELOCITY = WIDTH;
	private static final int GAME_PACE = 30;
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
	private int bulletTimer = 0;
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
		player = new Player(frame,startX,startY,1,10,RIGHT);
		player.draw();
		
		ducks = new EnemyGroup(frame, WIDTH, NUM_STARTING_COLS, NUM_STARTING_ROWS, ENEMY_VELOCITY, ENEMY_HEALTH);
		
		playerBullets = new BulletManager(frame,HEIGHT,BULLET_VELOCITY);
		
		gameTimer = new java.util.Timer();
		gameTimer.schedule(this, 0, GAME_PACE);
	}
	

	
	public void run() {
		winOrLose();
		enemyTimer += GAME_PACE;
		if (enemyTimer > ENEMY_TIMER) {
			enemyTimer = 0;
			//winOrLose();
			ducks.move();
		}
		
		playerBullets.move();
		
		if (spacePressed && canShoot) {
			playerBullets.shoot(player);
			canShoot = false;
		}
		if (arrowPressed) {
			player.move(lastArrowPressed, WIDTH);
		}

		
		if (!canShoot)
			bulletTimer += GAME_PACE;
		if (bulletTimer > BULLET_TIMER) {
			bulletTimer = 0;
			canShoot = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode()==KeyEvent.VK_SPACE && canShoot) {
			playerBullets.shoot(player);
			canShoot = false;
			spacePressed = true;
		}
		
		else if (key.getKeyCode() != KeyEvent.VK_SPACE) {
			arrowPressed = true;
			lastArrowPressed = key;
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
	
	private void winOrLose() {
		if(ducks.furthestDownPos() >= player.getY()) {
			System.out.println("AHHHHHHH" + player.imageHeight());
			System.exit(0);
		}
	}
}
