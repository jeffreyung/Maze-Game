package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		this.setBackground(SwingUI.DEFAULT_COLOR);
		init();
	}

	/**
	 * Initializes the start screen.
	 */
	public void init() {
		this.addButton("Start", "start_game");
		this.addButton("Walkthrough", "rules");
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
			this.swingUI.switchPanel(new GameOptionPanel(this.swingUI));
			break;
		case "rules":
			this.swingUI.switchPanel(new RulesPanel(this.swingUI));
			break;
		case "scoreboard":
			this.swingUI.switchPanel(new ScoreBoardPanel(this.swingUI));
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
	 * Adds a button to the buttons list.
	 * @param name of the button to be added.
	 */
	private void addButton(String name, String command) {
		JButton button = new JButton();
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2.png");
		Image newimg1 = image1.getScaledInstance(420, 100, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(420, 100, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		button.setText(name);
		button.setActionCommand(command);
		this.add(button);
		button.setIcon(imgIcon1);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				button.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		this.buttons.add(button);
	}
}
