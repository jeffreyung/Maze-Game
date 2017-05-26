package comp2911.game;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Jamison Tsai
 */
public class Solver {

	private ArrayList<ArrayList<Character>> board;
	
	private int goalTotal;
	
	private PriorityQueue<State> pQueue;
	private ArrayList<State> visited; 

	public Solver(ArrayList<ArrayList<Character>> board, int goalTotal) {
		this.board = board;
		this.goalTotal = goalTotal;
		this.pQueue = new PriorityQueue<State>();
		this.visited = new ArrayList<State>();
	}
	
	/**
	 * @param xChar is the x-coordinate of the character.
	 * @param yChar is the y-coordinate of the character.
	 * @param xOne is the x-coordinate of the first box.
	 * @param yOne is the y-coordinate of the first box.
	 * @param xTwo is the x-coordinate of the second box.
	 * @param yTwo is the y-coordinate of the second box.
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
			if(board.get(state.getYOne()).get(state.getXOne()) == ':') return true;
		} else if(goalTotal == 2) {
			if(board.get(state.getYOne()).get(state.getXOne()) == ':' &&
					board.get(state.getYTwo()).get(state.getXTwo()) == ':') return true;
		}
		return false;
	}
}
