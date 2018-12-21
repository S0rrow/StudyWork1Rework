package edu.handong.csee.java.sw1R;

import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class UserInterface {
	private int ROW;
	private int COL;
	private int MINE;
	Game NewGame;
	//지뢰 이미지
	Image MineImage = new ImageIcon("img/mine.png").getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
	//저장한 이미지를 이미지 아이콘으로 저장.
	ImageIcon MineIcon = new ImageIcon(MineImage);
	private int SCORE;
	private int elapsedTime;
	JLabel timeLabel = new JLabel("Time passed: "+0+" seconds");
	int initTime = (int)System.currentTimeMillis()/1000;
	boolean ongoing = false;
	//생성자
	UserInterface(){
		ROW = 0;
		COL = 0;
		MINE = 0;
		SCORE = 0;
		elapsedTime = 0;
	}
	//게임클래스를 받아서 지정.
	void setGame(Game givenGame) {
		NewGame = givenGame;
	}
	//초기 게임의 정보를 받아오기 위한 콘솔 인터페이스.
	public void setInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Row: ");
		ROW = scanner.nextInt();
		System.out.print("Column: ");
		COL = scanner.nextInt();
		System.out.print("Number of mines: ");
		MINE = scanner.nextInt();
		NewGame.Start(ROW, COL, MINE);
		scanner.close();
	}
	//초기 게임의 정보를 받아오기 위한 새로운 JFrame을 띄우는 함수.
	public void InputScreen() {
		ongoing = false;
		//메인프레임 생성.
		JFrame inputFrame = new JFrame("New game");
		JPanel mainContainer = new JPanel();//메인 패널 생성
		inputFrame.setLocation(200,200);//초기 위치 지정
		inputFrame.setPreferredSize(new Dimension(650,100));//사이즈 지정
		
		//행의 정보를 받아오기 위한 텍스트 필드를 배치하기 위해 패널과 라벨 생성.
		JPanel rowPane = new JPanel();
		JLabel rowLabel = new JLabel("Row: ");
		JSpinner row = new JSpinner(new SpinnerNumberModel(10, 1, 30, 1));
		//JTextField row = new JTextField("10");
		
		//열의 정보를 받아오기 위한 텍스트 필드를 배치하기 위한 패널과 라벨 생성.
		JPanel colPane = new JPanel();
		JLabel colLabel = new JLabel("Column: ");
		JSpinner col = new JSpinner(new SpinnerNumberModel(10, 1, 30, 1));
		//JTextField col = new JTextField("10");
		//지뢰의 갯수를 받아오기 위한 텍스트 필드를 배치하기 위한 패널과 라벨 생성.
		JPanel minePane = new JPanel();
		JLabel mineLabel = new JLabel("Number of Mines: ");
		JSpinner mine = new JSpinner(new SpinnerNumberModel(10, 1, 999, 1));
		//JTextField mine = new JTextField("10");//각 행, 열, 지뢰 갯수의 초기값은 10으로 설정.
		GridLayout two = new GridLayout(1,2);
		mainContainer.setLayout(new GridLayout(1,3));
		rowPane.setLayout(two);
		rowPane.add(rowLabel);
		rowPane.add(row);
		colPane.setLayout(two);
		colPane.add(colLabel);
		colPane.add(col);
		minePane.setLayout(two);
		minePane.add(mineLabel);
		minePane.add(mine);
		
		//메인 패널에 행, 열, 지뢰 갯수를 받아오기 위한 패널들을 배치.
		mainContainer.add(rowPane);
		mainContainer.add(colPane);
		mainContainer.add(minePane);
		
		//프레임의 중앙에 메인 패널을 배치.
		inputFrame.add(mainContainer, BorderLayout.CENTER);
		
		//버튼들을 배치하기 위한 패널을 생성.
		JPanel buttonPane = new JPanel(new GridLayout(1,2));
		JButton starter = new JButton("Start New Game");
		JButton quiter = new JButton("Quit");
		
		//시작 버튼에 액션 리스너 추가
		starter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ROW = (int)row.getValue();
				COL = (int)col.getValue();
				MINE = (int)mine.getValue();
				if(MINE > ROW * COL) new RestateWindow();
				else {
					NewGame.Start(ROW, COL, MINE);
					inputFrame.dispose();
					Screen();
				}
			}
		});
		//취소 버튼에 액션 리스너 추가
		quiter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				inputFrame.dispose();
			}
		});
		buttonPane.add(starter);
		buttonPane.add(quiter);
		//버튼 패널을 프레임의 하단에 배치.
		inputFrame.add(buttonPane, BorderLayout.SOUTH);
		inputFrame.pack();
		inputFrame.setVisible(true);
	}
	
	//게임을 실제 창으로 띄우기 위한 클래스의 메인 함수.
	public void Screen() {
		JFrame frame = new JFrame("Minesweeper");//프레임이름을 주고 프레임 인스턴스화
		JPanel MainContainer = new JPanel();
		frame.setLocation(200,100);//프레임 초기 위치 지정.
		JPanel GameStatus = new JPanel();//게임의 정보를 저장하기 위한 패널 생성.
		
		//게임상의 남은 지뢰 갯수를 세기 위한 라벨
		JLabel MineCount = new JLabel("Mine Left:"+(NewGame.getMineNum()-NewGame.MarkCount()));
		
		//현재 취해진 액션에 대한 정보를 보여주기 위한 라벨
		JLabel ActionLabel = new JLabel("[ ]");
		//게임의 점수를 보여주기 위한 라벨
		JLabel IngameCaller = new JLabel("Score: "+SCORE);
		SCORE = 0;
		
		//시간을 재기 위한 타이머
		Timer elapsedTime = new Timer();
		TimerTask updateTimer = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(ongoing) setTimeLabel((int)System.currentTimeMillis()/1000);
			}
		};
		elapsedTime.schedule(updateTimer, 0, 500);
		
		//각각 취소, 새로운 게임, 재시작 버튼.
		JButton quitGame = new JButton("Quit");
		JButton callNewGame = new JButton("New Game");
		JButton callSameGame = new JButton("Start again");
		
		quitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
		
		callNewGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.dispose();
				InputScreen();
			}
		});
		
		callSameGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.dispose();
				NewGame.Start(ROW, COL, MINE);
				timeLabel.setText("Time passed: "+0+" seconds");
				ongoing = false;
				Screen();
			}
		});
		//게임의 상태를 보여주기 위한 패널이므로 그리드 레이아웃으로 정의.
		GameStatus.setLayout(new GridLayout(1,2));
		//카운터들을 하나의 패널로 묶어서 저장.
		JPanel Counters = new JPanel(new GridLayout(4,1));
		Counters.add(MineCount);
		Counters.add(IngameCaller);
		Counters.add(timeLabel);
		Counters.add(ActionLabel);
		GameStatus.add(Counters);
		//게임의 메뉴버튼들을 삽입하기 위한 패널.
		JPanel menu = new JPanel(new GridLayout(1,3));
		menu.add(quitGame);
		menu.add(callNewGame);
		menu.add(callSameGame);
		//메뉴버튼들을 상태패널에 추가.
		GameStatus.add(menu);
		frame.setPreferredSize(new Dimension(800,500));//프레임 화면 크기 지정.
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridLayout layout = new GridLayout(ROW, COL);// 그리드레이아웃의 행과 열 지정.
		frame.add(GameStatus, BorderLayout.NORTH);//게임 정보를 저장한 패널을 프레임에 삽입.
		MainContainer.setLayout(layout);// 맵에 해당하는 패널에 그리드레이아웃 지정.
		frame.add(MainContainer, BorderLayout.CENTER);
		JPanel[][] Buttons = new JPanel[ROW][COL];
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				final int posX = i;
				final int posY = j;
				//JFrame tempFrame = new JFrame();
				Buttons[posX][posY] = new JPanel(new GridLayout());
				//JButton button = new JButton("["+NewGame.map.getButton(posX, posY).isChecked()+"]"+","+NewGame.map.getButton(posX, posY).isMine+","+NewGame.map.getButton(posX, posY).getCount());
				JButton button = new JButton(" ");
				button.setBackground(Color.WHITE);
				//마우스 리스너
				MouseListener listener = new MouseListener() {
					Button ClickedButton = NewGame.map.getButton(posX, posY);
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						int Button1Mask = MouseEvent.BUTTON1_DOWN_MASK;
						int Button3Mask = MouseEvent.BUTTON3_DOWN_MASK;
						//만일 마우스 오른쪽과 왼쪽을 같이 누르는 경우
						if(((e.getModifiersEx() & Button1Mask) == Button1Mask && e.getButton() == MouseEvent.BUTTON3) ||
								((e.getModifiersEx() & Button3Mask) == Button3Mask && e.getButton() == MouseEvent.BUTTON1)) {
							if(Buttons[posX][posY].isEnabled() && ClickedButton.isChecked()) {
								//해당버튼의 주변에 마크된 버튼의 갯수가 해당버튼의 주변 지뢰수와 같을 경우
								if(NewGame.getButtonMarkCount(posX, posY) == ClickedButton.getCount()) {
									for(int i = -1; i <= 1; i++) {
										for(int j = -1; j <= 1; j++) {
											if((i != 0 || j != 0) && NewGame.map.PosValid(posX + i, posY + j)) {
												//해당 버튼의 주변 버튼에 대해 클릭.
												if(!NewGame.map.getButton(posX + i, posY + j).isMarked() && NewGame.map.getButton(posX + i, posY + j).isMine) {
													GameOver(Buttons, MainContainer, ActionLabel);
												}
												else if(!NewGame.map.getButton(posX + i, posY + j).isMarked() && !NewGame.map.getButton(posX + i, posY  + j).isMine) {
													NewGame.Cascade(posX+i, posY+j);
													JButton OtherButton = (JButton)Buttons[posX+i][posY+j].getComponent(0);
													OtherButton.setText(""+ClickedButton.getCount());
													OtherButton.setBackground(Color.LIGHT_GRAY);
													//점수가 10점 증가
													Scorer(10, IngameCaller);
													//게임을 시작, 타이머가 작동중이지 않다면 타이머를 작동.
													if(!ongoing) beginTimer();
													ActionLabel.setText("[Chord]");
													CheckMap(Buttons, IngameCaller);
												}
											}
										}
									}
								}
							}
							
						}
						//왼쪽 마우스 클릭할시
						else if(e.getButton() == MouseEvent.BUTTON1) {
							if(Buttons[posX][posY].isEnabled()) {
								//마크되거나 체크된 버튼이 아니라면
								if(!ClickedButton.isMarked() && !ClickedButton.isChecked()) {
									//만일 지뢰를 클릭하면 gameover 함수 호출.
									if(ClickedButton.isMine) {
										GameOver(Buttons, MainContainer, ActionLabel);
									}
									//클릭한 버튼이 지뢰가 아니라면
									else{
										//해당 버튼에서 카스케이드 실행
										NewGame.Cascade(posX, posY);
										//해당 버튼의 텍스트를 주변의 지뢰 갯수로 설정.
										button.setText(""+ClickedButton.getCount());
										//button.setText("["+NewGame.map.getButton(posX, posY).isChecked()+"]"+","+NewGame.map.getButton(posX, posY).isMine+","+NewGame.map.getButton(posX, posY).getCount());
										//클릭된 버튼의 배경색을 회색으로 변경.
										button.setBackground(Color.LIGHT_GRAY);
										//점수를 5점 증가.
										Scorer(5, IngameCaller);
										//게임을 시작, 타이머가 작동중이지 않다면 타이머를 작동.
										if(!ongoing) beginTimer();
										ActionLabel.setText("[Check]");
										//맵의 카스케이드된 버튼들의 상황을 업데이트.
										CheckMap(Buttons, IngameCaller);
									}
								}
							}
						}
						//클릭한 마우스가 오른쪽인 경우
						else if(e.getButton() == MouseEvent.BUTTON3) {
							if(Buttons[posX][posY].isEnabled()) {
								//헤당 버튼이 체크된 버튼이 아니라면
								if(!ClickedButton.isChecked()) {
									//이미 마크된 버튼의 경우
									if(ClickedButton.isMarked()) {
										//마크를 해제하고
										ClickedButton.UnMark();
										//클릭 가능한 버튼의 상태로 복귀
										button.setBackground(Color.WHITE);
										Scorer(-5, IngameCaller);
										ActionLabel.setText("[Unmark]");
										button.setEnabled(true);
									}
									//마크되지 않은 버튼의 경우
									else {
										//버튼을 마크하고
										//게임을 시작, 타이머가 작동중이지 않다면 타이머를 작동.
										if(!ongoing) beginTimer();
										ClickedButton.Mark();
										//클릭 불가능하게 변경.
										button.setBackground(Color.GREEN);
										Scorer(5, IngameCaller);
										ActionLabel.setText("[Mark]");
										button.setEnabled(false);
									}
								}
							}
						}
						//IngameCaller.setText("debugger:mouseEvent in ["+posX+"]["+posY+"]: isChecked="+ClickedButton.isChecked()
						//		+", isMarked="+ClickedButton.isMarked());
						//만일 승리조건을 만족하면 gamewin 함수 호출.
						if(NewGame.MarkCount() + NewGame.CheckCount() == ROW*COL && MINE == NewGame.MarkCount() && MainContainer.isEnabled()) {
							GameWin(MainContainer, ActionLabel);
						}
						
						//매 이벤트 실행시마다 마지막에 게임의 상황을 업데이트.
						UpdateMineCount(MineCount);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				};
				button.addMouseListener(listener);
				//tempFrame.add(button);
				//frame.add(tempFrame);
				Buttons[posX][posY].add(button);
				MainContainer.add(Buttons[posX][posY]);
			}
		}
		frame.pack();
		frame.transferFocus();
		frame.setVisible(true);
	}
	//맵의 버튼들의 상태와 실제 JButton들의 정보를 동기화하기 위한 함수.
	private void CheckMap(JPanel[][] Buttons, JLabel IngameCaller) {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				//모든 JButton들에 대해
				//만약 맵의 해당 좌표의 버튼이 체크된 상태라면 해당 좌표의 JButton을 클릭된 상태로 변경.
				if(NewGame.map.getButton(i, j).isChecked()) {
					JButton CheckedButton = (JButton)Buttons[i][j].getComponent(0);
					CheckedButton.setText(""+NewGame.map.getButton(i, j).getCount());
					//CascadedButton.setText("["+NewGame.map.getButton(i, j).isChecked()+"]"+","+NewGame.map.getButton(i, j).isMine+","+NewGame.map.getButton(i, j).getCount());
					CheckedButton.setBackground(Color.LIGHT_GRAY);
				}
			}
		}
	}
	//게임상에서 남은 지뢰의 정보들의 상태를 업데이트.
	private void UpdateMineCount(JLabel MineCount) {
		MineCount.setText("Mine Left: "+(NewGame.getMineNum()-NewGame.MarkCount()));
	}
	//게임 오버시의 상황들을 처리하기 위한 함수.
	private void GameOver(JPanel[][] Buttons, JPanel mainContainer, JLabel ActionLabel) {
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				if(NewGame.map.getButton(i, j).isMine && !NewGame.map.getButton(i, j).isMarked()) {
					JButton MineButton = (JButton)Buttons[i][j].getComponent(0);
					MineButton.setIcon(MineIcon);
					MineButton.setBackground(Color.RED);
				}
				Buttons[i][j].getComponent(0).setEnabled(false);
			}
		}
		setPanelEnabled(mainContainer, false);
		ongoing = false;
		ActionLabel.setText("[GAME OVER]");
		new GameOverWindow();
	}
	//특정 패널의 모든 요소들을 disable하기 위한 함수.
	private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	        }
	        component.setEnabled(isEnabled);
	    }
	}
	//게임 승리시의 상황들을 처리하기 위한 함수.
	private void GameWin(JPanel mainContainer, JLabel ActionLabel) {
		setPanelEnabled(mainContainer, false);
		ongoing = false;
		ActionLabel.setText("[GAME WIN]");
		new GameWinWindow(SCORE, elapsedTime);
	}
	//게임 상의 점수와 점수 표시를 업데이트하기 위한 함수.
	private void Scorer(int increaseScore, JLabel ScoreText) {
		SCORE += increaseScore;
		ScoreText.setText("Score: "+SCORE);
	}
	//게임상의 타이머를 업데이트 하기 위한 함수.
	public void setTimeLabel(int timeDiff) {
		elapsedTime = timeDiff-initTime;
		timeLabel.setText("Time passed: "+elapsedTime+" seconds");
	}
	public JLabel getTimeLabel() {
		return timeLabel;
	}
	private void beginTimer() {
		initTime = (int)System.currentTimeMillis()/1000;
		ongoing = true;
	}
}
