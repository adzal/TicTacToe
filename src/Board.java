import java.util.Scanner;

public class Board {
	private Scanner sc = new Scanner(System.in);

	char humanPlayer = 'O';
	char aiPlayer = 'X';

	public Board() {
	}

	public void play(boolean isHumanToPlay) {
		// choose who goes first and what letter they play
		boolean isContinue = true;
		while (isContinue) {
			System.out.println("Do you want to play as 'X' or 'O'");
			String letter = sc.nextLine().toUpperCase();
			if (letter.startsWith("X")) {
				humanPlayer = 'X';
				aiPlayer = 'O';
			} else if (letter.startsWith("O")) {
				humanPlayer = 'O';
				aiPlayer = 'X';
			} else {
				System.out.println("Invalie input, try again");
				continue;
			}
			isContinue = false;
		}

		GameEngine gameEngine = new GameEngine(humanPlayer, aiPlayer);
		int aiCount = 0;
		int humanCount = 0;

		while (true) {
			gameEngine.resetGame();
			boolean nextPlayer = isHumanToPlay;
			while (gameEngine.isStillPlaying()) {
				System.out.println(gameEngine.getMessage());
				gameEngine.displayBoard();

				if (nextPlayer) {
					String move;
					do {
						System.out.println("Your turn, make move:");
						move = sc.nextLine().toUpperCase();
					} while (!gameEngine.moveHuman(move));
				} else {
					System.out.println("AI's turn");
					gameEngine.moveAI();
				}

				nextPlayer = !nextPlayer;
			}

			gameEngine.displayBoard();

			char whoWon = gameEngine.whoWon();
			if (whoWon == aiPlayer) {
				aiCount++;
				System.out.println("You lost :(  Ai:" + aiCount + " you:" + humanCount);
			} else if (whoWon == humanPlayer) {
				humanCount++;
				System.out.println("You won  :)  Ai:" + aiCount + " you:" + humanCount);
			} else {
				System.out.println("Tied game    Ai:" + aiCount + " you:" + humanCount);
			}

			System.out.println("do you want to play again? (Y/N)");
			// Input choice
			String line = sc.nextLine().toUpperCase();
			if (line.charAt(0) == 'N') {
				break;
			}
			isHumanToPlay = !isHumanToPlay;

		}
	}
}
