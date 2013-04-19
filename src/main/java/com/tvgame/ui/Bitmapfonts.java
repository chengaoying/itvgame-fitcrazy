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
	/* ���Σ���ģָ�롢�����С����ʼ����(x,y)����ɫ */
	{
		
		int i16; // ���ֽ�һ��,��16λ
		for (int line = 0; line < matsize; line++) {
			if (matsize == 12) {
				g.setColor(color);
			} else if (matsize == 16) {
				g.setColor(color);
			}

			i16 = mat[line << 1] & 0x000000ff;
			i16 = i16 << 8 | (mat[(line << 1) + 1] & 0x000000ff); // 16λ����ֵ,ע����ͨ��������תΪint
			for (int i = 0; i < matsize; i++) {
				if ((i16 & (0x8000 >> i)) != 0) { // ��λ���ԣ�ͨ����1����������
					g.drawLine(x + i, y + line, x + i, y + line);
				}
			}
		}
	}

	/**
	 * ���仭�� by/Shy.O
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
	/* ���Σ���ģָ�롢�����С����ʼ����(x,y)����ɫ */
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
		int i16; // ���ֽ�һ��,��16λ
		for (int line = 0; line < matsize; line++) {
			if (matsize == 12) {
				//������
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
			i16 = i16 << 8 | (mat[(line << 1) + 1] & 0x000000ff); // 16λ����ֵ,ע����ͨ��������תΪint
			for (int i = 0; i < matsize; i++) {
				if ((i16 & (0x8000 >> i)) != 0) { // ��λ���ԣ�ͨ����1����������
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
				 System.out.println("�ֿ����Ҳ�����"+s.substring(i, i + 1));
//				 Game.Debug(null, "�ֿ����Ҳ�����" + s.substring(i, i + 1));
			}
		}
		return offset;
	}

	/**
	 *  ��������� by/Shy.O
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 * @param type
	 * @param spacetype
	 * @param color ����ɫ 
	 * @param color1 ����ɫ
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
				// Game.Debug(null, "�ֿ����Ҳ�����" + s.substring(i, i + 1));
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

	static String FONT12_S = "Ϊ�ֻ�ǰ��s˫�޽��ͷ���ʿ������Q���ѯ�������߷����������������ɫ������������ѡ���½ڻ��ιس�ɽ���������˳��뼰ʱ�浵,�Ƿ�?�½�����Ϸɾ��������������x2�����Զ���������ƿ��е��̵걣�ȵ�ͼ��ʾ����������ս�������˵�����SOB����Ʒ���ý�������һ����ѣ��Ѫ���������������۰�����֮ȸ������L�㲻�ϲ��ߺ�������ǿ�����Ч��/-+��ʹ�Ұ�013456789()!@#$%&*{}[]<>;':. abcdefghijklmnopqrtuvwyz�ʹ���С��ʯ�ƺ���ȡ��ȷ����ӿ����ľ���ۿ���ꪻ������й�����ƽ��ױ���Ƥ������Ӱ���ܼ�̷���׼��ע���Լ���������ϵͳ�ظ�������ͻ��а������ŭ���׳����������������������ʡ��װ����ˢ�����۾���Ǯ�ռ����װ�ɱ��ҩƿ";
}
