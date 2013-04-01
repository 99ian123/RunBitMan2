package millstein.RunBitMan2;

import javax.swing.JOptionPane;


public class Mario extends Game implements LevelPlugin {

	@Override
	public void gameRenderMobs() {

	}

	@Override
	public void gameRenderMovement() {

	}

	@Override
	public void gameRenderObjects() {

	}

	@Override
	public void gameRenderHitbox(long pauseTime) {

	}

	@Override
	public void lost() {

	}

	@Override
	public void cycler() {

		// Choices
		String[] speedModes = { "Easy", "Normal", "Hard" };

		// Input System
		String speedSelect = (String) JOptionPane.showInputDialog(null,
				"Choose Difficulty", "RunBitMan 2", JOptionPane.PLAIN_MESSAGE,
				null, speedModes, "Easy");

		long speedDeclare = 100;
		if ((speedSelect != null) && (speedSelect.length() > 0)) {
			if (speedSelect.equals("Easy")) {
				speedDeclare = 45;
			}
			if (speedSelect.equals("Normal")) {
				speedDeclare = 30;
			}
			if (speedSelect.equals("Hard")) {
				speedDeclare = 20;
			}
		} else {
			System.exit(0);
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
		while (true) {
			gameRenderObjects();
			gameRenderMovement();
			gameRenderMobs();
			paintScreen();
			gameRenderHitbox(500);

			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
