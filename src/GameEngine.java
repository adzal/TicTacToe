import java.util.ArrayList;

public class GameEngine {
	private ArrayList<String> validMoves;
	private ArrayList<Move> moves;
	private String message = "";
	private String[] rows = { "A", "B", "C" };
	private String[] cols = { "1", "2", "3" };

	public ArrayList<String> getValidMoves() {
		return validMoves;
	}

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public String getMessage() {
		return message;
	}

	public GameEngine() {
		message = "Let's play a game of Tic Tac Toe";
		resetGame();
	}

	public void resetGame() {
		moves = new ArrayList<>();
		validMoves = new ArrayList<>();

		for (String row : rows) {
			for (String col : cols) {
				validMoves.add(row + col);
			}
		}
	}

	public String move(String letter) {
		//chose a random location from list of valid moves
		int index = (int) (Math.random() * validMoves.size());
		String selectedMove = validMoves.get(index).toUpperCase();
		message = "Computer plays " + selectedMove;

		moves.add(new Move(letter, selectedMove));

		validMoves.remove(index);

		return selectedMove;
	}

	public boolean isValidMove(String letter, String move) {
		if (validMoves.contains(move)) {
			message = "You play " + move;
			moves.add(new Move(letter, move));
			//Move was valid so remove from list of validMoves
			validMoves.remove(move);
			return true;
		}

		// Invalid move so retry
		System.out.println("Enter a valid move");
		return false;
	}

	public boolean isStillPlaying() {
		if (validMoves.size() > 0 || noWinners()) {
			return true;
		} else {
			// If no moves left or someone won Game Over
			message = "Game Over";
			return false;
		}
	}

	private boolean noWinners() {
		// TODO Enter game to check for winners
		return true;
	}

	public void displayBoard(ArrayList<Move> moves) {
		// Header Row
		String line = " |";
		for (String col : cols) {
			line += col + "|";
		}
		System.out.println(line);

		for (String row : rows) {
			line = row + "|";
			for (String col : cols) {
				String location = row + col;
				var result = moves.stream().filter(t -> t.location.equals(location)).findAny().orElse(null);
				if (result == null) {
					line += "_|";
				} else {
					line += result.letter + "|";
				}
			}
			System.out.println(line);
		}
	}
}
