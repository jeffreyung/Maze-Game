package comp2911.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import comp2911.Constants;
import comp2911.gui.panel.ScorePanel;
import comp2911.gui.panel.StartPanel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class SwingUI extends JFrame {
	
	/**
	 * The default width.
	 */
	public final static int DEFAULT_WIDTH = 600;
	
	/**
	 * The default height.
	 */
	public final static int DEFAULT_HEIGHT = 600;
	
	/**
	 * The default color.
	 */
	public final static Color DEFAULT_COLOR = Color.decode("0x404040");
	
	/**
	 * The menu panel.
	 */
	private JPanel startPanel;
	
	/**
	 * The current panel.
	 */
	private JPanel panel;
	
	/**
	 * The left panel i.e., the score panel not scoreboard panel.
	 */
	private ScorePanel scorePanel;
	
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
	 * The panel used for pausingto return.
	 */
	private JPanel returnPanel;
	
	/**
	 * If game has started.
	 */
	private boolean gameStart;
	
	/**
	 * The game mode.
	 */
	private int gameMode;
	
	
	/**
	 * Constructs the SwingUI class.
	 */
	public SwingUI() {
		this.panel = new StartPanel(this);
		this.scorePanel = new ScorePanel();
		this.rightPanel = new JPanel();
		this.topPanel = new JPanel();
		this.botPanel = new JPanel();
		this.setStartPanel(panel);
		this.setGameStart(false);
	}
	
	/**
	 * Initializes the JFrame interface.
	 */
	public void init() {
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			this.setLayout(new BorderLayout());
			this.setTitle(Constants.GAME_NAME);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
			this.getPanel().setLayout(new GridLayout(4, 0, 0, 20));
			this.scorePanel.setPreferredSize(new Dimension(100, 100));
			this.rightPanel.setPreferredSize(new Dimension(100, 100));
			this.topPanel.setPreferredSize(new Dimension(50, 100));
			this.botPanel.setPreferredSize(new Dimension(50, 50));
			this.scorePanel.setBackground(SwingUI.DEFAULT_COLOR);
			this.rightPanel.setBackground(SwingUI.DEFAULT_COLOR);
			this.topPanel.setBackground(SwingUI.DEFAULT_COLOR);
			this.botPanel.setBackground(SwingUI.DEFAULT_COLOR);
			this.topPanel.add(new JLabel(new ImageIcon("./data/img/logo.png")));
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
	 * @param the current score of the player.
	 */
	public void updateInterface(int score, int hiscore) {
		if (this.gameMode == 0)
			this.scorePanel.updateScore(score, hiscore);
		this.repaint();
		this.getPanel().repaint();
	}

	/**
	 * @return if the game started.
	 */
	public boolean isGameStart() {
		return gameStart;
	}

	/**
	 * Updates if the game started.
	 * @param gameStart is if the game started.
	 */
	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

	/**
	 * @return the startPanel.
	 */
	public JPanel getStartPanel() {
		scorePanel.clear();
		this.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
		return startPanel;
	}

	/**
	 * @param startPanel the startPanel to set.
	 */
	public void setStartPanel(JPanel startPanel) {
		this.startPanel = startPanel;
	}

	/**
	 * @return the game mode.
	 */
	public int getGameMode() {
		return gameMode;
	}

	/**
	 * @param gameMode the game mode to set.
	 */
	public void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * @return the return panel.
	 */
	public JPanel getReturnPanel() {
		return returnPanel;
	}

	/**
	 * @param returnPanel the return panel to set.
	 */
	public void setReturnPanel(JPanel returnPanel) {
		this.returnPanel = returnPanel;
	}
	
}
