package comp2911.game;

public class Position {
	
	/**
	 * The x-coordinate.
	 */
	private int x;
	
	/**
	 * The y-coordinate.
	 */
	private int y;
	
	/**
	 * Constructs the Position class.
	 * @param x is the x-coordinate.
	 * @param y is the y-coordinate.
	 */
	public Position (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * The function is usually called for moving a player.
	 * This decrements the y-coordinate.
	 */
	public void moveUp() {
		this.y--;
	}

	/**
	 * The function is usually called for moving a player.
	 * This increments the y-coordinate.
	 */
	public void moveDown() {
		this.y++;
	}
	
	/**
	 * The function is usually called for moving a player.
	 * This decrements the x-coordinate.
	 */
	public void moveLeft() {
		this.x--;
	}
	
	/**
	 * The function is usually called for moving a player.
	 * This increments the x-coordinate.
	 */
	public void moveRight() {
		this.x++;
	}
	
	/**
	 * @return the x-coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * @return the y-coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return the string format of the position.
	 */
	@Override
	public String toString() {
		return x + "," + y + " ";
	}
}
