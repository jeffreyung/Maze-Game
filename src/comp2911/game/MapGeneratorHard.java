package comp2911.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates a game board with the trapdoor algorithm
 * @author James Gibson Z3418942 and 
 *
 */
public class MapGeneratorHard implements MapInterface{

	private static final int RIGHT = 0;
	private static final int DOWN = 1;
	
	private static final int sectionNum = 6;
	
	private ArrayList<ArrayList<Character>> map;
	private ArrayList<Section> sections;
	private ArrayList<Position> vertiPath;
	private ArrayList<Position> horizPath;
	
	private ArrayList<Position> boxes;
	
	private ArrayList<ArrayList<Position>> boxOnSect;
	
	private final int boardSize = 10;
	private final Position intialCharPos = new Position(1,1);
	
	private boolean NoSection5;
	private boolean NoSection6;
	
	public MapGeneratorHard(){
		map = new ArrayList<ArrayList<Character>>();
		sections = new ArrayList<Section>();
		boxes = new ArrayList<Position>();
		boxOnSect = new ArrayList<ArrayList<Position>>();
		vertiPath = new ArrayList<Position>();
		horizPath = new ArrayList<Position>();
	}

	@Override
	public ArrayList<ArrayList<Character>> createBoard() {
		map.clear();
		initMap(boardSize, boardSize);
		
		setSection1();
		setSection2();
		setSection3();
		setSection4();
		setSection5();
		setSection6();

		//setSections();
		
		setPath();
		setBox();
		
		map.get(1).set(1, '1');
		
		setGoals();
		
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
	
	private void setSection1() {
		Random rand = new Random();
		int orientation = rand.nextInt(2);
		
		Section section = new Section(new Position(1,1), orientation);
		sections.add(section);
		
		map.get(1).set(1, ' ');
		map.get(1).set(2, ' ');
		map.get(2).set(1, ' ');
		map.get(2).set(2, ' ');
		
		if (orientation == RIGHT) {
			map.get(1).set(3, ' ');
			map.get(2).set(3, ' ');
		} else if (orientation == DOWN) {
			map.get(3).set(1, ' ');
			map.get(3).set(2, ' ');
			
			NoSection5 = true;
		}
	}
	
	private void setSection2() {
		int poX = 5;
		if (sections.get(0).getOrientation() == DOWN) poX = 4;
		
		Random rand = new Random();
		int orientation = rand.nextInt(2);
		
		Section section = new Section(new Position(poX, 1), orientation);
		sections.add(section);
		
		map.get(1).set(poX, ' ');
		map.get(1).set(poX + 1, ' ');
		map.get(2).set(poX, ' ');
		map.get(2).set(poX + 1, ' ');
		
		if (orientation == RIGHT) {
			map.get(1).set(poX + 2, ' ');
			map.get(2).set(poX + 2, ' ');
		} else if (orientation == DOWN) {
			map.get(3).set(poX, ' ');
			map.get(3).set(poX + 1, ' ');
			
			NoSection6 = true;
		}
	}
	
	private void setSection3() {
		int poY = 4;
		if (sections.get(0).getOrientation() == DOWN) poY = 5;
		
		Random rand = new Random();
		int orientation = rand.nextInt(2);
		
		Section section = new Section(new Position(1, poY), orientation);
		sections.add(section);
		
		map.get(poY).set(1, ' ');
		map.get(poY).set(2, ' ');
		map.get(poY + 1).set(1, ' ');
		map.get(poY + 1).set(2, ' ');
		
		if (orientation == RIGHT) {
			map.get(poY).set(3, ' ');
			map.get(poY + 1).set(3, ' ');
		} else if (orientation == DOWN) {
			map.get(poY + 2).set(1, ' ');
			map.get(poY + 2).set(2, ' ');
			
			NoSection5 = true;
		}
	}
	
	private void setSection4() {
		int poX = 5;
		int poY = 4;
		
		if (sections.get(2).getOrientation() == DOWN) poX = 4;
		if (sections.get(1).getOrientation() == DOWN) poY = 5;
		
		Random rand = new Random();
		int orientation = rand.nextInt(2);
		
		Section section = new Section(new Position(poX, poY), orientation);
		sections.add(section);
		
		map.get(poY).set(poX, ' ');
		map.get(poY).set(poX + 1, ' ');
		map.get(poY + 1).set(poX, ' ');
		map.get(poY + 1).set(poX + 1, ' ');
		
		if (orientation == RIGHT) {
			map.get(poY).set(poX + 2, ' ');
			map.get(poY + 1).set(poX + 2, ' ');
		} else if (orientation == DOWN) {
			map.get(poY + 2).set(poX, ' ');
			map.get(poY + 2).set(poX + 1, ' ');
			
			NoSection6 = true;
		}
	}
	
	private void setSection5() {
		if (NoSection5) {
			sections.add(null);
			return;
		}
		Section section = new Section(new Position(1,7), RIGHT);
		sections.add(section);
		map.get(7).set(1, ' ');
		map.get(7).set(2, ' ');
		map.get(7).set(3, ' ');
		map.get(8).set(1, ' ');
		map.get(8).set(2, ' ');
		map.get(8).set(3, ' ');
	}
	
	private void setSection6() {
		if (NoSection6) {
			sections.add(null);
			return;
		}
		int poX = 5;
		if (NoSection5 || sections.get(4).getOrientation() == DOWN) poX = 4;
		
		Random rand = new Random();
		//int orientation = rand.nextInt(2);
		
		Section section = new Section(new Position(poX, 1), RIGHT);
		sections.add(section);
			map.get(7).set(poX, ' ');
			map.get(7).set(poX + 1, ' ');
			map.get(7).set(poX + 2, ' ');
			map.get(8).set(poX, ' ');
			map.get(8).set(poX + 1, ' ');
			map.get(8).set(poX + 2, ' ');
	}
	
	private void setPath() {
		for (int sectCounter = 0; sectCounter < 4; sectCounter += 2) {
			if (sections.get(sectCounter + 2) == null) break;
			
			int poX = 0;
			int poY = 0;
			if (sections.get(sectCounter).getOrientation() == RIGHT) {
				if (sections.get(sectCounter + 2).getOrientation() == RIGHT) {
					Random rand = new Random();
					poX = rand.nextInt(2) + 2;
					poY = sections.get(sectCounter).getPosition().getY() + 2;
					map.get(poY).set(poX, ' ');
					vertiPath.add(new Position(poX, poY));
				} else {
					poX = 2;
					poY = sections.get(sectCounter).getPosition().getY() + 2;
					map.get(poY).set(poX, ' ');
					vertiPath.add(new Position(poX, poY));
				}
			} else if (sections.get(sectCounter).getOrientation() == DOWN) {
				poX = 2;
				poY = sections.get(sectCounter).getPosition().getY() + 3;
				map.get(poY).set(poX, ' ');
				vertiPath.add(new Position(poX, poY));
			}
		}
		
		for (int sectCounter = 0; sectCounter < 6; sectCounter += 2) {
			if (sections.get(sectCounter) == null) break;
			Section left = sections.get(sectCounter);
			for (int rightSectCounter = 1; rightSectCounter < 6; rightSectCounter += 2) {
				if (sections.get(rightSectCounter) == null) break;
				Section right = sections.get(rightSectCounter);
				if (left.getOrientation() == RIGHT && right.getOrientation() == RIGHT) {
					if (left.getPosition().getY() == right.getPosition().getY() + 1) {
						int poX = 4;
						int poY = left.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY()) {
						Random rand = new Random();
						int poX = 4;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 1) poY ++;
						if (poY == 8) poY --;
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 1) {
						int poX = 4;
						int poY = right.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					}
				} else if (left.getOrientation() == RIGHT && right.getOrientation() == DOWN) {
					if (left.getPosition().getY() == right.getPosition().getY() + 2) {
						int poX = 4;
						int poY = left.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() + 1) {
						Random rand = new Random();
						int poX = 4;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 8) poY --;//TODO
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY()) {
						Random rand = new Random();
						int poX = 4;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 1) poY ++;
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 1) {
						int poX = 4;
						int poY = right.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					}
				} else if (left.getOrientation() == DOWN && right.getOrientation() == RIGHT) {
					if (left.getPosition().getY() == right.getPosition().getY() + 1) {
						int poX = 3;
						int poY = left.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY()) {
						Random rand = new Random();
						int poX = 3;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 1) poY ++;
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 1) {
						Random rand = new Random();
						int poX = 3;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 8) poY --;
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 2) {
						int poX = 3;
						int poY = right.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					}
				} else if (left.getOrientation() == DOWN && right.getOrientation() == DOWN) {
					if (left.getPosition().getY() == right.getPosition().getY() + 2) {
						int poX = 3;
						int poY = left.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() + 1) {
						Random rand = new Random();
						int poX = 3;
						int poY = left.getPosition().getY() + rand.nextInt(3);
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY()) {
						Random rand = new Random();
						int poX = 3;
						int poY = left.getPosition().getY() + rand.nextInt(2);
						if (poY == 1) poY ++;
						if (poY == 8) poY --;
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 1) {
						Random rand = new Random();
						int poX = 3;
						int poY = right.getPosition().getY() + rand.nextInt(2);
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					} else if (left.getPosition().getY() == right.getPosition().getY() - 2) {
						int poX = 3;
						int poY = right.getPosition().getY();
						map.get(poY).set(poX, ' ');
						horizPath.add(new Position(poX, poY));
					}
				}
			}
		}
		
		for (int sectCounter = 1; sectCounter < 4; sectCounter += 2) {
			if (sections.get(sectCounter + 2) == null) break;
			Section up = sections.get(sectCounter);
			Section down = sections.get(sectCounter + 2);
			int poY = 3;
			if (sectCounter == 3) poY = 6; 
			if (sections.get(sectCounter).getOrientation() == RIGHT) {
				if (sections.get(sectCounter + 2).getOrientation() == RIGHT) {
					if (up.getPosition().getX() == down.getPosition().getX() + 1) {
						int poX = up.getPosition().getX();
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					} else if (up.getPosition().getX() == down.getPosition().getX()) {
						Random rand = new Random();
						int poX = up.getPosition().getX() + rand.nextInt(2);
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					} else if (up.getPosition().getX() == down.getPosition().getX() - 1) {
						int poX = down.getPosition().getX();
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					}
				} else if (sections.get(sectCounter + 2).getOrientation() == DOWN){
					if (up.getPosition().getX() == down.getPosition().getX() + 1) {
						int poX = up.getPosition().getX();
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					} else if (up.getPosition().getX() == down.getPosition().getX()) {
						int poX = up.getPosition().getX();
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					} else if (up.getPosition().getX() == down.getPosition().getX() - 1) {
						int poX = down.getPosition().getX();
						map.get(poY).set(poX, ' ');
						vertiPath.add(new Position(poX, poY));
					}
				}
			} else if (sections.get(sectCounter).getOrientation() == DOWN) {
				if (up.getPosition().getX() == down.getPosition().getX() + 1) {
					int poX = up.getPosition().getX();
					map.get(poY + 1).set(poX, ' ');
					vertiPath.add(new Position(poX, poY));
				} else if (up.getPosition().getX() == down.getPosition().getX()) {
					int poX = up.getPosition().getX();
					map.get(poY + 1).set(poX, ' ');
					vertiPath.add(new Position(poX, poY));
				} else if (up.getPosition().getX() == down.getPosition().getX() - 1) {
					int poX = down.getPosition().getX();
					map.get(poY + 1).set(poX, ' ');
					vertiPath.add(new Position(poX, poY));
				}
			}
		}
	}
	
	public void setBox () {
		int lastSection = 3;
		if (NoSection5 && !NoSection6) lastSection = 5;
		if (!NoSection5 && NoSection6) lastSection = 4;
		if (!NoSection5 && !NoSection6) lastSection = 5; 
		for (Position p : vertiPath) {
			for (int count = 0; map.get(p.getY() - count).get(p.getX()) != '|'; count ++) {
				int poX = p.getX();
				int poY = p.getY() - count;
				
				//if (poX == p.getX() && poY == p.getY()) continue;
				
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				
				if (sections.get(lastSection).getOrientation() == DOWN) {
					if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
				} else if (sections.get(lastSection).getOrientation() == RIGHT) {
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY()) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				}
				
				if (poY == 1) continue;
				
				int surroundWall = 0;
				if (map.get(poY - 1).get(poX) == '|') surroundWall ++;
				if (map.get(poY).get(poX + 1) == '|') surroundWall ++;
				if (map.get(poY).get(poX - 1) == '|') surroundWall ++;
				if (surroundWall > 1) continue;
				
				boxes.add(new Position(poX, poY));
				map.get(poY).set(poX, '.');
				
			}
			
			for (int count = 0; map.get(p.getY() + count).get(p.getX()) != '|'; count ++) {
				int poX = p.getX();
				int poY = p.getY() + count;
				
				//if (poX == p.getX() && poY == p.getY()) continue;
				
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				
				if (sections.get(lastSection).getOrientation() == DOWN) {
					if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
				} else if (sections.get(lastSection).getOrientation() == RIGHT) {
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY()) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				}
				
				if (lastSection == 3) {
					if (poY == sections.get(2).getPosition().getY() + 1 + sections.get(2).getOrientation()) continue;
				} else {
					if (poY == 8) continue;
				}
				
				int surroundWall = 0;
				if (map.get(poY + 1).get(poX) == '|') surroundWall ++;
				if (map.get(poY).get(poX + 1) == '|') surroundWall ++;
				if (map.get(poY).get(poX - 1) == '|') surroundWall ++;
				if (surroundWall > 1) continue;
				
				boxes.add(new Position(poX, poY));
				map.get(poY).set(poX, '.');
			}
		}
		
		for (Position p : horizPath) {
			for (int count = 0; map.get(p.getY()).get(p.getX() - count) != '|'; count ++) {
				int poX = p.getX() - count;
				int poY = p.getY();
				
				//if (poX == p.getX() && poY == p.getY()) continue;
				
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				
				if (sections.get(lastSection).getOrientation() == DOWN) {
					if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
				} else if (sections.get(lastSection).getOrientation() == RIGHT) {
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY()) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				}
				
				if (poX == 1) continue;
				
				int surroundWall = 0;
				if (map.get(poY).get(poX - 1) == '|') surroundWall ++;
				if (map.get(poY + 1).get(poX) == '|') surroundWall ++;
				if (map.get(poY - 1).get(poX) == '|') surroundWall ++;
				if (surroundWall > 1) continue;
				
				boxes.add(new Position(poX, poY));
				map.get(poY).set(poX, '.');
			}
			
			for (int count = 0; map.get(p.getY()).get(p.getX() + count) != '|'; count ++) {
				int poX = p.getX() + count;
				int poY = p.getY();
				
				//if (poX == p.getX() && poY == p.getY()) continue;
				
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY()) continue;
				if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				
				if (sections.get(lastSection).getOrientation() == DOWN) {
					if (poX == sections.get(lastSection).getPosition().getX() && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 1 && poY == sections.get(lastSection).getPosition().getY() + 2) continue;
				} else if (sections.get(lastSection).getOrientation() == RIGHT) {
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY()) continue;
					if (poX == sections.get(lastSection).getPosition().getX() + 2 && poY == sections.get(lastSection).getPosition().getY() + 1) continue;
				}
				
				boolean cont = false;
				for (int i = 1; i < 6; i ++) {
					if (sections.get(i) == null) break;
					if (sections.get(i).getOrientation() == RIGHT) {
						if (poX == sections.get(i).getPosition().getX() + 2) {
							if (poY == sections.get(i).getPosition().getY()) cont = true;
							if (poY == sections.get(i).getPosition().getY() + 1) cont = true;
						}
					} else if (sections.get(i).getOrientation() == DOWN) {
						if (poX == sections.get(i).getPosition().getX() + 1) {
							if (poY == sections.get(i).getPosition().getY()) cont = true;
							if (poY == sections.get(i).getPosition().getY() + 1) cont = true;
							if (poY == sections.get(i).getPosition().getY() + 2) cont = true;
							
						}
					}
				}
				if (cont == true) continue;
				
				
				int surroundWall = 0;
				if (map.get(poY).get(poX + 1) == '|') surroundWall ++;
				if (map.get(poY + 1).get(poX) == '|') surroundWall ++;
				if (map.get(poY - 1).get(poX) == '|') surroundWall ++;
				if (surroundWall > 1) continue;
				
				boxes.add(new Position(poX, poY));
				map.get(poY).set(poX, '.');
			}
		}
		
		
		for (int i = 0; i < boxes.size(); i ++) {
			for (int j = i + 1; j < boxes.size(); j ++) {
				if (boxes.get(i) == boxes.get(j)) {
					boxes.remove(j);
					j --;
				}
			}
		}
		
		for (int i = 0; i < 4; i ++) {
			Section section = sections.get(i);
			int minX = section.getPosition().getX();
			int minY = section.getPosition().getY();
			int maxX = minX + 1;
			int maxY = minY + 1;
			if (section.getOrientation() == RIGHT) {
				maxX ++;
			} else if (section.getOrientation() == DOWN) {
				maxY ++;
			}
			ArrayList<Position> box = new ArrayList<Position> ();
			
			boolean hasBox = false;
			for (int x = minX; x <= maxX; x ++) {
				for (int y = minY ; y <= maxY; y ++) {
					if (map.get(y).get(x).equals('.')) {
						box.add(new Position (x, y));
						hasBox = true;
					}
				}
			}
			if (!hasBox) box.add(null);
			boxOnSect.add(box);
		}
		
		for (int i = 0; i < boxOnSect.size() - 1; i ++) {
			if (boxOnSect.get(i).get(0) == null) continue;
			ArrayList<Position> box = new ArrayList<Position> ();
			for (int j = 0; j < boxOnSect.get(i).size(); j ++) {
				int poX = boxOnSect.get(i).get(j).getX();
				int poY = boxOnSect.get(i).get(j).getY();
				if (!map.get(poY).get(poX + 2).equals('.') &&
					!map.get(poY).get(poX - 2).equals('.') &&
					!map.get(poY + 2).get(poX).equals('.') &&
					!map.get(poY - 2).get(poX).equals('.')) {
					box.add(new Position (poX, poY));
				}
			}
			Random rand = new Random ();
			int randBox = rand.nextInt(box.size());
			map.get(box.get(randBox).getY()).set(box.get(randBox).getX(), '.');
		}
		
		for (int i = 0; i < boardSize; i ++) {
			for (int j = 0; j < boardSize; j ++) {
				if (map.get(i).get(j).equals('.')) map.get(i).set(j, ' ');
			}
		}
	}
	
	public void setGoals() {
		int lastSection = 3;
		if (NoSection5 && !NoSection6) lastSection = 5;
		if (!NoSection5 && NoSection6) lastSection = 4;
		if (!NoSection5 && !NoSection6) lastSection = 5;
		int poX = sections.get(lastSection).getPosition().getX();
		int poY = sections.get(lastSection).getPosition().getY();
		if (sections.get(lastSection).getOrientation() == RIGHT) {
			poY ++;
		} else if (sections.get(lastSection).getOrientation() == DOWN) {
			poY += 2;
		}
		
		map.get(poY).set(poX, 'x');
		map.get(poY).set(poX + 1, 'x');
		
		if (sections.get(lastSection).getOrientation() == RIGHT) {
			map.get(poY).set(poX + 2, 'x');
		} else if (sections.get(lastSection).getOrientation() == DOWN) {
			map.get(poY - 1).set(poX + 1, 'x');
		}
	}
	
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

	/**
	 * General solution of generating sections
	 * in case a large amount sections are needed
	 * *not working yet
	 */
	private void setSections () {
		int sectionCounter = 0;
		int poX = 1;
		int leftPoY = 1;
		int rightPoY = 1;
		for (int i = 0; i < 3; i ++) {
			for (int j = 0; j < 2; j ++) {
				int poY = leftPoY;
				if (j == 1) poY = rightPoY;
				
				map.get(poY).set(poX, ' ');
				map.get(poY).set(poX + 1, ' ');
				map.get(poY + 1).set(poX, ' ');
				map.get(poY + 1).set(poX + 1, ' ');
				
				Random rand = new Random();
				int orientation = rand.nextInt(2);
				sections.add(new Section(new Position(poX, poY), orientation));
				
				if (orientation == RIGHT) {
					map.get(poY).set(poX + 2, ' ');
					map.get(poY + 1).set(poX + 2, ' ');
					
					poX = 5;
					poY += 3;
				} else if (orientation == DOWN) {
					map.get(poY + 2).set(poX, ' ');
					map.get(poY + 2).set(poX + 1, ' ');
					
					poX = 4;
					poY += 4;
				}
				
				if (j == 0) {
					leftPoY = poY;
				} else if (j == 1) {
					rightPoY = poY;
				}
				sectionCounter ++;
				break;
			}
			poX = 1;
			break;
			
		}
	}
}
