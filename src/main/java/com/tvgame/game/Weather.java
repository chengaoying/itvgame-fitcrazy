package com.tvgame.game;


import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

//import com.sun.j2me.global.Resource;
import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;

public class Weather {
	/***************************************************************************
	 * 天气效果
	 */
	public static boolean IsWeatherEffect;

	public static int Weather_Index;

//	public static final int Weather_NONE = -1; //没有任何天气
	
	public static final int Weather_Slow = 0;

	public static final int Weather_Rain = 1;

	public static final int Weather_Sunny = 2;
	
//	public static final int Weather_Autumn = 3;

	public static void SetWeather(int Weather) {// 设置天气
		Weather_Index = Weather;
		// Weather_Index = Weather_Rain;
		switch (Weather_Index) {
		case Weather_Slow:
			initSlow();
			break;
		case Weather_Rain:
			initRain();
			break;
		case Weather_Sunny:
			//#if !JAR_300K
//			loadImage(Res.LIGHT_PNG);
			//#endif
			break;
//		case Weather_Autumn://秋天
//			break;
		}
		IsWeatherEffect = true;
	}

	
	public static void DrawWeaher(Graphics g) {// 绘制天气
		switch (Weather_Index) {
		case Weather_Slow:// 下雪
			DrawSlow(g);
			break;
		case Weather_Rain:// 下雨
			DrawRain(g);
			break;
		case Weather_Sunny://春天 晴天
			DrawLight(g);
			break;
			
//		case Weather_NONE://没有任何天气
//			break;
//		case Weather_Autumn://秋天
//			DrawAutumn(g);
//			break;
		}
	}

	public static void CloseWeather() {
		IsWeatherEffect = false;
	}

	private static void DrawRain(Graphics g) {
		g.setColor(0xccffff);
		for (int i = 0; i < point_x.length; i++) {
			int offsetx = offset[i] ;
			while (point_x[i] + offsetx < 0) {
				offsetx += Const.WIDTH;
			}
			while (point_x[i] + offsetx > Const.WIDTH) {
				offsetx -= Const.WIDTH;
			}
			g.drawLine(point_x[i] + offsetx, point_y[i] ,
					point_x[i] + offsetx - 2, point_y[i] 
							+ len[i]);
		}

	}

	// 雪地效果
	private static void DrawSlow(Graphics g) {
		for (int i = 0; i < point_x.length; i++) {
			int offsetx = offset[i] ;
			if (point_x[i] + offsetx < 0) {
				offsetx += Const.WIDTH;
			}
			if (point_x[i] + offsetx > Const.WIDTH) {
				offsetx -= Const.WIDTH;
			}
			DrawArc(g, point_x[i] + offsetx, point_y[i] , len[i]);
		}
	}

	private static void DrawLight(Graphics g)
	{
		g.drawImage(Resources.loadImage(Resources.IMG_ID_LIGHT), 0, 0, 20);
		
	}
	
//	private static void DrawAutumn(Graphics g)
//	{
//		int light_w = Resources.loadImage(Resources.IMG_ID_LIGHT).getWidth();
//		int light_h = Resources.loadImage(Resources.IMG_ID_LIGHT).getHeight();
//		g.drawRegion(Resources.loadImage(Resources.IMG_ID_LIGHT), 0, 0, light_w, light_h, Sprite.TRANS_ROT90,  Const.WIDTH-light_w+10, 0, 20);
//	}
	
