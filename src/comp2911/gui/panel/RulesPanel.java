package comp2911.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import comp2911.gui.SwingUI;

/**
 * @author Jeffrey Ung and Jamison Tsai
 */
@SuppressWarnings("serial")
public class RulesPanel extends JPanel {

	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * Constructs the rules panel.
	 * @param swingUI
	 */
	public RulesPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		this.setBackground(SwingUI.DEFAULT_COLOR);
		swingUI.setPreferredSize(new Dimension(800, 900));
		this.init();
	}
	
	/**
	 * Initializes the top score board.
	 */
	private void init() {
		JButton exit = new JButton("Return to main menu");
		ImageIcon image = new ImageIcon("./data/img/walkthrough_1.jpg");
		JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add( label, BorderLayout.CENTER );
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
			}
		});
		this.add(label, BorderLayout.NORTH);
		this.add(exit, BorderLayout.SOUTH);
	}

}
