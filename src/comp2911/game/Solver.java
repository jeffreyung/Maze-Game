package comp2911.game;

import java.util.ArrayList;

/**
 * @author Jeffrey Ung
 */
public class Solver {

	/**
	 * The initial 2-Dimensional board.
	 */
	private ArrayList<ArrayList<Character>> board;

	/**
	 * The board height.
	 */
	private int height;
	
	/**
	 * The board width.
	 */
	private int width;
	
	/**
	 * The total goal(s) of the current board.
	 */
	private int goalTotal;
	
	/**
	 * Constructs the solver class.
	 * @param board is the first game board.
	 */
	public Solver(ArrayList<ArrayList<Character>> board, int width, int height) {
		this.initNewGame(board, width, height);
	}
	
	/**
	 * Initializes the solver for a new game.
	 * @param board
	 */
	public void initNewGame(ArrayList<ArrayList<Character>> board, int width, int height) {
		this.board = board;
		this.height = height;
		this.width = width;
		this.updateGoalTotal();
	}
	
	/**
	 * Updates the goal total;
	 */
	public void updateGoalTotal() {
		this.goalTotal = 0;
		for (int x = 0; x < this.width; x++)
			for (int y = 0; y < this.height; y++)
				if (board.get(y).get(x) == 'x')
					this.goalTotal++;
	}
	
	/**
	 * Gets the score by counting the amount of completed crates.
	 * @return
	 */
	public int getScore() {
		int count = 0;
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				if (board.get(y).get(x) == ':')
					count++;
			}
		}
		return count;
	}
	
	/**
	 * Checks if the game is completed.
	 * @return true if the game is completed.
	 */
	public boolean isGameComplete() {
		return this.goalTotal == getScore();
	}
	
}
