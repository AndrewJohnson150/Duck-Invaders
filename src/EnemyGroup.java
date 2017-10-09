import javax.swing.JFrame;

public class EnemyGroup {
	private int numColumns;
	private int numRows;
	private Enemy[][] enemies;
	private boolean[][] enemyAlive;
	private JFrame frame;
	
	private int width;
	
	public EnemyGroup(JFrame passedInFrame, int screenWidth, int cols, int rows, int eVelocity, int eHealth) {
		numColumns = cols;
		numRows = rows;
		frame = passedInFrame;
		width = screenWidth;
		
		enemies = new Enemy[numColumns][numRows];
		enemyAlive = new boolean[numColumns][numRows];
		
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				enemies[i][j] = new Enemy(frame,0,0,eHealth,eVelocity,Invader_GUI.RIGHT);
				
				int enemyX = enemies[i][j].imageWidth()*i + 10*i; //will need work but good for immediate use
				int enemyY = enemies[i][j].imageHeight()*j + 10*j;
				
				enemies[i][j].setX(enemyX);
				enemies[i][j].setY(enemyY);
				
				enemyAlive[i][j] = true;
			}
		}
		//for testing
		checkCollision();
	}
	
	public void move() {
		int imWidth = enemies[0][0].imageWidth();
		int rightBound =  (width - imWidth);
		int leftBound = 0;
		int rightEnemyEdge =  enemies[getFurthestRight()][0].getX();
		int leftEnemyEdge = enemies[getFurthestLeft()][0].getX();
		
		if(rightEnemyEdge>rightBound) {
			changeDirection(Invader_GUI.LEFT);
		}
		if(leftEnemyEdge<leftBound) {
			changeDirection(Invader_GUI.RIGHT);
		}
		
		moveAll();

	}
	
	
	private void changeDirection(int dir) {
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
					enemies[i][j].setDirection(dir);
					enemies[i][j].goDown();
			}
		}
	}
	
	private void moveAll() {
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				if (enemyAlive[i][j])
					enemies[i][j].move();
			}
		}
	}
	
	public void checkCollision() {
		//implement later
		// for testing purpose
		
		enemies[5][4].loseHealth();
		if (enemies[5][4].getHealth()==0) {
			enemies[5][4].erase();
			enemyAlive[5][4] = false;		}
	}
	
	public int getFurthestRight() {
		for (int i = enemyAlive.length-1; i>0 ;i--) {
			for (int j = 0; j<enemyAlive[i].length;j++) {
				if (enemyAlive[i][j]) {
					return i;
				}
			}
		}
		return 0;	
	}
	
	public int getFurthestLeft() {
		for (int i = 0; i<enemyAlive.length ;i++) {
			for (int j = 0; j<enemyAlive[i].length;j++) {
				if (enemyAlive[i][j]) {
					return i;
				}
			}
		}
		return 0;	
	}

}
