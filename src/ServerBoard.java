/**
 * The ServerBoard class represents the game board for Tic Tac Toe.
 * It manages the state of the board, updates player moves, checks for wins,
 * and resets the board for new games.
 */
public class ServerBoard {
	private String[][] board;
	
	/**
     * Constructs a ServerBoard and initializes the board to a 3x3 grid.
     */
	public ServerBoard() {
		board = new String [3][3];
	}
	
	/**
     * Updates the board at the specified row and column with the player's identifier.
     *
     * @param player 	the identifier of the player making the move (either X or O)
     * @param row 		the row index where the player is making a move
     * @param col 		the column index where the player is making a move
     */
	public void updateBoard(String player, int row, int col) {
		System.out.println("Update board " + row + ", " + col + "by " + player);
		board[row][col] = player;
	}
	
	/**
     * Checks if the board is full.
     *
     * @return true if the board is full, false otherwise
     */
	public synchronized boolean isBoardFull() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (board[row][col] == null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
     * Resets the board by clearing all texts.
     */
	public synchronized void resetBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = null;
			}
		}
	}

	/**
     * Retrieves the value at a specified grid position.
     *
     * @param row 	the row index of the grid
     * @param col 	the column index of the grid
     * @return the value at the specified grid position, or null if empty
     */
	public synchronized String getGrid(int row, int col) {
		return board[row][col];
	}
	
	/**
     * Checks the current state of the game to determine if there is a winner,
     * a draw, or if the game should continue.
     *
     * @return "X" if player X wins, "O" if player O wins, "DRAW" if it's a draw,
     *         or "continue" if the game is still ongoing
     */
	public synchronized String checkWin() {
		// check horizontally
		for (int i = 0; i < 3; i++) {

			if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][0].equals(board[i][2])) {
				return board[i][0];
			}
			
			if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[0][i].equals(board[2][i])) {
				return board[0][i];
			}
		}
		
		// check diagonally
		if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
			return board[0][0];
		}
		else if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
			return board[0][2];
		}
		
		// check draw
		if (isBoardFull()) {
			return "DRAW";
		}
		
		return "continue";
				
	}
}
