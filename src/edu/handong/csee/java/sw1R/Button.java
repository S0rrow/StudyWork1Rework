package edu.handong.csee.java.sw1R;

public class Button {
	private int[] Position = new int[2];
	private int mineCount;
	private int isChecked = 0;
	public boolean isMine;
	
	Button() {
		Position[0] = 0;
		Position[1] = 0;
		mineCount = 0;
		isMine = false;
	}
	
	public void Mine() {
		isMine = true;
	}
	
	public void setPosition(int x, int y) {
		Position[0] = x;
		Position[1] = y;
	}
	public void setCount(int c) {
		mineCount = c;
	}
	public int getCount() {
		return mineCount;
	}
	public int[] getPosition() {
		return Position;
	}
	public void Check() {
		isChecked = 1;
	}
	public boolean isChecked() {
		return (isChecked==1);
	}
	public void Mark() {
		isChecked =-1;
	}
	public void UnMark() {
		isChecked = 0;
	}
	public boolean isMarked() {
		return (isChecked==-1);
	}
}
