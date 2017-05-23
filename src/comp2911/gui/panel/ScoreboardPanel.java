package comp2911.gui.panel;

import javax.swing.JPanel;

import comp2911.game.ScoreData;
import comp2911.game.ScoreHandler;
import comp2911.gui.SwingUI;
import java.util.PriorityQueue;

import javax.swing.JLabel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class ScoreboardPanel extends JPanel {

	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The score handler.
	 */
	private ScoreHandler score;
	
	/**
	 * Constructs the score board panel.
	 * @param swingUI
	 */
	public ScoreboardPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.score = new ScoreHandler();
		this.score.readScoreData();
		this.init();
	}
	
	/**
	 * Initializes the top score board.
	 */
	private void init() {
		PriorityQueue<ScoreData> topScores = score.getScores();
		int count = 1;
		String whole = "<html><b><font size=\"14\" color=\"black\"> Top Five Scores<font></b><br><br>";
		while(count <= 5 && !topScores.isEmpty()) {
			ScoreData s = topScores.poll();
			whole += count + ".&nbsp;&nbsp;&nbsp;&nbsp;" + s.getScore() + "&nbsp;&nbsp;" + s.getUsername()+ "<br>";
			count++;
		}
		whole += "</html>";
		this.add(new JLabel(whole));
	}
	

}
