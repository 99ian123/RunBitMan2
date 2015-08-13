package millstein.RunBitMan2;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JOptionPane;

public class Kirby extends Game implements LevelPlugin {

	ArrayList<Integer> initial = new ArrayList<Integer>(0);
	AtomicBoolean justHit = new AtomicBoolean();
	AtomicBoolean invincible = new AtomicBoolean();
	AtomicBoolean beingReset = new AtomicBoolean();
	long resetSpeed;

	/**
	 * Thread and data to render the hit boxes of all the mobs and of the player
	 * character.
	 *
	 * @param pauseTime
	 *            - amount of time the thread will slow the frame-rate when hit.
	 */
	@Override
	public void gameRenderHitbox(final long pauseTime) {

		// Make sure the program is getting the correct data.
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (justHit.get() == false) {
			try {
				if (((armStartX <= (bluHeadX + 30)) && (armEndX >= bluHeadX))) {
					if ((rightLegEndY >= bluHeadY)
							&& (rightLegEndY <= (bluHeadY + 30))) {
						Thread.sleep(pauseTime);
						life--;
						justHit.compareAndSet(false, true);
						resetJustHit.run();

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
						justHit.getAndSet(true);
						resetJustHit.run();

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
						justHit.getAndSet(true);
						resetJustHit.run();

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
						justHit.set(true);
						resetJustHit.run();

						if (life == 0) {
							lost();
						}

					}

				}

				if (((rightLegEndX <= (magHeadX + 30))
						&& (rightLegEndX >= magHeadX))) {
					if ((rightLegEndY >= magHeadY)
							&& (rightLegEndY <= (magHeadY + 30))) {
						Thread.sleep(pauseTime);
						life--;
						justHit.set(true);
						resetJustHit.run();

						if (life == 0) {
							lost();
						}
					}
				}
			} catch (InterruptedException e) {
			}
		}

		// Separate the Block logic from the hitbox logic.
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
	}

