import javax.swing.JFrame;

public class EnemyGroup {
	private int numColumns;
	private int numRows;
	private Enemy[][] enemies;
	private boolean[][] enemyAlive;
	private JFrame frame;
	
	public EnemyGroup(JFrame passedInFrame, int cols, int rows, int eVelocity, int eHealth) {
		numColumns = cols;
		numRows = rows;
		frame = passedInFrame;
		
		enemies = new Enemy[numColumns][numRows];
		enemyAlive = new boolean[numColumns][numRows];
		
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				enemies[i][j] = new Enemy(frame,0,0,eHealth,eVelocity,Invader_GUI.RIGHT);
				
				int enemyX = enemies[i][j].imageWidth()*i + 40*i; //will need work but good for immediate use
				int enemyY = enemies[i][j].imageHeight()*j + 40*j;
				
				enemies[i][j].setX(enemyX);
				enemies[i][j].setY(enemyY);
				
				enemyAlive[i][j] = true;
			}
		}
	}
	
	public void move() {
		int imWidth = enemies[0][0].imageWidth();
		
		int rightBound = Invader_GUI.WIDTH;
		int leftBound = 0;
		
		int[] furthestRightCoords = getFurthestRight();
		int[] furthestLeftCoords = getFurthestLeft();
		
		int rightEnemyEdge =  enemies[furthestRightCoords[0]][furthestRightCoords[1]].getX() + imWidth + enemies[0][0].getVelocity();
		int leftEnemyEdge = enemies[furthestLeftCoords[0]][furthestLeftCoords[1]].getX() - enemies[0][0].getVelocity();
		
		if(rightEnemyEdge>=rightBound) {
			changeDirection(Invader_GUI.LEFT);
		}
		if(leftEnemyEdge<=leftBound) {
			changeDirection(Invader_GUI.RIGHT);
		}
		
		moveAll();
		//for testing
		getFurthestDown();

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
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				if (enemyAlive[i][j])
					enemies[i][j].draw();
			}
		}
	}
	
	public int[] getFurthestRight() {
		for (int i = enemyAlive.length-1; i>0 ;i--) {
			for (int j = 0; j<enemyAlive[i].length;j++) {
				if (enemyAlive[i][j]) {
					return new int[] {i,j};
				}	
			}
		}
		return new int[] {0,0};	
	}
	
	public int[] getFurthestLeft() {
		for (int i = 0; i<enemyAlive.length ;i++) {
			for (int j = 0; j<enemyAlive[i].length;j++) {
				if (enemyAlive[i][j]) {
					return new int[] {i,j};
				}
			}
		}
		return new int[] {0,0};	
	}

	public int[][] getDuckMap() {
		int [][] duckMap = new int[Invader_GUI.WIDTH+enemies[0][0].imageWidth()][Invader_GUI.HEIGHT+enemies[0][0].imageWidth()];
											//adding as a buffer
		for (int[] arr : duckMap) {
			for (int i : arr) {
				i = 0;
			}
		}
		
		for (int i = 0; i<enemies.length ;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				Enemy currentE = enemies[i][j];
				for (int x = currentE.getX(); x < Math.max(Math.min(currentE.getX() + currentE.imageWidth(),Invader_GUI.WIDTH),0); x++) {
					for (int y = currentE.getY(); y < currentE.getY() + currentE.imageHeight(); y++) {
							if (!enemyAlive[i][j])
								continue;	
							else if (i == 0 && j == 0) { //requires special implementation cause it would be 0
								duckMap[x][y] = -1;						
							}
							else {
								duckMap[x][y] = i*1000 + j;
							}
					}
				}
			}
		}
		return duckMap;
	}
	
	public void registerCollision(int x, int y) {
		enemies[x][y].loseHealth();
		if (enemies[x][y].getHealth()<=0) {
			enemies[x][y].erase();
			synchronized (this) {
				enemyAlive[x][y] = false;
			}
		}
	}
	
	public int[] getFurthestDown() {
		for (int j = enemyAlive[0].length-1; j>=0 ;j--) {
			for (int i = enemyAlive.length-1; i>=0;i--) {
				if (enemyAlive[i][j]) {
					return new int[] {i,j};
				}
			}
		}
		return new int[] {0,0};
	}
	
	public int furthestDownPos() {
		int[] furthestDownCoods = getFurthestDown();
		int downEnemyEdge = enemies[furthestDownCoods[0]][furthestDownCoods[1]].getY() + enemies[furthestDownCoods[0]][furthestDownCoods[1]].imageHeight();
		return downEnemyEdge;
	}
	
	public boolean emptyDucks() {
		//boolean empty = true;
		for (int i =0; i<enemyAlive.length;i++) {
			for (int j = 0; j<enemyAlive[i].length;j++) {
				if (enemyAlive[i][j]) {
					return false;
				}	
			}
		}
		return true;
	}
}
