package comp2911.gui.panel;

import java.awt.event.KeyListener;

import javax.swing.JPanel;

import comp2911.GameEngine;
import comp2911.gui.SwingUI;
import comp2911.gui.UserInput;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	/**
	 * The swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The user input.
	 */
	private KeyListener userInput;

	/**
	 * The game engine.
	 */
	private GameEngine gameEngine;
	
	/**
	 * The constructor of the StartScreen class
	 * @param swingUI 
	 */
	public GamePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.gameEngine = new GameEngine(this.swingUI);
		this.userInput = new UserInput(this.gameEngine);
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
