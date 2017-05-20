package comp2911.game;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator implements MapInterface{
	private static final int TOP = 0;
	private static final int LEFT = 1;
	private static final int BOTTOM = 2;
	private static final int RIGHT = 3;
	
	private boolean notQuadThree;
	private boolean notQuadOne;
	private boolean extraGoalUp;
	private ArrayList<ArrayList<Character>> map;
	private ArrayList<ArrayList<Integer>> box2Pos;
	private int boardSize;
	private Position intialCharPos;
	
	public MapGenerator(){
		map = new ArrayList<ArrayList<Character>>();
		box2Pos = new ArrayList<ArrayList<Integer>>();
		notQuadThree = false;
		notQuadOne = false;
		extraGoalUp = false;
		initBox2Array();
	}
	
	@Override
	public ArrayList<ArrayList<Character>> createBoard() {
		int size = 7;
		boardSize = size;
		initMap(size);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++){
				if ( (i != 0) && (j != 0) && (i != size - 1) && (j != size - 1) ) {
					map.get(i).set(j, ' ');
				}
			}
		}
		
		setExtraWalls();
		setBox();
		
		map.get(3).set(3, '|');
		map.get(1).set(1, 'c');
		intialCharPos = new Position(1,1);
		map.get(5).set(5, 'x');
		
		
		
		printBoard();
		return map;
	}
	
	/**
	 * Adds extra walls to the map
	 */
	private void setExtraWalls() {
		Random rand = new Random();
		int maxNumWalls = 3;
		int numWalls = rand.nextInt(maxNumWalls) + 1;
		boolean onCentre = rand.nextBoolean();
		int directions = 0;
		
		if (numWalls == 3){
			map.get(3).set(4, '|');
			directions = 3;
			numWalls = rand.nextInt(directions);
			if (numWalls == TOP){
				notQuadOne = true;
				map.get(1).set(3, '|');
				map.get(3).set(1, '|');
			} else if (numWalls == LEFT){
				extraGoalUp = true;
				map.get(1).set(3, '|');
				map.get(5).set(3, '|');
			} else if (numWalls == BOTTOM){
				notQuadThree = true;
				extraGoalUp = true;
				map.get(3).set(1, '|');
				map.get(5).set(3, '|');
			}
		} else if (numWalls == 2){
			if (onCentre == true){
				//placing wall on inside
				directions = 4;
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(2).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(2, '|');
				} else if (numWalls == BOTTOM){
					map.get(4).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(4, '|');
				} 
				//placing second wall on outside
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(1).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(1, '|');
				} else if (numWalls == BOTTOM){
					map.get(5).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(5, '|');
				} 
			} else {
				//placing wall on outside
				directions = 4;
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(1).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(1, '|');
				} else if (numWalls == BOTTOM){
					map.get(5).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(5, '|');
				} 
				//placing second wall on outside
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(1).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(1, '|');
				} else if (numWalls == BOTTOM){
					map.get(5).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(5, '|');
				} 
			}
		} else if (numWalls == 1){
			if (onCentre == true){
				//placing wall on inside
				directions = 4;
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(2).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(2, '|');
				} else if (numWalls == BOTTOM){
					map.get(4).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(4, '|');
				} 
			} else {
				//placing wall on outside
				directions = 4;
				numWalls = rand.nextInt(directions);
				if (numWalls == TOP){
					map.get(1).set(3, '|');
				} else if (numWalls == LEFT){
					map.get(3).set(1, '|');
				} else if (numWalls == BOTTOM){
					map.get(5).set(3, '|');
				} else if (numWalls == RIGHT){
					map.get(3).set(5, '|');
				} 
			}
		} 
	}

	/**
	 * sets the box position
	 */
	private void setBox() {
		Random rand = new Random();
		int numBoxes = rand.nextInt(2) + 1;
		int bound = 8;
		int boxPos = rand.nextInt(bound);
		int boxPos2 = box2Pos.get(boxPos).get(rand.nextInt(box2Pos.get(boxPos).size()));;
		int topLeft = 0;
		int topMid = 1;
		int topRight = 2;
		int midLeft = 3;
		int midRight = 4;
		int botLeft = 5;
		int botMid = 6;
		int botRight = 7;
		
		if (numBoxes == 1){
			if (boxPos == topLeft){
				if(notQuadOne == false){
					map.get(2).set(2, '.');
				} else { 
					map.get(2).set(4, '.');
				}
			} else if (boxPos == topMid){
				map.get(2).set(3, '.');
			} else if (boxPos == topRight){
				map.get(2).set(4, '.');
			} else if (boxPos == midLeft){
				map.get(3).set(2, '.');
			} else if (boxPos == midRight){
				map.get(3).set(4, '.');
			} else if (boxPos == botLeft){
				if(notQuadThree == false){
					map.get(4).set(2, '.');
				} else {
					map.get(4).set(4, '.');
				}
			} else if (boxPos == botMid){
				map.get(4).set(3, '.');
			} else if (boxPos == botRight){
				map.get(4).set(4, '.');
			}
		} else {
			//place extra goal
			if((extraGoalUp == true) || (rand.nextBoolean() == true)){
				map.get(4).set(5, 'x');
			} else {
				map.get(5).set(4, 'x');
			}
			
			System.out.println("boxPos1 =" + boxPos+" == "+ boxPos2+ "= boxPos2");
			System.out.println("notQuadThree = " + notQuadThree + "notQuadOne = " + notQuadOne);
			
			//place first box
			if (boxPos == topLeft){
				if(notQuadOne == false){
					map.get(2).set(2, '.');
				} else { 
					map.get(2).set(4, '.');
				}
			} else if (boxPos == topMid){
				map.get(2).set(3, '.');
			} else if (boxPos == topRight){
				map.get(2).set(4, '.');
			} else if (boxPos == midLeft){
				map.get(3).set(2, '.');
			} else if (boxPos == midRight){
				map.get(3).set(4, '.');
			} else if (boxPos == botLeft){
				if(notQuadThree == false){
					map.get(4).set(2, '.');
				} else {
					map.get(4).set(4, '.');
				}
			} else if (boxPos == botMid){
				map.get(4).set(3, '.');
			} else if (boxPos == botRight){
				map.get(4).set(4, '.');
			}
			
			//place second box
			if (boxPos2 == topLeft){
				if(notQuadOne == false){
					map.get(2).set(2, '.');
				} else { 
					map.get(2).set(4, '.');
				}
			} else if (boxPos2 == topMid){
				map.get(2).set(3, '.');
			} else if (boxPos2 == topRight){
				map.get(2).set(4, '.');
			} else if (boxPos2 == midLeft){
				map.get(3).set(2, '.');
			} else if (boxPos2 == midRight){
				map.get(3).set(4, '.');
			} else if (boxPos2 == botLeft){
				if(notQuadThree == false){
					map.get(4).set(2, '.');
				} else {
					map.get(4).set(4, '.');
				}
			} else if (boxPos2 == botMid){
				map.get(4).set(3, '.');
			} else if (boxPos2 == botRight){
				map.get(4).set(4, '.');
			}
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
	
	/**
	 * Initialise the position guide for box two
	 * given the first box position box two can be placed in one 
	 * of three positions
	 */
	private void initBox2Array() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(4);
		list.add(6);
		list.add(7);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(5);
		list.add(6);
		list.add(7);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(3);
		list.add(5);
		list.add(6);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(2);
		list.add(4);
		list.add(7);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(0);
		list.add(3);
		list.add(5);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(4);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(2);
		box2Pos.add(list);
		list = new ArrayList<Integer>();
		list.add(0);
		list.add(1);
		list.add(3);
		box2Pos.add(list);
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
