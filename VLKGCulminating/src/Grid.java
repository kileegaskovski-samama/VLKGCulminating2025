import java.util.ArrayList;

/**
 * Models the grid for connect 4 Can add tokens in the grid for two players Can
 * check if one player has four tokens in a row horizontally, vertically, or
 * diagonally
 */
public class Grid {
	private int[][] grid = new int[6][7]; // the Connect Four grid
	private int turns; // keeps track of whose turn it is
	private int currentRow; // keeps track of the row of the last placed token

	// hold temporary values to determine as the game is being played whether a
	// player has four-in-a-row
	public ArrayList<Integer> rowValues = new ArrayList<>();
	public ArrayList<Integer> colValues = new ArrayList<>();

	/**
	 * Creates a six by seven grid
	 * 
	 * @param int turns - tracks whose turn it is
	 * @param int currentRow - tracks the row of the last placed token
	 */
	public Grid() {
		turns = 0;
		currentRow = 0;
	}

	/**
	 * Checks if a certain coordinate in the grid is already filled with a token
	 * 
	 * @param row    - the row of the coordinate being checked
	 * @param column - the column of the coordinate being checked
	 * @return returns true if the coordinate is filled, returns false otherwise
	 */
	public boolean isFilled(int row, int column) {
		if (grid[row][column] == 0) {
			return false; // the location on the grid is not filled
		}
		return true; // the location on the grid IS filled
	}

	/**
	 * Gets the current turn of the player and adds to the turn
	 * 
	 * @return returns 1 if it is player 1's turn, and 2 if it is player 2's turn
	 *         Postcondition: turns will be increased by 1
	 */
	public int getTurn() {
		if (turns > 42) {
			return -1;
		} else if (turns % 2 == 0) {
			turns++; // increases the turns count by one
			return 1;
		}
		turns++; // increases the turns count by one
		return 2;
	}

	/**
	 * Subtracts a turn from the turns count
	 */
	public void subtractTurn() {
		turns--;
	}

	/**
	 * Gets the current turn of the player without modifying turns
	 * 
	 * @return returns 1 if it is player 1's turn, and 2 if it is player 2's turn
	 */
	public int getTurnsNoModify() {
		if (turns % 2 == 0) {
			return 1;
		}
		return 2;
	}

	/**
	 * Gets the column number
	 * 
	 * @param currentX - the x-coordinate of the token's position on the grid
	 * @return returns the column number based on the x-coordinate of the token's
	 *         position
	 */
	public int getColumn(double currentX) {
		if (currentX == 111) {
			return 0;
		} else if (currentX == 191.0) {
			return 1;
		} else if (currentX == 271.0) {
			return 2;
		} else if (currentX == 351.0) {
			return 3;
		} else if (currentX == 431.0) {
			return 4;
		} else if (currentX == 511.0) {
			return 5;
		} else if (currentX == 591.0) {
			return 6;
		}
		return -1;
	}

	/**
	 * Gets the value of the y-coordinate of a specific column in the grid
	 * 
	 * @param colNum - the column number in the grid
	 * @return returns the y-coordinate on the screen based on the column number
	 */
	public double getColumnVal(int colNum) {
		if (colNum == 0) {
			return 111;
		} else if (colNum == 1) {
			return 191.0;
		} else if (colNum == 2) {
			return 271.0;
		} else if (colNum == 3) {
			return 351.0;
		} else if (colNum == 4) {
			return 431.0;
		} else if (colNum == 5) {
			return 511.0;
		} else if (colNum == 6) {
			return 591.0;
		}
		return -1;
	}

	/**
	 * Gets the value of the x-coordinate of a specific row in the grid
	 * 
	 * @param rowNum - the row number in the grid
	 * @return returns the x-coordinate on the screen based on the row number
	 */
	public double getRowVal(int rowNum) {
		if (rowNum == 0) {
			return 151;
		} else if (rowNum == 1) {
			return 231;
		} else if (rowNum == 2) {
			return 311;
		} else if (rowNum == 3) {
			return 391;
		} else if (rowNum == 4) {
			return 471;
		} else if (rowNum == 5) {
			return 551;
		}
		return -1;
	}

