package comp2911.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp2911.Constants;

/**
 * @author Jeffrey Ung
 */
public class Score {
	
	/**
	 * The scores of the current level in the format "<score>\t<username>".
	 */
	private List<String> scores;
	
	/**
	 * The level.
	 */
	private int level;
	
	/**
	 * Constructs a new Score object.
	 */
	public Score(int level) {
		this.scores = new ArrayList<String>(Constants.SCOREBOARD_SIZE);
		this.level = level;
	}

	/**
	 * The file corresponding to the level is being read and the information
	 * is added onto the score map.
	 * This is to be called before beginning a level.
	 */
	public void readScoreData() {
		BufferedReader reader = null;

		try {
			File file = new File(Constants.SCORES_DIR + "/" + level + ".txt");
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				scores.add(line);
			}
		} catch(IOException e) {
		} finally {
			try {
				reader.close();
			} catch(IOException e) {
			}
		}
	}

	/**
	 * The file corresponding to the level is to be re-written to what is
	 * in the scores map in descending order of scores.
	 * This is to be called after a level is completed.
	 * @param username of the player.
	 * @param score of the player has got.
	 */
	public void writeScoreData(String username, int score) {
		scores.add(score + "\t" + username);
		Collections.sort(scores, Collections.reverseOrder());
		BufferedWriter writer = null;
		try {
			File file = new File(Constants.SCORES_DIR + "/" + level + ".txt");
			if (!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			for (String s : scores) {
				writer.write(s);
			}
		} catch(IOException e) {
		} finally { 
			try {
				writer.close();
			} catch(Exception e) {
			}
		}
	}
	
}
