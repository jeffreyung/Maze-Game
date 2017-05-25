package comp2911.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp2911.gui.SwingUI;

@SuppressWarnings("serial")
public class GameOptionPanel extends JPanel {
	
	/**
	 * The swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The button for one player.
	 */
	private JButton onePlayer;
	
	/**
	 * The button for two player.
	 */
	private JButton twoPlayer;
	
	/**
	 * Constructs the enter user panel.
	 * @param swingUI is the swing user interface.
	 */
	public GameOptionPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.onePlayer = new JButton("One Player - move as many crates to the goal");
		this.twoPlayer = new JButton("Two Player - who can score the most?");
		this.setBackground(Color.BLACK);
		this.init();
	}
	
	/**
	 * Initializes the enter user panel.
	 */
	public void init() {
		JLabel label = new JLabel("<html><b><font color=\"white\" size=\"14\">Select game mode</font><b></html>");
		this.setLayout(new GridLayout(3, 0, 0, 20));
		swingUI.setPreferredSize(new Dimension(600, 400));
		onePlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swingUI.setGameMode(0);
				swingUI.switchPanel(new EnterUserPanel(swingUI));
			}
		});
		twoPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swingUI.setGameMode(1);
				swingUI.switchPanel(new EnterUserPanel(swingUI));
			}
		});
		this.add(label);
		this.add(this.onePlayer);
		this.add(this.twoPlayer);
	}
}
