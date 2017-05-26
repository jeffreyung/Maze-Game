package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import comp2911.GameEngine;
import comp2911.game.Game;
import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class EnterUserPanel extends JPanel {

	/**
	 * The swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * The username text field for player 1.
	 */
	private JTextField username1;
	
	/**
	 * The username text field for player 2.
	 */
	private JTextField username2;
	
	/**
	 * The button to set the name.
	 */
	private JButton enter;
	
	/**
	 * The button to return to main menu.
	 */
	private JButton exit;
	
	/**
	 * Constructs the enter user panel.
	 * @param swingUI is the swing user interface.
	 */
	public EnterUserPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.username1 = new JTextField();
		this.username2 = new JTextField();
		this.enter = new JButton("Enter");
		this.exit = new JButton("Return to main menu");
		this.setBackground(Color.BLACK);
		if (swingUI.getGameMode() == 0)
			this.initOnePlayer();
		else
			this.initTwoPlayer();
	}
	
	/**
	 * Initializes the enter user panel for one player.
	 */
	public void initOnePlayer() {
		JLabel label = new JLabel("Enter your name: ");
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		this.setLayout(new BorderLayout());
		this.username1.setColumns(10);
		swingUI.setPreferredSize(new Dimension(600, 300));
		username1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swingUI.switchPanel(getGamePanel());
			}
		});
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(getGamePanel());
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
				swingUI.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
			}
		});
		subPanel1.add(label);
		subPanel1.add(username1);
		subPanel2.add(enter);
		subPanel2.add(exit);
		this.add(subPanel1, BorderLayout.CENTER);
		this.add(subPanel2, BorderLayout.SOUTH);
	}
	
	/**
	 * Initializes the enter user panel for two player.
	 */
	public void initTwoPlayer() {
		JLabel label1 = new JLabel("Enter player one name: ");
		JLabel label2 = new JLabel("Enter player two name: ");
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		this.setLayout(new BorderLayout());
		this.username1.setColumns(10);
		this.username2.setColumns(10);
		swingUI.setPreferredSize(new Dimension(600, 300));
		username1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				swingUI.switchPanel(getGamePanel());
			}
		});
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(getGamePanel());
			}
		});
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
				swingUI.setPreferredSize(new Dimension(SwingUI.DEFAULT_WIDTH, SwingUI.DEFAULT_HEIGHT));
			}
		});
		subPanel1.add(label1);
		subPanel1.add(username1);
		subPanel1.add(label2);
		subPanel1.add(username2);
		subPanel2.add(enter);
		subPanel2.add(exit);
		this.add(subPanel1, BorderLayout.CENTER);
		this.add(subPanel2, BorderLayout.SOUTH);
	}
	
	/**
	 * Determines the game panel of the game mode.
	 * @return the panel.
	 */
	public JPanel getGamePanel() {
		List<Game> games = new ArrayList<Game>();
		GameEngine gameEngine = new GameEngine(games);
		String name1 = username1.getText();
		if (name1.equals(""))
			name1 = "default_1";
		if (swingUI.getGameMode() == 1) {
			String name2 = username2.getText();
			JPanel panel = new JPanel(new GridLayout(0, 2));
			if (name2.equals(""))
				name2 = "default_2";
			GamePanel game1 = new GamePanel(0, swingUI, gameEngine, name1);
			GamePanel game2 = new GamePanel(1, swingUI, gameEngine, name2);
			panel.add(game1);
			panel.add(game2);
			games.add(game1.getGame());
			games.add(game2.getGame());
			swingUI.setReturnPanel(panel);
			return panel;
		}
		GamePanel game = new GamePanel(0, swingUI, gameEngine, name1);
		games.add(game.getGame());
		swingUI.setReturnPanel(game);
		return game;
	}
	
}
