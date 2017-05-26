package comp2911.game;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
public class Solver {

	/**
	 * The initial 2-Dimensional board.
	 */
	private ArrayList<ArrayList<Character>> board;
	
	/**
	 * The priority queue.
	 */
	private Queue<State> pQueue;
	
	/**
	 * The visited list.
	 */
	private List<State> visited; 
	
	/**
	 * List of static objects excluding the goal.
	 */
	private List<Character> staticObj;
	
	/**
	 * The board height.
	 */
	private int height;
	
	/**
	 * The board width.
	 */
	private int width;
	
	/**
	 * The total goal(s) of the current board.
	 */
	private int goalTotal;

	/**
	 * Constructs the solver class.
	 * @param board is the first game board.
	 */
	public Solver(ArrayList<ArrayList<Character>> board, int width, int height) {
		this.pQueue = new PriorityQueue<State>();
		this.visited = new ArrayList<State>();
		this.staticObj = new ArrayList<Character>();
		this.staticObj.add('|');
		this.staticObj.add('.');
		this.staticObj.add(':');
		this.initNewGame(board, width, height);
	}
	
	/**
	 * Initializes the solver for a new game.
	 * @param board
	 */
	public void initNewGame(ArrayList<ArrayList<Character>> board, int width, int height) {
		this.board = board;
		this.height = height;
		this.width = width;
		this.updateGoalTotal();
	}
	
