package comp2911.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game board generator
 * @author James Gibson Z3418942
 *
 */
public class MapGenerator {
	private ArrayList<ArrayList<Character>> board;
	private static final int TEMPLATE = 5;
	private static final int NUM_DIRECTIONS = 4;
	private static final int UP = 0; //Use Direction.UP instead etc...
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	private static final int X = 1;
	private static final int Y = 0;
	
	private ArrayList<Position> seen;
	private boolean finished;
	private int boardSize;
	
	
	/**
	 * Creates a new map generator
	 */
	public MapGenerator(int boardSize) {
		finished = false;
		this.boardSize = boardSize + TEMPLATE*2;
	}
	
	private void initBoard() {
		seen = new ArrayList<Position>();
		board = new ArrayList<ArrayList<Character>>();
		ArrayList<Character> row;
		
		for(int i = 0; i < boardSize; i++){
			row = new ArrayList<Character>();
			for(int j = 0; j < boardSize; j++){
				if ((i < TEMPLATE) || (j < TEMPLATE)){
					row.add('.');
				} else if((i >= boardSize - TEMPLATE) || (j >= boardSize - TEMPLATE)){
					row.add('.');
				} else {
					row.add('o');
				}
			}
			board.add(row);
		}
	}
	
	/**
	 * Creates a game board
	 * @return a 2d array representing the game board, where:
	 * | - wall
	 * x - goal
	 * o - empty space
	 * . - box start position
	 * c - character start position
	 * b - bomb start position
	 */
	public ArrayList<ArrayList<Character>> createBoard() {
		ArrayList<Position> path = new ArrayList<Position>();
		Position charPos = new Position(0, 0);

		while(path.isEmpty()){
			initBoard();
			addRandWalls();
			charPos = addFixtures();
			
			printBoard();
			path = makePath(charPos);
		}
		
		addPath(path);
			
		printBoard();
		return board;
	}
	
	/**
	 * Add spaces to the board to represent a path
	 * @param path list of tiles from c to x
	 */
	private void addPath(ArrayList<Position> path) {
		for(Position p : path){
			board.get(p.getX()).set(p.getY(), ' ');
		}
		System.out.println();
	}

	/**
	 * creates a path between the character and a goal 
	 * @param charPos, the position of the character on the board
	 * TODO noSol error
	 */
	private ArrayList<Position> makePath(Position charPos) {
		Position p;
		ArrayList<Position> path = new ArrayList<Position>();
		int x = charPos.getX();
		int y = charPos.getY();
		char next = 'q';
		
		seen.add(charPos);
		
		if(board.get(x).get(y) != 'x'){
			
			next = board.get(x + 1).get(y); //down
			p = new Position(x + 1, y);
			if(((next == 'o') || (next == 'x') || (next == 'c')) && contains(p, seen) == false && finished == false) {
				path = makePath(p);
				if (finished == true){
					path.add(0, p);
					return path;
				}
			} 
			
			
			next = board.get(x - 1).get(y); //up
			p = new Position(x - 1, y);
			if(((next == 'o') || (next == 'x') || (next == 'c')) && contains(p, seen) == false && finished == false) {
				path = makePath(p);
				if (finished == true){
					path.add(0, p);
					return path;
				}
			}
			
			next = board.get(x).get(y + 1); //right
			p = new Position(x, y + 1);
			if(((next == 'o') || (next == 'x') || (next == 'c')) && contains(p, seen) == false && finished == false) {
				path = makePath(p);
				if (finished == true){
					path.add(0, p);
					return path;
				}
			}
			
			next = board.get(x).get(y - 1); //left
			p = new Position(x, y - 1);
			if( ((next == 'o') || (next == 'x') || (next == 'c')) && contains(p, seen) == false && finished == false) {
				path = makePath(p);
				if (finished == true){
					path.add(0, p);
					return path;
				}
			}
			
		} else {
			finished = true;
			p = new Position(x, y);
			path.add(p);
		}
		return path;
	}
	
	/**
	 * Checks a list to see if a position is contained in it
	 * @param position, a position to find in the list
	 * @param posList a list of positions
	 * @return true if the list contains the position
	 */
	private boolean contains(Position position, ArrayList<Position> posList) {
		
		for(Position p : posList) {
			if((p.getX() == position.getX()) && (p.getY() == position.getY())) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * creates a path between the character and a goal 
	 * @param charPos, the position of the character on the board
	 *
	private void makePath(int[] charPos) {
		Random rand = new Random();
		ArrayList<Integer[]> seen = new ArrayList<Integer[]>();
		Integer[] nextPos;
		int bound = 4;
		
		while (board.get(charPos[X]).get(charPos[Y]) != 'x'){
			nextPos = new Integer[2];
			nextPos[X] = charPos[X];
			nextPos[Y] = charPos[Y];
			
			board.get(charPos[X]).set(charPos[Y], ' ');
			
			if (rand.nextInt(bound) == UP){
				nextPos[Y] = charPos[Y] + 1;
			} else if (rand.nextInt(bound) == DOWN){
				nextPos[Y] = charPos[Y] + 1;
			} else if (rand.nextInt(bound) == LEFT){
				nextPos[X] = charPos[X] + 1;
			} else if (rand.nextInt(bound) == RIGHT){
				nextPos[X] = charPos[X] + 1;
			}
			
			if (seen.contains(nextPos) == false){
				if((board.get(nextPos[X]).get(nextPos[Y]) != '.')){ // && (board.get(nextPos[X]).get(nextPos[Y]) != '|')){
					charPos[X] = nextPos[X];
					charPos[Y] = nextPos[Y];
					seen.add(nextPos);
					System.out.print(charPos[X] + "," + charPos[Y] + " " +board.get(charPos[X]).get(charPos[Y]) + "->");
				}
			}
			System.out.println();
			System.out.println();
			System.out.println();
			printBoard();
		}
		System.out.println();
	}*/

	/** 
	 * Adds characters and box start positions to the board
	 * TODO use TEMPLATEs
	 */
	private Position addFixtures() {
		Position p = new Position(6,6);
		
		board.get(6).set(6, 'c');
		board.get(13).set(13, 'x');
		return p;
	}

	/**
	 * Adds wall to the board at any real location, with a probability of 1 in 3
	 */
	private void addRandWalls(){
		Random random = new Random();
		final int minBound = 0;
		int randNum;
		int rowSize = 0;
		int columnSize = board.size();
		int bound = 3;
		
		for(int i = 0; i < columnSize; i++){
			rowSize = board.get(i).size();
			for(int j = 0; j < rowSize; j++){
				randNum = random.nextInt(bound);
				if (randNum == minBound){
					if ( (((i >= TEMPLATE) && (i < rowSize - TEMPLATE))) && (((j >= TEMPLATE) && (j < rowSize - TEMPLATE)))) {
						board.get(i).set(j, '|');
					}
				}
			}
		}
	}
	
	/**
	 * TODO delete
	 */
	public void printBoard() {
		int i = 0;
		
		System.out.print("  ");
		for(ArrayList<Character> b : board){
			if (i > 9){
				System.out.print(i);
			} else {
				System.out.print(" " + i);
			}
			i++;
		}
		System.out.println();
		
		i = 0;
		for(ArrayList<Character> a : board){
			if (i > 9){
				System.out.print(i);
			} else {
				System.out.print(" " + i);
			}
			i++;
			for(Character c : a){
				System.out.print(" " + c);
			}
			System.out.println();
		}
	}
	
}
