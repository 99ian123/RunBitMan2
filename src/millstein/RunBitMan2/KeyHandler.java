package millstein.RunBitMan2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/*
*Handles the Key presing. Then sets system-wide data with stimuli, which is read by game loop.
*
*@author erm, who made this exactly? I just copy/pasted what was there.
*@since 2013, April 1st
*/
public class KeyHandler extends KeyAdapter {

	private boolean jumpKeyPressed, leftKeyPressed, rightKeyPressed;
	
	/*
	* Updates data when a key is pressed.
	*/
  	@Override
  	public void keyPressed(KeyEvent e) {
  		int key = e.getKeyCode();

			if ((key == KeyEvent.VK_Q) || (key == KeyEvent.VK_ESCAPE)) {
				System.exit(0);
			}
			if (key == KeyEvent.VK_H) {
				String helpString = "Standard Controls are WASD and/or stanadard keypad controls."
						+ "\nPress Q to Quit, I for information, and H to show this.";
				JOptionPane.showMessageDialog(null, helpString);
			}
			if (key == KeyEvent.VK_I) {
				String helpString = "Authors:"
						+ "\n99ian123 - Designer \nifly6 - Editor \nkullalok - Consulting";
				JOptionPane.showMessageDialog(null, helpString);
			}

			// Movement
			if ((e.getKeyCode() == KeyEvent.VK_W)
					|| (e.getKeyCode() == KeyEvent.VK_UP)) {
				jumpKeyPressed = true;
			}

			if ((e.getKeyCode() == KeyEvent.VK_D)
					|| (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
				rightKeyPressed = true;
			}

			if ((e.getKeyCode() == KeyEvent.VK_A)
					|| (e.getKeyCode() == KeyEvent.VK_LEFT)) {
				leftKeyPressed = true;
			}

	}
	
	/*
	* Updates data when a key is released.
	*/
	@Override
	public void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_W) || (e.getKeyCode() == KeyEvent.VK_UP)) {
			jumpKeyPressed = false;
		}
		if ((e.getKeyCode() == KeyEvent.VK_D) || (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
			rightKeyPressed = false;
		}
		if ((e.getKeyCode() == KeyEvent.VK_A) || (e.getKeyCode() == KeyEvent.VK_LEFT)) {
			leftKeyPressed = false;
		}
	}
                
        /*
        * Get method for jumpKeyPressed.
        *
        * @return
        *		-boolean that holds whether or not BitMan should be jumping.
        */     
	public boolean getJumpKeyPressed() {
      		return jumpKeyPressed;
    	}
        
        /*
        * Get method for leftKeyPressed
        *
        * @return
        * 		-boolean that holds whether or not BitMan should be moving to the left.
        */
    	public boolean getLeftKeyPressed() {
      		return leftKeyPressed;
    	}
                
        /*
        * Get method for rightKeyPressed
        *
        * @return
        *		-boolean that holds whether or not BitMan should be moving right.
        */
    	public boolean getRightKeyPressed() {
      		return rightKeyPressed;
    	}
}
