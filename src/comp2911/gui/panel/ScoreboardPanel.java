package comp2911.gui.panel;

import javax.swing.JPanel;

import comp2911.gui.SwingUI;
import java.util.ArrayList;
import java.util.PriorityQueue;

import comp2911.game.Score;
import comp2911.game.Player;
import javax.swing.JLabel;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class ScoreboardPanel extends JPanel {

	
	public ScoreboardPanel(SwingUI swingUI, PriorityQueue<String> scores) {
		// TODO Auto-generated constructor stub
		
		PriorityQueue<String> topScores = scores;
		
		int countTopFive = 5;
		String whole = null;
		
		while(countTopFive > 0 || !scores.isEmpty()) {
			String newScore = null;
			newScore = topScores.poll();
			String[] splitLine = newScore.split("\t");

			String name = splitLine[0];
			whole = whole + name + ' ';
			String score = splitLine[1];
			whole = whole + score + '\n';
		}
		JLabel label = new JLabel(whole);
		this.add(label);
	}
	
	

}
