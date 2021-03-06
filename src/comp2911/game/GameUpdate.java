package comp2911.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
public class GameUpdate {

	/**
	 * The initial 2-Dimensional board.
	 */
	private ArrayList<ArrayList<Character>> board;
	
	/**
	 * List of static objects excluding the goal.
	 */
	private List<Character> staticObj;
	
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
	 * @param game is the game to be update.
	 */
	public GameUpdate(Game game) {
		this.staticObj = new ArrayList<Character>();
		this.staticObj.add('|');
		this.staticObj.add('.');
		this.staticObj.add(':');
		this.initNewGame(game.getBoard(), game.getWidth(), game.getHeight());
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
	 * Checks if the board is solvable i.e., if a crate is stuck.
	 * @return true if it is solvable.
	 */
	public boolean isSolvable() {
		int count = 0;
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				if(board.get(y).get(x) == '.' || board.get(y).get(x) == ':') {
					if((staticObj.contains(board.get(y + 1).get(x))
							&& staticObj.contains(board.get(y).get(x + 1))) ||
							(staticObj.contains(board.get(y + 1).get(x))
							&& staticObj.contains(board.get(y).get(x - 1))) ||
							(staticObj.contains(board.get(y - 1).get(x))
							&& staticObj.contains(board.get(y).get(x + 1))) ||
							(staticObj.contains(board.get(y - 1).get(x))
							&& staticObj.contains(board.get(y).get(x - 1))))
						count++;
				}
			}
		}
		return count < this.goalTotal;
	}
	
	/**
	 * Updates the goal total;
	 */
	public void updateGoalTotal() {
		this.goalTotal = 0;
		for(int x = 0; x < this.width; x++)
			for(int y = 0; y < this.height; y++)
				if(board.get(y).get(x) == 'x')
					this.goalTotal++;
	}
	
	/**
	 * Gets the score by counting the amount of completed crates.
	 * @return
	 */
	public int getScore() {
		int count = 0;
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				if(board.get(y).get(x) == ':')
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
