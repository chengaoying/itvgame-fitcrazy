package com.tvgame.ui;


import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.game.Game;
import com.tvgame.util.Util;


public class ScrolText extends ScrollArea{
	
	public String[] str;
	
	public int drawX;
	public int drawY;
	
	public int width;
	public int height;
	private int clipX,clipY,clipW,clipH;
	
	private Font font;
	
	public void init(String str,int x,int y,int w,int h,Font font){
		this.font = font;
		drawX = x;
		drawY = y;
		width = w;
		height = h;
		clipX = drawX+5;
		clipY = drawY+5;
		clipW = width-10-SCROL_BAR_W;
		clipH = height-10;		
		this.str = Util.convertRectString(font, str, clipW);
		super.init(0,this.str.length, 1, (clipH+space)/(font.getHeight()+space));				
	}
	
	
	public void draw(Graphics g){
		g.setColor(0xa77838);
		g.fillRect(drawX, drawY, width, height);
		g.setColor(0xffdb8c);
		for (int i = 0; i < maxShowColNum; i++) {
			if(i+showFromColNum<str.length)
				g.drawString(str[i+showFromColNum], clipX, clipY+i*(font.getHeight()+space), g.TOP|g.LEFT);
		}
		
		if(maxShowColNum<colNum)
			drawScrolBar(g, drawX + width - SCROL_BAR_W, drawY, SCROL_BAR_W, height);
	}
	public void update(KeyState key){
		if(key.containsAndRemove(KeyCode.UP)){			
			controlNextPage(KeyCode.UP);
		}else if(key.containsAndRemove(KeyCode.DOWN)){
			controlNextPage(KeyCode.DOWN);
		}else if(key.containsAndRemove(KeyCode.LEFT)){
			controlNextPage(KeyCode.LEFT);
		}else if(key.containsAndRemove(KeyCode.RIGHT)){
			controlNextPage(KeyCode.RIGHT);
		}
	}
	
	public void drawScrolBar(Graphics g, int x, int y, int w,int h) {
		
		h-=14;
		y+=7;
		
		
		long curPos = this.showFromColNum;
		long linesNumber = maxShowColNum;
		long sum = colNum;
		long barH = h * linesNumber / sum;
		long barY = curPos * h / sum;

//		int w = 7;
		
	
		
//		Image up = Game.loadImage(Res.UI_SCROL_UP_PNG);
//		Image down = Game.loadImage(Res.UI_SCROL_DOWN_PNG);
//		g.drawImage(up, x, y, g.BOTTOM|g.LEFT);
//		g.drawImage(down, x, y+h, g.TOP|g.LEFT);
		g.setColor(0x715330);
		g.fillRect(x, y, w, h);
		
		g.setColor(0xfbdc97);
		g.fillRect(x+1, y + (int) barY, w-2, (int) barH);
		g.setColor(0x118359);
		g.fillRect(x+2, y + (int) barY+1, w-4, (int) barH-2);
		
		
	}
}
