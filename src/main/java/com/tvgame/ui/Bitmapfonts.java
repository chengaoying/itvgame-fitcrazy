package com.tvgame.ui;


import java.io.DataInputStream;
import java.io.InputStream;

import javax.microedition.lcdui.Graphics;

import com.tvgame.game.Game;

public class Bitmapfonts {

	static public int BitFontW = 12;

	static public int BitFontH = 12;

	static public void drawmat(byte[] mat, Graphics g, int matsize, int x,
			int y, int color)
	/* 依次：字模指针、点阵大小、起始坐标(x,y)、颜色 */
	{
		
		int i16; // 两字节一行,即16位
		for (int line = 0; line < matsize; line++) {
			if (matsize == 12) {
				g.setColor(color);
			} else if (matsize == 16) {
				g.setColor(color);
			}

			i16 = mat[line << 1] & 0x000000ff;
			i16 = i16 << 8 | (mat[(line << 1) + 1] & 0x000000ff); // 16位整形值,注意先通过与运算转为int
			for (int i = 0; i < matsize; i++) {
				if ((i16 & (0x8000 >> i)) != 0) { // 逐位测试：通过与1进行与运算
					g.drawLine(x + i, y + line, x + i, y + line);
				}
			}
		}
	}

	/**
	 * 渐变画法 by/Shy.O
	 * @param mat
	 * @param g
	 * @param matsize
	 * @param x
	 * @param y
	 * @param color
	 * @param color1
	 */
	static public void drawmat(byte[] mat, Graphics g, int matsize, int x,
			int y, int color,int color1)
	/* 依次：字模指针、点阵大小、起始坐标(x,y)、颜色 */
	{
		
		int Color = 0;
		int R1 = color >> 16;
		int G1 = ( color >> 8 ) & 0xff;
		int B1 = color & 0xff ;
		int R2 = color1 >> 16;
		int G2 = ( color1 >> 8 ) & 0xff;
		int B2 = color1 & 0xff ;
		int tr = (R2 -R1) / 12;
		int tg = (G2 - G1) / 12; 
		int tb = (B2 - B1) / 12;
		int i16; // 两字节一行,即16位
		for (int line = 0; line < matsize; line++) {
			if (matsize == 12) {
				//渐变字
				R1 += tr;
				G1 += tg;
				B1 += tb;
				if(R1 < 0)
					R1 = 0;
				if(G1 < 0)
					G1 = 0;
				if(B1 < 0)
					B1 = 0;
				if(R1 > 0xff)
					R1 = 0xff;
				if(G1 > 0xff)
					G1 = 0xff;
				if(G1 > 0xff)
					G1 = 0xff;
				Color = (R1 << 16) | (G1 << 8) | B1;
				g.setColor(Color);
			} else if (matsize == 16) {
				g.setColor(color);
			}

			i16 = mat[line << 1] & 0x000000ff;
			i16 = i16 << 8 | (mat[(line << 1) + 1] & 0x000000ff); // 16位整形值,注意先通过与运算转为int
			for (int i = 0; i < matsize; i++) {
				if ((i16 & (0x8000 >> i)) != 0) { // 逐位测试：通过与1进行与运算
					g.drawLine(x + i, y + line, x + i, y + line);
//					g.fillRect(x + i, y + line, 1, 1);
				}
			}
		}
	}
	
	public static String SingleSpaceWord = "0123456789(),!@#$%&*{}[]<>;-+':/?., ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static final int FLAG_RIGHT = 2;

	public static final int FLAG_HCENTER = 1;

	public static final int FLAG_LEFT = 0;

