package comp2911.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 * @author Jeffrey Ung
 */
public class ScoreHandler {
	
	/**
	 * The scores list.
	 */
	private PriorityQueue<ScoreData> scores;
	
	/**
	 * Constructs a new Score handler object.
	 */
	public ScoreHandler() {
		this.scores = new PriorityQueue<ScoreData>();
	}

	/**
	 * The file corresponding to the level is being read and the information
	 * is added onto the score map.
	 * This is to be called before beginning a level.
	 */
	public void readScoreData() {
		BufferedReader reader = null;

		try {
			File file = new File("./data/score/scoreboard.txt");
			if (!file.exists())
				file.createNewFile();
			reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				String[] split = line.split("\t");
				scores.add(new ScoreData(split[1], Integer.parseInt(split[0])));
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
	 * The file is to be re-written to what is on the score list.
	 */
	public void writeScoreData() {
		BufferedWriter writer = null;
		try {
			File file = new File("./data/score/scoreboard.txt");
			if(!file.exists())
				file.createNewFile();
			FileWriter fw = new FileWriter(file);
			writer = new BufferedWriter(fw);
			for (ScoreData s : scores)
				writer.write(s.getScore() + "\t" + s.getUsername() + "\n");
		} catch(IOException e) {
		} finally { 
			try {
				writer.close();
			} catch(IOException e) {
			}
		}
	}
	
	/**
	 * Updates the scores list if the player scores a high score than previous.
	 * @param username of the player.
	 * @param score of the player has got.
	 */
	public void updateTopScore(String username, int score) {
		for (ScoreData s : scores) {
			if (s.getUsername().equals(username) && score > s.getScore()) {
				s.setScore(score);
				this.writeScoreData();
			}
		}
	}
	
	/**
	 * Adds a new player if they are not on the score board list.
	 */
	public void addNewScore(String username) {
		for (ScoreData s : scores)
			if (s.getUsername().equals(username))
				return;
		scores.add(new ScoreData(username, 0));
	}
	
	/**
	 * @return the score list.
	 */
	public PriorityQueue<ScoreData> getScores() {
		return scores;
	}
}
