package com.tvgame.ui;

import javax.microedition.lcdui.Graphics;
/**
 * 
 * @author Shy.o
 *
 */
public class SelectHander {
	private int SUBINDEX = 0;

	private int SHOWINDEX = 0;

	private boolean isCycle;

	private int MAXINDEX;

	private int SHOWMAXINDEX;

	/**
	 * 构造索引
	 * 
	 * @param maxIndex
	 *            最大索引值
	 * @param drawIndex
	 *            显示最大索引值
	 * @param iscycle
	 *            是否循环
	 */
	public SelectHander(int maxIndex, int drawIndex, boolean iscycle) {
		// TODO Auto-generated constructor stub
		setMAXINDEX(maxIndex);
		if(drawIndex > maxIndex)
			drawIndex =  maxIndex;
		SHOWMAXINDEX = drawIndex;
		this.isCycle = iscycle;
	}

	public int getShowMaxIndex() {
		return SHOWMAXINDEX;
	}
	
	/**
	 * 设置最大显示索引
	 * @param new_maxIndex
	 */
	public void setShowMaxIndex(int new_maxIndex)
	{
		this.SHOWMAXINDEX = new_maxIndex;
	}

	/**
	 * 获取真实索引
	 * 
	 * @return
	 */
	public int getRealIndex() {
		return SUBINDEX + SHOWINDEX;
	}

	/**
	 * 获取显示索引
	 * 
	 * @return
	 */
	public int getShowIndex() {
		return SHOWINDEX;
	}
	
	/**
	 * 获取非显示索引
	 * 
	 * @return
	 */
	public int getSubIndex() {
		return SUBINDEX;
	}
	
	public void initIndex(){
		System.out.println("初始化SelectHanlder");
		SHOWINDEX = 0;
		SUBINDEX = 0;
	}
	
	/**
	 * 
	 * -1表示向一个选项
	 * 1 表示下一个选项
	 * 返回值false表示选项已经走到头儿了
	 * 
	 * @param sa
	 * @return
	 */
	public boolean Sub_Add(int sa) {
		boolean A = sa > 0;
		boolean S = sa < 0;
		if (isCycle) {
			if (A) {
				if (SHOWINDEX < SHOWMAXINDEX - sa)
					SHOWINDEX += sa;
				else {
					SUBINDEX += sa;
					if (getRealIndex() >= getMAXINDEX()) {
						SHOWINDEX = 0;
						SUBINDEX = 0;
					}
				}
			} else if (S) {
				if (SHOWINDEX > 0)
					SHOWINDEX += sa;
				else {
					SUBINDEX += sa;
					if (getRealIndex() < 0) {
						SHOWINDEX = SHOWMAXINDEX + sa;
						SUBINDEX = getMAXINDEX() - SHOWMAXINDEX;
					}
				}
			}
			return true;
		} else {
			if (A) {
				if (SHOWINDEX < SHOWMAXINDEX - sa)
					SHOWINDEX += sa;
				else {
					SUBINDEX += sa;
					if (getRealIndex() >= getMAXINDEX()) {
						SHOWINDEX = SHOWMAXINDEX - sa;
						SUBINDEX = getMAXINDEX() - SHOWMAXINDEX;
						return true;
					}
				}
			} else if (S) {
				if (SHOWINDEX > 0)
					SHOWINDEX += sa;
				else {
					SUBINDEX += sa;
					if (getRealIndex() < 0) {
						SHOWINDEX = 0;
						SUBINDEX = 0;
						return true;
					}
				}
			}
		}
		return false;
	}

	public void drawBar(Graphics g, int x, int y, int w, int h, int color) {
		if (SHOWMAXINDEX >= getMAXINDEX())
			return;
		int rh = h * SHOWMAXINDEX / getMAXINDEX();
		int ry = getRealIndex() * (h - rh) / (getMAXINDEX()-1);
		//因为非整除原因 这里进行原则上的四舍五入
		
		g.setColor(color);
		g.drawRect(x, y, w, h);
	
		g.fillRect(x, y + ry, w, rh);
	}

	public void setMAXINDEX(int mAXINDEX) {
		MAXINDEX = mAXINDEX;
	}

	public int getMAXINDEX() {
		return MAXINDEX;
	}
	
	/**
	 * 重置handler
	 * @param maxNumber
	 * @param showMaxNumber
	 */
	public void resetSelectHandler(int maxNumber,int showMaxNumber)
	{
		setMAXINDEX(maxNumber);
		setShowMaxIndex(showMaxNumber);
		initIndex();
	}
}
