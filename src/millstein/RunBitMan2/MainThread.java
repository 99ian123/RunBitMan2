package millstein.RunBitMan2;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
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
 * @author ifly6
 * @since 29 March 2013
 */

/*
 * TODO Prevent going off the side of the screen!
 * 
 * TODO Add a background or something (it makes it look better)
 * 
 * TODO Split into more than one class for maintenance purposes.
 *
 * TODO Fix platform issue.
 */

public class MainThread implements Runnable {
	/*
	Here is where I implemented all of the integers and variables which I use
	in the rest of the program.
	*/

	// Graphics
	public Graphics dbg;
	public int bluHeadX = 225, bluHeadY = 369;
	public int magHeadX = 285, magHeadY = 479;
	public int blaHeadX = 70, blaHeadY = 369;
	public int oraHeadX = 520, oraHeadY = 354;
	public int headX = 292, headY = 215;
	public int bodyStartX = 297, bodyStartY = 225, bodyEndX = 297,
			bodyEndY = 240;
	public int armStartX = 292, armStartY = 234, armEndX = 302, armEndY = 234;
	public int rightLegEndX = 290, rightLegEndY = 249, leftLegEndX = 304,
			leftLegEndY = 249;
	public int Block = 500;

	// Controls
	public boolean jumpKeyPressed, rightKeyPressed, leftKeyPressed, falling;
	public float velocityX = 5, velocityY = 5, gravity = 0.5f;
	public int counter;
	public int direction;
	public int direction1;
	public int direction2;
	public int direction3;

	// Screen
	public static JFrame frame = new JFrame();
	public Image dbImage = null;
	public int dbHeight = 600, dbWidth = 600;
	public int score, life = 3;
	public Container container;

	// Program
	public boolean running;
	public Thread animator;

	public MainThread() { // The Basic GUI Method. aka Sreen Size, Visiblilty, Layout, etc.

		frame.setTitle("RunBitMan 2");
		container = frame.getContentPane();
		container.setLayout(new FlowLayout());
		startGame();
		KeyHandler handler = new KeyHandler();
		frame.addKeyListener(handler);
		frame.setSize(600, 600);
		frame.setVisible(true);

	}

