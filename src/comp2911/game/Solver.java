package comp2911.game;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Solver {


	private static final int UP = 1; // constant to represent up
	private static final int DOWN = 2; // constant to represent down
	private static final int LEFT = 3; // constant to represent left
	private static final int RIGHT = 4; // constant to represent right

	private ArrayList<ArrayList<Character>> board; // starting board
	private int width; // width of board
	private int height; // height of board
	private int goalTotal; // number of goals
	private PriorityQueue<State> pQueue; // queue to hold states
	private ArrayList<State> visited; // visited states

	/**
	 * Main function
	 * (Postconditions: completes without errors and exceptions)
	 * @param board with starting positions
	 * @param width of board
	 * @param height of board
	 */
	
	public Solver(ArrayList<ArrayList<Character>> board, int goalTotal, int width, int height) {
		this.board = board;
		this.width = width;
		this.height = height;
		this.goalTotal = goalTotal;
		this.pQueue = new PriorityQueue<State>(11, new State());
		this.visited = new ArrayList<State>();
	}
	
	/**
	 * Checks whether a solution exists
	 * (Postconditions: return true if there is a solution otherwise false)
	 * @return true if there is a solution. Otherwise false.
	 */
	
	public boolean search() {
		State begin = null;
		
		int xChar = -1;
		int yChar =  -1;
		int xOne = -1;
		int yOne = -1;
		int xTwo = -1;
		int yTwo = -1;
		int xThree = -1;
		int yThree = -1;
		
		xChar = getXIndex('1', 1);
		yChar =  getYIndex('1', 1);
		xOne = getXIndex('.', 1);
		yOne = getYIndex('.', 1);
		if (goalTotal > 1) {
			xTwo = getXIndex('.', 2);
			yTwo = getYIndex('.', 2);
			if (goalTotal > 2) {
				xThree = getXIndex('.', 3);
				yThree = getYIndex('.', 3);
			}
		}
		
		if (goalTotal == 1) {
			begin = new State(0, xChar, yChar, xOne, yOne);
		} else if (goalTotal == 2) {
			begin = new State(0, xChar, yChar, xOne, yOne, xTwo, yTwo);
		} else { // must be three
			begin = new State(0, xChar, yChar, xOne, yOne, xTwo, yTwo, xThree, yThree);
		}
		
		pQueue.add(begin);
		
		while (!pQueue.isEmpty()) {
			State tempState = pQueue.poll();
			
			if (finished(tempState)) return true; // return true if game is finished
			
			if (visited.contains(tempState)) continue; 
			visited.add(tempState);
			
			int currCharX = tempState.getXChar();
			int currCharY = tempState.getYChar();
			int currXOne = tempState.getXOne();
			int currYOne = tempState.getYOne();
			int currXTwo = tempState.getXTwo();
			int currYTwo = tempState.getYTwo();
			int currXThree = tempState.getXThree();
			int currYThree = tempState.getYThree();
			int currNumMoves = tempState.getNumMoves();
			
			if (currNumMoves > 200) break;
			
			State newState = null;
			
			if (goalTotal == 1) {
				if (isLegal(UP, tempState)) {
					if (pushBox(UP, tempState)) {
						newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne-1);
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(DOWN, tempState)) {
					if (pushBox(DOWN, tempState)) {
						newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne+1);
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(LEFT, tempState)) {
					if (pushBox(LEFT, tempState)) {
						newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne-1, currYOne);
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(RIGHT, tempState)) {
					if (pushBox(RIGHT, tempState)) {
						newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne+1, currYOne);
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne);
						pQueue.add(newState);
					}
				}
			} else if (goalTotal == 2) {
				
				if (isLegal(UP, tempState)) {
					if (pushBox(UP, tempState)) {
						newState = null;
						if (whichBox(UP, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne-1, currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo-1);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(DOWN, tempState)) {
					if (pushBox(DOWN, tempState)) {
						newState = null;
						if (whichBox(DOWN, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne+1, currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo+1);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(LEFT, tempState)) {
					if (pushBox(LEFT, tempState)) {
						newState = null;
						if (whichBox(LEFT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne-1, currYOne , currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne , currXTwo-1, currYTwo);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(RIGHT, tempState)) {
					if (pushBox(RIGHT, tempState)) {
						newState = null;
						if (whichBox(RIGHT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne+1, currYOne , currXTwo, currYTwo);
						} else {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne , currXTwo+1, currYTwo);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne, currXTwo, currYTwo);
						pQueue.add(newState);
					}
				}
			} else {
				if (isLegal(UP, tempState)) {
					if (pushBox(UP, tempState)) {
						newState = null;
						if (whichBox(UP, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne-1, currXTwo, currYTwo, currXThree, currYThree);
						} else if (whichBox(UP, tempState) == 2) {
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo-1, currXThree, currYThree);
						} else { // must be 3
							newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree-1);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY-1, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(DOWN, tempState)) {
					if (pushBox(DOWN, tempState)) {
						newState = null;
						if (whichBox(DOWN, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne+1, currXTwo, currYTwo, currXThree, currYThree);
						} else if (whichBox(DOWN, tempState) == 2) {
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo+1, currXThree, currYThree);
						} else { // must be 3
							newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree+1);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX, currCharY+1, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree);
						pQueue.add(newState);
					}
				}
				
				if (isLegal(LEFT, tempState)) {
					if (pushBox(LEFT, tempState)) {
						newState = null;
						if (whichBox(LEFT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne-1, currYOne , currXTwo, currYTwo, currXThree, currYThree);
						} else if (whichBox(LEFT, tempState) == 2) {
							newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne , currXTwo-1, currYTwo, currXThree, currYThree);
						} else { // must be 3
							newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne, currXTwo, currYTwo, currXThree-1, currYThree);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX-1, currCharY, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree);
						pQueue.add(newState);
					}
				}

				if (isLegal(RIGHT, tempState)) {
					if (pushBox(RIGHT, tempState)) {
						newState = null;
						if (whichBox(RIGHT, tempState) == 1) {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne+1, currYOne , currXTwo, currYTwo, currXThree, currYThree);
						} else if (whichBox(RIGHT, tempState) == 2) {
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne , currXTwo+1, currYTwo, currXThree, currYThree);
						} else { // must be 3
							newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne, currXTwo, currYTwo, currXThree+1, currYThree);
						}
						pQueue.add(newState);
					} else {
						newState = new State(currNumMoves+1, currCharX+1, currCharY, currXOne, currYOne, currXTwo, currYTwo, currXThree, currYThree);
						pQueue.add(newState);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether a move is legal
	 * (Postconditions: return true if it is a legal move otherwise false)
	 * @param dir is the direction of move being checked
	 * @param state is current state of the game
	 * @return true if it is a legal move. Otherwise false.
	 */
	
	public boolean isLegal(int dir, State state){
		if (dir == UP) {
			if (board.get(state.getYChar()-1).get(state.getXChar()) == '|') return false;
			if (state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) {
				if (board.get(state.getYChar()-2).get(state.getXChar()) == '|') return false;
				if (state.getXOne() == state.getXTwo() && state.getYOne()-1 == state.getYTwo()) return false;
				if (state.getXOne() == state.getXThree() && state.getYOne()-1 == state.getYThree()) return false;
			}
			if (state.getXChar() == state.getXTwo() && state.getYChar()-1 == state.getYTwo()) {
				if (board.get(state.getYChar()-2).get(state.getXChar()) == '|') return false;
				if (state.getXTwo() == state.getXOne() && state.getYTwo()-1 == state.getYOne()) return false;
				if (state.getXTwo() == state.getXThree() && state.getYTwo()-1 == state.getYThree()) return false;
			}
			if (state.getXChar() == state.getXThree() && state.getYChar()-1 == state.getYThree()) {
				if (board.get(state.getYChar()-2).get(state.getXChar()) == '|') return false;
				if (state.getXThree() == state.getXOne() && state.getYThree()-1 == state.getYOne()) return false;
				if (state.getXThree() == state.getXTwo() && state.getYThree()-1 == state.getYTwo()) return false;
			}
		} else if (dir == DOWN) {
			if (board.get(state.getYChar()+1).get(state.getXChar()) == '|') return false;
			if (state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) {
				if (board.get(state.getYChar()+2).get(state.getXChar()) == '|') return false;
				if (state.getXOne() == state.getXTwo() && state.getYOne()+1 == state.getYTwo()) return false;
				if (state.getXOne() == state.getXThree() && state.getYOne()+1 == state.getYThree()) return false;
			}
			if (state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) {
				if (board.get(state.getYChar()+2).get(state.getXChar()) == '|') return false;
				if (state.getXTwo() == state.getXOne() && state.getYTwo()+1 == state.getYOne()) return false;
				if (state.getXTwo() == state.getXThree() && state.getYTwo()+1 == state.getYThree()) return false;
			}
			if (state.getXChar() == state.getXThree() && state.getYChar()+1 == state.getYThree()) {
				if (board.get(state.getYChar()+2).get(state.getXChar()) == '|') return false;
				if (state.getXThree() == state.getXOne() && state.getYThree()+1 == state.getYOne()) return false;
				if (state.getXThree() == state.getXTwo() && state.getYThree()+1 == state.getYTwo()) return false;
			}
		} else if (dir == LEFT) {
			if (board.get(state.getYChar()).get(state.getXChar()-1) == '|') return false;
			if (state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) {
				if (board.get(state.getYChar()).get(state.getXChar()-2) == '|') return false;
				if (state.getXOne()-1 == state.getXTwo() && state.getYOne() == state.getYTwo()) return false;
				if (state.getXOne()-1 == state.getXThree() && state.getYOne() == state.getYThree()) return false;
			}
			if (state.getXChar()-1 == state.getXTwo() && state.getYChar() == state.getYTwo()) {
				if (board.get(state.getYChar()).get(state.getXChar()-2) == '|') return false;
				if (state.getXTwo()-1 == state.getXOne() && state.getYTwo() == state.getYOne()) return false;
				if (state.getXTwo()-1 == state.getXThree() && state.getYTwo() == state.getYThree()) return false;
			}
			if (state.getXChar()-1 == state.getXThree() && state.getYChar() == state.getYThree()) {
				if (board.get(state.getYChar()).get(state.getXChar()-2) == '|') return false;
				if (state.getXThree()-1 == state.getXOne() && state.getYThree() == state.getYOne()) return false;
				if (state.getXThree()-1 == state.getXTwo() && state.getYThree() == state.getYTwo()) return false;
			}
		} else if (dir == RIGHT) {
			if (board.get(state.getYChar()).get(state.getXChar()+1) == '|') return false;
			if (state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) {
				if (board.get(state.getYChar()).get(state.getXChar()+2) == '|') return false;
				if (state.getXOne()+1 == state.getXTwo() && state.getYOne() == state.getYTwo()) return false;
				if (state.getXOne()+1 == state.getXThree() && state.getYOne() == state.getYThree()) return false;
			}
			if (state.getXChar()+1 == state.getXTwo() && state.getYChar() == state.getYTwo()) {
				if (board.get(state.getYChar()).get(state.getXChar()+2) == '|') return false;
				if (state.getXTwo()+1 == state.getXOne() && state.getYTwo() == state.getYOne()) return false;
				if (state.getXTwo()+1 == state.getXThree() && state.getYTwo() == state.getYThree()) return false;
			}
			if (state.getXChar()+1 == state.getXThree() && state.getYChar() == state.getYThree()) {
				if (board.get(state.getYChar()).get(state.getXChar()+2) == '|') return false;
				if (state.getXThree()+1 == state.getXOne() && state.getYThree() == state.getYOne()) return false;
				if (state.getXThree()+1 == state.getXTwo() && state.getYThree() == state.getYTwo()) return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks whether box is being pushed in move
	 * (Postconditions: return true if it is a push otherwise false)
	 * @param dir is the direction of move
	 * @param state is current state of the game
	 * @return true if it is a push move otherwise false
	 */
	
	public boolean pushBox(int dir, State state){
		if (dir == UP) {
			if (state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) return true;
			if (state.getXChar() == state.getXTwo() && state.getYChar()-1 == state.getYTwo()) return true;
			if (state.getXChar() == state.getXThree() && state.getYChar()-1 == state.getYThree()) return true;
		} else if (dir == DOWN) {
			if (state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) return true;
			if (state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return true;
			if (state.getXChar() == state.getXThree() && state.getYChar()+1 == state.getYThree()) return true;
		} else if (dir == LEFT) {
			if (state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) return true;
			if (state.getXChar()-1 == state.getXTwo() && state.getYChar() == state.getYTwo()) return true;
			if (state.getXChar()-1 == state.getXThree() && state.getYChar() == state.getYThree()) return true;
		} else if (dir == RIGHT) {
			if (state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) return true;
			if (state.getXChar()+1 == state.getXTwo() && state.getYChar() == state.getYTwo()) return true;
			if (state.getXChar()+1 == state.getXThree() && state.getYChar() == state.getYThree()) return true;
		}
		return false;
	}
	
	/**
	 * Checks which box is being moved
	 * (Postconditions: return box number being moved)
	 * @param dir is the direction of move
	 * @param state is current state of the game
	 * @return box number being moved
	 */
	
	public int whichBox(int dir, State state){
		if (dir == UP) {
			if (state.getXChar() == state.getXOne() && state.getYChar()-1 == state.getYOne()) return 1;
			if (state.getXChar() == state.getXTwo() && state.getYChar()-1 == state.getYTwo()) return 2;
			if (state.getXChar() == state.getXThree() && state.getYChar()-1 == state.getYThree()) return 3;
		} else if (dir == DOWN) {
			if (state.getXChar() == state.getXOne() && state.getYChar()+1 == state.getYOne()) return 1;
			if (state.getXChar() == state.getXTwo() && state.getYChar()+1 == state.getYTwo()) return 2;
			if (state.getXChar() == state.getXThree() && state.getYChar()+1 == state.getYThree()) return 3;
		} else if (dir == LEFT) {
			if (state.getXChar()-1 == state.getXOne() && state.getYChar() == state.getYOne()) return 1;
			if (state.getXChar()-1 == state.getXTwo() && state.getYChar() == state.getYTwo()) return 2;
			if (state.getXChar()-1 == state.getXThree() && state.getYChar() == state.getYThree()) return 3;
		} else if (dir == RIGHT) {
			if (state.getXChar()+1 == state.getXOne() && state.getYChar() == state.getYOne()) return 1;
			if (state.getXChar()+1 == state.getXTwo() && state.getYChar() == state.getYTwo()) return 2;
			if (state.getXChar()+1 == state.getXThree() && state.getYChar() == state.getYThree()) return 3;
		}
		return 0;
	}
	
	/**
	 * Checks if game is finished
	 * (Postconditions: return box number being moved)
	 * @param state is current state of the game
	 * @return true if finished otherwise false
	 */
	
	public boolean finished(State state){
		if (goalTotal == 1) {
			if (board.get(state.getYOne()).get(state.getXOne()) == 'x') return true;
		} else if (goalTotal == 2) {
			if (board.get(state.getYOne()).get(state.getXOne()) == 'x' &&
					board.get(state.getYTwo()).get(state.getXTwo()) == 'x') return true;
		} else if (goalTotal == 3) {
			if (board.get(state.getYOne()).get(state.getXOne()) == 'x' &&
					board.get(state.getYTwo()).get(state.getXTwo()) == 'x' &&
						board.get(state.getYThree()).get(state.getXThree()) == 'x') return true;
		}
		return false;
	}
	
	/**
	 * Get X Index of character
	 * @param c is the character being checked
	 * @param order number of box if box is being searched. otherwise defaults 1
	 * @return index X of character
	 */
	
	public int getXIndex(Character c, int order) {
		int pos = -10;
		int count = order;
		for (int x = 0 ; x < width; x++) {
			for (int y = 0 ; y < height; y++) {
				if (board.get(y).get(x) == c) {
					if (count == 1) {
						return x;
					} else {
						count--;
					}
				}
			}
		}
		
		return pos;
	}
	
	/**
	 * Get Y Index of character
	 * @param c is the character being checked
	 * @param order number of box if box is being searched. otherwise defaults 1
	 * @return index Y of character
	 */
	
	public int getYIndex(Character c, int order) {
		int pos = -10;
		int count = order;
		for (int x = 0 ; x < width; x++) {
			for (int y = 0 ; y < height; y++) {
				if (board.get(y).get(x) == c) {
					if (count == 1) {
						return y;
					} else {
						count--;
					}
				}
			}
		}
		
		return pos;
	}
}
