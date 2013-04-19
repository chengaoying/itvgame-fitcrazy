package com.tvgame.ui;


import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.game.Game;

/**
 * 
 * @author Shy.o
 *
 */
public class BoxPlane implements UiObject{
	public Box[] BasicBox;
	public String info[];
	public int DrawinfoY;
	public int Plane_X;
	public int Plane_Y;
	public int Plane_W;
	public int Plane_H;
	public int BoxOffsetH = 4;
	public int BoxOffsetW = 4;
	public String Title[] = {""};
	public int Tilte_Y;
	public int TitleColor = 0x4D3A30;
	public int PlaneColor = 0xffdf8c;
	public SelectHander sh;
	public int strColor[] = { 0xffffff,  0x4D3A30 };
	public static final int FLAG_Y_VCENTER = 1;
	public static final int FLAG_Y_TOP = 1 << 1;
	public static final int FLAG_Y_BOTTOM = 1 << 2;
	public static final int FLAG_X_HCENTER = 1 << 3;
	public static final int FLAG_X_RIGHT = 1 << 4;
	public static final int FLAG_X_LEFT = 1 << 5;
	
	public int BoxStyle;
	public static final int Style_SelectBox = 0;//选项盒子
	public static final int Style_YesNoBox = 1;//是否盒子
	public static final int Style_NormalBox = 2;//正常盒子
	
	public static final int MAXBOX = 5;
	public int ButtonID;
	
	public int Flag;
	/**
	 * 带有选项的盒子
	 * 
	 * @param sh 选项句柄
	 * @param info 选项信息
	 * @param title 选项标题 如果无则 NULL
	 * @param x 
	 * @param y
	 * @param w
	 * @param offsetW 
	 * @param offsetH
	 * @param ButtonID 按键
	 * @param Flag 排列标示
	 */
	public BoxPlane(SelectHander sh,String info[],String title,int x,int y,int w,int offsetW,int offsetH,int ButtonID,int Flag){
		this.Title[0] = title;
		this.sh = sh;
		this.ButtonID = ButtonID;
		this.info = info;
		int fontH = Bitmapfonts.BitFontH;
		BoxOffsetH = offsetH;
		Plane_W = w;
		int boxcounts = sh.getShowMaxIndex();
		int BoxH = BoxOffsetH * 2 + fontH;
		Plane_H = offsetH * ( boxcounts +1) + BoxH * boxcounts + ((title != null ) ? (offsetH + fontH) : 0) + 1;
		this.Flag = Flag;
		if((Flag & FLAG_X_HCENTER) != 0)
			x -= w >> 1;
		if((Flag & FLAG_Y_VCENTER) != 0)
			y -= Plane_H >> 1;
		if((Flag & FLAG_X_RIGHT) != 0)
			x -= w ;
		if((Flag & FLAG_Y_BOTTOM) != 0)
			y -= Plane_H ;
			
		Plane_X = x;
		Plane_Y = y;
		Tilte_Y = Plane_Y + offsetH;
		
		int BoxW = Plane_W - offsetW * 2 - 1;
		int BoxX = Plane_X + offsetW;
		
		BasicBox = new Box[boxcounts];
		
		DrawinfoY = y + offsetH + ((title != null ) ? (offsetH + fontH) : 0);
		for (int i = 0; i < boxcounts; i++) {
			int BoxY = DrawinfoY + i * (offsetH + BoxH);
			BasicBox[i] = new Box(this.sh,i,BoxX,BoxY,BoxW,BoxOffsetH);
		}
	}
	
