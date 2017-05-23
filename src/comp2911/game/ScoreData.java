package comp2911.game;

public class ScoreData implements Comparable<ScoreData> {

	private String username;
	private int score;
	
	/**
	 * The constructor for the score data.
	 * @param username of the scorer.
	 * @param score of the scorer.
	 */
	public ScoreData(String username, int score) {
		this.setUsername(username);
		this.setScore(score);
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

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public int compareTo(ScoreData o) {
		return o.score - this.score;
	}
	
}