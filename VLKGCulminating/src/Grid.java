
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
	
	public int getRowNum(double currentY) {
		if (currentY == 151) {
			return 0;
		}
		else if (currentY == 231) {
			return 1;
		}
		else if (currentY == 311) {
			return 2;
		}
		else if (currentY == 391) {
			return 3;
		}
		else if (currentY == 417) {
			return 4;
		}
		else if (currentY == 551) {
			return 5;
		}
		return -1;
	}
	
	public void addToken(int row, int column) {
		boolean taken = isFilled(row, column);
//		if (column == -1) {
//			System.out.println("INVALID!");
//		}
//		while (taken && column < 7) {
		while (taken) {
//			column++;
			row--;
//			if (column < 7) {
			if (column >= 0) {
				taken = isFilled(row, column);
//				System.out.println(taken);
			}
		}
//		if (taken && column == 7) {
//			System.out.println("Row is filled!");
//		}
//		else {
			grid[row][column] = getTurn();
//		}
	}
	
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
