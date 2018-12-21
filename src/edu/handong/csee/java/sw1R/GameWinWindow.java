package edu.handong.csee.java.sw1R;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWinWindow extends JFrame{
	GameWinWindow(int Score, int time){
		setLocation(300,300);
		setTitle("Game Won");
		JPanel NewWindowContainer = new JPanel(new GridLayout(3,1));
		setContentPane(NewWindowContainer);
		JLabel gamewonLabel = new JLabel("Game Win!");
		JLabel elapsedTimeLabel = new JLabel("You cleared game in "+time+" seconds");
		JLabel gameScoreLabel = new JLabel("Your Score was ["+Score+"]");
		NewWindowContainer.add(gamewonLabel);
		NewWindowContainer.add(elapsedTimeLabel);
		NewWindowContainer.add(gameScoreLabel);
		
		setSize(300,100);
		setResizable(false);
		setVisible(true);
	}
	
}
