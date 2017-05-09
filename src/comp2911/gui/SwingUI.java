package comp2911.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import comp2911.Constants;
import comp2911.GameEngine;
import comp2911.gui.panel.StartPanel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class SwingUI extends JFrame {
	
	private GameEngine gameEngine;
	private JPanel panel;
	
	/**
	 * Constructs the SwingUI class
	 */
	public SwingUI(GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.panel = new StartPanel(this);
		this.init();
	}
	
	/**
	 * Initializes the JFrame
	 */
	public void init() {
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			JFrame.setDefaultLookAndFeelDecorated(true);
			this.setLayout(new BorderLayout());
			this.setTitle(Constants.GAME_NAME);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(600, 600));
			this.getPanel().setLayout(new GridLayout(4, 0));
			this.getContentPane().add(this.getPanel());
			this.pack();
			this.setVisible(true);
			this.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return JPanel the current panel viewed by the user
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Sets the panel to be viewed by the user
	 * @param panel to be viewed
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	/**
	 * @return GameEngine the game engine
	 */
	public GameEngine getGameEngine() {
		return gameEngine;
	}
	
}
