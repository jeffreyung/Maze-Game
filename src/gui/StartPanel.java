package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gui.panel.GamePanel;

@SuppressWarnings("serial")
public class StartPanel extends JPanel {

	private SwingUI swingUI;
	private List<JButton> buttons;
	/**
	 * The constructor of the StartScreen class
	 */
	public StartPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		JFrame.setDefaultLookAndFeelDecorated(true);
		buttons = new ArrayList<JButton>();
		this.setLayout(new BorderLayout());
		init();
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
			swingUI.getContentPane().remove(this);
			swingUI.setPanel(new GamePanel());
			swingUI.init();
			this.repaint();
			this.revalidate();
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
		this.add(button);
		this.buttons.add(button);
	}
}
