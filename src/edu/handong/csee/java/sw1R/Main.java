package edu.handong.csee.java.sw1R;

public class Main {
	public static void main(String[] args) {
		Main main = new Main();
		Game NewGame = new Game();
		main.callUserInterface(NewGame);
	}
	
	void callUserInterface(Game Newgame) {
		UserInterface calledUI = new UserInterface();
		calledUI.setGame(Newgame);
		calledUI.InputScreen();
	}
}
