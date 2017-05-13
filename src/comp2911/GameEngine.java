package comp2911;

import java.util.ArrayList;
import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.MapGenerator;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class GameEngine {
	
	/**
	 * Map generator.
	 */
	private MapGenerator mapGenerator;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The 2-Dimensional board.
	 */
	private ArrayList<ArrayList<Character>> board;
	
	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public GameEngine(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.mapGenerator = new comp2911.game.MapGenerator(Constants.BOARD_SIZE);
		this.board = this.mapGenerator.createBoard();
	}
	
	/**
	 * Sends the user input to the map.
	 * @param input 
	 */
	public void sendUserDirection(Direction dir) {
		/**
		 * if (dir == Direction.UP)
		 * 		go up
		 * if (dir == Direction.DOWN
		 * 		go down
		 * if (dir == Direction.LEFT
		 * 		go left
		 * if (dir == DIRECTION.RIGHT
		 * 		go right
		 * this.board = ???;
		 */
		/*
		 * TODO Insert code for updating the 2D matrix here
		 */
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving...");
		this.swingUI.updateInterface();
	}
	
	/**
	 * @return the board of the game.
	 */
	public ArrayList<ArrayList<Character>> getBoard() {
		return board;
	}
	
}