	/**
	 * Checks if the board is solvable i.e., if a crate is stuck.
	 * @return true if it is solvable.
	 */
	public boolean isSolvable() {
		int count = 0;
		boolean moveable = false;
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				if(board.get(y).get(x) == '.') {
					if((staticObj.contains(board.get(y + 1).get(x))
							&& staticObj.contains(board.get(y).get(x + 1))) ||
							(staticObj.contains(board.get(y + 1).get(x))
							&& staticObj.contains(board.get(y).get(x - 1))) ||
							(staticObj.contains(board.get(y - 1).get(x))
							&& staticObj.contains(board.get(y).get(x + 1))) ||
							(staticObj.contains(board.get(y - 1).get(x))
							&& staticObj.contains(board.get(y).get(x - 1))))
						count++;
				} else if(board.get(y).get(x) == ':') {
					if(board.get(y + 1).get(x) == ' ' ||
							board.get(y - 1).get(x) == ' '||
							board.get(y).get(x + 1) == ' '||
							board.get(y).get(x - 1) == ' ')
						moveable = true;
				}
			}
		}
		return count < this.goalTotal || moveable;
	}
	
	
	/**
	 * Updates the goal total;
	 */
	public void updateGoalTotal() {
		this.goalTotal = 0;
		for(int x = 0; x < this.width; x++)
			for(int y = 0; y < this.height; y++)
				if(board.get(y).get(x) == 'x')
					this.goalTotal++;
	}
	
	/**
	 * Gets the score by counting the amount of completed crates.
	 * @return
	 */
	public int getScore() {
		int count = 0;
		for(int x = 0; x < this.width; x++) {
			for(int y = 0; y < this.height; y++) {
				if(board.get(y).get(x) == ':')
					count++;
			}
		}
		return count;
	}
	
	/**
	 * @param xChar is the x-coordinate of the character.
	 * @param yChar is the y-coordinate of the character.
	 * @param xOne is the x-coordinate of the first goal.
	 * @param yOne is the y-coordinate of the second goal.
	 * @param xTwo
	 * @param yTwo
	 * @return is the search is valid.
	 */
	public boolean search(int xChar, int yChar, int xOne, int yOne, int xTwo, int yTwo) {
		State begin = null;
		if(goalTotal == 1)
			begin = new State(0, xChar, yChar, xOne, yOne);
		else // must be two
			begin = new State(0, xChar, yChar, xOne, yOne, xTwo, yTwo);
		pQueue.add(begin);
		while (!pQueue.isEmpty()) { 
			State tempState = pQueue.poll();
			
			if(finished(tempState)) return true;
			
			if(visited.contains(tempState)) continue; 
			visited.add(tempState);

			int currCharX = tempState.getXChar();
			int currCharY = tempState.getYChar();
			int currXOne = tempState.getXOne();
			int currYOne = tempState.getYOne();
			int currXTwo = tempState.getXOne();
			int currYTwo = tempState.getYOne();
			int currNumMoves = tempState.getNumMoves();
			if(goalTotal == 1) {
				if(isLegal(Direction.UP, tempState)) {
					if(pushBox(Direction.UP, tempState)) {
						State newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne+1);
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.DOWN, tempState)) {
					if(pushBox(Direction.DOWN, tempState)) {
						State newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne-1);
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.LEFT, tempState)) {
					if(pushBox(Direction.LEFT, tempState)) {
						State newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne-1, currYOne);
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.RIGHT, tempState)) {
					if(pushBox(Direction.RIGHT, tempState)) {
						State newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne+1, currYOne);
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
			} else {
				if(isLegal(Direction.UP, tempState)) {
					if(pushBox(Direction.UP, tempState)) {
						State newState = null;
						if(whichBox(Direction.UP, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne+1, currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo+1);
						}
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.DOWN, tempState)) {
					if(pushBox(Direction.DOWN, tempState)) {
						State newState = null;
						if(whichBox(Direction.DOWN, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne-1, currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo-1);
						}
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.LEFT, tempState)) {
					if(pushBox(Direction.LEFT, tempState)) {
						State newState = null;
						if(whichBox(Direction.LEFT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne-1, currYOne , currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne , currXTwo-1, currYTwo);
						}
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				if(isLegal(Direction.RIGHT, tempState)) {
					if(pushBox(Direction.RIGHT, tempState)) {
						State newState = null;
						if(whichBox(Direction.RIGHT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne+1, currYOne , currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne , currXTwo+1, currYTwo);
						}
						pQueue.add(newState);
					} else {
						State newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param dir of the position.
	 * @param state of the node.
	 * @return if it is legal to move.
	 */
	public boolean isLegal(Direction dir, State state) {
		if(dir == Direction.UP) {
			if(board.get(state.getXChar()).get(state.getYChar()+1) == '|') return false;
			if(state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) {
				if(board.get(state.getXChar()).get(state.getYChar()+2) == '|') return false;
				// need to add case for other box blocking push if there are two boxes
			}
		} else if(dir == Direction.DOWN) {
			if(board.get(state.getXChar()).get(state.getYChar()-1) == '|') return false;
			if(state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) {
				if(board.get(state.getXChar()).get(state.getYChar()-2) == '|') return false;
				// need to add case for other box blocking push if there are two boxes
			}
		} else if(dir == Direction.LEFT) {
			if(board.get(state.getXChar()-1).get(state.getYChar()) == '|') return false;
			if(state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) {
				if(board.get(state.getXChar()-2).get(state.getYChar()) == '|') return false;
				// need to add case for other box blocking push if there are two boxes
			}
		} else if(dir == Direction.RIGHT) {
			if(board.get(state.getXChar()+1).get(state.getYChar()) == '|') return false;
			if(state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) {
				if(board.get(state.getXChar()+2).get(state.getYChar()) == '|') return false;
				// need to add case for other box blocking push if there are two boxes
			}
		}
		return true;
	}
	
	/**
	 * @param dir of the box being pushed.
	 * @param state of the node.
	 * @return if the box is pushed.
	 */
	public boolean pushBox(Direction dir, State state) {
		if(dir == Direction.UP) {
			if(state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) return true;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return true;
		} else if(dir == Direction.DOWN) {
			if(state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) return true;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return true;
		} else if(dir == Direction.LEFT) {
			if(state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) return true;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return true;
		} else if(dir == Direction.RIGHT) {
			if(state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) return true;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return true;
		}
		return false;
	}
	
	/**
	 * @param dir of the position.
	 * @param state of the node.
	 * @return which box to move.
	 */
	public int whichBox(Direction dir, State state) {
		if(dir == Direction.UP) {
			if(state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) return 1;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return 2;
		} else if(dir == Direction.DOWN) {
			if(state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) return 1;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return 2;
		} else if(dir == Direction.LEFT) {
			if(state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) return 1;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return 2;
		} else if(dir == Direction.RIGHT) {
			if(state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) return 1;
			if(state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return 2;
		}
		return 0;
	}
	
	/**
	 * @param state of the node.
	 * @return if the game is finished.
	 */
	public boolean finished(State state) {
		if(goalTotal == 1) {
			if(board.get(state.getYOne()).get(state.getXOne()) == 'x') return true;
		} else if(goalTotal == 2) {
			if(board.get(state.getYOne()).get(state.getXOne()) == 'x' &&
					board.get(state.getYTwo()).get(state.getXTwo()) == 'x') return true;
		}
		return false;
	}
	
	/**
	 * Checks if the game is completed.
	 * @return true if the game is completed.
	 */
	public boolean isGameComplete() {
		return this.goalTotal == getScore();
	}
	
}
