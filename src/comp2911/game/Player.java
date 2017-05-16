package comp2911.game;

/**
 * @author Jeffrey Ung
 */
public class Player {

	/**
	 * The player position.
	 */
	private Position position;
	
	/**
	 * The player index (player 1 is index 0 and player 2 is index 1).
	 */
	private int index;
	
	/**
	 * The player username.
	 */
	private String username;
	
	/**
	 * Constructs a new player.
	 * @param index of the player.
	 */
	public Player(int index) {
		this.index = index;
	}

	/**
	 * @return the position of the player.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position of ther player to set.
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the index of the player.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
