import java.util.Scanner;

public class Board {
	private Scanner sc = new Scanner(System.in);

	char humanLetter = 'X';
	char computerLetter = 'O';

	public Board() {
	}

	public void play(boolean isHumanToPlay) {
		// TODO choose who goes first and what letter they play
		boolean isContinue = true;
		while (isContinue) {
			System.out.println("Do you want to play as 'X' or 'O'");
			String letter = sc.nextLine().toUpperCase();
			if (letter.startsWith("X")) {
				humanLetter = 'X';
				computerLetter = 'O';
			} else if (letter.startsWith("O")) {
				humanLetter = 'O';
				computerLetter = 'X';
			} else {
				System.out.println("Invalie input, try again");
				continue;
			}
			isContinue = false;
		}

		GameEngine gameEngine = new GameEngine();

		while (gameEngine.isStillPlaying()) {
			System.out.println(gameEngine.getMessage());
			gameEngine.displayBoard();

			if (isHumanToPlay) {
				String move;
				do {
					System.out.println("Enter move:");
					move = sc.nextLine().toUpperCase();
				} while (!gameEngine.moveHuman(humanLetter, move));
			} else {
				gameEngine.moveAI(computerLetter);
			}

			isHumanToPlay = !isHumanToPlay;
		}

		System.out.println("Finished!!");
		gameEngine.displayBoard();
	}
}