	public static void UpdateWeather() {
		switch (Weather_Index) {
		case Weather_Slow:
			for (int i = 0; i < point_x.length; i++) {
				point_y[i] += len[i] >> 2;
				if (boffset[i]) {
					offset[i]--;
					if (offset[i] <= 0) {
						boffset[i] = false;
					}
				} else {
					offset[i]++;
					if (offset[i] >= len[i] << 2) {
						boffset[i] = true;
					}
				}
				if (point_y[i]  > Const.HEIGHT) {
					point_y[i] = -Util.getRandom(0, EFFECTSPACE * 2);
					len[i] = 4 + Util.getRandom(0, 16);
				}
			}
			break;

		case Weather_Rain:
			int popnum = 0;
			for (int i = 0; i < point_x.length; i++) {
				point_y[i] += 16;
				offset[i] -= 2;
				if (point_y[i]  > Const.HEIGHT) {
					offset[i] = 0;
					point_y[i] = -Util.getRandom(0, EFFECTSPACE * 2);
					len[i] = Util.getRandom(6, 10);
				}
				if (Util.getRandom(0, point_x.length) < i
						&& popnum < popnum - 1) {
					if (Papr[popnum] == 0) {
						Papx[popnum] = point_x[i] + offset[i];
						Papy[popnum] = point_y[i];
						offset[i] = 0;
						point_y[i] = -Util.getRandom(0, EFFECTSPACE * 2);
						len[i] = Util.getRandom(6, 10);
					}
					popnum++;
				}
			}

			for (int i = 0; i < popnum; i++) {
				if (Papr[i] < 4)
					Papr[i]++;
				else {
					Papx[i] = -1;
					Papr[i] = 0;
				}
			}
			break;
		}
	}

	// static boolean iSPaop[] ;
	static int Papx[];

	static int Papy[];

	static int Papr[];

	static int popnum = 20;

	private static void initRain() {
		EFFECTSPACE = 35;
		Papx = new int[popnum];
		Papy = new int[popnum];
		Papr = new int[popnum];
		point_x = new int[Const.WIDTH * Const.HEIGHT / EFFECTSPACE / EFFECTSPACE];
		point_y = new int[point_x.length];
		len = new int[point_x.length];
		offset = new int[point_x.length];
		boffset = new boolean[point_x.length];
		NUM_V = Const.WIDTH / EFFECTSPACE;
		NUM_H = Const.HEIGHT / EFFECTSPACE;
		for (int i = 0; i < point_y.length; i++) {
			point_y[i] = (i / NUM_V) * EFFECTSPACE
					+ Util.getRandom(0, EFFECTSPACE) ;
			point_x[i] = (i % NUM_H) * EFFECTSPACE * 2
					+ Util.getRandom(0, EFFECTSPACE) 
					- Const.WIDTH_HALF;
			len[i] = 4 + Util.getRandom(0, 2);
			offset[i] = len[i];
		}
	}

	private static void initSlow() {
		EFFECTSPACE = 25;
		
		point_x = new int[Const.WIDTH * Const.HEIGHT / EFFECTSPACE / EFFECTSPACE];
		point_y = new int[point_x.length];
		len = new int[point_x.length];
		offset = new int[point_x.length];
		boffset = new boolean[point_x.length];
		NUM_V = Const.WIDTH / EFFECTSPACE;
		NUM_H = Const.HEIGHT / EFFECTSPACE;
		for (int i = 0; i < point_y.length; i++) {
			point_y[i] = (i / NUM_V) * EFFECTSPACE
					+ Util.getRandom(0, EFFECTSPACE) ;
			point_x[i] = (i % NUM_H) * EFFECTSPACE * 2
					+ Util.getRandom(0, EFFECTSPACE) 
					- Const.WIDTH_HALF;
			len[i] = 4 + Util.getRandom(0, 16);
			offset[i] = len[i];
		}
	}

	
	
	private static void ReleaseWeather() {
		point_x = null;
		point_y = null;
		len = null;
		offset = null;
		boffset = null;
		IsWeatherEffect = false;
	}

	private static void DrawArc(Graphics g, int x, int y, int len) {
		g.setColor(0xffffff);
		int l = len / 4;
		switch (l) {
		case 1:
			g.drawLine(x, y, x, y);
			break;
		case 2:
			g.drawLine(x, y, x + 1, y + 1);
			g.drawLine(x, y, x - 1, y + 1);
			break;
		case 3:
			g.fillRect(x, y, 2, 2);
			break;
		case 4:
			g.fillRect(x + 1, y + 1, 2, 2);
			g.drawLine(x, y, x + 3, y + 3);
			g.drawLine(x, y + 3, x + 3, y);
			break;
		default:
			break;
		}
		// g.fillArc(x, y, l, l, 0, 360);
	}

	private static int EFFECTSPACE = 25;

	private static int NUM_V;

	private static int NUM_H;

	private static int point_x[];

	private static int point_y[];

	private static int len[];

	private static int offset[];

	private static boolean boffset[];

}
