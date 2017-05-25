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
	 * The player's incremented score after completing game.
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
	 * If player's current game is game over.
	 */
	private boolean gameOver;
	
	/**
	 * Constructs a new player.
	 * @param username of the player.
	 * @param index 
	 */
	public Player(String username, int index) {
		this.username = username;
		this.index = index;
		this.gameOver = false;
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

	/**
	 * @return the score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to add.
	 */
	public void addScore(int score) {
		this.score += score;
	}
	
	/**
	 * Clears the player score.
	 */
	public void clearScore() {
		this.score = 0;
	}

	/**
	 * @return the gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * @param gameOver the gameOver to set
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
