
public class Main {

	public Main() {
	}

	public static void main(String[] args) {
		Board board = new Board();
		board.play(Math.random() < 0.5);
	}
}
