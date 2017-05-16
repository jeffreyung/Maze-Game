package comp2911.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import comp2911.Constants;
import comp2911.GameEngine;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/**
	 * The swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The user input.
	 */
	private KeyListener userInput;

	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * The list of tiles.
	 */
	private List<Tile> tiles;
	
	/**
	 * The constructor of the StartScreen class
	 * @param swingUI 
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.gameEngine = new GameEngine(this.swingUI);
		this.userInput = new UserInput(this.gameEngine);
		this.tiles = new ArrayList<Tile>();
		this.init();
	}

	/**
	 * Initializes the start screen
	 */
	public void init() {
		this.addKeyListener(this.userInput);
		this.swingUI.addKeyListener(this.userInput);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
	}
	
	/**
	 * Creates a list of tiles depending on the board size.
	 * @param boardSize is the size of the board
	 * @param tileSize is the size of each tile.
	 */
	public void createTiles(int boardSize, int tileSize) {
		if (!tiles.isEmpty())
			return;
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				Rectangle rectangle = new Rectangle(x * tileSize, y * tileSize, tileSize - 5, tileSize - 5);
				this.tiles.add(new Tile(getColor(x, y), rectangle));
			}
		}
	}
	
	/**
	 * Gets the color of the given axis.
	 * @param x is the x-coordinate.
	 * @param y is the y-coordinate.
	 * @return the color of the corresponding coordinates.
	 * | - wall
	 * x - goal
	 * o - empty space
	 * . - box start position
	 * c - character start position
	 * b - bomb start position
	 */
	public Color getColor(int x, int y) {
		switch (this.gameEngine.getBoard().get(y).get(x)) {
		case '|':
			return Color.BLACK;
		case 'x':
			return Color.CYAN;
		case 'o':
			return Color.GRAY;
		case 't':
			return Color.BLACK;
		case '.':
			return Color.ORANGE;
		case 'c':
			return Color.GREEN;
		case 'b':
			return Color.YELLOW;
		case ' ':
			return Color.GRAY;
		}
		return null;
	}
	
	/**
	 * Paints and creates the shape of the tiles.
	 * @param g is the graphics parameter.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final int boardSize = Constants.BOARD_SIZE + 10;
		final int tileSize = Constants.TILE_SIZE;
		this.createTiles(boardSize, tileSize);
		Graphics2D g2d = (Graphics2D) g.create();
		for (Tile tile : tiles) {
			g2d.setColor(tile.getColor());
			g2d.fill(tile.getShape());
			g2d.draw(tile.getShape());
		}
		g2d.dispose();
		this.tiles.clear();
	}
	
	/**
	 * The Tile class contains data of a tile such as the color and the the shape.
	 */
	private class Tile {
		private Color color;
		private Shape shape;
		
		public Tile(Color color, Shape shape) {
			this.color = color;
			this.shape = shape;
		}
	
		/**
		 * @return the color
		 */
		public Color getColor() {
			return color;
		}

		/**
		 * @return the shape
		 */
		public Shape getShape() {
			return shape;
		}

	}

}
