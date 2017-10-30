
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Invader_GUI extends TimerTask implements KeyListener{
	//private static final long serialVersionUID = 1L;
	private Container gameContentPane;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private static final int PLAYER_VELOCITY = WIDTH/200;
	private static final int BULLET_VELOCITY = WIDTH/120;	
	private static final int ENEMY_TIMER = 10;	
	private static final int NUM_STARTING_ROWS = 1;
	private static final int ENEMY_VELOCITY = WIDTH/250;
	private static final int GAME_PACE = 10;
	private static final int ENEMY_HEALTH = 1;
	private static final int gameWindowX = 0;
	private static final int gameWindowY = 0;
	
	private static boolean gameIsLost = false;
	private static Object lock = new Object();
	
	
	public static void main(String[] args) {	
		boolean first = true;
		while(true) {
			gameIsLost = false;
			Invader_GUI myGame = new Invader_GUI(NUM_STARTING_ROWS,ENEMY_HEALTH,ENEMY_VELOCITY,first);
			first = false;
			int level = 1;
			while (!gameIsLost) {
				synchronized(lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {e.printStackTrace();}
				}
				
				if (!gameIsLost) {
					myGame.close();
					myGame = null;
					level++;
					int rows = NUM_STARTING_ROWS*(1+ level/2);
					int health = ENEMY_HEALTH*(1+level/3);
					int velocity = (int) (ENEMY_VELOCITY*(1+ ((double)level*.3)/2));
					myGame = new Invader_GUI(rows,health,velocity,false);
				}
			}
		}
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
	
	private int enemyHealth;
	private int enemyVelocity;
	private int startingRows;
	
	
	/**
	 * sets up the GUI and starts the timer. Creates player and enemy manager as well.
	 */
	public Invader_GUI(int rows, int duckHealth, int duckVelocity, boolean firstTime) {
		enemyHealth = duckHealth;
		enemyVelocity = duckVelocity;
		startingRows = rows;
		
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
        
		if (firstTime) {
			openStartMenu();
		}
		else {
			startGame();
		}
	}
	
	public void close() {
		frame.removeAll();
	}
	
	/**
	 * called every tick of the timer. moves enemies, bullets, and checks for collisions.
	 */
	public void run() {
		winOrLose();
		enemyTimer += GAME_PACE;
		if (enemyTimer > ENEMY_TIMER) {
			enemyTimer = 0;
			//winOrLose();
			ducks.move();
		}
		
		playerBullets.move();
		
		checkForCollision();
		
		if (spacePressed) {
			playerBullets.shoot(player);
		}
		if (arrowPressed) {
			player.move(lastArrowPressed);
		}
	}			
	
	/**
	 * checks for collision by creating a 2d array of ints that represents all the living ducks' 
	 * locations.
	 * @see #playerBullets
	 * @see #ducks
	 */
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

	
	/**
	 * if the key was space, will shoot and then set spacePressed to true, which indicates that space is pressed.
	 * If the key was not space, will register that arrow was pressed and set arrowPressed to true
	 */
	@Override
	public void keyPressed(KeyEvent key) {
		if (key.getKeyCode()==KeyEvent.VK_SPACE && canShoot) { //canShoot might be obsolete
			playerBullets.shoot(player);
			spacePressed = true;
		}
		
		else if (key.getKeyCode() != KeyEvent.VK_SPACE) {
			arrowPressed = true;
			lastArrowPressed = key;
			player.move(key);
		}
		
	}

	/**
	 * if the key that was release was space, set spacePressed to false. 
	 * Likewise with arrow and arrowPressed
	 */
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
	
	/**
	 * checks for a win or loss
	 */
	private void winOrLose() {
		
		if(ducks.furthestDownPos() >= player.getY()) {
			openLoseMenu();
		}
		if(ducks.emptyDucks()) {
			openWinMenu();
		}
	} 
	
	private void openStartMenu() {
		JButton startButton = new JButton("Start Game");
		startButton.setBounds(WIDTH/2-100, HEIGHT/2-50, 200, 100);
		gameContentPane.add(startButton);
		startButton.setVisible(true);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startButton.setVisible(false);
				startGame();
			}
		});
	}
	
	private void openWinMenu() {
		eraseAll();
		JButton nextButton = new JButton("Start Next Level");
		nextButton.setBounds(WIDTH/2-100, HEIGHT/2-50, 200, 100);
		gameContentPane.add(nextButton);
		nextButton.setVisible(true);
		nextButton.requestFocus();
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nextButton.setVisible(false);
				synchronized(lock) {
					lock.notifyAll();
				}
			}
		});
	}
	
	private void openLoseMenu() {
		eraseAll();
		JLabel loseMessage = new JLabel();
		loseMessage.setText("GAME OVER");
		loseMessage.setBounds(WIDTH/2-60, HEIGHT/2-100, 200, 50);
		gameContentPane.add(loseMessage);
		loseMessage.setFont(new Font(loseMessage.getName(), Font.PLAIN, 20));
		loseMessage.setForeground(Color.RED);
		loseMessage.setVisible(true);
		loseMessage.requestFocus();

		JButton startNewButton = new JButton("Start New Game");
		startNewButton.setBounds(WIDTH/2-100, HEIGHT/2-50, 200, 100);
		gameContentPane.add(startNewButton);
		startNewButton.setVisible(true);
		startNewButton.requestFocus();
		startNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startNewButton.setVisible(false);
				loseMessage.setVisible(false);
				synchronized(lock) {
					gameIsLost = true;
					lock.notifyAll();
				}
			}
		});
	}
	
	private void eraseAll() {
		gameTimer.cancel();
		gameTimer.purge();
		player.erase();
		player = null;
		playerBullets = null;
		ducks.eraseDucks();
		ducks = null;
	}
	
	private void startGame() {
		frame.requestFocus();
		int startX = WIDTH / 2;
		int startY = (int)(((double)9/10)*HEIGHT);
		player = new Player(frame,startX,startY,1,PLAYER_VELOCITY,RIGHT);
		player.draw();
		
		ducks = new EnemyGroup(frame,startingRows*3, startingRows, enemyVelocity, enemyHealth);
		
		playerBullets = new BulletManager(frame,HEIGHT,BULLET_VELOCITY);
		
		
		gameTimer = new java.util.Timer();
		gameTimer.schedule(this, 0, GAME_PACE);
	}
}
