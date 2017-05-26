package comp2911.game;
import java.util.Comparator;

public class State implements Comparator<State>  {
	
	private int xOne;
	private int yOne;
	private int xTwo;
	private int yTwo;
	private int xThree;
	private int yThree;
	private int xChar;
	private int yChar;
	private boolean boxOne;
	private boolean boxTwo;
	private boolean boxThree;
	private int numMoves;
	
	/**
	 * Constructor for State when there are no Parameters
	 */
	public State() {
	}
	
	/**
	 * Constructor for State when there is one box
	 * (Postconditions: new state with fields initialized)
	 * @param numMoves is the move number of this State
	 * (Preconditions: non-negative)
	 * @param xChar
	 * (Preconditions: valid coordinate)
	 * @param yChar
	 * (Preconditions: valid coordinate)
	 * @param xOne
	 * (Preconditions: valid coordinate)
	 * @param yOne
	 * (Preconditions: valid coordinate)
	 */
	
	public State(int numMoves, int xChar, int yChar, int xOne, int yOne) {
		this.numMoves = numMoves;
		this.xChar = xChar;
		this.yChar = yChar;
		this.xOne = xOne;
		this.yOne = yOne;
		this.xTwo = -10;
		this.yTwo = -10;
		this.xThree = -10;
		this.yThree = -10;
		this.boxOne = true;
		this.boxTwo = false;
		this.boxThree = false;
	}
	
	/**
	 * Constructor for State when there is one box
	 * (Postconditions: new state with fields initialized)
	 * @param numMoves is the move number of this State
	 * (Preconditions: non-negative)
	 * @param xChar
	 * (Preconditions: valid coordinate)
	 * @param yChar
	 * (Preconditions: valid coordinate)
	 * @param xOne
	 * (Preconditions: valid coordinate)
	 * @param yOne
	 * (Preconditions: valid coordinate)
	 * @param xTwo
	 * (Preconditions: valid coordinate)
	 * @param yTwo
	 * (Preconditions: valid coordinate)
	 */
	
	public State(int numMoves, int xChar, int yChar, int xOne, int yOne, int xTwo, int yTwo) {
		this.numMoves = numMoves;
		this.xChar = xChar;
		this.yChar = yChar;
		this.xOne = xOne;
		this.yOne = yOne;
		this.xTwo = xTwo;
		this.yTwo = yTwo;
		this.xThree = -10;
		this.yThree = -10;
		this.boxOne = true;
		this.boxTwo = true;
		this.boxThree = false;
	}
	
	/**
	 * Constructor for State when there is one box
	 * (Postconditions: new state with fields initialized)
	 * @param numMoves is the move number of this State
	 * (Preconditions: non-negative)
	 * @param xChar
	 * (Preconditions: valid coordinate)
	 * @param yChar
	 * (Preconditions: valid coordinate)
	 * @param xOne
	 * (Preconditions: valid coordinate)
	 * @param yOne
	 * (Preconditions: valid coordinate)
	 * @param xTwo
	 * (Preconditions: valid coordinate)
	 * @param yTwo
	 * (Preconditions: valid coordinate)
	 * @param xThree
	 * (Preconditions: valid coordinate)
	 * @param yThree
	 * (Preconditions: valid coordinate)
	 */
	
	public State(int numMoves, int xChar, int yChar, int xOne, int yOne, int xTwo, int yTwo, int xThree, int yThree) {
		this.numMoves = numMoves;
		this.xChar = xChar;
		this.yChar = yChar;
		this.xOne = xOne;
		this.yOne = yOne;
		this.xTwo = xTwo;
		this.yTwo = yTwo;
		this.xThree = xThree;
		this.yThree = yThree;
		this.boxOne = true;
		this.boxTwo = true;
		this.boxThree = true;
	}
	
	/**
	 * Getter for numMoves
	 * @return numMoves
	 */
	
	public int getNumMoves() {
		return numMoves;
	}
	
	/**
	 * Getter for xChar
	 * @return xChar
	 */
	
	public int getXChar() {
		return xChar;
	}
	
	/**
	 * Getter for yChar
	 * @return yChar
	 */
	
	public int getYChar() {
		return yChar;
	}
	
	/**
	 * Getter for xOne
	 * @return xOne
	 */
	
	public int getXOne() {
		return xOne;
	}
	
	/**
	 * Getter for yOne
	 * @return yOne
	 */
	
	public int getYOne() {
		return yOne;
	}
	
	/**
	 * Getter for xTwo
	 * @return xTwo
	 */
	
	public int getXTwo() {
		return xTwo;
	}
	
	/**
	 * Getter for yTwo
	 * @return yTwo
	 */
	
	public int getYTwo() {
		return yTwo;
	}
	
	/**
	 * Getter for xThree
	 * @return xThree
	 */
	
	public int getXThree() {
		return xThree;
	}
	
	/**
	 * Getter for yThree
	 * @return yThree
	 */
	
	public int getYThree() {
		return yThree;
	}
	
	/**
	 * Compares number of moves between two states
	 * (Postconditions: returns difference in between number of moves)
	 * @param first state being compared
	 * (Preconditions: valid non null position object)
	 * @param second state being compared
	 * (Preconditions: valid non null position object)
	 * @return positive integer if state one greater than state two. Vice versa for opposite. Zero otherwise.
	 * (Preconditions: valid integer)
	 */
	
	@Override
	public int compare(State one, State two) {
		return one.getNumMoves() - two.getNumMoves();
	}
	
	/**
	 * Check if this state is equal to the object
	 * @param o is the Object being compared
	 * (Preconditions: valid non null object)
	 * @return true if they are equal. Otherwise false.
	 */
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true; // same instance
		if (getClass() != o.getClass()) return false; // not identical
		State otherState = (State) o;
		if (xChar != otherState.getXChar() ) return false;
		if (yChar != otherState.getYChar() ) return false;
		if (boxOne == true) {
			if (xOne != otherState.getXOne() ) return false;
			if (yOne != otherState.getYOne() ) return false;
		}
		if (boxTwo == true) {
			if (xTwo != otherState.getXTwo() ) return false;
			if (yTwo != otherState.getYTwo() ) return false;
		}
		if (boxThree == true) {
			if (xThree != otherState.getXThree() ) return false;
			if (yThree != otherState.getYThree() ) return false;
		}
		return true;
	}
}
