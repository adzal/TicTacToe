/**
 * Class to help visualise the game tree.
 * @author adzal
 *
 */
public class GameNode implements Comparable<GameNode> {
	private String coords;
	private Integer move;
	private Integer depth;
	private char[] board;
	private Integer score;
	private boolean isMax;

	public GameNode(String coords, Integer move, Integer depth, char[] board, Integer score, boolean isMax) {
		this.coords = coords;
		this.move = move;
		this.depth = depth;
		this.board = board;
		this.score = score;
		this.isMax = isMax;
	}

	@Override
	public String toString() {
		String s = "\n";
		String[] lines = GameEngine.getBoard(board);
		for (String line : lines) {
			s += " ".repeat(depth) + line + "\n";
		}
		s += isMax ? "Max" : "Min";
		s += " ".repeat(depth) + "[depth=" + depth + ", coords=" + coords + ", move=" + move + ", " + score + "]";
		return s;
	}

	/**
	 * Here we are sorting by the Depth 1st then the Move
	 */
	@Override
	public int compareTo(GameNode o) {
		int depthComparator = depth.compareTo(o.depth);
		int moveComparator = move.compareTo(o.move);

		if (depthComparator == 0) {
			if (moveComparator > 0)
				return 1;
			else if (moveComparator < 0)
				return -1;
			else
				return 0;
		}
		return depthComparator;
	}
}