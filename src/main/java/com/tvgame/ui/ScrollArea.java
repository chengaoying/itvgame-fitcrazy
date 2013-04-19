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
//	public int w;//滚动条宽度
//	public int h;//滚动条高度

	public int borderWidth = 0;// 选项与边缘的宽度
	public int space = 1;// 选项之间的宽度

//	public int optionW;// 选项的宽度
//	public int optionH;// 选项的高度

	public int curPos;

	public int rowNum;// 横向选项的数量
	public int colNum;// 纵向选项的数量

	public int showFromColNum;// 从第几行显示

	public int maxShowRowNum = 4;// 能显示几行
	public int maxShowColNum = 2;// 能显示几列

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
 * 初始化
 * @param curPos 当前光标位置
 * @param optionW 选项宽度
 * @param optionH 选项高度
 * @param optionNum 选项数量
 * @param maxShowRowNum 最大显示列数
 * @param maxShowColNum 最大显示行数
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
	 * 翻页
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
