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
	 * The player's username.
	 */
	private String username;
	
	/**
	 * The player's current score.
	 */
	private int score;
	
	/**
	 * If player is standing on the goal.
	 */
	private boolean standingOnGoal;
	
	/**
	 * If player's current game is completed.
	 */
	private boolean completeGame;
	
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
	 * @param index the index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Increments the player's score.
	 */
	public void incrementScore() {
		this.score++;
	}
	
	/**
	 * Decrements the player's score.
	 */
	public void decrementScore() {
		this.score--;
	}

	/**
	 * @return if the player is standing on the goal.
	 */
	public boolean isStandingOnGoal() {
		return standingOnGoal;
	}

	/**
	 * @param standingOnGoal is if the player is standing on the goal.
	 */
	public void setStandingOnGoal(boolean standingOnGoal) {
		this.standingOnGoal = standingOnGoal;
	}

	/**
	 * @return if the current game is completed.
	 */
	public boolean isCompleteGame() {
		return completeGame;
	}

	/**
	 * @param completeGame is if the current game is completed.
	 */
	public void setCompleteGame(boolean completeGame) {
		this.completeGame = completeGame;
	}
}
