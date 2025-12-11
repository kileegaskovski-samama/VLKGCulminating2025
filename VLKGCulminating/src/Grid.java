
public class Grid {
	int[][] grid = new int[6][7];
	int turns;
	
	public Grid() {
		turns = 0;
	}
	
	public boolean isFilled(int row, int column) {
		if (grid[row][column] == 0) {
			return false;
		}
		return true;
	}

	public int getTurn() {
		if (turns > 42) {
			return -1;
		}
		else if (turns % 2 == 0) {
			turns++;
			return 1;
		}
		turns++;
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
	
	public int getRowVal(int rowNum) {
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
	
//	public void addToken(int row, int column) {
	public double addToken(int column) {
		int row = 5;
		boolean taken = isFilled(row, column);
		while (taken) {
			row--;
			if (column >= 0) {
				taken = isFilled(row, column);
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
}
