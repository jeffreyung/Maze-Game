package comp2911.gui.panel;

import java.awt.BorderLayout;
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

/**
 * @author Jeffrey Ung
 */
@SuppressWarnings("serial")
public class GameOverPanel extends JPanel {

	/**
	 * The swing UI.
	 */
	private SwingUI swingUI;
	
	/**
	 * The panel to return to.
	 */
	private GamePanel gamePanel;
	
	/**
	 * Constructs the pause panel.
	 * @param gamePanel is the panel to return to.
	 */
	public GameOverPanel(SwingUI swingUI, GamePanel gamePanel) {
		this.setLayout(new BorderLayout());
		this.swingUI = swingUI;
		this.gamePanel = gamePanel;
		this.setBackground(SwingUI.DEFAULT_COLOR);
	}
	
	/**
	 * Initializes the top score board.
	 */
	public void init() {
		JLabel text = new JLabel("<html><b><font color=\"white\" size=\"16\">Game over</font><b></html>");
		JButton button1 = new JButton("Restart game");
		JButton button2 = new JButton("Return to main menu");
		JPanel subPanel = new JPanel(new GridLayout(3, 0, 0, 50));
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_2.png");
		Image newimg1 = image1.getScaledInstance(
				(int) (420 * (swingUI.getGameMode() == 1 ? 2.5 : 1)), 110, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(
				(int) (420 * (swingUI.getGameMode() == 1 ? 2.5 : 1)), 110, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				gamePanel.getGame().restartGame();
				swingUI.switchPanel(gamePanel);
				swingUI.requestFocusInWindow();
			}
		});
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				swingUI.switchPanel(swingUI.getStartPanel());
			}
		});
		button1.setIcon(imgIcon1);
		button1.setHorizontalTextPosition(SwingConstants.CENTER);
		button1.setBorderPainted(false);
		button1.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				button1.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button1.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		button2.setIcon(imgIcon1);
		button2.setHorizontalTextPosition(SwingConstants.CENTER);
		button2.setBorderPainted(false);
		button2.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				button2.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button2.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});
		subPanel.setBackground(SwingUI.DEFAULT_COLOR);
		subPanel.add(text);
		if (swingUI.getGameMode() == 0)
			subPanel.add(button1);
		else {
			if (gamePanel.getGameEngine().getWinner() == null)
				subPanel.add(new JLabel("<html><font size=\"14\" color=\"white\">No winner</font></html>"));
			else
				subPanel.add(new JLabel("<html><font size=\"14\" color=\"white\">Winner is " +
					gamePanel.getGameEngine().getWinner().getUsername() + "</font></html>"));
		}
		subPanel.add(button2);
		this.add(subPanel);
	}

}