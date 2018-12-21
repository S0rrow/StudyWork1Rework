package edu.handong.csee.java.sw1R;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
@SuppressWarnings("serial")
class GameOverWindow extends JFrame{
	GameOverWindow(){
		setLocation(300,300);
		setTitle("Game Over");
		JPanel NewWindowContainer = new JPanel();
		setContentPane(NewWindowContainer);
		JLabel gameoverLabel = new JLabel("Game Over");
		NewWindowContainer.add(gameoverLabel);
		
		setSize(300,100);
		setResizable(false);
		setVisible(true);
	}
}