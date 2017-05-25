package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class GameOverPanel extends JPanel {

	/**
	 * The swing UI.
	 */
	private SwingUI swingUI;
	
	/**
	 * The panel to return to.
	 */
	private GamePanel gamePanel;
	
	/**
	 * Constructs the pause panel.
	 * @param gamePanel is the panel to return to.
	 */
	public GameOverPanel(SwingUI swingUI, GamePanel gamePanel) {
		this.setLayout(new BorderLayout());
		this.swingUI = swingUI;
		this.gamePanel = gamePanel;
		this.setBackground(Color.BLACK);
	}
	
	/**
	 * Initializes the top score board.
	 */
	public void init() {
		JLabel text = new JLabel("<html><b><font color=\"white\" size=\"14\">Game over</font><b></html>");
		JButton button1 = new JButton("Restart game");
		JButton button2 = new JButton("Return to main menu");
		JPanel subPanel = new JPanel(new GridLayout(3, 0, 0, 50));
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				gamePanel.getGame().restartGame();
				swingUI.switchPanel(gamePanel);
				swingUI.requestFocusInWindow();
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
			}
		});
		subPanel.setBackground(Color.BLACK);
		subPanel.add(text);
		if (swingUI.getGameMode() == 0)
			subPanel.add(button1);
		else {
			if (gamePanel.getGameEngine().getWinner() == null)
				subPanel.add(new JLabel("<html><font color=\"white\">No winner</font></html>"));
			else
				subPanel.add(new JLabel("<html><font color=\"white\">Winner is " +
					gamePanel.getGameEngine().getWinner().getUsername() + "</font></html>"));
		}
		subPanel.add(button2);
		this.add(subPanel);
	}

}