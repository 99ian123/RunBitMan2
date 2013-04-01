package millstein.RunBitMan2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Holds methods all relating to the change of the difficulty and GameModes.
 */
public class GameMode extends MainThread {

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
	 * Read configuration of the programme from file. This is currently not
	 * used. The source is copied from iFlyCode/JavaPy's package involving.
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
				difficulty = scan.nextLine();
			}
		} catch (FileNotFoundException e) {
		}
	}
}