	/**
	 * 带有选项的盒子，无标题
	 * @param sh
	 * @param info
	 * @param x
	 * @param y
	 * @param w
	 * @param offsetW
	 * @param offsetH
	 * @param ButtonID
	 * @param Flag
	 */
	public BoxPlane(SelectHander sh,String info[],int x,int y,int w,int offsetW,int offsetH,int ButtonID,int Flag){
		this.sh = sh;
		this.ButtonID = ButtonID;
		this.info = info;
		int fontH = Bitmapfonts.BitFontH;
		BoxOffsetH = offsetH;
		Plane_W = w;
		int boxcounts = sh.getShowMaxIndex();
		int BoxH = BoxOffsetH * 2 + fontH;
		Plane_H = offsetH * ( boxcounts +1) + BoxH * boxcounts + 1;
		this.Flag = Flag;
		if((Flag & FLAG_X_HCENTER) != 0)
			x -= w >> 1;
		if((Flag & FLAG_Y_VCENTER) != 0)
			y -= Plane_H >> 1;
		if((Flag & FLAG_X_RIGHT) != 0)
			x -= w ;
		if((Flag & FLAG_Y_BOTTOM) != 0)
			y -= Plane_H ;
			
		Plane_X = x;
		Plane_Y = y;
		Tilte_Y = Plane_Y + offsetH;
		
		int BoxW = Plane_W - offsetW * 2 - 1;
		int BoxX = Plane_X + offsetW;
		
		BasicBox = new Box[boxcounts];
		
		DrawinfoY = y + offsetH +  0;
		for (int i = 0; i < boxcounts; i++) {
			int BoxY = DrawinfoY + i * (offsetH + BoxH);
			BasicBox[i] = new Box(this.sh,i,BoxX,BoxY,BoxW,BoxOffsetH);
		}
	}
	
	boolean isRow;
	/**
	 *  选项横向排列
	 * @param sh
	 * @param info
	 * @param title
	 * @param x
	 * @param y
	 * @param w
	 * @param offsetW
	 * @param offsetH
	 * @param ButtonID
	 */
	public BoxPlane(SelectHander sh,String info[],String title,int x,int y,int w,int offsetW,int offsetH,int ButtonID){
		isRow = true;
		this.Title[0] = title;
		this.sh = sh;
		this.ButtonID = ButtonID;
		this.info = info;
		int fontH = Bitmapfonts.BitFontH;
		BoxOffsetH = offsetH;
		BoxOffsetW = offsetW;
		Plane_W = w;
		int boxcounts = sh.getShowMaxIndex();
		int BoxH = BoxOffsetH * 2 + fontH;
		Plane_H = offsetH * 2 + BoxH  + ((title != null ) ? (offsetH + fontH) : 0) + 1;
			x -= w >> 1;
			y -= Plane_H >> 1;
		Plane_X = x;
		Plane_Y = y;
		Tilte_Y = Plane_Y + offsetH;
		
		int BoxW = (Plane_W - offsetW * (info.length + 1))/info.length;
		int BoxX = Plane_X + offsetW;
		
		BasicBox = new Box[boxcounts];
		
		DrawinfoY = y + offsetH + ((title != null ) ? (offsetH + fontH) : 0);
		for (int i = 0; i < boxcounts; i++) {
			int BoxY = DrawinfoY ;
			int BoxX_ = BoxX +  i * (offsetW + BoxW);
			BasicBox[i] = new Box(this.sh,i,BoxX_,BoxY,BoxW,BoxOffsetH);
		}
	}
	
	/**
	 * 带有选项 标题可以分行的盒子
	 * @param sh
	 * @param info
	 * @param title
	 * @param x
	 * @param y
	 * @param w
	 * @param offsetW
	 * @param offsetH
	 * @param ButtonID
	 * @param Flag
	 */
	public BoxPlane(SelectHander sh,String info[],String title[],int x,int y,int w,int offsetW,int offsetH,int ButtonID,int Flag){
		this.Title = title;
		this.sh = sh;
		this.ButtonID = ButtonID;
		this.info = info;
		int fontH = Bitmapfonts.BitFontH;
		BoxOffsetH = offsetH;
		Plane_W = w;
		int boxcounts = sh.getShowMaxIndex();
		int BoxH = BoxOffsetH * 2 + fontH;
		
		Plane_H = offsetH * ( boxcounts +1) + BoxH * boxcounts + ((title != null ) ? (offsetH + fontH * title.length)  : 0) + 1;
		
		this.Flag = Flag;
		if((Flag & FLAG_X_HCENTER) != 0)
			x -= w >> 1;
		if((Flag & FLAG_Y_VCENTER) != 0)
			y -= Plane_H >> 1;
		if((Flag & FLAG_X_RIGHT) != 0)
			x -= w ;
		if((Flag & FLAG_Y_BOTTOM) != 0)
			y -= Plane_H ;
			
		Plane_X = x;
		Plane_Y = y;
		Tilte_Y = Plane_Y + offsetH;
		
		int BoxW = Plane_W - offsetW * 2 - 1;
		int BoxX = Plane_X + offsetW;
		
		BasicBox = new Box[boxcounts];
		
		DrawinfoY = y + offsetH + ((title != null ) ? (offsetH + fontH * title.length) : 0);
		for (int i = 0; i < boxcounts; i++) {
			int BoxY = DrawinfoY + i * (offsetH + BoxH);
			BasicBox[i] = new Box(this.sh,i,BoxX,BoxY,BoxW,BoxOffsetH);
		}
	}
	/**
	 * 大面板颜色
	 * 文字选中颜色
	 * 文字未选中颜色
	 * 盒子选中颜色
	 * 盒子未选中颜色
	 * 盒子边框颜色
	 * @param color
	 */
	public void changeBoxColor(int color[]){
		PlaneColor = color[0];
		strColor[0] =  color[1];       
		strColor[1] =  color[2];
		for (int i = 0; i < BasicBox.length; i++) {
			BasicBox[i].SelectColor[0] = color[3];
			BasicBox[i].SelectColor[1] = color[4];
			BasicBox[i].RectColot = color[5];
		}
	}
	
