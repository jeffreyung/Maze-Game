package comp2911.gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import comp2911.GameEngine;
import comp2911.game.Direction;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
public class UserInput implements KeyListener {
	
	/**
	 * Swing user input.
	 */
	private GameEngine gameEngine;
	
	/**
	 * Constructs an instance for user input.
	 * @param swingUI
	 */
	public UserInput(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
	}
	
	/**
	 * The key typed.
	 * @param e is the event.
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * The key pressed.
	 * @param e is the event.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			this.gameEngine.sendUserDirection(0, Direction.UP);
			break;
		case KeyEvent.VK_A:
			this.gameEngine.sendUserDirection(0, Direction.LEFT);
			break;
		case KeyEvent.VK_S:
			this.gameEngine.sendUserDirection(0, Direction.DOWN);
			break;
		case KeyEvent.VK_D:
			this.gameEngine.sendUserDirection(0, Direction.RIGHT);
			break;
		//default:
			//Logger.getLogger(UserInput.class.getName()).info("Unhandled: " + KeyEvent.getKeyText(e.getKeyCode()));
		}
		
	}

	/**
	 * The key released.
	 * @param e is the event.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}