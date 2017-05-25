package comp2911;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import comp2911.game.MapGenerator;
import comp2911.game.MapInterface;

/**
 * @author Jeffrey Ung
 */
public class GameEngine {

	/**
	 * Map generator.
	 */
	private MapInterface mapGenerator;

	/**
	 * The 2-Dimensional board map corresponding to their levels.
	 */
	private Map<Integer, ArrayList<ArrayList<Character>>> boardMap;
	
	/**
	 * The game engine constructor.
	 */
	public GameEngine() {
		this.mapGenerator = new MapGenerator();
		this.boardMap = new HashMap<Integer, ArrayList<ArrayList<Character>>>();
	}
	
	/**
	 * Generates a new board for a given level
	 * @param level of the game.
	 */
	public void generateBoard(int level) {
		if (this.boardMap.containsKey(level))
			return;
		ArrayList<ArrayList<Character>> board = this.mapGenerator.createBoard();
		this.boardMap.put(level, board);
	}
	
	/**
	 * @return the map generator.
	 */
	public MapInterface getMapGenerator() {
		return mapGenerator;
	}

	
	/**
	 * @return the game board.
	 */
	public ArrayList<ArrayList<Character>> getBoard(int level) {
		return boardMap.get(level);
	}

}
