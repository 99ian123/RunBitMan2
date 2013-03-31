package millstein.RunBitMan2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CharaterSelection extends JFrame implements ActionListener {
  private static final long serialVersionUID = 1L;
	JPanel pane = new JPanel();
	JLabel label = new JLabel("           Select Your Character           ");
	JButton done = new JButton("Quit");
	JButton button = new JButton("BitMan");
	JButton button1 = new JButton("FreezeBitMan");
	CharaterSelection() 
	{
		super ("RunBitMan 2"); 
		setBounds(120,125,270,225);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container con = this.getContentPane();
		button.addActionListener(this);
		button1.addActionListener(this);
		done.addActionListener(this);
		con.add(pane); pane.add(label); pane.add(button); pane.add(button1);
		pane.add(done);
		setVisible(true);
	}

	public static void main(String[] args) {
		new CharaterSelection();

	}
	public void actionPerformed1(ActionEvent e){
		Object source = e.getSource(); }
	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();
		if (source == button){
			MainThread.main(null);
		}
		if (source == button1){
			Freeze.main(null);
		}

		if (source == done){
			System.exit(0);
		}
	}
}


