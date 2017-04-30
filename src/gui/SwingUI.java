package gui;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class SwingUI {

	private JFrame currentFrame;
	
	public SwingUI() {
		this.init();
	}
	
	private void init() {
		try {
			JPopupMenu.setDefaultLightWeightPopupEnabled(false);
			this.setCurrentFrame(new StartScreen());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the currentFrame
	 */
	public JFrame getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * @param currentFrame to set
	 */
	public void setCurrentFrame(JFrame currentFrame) {
		this.currentFrame = currentFrame;
	}
	
}