	public static int drawFont12_S(Graphics g, String s, int x, int y,
			int type, int spacetype, int color) {
		int reallengh = x ;
		int offset = 0;
		for (int i = 0; i < s.length(); i++) {
			int sp = BitFontW;
				char str = s.charAt(i);
				if (SingleSpaceWord.indexOf(str) != -1) {
					sp = BitFontW / 2;
				}
			offset += (sp + spacetype);
		}
		switch (type) {
		case FLAG_HCENTER:
			reallengh -= offset / 2;
			break;
		case FLAG_RIGHT:
			reallengh -= offset;
			break;
		default:

			break;
		}
		for (int i = 0; i < s.length(); i++) {
			
				int sp = BitFontW;
				if (i >= 1) {
					String str = s.substring(i - 1, i);
					if (SingleSpaceWord.indexOf(str) != -1) {
						sp = BitFontW / 2;
					}
					reallengh += (sp + spacetype);
				}
				try {
				drawmat(getFont12Data(s.substring(i, i + 1)), g, 12, reallengh,
						y, color);
				
			} catch (Exception e) {
				// e.printStackTrace();
				 g.setColor(color);
				 g.drawString(s.substring(i, i + 1), reallengh, y, 0);
				 System.out.println("字库中找不到："+s.substring(i, i + 1));
//				 Game.Debug(null, "字库中找不到：" + s.substring(i, i + 1));
			}
		}
		return offset;
	}

	/**
	 *  渐变点阵字 by/Shy.O
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 * @param type
	 * @param spacetype
	 * @param color 基础色 
	 * @param color1 渐变色
	 * @return
	 */
	public static int drawFont12_S_SHADE(Graphics g, String s, int x, int y,
			int type, int spacetype, int color,int color1) {
		int reallengh = x ;
		int offset = 0;
		for (int i = 0; i < s.length(); i++) {
			int sp = BitFontW;
				char str = s.charAt(i);
				if (SingleSpaceWord.indexOf(str) != -1) {
					sp = BitFontW / 2;
				}
			offset += (sp + spacetype);
		}
		switch (type) {
		case FLAG_HCENTER:
			reallengh -= offset / 2;
			break;
		case FLAG_RIGHT:
			reallengh -= offset;
			break;
		default:

			break;
		}
		for (int i = 0; i < s.length(); i++) {
			
				int sp = BitFontW;
				if (i >= 1) {
					String str = s.substring(i - 1, i);
					if (SingleSpaceWord.indexOf(str) != -1) {
						sp = BitFontW / 2;
					}
					reallengh += (sp + spacetype);
				}
				try {
				drawmat(getFont12Data(s.substring(i, i + 1)), g, 12, reallengh,
						y, color,color1);
				
			} catch (Exception e) {
				// e.printStackTrace();
				 g.setColor(color);
				 g.drawString(s.substring(i, i + 1), reallengh, y, 0);
				// Game.Debug(null, "字库中找不到：" + s.substring(i, i + 1));
			}
		}
		return offset;
	}
	
	static byte[][] FontData12;

	public static void readfont12data(String s) {
		try {
			InputStream Input = "".getClass().getResourceAsStream(s);
			DataInputStream din = new DataInputStream(Input);
			FontData12 = new byte[FONT12_S.length()][24];

			for (int i = 0; i < FontData12.length; i++) {
				for (int j = 0; j < FontData12[0].length; j++) {
					FontData12[i][j] = din.readByte();
				}
			}
			din.close();
		} catch (Exception e) {
		}
	}

	public static byte[] getFont12Data(String s) throws Exception {
		try {
			return FontData12[FONT12_S.indexOf(s)];
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	static String FONT12_S = "为分积前当s双无将猛锋先士勇区社Q则规询查励奖具防数器武命生高最级等色角王银两万套选择章节虎牢关常山下邳城外退出请及时存档,是否?新进入游戏删除赠送替身令牌x2购买自动复活覆盖向移空中道商店保度地图提示音乐联网挑战返回主菜单包背SOB秀优品极好较良精般一质破眩晕血吸击暴毒麟麒凌欺白玄魂之雀朱龙青L足不料材者耗消续继强化后的效果/-+用使币按013456789()!@#$%&*{}[]<>;':. abcdefghijklmnopqrtuvwyz率功成小大超石计合量取定确格价子烤肉酒木打折卡铁戟画方天刃鬼死神黄金炎冰电皮甲铠霸影闪避坚固缝炼准备注属性技能气攻御系统特附加连环突刺邪恶霹雳怒吼风壮体力躯键？：，。置设升省征装看镰刷开启售经验钱收集火雷暗杀丹药瓶";
}
