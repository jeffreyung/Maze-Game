package comp2911;

import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.MapGenerator;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class GameEngine {

	/**
	 * The board size.
	 */
	public static final int BOARD_SIZE = 10;
	
	/**
	 * Map generator.
	 */
	private MapGenerator mapGenerator;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * Constructs a new GameEngine and initializes the variables
	 */
	public GameEngine() {
		this.swingUI = new SwingUI(this);
		this.mapGenerator = new comp2911.game.MapGenerator(GameEngine.BOARD_SIZE);
	}
	
	/**
	 * Sends the user input to the map
	 * @param input 
	 */
	public void sendUserDirection(Direction dir) {
		/**
		 * dir == Direction.UP
		 * dir == Direction.DOWN
		 * dir == Direction.LEFT
		 * dir == DIRECTION.RIGHT
		 */
		if (Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving...");
	}
	
}
