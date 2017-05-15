package comp2911;

import java.util.ArrayList;
import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.MapGenerator;
import comp2911.game.Position;
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
	 * Player's current position.
	 */
	private Position playerPos;
	
	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public GameEngine(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.mapGenerator = new comp2911.game.MapGenerator(Constants.BOARD_SIZE);
		this.board = this.mapGenerator.createBoard();
		this.playerPos = getPlayerPos();
	}
	
	/**
	 * Sends the user input to the map.
	 * @param input 
	 */
	public void sendUserDirection(Direction dir) {
		switch(dir) {
		case UP:
			if (getTileType(playerPos.getX(), playerPos.getY() - 1) != 'o' &&
					getTileType(playerPos.getX(), playerPos.getY() - 1) != ' ')
				return;
			board.get(playerPos.getY()).set(playerPos.getX(), 'o');
			board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
			playerPos.moveUp();
			break;
		case DOWN:
			if (getTileType(playerPos.getX(), playerPos.getY() + 1) != 'o' &&
					getTileType(playerPos.getX(), playerPos.getY() + 1) != ' ')
				return;
			board.get(playerPos.getY()).set(playerPos.getX(), 'o');
			board.get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
			playerPos.moveDown();
			break;
		case LEFT:
			if (getTileType(playerPos.getX() - 1, playerPos.getY()) != 'o' &&
					getTileType(playerPos.getX() - 1, playerPos.getY()) != ' ')
				return;
			board.get(playerPos.getY()).set(playerPos.getX(), 'o');
			board.get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
			playerPos.moveLeft();
			break;
		case RIGHT:
			if (getTileType(playerPos.getX() + 1, playerPos.getY()) != 'o' &&
					getTileType(playerPos.getX() + 1, playerPos.getY()) != ' ')
				return;
			board.get(playerPos.getY()).set(playerPos.getX(), 'o');
			board.get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
			playerPos.moveRight();
			break;
		}
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving (" + playerPos.getX() + ", " + playerPos.getY() + ")");
		this.swingUI.updateInterface();
	}
	
	public char getTileType(int x, int y) {
		return board.get(y).get(x);
	}
	
	/**
	 * @return the character's current position on the board.
	 */
	public Position getPlayerPos() {
		int x = 0, y = 0;
		for (int i = 0; i < Constants.BOARD_SIZE + 10; i++) {
			for (int j = 0; j < Constants.BOARD_SIZE + 10; j++) {
				if (board.get(j).get(i) == 'c') {
					x = i;
					y = j;
					break;
				}
			}
		}
		return new Position(x, y);
	}
	/**
	 * @return the board of the game.
	 */
	public ArrayList<ArrayList<Character>> getBoard() {
		return board;
	}
	
}