	public void setBoxw(int w){
		for (int i = 0; i < BasicBox.length; i++) {
			BasicBox[i].w = w;
			if((Flag & FLAG_X_HCENTER) != 0)
				BasicBox[i].x += w ;
//			if((Flag & FLAG_Y_VCENTER) != 0)
//				BasicBox[i].y -= Plane_H >> 1;
			if((Flag & FLAG_X_RIGHT) != 0)
				BasicBox[i].x += w ;
//			if((Flag & FLAG_Y_BOTTOM) != 0)
//				BasicBox[i].y -= Plane_H ;
		}
	}
	public BoxPlane(){
		
	}
	public void drawPlane(Graphics g){
		
		g.setColor(PlaneColor);
		g.fillRect(Plane_X, Plane_Y, Plane_W, Plane_H);
		if(Title!=null){
			g.setColor(TitleColor);
//			AdvancedGraphics.getInstance(g).drawString(Title, Plane_X + Plane_W / 2, Tilte_Y, Graphics.TOP | Graphics.HCENTER);
			for (int i = 0; i < Title.length; i++) {
				Bitmapfonts.drawFont12_S(g, Title[i] ,Plane_X + Plane_W / 2, Tilte_Y + i * Bitmapfonts.BitFontH, Bitmapfonts.FLAG_HCENTER, 0, TitleColor);
			}
		}
		

	}
	private void drawStr(Graphics g,int x,int y,String str,int id){
//		g.setColor(strColor[sh.getRealIndex() == id ? 0 : 1]);
//		AdvancedGraphics.getInstance(g).drawString(str, x, y , Graphics.TOP | Graphics.HCENTER);
		if(str.equals("道具商店")){
			Bitmapfonts.drawFont12_S_SHADE(g, str, x, y, Bitmapfonts.FLAG_HCENTER, 0, 0xFFF800 ,0xFF7502);
		}
		else
		Bitmapfonts.drawFont12_S(g, str, x, y, Bitmapfonts.FLAG_HCENTER, 0, strColor[sh.getRealIndex() == id ? 0 : 1]);
	}
	public void draw(Graphics g) {
		drawPlane(g);
		if(BasicBox != null)
		for (int i = 0; i < BasicBox.length; i++) {
			BasicBox[i].drawBox(g);
			if(isRow)
				drawStr(g, BasicBox[i].x + BasicBox[i].w / 2  ,BoxOffsetH + DrawinfoY , info[sh.getSubIndex()+ i], sh.getSubIndex() + i);
			else
			drawStr(g, Plane_X + Plane_W / 2,BoxOffsetH + DrawinfoY + i * (BoxOffsetH  +BasicBox[i].h), info[sh.getSubIndex()+ i], sh.getSubIndex() + i);
		}
		
		sh.drawBar(g, Plane_X + Plane_W - 3, Plane_Y, 2, Plane_H, 0x4D3A30);
	}
	public void update(KeyState key) {
		// TODO Auto-generated method stub
		if(isRow){
			if(key.containsAndRemove(KeyCode.RIGHT)){
				sh.Sub_Add(1);
			}else if(key.containsAndRemove(KeyCode.LEFT)){
				sh.Sub_Add(-1);
			}
		}else{
		if(key.containsAndRemove(KeyCode.DOWN)){
			sh.Sub_Add(1);
		}else if(key.containsAndRemove(KeyCode.UP)){
			sh.Sub_Add(-1);
		}
		}
		if(key.containsAndRemove(KeyCode.OK)){
			Game.popStack();
		}
	}
}
