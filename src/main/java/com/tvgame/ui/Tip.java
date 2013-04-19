package com.tvgame.ui;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.game.Game;
import com.tvgame.util.GraphicsUtil;

public class Tip implements UiObject{
	
	int Time;
	int width,height;
	int x,y;
	int count;
	Text text;
	public Tip(String str,int w ,int h,int loopCount){
		width  = w;
		height = h;
		x = (Const.WIDTH-w)>>1;
		y = (Const.HEIGHT-h)>>1;
		Time   = loopCount;
		text= new Text();
		text.setContent( x+2, y+2, w-4, h-4, Const.smallFont, str, 0x0, true, true);
	}
	public Tip(String str,int loopCount){
		this(str,200,100,loopCount);
	}
	public Tip(String str){
		this(str,30);
	}
	public Tip(String str,int w,int h){
		this(str, w, h,30);
	}
	
	public void draw(Graphics g){
	
		GraphicsUtil.drawFrame(g, x, y, width, height);
		text.paint(g);
	}
	public void update(KeyState key){
		if(Time>0){
			if(count++>Time){
				Game.getInstance().popStack();
				return ;
			}
		}
		if (key.containsAndRemove(KeyCode.OK)) {
			Game.getInstance().popStack();
		}
		text.keyProcess(key);
	}
}
