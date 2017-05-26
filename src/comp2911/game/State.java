package comp2911.game;

/**
 * @author Jamison Tsai
 */
public class State implements Comparable<State>  {
	
	private int xOne;
	private int yOne;
	private int xTwo;
	private int yTwo;
	private int xChar;
	private int yChar;
	private boolean boxOne;
	private boolean boxTwo;
	private int numMoves;
	
	public State() {
	}
	
	public State(int numMoves, int xChar, int yChar, int xOne, int yOne) {
		this.numMoves = numMoves;
		this.xChar = xChar;
		this.yChar = yChar;
		this.xOne = xOne;
		this.yOne = yOne;
		this.xTwo = -10;
		this.yTwo = -10;
		this.boxOne = true;
		this.boxTwo = false;
	}
	
	public State(int numMoves, int xChar, int yChar, int xOne, int yOne, int xTwo, int yTwo) {
		this.numMoves = numMoves;
		this.xChar = xChar;
		this.yChar = yChar;
		this.xOne = xOne;
		this.yOne = yOne;
		this.xTwo = xTwo;
		this.yTwo = yTwo;
		this.boxOne = true;
		this.boxTwo = true;
	}
	
	public int getNumMoves() {
		return numMoves;
	}
	
	public int getXChar() {
		return xChar;
	}
	
	public int getYChar() {
		return yChar;
	}
	
	public int getXOne() {
		return xOne;
	}
	
	public int getYOne() {
		return yOne;
	}
	
	public int getXTwo() {
		return xTwo;
	}
	
	public int getYTwo() {
		return yTwo;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true; // same instance
		if (getClass() != o.getClass()) return false; // not identical
		State otherState = (State) o;
		if (boxOne = true) {
			if (xOne != otherState.xOne ) return false;
			if (yOne != otherState.yOne ) return false;
		}
		if (boxTwo = true) {
			if (xTwo != otherState.xTwo ) return false;
			if (yTwo != otherState.yTwo ) return false;
		}

		return true;
	}

	@Override
	public int compareTo(State o) {
		return this.getNumMoves() - o.getNumMoves();
	}
}
