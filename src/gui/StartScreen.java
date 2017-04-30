package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartScreen extends JFrame {

	public final static String GAME_NAME = "COMP2911 Project";
	private List<JButton> buttons;
	private JPanel panel;
	
	/**
	 * The constructor of the StartScreen class
	 */
	public StartScreen() {
		panel = new JPanel();
		JFrame.setDefaultLookAndFeelDecorated(true);
		buttons = new ArrayList<JButton>();
		this.setLayout(new BorderLayout());
		this.setTitle(GAME_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(800, 500));
		this.panel.setLayout(new GridLayout(4, 0));
		this.getContentPane().add(this.panel);
		this.init();
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * Initializes the start screen
	 */
	public void init() {
		this.addButton("Start", "start_game");
		this.addButton("Rules", "rules");
		this.addButton("Scoreboard", "scoreboard");
		this.addButton("Exit", "exit");
		for (JButton button : buttons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					handleButton(event.getActionCommand());
				}
			});
		}
	}
	
	/**
	 * Handles the buttons selected by the user
	 * @param name of the button
	 */
	public void handleButton(String name) {
		switch (name) {
		case "start_game":
			System.out.println("start");
			break;
		case "rules":
			System.out.println("rules");
			break;
		case "scoreboard":
			System.out.println("scoreboard");
			break;
		case "exit":
			System.exit(0);
			break;
		}
	}
	
	/**
	 * Adds a button to the buttons list
	 * @param name of the button to be added
	 */
	private void addButton(String name, String command) {
		JButton button = new JButton();
		button.setText(name);
		button.setActionCommand(command);
		this.panel.add(button);
		this.buttons.add(button);
	}
}
