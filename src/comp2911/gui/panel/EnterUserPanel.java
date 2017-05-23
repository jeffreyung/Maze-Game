package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	 * The username text field.
	 */
	private JTextField username;
	
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
		this.username = new JTextField();
		this.enter = new JButton("Enter");
		this.exit = new JButton("Return to main menu");
		this.init();
	}
	
	/**
	 * Initializes the enter user panel.
	 */
	public void init() {
		JLabel label = new JLabel("Enter your name: ");
		JPanel subPanel1 = new JPanel();
		JPanel subPanel2 = new JPanel();
		this.setLayout(new BorderLayout());
		this.username.setColumns(8);
		swingUI.setPreferredSize(new Dimension(600, 300));
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String name = username.getText();
				if (name.equals(""))
					name = "un-named";
				swingUI.switchPanel(new GamePanel(swingUI, name));
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
		subPanel1.add(username);
		subPanel1.add(enter);
		subPanel2.add(exit);
		this.add(subPanel1, BorderLayout.CENTER);
		this.add(subPanel2, BorderLayout.SOUTH);
	}
	
}
