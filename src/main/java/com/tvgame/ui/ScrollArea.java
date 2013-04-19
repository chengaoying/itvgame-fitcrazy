package com.tvgame.ui;

import javax.microedition.lcdui.*;

import cn.ohyeah.stb.key.KeyCode;

import com.tvgame.game.Game;

//import cn.g9.j2me.game.GraphicsBase;

/**
 * 
 * @author chris
 * @version 1.0.1
 */
public class ScrollArea {
//	public int x;
//	public int y;
//	public int w;//���������
//	public int h;//�������߶�

	public int borderWidth = 0;// ѡ�����Ե�Ŀ��
	public int space = 1;// ѡ��֮��Ŀ��

//	public int optionW;// ѡ��Ŀ��
//	public int optionH;// ѡ��ĸ߶�

	public int curPos;

	public int rowNum;// ����ѡ�������
	public int colNum;// ����ѡ�������

	public int showFromColNum;// �ӵڼ�����ʾ

	public int maxShowRowNum = 4;// ����ʾ����
	public int maxShowColNum = 2;// ����ʾ����

//	public void init(int curPos,  int optionNum) {
//
//		maxShowRowNum = 4;
//		maxShowColNum = 2;
//
//		showFromColNum = 0;
//		this.curPos = curPos;
//
////		this.optionW = optionW;
////		this.optionH = optionH;
//
////		this.w = borderWidth * 2 + maxShowRowNum * optionW + space
////				* (maxShowRowNum - 1);
////		this.h = borderWidth * 2 + maxShowColNum * optionH + space
////				* (maxShowColNum - 1);
//
//		rowNum = maxShowRowNum;
//		colNum = optionNum / maxShowRowNum;
//	}
/**
 * ��ʼ��
 * @param curPos ��ǰ���λ��
 * @param optionW ѡ����
 * @param optionH ѡ��߶�
 * @param optionNum ѡ������
 * @param maxShowRowNum �����ʾ����
 * @param maxShowColNum �����ʾ����
 */
	public void init(int curPos, int optionNum,
			int maxShowRowNum, int maxShowColNum) {
		this.maxShowRowNum = maxShowRowNum;
		this.maxShowColNum = maxShowColNum;

		showFromColNum = 0;
		this.curPos = curPos;

		rowNum = maxShowRowNum;
		colNum = optionNum / maxShowRowNum;
	}
	
	public int curFuc;
	private int toDoFuc;
//	private boolean isCanScol;
	public int getCurFuc(){
		return curFuc;
	}
	public void setToDoFuc(int toDoFuc){
		this.toDoFuc = toDoFuc;
	}
	
	public void control(int key) {
		switch (key) {
//		case GameCanvas.KEY_UP:
			case KeyCode.UP:
			curPos -= rowNum;
			if (curPos < 0) {
				curPos +=rowNum;
				curFuc = toDoFuc;
			}
			break;
//		case GameCanvas.KEY_DOWN:
			case KeyCode.DOWN:
			curPos += rowNum;
			if (curPos >= rowNum * colNum) {
				curPos -= rowNum;
			}
			break;
//		case GameCanvas.KEY_LEFT:
			case KeyCode.LEFT:
			curPos--;
			if (curPos < 0) {
				curPos = 0;
			}
			break;
//		case GameCanvas.KEY_RIGHT:
			case KeyCode.RIGHT:
			curPos++;
			if (curPos >= rowNum * colNum) {
				curPos = rowNum * colNum - 1;
			}
			break;
		}
		checkScroll();
	}


	/**
	 * ��ҳ
	 * @param key
	 */
	public void controlNextPage(int key) {
		switch (key) {
			case KeyCode.UP:
				if (showFromColNum > 0) {
					showFromColNum--;
				}
				break;
			case KeyCode.DOWN:
				if(showFromColNum+maxShowColNum<colNum){
					showFromColNum++;
				}
//				else {
//					showFromColNum = colNum-1 - maxShowColNum;
//				}
				break;
//			case Game.GMK_LEFT:
//				curPos -= maxShowColNum;
//				if (curPos < 0) {
//					curPos = 0;
//				}
//				break;
//			case Game.GMK_RIGHT:
//				curPos += maxShowColNum;
//				if (curPos >= rowNum * colNum) {
//					curPos = rowNum * colNum - 1;
//				}
//				break;
		}
		//		checkScroll();
	}

	public void checkScroll() {

		if (curPos / maxShowRowNum < showFromColNum) {
			showFromColNum--;
		} else if (curPos / maxShowRowNum >= showFromColNum + maxShowColNum) {
			showFromColNum++;
		} else {
			return;
		}
		checkScroll();
	}
	public static final int SCROL_BAR_W = 7;
	public void drawScrolBar(Graphics g, int x, int y, int w, int h) {
		long curPos = this.showFromColNum;
		long linesNumber = maxShowColNum;
		long sum = colNum;
		long barH = h * linesNumber / sum;
		long barY = curPos * h / sum;

		
		g.setColor(0x885c21);
		g.drawRect(x, y + (int) barY, w, (int) barH);
		g.fillRect(x, y + (int) barY, w, (int) barH);
		
	}
	
//	public void drawScrolBar(Graphics g, int x, int y, int h) {
////		GameCanvas.drawScrollBar(gg, g, x, y, h, colNum, maxShowColNum, showFromColNum);		
////		drawScrolBar(g, x, y, w, h);
//	}
	
}
