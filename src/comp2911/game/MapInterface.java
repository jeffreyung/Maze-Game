package comp2911.game;

import java.util.ArrayList;

public interface MapInterface {
	
	/**
	 * Generates a new map
	 * @return a functioning map
	 */
	public ArrayList<ArrayList<Character>> createBoard();
	
	/**
	 * Gets the boardSize
	 * @return board size
	 */
	public int getBoardSize();
	
	/**
	 * Gets character position
	 * @return character position
	 */
	public Position getCharPos();
}
