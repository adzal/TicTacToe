import java.util.ArrayList;

public class GameEngine {
	private char[] board;
	private ArrayList<String> validMoves;
	private String message = "";
	private String[] rows = { "A", "B", "C" };
	private String[] cols = { "1", "2", "3" };

	public ArrayList<String> getValidMoves() {
		return validMoves;
	}

	public String getMessage() {
		return message;
	}

	public GameEngine() {
		message = "Let's play a game of Tic Tac Toe";
		resetGame();
	}

	public void resetGame() {
		validMoves = new ArrayList<>();

		String currentBoard = "";
		for (String row : rows) {
			for (String col : cols) {
				validMoves.add(row + col);
				currentBoard += "_";
			}
		}
//		currentBoard="XXO OOOXX";
		board = currentBoard.toCharArray();
	}

	public void moveAI(char letter) {
		// chose a random location from list of valid moves
		int index = (int) (Math.random() * validMoves.size());
		String selectedMove = validMoves.get(index).toUpperCase();

		makeMove(letter, selectedMove);
	}

	public boolean moveHuman(char letter, String move) {
		if (validMoves.contains(move)) {
			makeMove(letter, move);
			return true;
		}

		// Invalid move so retry
		System.out.println("Enter a valid move");
		return false;
	}

	private void makeMove(char letter, String move) {
		message = "'" + letter + "' plays " + move;
		// Move was valid so remove from list of validMoves
		validMoves.remove(move);
		updateBoard(letter, move);
	}

	public boolean isStillPlaying() {
		if (validMoves.size() > 0 && !isWinners()) {
			return true;
		} else {
			// If no moves left or someone won Game Over
			message = "Game Over";
			return false;
		}
	}

	private boolean isWinners() {
		for (int i = 0; i < board.length; i += cols.length) {
			if (isWinner(i, 1)) {
				return true;
			}
		}

		for (int i = 0; i < cols.length; i++) {
			if (isWinner(i, cols.length)) {
				return true;
			}
		}

		if (isWinner(0, cols.length + 1)) {
			return true;
		}

		if (isWinner(2, cols.length - 1)) {
			return true;
		}

		return false;
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

	public void displayBoard() {
		// Header Row
		String line = " |";
		for (String col : cols) {
			line += col + "|";
		}
		System.out.println(line);

		int index = 0;
		for (String row : rows) {
			line = row + "|";
			for (int i = 0; i < cols.length; i++) {
				line += board[index++] + "|";
			}
			System.out.println(line);
		}
	}

	public void updateBoard(char letter, String move) {
		// Add the move to the board
		int index = 0;
		for (String row : rows) {
			for (String col : cols) {
				// here is the move set the letter to the letter played
				if (move.equals(row + col)) {
					board[index] = letter;
				}
				index++;
			}
		}
	}
}
