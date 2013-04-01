package millstein.RunBitMan2;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Programme to be a rudimentary game.
 * 
 * @author 99ian123 - Designer
 * @author ifly6 - Editor
 * @author kullalok - Consultant
 * 
 * @since 29 March 2013
 */

/*
 * TODO Add a background or something (it makes it look better)
 * 
 * TODO Split into more than one class for maintenance purposes.
 * 
 * TODO Fix reset after BitMan loses.
 */

public class Game {

	/**
	 * Handles the Key pressing. Then sets system-wide data with stimuli, which
	 * is read by while loop.
	 */
	private class KeyHandler extends KeyAdapter {

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

		@Override
		public void keyReleased(KeyEvent e) {
			if ((e.getKeyCode() == KeyEvent.VK_W)
					|| (e.getKeyCode() == KeyEvent.VK_UP)) {
				jumpKeyPressed = false;
			}
			if ((e.getKeyCode() == KeyEvent.VK_D)
					|| (e.getKeyCode() == KeyEvent.VK_RIGHT)) {
				rightKeyPressed = false;
			}
			if ((e.getKeyCode() == KeyEvent.VK_A)
					|| (e.getKeyCode() == KeyEvent.VK_LEFT)) {
				leftKeyPressed = false;
			}
		}
	}

	// Graphics
	Graphics dbg;
	int bluHeadX = 225, bluHeadY = 369;
	int magHeadX = 285, magHeadY = 479;
	int blaHeadX = 70, blaHeadY = 369;
	int oraHeadX = 520, oraHeadY = 354;
	int headX = 292, headY = 215;
	int bodyStartX = 297, bodyStartY = 225, bodyEndX = 297, bodyEndY = 240;
	int armStartX = 292, armStartY = 234, armEndX = 302, armEndY = 234;
	int rightLegEndX = 290, rightLegEndY = 249, leftLegEndX = 304,
			leftLegEndY = 249;

	int Block = 500;
	// Controls
	boolean jumpKeyPressed, rightKeyPressed, leftKeyPressed, falling;
	float velocityX = 5, velocityY = 5, gravity = 0.5f;
	int counter;
	int directionBlu;
	int directionMag;
	int directionBla;
	int diredctionOra;
	String difficulty;

	// Screen
	public static JFrame frame = new JFrame();

	/**
	 * Main. Sets difficulty, then constructs the other things. Calls the
	 * constructor.
	 * 
	 * @param args
	 *            - No arguments are taken from the Main.
	 */
	public static void main(String[] args) {

		new Game();

		// Add Window Listener
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		String[] gameModes = { "Kirby", "Mario" };
		String gameSelect = (String) JOptionPane.showInputDialog(null,
				"Choose Game Mode", "RunBitMan 2", JOptionPane.PLAIN_MESSAGE,
				null, gameModes, "Kirby");

		if ((gameSelect != null) && (gameSelect.length() > 0)) {
			if (gameSelect.equals("Kirby")) {
				Kirby mode = new Kirby();
				mode.cycler();
			}
			if (gameSelect.equals("Mario")) {
				Mario mode = new Mario();
				mode.cycler();
			} else {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}

	Image dbImage = null;
	int dbHeight = 600, dbWidth = 600;

	int score, life = 3;
	Container container;

	// Program
	boolean running;

	Thread animator;

	/**
	 * Constructor. It sets the frame, then creates a runnable for starting the
	 * game - and calls startGame(). It then adds the handler for the keys and
	 * sets the frame to visible.
	 */
	Game() { // Constructor

		frame.setTitle("RunBitMan 2");
		container = frame.getContentPane();

		KeyHandler handler = new KeyHandler();
		frame.addKeyListener(handler);
		frame.setSize(600, 600);
		frame.setVisible(true);

	}

	/**
	 * Updates the screen with the new data provided by the many gameRender
	 * methods.
	 */
	public void paintScreen() {
		Graphics g;
		try {
			g = frame.getGraphics();
			if ((g != null) && (dbImage != null)) {
				g.drawImage(dbImage, 0, 0, null);
				Toolkit.getDefaultToolkit().sync();
				g.dispose();
			}
		} catch (Exception e) {
		}
	}

	/*
	 * public void freezeVersion() { bluHeadX = ; bluHeadY = ; magHeadX = ;
	 * magHeadY = ; blaHeadX = ; blaHeadY = ; oraHeadX = ; oraHeadY = ; headX =
	 * ; headY = ; bodyStartX = ; bodyStartY = ; bodyEndX = ; bodyEndY = ;
	 * armStartX = ; armStartY = ; armEndX = ; armEndY = ; rightLegEndX = ;
	 * rightLegEndY = ; leftLegEndX = ; leftLegEndY = ; Block = ; difficulty = ;
	 * }
	 */
}
