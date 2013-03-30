package millstein.RunBitMan2;

import java.awt.Color;
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
 * Programme to <b>INSERT PURPOSE HERE</b>.
 * 
 * @author 99ian123
 * @author ifly6 - Fixing a VERY LARGE NUMBER of bugs.
 * @since 29 March 2013
 */

/*
 * TODO Prevent going off the side of the screen!
 * 
 * TODO Add a background or something (it makes it look better)
 * 
 * TODO Split into more than one class for maintenance purposes.
 */

public class MainThread implements Runnable {
	/*
	 * Here is where I implemented all of the integers and variables which I use
	 * in the rest of the program.
	 */

	// Graphics
	public Graphics dbg;
	protected int bluHeadX = 225, bluHeadY = 369;
	protected int magHeadX = 285, magHeadY = 479;
	protected int blaHeadX = 70, blaHeadY = 369;
	protected int oraHeadX = 520, oraHeadY = 354;
	protected int headX = 292, headY = 215;
	protected int bodyStartX = 297, bodyStartY = 225, bodyEndX = 297,
			bodyEndY = 240;
	protected int armStartX = 292, armStartY = 234, armEndX = 302,
			armEndY = 234;
	protected int rightLegEndX = 290, rightLegEndY = 249, leftLegEndX = 304,
			leftLegEndY = 249;
	protected int Block = 500;

	// Controls
	protected boolean jumpKeyPressed, rightKeyPressed, leftKeyPressed, falling;
	protected float velocityX = 5, velocityY = 5, gravity = 0.5f;
	protected int counter;
	protected int direction;
	protected int direction1;
	protected int direction2;
	protected int direction3;

	// Screen
	protected static JFrame frame = new JFrame();
	protected Image dbImage = null;
	protected int dbHeight = 600, dbWidth = 600;
	protected int score, life = 3;
	protected Container container;

	// Program
	protected boolean running;
	protected Thread animator;

	MainThread() { // Constructor

		frame.setTitle("RunBitMan: II");
		container = frame.getContentPane();

		Runnable startGame = new Runnable() {
			@Override
			public void run() {
				startGame();
			}
		};
		startGame.run();

		KeyHandler handler = new KeyHandler();
		frame.addKeyListener(handler);
		frame.setSize(600, 600);
		frame.setVisible(true);

	}

