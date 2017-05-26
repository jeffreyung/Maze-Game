package comp2911.game;

import java.util.ArrayList;
import java.util.Random;

public class MapGeneratorMVP implements MapInterface{
	
	private ArrayList<ArrayList<Character>> map;
	private int boardSize;
	private Position intialCharPos;
	
	public MapGeneratorMVP(){
		map = new ArrayList<ArrayList<Character>>();
	}
	
	@Override
	public ArrayList<ArrayList<Character>> createBoard() {
		int size = 7;
		boardSize = size;
		map.clear();
		initMap(size);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++){
				if ( (i != 0) && (j != 0) && (i != size - 1) && (j != size - 1) ) {
					map.get(i).set(j, ' ');
				}
			}
		}
		
		setBox();
		
		map.get(3).set(3, '|');
		map.get(1).set(1, '1');
		intialCharPos = new Position(1,1);
		map.get(5).set(5, 'x');
		
		
		
		printBoard();
		return map;
	}
	
	/**
	 * sets the box position
	 */
	private void setBox() {
		Random rand = new Random();
		int bound = 8;
		int boxPos = rand.nextInt(bound);
		int topLeft = 0;
		int topMid = 1;
		int topRight = 2;
		int midLeft = 3;
		int midRight = 4;
		int botLeft = 5;
		int botMid = 6;
		int botRight = 7;
		
		if (boxPos == topLeft){
			map.get(2).set(2, '.');
		} else if (boxPos == topMid){
			map.get(2).set(3, '.');
		} else if (boxPos == topRight){
			map.get(2).set(4, '.');
		} else if (boxPos == midLeft){
			map.get(3).set(2, '.');
		} else if (boxPos == midRight){
			map.get(3).set(4, '.');
		} else if (boxPos == botLeft){
			map.get(4).set(2, '.');
		} else if (boxPos == botMid){
			map.get(4).set(3, '.');
		} else if (boxPos == botRight){
			map.get(4).set(4, '.');
		}
		
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
	
	@Override
	public int getBoardSize() {
		return boardSize;
	}
	
	@Override
	public Position getInitialCharPos(){
		return intialCharPos;
	}
	
}
