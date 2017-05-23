package comp2911.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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
	 * The images of characters.
	 */
	private Map<Character, BufferedImage> attribute;
	
	/**
	 * The constructor of the StartScreen class
	 * @param swingUI 
	 */
	public GamePanel(SwingUI swingUI, String username) {
		this.swingUI = swingUI;
		this.gameEngine = new GameEngine(this.swingUI);
		this.userInput = new UserInput(this.gameEngine);
		this.tiles = new ArrayList<Tile>();
		this.attribute = new HashMap<Character, BufferedImage>();
		this.swingUI.updateInterface(0);
		this.gameEngine.addPlayer(username);
		this.init();
	}

	/**
	 * Initializes the start screen
	 */
	public void init() {
		this.addKeyListener(this.userInput);
		this.swingUI.addKeyListener(this.userInput);
		this.swingUI.setGameStart(true);
		this.swingUI.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(Color.BLACK);
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
		try {
			this.attribute.put('c', ImageIO.read(new File(Constants.IMAGES_DIR + "/player.gif")));
			this.attribute.put('.', ImageIO.read(new File(Constants.IMAGES_DIR + "/crate_1.gif")));
			this.attribute.put(':', ImageIO.read(new File(Constants.IMAGES_DIR + "/crate_2.gif")));
			this.attribute.put('x', ImageIO.read(new File(Constants.IMAGES_DIR + "/goal.gif")));
			this.attribute.put(' ', ImageIO.read(new File(Constants.IMAGES_DIR + "/tile.gif")));
		} catch (IOException e) {
		}
	}
	
	/**
	 * Creates a list of tiles depending on the board size.
	 * @param width is the size of the board width.
	 * @param height is the size of the board height.
	 * @param tileSize is the size of each tile.
	 */
	public void createTiles(int width, int height, int tileSize) {
		if (!tiles.isEmpty())
			return;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Rectangle rectangle = new Rectangle(x * tileSize, y * tileSize, tileSize - 2, tileSize - 2);
				char type = this.gameEngine.getBoard().get(y).get(x);
				if (this.attribute.containsKey(type))
					this.tiles.add(new Tile(getColor(type), this.attribute.get(type), rectangle));
				else
					this.tiles.add(new Tile(getColor(type), null, rectangle));
			}
		}
	}
	
	/**
	 * Gets the color of the given axis.
	 * @param type the type of the character.
	 * @return the color of the corresponding coordinates.
	 * | - wall
	 * x - goal
	 * o - empty space
	 * . - box
	 * : - box in goal
	 * c - character
	 * b - bomb
	 */
	public Color getColor(char type) {
		switch (type) {
		case '|':
			return Color.BLACK;
		case 't':
			return Color.BLACK;
		}
		return Color.DARK_GRAY;
	}
	
	/**
	 * Paints and creates the shape of the tiles.
	 * @param g is the graphics parameter.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		final int width = gameEngine.getWidth();
		final int height = gameEngine.getHeight();
		final int tileSize = Constants.TILE_SIZE;
		this.createTiles(width, height, tileSize);
		Graphics2D g2d = (Graphics2D) g.create();
		for (Tile tile : tiles) {
			g2d.setColor(tile.color);
			g2d.fill(tile.shape);
			g2d.draw(tile.shape);
			if (tile.image != null)
				g2d.drawImage(tile.image, tile.shape.getBounds().x, tile.shape.getBounds().y, this);
		}
		g2d.dispose();
		this.tiles.clear();
	}
	
	/**
	 * The Tile class contains data of a tile such as the color and the the shape.
	 */
	private class Tile {
		private Color color;
		private BufferedImage image;
		private Shape shape;
		
		public Tile(Color color, BufferedImage image, Shape shape) {
			this.color = color;
			this.image = image;
			this.shape = shape;
		}
	}

}
