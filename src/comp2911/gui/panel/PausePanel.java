package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class PausePanel extends JPanel {

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
	public PausePanel(SwingUI swingUI, GamePanel gamePanel) {
		this.setLayout(new BorderLayout());
		this.swingUI = swingUI;
		this.gamePanel = gamePanel;
		this.setBackground(Color.BLACK);
		this.init();
	}
	
	/**
	 * Initializes the top score board.
	 */
	private void init() {
		JButton button1 = new JButton("Return to game");
		JButton button2 = new JButton("Return to main menu");
		JPanel subPanel = new JPanel(new GridLayout(2, 0, 0, 50));
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				switchPanel();
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
			}
		});
		subPanel.setBackground(Color.BLACK);
		subPanel.add(button1);
		subPanel.add(button2);
		this.add(subPanel);
	}
	
	/**
	 * Return back to game panel.
	 */
	public void switchPanel() {
		this.gamePanel.getGame().setPause(false);
		this.swingUI.switchPanel(gamePanel);
		this.swingUI.requestFocusInWindow();
	}
}
