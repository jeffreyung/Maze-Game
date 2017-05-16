package comp2911.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import comp2911.Constants;
import comp2911.gui.panel.StartPanel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class SwingUI extends JFrame {

	/**
	 * The current panel.
	 */
	private JPanel panel;
	
	/**
	 * The left panel i.e., the score panel.
	 */
	private JPanel scorePanel;
	
	/**
	 * The right panel.
	 */
	private JPanel rightPanel;

	/**
	 * The top panel.
	 */
	private JPanel topPanel;

	/**
	 * The bottom panel.
	 */
	private JPanel botPanel;
	
	/**
	 * Constructs the SwingUI class.
	 */
	public SwingUI() {
		this.panel = new StartPanel(this);
		this.scorePanel = new JPanel();
		this.rightPanel = new JPanel();
		this.topPanel = new JPanel();
		this.botPanel = new JPanel();
	}
	
	/**
	 * Initializes the JFrame interface.
	 */
	public void init() {
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			JFrame.setDefaultLookAndFeelDecorated(true);
			this.setLayout(new BorderLayout());
			this.setTitle(Constants.GAME_NAME);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(800, 800));
			this.getPanel().setLayout(new GridLayout(4, 0));
			this.scorePanel.setPreferredSize(new Dimension(100, 100));
			this.rightPanel.setPreferredSize(new Dimension(100, 100));
			this.topPanel.setPreferredSize(new Dimension(50, 50));
			this.botPanel.setPreferredSize(new Dimension(50, 50));
			this.scorePanel.setBackground(Color.BLACK);
			this.rightPanel.setBackground(Color.BLACK);
			this.topPanel.setBackground(Color.BLACK);
			this.botPanel.setBackground(Color.BLACK);
			this.add(scorePanel, BorderLayout.WEST);
			this.add(rightPanel, BorderLayout.EAST);
			this.add(topPanel, BorderLayout.NORTH);
			this.add(botPanel, BorderLayout.SOUTH);
			this.switchPanel(this.getPanel());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches the panel.
	 * @param panel that is being switched to.
	 */
	public void switchPanel(JPanel panel) {
		this.setResizable(true);
		this.getContentPane().remove(this.getPanel());
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.setPanel(panel);
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setResizable(false);
		this.pack();
	}

	/**
	 * @return JPanel the current panel viewed by the user.
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Sets the panel to be viewed by the user.
	 * @param panel to be viewed.
	 */
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	/**
	 * Updates the current interface of the panel.
	 * The current panel should be the game panel.
	 */
	public void updateInterface() {
		this.repaint();
		this.getPanel().repaint();
	}
	
}
