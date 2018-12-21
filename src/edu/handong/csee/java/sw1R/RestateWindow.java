package edu.handong.csee.java.sw1R;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RestateWindow extends JFrame{
	RestateWindow(){
		setLocation(300,300);
		setTitle("Game Condition Error");
		JPanel NewWindowContainer = new JPanel();
		setContentPane(NewWindowContainer);
		JLabel gameoverLabel = new JLabel("Check if the condition is valid!");
		NewWindowContainer.add(gameoverLabel);
		
		setSize(300,100);
		setResizable(false);
		setVisible(true);
	}
}
