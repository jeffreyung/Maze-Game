package comp2911.game;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator {
	
	private ArrayList<ArrayList<Character>> map;
	private int boardSize;
	private Position intialCharPos;
	
	public MapGenerator(){
		map = new ArrayList<ArrayList<Character>>();
	}
	
	/**
	 * Generates a new minimum viable product map
	 * @return a functioning map
	 */
	public ArrayList<ArrayList<Character>> createBoard() {
		Random rand = new Random();
		int bound = 8;
		int size = 7;
		boardSize = size;
		initMap(size);
		int boxPos = -1;
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++){
				if ( (i != 0) && (j != 0) && (i != 6) && (j != 6) ) {
					map.get(i).set(j, ' ');
				}
			}
		}
		
		boxPos = rand.nextInt(bound);
		
		if (boxPos == 0){
			map.get(2).set(2, '.');
		} else if (boxPos == 1){
			map.get(2).set(3, '.');
		} else if (boxPos == 2){
			map.get(2).set(4, '.');
		} else if (boxPos == 3){
			map.get(3).set(2, '.');
		} else if (boxPos == 4){
			map.get(3).set(4, '.');
		} else if (boxPos == 5){
			map.get(4).set(2, '.');
		} else if (boxPos == 6){
			map.get(4).set(3, '.');
		} else {
			map.get(4).set(4, '.');
		}
		
		map.get(3).set(3, '|');
		map.get(1).set(1, 'c');
		intialCharPos = new Position(1,1);
		map.get(5).set(5, 'x');
		
		printBoard();
		return map;
	}
	
	/**
	 * Initialises map
	 * @param x, size of x direction
	 * @param y, size of y direction
	 */
	private void initMap(int x, int y) {
		ArrayList<Character> row;
		int i = 0;
		int j = 0;
		
		for (i = 0; i < y; i++){
			row = new ArrayList<Character>();
			for (j = 0; j < x; j++){
				row.add('|');
			}
			map.add(row);
		}
	}
	
	/**
	 * Initialises map
	 * @param size, size of square map
	 */
	private void initMap(int size) {
		ArrayList<Character> row;
		int i = 0;
		int j = 0;
		
		for (i = 0; i < size; i++) {
			row = new ArrayList<Character>();
			for (j = 0; j < size; j++) {
				row.add('|');
			}
			map.add(row);
		}
	}
	
	/**
	 * TODO delete
	 */
	public void printBoard() {
		int i = 0;
		
		System.out.print("  ");
		for(ArrayList<Character> b : map) {
			if (i > 9){
				System.out.print(i);
			} else {
				System.out.print(" " + i);
			}
			i++;
		}
		System.out.println();
		
		i = 0;
		for(ArrayList<Character> a : map) {
			if (i > 9){
				System.out.print(i);
			} else {
				System.out.print(" " + i);
			}
			i++;
			for(Character c : a) {
				System.out.print(" " + c);
			}
			System.out.println();
		}
	}

	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * @return the initial character position.
	 */
	public Position getInitialCharPos() {
		return intialCharPos;
	}
}
