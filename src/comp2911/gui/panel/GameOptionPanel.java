package comp2911.gui.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		this.setBackground(SwingUI.DEFAULT_COLOR);
		this.init();
	}
	
	/**
	 * Initializes the enter user panel.
	 */
	public void init() {
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2.png");
		Image newimg1 = image1.getScaledInstance(420, 60, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(420, 60, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
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
		onePlayer.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				onePlayer.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				onePlayer.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		twoPlayer.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				onePlayer.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				onePlayer.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		onePlayer.setIcon(new ImageIcon(newimg1));
		twoPlayer.setIcon(new ImageIcon(newimg1));
		onePlayer.setHorizontalTextPosition(SwingConstants.CENTER);
		twoPlayer.setHorizontalTextPosition(SwingConstants.CENTER);
		onePlayer.setBorderPainted(false);
		twoPlayer.setBorderPainted(false);
		this.add(label);
		this.add(this.onePlayer);
		this.add(this.twoPlayer);
	}
}
