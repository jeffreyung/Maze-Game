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
import comp2911.game.Game;
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
	 * The username of the user.
	 */
	private String username;
	
	/**
	 * The user input.
	 */
	private KeyListener userInput;

	/**
	 * The game.
	 */
	private Game game;
	
	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * The pause panel.
	 */
	private PausePanel pausePanel;

	/**
	 * The game over panel.
	 */
	private GameOverPanel gameOverPanel;
	
	/**
	 * The list of tiles.
	 */
	private List<Tile> tiles;
	
	/**
	 * The images of characters.
	 */
	private Map<Character, BufferedImage> attribute;
	
	/**
	 * The index of  game panel.
	 */
	private int index;

	/**
	 * The constructor of the StartScreen class
	 * @param swingUI 
	 */
	public GamePanel(int index, SwingUI swingUI, GameEngine gameEngine, String username) {
		this.index = index;
		this.swingUI = swingUI;
		this.gameEngine = gameEngine;
		this.username = username;
		this.game = new Game(swingUI, gameEngine);
		this.pausePanel = new PausePanel(this.swingUI, this);
		this.userInput = new UserInput(this);
		this.gameOverPanel = new GameOverPanel(this.swingUI, this);
		this.tiles = new ArrayList<Tile>();
		this.attribute = new HashMap<Character, BufferedImage>();
		this.swingUI.updateInterface(0, game.getScoreHandler().getTopScore(username));
		this.game.setPlayer(username, index);
		this.init();
	}

	/**
	 * Initializes the start screen
	 */
	public void init() {
		this.addKeyListener(this.userInput);
		this.swingUI.addKeyListener(this.userInput);
		this.swingUI.setGameStart(true);
		if(this.swingUI.getGameMode() == 0)
			this.swingUI.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
		else
			this.swingUI.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH * 2, SwingUI.DEFAULT_HEIGHT));
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(Color.DARK_GRAY);
		this.swingUI.requestFocusInWindow();
		this.setVisible(true);
		try {
			this.attribute.put('|', ImageIO.read(new File(Constants.IMAGES_DIR + "/tile_1.png")));
			this.attribute.put('-', ImageIO.read(new File(Constants.IMAGES_DIR + "/tile_2.png")));
			this.attribute.put('0', ImageIO.read(new File(Constants.IMAGES_DIR + "/c_up.png")));
			this.attribute.put('1', ImageIO.read(new File(Constants.IMAGES_DIR + "/c_down.png")));
			this.attribute.put('2', ImageIO.read(new File(Constants.IMAGES_DIR + "/c_left.png")));
			this.attribute.put('3', ImageIO.read(new File(Constants.IMAGES_DIR + "/c_right.png")));
			this.attribute.put('.', ImageIO.read(new File(Constants.IMAGES_DIR + "/crate_1.gif")));
			this.attribute.put(':', ImageIO.read(new File(Constants.IMAGES_DIR + "/crate_2.gif")));
			this.attribute.put('x', ImageIO.read(new File(Constants.IMAGES_DIR + "/goal.gif")));
		} catch(IOException e) {
		}
	}
	
	/**
	 * Creates a list of tiles depending on the board size.
	 * @param width is the size of the board width.
	 * @param height is the size of the board height.
	 * @param tileSize is the size of each tile.
	 */
	public void createTiles(int width, int height, int tileSize) {
		if(!tiles.isEmpty())
			return;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Rectangle rectangle = new Rectangle(x * tileSize, y * tileSize, tileSize - 2, tileSize - 2);
				char type = this.game.getBoard().get(y).get(x);
				if(this.attribute.containsKey(type)) {
					if (type == '|' && x % 2 == 0)
						type = '-';
					this.tiles.add(new Tile(Color.DARK_GRAY, this.attribute.get(type), rectangle));
				} else
					this.tiles.add(new Tile(Color.DARK_GRAY, null, rectangle));
			}
		}
	}
	
	/**
	 * Paints and creates the shape of the tiles.
	 * @param g is the graphics parameter.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(game.isPause()) {
			if(!this.swingUI.getPanel().equals(this.pausePanel))
				this.swingUI.switchPanel(this.pausePanel);
			return;
		} else if(game.isGameOver()) {
			this.gameOverPanel.init();
			this.swingUI.switchPanel(this.gameOverPanel);
			return;
		}
		super.paintComponent(g);
		final int width = game.getWidth();
		final int height = game.getHeight();
		final int tileSize = Constants.TILE_SIZE;
		this.createTiles(width, height, tileSize);
		Graphics2D g2d =(Graphics2D) g.create();
		for(Tile tile : tiles) {
			g2d.setColor(tile.color);
			g2d.fill(tile.shape);
			g2d.draw(tile.shape);
			if(tile.image != null)
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
	
	/**
	 * The game.
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the game engine.
	 */
	public GameEngine getGameEngine() {
		return gameEngine;
	}

	/**
	 * @param gameEngine the game engine to set.
	 */
	public void setGameEngine(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}

}
