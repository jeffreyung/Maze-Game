package comp2911;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import comp2911.game.Direction;
import comp2911.game.MapGenerator;
import comp2911.game.Player;
import comp2911.game.Position;
import comp2911.game.Score;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

public class GameEngine {
	
	/**
	 * Map generator.
	 */
	private MapGenerator mapGenerator;
	
	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The score handler.
	 */
	private Score score;
	
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
	 * The board size.
	 */
	private int boardSize;
	
	/**
	 * Constructs a new GameEngine and initializes the variables.
	 */
	public GameEngine(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.mapGenerator = new comp2911.game.MapGenerator();
		this.generateBoard(1); // generate board for level 1
		this.players = new ArrayList<Player>(Constants.MAX_PLAYERS);
		this.addPlayer("username"); // TODO handle username system
	}
	
	/**
	 * Generates a new board for a given level
	 * @param level of the game.
	 */
	public void generateBoard(int level) {
		this.score = new Score(level);
		this.board = this.mapGenerator.createBoard();
		this.boardSize = this.mapGenerator.getBoardSize();
		this.score.readScoreData();
	}
	
	/**
	 * Completes the game for a player.
	 * @param index of the player who has completed current level of the game.
	 */
	public void completeGame(int index) {
		Player player = this.players.get(index);
		//TODO
		this.score.writeScoreData(player.getUsername(), player.getScore());
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
		if(this.interact)
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
		if(this.interact)
			return;
		switch(dir) {
		case UP:
			tileType1 = getTileType(playerPos.getX(), playerPos.getY() - 1);
			if(tileType1 != '.' && tileType1 != ':')
				return;
			tileType2 = getTileType(playerPos.getX(), playerPos.getY() - 2);
			if (tileType2 == ' ') {
				board.get(playerPos.getY()).set(playerPos.getX(), player.isStandingOnGoal() ? 'x' : ' ');
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() - 2).set(playerPos.getX(), '.');
				playerPos.moveUp();
				this.interact = true;
			} else if (tileType2 == 'x') {
				this.players.get(index).setStandingOnGoal(tileType1 == ':');
				board.get(playerPos.getY()).set(playerPos.getX(),  ' ');
				board.get(playerPos.getY() - 1).set(playerPos.getX(), 'c');
				board.get(playerPos.getY() - 2).set(playerPos.getX(), ':');
				playerPos.moveUp();
				players.get(index).incrementScore();
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
				players.get(index).incrementScore();
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
				players.get(index).incrementScore();
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
				players.get(index).incrementScore();
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
	 * Provides the rectangular board size
	 * @return the size of the board
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
}
