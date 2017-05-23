package comp2911.gui.panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

	/**
	 * The swing UI.
	 */
	private SwingUI swingUI;
	
	/**
	 * The current score text".
	 */
	private JLabel label;
	
	/**
	 * Constructs the score panel.
	 */
	public ScorePanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.label = new JLabel();
		this.add(label);
	}
	
	/**
	 * Updates the score panel.
	 */
	public void updateScore(int score) {
		label.setText("<html><font color=\"white\"> Score: " + score + "</font></html>");
	}
	
}
