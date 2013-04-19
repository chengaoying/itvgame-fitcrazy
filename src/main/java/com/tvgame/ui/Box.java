package com.tvgame.ui;


import javax.microedition.lcdui.Graphics;

import com.tvgame.game.Game;
/**
 * 
 * @author Shy.o
 *
 */
public class Box {
	public int x;

	public int y;

	public int w;

	public int h;

	public int INDEX;

	public final int Flag_STYLE_NORMAL = 1;

	public final int Flag_STYLE_HASRECT = 1 << 1;

	public final int Flag_STYLE_HASCORNER = 1 << 2;
	
	public int Style_Flag = 1;
	
	public SelectHander SH;

	public int SelectColor[] = { 0x908d36, 0xA87837 };
	
	public int RectColot = 0xffffff;
	
	public Box(SelectHander sh,int id,int x,int y,int w,int offsetH) {
		// TODO Auto-generated constructor stub
		this.SH = sh;
		INDEX = id;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = Bitmapfonts.BitFontH + offsetH * 2;
		
//		SetStyleFlag(Flag_STYLE_NORMAL | Flag_STYLE_HASRECT | Flag_STYLE_HASCORNER , true);
	}

	private void drawNormalBox(Graphics g) {
		g.setColor(SelectColor[SH.getShowIndex() == INDEX ? 0 : 1]);
		g.fillRect(x, y, w, h);
		if(SH.getShowIndex() == INDEX){
			g.setColor(RectColot);
			g.drawRect(x, y, w, h);
		}
		
	}
	private void drawCorner(Graphics g) {

	}

	private void drawrect(Graphics g) {
		g.setColor(RectColot);
		g.drawRect(x, y, w, h);
	}
	
	private boolean HasStyleFlag(int flag){
		return (Style_Flag & flag) != 0;
	}
	
	public void SetStyleFlag(int flag,boolean add){
		if(add)
		Style_Flag |= flag;
		else Style_Flag &= ~flag;
	}
	
	public void drawBox(Graphics g){
		if(HasStyleFlag(Flag_STYLE_NORMAL)) drawNormalBox(g);
		if(HasStyleFlag(Flag_STYLE_HASRECT)) drawrect(g);
		if(HasStyleFlag(Flag_STYLE_HASCORNER)) drawCorner(g);
	}
	
}