	// Invoke Running
	public void startGame() {
		if ((animator == null) || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	// Opens Screen
	@Override
	public void run() {
		running = true;

		JOptionPane.showMessageDialog(null, "RunBitMan 2",
				"This is a testing programme."
						+ "\nIt makes no claims to working perfectly.",
				JOptionPane.WARNING_MESSAGE);

		// While Loop to Repaint the Screen
		while (running) {
			gameRender();
			paintScreen();

			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Animation Rendering and Key Logic
	public void gameRender() {

		if (dbImage == null) {
			dbImage = frame.createImage(dbWidth, dbHeight);
			if (dbImage == null) {
				return;
			}
		}
		// Graphics Method. Where all of the character/platforms are drawn.
		// It's a bit confusing - some of the points are defined in the method above where I
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

		dbg.setColor(Color.black); // BitMan - all the points are implemented in the top method.
		dbg.drawOval(headX, headY, 10, 10);
		dbg.drawLine(bodyStartX, bodyStartY, bodyEndX, bodyEndY);
		dbg.drawLine(armStartX, armStartY, armEndX, armEndY);
		dbg.drawLine(bodyEndX, bodyEndY, leftLegEndX, leftLegEndY);
		dbg.drawLine(bodyEndX, bodyEndY, rightLegEndX, rightLegEndY);
		
		// In the following, I implemented movement.

		if (jumpKeyPressed && (counter <= 20)) { // Jumping
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

		if (rightKeyPressed) { // Moving Right
			headX += velocityX;
			bodyStartX += velocityX;
			bodyEndX += velocityX;
			armStartX += velocityX;
			armEndX += velocityX;
			rightLegEndX += velocityX;
			leftLegEndX += velocityX;

		}

		if (leftKeyPressed) { // Moving Left
			headX -= velocityX;
			bodyStartX -= velocityX;
			bodyEndX -= velocityX;
			armStartX -= velocityX;
			armEndX -= velocityX;
			rightLegEndX -= velocityX;
			leftLegEndX -= velocityX;
		}
		
		// Player bounderies ( aka. where the different body parts can/can't go ).

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

		// HitBox Logic
		// What happens if character touches monters/goal block.
		
		for (int i = 0; i <= 900; i++) {
			if (((armStartX <= (bluHeadX + 30)) && (armEndX >= bluHeadX))) {
				if ((rightLegEndY >= bluHeadY)
						&& (rightLegEndY <= (bluHeadY + 30))) {

					lost();
					life--;

					if (life == 0) {
						JOptionPane.showMessageDialog(null,
								"You Lose!\nYour high score was: " + score
										+ " points", "Lost",
								JOptionPane.WARNING_MESSAGE); // Message
																// displayed
																// when player
																// dies
						score = 0;
						life = 3; // how many lives player starts with

					}
				}
			}

			if (((armStartX <= (blaHeadX + 30)) && (armEndX >= blaHeadX))) {
				if ((rightLegEndY >= blaHeadY)
						&& (rightLegEndY <= (blaHeadY + 30))) {

					lost();
					life--;

					if (life == 0) {
						MainThread.main(null);
					}

				}

			}

			if (((armStartX <= (oraHeadX + 30)) && (armEndX >= oraHeadX))) {
				if ((rightLegEndY >= oraHeadY)
						&& (rightLegEndY <= (oraHeadY + 30))) {

					lost();
					life--;

					if (life == 0) {
						MainThread.main(null);
					}

				}

			}

			if (((armStartX <= (magHeadX + 30)) && (armEndX >= magHeadX))) {
				if ((rightLegEndY >= magHeadY)
						&& (rightLegEndY <= (magHeadY + 30))) {

					lost();
					life--;

					if (life == 0) {
						MainThread.main(null);
					}

				}

			}

			if (((rightLegEndX <= (magHeadX + 30)) && (rightLegEndX >= magHeadX))) {
				if ((rightLegEndY >= magHeadY)
						&& (rightLegEndY <= (magHeadY + 30))) {
					lost();
					life--;

					if (life == 0) {
						MainThread.main(null);
					}
				}
			}
		}

		for (int i = 0; i <= 225; i++) {

			if (((armStartX <= (Block + 15)) && (armEndX >= Block))) {
				if ((rightLegEndY >= 385) && (rightLegEndY <= 400)) {
					lost();
					score++;

					int randNum = (int) (Math.random() * 500);
					if (randNum < 110) {
						randNum = 110;
					}
					Block = randNum;
					break;
				}
			}
		}
		
		// The score and lives screen.
		
		dbg.setColor(Color.black);
		dbg.drawString("Score: " + score, 25, 100);
		dbg.drawString("Lives remaining: " + life, 25, 120);
		
		// The Speed of the various monsters, and where they can/can't go.
		
		if ((direction % 2) == 0) { // Blue Monster
			bluHeadX -= 4; // Speed
		} else {
			bluHeadX += 4;
		}

		if ((bluHeadX <= 220) || (bluHeadX >= 355)) {
			direction++;
		}

		if ((direction1 % 2) == 0) { // Magenta Monster
			magHeadX -= 13; // Speed
		} else {
			magHeadX += 13;
		}

		if ((magHeadX <= 5) || (magHeadX >= 555)) {
			direction1++;
		}

		if ((direction2 % 2) == 0) { // Black Monster
			blaHeadX -= 3; // Speed
		} else {
			blaHeadX += 3;
		}

		if ((blaHeadX <= 5) || (blaHeadX >= 130)) {
			direction2++;
		}

		if ((direction3 % 2) == 0) { // Oranger Monster
			oraHeadX -= 2.5; // Speed
		} else {
			oraHeadX += 2.5;
		}

		if ((oraHeadX <= 445) || (oraHeadX >= 570)) {
			direction3++;
		}
	}

	public void lost() { // Lost Method
		try {
			Thread.sleep(1500); // Time Before Respawn
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Respawn Position for BitMan
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

	public static void main(String[] args) {
		// Construct Frame
		new MainThread();

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