	/**
	 * Adds a token to a specific column in the grid
	 * 
	 * @param column - the column the token is meant to be placed in
	 * @return - returns the lowest unfilled row number in the indicated column
	 */
	public double addToken(int column) {
		int row = 5; // assumes that the bottom row in the specified column is unfilled
		boolean taken = isFilled(row, column); // checks if the row is in fact taken

		// if the row is taken, check if the row above is taken until an unfilled row is
		// found
		while (taken) {
			row--;
			if (column >= 0) {
				taken = isFilled(row, column);
			}
		}

		currentRow = row; // update currentRow
		grid[row][column] = getTurn(); // assigns the number 1 or 2 to represent which player has dropped a token in
										// location grid[row][column]
		double rowNum = getRowVal(row);
		return rowNum; // returns the x-coordinate location for the token to be placed on the screen
	}

	/**
	 * Gets the row of the last-placed token
	 * 
	 * @return returns the row of the last-placed token
	 */
	public int getCurrentRow() {
		return currentRow;
	}

	/**
	 * Prints out the entire grid Includes the most recent locations of the tokens
	 * of players 1 and 2
	 */
	public void print() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++) {
				System.out.print(grid[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Checks if a player got four-in-a-row
	 * 
	 * @return returns true if a player got four-in-a-row, and returns false
	 *         otherwise
	 */
	public boolean checkForWin() {
		int player = getTurnsNoModify();
		int rows = 6;
		int cols = 7;

		// The numbers for player 1 and 2 are swapped because due to the game logic,
		// player 1 is represented by "2" and vice versa
		if (player == 1) {
			player = 2;
		} else if (player == 2) {
			player = 1;
		}

		// checks the rows for four-in-a-row
		int hInARow = 0;

		for (int r = 0; r < rows; r++) {
			hInARow = 0;

			for (int c = 0; c < cols; c++) {
				if (grid[r][c] == player) {
					hInARow++;
					addIndex(r, c);

					if (hInARow >= 4) {
						return true;
					}
				} else {
					hInARow = 0;
					rowValues.clear();
					colValues.clear();

				}
			}

		}

		// checks the columns for four-in-a-row
		int cInARow = 0;

		for (int c = 0; c < cols; c++) {
			cInARow = 0;
			rowValues.clear();
			colValues.clear();

			for (int r = 0; r < rows; r++) {
				if (grid[r][c] == player) {
					cInARow++;
					addIndex(r, c);

					if (cInARow >= 4) {
						return true;
					}
				} else {
					cInARow = 0;
					rowValues.clear();
					colValues.clear();

				}
			}
		}

		/// checks diagonals left to right
		// checks the top half (diagonals starting from top row)
		int dInARow = 0;

		for (int r = 0; r < rows; r++) { // determines index of token where diagonal starts
			int i = r;
			int j = 0;
			dInARow = 0;

			while (i >= 0 && j < cols) { // goes through diagonal while i and j values are valid in grid
				if (grid[i][j] == player) {
					dInARow++;
					addIndex(i, j);

					if (dInARow >= 4) {
						return true;
					}
				} else {
					dInARow = 0;
					rowValues.clear();
					colValues.clear();

				}

				i--;
				j++;
			}
		}

		// checks the bottom half (diagonals starting from remaining rows)
		for (int c = 1; c < cols; c++) {
			int i = rows - 1;
			int j = c;
			dInARow = 0;

			while (i >= 0 && j < cols) {
				if (grid[i][j] == player) {
					dInARow++;
					addIndex(i, j);

					if (dInARow >= 4) {
						return true;
					}
				} else {
					dInARow = 0;
					rowValues.clear();
					colValues.clear();

				}

				i--;
				j++;
			}
		}

		/// checks diagonals right to left
		// checks the top half
		for (int r = 0; r < rows; r++) {
			int i = r;
			int j = 6;
			dInARow = 0;

			while (i >= 0 && j >= 0) {
				if (grid[i][j] == player) {
					dInARow++;
					addIndex(i, j);

					if (dInARow >= 4) {
						return true;
					}
				} else {
					dInARow = 0;
					rowValues.clear();
					colValues.clear();

				}

				i--;
				j--;
			}
		}

		// checks the bottom half
		for (int c = 1; c < cols; c++) {
			int i = rows - 1;
			int j = rows - c;
			dInARow = 0;

			while (i >= 0 && j >= 0) {
				if (grid[i][j] == player) {
					dInARow++;
					addIndex(i, j);

					if (dInARow >= 4) {
						return true;
					}
				} else {
					dInARow = 0;
					rowValues.clear();
					colValues.clear();

				}

				i--;
				j--;
			}
		}

		return false; // no player has four-in-a-row
	}

	/**
	 * Keeps track of winning four-in-a-row tokens
	 * 
	 * @param r - the row of the token
	 * @param c - the column of the token
	 */
	public void addIndex(int r, int c) {
		rowValues.add(r);
		colValues.add(c);

	}

}