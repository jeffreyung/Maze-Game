package comp2911.gui;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;

import comp2911.Constants;
import comp2911.game.Direction;
import comp2911.gui.panel.GamePanel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
public class UserInput implements KeyListener {
	
	/**
	 * Swing user input.
	 */
	private GamePanel gamePanel;
	
	/**
	 * Constructs an instance for user input.
	 * @param swingUI
	 */
	public UserInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
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
			if (this.gamePanel.getIndex() == 0)
				this.gamePanel.getGame().sendUserDirection(0, Direction.UP);
			break;
		case KeyEvent.VK_UP:
			if (this.gamePanel.getIndex() == 1)
				this.gamePanel.getGame().sendUserDirection(1, Direction.UP);
			break;
		case KeyEvent.VK_A:
			if (this.gamePanel.getIndex() == 0)
				this.gamePanel.getGame().sendUserDirection(0, Direction.LEFT);
			break;
		case KeyEvent.VK_LEFT:
			if (this.gamePanel.getIndex() == 1)
				this.gamePanel.getGame().sendUserDirection(1, Direction.LEFT);
			break;
		case KeyEvent.VK_S:
			if (this.gamePanel.getIndex() == 0)
				this.gamePanel.getGame().sendUserDirection(0, Direction.DOWN);
			break;
		case KeyEvent.VK_DOWN:
			if (this.gamePanel.getIndex() == 1)
				this.gamePanel.getGame().sendUserDirection(1, Direction.DOWN);
			break;
		case KeyEvent.VK_D:
			if (this.gamePanel.getIndex() == 0)
				this.gamePanel.getGame().sendUserDirection(0, Direction.RIGHT);
			break;
		case KeyEvent.VK_RIGHT:
			if (this.gamePanel.getIndex() == 1)
				this.gamePanel.getGame().sendUserDirection(1, Direction.RIGHT);
			break;
		case KeyEvent.VK_ESCAPE:
			this.gamePanel.getGame().pause();
			break;
		default:
			if (Constants.DEBUG_MODE)
				Logger.getLogger(UserInput.class.getName()).info("Unhandled: " + KeyEvent.getKeyText(e.getKeyCode()));
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