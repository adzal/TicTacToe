import java.util.Scanner;

public class Board {
	private Scanner sc = new Scanner(System.in);

	String humanLetter = "X";
	String computerLetter = "O";

	public Board() {
		//TODO choose who goes first and what letter they play
	}

	public void play(boolean isHumanToPlay) {
		GameEngine gameEngine = new GameEngine();

		do {
			System.out.println(gameEngine.getMessage());
			gameEngine.displayBoard(gameEngine.getMoves());

			String move = "";
			if (isHumanToPlay) {
				do {
					System.out.println("Enter move:");
					move = sc.nextLine().toUpperCase();
				} while (!gameEngine.isValidMove(humanLetter, move));
			} else {
				move = gameEngine.move(computerLetter);
			}

			isHumanToPlay = !isHumanToPlay;

		} while (gameEngine.isStillPlaying());

		System.out.println("Finished!!");
		gameEngine.displayBoard(gameEngine.getMoves());
	}
}
