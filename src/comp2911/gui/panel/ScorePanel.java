package comp2911.gui.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

	/**
	 * The current score text".
	 */
	private JLabel label;
	
	/**
	 * Constructs the score panel.
	 */
	public ScorePanel() {
		this.label = new JLabel();
		this.setBackground(Color.BLACK);
		this.add(label);
	}
	
	/**
	 * Updates the score panel.
	 */
	public void updateScore(int score, int hiscore) {
		label.setText("<html><font color=\"white\"> <b>Current score:</b><br>" + score + "<br>"
				+ "<br><br><b>My top score:</b><br>" + hiscore + "</font></html>");
	}
	
	/**
	 * Updates the score panel by removing the text.
	 */
	public void clear() {
		label.setText("");
	}
	
}
