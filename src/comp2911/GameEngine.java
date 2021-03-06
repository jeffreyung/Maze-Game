package comp2911;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comp2911.game.Game;
import comp2911.game.MapGenerator;
import comp2911.game.MapGeneratorHard;
import comp2911.game.MapGeneratorMVP;
import comp2911.game.MapInterface;
import comp2911.game.Player;

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
	 * The games list.
	 */
	private List<Game> games;
	
	/**
	 * The game engine constructor.
	 */
	public GameEngine(List<Game> games) {
		this.boardMap = new HashMap<Integer, ArrayList<ArrayList<Character>>>();
		this.games = games;
	}
	
	/**
	 * @return the player who completed the most board.
	 */
	public Player getWinner() {
		Player p1 = games.get(0).getPlayer();
		Player p2 = games.get(1).getPlayer();
		if (p1.getScore() > p2.getScore())
			return p1;
		else if (p1.getScore() < p2.getScore())
			return p2;
		return null;
	}
	
	/**
	 * Generates a new board for a given level
	 * @param level of the game.
	 */
	public void generateBoard(int level) {
		if (this.boardMap.containsKey(level))
			return;
		if (level == 1)
			this.mapGenerator = new MapGeneratorMVP();
		if (level == 3)
			this.mapGenerator = new MapGenerator();
		if (level == 7)
			this.mapGenerator = new MapGeneratorHard();
		ArrayList<ArrayList<Character>> board = this.mapGenerator.createBoard();
		this.boardMap.put(level, board);
	}
	
	/**
	 * Clears the board map.
	 */
	public void clearBoardMap() {
		this.boardMap.clear();
	}
	
	/**
	 * @return the games list.
	 */
	public List<Game> getGames() {
		return games;
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
