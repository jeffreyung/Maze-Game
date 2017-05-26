package comp2911.game;

/**
 * A trapdoor section placed on the map
 * @author James Gibson Z3418942 and 
 *
 */
public class Section {
	private Position position; //top left corner of the the section box is the position
	private int orientation; //an array length 2 with the orientation of the section either 2x3 or 3x2

	public Section (Position position, int orientation) {
		this.position = position;
		this.orientation = orientation;
	}
	
	public Position getPosition () {
		return position;
	}
	
	public int getOrientation() {
		return orientation;
	}
}
