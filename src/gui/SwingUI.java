package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class SwingUI extends JFrame {

	public final static String GAME_NAME = "COMP2911 Project";
	
	private JPanel panel;
	
	public SwingUI() {
		this.setPanel(new StartPanel(this));
		this.init();
	}
	
	public void init() {
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			JFrame.setDefaultLookAndFeelDecorated(true);
			this.setLayout(new BorderLayout());
			this.setTitle(GAME_NAME);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(800, 500));
			this.getPanel().setLayout(new GridLayout(4, 0));
			this.getContentPane().add(this.getPanel());
			this.pack();
			this.setVisible(true);
			this.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
}