	/**
	 * Resets the variables which are required when you lose.
	 */
	@Override
	public void lost() {

		JOptionPane.showMessageDialog(null,
				"You Lose!\nYour high score was: " + score + " points", "Lost",
				JOptionPane.WARNING_MESSAGE);

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
	 * Read configuration of the programme from file. This is currently not
	 * used. The source is based on iFlyCode/JavaPy's package involving. I
	 * intend that it replace all the hardcoded sections.
	 *
	 * @author ifly6
	 * @see iFlyCode/JavaPy
	 * @see javapy.files.FileReading
	 */
	public void readConfig(String file) {
		try {
			FileReader configRead;
			configRead = new FileReader(file);
			Scanner scan = new Scanner(configRead);
			while (scan.hasNext()) {
				bluHeadX = scan.nextInt();
				bluHeadY = scan.nextInt();
				magHeadX = scan.nextInt();
				magHeadY = scan.nextInt();
				blaHeadX = scan.nextInt();
				blaHeadY = scan.nextInt();
				oraHeadX = scan.nextInt();
				oraHeadY = scan.nextInt();
				headX = scan.nextInt();
				headY = scan.nextInt();
				bodyStartX = scan.nextInt();
				bodyStartY = scan.nextInt();
				bodyEndX = scan.nextInt();
				bodyEndY = scan.nextInt();
				armStartX = scan.nextInt();
				armStartY = scan.nextInt();
				armEndX = scan.nextInt();
				armEndY = scan.nextInt();
				rightLegEndX = scan.nextInt();
				rightLegEndY = scan.nextInt();
				leftLegEndX = scan.nextInt();
				leftLegEndY = scan.nextInt();
				Block = scan.nextInt();
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveConfig(String file) {
		try {
			FileReader configRead;
			configRead = new FileReader(file);
			Scanner scan = new Scanner(configRead);
			while (scan.hasNext()) {
				// These are in the same order as the readConfig system. They
				// should be in that order as well.
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
				initial.add(scan.nextInt());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method renders all the Mobs in the Game.
	 */
	@Override
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
	@Override
	public void gameRenderMovement() {

		boolean hitPlatformFall = false;
		/*
		 * Boolean to hold whether or not BitMan is falling due to hitting a
		 * platform on the underside.
		 */

		// Determines whether or not BitMan has landed on a platform and stops
		// him from falling if so.
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

		// Determines whether or not BitMan has landed on any one of the middle
		// platforms and stops him from falling if so.
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
			// Checks the middle-centre platform.
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

		// Determines whether or not BitMan has landed on the lowest platform
		// and stops him from falling if so.
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

		// Checks top platform.
		else if ((headY >= 265) && (headY <= 275)) {

			if ((headX >= 275) && (headX <= 325)) {
				falling = true;
				jumpKeyPressed = false;
				counter = 21;
				hitPlatformFall = true;
			}
		}

		// Ensures BitMan cannot go through the sides of the middle platforms.
		if ((leftLegEndY > 409) && (headY < 425)) {
			// Checks right side of middle-left platform.
			if ((leftLegEndX <= 172) && (leftLegEndX >= 162)) {
				headX = 160;
				bodyStartX = 165;
				bodyEndX = 165;
				armStartX = 160;
				armEndX = 170;
				rightLegEndX = 158;
				leftLegEndX = 172;
			}
			// Checks left side of middle-centre platform.
			if ((leftLegEndX >= 221) && (leftLegEndX <= 231)) {
				headX = 209;
				bodyStartX = 214;
				bodyEndX = 214;
				armStartX = 209;
				armEndX = 219;
				rightLegEndX = 207;
				leftLegEndX = 221;
			}
			// Checks right side of middle-centre platform.
			if ((leftLegEndX <= 392) && (leftLegEndX >= 382)) {
				headX = 380;
				bodyStartX = 385;
				bodyEndX = 385;
				armStartX = 380;
				armEndX = 390;
				rightLegEndX = 378;
				leftLegEndX = 392;
			}
			// Checks left side of middle-right platform.
			if ((leftLegEndX >= 427) && (leftLegEndX <= 447)) {
				headX = 429;
				bodyStartX = 434;
				bodyEndX = 434;
				armStartX = 429;
				armEndX = 439;
				rightLegEndX = 427;
				leftLegEndX = 441;
			}
		}
		// Ensures BitMan cannot go through the sides of the top platform.
		else if ((leftLegEndY > 245) && (headY < 275)) {
			// Checks left side of top platform.
			if ((leftLegEndX >= 271) && (leftLegEndX <= 281)) {
				headX = 259;
				bodyStartX = 264;
				bodyEndX = 264;
				armStartX = 259;
				armEndX = 269;
				rightLegEndX = 257;
				leftLegEndX = 271;
			}

			// Checks right side of top platform.
			if ((leftLegEndX >= 334) && (leftLegEndX <= 344)) {
				headX = 332;
				bodyStartX = 337;
				bodyEndX = 337;
				armStartX = 332;
				armEndX = 342;
				rightLegEndX = 330;
				leftLegEndX = 344;
			}
		}

		// Adjusts BitMan's coordinates if it is determined that he should be
		// falling.
		if (falling) {
			headY += velocityY;
			bodyStartY += velocityY;
			bodyEndY += velocityY;
			armStartY += velocityY;
			armEndY += velocityY;
			rightLegEndY += velocityY;
			leftLegEndY += velocityY;
		}

		// Ensures BitMan cannot go through left side of screen.
		if (headX <= 12) {
			headX = 12;
			bodyStartX = 17;
			bodyEndX = 17;
			armStartX = 12;
			armEndX = 22;
			rightLegEndX = 10;
			leftLegEndX = 24;
		}
		// Ensures BitMan cannot go through right side of screen.
		else if (headX >= 577) {
			headX = 577;
			bodyStartX = 582;
			bodyEndX = 582;
			armEndX = 587;
			rightLegEndX = 575;
			leftLegEndX = 589;
			armStartX = 577;
		}
		// Adjusts BitMan's coordinates if he is jumping
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
		// Adjusts BitMan's coordinates if he should be moving to the right.
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
	@Override
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

	@Override
	public void cycler() {

		// Choices
		String[] speedModes = { "Easy", "Normal", "Hard" };

		// Input System
		String speedSelect = (String) JOptionPane.showInputDialog(null,
				"Choose Difficulty", "RunBitMan 2", JOptionPane.PLAIN_MESSAGE,
				null, speedModes, "Easy");

		long resetSpeedInit = 0;
		long speed = 100;
		if ((speedSelect != null) && (speedSelect.length() > 0)) {
			if (speedSelect.equals("Easy")) {
				speed = 45;
				resetSpeedInit = 1000;
			}
			if (speedSelect.equals("Normal")) {
				speed = 30;
				resetSpeedInit = 750;
			}
			if (speedSelect.equals("Hard")) {
				speed = 20;
				resetSpeedInit = 500;
			}
		} else {
			System.exit(0);
		}

		resetSpeed = resetSpeedInit;

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
		while (true) {
			gameRenderObjects();
			gameRenderMovement();
			gameRenderMobs();
			paintScreen();
			gameRenderHitbox(500);

			// Testing
			System.out.println("justHit = " + justHit.get());

			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Timer to reset the JustHit variable.
	 *
	 * @author ifly6
	 * @param justHit
	 *            Must not be null. Is the period the system waits for you to be
	 *            able to be hit again.
	 */
	public Runnable resetJustHit = new Runnable() {
		@Override
		public void run() {
			if (invincible.get() == false) {
				try {
					System.out.println("We've gotten to the resetting part.");
					Thread.sleep(resetSpeed);

					// while (justHit.get() == true) {
					justHit.set(false);
					// }

					System.out.println("== Reset.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null,
							"Some developer has failed to include the resetSpeed "
									+ "in the difficulty (or something) section. Complain to them.");
				}
			}
		}
	};
}