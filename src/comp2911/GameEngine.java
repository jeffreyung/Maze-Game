package comp2911;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.MapInterface;
import comp2911.game.Player;
import comp2911.game.Position;
import comp2911.game.ScoreHandler;
import comp2911.game.Solver;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class GameEngine {
	
	/**
	 * The schedule executor.
	 */
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	/**
	 * Map generator.
	 */
	private MapInterface mapGenerator;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The score handler.
	 */
	private ScoreHandler scoreHandler;
	
	/**
	 * The solver.
	 */
	private Solver solver;
	
	/**
	 * The 2-Dimensional board.
	 */
	private ArrayList<ArrayList<Character>> board;
	
	/**
	 * The players list.
	 */
	private List<Player> players;
	
	/**
	 * To avoid multiple interactions.
	 */
	private boolean interact;

	/**
	 * The height of the board.
	 */
	private int height;
	
	/**
	 * The width of the board.
	 */
	private int width;
	
	/**
	 * The current level.
	 */
	private int level;
	
	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public GameEngine(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.mapGenerator = new comp2911.game.MapGenerator();
		this.scoreHandler = new ScoreHandler();
		this.level = 1;
		this.generateBoard(level); // generate board for level 1
		this.players = new ArrayList<Player>(Constants.MAX_PLAYERS);
		this.addPlayer("username"); // TODO handle username system
	}
	
	/**
	 * Generates a new board for a given level
	 * @param level of the game.
	 */
	public void generateBoard(int level) {
		this.board = this.mapGenerator.createBoard();
		this.width = this.mapGenerator.getBoardSize();
		this.height = this.mapGenerator.getBoardSize();
		this.scoreHandler.readScoreData();
		this.solver = new Solver(board, width, height);
	}
	
	/**
	 * Completes the game for a player.
	 * @param index of the player who has completed current level of the game.
	 */
	public void completeGame(int index) {
		Player player = this.players.get(index);
		if (player.isCompleteGame())
			return;
		this.scoreHandler.writeScoreData(player.getUsername(), player.getScore());
		player.setCompleteGame(true);
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Generating new board...");
		scheduler.schedule(new Runnable() {
		    @Override
		    public void run() {
				generateBoard(level++);
				solver.initNewGame(board, width, height);
				player.setPosition(mapGenerator.getInitialCharPos());
				swingUI.updateInterface();
				player.setCompleteGame(false);
		    }
		}, 1, TimeUnit.SECONDS);
	}
	
	/**
	 * Sends the user input to the map.
	 * @param index of the player to be moved.
	 * @param dir is the direction to be moved.
	 */
	public void sendUserDirection(int index, Direction dir) {
		Position playerPos = this.players.get(index).getPosition();
		this.interact = false;
		this.interactEmptyTile(index, dir);
		this.interactCrate(index, dir);
		this.swingUI.updateInterface();
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving (" + playerPos.getX() + ", " + playerPos.getY() + ")");
		if(solver.isGameComplete())
			this.completeGame(index);
		else if(!solver.isSolvable()) {
			Logger.getLogger(UserInput.class.getName()).info("Game over - no crates can be moved.");
			System.out.println();
		}
			
	}

	/**
	 * A player moves to the direction they have input.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	public void interactEmptyTile(int index, Direction dir) {
		Player player = this.players.get(index);
		Position playerPos = player.getPosition();
		char tileType;
		if(this.interact || player.isCompleteGame())
			return;
		switch(dir) {
		case UP:
			tileType = getTileType(playerPos.getX(), playerPos.getY() - 1);
			if(tileType == ' ' || tileType == 'x') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveUp();
				this.interact = true;
			}
			break;
		case DOWN:
			tileType = getTileType(playerPos.getX(), playerPos.getY() + 1);
			if(tileType == ' ' || tileType == 'x') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				board.get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveDown();
				this.interact = true;
			}
			break;
		case LEFT:
			tileType = getTileType(playerPos.getX() - 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				board.get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveLeft();
				this.interact = true;
			}
			break;
		case RIGHT:
			tileType = getTileType(playerPos.getX() + 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				board.get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}
	
	/**
	 * A player moves to the direction they have input. This is similar to moving
	 * to an empty tile, except this also pushes the crate.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	private void interactCrate(int index, Direction dir) {
		Player player = this.players.get(index);
		Position playerPos = player.getPosition();
		char tileType1;
		char tileType2;
		if(this.interact || player.isCompleteGame())
			return;
		switch(dir) {
		case UP:
			tileType1 = getTileType(playerPos.getX(), playerPos.getY() - 1);
			if(tileType1 != '.' && tileType1 != ':')
				return;
			tileType2 = getTileType(playerPos.getX(), playerPos.getY() - 2);
			if(tileType2 == ' ') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() - 2).set(playerPos.getX(), '.');
				playerPos.moveUp();
				this.interact = true;
			} else if(tileType2 == 'x') {
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX(),  ' ');
				board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() - 2).set(playerPos.getX(), ':');
				playerPos.moveUp();
				this.interact = true;
			}
			break;
		case DOWN:
			tileType1 = getTileType(playerPos.getX(), playerPos.getY() + 1);
			if(tileType1 != '.' && tileType1 != ':')
				return;
			tileType2 = getTileType(playerPos.getX(), playerPos.getY() + 2);
			if(tileType2 == ' ') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() + 2).set(playerPos.getX(), '.');
				playerPos.moveDown();
				this.interact = true;
			} else if(tileType2 == 'x') {
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX(),  ' ');
				board.get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() + 2).set(playerPos.getX(), ':');
				playerPos.moveDown();
				this.interact = true;
			}
			break;
		case LEFT:
			tileType1 = getTileType(playerPos.getX() - 1, playerPos.getY());
			if(tileType1 != '.' && tileType1 != ':')
				return;
			tileType2 = getTileType(playerPos.getX() - 2, playerPos.getY());
			if(tileType2 == ' ') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				board.get(playerPos.getY()).set(playerPos.getX() - 2, '.');
				playerPos.moveLeft();
				this.interact = true;
			} else if(tileType2 == 'x') {
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX(), ' ');
				board.get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				board.get(playerPos.getY()).set(playerPos.getX() - 2, ':');
				playerPos.moveLeft();
				this.interact = true;
			}
			break;
		case RIGHT:
			tileType1 = getTileType(playerPos.getX() + 1, playerPos.getY());
			if(tileType1 != '.' && tileType1 != ':')
				return;
			tileType2 = getTileType(playerPos.getX() + 2, playerPos.getY());
			if(tileType2 == ' ') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				board.get(playerPos.getY()).set(playerPos.getX() + 2, '.');
				playerPos.moveRight();
				this.interact = true;
			} else if(tileType2 == 'x') {
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX(), ' ');
				board.get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				board.get(playerPos.getY()).set(playerPos.getX() + 2, ':');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}

	/**
	 * Adds a new player to the game.
	 * @param username of the player
	 */
	public void addPlayer(String username) {
		Player player = new Player(this.players.size());
		this.players.add(player);
		player.setPosition(this.mapGenerator.getInitialCharPos());
	}
	
	/**
	 * @param x coordinate of the tile.
	 * @param y coordinate of the tile.
	 * @return the tile type.
	 */
	public char getTileType(int x, int y) {
		return board.get(y).get(x);
	}

	/**
	 * @return the board of the game.
	 */
	public ArrayList<ArrayList<Character>> getBoard() {
		return board;
	}

	/**
	 * @return the height of the board.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the width of the board.
	 */
	public int getWidth() {
		return width;
	}
	
}