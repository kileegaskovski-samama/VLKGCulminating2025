public class Grid {
	int[][] grid = new int[6][7];
	int turns;
	int currentRow;
	
	String p1Color = "yellow";
	String p2Color = "red";
	
	public Grid() {
		turns = 0;
		currentRow = 0;
	}
	
	public boolean isFilled(int row, int column) {
//		if (row != -1 && column != -1) {
		
		System.out.println("GETS HERE. " + row);
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
	
	public void subtractTurn() {
		turns--;
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
		System.out.println("Old row = " + currentRow);
		currentRow = row;
		System.out.println("Updated row = " + currentRow);
		grid[row][column] = getTurn();
		double rowNum = getRowVal(row);
		return rowNum;
	}
	
	public int getCurrentRow() {
		System.out.println("FINAL row = " + currentRow);
		return currentRow;
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
	
	public String tokenColorPOne(String color) {
		p1Color = color;
		return p1Color;
	}
	
	public String tokenColorPTwo(String color) {
		p2Color = color;
		return p2Color;
	}
	
	public boolean checkForWin() {
		int player = getTurnsNoModify();
		int rows = 6;
		int cols = 7;
		
		if (player == 1) {
			player = 2;
		}
		
		else if (player == 2) {
			player = 1;
		}
		
		System.out.println("Player " + player);
		
		// checks rows
		int hInARow = 0;
		
		for (int r = 0; r < rows; r++) {
			
			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == player) {
					hInARow++;
					
					if (hInARow >= 4) {
						System.out.println("Player " + player + " won! (row)");
						return true;
					}
				} else {
					hInARow = 0;
				}
			}
			
		}
		
		// checks columns
		int cInARow = 0;
		
		for (int c = 0; c < cols; c++) {
			
			for (int r = 0; r < rows; r++) {
				if (grid[r][c] == player) {
					cInARow++;
					
					if (cInARow >= 4) {
						System.out.println("Player " + player + " won! (column)");
						return true;
					}
				} else {
					cInARow = 0;
				}
			}
		}
		
		// checks diagonals left to right
		// https://www.geeksforgeeks.org/dsa/zigzag-or-diagonal-traversal-of-matrix/  
		int dInARow = 0;

		for (int r = 0; r < rows; r++) { // top half (diagonals starting from top row)
			int i = r;
			int j = 0;
			
			while (i >= 0 && j < cols) {
				// System.out.println(i + "," + j);
				
				if (grid[i][j] == player) {
					dInARow++;
					
					if (dInARow >= 4) {
						System.out.println("Player " + player + " won! (diagonal, L -> R, top half)");
						return true;
					}
				} else {
					dInARow = 0;
				}
				
				i--;
				j++;
			}
		}
		
		for (int c = 1; c < cols; c++) { // bottom half (diagonals starting from remaining rows)
			int i = rows - 1;
			int j = c;
			
			while (i >= 0 && j < cols) {
				// System.out.println(i + "," + j);
				if (grid[i][j] == player) {
					dInARow++;
					
					if (dInARow >= 4) {
						System.out.println("Player " + player + " won! (diagonal, L -> R, bottom half)");
						return true;
					}
				} else {
					dInARow = 0;
				}
				
				i--;
				j++;
			}
		}
		
		// checks diagonals right to left
		
		for (int r = 0; r < rows; r++) { // top half
			int i = r;
			int j = 6;
			
			while (i >= 0 && j >= 0) {
				// System.out.println(i + "," + j);
				if (grid[i][j] == player) {
					dInARow++;
					
					if (dInARow >= 4) {
						System.out.println("Player " + player + " won! (diagonal, R -> L, top half)");
						return true;
					}
				} else {
					dInARow = 0;
				}
				
				i--;
				j--;
			}
		}
		
		for (int c = 1; c < cols; c++) { // bottom half
			int i = rows - 1;
			int j = rows - c;
			
			while (i >= 0 && j >= 0) {
				// System.out.println(i + "," + j);
				
				if (grid[i][j] == player) {
					dInARow++;
					
					if (dInARow >= 4) {
						System.out.println("Player " + player + " won! (diagonal, R -> L, bottom half)");
						return true;
					}
				} else {
					dInARow = 0;
				}
				
				i--;
				j--;
			}
		}
			
			System.out.println("No wins");
			return false;
	}
		
		
		
}
