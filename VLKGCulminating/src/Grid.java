
public class Grid {
	int[][] grid = new int[6][7];
	int turns;
	
	public Grid() {
		turns = 0;
	}
	
	public boolean isFilled(int row, int column) {
//		if (row != -1 && column != -1) {
		if (grid[row][column] == 0) {
			return false;
		}
		return true;
//		}
//		return true;
	}

	public int getTurn() {
		if (turns > 42) {
			return -1;
		}
		else if (turns % 2 == 0) {
			turns++;
//			System.out.println("Turns AFTER: " + turns);
			return 1;
		}
//		System.out.println("Turns: " + turns);
		turns++;
//		System.out.println("Turns after: " + turns);
		return 2;
	}
	
	public int getTurnsNoModify() {
		if (turns % 2 == 0) {
			return 1;
		}
		return 2;
	}
	
	public int getColumnNum(double currentX) {
		if (currentX == 111) {
			return 0;
		}
		else if (currentX == 191.0) {
			return 1;
		}
		else if (currentX == 271.0) {
			return 2;
		}
		else if (currentX == 351.0) {
			return 3;
		}
		else if (currentX == 431.0) {
			return 4;
		}
		else if (currentX == 511.0) {
			return 5;
		}
		else if (currentX == 591.0) {
			return 6;
		}
		return -1;
	}
	
	public double getColumnVal(int colNum) {
		if (colNum == 0) {
			return 111;
		}
		else if (colNum == 1) {
			return 191.0;
		}
		else if (colNum == 2) {
			return 271.0;
		}
		else if (colNum == 3) {
			return 351.0;
		}
		else if (colNum == 4) {
			return 431.0;
		}
		else if (colNum == 5) {
			return 511.0;
		}
		else if (colNum == 6) {
			return 591.0;
		}
		return -1;
	}
	
	public double getRowVal(int rowNum) {
		if (rowNum == 0) {
			return 151;
		}
		else if (rowNum == 1) {
			return 231;
		}
		else if (rowNum == 2) {
			return 311;
		}
		else if (rowNum == 3) {
			return 391;
		}
		else if (rowNum == 4) {
			return 471;
		}
		else if (rowNum == 5) {
			return 551;
		}
		return -1;
	}
	
	public double addToken(int column) {
		int row = 5;
		boolean taken = isFilled(row, column);
		while (taken) {
			System.out.println("Row: " + row);
			row--;
			if (column >= 0) {
				taken = isFilled(row, column);
				System.out.println("Filled? " + taken);
			}
		}
		grid[row][column] = getTurn();
		double rowNum = getRowVal(row);
		return rowNum;
	}
	
	public int getRowNum(int column) {
		int row = 5;
		boolean taken = isFilled(row, column);
		while (taken) {
			row--;
			if (column >= 0) {
				taken = isFilled(row, column);
			}
		}
		return row;
	}
	
	// Prints entire grid
	public void print() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				System.out.print(grid[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean check() {
		int inARow = 0;
		int player = getTurnsNoModify();
		
		// checks rows
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				if (grid[r][c] == player) {
					inARow++;
					
					if (inARow == 4) {
						System.out.println("Player " + player + " won!");
						return true;
					}
				} else {
					inARow = 0;
				}
			}
			
		}
		System.out.println("No wins");
		return false;
	}
}
