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
 * Programme to be a rudimentary game.
 * 
 * @author 99ian123 - Designer
 * @author ifly6 - Editor
 * @author kullalok - Consultant
 * 
 * @since 29 March 2013
 */

/*
 * TODO Prevent going off the side of the screen!
 * 
 * TODO Add a background or something (it makes it look better)
 * 
 * TODO Split into more than one class for maintenance purposes.
 *
 * TODO Fix reset after BitMan loses.
 */

public class MainThread implements Runnable {

	/**
	 * Handles the Key pressing. Then sets system-wide data with stimuli, which
	 * is read by while loop.
	 */
	private class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_Q || key == KeyEvent.VK_ESCAPE) {
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
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				jumpKeyPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightKeyPressed = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftKeyPressed = true;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
				jumpKeyPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightKeyPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftKeyPressed = false;
			}
		}
	}

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
	protected int directionBlu;
	protected int directionMag;
	protected int directionBla;
	protected int diredctionOra;
	protected static String difficulty;

	// Screen
	protected static JFrame frame = new JFrame();

	/**
	 * Main. Sets difficulty, then constructs the other things. Calls the
	 * constructor.
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

	protected Image dbImage = null;
	protected int dbHeight = 600, dbWidth = 600;

	protected int score, life = 3;
	protected Container container;

	// Program
	protected boolean running;

	protected Thread animator;

	/**
	 * Constructor. It sets the frame, then creates a runnable for starting the
	 * game - and calls startGame(). It then adds the handler for the keys and
	 * sets the frame to visible.
	 */
	MainThread() { // Constructor

		frame.setTitle("RunBitMan 2");
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

	/**
	 * Thread and data to render the hit boxes of all the mobs and of the player
	 * character.
	 * 
	 * @param pauseTime
	 *            - amount of time the thread will slow the frame-rate when hit.
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

		// Mob Movement
		if ((directionBlu % 2) == 0) {
			bluHeadX -= 4; // Speed
		} else {
			bluHeadX += 4;
		}

		if ((bluHeadX <= 220) || (bluHeadX >= 355)) {
			directionBlu++;
		}

		if ((directionMag % 2) == 0) {
			magHeadX -= 13; // Speed
		} else {
			magHeadX += 13;
		}

		if ((magHeadX <= 5) || (magHeadX >= 555)) {
			directionMag++;
		}

		if ((directionBla % 2) == 0) {
			blaHeadX -= 3; // Speed
		} else {
			blaHeadX += 3;
		}

		if ((blaHeadX <= 5) || (blaHeadX >= 130)) {
			directionBla++;
		}

		if ((diredctionOra % 2) == 0) {
			oraHeadX -= 2.5; // Speed
		} else {
			oraHeadX += 2.5;
		}

		if ((oraHeadX <= 445) || (oraHeadX >= 570)) {
			diredctionOra++;
		}
	}

	/**
	 * Renders movement of the player Character.
	 */
	public void gameRenderMovement() {
            
                boolean hitPlatformFall = false; // Boolean to hold whether or not BitMan is falling due to hitting a platform on the underside.

		// Determines whether or not BitMan has landed on a platform and stops him from falling if so.
		if ((rightLegEndY >= 245) && (leftLegEndY <= 255)) {

			if ((rightLegEndX >= 265) && (leftLegEndX <= 335)) {
				headY = 211;
				bodyStartY = 221;
				bodyEndY = 236;
				armStartY = 230;
				armEndY = 230;
				rightLegEndY = 245;
				leftLegEndY = 245;
                                
                                hitPlatformFall = false;
				falling = false;
				counter = 0;
			}
		}

		// Determines whether or not BitMan has landed on any one of the middle platforrms and stops him from falling if so.
		if ((rightLegEndY >= 395) && (leftLegEndY <= 410)) {
                        
                        // Checks the middle-left platform.
			if ((rightLegEndX >= 5) && (leftLegEndX <= 165)) {
				headY = 360;
				bodyStartY = 370;
				bodyEndY = 385;
				armStartY = 379;
				armEndY = 379;
				rightLegEndY = 395;
				leftLegEndY = 395;
                                
                                hitPlatformFall = false;
				falling = false;
				counter = 0;
			}
                        // Checks the middle-center platform.
			if ((rightLegEndX >= 215) && (leftLegEndX <= 385)) {
				headY = 360;
				bodyStartY = 370;
				bodyEndY = 385;
				armStartY = 379;
				armEndY = 379;
				rightLegEndY = 395;
				leftLegEndY = 395;
                                
                                hitPlatformFall = false;
				falling = false;
				counter = 0;
			}
                        // Checks the middle-right platform.
			if ((rightLegEndX >= 435) && (leftLegEndX <= 595)) {
				headY = 360;
				bodyStartY = 370;
				bodyEndY = 385;
				armStartY = 379;
				armEndY = 379;
				rightLegEndY = 395;
				leftLegEndY = 395;
                                
                                hitPlatformFall = false;
				falling = false;
				counter = 0;
			}
		}
                
                // Determines whether or not BitMan has landed on the lowest platform and stops him from falling if so.
		if (leftLegEndY >= 495) {
			headY = 460;
			bodyStartY = 470;
			bodyEndY = 485;
			armStartY = 479;
			armEndY = 479;
			rightLegEndY = 495;
			leftLegEndY = 495;
                        
                        hitPlatformFall = false;
			falling = false;
			counter = 0;
		}
                // Ensures BitMan cannot jump through any of the platforms on the underside.
                if( headY >= 415 && headY <= 425 ) {
                    // Checks middle-left platform.
                    if( headX >= 5 && headX <= 155 ) {
                        falling = true;
                        jumpKeyPressed = false;
                        counter = 21;
                        hitPlatformFall = true;
                    }
                    // Checks middle-center platform.
                    if( headX >= 215 && headX <= 375 ) {
                        falling = true;
                        jumpKeyPressed = false;
                        counter = 21;
                        hitPlatformFall = true;
                    }
                    // Checks middle-right platform.
                    if( headX >= 435 && headX <= 595 ) {
                        falling = true;
                        jumpKeyPressed = false;
                        counter = 21;
                        hitPlatformFall = true;
                        
                    }
                }// Checks top platform.
                else if( headY >= 265 && headY <= 275 ) {
                    
                    if( headX >= 275 && headX <= 325 ) {
                        falling = true;
                        jumpKeyPressed = false;
                        counter = 21;
                        hitPlatformFall = true;
                    }
                }
                
                // Ensures BitMan cannot go through the sides of the middle platforms.
                if( leftLegEndY > 409 && headY < 425 ) {
                    // Checks right side of middle-left platform.
                    if( leftLegEndX <= 172 && leftLegEndX >= 162) {
                        headX = 160;
                        bodyStartX = 165;
                        bodyEndX = 165;
                        armStartX = 160;
                        armEndX = 170;
                        rightLegEndX = 158;
                        leftLegEndX= 172;
                    }
                    // Checks left side of middle-center platform.
                    if ( leftLegEndX >= 221 && leftLegEndX <= 231 ) {
                        headX = 209;
                        bodyStartX = 214;
                        bodyEndX = 214;
                        armStartX = 209;
                        armEndX = 219;
                        rightLegEndX = 207;
                        leftLegEndX = 221;
                    }
                    // Checks right side of middle-center platform.
                    if( leftLegEndX <= 392 && leftLegEndX >= 382 ) {
                        headX =  380;
                        bodyStartX= 385;
                        bodyEndX = 385;
                        armStartX = 380;
                        armEndX = 390;
                        rightLegEndX = 378;
                        leftLegEndX = 392;
                    }
                    // Checks left side of middle-right platform.
                    if( leftLegEndX >= 427 && leftLegEndX <= 447 ) {
                        headX = 429;
                        bodyStartX = 434;
                        bodyEndX = 434;
                        armStartX = 429;
                        armEndX = 439;
                        rightLegEndX = 427;
                        leftLegEndX = 441;
                    }
                } // Ensures BitMan cannot go through the sides of the top platform.
                else if( leftLegEndY > 245 && headY < 275 ) {
                    // Checks left side of top platform.
                    if( leftLegEndX >= 271 && leftLegEndX <= 281) {
                        headX = 259;
                        bodyStartX = 264;
                        bodyEndX = 264;
                        armStartX = 259;
                        armEndX = 269;
                        rightLegEndX = 257;
                        leftLegEndX = 271;
                    }
                    
                    // Checks right side of top platform.
                    if( leftLegEndX >= 334 && leftLegEndX <= 344 ) {
                        headX = 332;
                        bodyStartX = 337;
                        bodyEndX = 337;
                        armStartX = 332;
                        armEndX = 342;
                        rightLegEndX = 330;
                        leftLegEndX = 344;
                    }
                }
                
                // Adjusts BitMan's coordinates if it is determined that he should be falling.
		if (falling) {
			headY += velocityY;
			bodyStartY += velocityY;
			bodyEndY += velocityY;
			armStartY += velocityY;
			armEndY += velocityY;
			rightLegEndY += velocityY;
			leftLegEndY += velocityY;
		}
                // Ensures BitMan cannot go through left side of scren.
                if( headX <= 12 ) {
                    headX = 12;
                    bodyStartX = 17;
                    bodyEndX = 17;
                    armStartX = 12;
                    armEndX = 22;
                    rightLegEndX = 10;
                    leftLegEndX = 24;
                }
                // Ensures BitMan cannot go through right side of screen.
                else if( headX >= 577 ) {
                    headX = 577;
                    bodyStartX = 582;
                    bodyEndX = 582;
                    armEndX = 587;
                    rightLegEndX = 575;
                    leftLegEndX = 589;
                    armStartX = 577;
                }
                // Adjusts BitMan's coordinates if it is determined that he should be jumping.               
		if (!hitPlatformFall && jumpKeyPressed && (counter <= 20)) {
			headY -= velocityY;
			bodyStartY -= velocityY;
			bodyEndY -= velocityY;
			armStartY -= velocityY;
			armEndY -= velocityY;
			rightLegEndY -= velocityY;
			leftLegEndY -= velocityY;
			counter++;
		}
                // Makes BitMan fall if he has jumped for too long.
		else if (jumpKeyPressed && (counter > 20)) {
			jumpKeyPressed = false;
		}
                
		if (!jumpKeyPressed) {
			falling = true;
		} else {
			falling = false;
		}
                // Adjusts BitMan's coordinates if he sould be moving to the right.
		if (rightKeyPressed) {
			headX += velocityX;
			bodyStartX += velocityX;
			bodyEndX += velocityX;
			armStartX += velocityX;
			armEndX += velocityX;
			rightLegEndX += velocityX;
			leftLegEndX += velocityX;

		}
                // Adjusts BitMan's coordinates if he should be moving
		if (leftKeyPressed) {
			headX -= velocityX;
			bodyStartX -= velocityX;
			bodyEndX -= velocityX;
			armStartX -= velocityX;
			armEndX -= velocityX;
			rightLegEndX -= velocityX;
			leftLegEndX -= velocityX;
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

		dbg = dbImage.getGraphics();

		dbg.setColor(Color.WHITE); // The Screen
		dbg.fillRect(0, 0, dbWidth, dbHeight);

		dbg.setColor(Color.green); // Left Platform
		dbg.fillRect(5, 400, 150, 25);

		dbg.setColor(Color.green); // Middle Platform
		dbg.fillRect(225, 400, 150, 25);

		dbg.setColor(Color.green); // Right Platform
		dbg.fillRect(445, 400, 150, 25);

		dbg.setColor(Color.green); // Bottom Platform
		dbg.fillRect(5, 500, 590, 25);

		dbg.setColor(Color.green); // Top Platform
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

		dbg.setColor(Color.black);
		dbg.drawString("Press H for Help", 25, 80);
		dbg.drawString("Score: " + score, 25, 100);
		dbg.drawString("Lives remaining: " + life, 25, 120);
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

		// Set Positions for Player Character
		headY = 211;
		bodyStartY = 221;
		bodyEndY = 236;
		armStartY = 230;
		armEndY = 230;
		rightLegEndY = 245;
		leftLegEndY = 245;

		headX = 292;
		bodyStartX = 297;
		bodyEndX = 297;
		armStartX = 292;
		armEndX = 302;
		rightLegEndX = 290;
		leftLegEndX = 304;
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

	// Opens Screen
	/**
	 * Thread which calls the rendering, then the painting. It sets the frame
	 * rate, and the game opening systems. It is the main graphics THREAD. The
	 * others haven't been localized into their respective threads yet.
	 */
	@Override
	public void run() {
		running = true;

		// Choices
		String[] possibilities = { "Easy", "Normal", "Hard", "Cancel" };

		// Input System
		int difficulty = JOptionPane.showOptionDialog( null, "Choose one", "RunBitMan2", 
		JOptionPane.DEFAULT_OPTION, 
                JOptionPane.PLAIN_MESSAGE, null, 
                possibilities, possibilities[ 0 ] );

		long speedDeclare = 45;
		if (difficulty == 0) {
			speedDeclare = 45;
		}
		else if (difficulty == 1) {
			speedDeclare = 30;
		}
		else if (difficulty == 2) {
			speedDeclare = 15;
		}
		else if(difficulty == 3 ) {
			System.exit( 0 );
		}
		final long speed = speedDeclare;

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
					gameRenderHitbox(500);

					gameRenderMobs();
					paintScreen();

					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		graphicsThread.run();

	}

	/**
	 * Starts the game, creating the animation system - with the thread, Run.
	 */
	void startGame() {
		if ((animator == null) || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}
}
