import java.util.Scanner;

public class Board {
	private Scanner sc = new Scanner(System.in);

	char humanLetter = 'X';
	char computerLetter = 'O';

	public Board() {
		// TODO choose who goes first and what letter they play
	}

	public void play(boolean isHumanToPlay) {
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
