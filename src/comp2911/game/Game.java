package comp2911.game;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import comp2911.Constants;
import comp2911.GameEngine;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class Game {
	
	/**
	 * The schedule executor.
	 */
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	/**
	 * The board for the current game.
	 */
	private ArrayList<ArrayList<Character>> board;
	/**
	 * The board width.
	 */
	private int width;
	
	/**
	 * The board height.
	 */
	private int height;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The gameUpdate.
	 */
	private GameUpdate gameUpdate;
	
	/**
	 * The score handler.
	 */
	private ScoreHandler scoreHandler;
	
	/**
	 * The player.
	 */
	private Player player;
	
	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * To avoid multiple interactions.
	 */
	private boolean interact;
	
	/**
	 * The current level.
	 */
	private int level;
	
	/**
	 * The game pause.
	 */
	private boolean pause;

	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public Game(SwingUI swingUI, GameEngine gameEngine) {
		this.swingUI = swingUI;
		this.pause = false;
		this.scoreHandler = new ScoreHandler();
		this.gameEngine = gameEngine;
		this.level = 1;
		gameEngine.generateBoard(level); // generate board for level 1
		this.width = gameEngine.getMapGenerator().getBoardSize();
		this.height = gameEngine.getMapGenerator().getBoardSize();
		this.board = copyBoard(gameEngine.getBoard(level));
		this.gameUpdate = new GameUpdate(this);
		this.scoreHandler.readScoreData();
	}
	
	/**
	 * @param board to be copied.
	 * @return a clone of the given board.
	 */
	public ArrayList<ArrayList<Character>> copyBoard(ArrayList<ArrayList<Character>> board) {
		ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>(board.size());
		for(int i = 0; i < board.size(); i++) {
			ArrayList<Character> line = board.get(i);
			copy.add(new ArrayList<Character>(line.size()));
			for(int j = 0; j < line.size(); j++) {
				copy.get(i).add(line.get(j));
			}
		}
		return copy;
	}
	
	/**
	 * Completes the game for a player.
	 * @param index of the player who has completed current level of the game.
	 */
	public void completeGame() {
		if (player.isCompleteGame())
			return;
		this.updateScore();
		player.setCompleteGame(true);
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				gameEngine.generateBoard(++level);
				board = copyBoard(gameEngine.getBoard(level));
				gameUpdate.initNewGame(getBoard(), width, height);
				player.setPosition(gameEngine.getMapGenerator().getInitialCharPos().clone());
				swingUI.updateInterface(gameUpdate.getScore() + player.getScore(),
						scoreHandler.getTopScore(player.getUsername()));
				player.setCompleteGame(false);
			}
		}, 1, TimeUnit.SECONDS);
	}
	
	/**
	 * Called when the game is to be restarted
	 * @param gamePanel is the user interface of the player.
	 */
	public void restartGame() {
		this.level = 1;
		this.gameEngine.clearBoardMap();
		this.gameEngine.generateBoard(level);
		board = copyBoard(gameEngine.getBoard(level));
		player.setGameOver(false);
		this.gameUpdate.initNewGame(getBoard(), getWidth(), getHeight());
		player.setPosition(this.gameEngine.getMapGenerator().getInitialCharPos().clone());
		player.clearScore();
		swingUI.updateInterface(this.gameUpdate.getScore() + player.getScore(),
				scoreHandler.getTopScore(player.getUsername()));
		player.setCompleteGame(false);
	}
	
	/**
	 * Updates the score of the player.
	 * @param index of the player.
	 */
	public void updateScore() {
		player.addScore(this.gameUpdate.getScore());
		this.scoreHandler.updateTopScore(player.getUsername(), player.getScore());
	}
	
	/**
	 * Sends the user input to the map.
	 * @param dir is the direction to be moved.
	 */
	public void sendUserDirection(Direction dir) {
		Position playerPos = player.getPosition();
		if (player.isCompleteGame() || this.pause || player.isGameOver())
			return;
		this.interact = false;
		this.interactEmptyTile(dir);
		this.interactCrate(dir);
		this.swingUI.updateInterface(this.gameUpdate.getScore() + player.getScore(),
				scoreHandler.getTopScore(player.getUsername()));
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving (" + playerPos.getX() + ","
					+ playerPos.getY() + ")");
		if(this.gameUpdate.isGameComplete())
			this.completeGame();
		else if(!this.gameUpdate.isSolvable()) {
			player.setCompleteGame(true);
			scheduler.schedule(new Runnable() {
				@Override
				public void run() {
					updateScore();
					player.setGameOver(true);
					swingUI.getPanel().repaint();
				}
			}, 1, TimeUnit.SECONDS);
		}
			
	}

	/**
	 * A player moves to the direction they have input.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	public void interactEmptyTile(Direction dir) {
		
		Position playerPos = player.getPosition();
		char tileType;
		if(this.interact || player.isCompleteGame())
			return;
		switch(dir) {
		case UP:
			tileType = getTileType(playerPos.getX(), playerPos.getY() - 1);
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveUp();
				this.interact = true;
			}
			break;
		case DOWN:
			tileType = getTileType(playerPos.getX(), playerPos.getY() + 1);
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveDown();
				this.interact = true;
			}
			break;
		case LEFT:
			tileType = getTileType(playerPos.getX() - 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveLeft();
				this.interact = true;
			}
			break;
		case RIGHT:
			tileType = getTileType(playerPos.getX() + 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}
	
	/**
	 * Checks if the game is game over.
	 * @return if it is game over.
	 */
	public boolean isGameOver() {
		return player.isGameOver();
	}
	
	/**
	 * A player moves to the direction they have input. This is similar to moving
	 * to an empty tile, except this also pushes the crate.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	private void interactCrate(Direction dir) {
		
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() - 2).set(playerPos.getX(), '.');
				playerPos.moveUp();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(),  ' ');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() - 2).set(playerPos.getX(), ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() + 2).set(playerPos.getX(), '.');
				playerPos.moveDown();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(),  ' ');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() + 2).set(playerPos.getX(), ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 2, '.');
				playerPos.moveLeft();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 2, ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 2, '.');
				playerPos.moveRight();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 2, ':');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}

	/**
	 * @return the width of the board.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height of the board.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the board of the current level.
	 */
	public ArrayList<ArrayList<Character>> getBoard() {
		return board;
	}

	/**
	 * Pause the game.
	 */
	public void pause() {
		this.pause = true;
		this.swingUI.repaint();
	}

	/**
	 * Adds a new player to the game.
	 * @param username of the player
	 */
	public void setPlayer(String username, int index) {
		Player player = new Player(username, index);
		this.player = player;
		player.setPosition(this.gameEngine.getMapGenerator().getInitialCharPos().clone());
		player.setUsername(username);
		this.scoreHandler.addNewScore(username);
	}
	
	/**
	 * @param x coordinate of the tile.
	 * @param y coordinate of the tile.
	 * @return the tile type.
	 */
	public char getTileType(int x, int y) {
		return this.getBoard().get(y).get(x);
	}
	
	/**
	 * @return the score handler.
	 */
	public ScoreHandler getScoreHandler() {
		return scoreHandler;
	}

	/**
	 * @return the pause
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * @return the level of the current game.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the player of the game.
	 */
	public Player getPlayer() {
		return player;
	}

}
=======
package comp2911;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.Player;
import comp2911.game.Position;
import comp2911.game.ScoreHandler;
import comp2911.game.Solver;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class Game {
	
	/**
	 * The schedule executor.
	 */
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	/**
	 * The board for the current game.
	 */
	private ArrayList<ArrayList<Character>> board;
	/**
	 * The board width.
	 */
	private int width;
	
	/**
	 * The board height.
	 */
	private int height;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The solver.
	 */
	private Solver solver;
	
	/**
	 * The score handler.
	 */
	private ScoreHandler scoreHandler;
	
	/**
	 * The player.
	 */
	private Player player;
	
	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * To avoid multiple interactions.
	 */
	private boolean interact;
	
	/**
	 * The current level.
	 */
	private int level;
	
	/**
	 * The game pause.
	 */
	private boolean pause;

	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public Game(SwingUI swingUI, GameEngine gameEngine) {
		this.swingUI = swingUI;
		this.pause = false;
		this.scoreHandler = new ScoreHandler();
		this.gameEngine = gameEngine;
		this.level = 1;
		gameEngine.generateBoard(level); // generate board for level 1
		this.width = gameEngine.getMapGenerator().getBoardSize();
		this.height = gameEngine.getMapGenerator().getBoardSize();
		this.board = copyBoard(gameEngine.getBoard(level));
		this.solver = new Solver(board, width, height);
		this.scoreHandler.readScoreData();
	}
	
	/**
	 * @param board to be copied.
	 * @return a clone of the given board.
	 */
	public ArrayList<ArrayList<Character>> copyBoard(ArrayList<ArrayList<Character>> board) {
		ArrayList<ArrayList<Character>> copy = new ArrayList<ArrayList<Character>>(board.size());
		for(int i = 0; i < board.size(); i++) {
			ArrayList<Character> line = board.get(i);
			copy.add(new ArrayList<Character>(line.size()));
			for(int j = 0; j < line.size(); j++) {
				copy.get(i).add(line.get(j));
			}
		}
		return copy;
	}
	
	/**
	 * Completes the game for a player.
	 * @param index of the player who has completed current level of the game.
	 */
	public void completeGame() {
		if (player.isCompleteGame())
			return;
		this.updateScore();
		player.setCompleteGame(true);
		scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				gameEngine.generateBoard(++level);
				board = copyBoard(gameEngine.getBoard(level));
				solver.initNewGame(getBoard(), width, height);
				player.setPosition(gameEngine.getMapGenerator().getInitialCharPos().clone());
				swingUI.updateInterface(solver.getScore() + player.getScore(),
						scoreHandler.getTopScore(player.getUsername()));
				player.setCompleteGame(false);
			}
		}, 1, TimeUnit.SECONDS);
	}
	
	/**
	 * Called when the game is to be restarted
	 * @param gamePanel is the user interface of the player.
	 */
	public void restartGame() {
		this.level = 1;
		this.gameEngine.clearBoardMap();
		this.gameEngine.generateBoard(level);
		board = copyBoard(gameEngine.getBoard(level));
		player.setGameOver(false);
		this.solver.initNewGame(getBoard(), getWidth(), getHeight());
		player.setPosition(this.gameEngine.getMapGenerator().getInitialCharPos().clone());
		player.clearScore();
		swingUI.updateInterface(this.solver.getScore() + player.getScore(),
				scoreHandler.getTopScore(player.getUsername()));
		player.setCompleteGame(false);
	}
	
	/**
	 * Updates the score of the player.
	 * @param index of the player.
	 */
	public void updateScore() {
		player.addScore(this.solver.getScore());
		this.scoreHandler.updateTopScore(player.getUsername(), player.getScore());
	}
	
	/**
	 * Sends the user input to the map.
	 * @param dir is the direction to be moved.
	 */
	public void sendUserDirection(Direction dir) {
		Position playerPos = player.getPosition();
		if (player.isCompleteGame() || this.pause || player.isGameOver())
			return;
		this.interact = false;
		this.interactEmptyTile(dir);
		this.interactCrate(dir);
		this.swingUI.updateInterface(this.solver.getScore() + player.getScore(),
				scoreHandler.getTopScore(player.getUsername()));
		if(Constants.DEBUG_MODE)
			Logger.getLogger(UserInput.class.getName()).info("Moving (" + playerPos.getX() + ","
					+ playerPos.getY() + ")");
		if(this.solver.isGameComplete())
			this.completeGame();
		else if(!this.solver.isSolvable()) {
			player.setCompleteGame(true);
			scheduler.schedule(new Runnable() {
				@Override
				public void run() {
					updateScore();
					player.setGameOver(true);
					swingUI.getPanel().repaint();
				}
			}, 1, TimeUnit.SECONDS);
		}
			
	}

	/**
	 * A player moves to the direction they have input.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	public void interactEmptyTile(Direction dir) {
		
		Position playerPos = player.getPosition();
		char tileType;
		if(this.interact || player.isCompleteGame())
			return;
		switch(dir) {
		case UP:
			tileType = getTileType(playerPos.getX(), playerPos.getY() - 1);
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveUp();
				this.interact = true;
			}
			break;
		case DOWN:
			tileType = getTileType(playerPos.getX(), playerPos.getY() + 1);
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveDown();
				this.interact = true;
			}
			break;
		case LEFT:
			tileType = getTileType(playerPos.getX() - 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveLeft();
				this.interact = true;
			}
			break;
		case RIGHT:
			tileType = getTileType(playerPos.getX() + 1, playerPos.getY());
			if(tileType == ' ' || tileType == 'x') {
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				player.setStandingOnGoal(tileType == 'x');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}
	
	/**
	 * Checks if the game is game over.
	 * @return if it is game over.
	 */
	public boolean isGameOver() {
		return player.isGameOver();
	}
	
	/**
	 * A player moves to the direction they have input. This is similar to moving
	 * to an empty tile, except this also pushes the crate.
	 * @param index of the player whose position is to be updated.
	 * @param dir is the direction to be moved direction to be moved.
	 */
	private void interactCrate(Direction dir) {
		
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() - 2).set(playerPos.getX(), '.');
				playerPos.moveUp();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(),  ' ');
				this.getBoard().get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() - 2).set(playerPos.getX(), ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() + 2).set(playerPos.getX(), '.');
				playerPos.moveDown();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(),  ' ');
				this.getBoard().get(playerPos.getY() + 1).set(playerPos.getX(), 'c');
				this.getBoard().get(playerPos.getY() + 2).set(playerPos.getX(), ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 2, '.');
				playerPos.moveLeft();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() - 2, ':');
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
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 2, '.');
				playerPos.moveRight();
				this.interact = true;
			} else if(tileType2 == 'x') {
				player.setStandingOnGoal(tileType1 == ':');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX(), ' ');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 1, 'c');
				this.getBoard().get(playerPos.getY()).set(playerPos.getX() + 2, ':');
				playerPos.moveRight();
				this.interact = true;
			}
			break;
		}
	}

	/**
	 * @return the width of the board.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return the height of the board.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the board of the current level.
	 */
	public ArrayList<ArrayList<Character>> getBoard() {
		return board;
	}

	/**
	 * Pause the game.
	 */
	public void pause() {
		this.pause = true;
		this.swingUI.repaint();
	}

	/**
	 * Adds a new player to the game.
	 * @param username of the player
	 */
	public void setPlayer(String username, int index) {
		Player player = new Player(username, index);
		this.player = player;
		player.setPosition(this.gameEngine.getMapGenerator().getInitialCharPos().clone());
		player.setUsername(username);
		this.scoreHandler.addNewScore(username);
	}
	
	/**
	 * @param x coordinate of the tile.
	 * @param y coordinate of the tile.
	 * @return the tile type.
	 */
	public char getTileType(int x, int y) {
		return this.getBoard().get(y).get(x);
	}
	
	/**
	 * @return the score handler.
	 */
	public ScoreHandler getScoreHandler() {
		return scoreHandler;
	}

	/**
	 * @return the pause
	 */
	public boolean isPause() {
		return pause;
	}

	/**
	 * @param pause the pause to set
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * @return the level of the current game.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the player of the game.
	 */
	public Player getPlayer() {
		return player;
	}

}
>>>>>>> 8b2d50709923566e050354a4f654887fe226a609:src/comp2911/Game.java
