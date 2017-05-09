package comp2911.gui.panel;

import java.awt.event.KeyListener;

import javax.swing.JPanel;

import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * User input.
	 */
	private KeyListener userInput;
	
	/**
	 * The constructor of the StartScreen class
	 * @param swingUI 
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.userInput = new UserInput(this.swingUI.getGameEngine());
		this.init();
		this.setVisible(true);
	}

	/**
	 * Initializes the start screen
	 */
	public void init() {
		this.addKeyListener(this.userInput);
		this.swingUI.addKeyListener(this.userInput);
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.swingUI.requestFocusInWindow();
	}

}
