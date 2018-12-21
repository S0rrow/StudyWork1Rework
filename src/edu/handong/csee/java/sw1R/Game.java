package edu.handong.csee.java.sw1R;

public class Game {
	private int ROW;
	private int COL;
	private int MINE;
	Map map;//버튼들의 정보를 담은 맵.
	//생성자.
	Game(){
		ROW = 0;
		COL = 0;
	}
	//값을 받아서 맵 생성.
	public void Start(int row, int col, int mine) {
		ROW = row;
		COL = col;
		MINE = mine;
		map = new Map(ROW, COL, MINE);
		map.Generate();
	}
	//해당 좌표의 버튼을 검사해 주변의 버튼들도 체크할지 말지를 검사.
	public void Cascade(int x, int y) {
		//만약 클릭한 좌표가 지뢰라면 함수 종료.
		if(map.getButton(x,y).isMine) {
			return;
		}
		else {
			//만약 클릭한 함수의 주변 지뢰의 수가 0이라면
			if(map.Count(x,y)==0) {
				map.getButton(x, y).Check();
				//주변의 다른 칸들에 대해서 검사 시작.
				for(int difX = -1; difX <= 1; difX++) {
					for(int difY = -1; difY <= 1; difY++) {
						//주변을 검사할때 어레이를 벗어나지 않는지, 그리고 검사하는 칸이 자기 자신은 아닌지를 확인.
						if(map.PosValid(x+difX,y+difY) && (difX != 0 || difY != 0)) {
							//검사하는 칸이 지뢰가 아닌지, 그리고 검사하는 칸이 이미 체크, 마크된 칸이 아닌지를 확인.
							//모든 확인이 끝나면 그 칸에 대해서 클릭.
							if(!map.getButton(x+difX, y+difY).isMine && !map.getButton(x+difX, y+difY).isChecked() && !map.getButton(x+difX, y+difY).isMarked()) {
								map.getButton(x+difX,y+difY).Check();
								Cascade(x+difX, y+difY);
							}
						}
					}
				}
			}
			else map.getButton(x,y).Check();
		}
	}
	//모든 체크된 버튼 수를 리턴.
	public int CheckCount() {
		int checkcount = 0;
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				if(map.getButton(i,j).isChecked()) checkcount++;
			}
		}
		return checkcount;
	}
	//특정 좌표의 버튼의 주변에 마크된 갯수를 리턴.
	public int getButtonMarkCount(int posX, int posY) {
		int buttonMarkCount = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(map.PosValid(posX+i, posY+j) && (i!= 0 || j != 0)) {
					if(map.getButton(posX+i, posY+j).isMarked()) buttonMarkCount++;
				}
			}
		}
		return buttonMarkCount;
	}
	//모든 마크된 버튼 수를 리턴.
	public int MarkCount() {
		int markcount = 0;
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				if(map.getButton(i,j).isMarked()) markcount++;
			}
		}
		return markcount;
	}
	//모든 지뢰 수를 리턴.
	public int getMineNum() {
		return MINE;
	}
}
