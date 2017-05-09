package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import comp2911.Constants;
import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel {

	/**
	 * Swing user input.
	 */
	private SwingUI swingUI;
	
	/**
	 * Buttons list.
	 */
	private List<JButton> buttons;
	
	/**
	 * The constructor of the StartScreen class.
	 */
	public StartPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		JFrame.setDefaultLookAndFeelDecorated(true);
		buttons = new ArrayList<JButton>();
		this.setLayout(new BorderLayout());
		init();
	}

	/**
	 * Initializes the start screen.
	 */
	public void init() {
		this.addButton("Start", "start_game");
		this.addButton("Rules", "rules");
		this.addButton("Scoreboard", "scoreboard");
		this.addButton("Exit", "exit");
		for(JButton button : buttons) {
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					handleButton(event.getActionCommand());
				}
			});
		}
	}
	
	/**
	 * Handles the buttons selected by the user.
	 * @param name of the button.
	 */
	public void handleButton(String name) {
		switch (name) {
		case "start_game":
			this.switchPanel(new GamePanel(this.swingUI));
			break;
		case "rules":
			this.switchPanel(new RulesPanel(this.swingUI));
			break;
		case "scoreboard":
			this.switchPanel(new ScoreboardPanel(this.swingUI));
			break;
		case "exit":
			System.exit(0);
			break;
		default:
			if(Constants.DEBUG_MODE)
				Logger.getLogger(StartPanel.class.getName()).info("Unhandled button for: " + name);
			break;
		}
	}
	
	/**
	 * Switches the panel.
	 * @param panel to be switched to.
	 */
	public void switchPanel(JPanel panel) {
		this.swingUI.getContentPane().remove(this);
		this.swingUI.setPanel(panel);
		this.swingUI.init();
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Adds a button to the buttons list.
	 * @param name of the button to be added.
	 */
	private void addButton(String name, String command) {
		JButton button = new JButton();
		button.setText(name);
		button.setActionCommand(command);
		this.add(button);
		this.buttons.add(button);
	}
}