	// Invoke Running
	void startGame() {
		if ((animator == null) || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	// Opens Screen
	@Override
	/**
	 * Thread which calls the rendering, then the painting. It sets the frame rate, and the game opening systems.
	 */
	public void run() {
		running = true;

		JOptionPane.showMessageDialog(null, "RunBitMan 2",
				"This is a testing programme."
						+ "\nIt makes no claims to working perfectly.",
				JOptionPane.WARNING_MESSAGE);

		gameRenderObjects();
		gameRenderMovement();
		gameRenderMobs();
		paintScreen();
		// Give some time to prepare.

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		// While Loop to Repaint the Screen
		Runnable graphicsThread = new Runnable() {
			@Override
			public void run() {
				while (running) {
					gameRenderObjects();
					gameRenderMovement();
					gameRenderMobs();
					gameRenderHitbox(200);
					paintScreen();

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		graphicsThread.run();

	}

	/**
	 * Thread and data to render the hit boxes of all the mobs and of the player
	 * character.
	 * 
	 * @param pauseTime
	 *            - amount of time the thread will pause for the
	 */
	public void gameRenderHitbox(final long pauseTime) {
		Runnable MobThread = new Runnable() {
			@Override
			public void run() {
				try {
					if (((armStartX <= (bluHeadX + 30)) && (armEndX >= bluHeadX))) {
						if ((rightLegEndY >= bluHeadY)
								&& (rightLegEndY <= (bluHeadY + 30))) {
							Thread.sleep(pauseTime);
							life--;

							if (life == 0) {
								lost();
							}
						}
					}

					if (((armStartX <= (blaHeadX + 30)) && (armEndX >= blaHeadX))) {
						if ((rightLegEndY >= blaHeadY)
								&& (rightLegEndY <= (blaHeadY + 30))) {
							Thread.sleep(pauseTime);
							life--;

							if (life == 0) {
								lost();
							}

						}

					}

					if (((armStartX <= (oraHeadX + 30)) && (armEndX >= oraHeadX))) {
						if ((rightLegEndY >= oraHeadY)
								&& (rightLegEndY <= (oraHeadY + 30))) {
							Thread.sleep(pauseTime);
							life--;

							if (life == 0) {
								lost();
							}

						}

					}

					if (((armStartX <= (magHeadX + 30)) && (armEndX >= magHeadX))) {
						if ((rightLegEndY >= magHeadY)
								&& (rightLegEndY <= (magHeadY + 30))) {
							Thread.sleep(pauseTime);
							life--;

							if (life == 0) {
								lost();
							}

						}

					}

					if (((rightLegEndX <= (magHeadX + 30)) && (rightLegEndX >= magHeadX))) {
						if ((rightLegEndY >= magHeadY)
								&& (rightLegEndY <= (magHeadY + 30))) {
							Thread.sleep(pauseTime);
							life--;

							if (life == 0) {
								lost();
							}
						}
					}

					if (((armStartX <= (Block + 15)) && (armEndX >= Block))) {
						if ((rightLegEndY >= 385) && (rightLegEndY <= 400)) {
							score++;

							int randNum = (int) (Math.random() * 500);
							if (randNum < 110) {
								randNum = 110;
							}
							Block = randNum;
						}
					}
				} catch (InterruptedException e) {
				}
			}
		};
		MobThread.run();
	}

	/**
	 * Method renders all the Mobs in the Game.
	 */
	public void gameRenderMobs() {
		dbg.setColor(Color.black);
		dbg.drawString("Score: " + score, 25, 100);
		dbg.drawString("Lives remaining: " + life, 25, 120);

		// Mob Movement
		if ((direction % 2) == 0) {
			bluHeadX -= 4; // Speed
		} else {
			bluHeadX += 4;
		}

		if ((bluHeadX <= 220) || (bluHeadX >= 355)) {
			direction++;
		}

		if ((direction1 % 2) == 0) {
			magHeadX -= 13; // Speed
		} else {
			magHeadX += 13;
		}

		if ((magHeadX <= 5) || (magHeadX >= 555)) {
			direction1++;
		}

		if ((direction2 % 2) == 0) {
			blaHeadX -= 3; // Speed
		} else {
			blaHeadX += 3;
		}

		if ((blaHeadX <= 5) || (blaHeadX >= 130)) {
			direction2++;
		}

		if ((direction3 % 2) == 0) {
			oraHeadX -= 2.5; // Speed
		} else {
			oraHeadX += 2.5;
		}

		if ((oraHeadX <= 445) || (oraHeadX >= 570)) {
			direction3++;
		}
	}

	/**
	 * Renders movement of the player Character.
	 */
	public void gameRenderMovement() {
		if (jumpKeyPressed && (counter <= 20)) {
			headY -= velocityY;
			bodyStartY -= velocityY;
			bodyEndY -= velocityY;
			armStartY -= velocityY;
			armEndY -= velocityY;
			rightLegEndY -= velocityY;
			leftLegEndY -= velocityY;
			counter++;
		}

		else if (jumpKeyPressed && (counter > 20)) {
			jumpKeyPressed = false;
		}

		if (!jumpKeyPressed) {
			falling = true;
		} else {
			falling = false;
		}

		if (rightKeyPressed) {
			headX += velocityX;
			bodyStartX += velocityX;
			bodyEndX += velocityX;
			armStartX += velocityX;
			armEndX += velocityX;
			rightLegEndX += velocityX;
			leftLegEndX += velocityX;

		}

		if (leftKeyPressed) {
			headX -= velocityX;
			bodyStartX -= velocityX;
			bodyEndX -= velocityX;
			armStartX -= velocityX;
			armEndX -= velocityX;
			rightLegEndX -= velocityX;
			leftLegEndX -= velocityX;
		}

		if (headY >= 360) {
			headY = 360;
			falling = false;
			counter = 0;
		}

		if (bodyStartY >= 380) {
			bodyStartY = 380;
		}

		if (bodyEndY >= 400) {
			bodyEndY = 400;
		}

		if (armStartY >= 390) {
			armStartY = 390;
		}

		if (armEndY >= 390) {
			armEndY = 390;
		}

		if (rightLegEndY >= 400) {
			rightLegEndY = 400;
		}

		if (leftLegEndY >= 410) {
			leftLegEndY = 410;
		}

		if (falling) {
			headY += velocityY;
			bodyStartY += velocityY;
			bodyEndY += velocityY;
			armStartY += velocityY;
			armEndY += velocityY;
			rightLegEndY += velocityY;
			leftLegEndY += velocityY;
		}
	}

	/**
	 * Renders the Objects.
	 */
	public void gameRenderObjects() {

		if (dbImage == null) {
			dbImage = frame.createImage(dbWidth, dbHeight);
			if (dbImage == null) {
				return;
			}
		}
		// Graphics Method. Where all of the character/platforms are drawn.
		// It's a bit confusing - some of the points are defined in the method
		// above where I
		// implemented everything.

		dbg = dbImage.getGraphics();

		dbg.setColor(Color.white); // The Screen
		dbg.fillRect(0, 0, dbWidth, dbHeight);

		dbg.setColor(Color.green); // Left Platform
		dbg.fillRect(5, 400, 150, 25);

		dbg.setColor(Color.green); // Middle Platform
		dbg.fillRect(225, 400, 150, 25);

		dbg.setColor(Color.green); // Right Platform
		dbg.fillRect(445, 400, 150, 25);

		dbg.setColor(Color.green); // Bottom Platform
		dbg.fillRect(5, 500, 590, 25);

		dbg.setColor(Color.green);
		dbg.fillRect(275, 250, 50, 25);

		dbg.setColor(Color.yellow); // Goal Block
		dbg.fillRect(Block, 385, 15, 15);

		dbg.setColor(Color.blue); // Blue Monster
		dbg.fillOval(bluHeadX, bluHeadY, 30, 30);
		dbg.setColor(Color.white);
		dbg.drawRect(bluHeadX, bluHeadY, 30, 30);

		dbg.setColor(Color.magenta); // Magenta Monster
		dbg.fillRect(magHeadX, magHeadY, 40, 20);
		dbg.setColor(Color.white);
		dbg.drawRect(magHeadX, magHeadY, 40, 20);

		dbg.setColor(Color.black); // Black Monster
		dbg.fillRect(blaHeadX, blaHeadY, 30, 30);
		dbg.setColor(Color.white);
		dbg.drawRect(blaHeadX, blaHeadY, 30, 30);

		dbg.setColor(Color.orange); // Orange Monster
		dbg.fillOval(oraHeadX, oraHeadY, 30, 45);
		dbg.setColor(Color.white);
		dbg.drawOval(oraHeadX, oraHeadY, 30, 45);

		dbg.setColor(Color.black); // BitMan - all the points are implemented in
		// the top method.
		dbg.drawOval(headX, headY, 10, 10);
		dbg.drawLine(bodyStartX, bodyStartY, bodyEndX, bodyEndY);
		dbg.drawLine(armStartX, armStartY, armEndX, armEndY);
		dbg.drawLine(bodyEndX, bodyEndY, leftLegEndX, leftLegEndY);
		dbg.drawLine(bodyEndX, bodyEndY, rightLegEndX, rightLegEndY);
	}

	/**
	 * Resets the variables which are required when you lose.
	 */
	public void lost() {

		JOptionPane.showMessageDialog(null, "You Lose!\nYour high score was: "
				+ score + " points", "Lost", JOptionPane.WARNING_MESSAGE);

		// Reset Possibly Stuck Keys
		leftKeyPressed = false;
		jumpKeyPressed = false;
		rightKeyPressed = false;

		// Reset Score and Lives
		score = 0;
		life = 3;

		// Set Positions for Things
		headX = 292;
		headY = 215;
		bodyStartX = 297;
		bodyStartY = 225;
		bodyEndX = 297;
		bodyEndY = 240;
		armStartX = 292;
		armStartY = 234;
		armEndX = 302;
		armEndY = 234;
		rightLegEndX = 290;
		rightLegEndY = 249;
		leftLegEndX = 304;
		leftLegEndY = 249;
		direction = 0;
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

	/**
	 * Main.
	 * 
	 * @param args
	 *            - No arguments are taken from the Main.
	 */
	public static void main(String[] args) {
		// Construct Frame
		Runnable construct = new Runnable() {
			@Override
			public void run() {
				new MainThread();
			}
		};
		construct.run();

		// Add Window Listener
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	/**
	 * Handles the Key pressing. Then sets system-wide data with stimuli, which
	 * is read by while loop.
	 * 
	 * @author 99ian123
	 */
	private class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_Q) {
				lost();
			}

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				jumpKeyPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightKeyPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftKeyPressed = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				jumpKeyPressed = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightKeyPressed = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftKeyPressed = false;
			}
		}
	}
}
