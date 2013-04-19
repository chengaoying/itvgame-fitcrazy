package com.tvgame.ui;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.tvgame.util.Util;

public class ScrolTip {
	public void init(String str,int x,int y,int w,int h,Font font){
		drawTime = 0;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		int border = 3;
		clipX = x+border;
		clipY = y+border;
		clipW = w-border*2;
		clipH = h-border*2;
		drawY = 0;
		
		stringLine = Util.convertRectString(font, str, clipW);
		stringHeight = stringLine.length*(font.getHeight()+space) - space;
		
		this.font = font;
		
	}
	public void draw(Graphics g,int color){
		if(stringLine == null){
			return;
		}
		int cx = g.getClipX();
		int cy = g.getClipY();
		int cw = g.getClipWidth();
		int ch = g.getClipHeight();
//		g.setClip(x, y, w, h);
//		g.setColor(0x4d3a30);
//		g.fillRect(x, y, w, h);
		g.setClip(clipX, clipY, clipW, clipH);
		g.setColor(color);
		for (int i = 0; i < stringLine.length; i++) {
			g.drawString(stringLine[i], clipX, clipY+i*(font.getHeight()+space)+drawY, g.TOP|g.LEFT);
		}
		g.setClip(cx, cy, cw, ch);
	}
	public void update(){
		if(clipH<=stringHeight){
			drawTime++;
		}else {
			return;
		}
		if(drawTime>18){
			drawY-=1;
		}
		
		if(drawY+stringHeight<0){
			drawTime = 0;
			drawY = 0;
		}
	}
	public void destroyStringLine(){
		stringLine = null;
	}
	private int drawTime;
	private String[] stringLine;
	private int x,y,w,h;
	private int clipX,clipY,clipW,clipH;
	private int stringHeight;
	private int drawY;
	private int space = 1;
	private Font font;
	
}
