import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class MainThread extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Graphics
	public Graphics dbg;
	public int bluHeadX = 225, bluHeadY = 369;
	public int magHeadX = 285, magHeadY = 479;
	public int blaHeadX = 70, blaHeadY = 369;
	public int oraHeadX = 520, oraHeadY = 354;
	public int headX = 292, headY = 215;
	public int bodyStartX = 297, bodyStartY = 225, bodyEndX = 297, bodyEndY = 240;
	public int armStartX = 292, armStartY = 234, armEndX = 302, armEndY = 234;
	public int rightLegEndX = 290, rightLegEndY = 249, leftLegEndX = 304, leftLegEndY = 249;
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
	public Image dbImage = null;
	public int dbHeight = 600, dbWidth = 600;
	public int score, life = 3;
	public Container c;

	// Program
	public boolean running;
	public Thread animator;

	public MainThread() {

		super("TEST");
		c = getContentPane();
		c.setLayout(new FlowLayout());
		startGame();
		KeyHandler handler = new KeyHandler();
		addKeyListener(handler);
		setSize(600, 600);
		setVisible(true);

	}

	public void startGame() { // Running Method

		if(animator == null || !running) {
			animator = new Thread( this );
			animator.start();
		}
	}

	public void run() { // Opening Screen Method

		running = true;

		try {
			JOptionPane.showMessageDialog(null, "TEST", "TEST", JOptionPane.WARNING_MESSAGE); 

		}

		catch(Exception e) {}
		while(running) {
			gameRender();
			paintScreen();

			try {
				Thread.sleep(20);
			}
			catch(Exception e){}
		}
	}

	public void gameRender() { // Graphics Method

		if(dbImage == null) {
			dbImage = createImage(dbWidth, dbHeight);
			if(dbImage == null)
				return;
		}

		dbg = dbImage.getGraphics();

		dbg.setColor(Color.white); // screen
		dbg.fillRect(0,0,dbWidth, dbHeight);

		dbg.setColor(Color.green); // Platform
		dbg.fillRect(5,400,150,25);

		dbg.setColor(Color.green);
		dbg.fillRect(225,400,150,25);

		dbg.setColor(Color.green);
		dbg.fillRect(445,400,150,25);

		dbg.setColor(Color.green);
		dbg.fillRect(5,500,590,25);

		dbg.setColor(Color.green);
		dbg.fillRect(275,250,50,25);

		dbg.setColor(Color.yellow); // Goal Block
		dbg.fillRect(Block, 385, 15, 15);

		dbg.setColor(Color.blue); // Blue Monster
		dbg.fillOval(bluHeadX,bluHeadY,30,30);
		dbg.setColor(Color.white);
		dbg.drawRect(bluHeadX, bluHeadY, 30, 30);

		dbg.setColor(Color.magenta); // Magenta Monster
		dbg.fillRect(magHeadX,magHeadY,40,20);
		dbg.setColor(Color.white);
		dbg.drawRect(magHeadX, magHeadY, 40, 20);

		dbg.setColor(Color.black); // Black Monster
		dbg.fillRect(blaHeadX,blaHeadY,30,30);
		dbg.setColor(Color.white);
		dbg.drawRect(blaHeadX, blaHeadY, 30, 30);

		dbg.setColor(Color.orange); // Orange Monster
		dbg.fillOval(oraHeadX,oraHeadY,30,45);
		dbg.setColor(Color.white);
		dbg.drawOval(oraHeadX, oraHeadY, 30, 45);

		dbg.setColor(Color.black); // BitMan
		dbg.drawOval(headX,headY, 10, 10);
		dbg.drawLine(bodyStartX,bodyStartY,bodyEndX,bodyEndY);
		dbg.drawLine(armStartX,armStartY,armEndX,armEndY);
		dbg.drawLine(bodyEndX, bodyEndY, leftLegEndX, leftLegEndY);
		dbg.drawLine(bodyEndX, bodyEndY, rightLegEndX, rightLegEndY);

		if( jumpKeyPressed && counter <= 20) {
			headY -= velocityY;
			bodyStartY -= velocityY;
			bodyEndY -= velocityY;
			armStartY -= velocityY;
			armEndY -= velocityY;
			rightLegEndY -= velocityY;
			leftLegEndY -= velocityY;
			counter++;
		}

		else if( jumpKeyPressed && counter > 20 )
			jumpKeyPressed = false;


		if( !jumpKeyPressed )
			falling = true;
		
		else
			falling = false;

		if( rightKeyPressed ) {
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

		if(headY >=  360) {
			headY = 360;
			falling = false;
			counter = 0;
		}

		if(bodyStartY >= 380)
			bodyStartY = 380;

		if(bodyEndY >= 400)
			bodyEndY = 400;

		if(armStartY >= 390)
			armStartY = 390;

		if(armEndY >= 390)
			armEndY = 390;

		if(rightLegEndY >= 400)
			rightLegEndY = 400;

		if(leftLegEndY >= 410)
			leftLegEndY = 410;

		if(falling) {
			headY += velocityY;
			bodyStartY += velocityY;
			bodyEndY += velocityY;
			armStartY += velocityY;
			armEndY += velocityY;
			rightLegEndY += velocityY;
			leftLegEndY += velocityY;
		}

		for( int i = 0; i <= 900; i++ ) {

			if(( armStartX <= bluHeadX + 30 && armEndX >= bluHeadX )) {
				if( rightLegEndY >= bluHeadY && rightLegEndY <= bluHeadY + 30) {

					lost();
					life--;

					if( life == 0 ) {
						JOptionPane.showMessageDialog( null, "You Lose!\nYour high score was: " + score + " points" , "Lost", JOptionPane.WARNING_MESSAGE ); // Message displayed when player dies
						score = 0;
						life = 3; // how many lives player starts with

					}
				}
			}
		}

		if(( armStartX <= blaHeadX + 30 && armEndX >= blaHeadX )) {
			if( rightLegEndY >= blaHeadY && rightLegEndY <= blaHeadY + 30) {

				lost();
				life--;

				if( life == 0 ) {
					main2.main(null);
				}

			}

		}

		if(( armStartX <= oraHeadX + 30 && armEndX >= oraHeadX )) {
			if( rightLegEndY >= oraHeadY && rightLegEndY <= oraHeadY + 30) {

				lost();
				life--;

				if( life == 0 ) {
					main2.main(null);
				}

			}

		}

		if(( armStartX <= magHeadX + 30 && armEndX >= magHeadX )) {
			if( rightLegEndY >= magHeadY && rightLegEndY <= magHeadY + 30) {

				lost();
				life--;

				if( life == 0 ) {
					main2.main(null);
				}

			}

		}

		if(( rightLegEndX <= magHeadX + 30 && rightLegEndX >= magHeadX )) {
			if( rightLegEndY >= magHeadY && rightLegEndY <= magHeadY + 30) {
				lost();
				life--;
				
				if(life == 0 ){
					main2.main(null);
				}
			}
		}

			for( int i = 0; i <= 225; i++ ) {
				
				if( ( armStartX <= Block + 15 && armEndX >= Block ) ) {
					if( rightLegEndY >= 385 && rightLegEndY <= 400 ) {
						lost();
						score++;

						int randNum = ( int ) ( Math.random() * 500 );
						if( randNum < 110 )
							randNum = 110;
						Block = randNum;
						break;
					}
				}
			}	
			dbg.setColor(Color.black);
			dbg.drawString("Score: " + score, 25, 100);
			dbg.drawString("Lives remaining: " + life, 25, 120);

			if(direction % 2 == 0) // Blue Monster Movement and Speed
				bluHeadX -= 4; // Speed
			else
				bluHeadX += 4; 

			if(bluHeadX <= 220 || bluHeadX >= 355)
				direction++;


			if(direction1 % 2 == 0) // Magenta Monster Movement and Speed
				magHeadX -= 13; // Speed
			else
				magHeadX += 13; 

			if(magHeadX <= 5 || magHeadX >= 555)
				direction1++;


			if(direction2 % 2 == 0) // Black Monster Movement and Speed
				blaHeadX -= 3; // Speed
			else
				blaHeadX += 3; 

			if(blaHeadX <= 5 || blaHeadX >= 130)
				direction2++;


			if(direction3 % 2 == 0) // Orange Monster Movement and Speed
				oraHeadX -= 2.5; // Speed
			else
				oraHeadX += 2.5; 

			if(oraHeadX <= 445 || oraHeadX >= 570)
				direction3++;
		}

	public void lost() {

		try {
			Thread.sleep( 500 ); // How much time before player spawns after death
		}
		catch( Exception e ) {}


		headX = 292; headY = 215; 
		bodyStartX = 297; bodyStartY = 225; bodyEndX = 297; bodyEndY = 240; 
		armStartX = 292; armStartY = 234; armEndX = 302; armEndY = 234; 
		rightLegEndX = 290; rightLegEndY = 249; leftLegEndX = 304; leftLegEndY = 249;
		direction = 0;
	}

	public void paintScreen() {

		Graphics g;

		try {

			g = this.getGraphics();
			if( ( g != null ) && ( dbImage != null ) ) {
				g.drawImage( dbImage, 0, 0, null );
				Toolkit.getDefaultToolkit().sync();
				g.dispose();
			}
		}
		catch( Exception e ) {}
	}
	public static void main(String[] args) {

		main2 app = new main2();

		app.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}
				);
	}

	private class KeyHandler extends KeyAdapter {


		public void keyPressed( KeyEvent e ) {

			if( e.getKeyCode() == KeyEvent.VK_UP )
				jumpKeyPressed = true;

			if( e.getKeyCode() == KeyEvent.VK_RIGHT )
				rightKeyPressed = true;

			if( e.getKeyCode() == KeyEvent.VK_LEFT )
				leftKeyPressed = true;
		}

		public void keyReleased( KeyEvent e ) {

			if( e.getKeyCode() == KeyEvent.VK_UP )
				jumpKeyPressed = false;

			if( e.getKeyCode() == KeyEvent.VK_RIGHT )
				rightKeyPressed = false;

			if( e.getKeyCode() == KeyEvent.VK_LEFT )
				leftKeyPressed = false;
		}
	}
}
