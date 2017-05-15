package comp2911;

import javax.swing.SwingUtilities;

import comp2911.gui.SwingUI;

public class Main {
	
	/**
	 * The main of the game
	 * @param args initial inputs from the controller
	 */
	public static void main(String[] args) {
        SwingUI swingUI = new SwingUI();
		SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	        	  swingUI.init();
	          }
		});
	}
	
}
