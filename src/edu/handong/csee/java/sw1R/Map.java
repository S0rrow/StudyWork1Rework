package edu.handong.csee.java.sw1R;

public class Map {
	private int ROW;
	private int COL;
	private int MINE;
	private Button[][] data;
	Map(int row, int col, int mine){
		this.ROW = row;
		this.COL = col;
		this.MINE = mine;
	}
	//버튼 어레이에 인스턴스 배정.
	//지뢰 배정.
	void Generate() {
		data = new Button[ROW][COL];
		for(int x = 0; x <ROW; x++) {
			for(int y=0; y< COL; y++) {
				data[x][y] = new Button();
			}
		}
		for(int x=0, y=0, mineLeft = this.MINE; mineLeft >0; mineLeft--) {
			x = (int)(Math.random()*ROW)+0;
			y = (int)(Math.random()*COL)+0;
			if(data[x][y].isMine) mineLeft++;
			data[x][y].Mine();
			//System.out.println("Mined in x:" + x + ", y:"+y);
		}
	}
	public boolean PosValid(int x, int y) {
		if(x < 0 || y < 0 || ROW <= x || COL <= y) return false;
		else return true;
	}
	
	//해당 좌표의 버튼이 지뢰인지 아닌지 체크.
	public boolean CheckMine(int x, int y) {
		if(!PosValid(x, y)) return false;
		return data[x][y].isMine;
	}
	//해당 좌표의 주변의 지뢰 갯수를 세서 해당 좌표의 버튼의 정보로 저장.
	public int Count(int curX, int curY) {
		int mineCounted = 0;
		for(int x = -1; x<=1; x++) {
			for(int y = -1; y<=1; y++) {
				if(PosValid(curX + x, curY + y) && (x != 0 || y != 0)) {
					if(CheckMine(curX+x, curY+y)) mineCounted++;
				}
			}
		}
		data[curX][curY].setCount(mineCounted);
		return data[curX][curY].getCount();
	}
	//해당 좌표의 버튼을 리턴.
	Button getButton(int x, int y) {
		return data[x][y];
	}
}
