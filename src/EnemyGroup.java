import javax.swing.JFrame;

public class EnemyGroup {
	private int numColumns;
	private int numRows;
	private Enemy[][] enemies;
	private boolean[][] enemyAlive;
	private JFrame frame;
	
	private int width;
	
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
		
		int rightBound =  (Invader_GUI.WIDTH);
		int leftBound = 0;
		
		int rightEnemyEdge =  enemies[getFurthestRight()][0].getX() + imWidth;
		int leftEnemyEdge = enemies[getFurthestLeft()][0].getX();
		
		if(rightEnemyEdge>rightBound-25) {
			changeDirection(Invader_GUI.LEFT);
		}
		if(leftEnemyEdge<leftBound+25) {
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
		for (int i = 0; i<enemies.length;i++) {
			for (int j = 0; j<enemies[i].length;j++) {
				if (enemyAlive[i][j])
					enemies[i][j].draw();
			}
		}
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
				for (int x = currentE.getX(); x < currentE.getX() + currentE.imageWidth(); x++) {
					for (int y = currentE.getY(); y < currentE.getY() + currentE.imageHeight(); y++) {
						//try{
							
							if (!enemyAlive[i][j])
								continue;
							else if (i == 0 && j == 0) { //requires implementation cause it would be 0
									duckMap[x][y] = -1;						
							}
							else
								duckMap[x][y] = i*1000 + j; //maybe unnecessary
						//} catch (Exception ignore) {System.out.println("error hit");}
					}
				}
			}
		}
		return duckMap;
	}
	
	public void registerCollision(int x, int y) {
		if (x == 0 && y == 0 ) {
			System.out.println("Hit that fucker");
		}
		enemies[x][y].loseHealth();
		if (enemies[x][y].getHealth()<=0) {
			enemies[x][y].erase();
			enemyAlive[x][y] = false;
		}
	}

}
