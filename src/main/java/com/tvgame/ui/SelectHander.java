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
	 * ��������
	 * 
	 * @param maxIndex
	 *            �������ֵ
	 * @param drawIndex
	 *            ��ʾ�������ֵ
	 * @param iscycle
	 *            �Ƿ�ѭ��
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
	 * ���������ʾ����
	 * @param new_maxIndex
	 */
	public void setShowMaxIndex(int new_maxIndex)
	{
		this.SHOWMAXINDEX = new_maxIndex;
	}

	/**
	 * ��ȡ��ʵ����
	 * 
	 * @return
	 */
	public int getRealIndex() {
		return SUBINDEX + SHOWINDEX;
	}

	/**
	 * ��ȡ��ʾ����
	 * 
	 * @return
	 */
	public int getShowIndex() {
		return SHOWINDEX;
	}
	
	/**
	 * ��ȡ����ʾ����
	 * 
	 * @return
	 */
	public int getSubIndex() {
		return SUBINDEX;
	}
	
	public void initIndex(){
		System.out.println("��ʼ��SelectHanlder");
		SHOWINDEX = 0;
		SUBINDEX = 0;
	}
	
	/**
	 * 
	 * -1��ʾ��һ��ѡ��
	 * 1 ��ʾ��һ��ѡ��
	 * ����ֵfalse��ʾѡ���Ѿ��ߵ�ͷ����
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
		//��Ϊ������ԭ�� �������ԭ���ϵ���������
		
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
	 * ����handler
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
