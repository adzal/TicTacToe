import java.util.ArrayList;
import java.util.Collections;

public class GameEngine {
	private char[] board;
	final char EMPTY_CELL = '_';
	ArrayList<GameNode> gameNodes = new ArrayList<GameNode>();

	private char humanPlayer;
	private char aiPlayer;

	private String message = "";
	private static String[] rows = { "A", "B", "C" };
	private static String[] cols = { "1", "2", "3" };

	public String getMessage() {
		return message;
	}

	public GameEngine(char humanPlayer, char aiPlayer) {
		message = "Let's play a game of Tic Tac Toe";
		this.humanPlayer = humanPlayer;
		this.aiPlayer = aiPlayer;
	}

	/**
	 * Wipe the board or preload data onto the board for testing
	 */
	public void resetGame() {
		String s = "";
		for (int i = 0; i < cols.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				s += "_";
			}
		}
//		s = "OXO_X_XOO";
		board = s.toCharArray();
	}

	/**
	 * return the number of empty cells as the depth. We use this when we have
	 * preloaded data onto the board.
	 * 
	 * @return
	 */
	private int currentDepth() {
		int depth = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != EMPTY_CELL) {
				depth++;
			}
		}
		return depth;
	}

	public static String[] getBoard(char[] board) {
		String[] lines = new String[4];
		// Header Row
		String line = " |";
		for (String col : cols) {
			line += col + "|";
		}
		lines[0] = line;

		int index = 0;
		for (int i = 0; i < rows.length; i++) {
			line = rows[i] + "|";
			for (int j = 0; j < cols.length; j++) {
				line += board[index++] + "|";
			}
			lines[i + 1] = line;
		}

		return lines;
	}

	public void displayBoard() {
		displayBoard(board, 0);
	}

	public static void displayBoard(char[] board, int depth) {
		String[] lines = getBoard(board);
		for (String line : lines) {
			System.out.println("   ".repeat(depth) + line);
		}
	}

	public void moveAI() {
		// chose a random location from list of valid moves
		int bestScore = -1000;
		int bestMove = 0;

		// set depth if we're starting partially filled in resetGame()
		int depth = currentDepth();
		for (int i = 0; i < board.length; i++) {
			if (board[i] == EMPTY_CELL) {
				board[i] = aiPlayer;

				boolean isMax = true;
				int score = minmax(depth + 1, !isMax);
				//gameNodes.add(new GameNode(moveToString(i), i, depth, board.clone(), score, isMax));

				if (score > bestScore) {
					bestScore = score;
					bestMove = i;
				}
				board[i] = EMPTY_CELL;
			}
		}

		// If you want to visualise the tree uncomment
//		Collections.sort(gameNodes);
//		System.out.println("After Sort");
//		System.out.println(gameNodes);

		System.out.println("AI plays " + moveToString(bestMove));
		makeMove(aiPlayer, bestMove);
	}

	private int minmax(int depth, boolean isMax) {
		int bestScore = isMax ? -1000 : 1000;

		// Get score for this position
		int score = evaluateBoard();
		if (score == 10) {
			bestScore = score - depth;
		} else if (score == -10) {
			bestScore = score + depth;
		} else if (depth == 9) {
			bestScore = 0;
		} else {
			// Loop through all the child moves recursively
			for (int i = 0; i < board.length; i++) {
				if (board[i] == EMPTY_CELL) {
					// Make passed move
					board[i] = isMax ? aiPlayer : humanPlayer;

					score = minmax(depth + 1, !isMax);
					//gameNodes.add(new GameNode(moveToString(i), i, depth, board.clone(), score, isMax));

					if (isMax) {
						bestScore = Math.max(bestScore, score);
					} else {
						bestScore = Math.min(bestScore, score);
					}

					// Reset board
					board[i] = EMPTY_CELL;
				}
			}
		}
		return bestScore;
	}

	private int evaluateBoard() {
		int score = 0;
		if (hasPlayerWon(aiPlayer)) {
			score = 10;
		} else if (hasPlayerWon(humanPlayer)) {
			score = -10;
		}
		return score;
	}

	/**
	 * Check if the cell is valid and empty, if so makes that move
	 * @param move
	 * @return if move is valid
	 */
	public boolean moveHuman(String move) {
		Integer i = moveFromString(move);
		//If a valid cell and is empty then make move
		if (i >= 0 && board[i] == EMPTY_CELL) {
			makeMove(humanPlayer, i);
			return true;
		}

		// Invalid move so retry
		System.out.println("Enter a valid move");
		return false;
	}

	/**
	 * Translates the index 0->8 into a nice string e.g. 
	 * cell 0 is A1 cell 8 is C3
	 * @param move
	 * @return index of cell or -1 if not found
	 */
	private int moveFromString(String move) {
		int index = 0;		
		for (int i = 0; i < cols.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				if (move.equals(rows[i] + cols[j])) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

	private String moveToString(int move) {
		int index = 0;
		for (int i = 0; i < cols.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				if (index == move) {
					return rows[i] + cols[j];
				}
				index++;
			}
		}
		return "No move to make!";
	}

	private void makeMove(char player, int move) {
		board[move] = player;
	}

	public boolean isStillPlaying() {
		if (currentDepth() <= 8 && !isWinners()) {
			return true;
		} else {
			// If no moves left or someone won Game Over
			message = "Game Over";
			return false;
		}
	}

	/**
	 * Has any player won
	 * 
	 * @return
	 */
	private boolean isWinners() {
		char winner = whoWon();

		if (winner == '0') {
			return false;
		}
		return true;
	}

	/**
	 * Has a specific player won?
	 * 
	 * @param player
	 * @return
	 */
	public boolean hasPlayerWon(char player) {
		char winner = whoWon();

		if (winner == player) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the 'X' or 'O' if a winner or '0' if no winner
	 *  
	 * given columns A,B&C cols.width=3, board is a simple array 0 to 8
	 * 
	 * to see if Three in a row we just
	 * 
	 * For horizontal it add +1 if x==x+1 && x+1==x+2 
	 * X X X
	 * 
	 * For Vertical it adds cols.width if x==x+3 && x+3==x+6 
	 * X _ _ 
	 * X _ _ 
	 * X _ _
	 * 
	 * For Diagonal down it adds cols.width+1 if x==x+4 && x+4==x+8 
	 * X _ _ 
	 * _ X _ 
	 * _ _ X
	 * 
	 * For Diagonal up it starts at the 3rd col and adds 2 if x==x+2 && x+2==x+4 
	 * _ _ X
	 * _ X _
	 * X _ _
	 * 
	 * @param startPos
	 * @param width
	 * @return
	 */
	public char whoWon() {
		// Check all horizontal
		for (int i = 0; i < board.length; i += cols.length) {
			if (isWinner(i, 1)) {
				return board[i];
			}
		}

		// Check verticals
		for (int i = 0; i < cols.length; i++) {
			if (isWinner(i, cols.length)) {
				return board[i];
			}
		}

		// Check Top left to bottom right diagonal
		if (isWinner(0, cols.length + 1)) {
			return board[0];
		}

		// Check bottom left to top right diagonal
		if (isWinner(2, cols.length - 1)) {
			return board[2];
		}

		return '0';
	}

	private boolean isWinner(int startPos, int width) {
		// starting position has an X or O continue if not return
		if (board[startPos] == 'X' || board[startPos] == 'O') {
			if (board[startPos] == board[startPos + width] && board[startPos + width] == board[startPos + 2 * width]) {
				return true;
			}
		}
		return false;
	}

}
