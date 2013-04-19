package com.tvgame.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.tvgame.constant.Const;
import com.tvgame.game.Game;
import com.tvgame.util.GraphicsUtil;

public class Util {


	private Util(){}
	private static Random random = new Random();

	/**
	 * 
	 * [min,max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max) {
		int iRet = random.nextInt() >>> 1;
		iRet = iRet % (max + 1 - min) + min;
		return iRet;
	}
	//---------------
//	public static final int MAX_RAND = 0x7fffffff;
//	private static int seed;
//	public static int getRandomFast(int min, int max){
//		int iRet = randomInt() >>> 1;
//		iRet = iRet % (max + 1 - min) + min;
//		return iRet;
//	}
//	private static int randomInt() {
//		seed = 69069 * seed + 1;
//		return ( seed & MAX_RAND );
//	}
//	public static void setRandomSead(int v){
//		seed = v;
//	}






	


	// ------------------?浵 --------------------
	
	
	static public void draw3DString( Graphics g, String str, int x, int y, int flag, int framecolor, int bodycolor ) {

        g.setColor( framecolor );
        g.drawString( str, x, y - 1, flag );
        g.drawString( str, x - 1, y - 1, flag );
        g.drawString( str, x - 1, y, flag );
        g.drawString( str, x - 1, y + 1, flag );
        g.drawString( str, x, y + 1, flag );
        g.drawString( str, x + 1, y + 1, flag );
        g.drawString( str, x + 1, y, flag );
        g.drawString( str, x + 1, y - 1, flag );
        g.setColor( bodycolor );
        g.drawString( str, x, y, flag );
    }
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
//	static public void drawSoft3DString(Graphics g, String str, int isleft,
//			int framecolor, int bodycolor) {
//		if (isleft == LEFT) {
//			draw3DString(g, str, 4, GameCanvas.SCREEN_H - 5, g.LEFT | Graphics.BOTTOM,
//					framecolor, bodycolor);
//		} else {
//			draw3DString(g, str, GameCanvas.SCREEN_W - 4,
//					GameCanvas.SCREEN_H - 5, g.RIGHT | g.BOTTOM, framecolor,
//					bodycolor);
//		}
//		//		
//
//	}
	
	//---------------------------------------------------------------

	public static void drawNumber(Graphics g, int x, int y, int flag,
			Image img, String num) {
		drawNumber(g,x,y,flag,img,num,10);
//		final int w = img.getWidth() / 10;
//		final int h = img.getHeight();
//		// right
//		if (((flag & 8) >> 3) == 1) {
//			x -= w * (num.length());
//		}
//		// buttom
//		if (((flag & 32) >> 5) == 1) {
//			y -= h;
//		}
//		for (int i = 0; i < num.length(); i++) {
//			int n = 0;
//			try {
//				n = Integer.parseInt(num.substring(i, i + 1));
//			} catch (Exception e) {
////
//			}
//			drawNmberBit(g, x + i * w, y, g.TOP | g.LEFT, img, n, w, h);
//		}
//		g.setClip(0, 0, GameCanvas.SCREEN_W, GameCanvas.SCREEN_H);
	}
	public static void drawNumber(Graphics g, int x, int y, int flag,
			Image img, String num,int maxNumber) {
		final int w = img.getWidth() / maxNumber;
		final int h = img.getHeight();
		// right
		if (((flag & 8) >> 3) == 1) {
			x -= w * (num.length());
		}
		// buttom
		if (((flag & 32) >> 5) == 1) {
			y -= h;
		}
		
		//hcenter
		if (((flag & 1)) == 1) {
			x = x-(w*(num.length())>>1);
		}
		
		for (int i = 0; i < num.length(); i++) {
			int n = 0;
			try {
				if(num.substring(i, i + 1).equals("/")){
					n = 10;
				}else
				n = Integer.parseInt(num.substring(i, i + 1));
			} catch (Exception e) {
//
			}
			drawNmberBit(g, x + i * w, y, g.TOP | g.LEFT, img, n, w, h);
		}
		g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
	}

	public static void drawNmberBit(Graphics g, int x, int y, int flag,
			Image img, int num, int w, int h) {
		int drawX = x - num * w;
		int drawY = y;
		// right
		if (flag >> 3 == 1) {
			drawX -= w;
		}
		// buttom
		if (flag >> 5 == 1) {
			drawY -= h;
		}
		g.setClip(drawX + num * w, drawY, w, h);
		g.drawImage(img, drawX, drawY, g.TOP | g.LEFT);
	}
	public static final int ROW = 0;
	public static final int COL = 1;
	/**
	 * draw a part of a Image.
	 * @param g
	 * @param x
	 * @param y
	 * @param flag 
	 * @param img
	 * @param num 
	 * @param maxNumber
	 * @param rowOrCol 
	 */
	public static void drawPartImage(Graphics g,int x,int y,int flag,Image img,int num,int maxNumber,int rowOrCol,boolean isFlipX){
//		g.setClip(0, 0, GameCanvas.SCREEN_W, GameCanvas.SCREEN_H);
		int w;
		int h;
//		int drawX;
//		int drawY;
				
		if(rowOrCol == ROW){
			w = img.getWidth() / maxNumber;
			h = img.getHeight();
//			drawX = x - num * w;
//			drawY = y;
		}else {
			w = img.getWidth() ;
			h = img.getHeight()/ maxNumber;
//			drawX = x ;
//			drawY = y- num * h;
		}
		
		 //right
		if (((flag & 8) >> 3) != 0) {
//
			x-=w;
		}
		// buttom
		if (((flag & 32) >> 5) != 0) {
//
			y-=h;
		}
		

		
//		g.setClip(0, 0, 240, 320);
		if(rowOrCol == ROW){
//			g.setClip(drawX + num * w, drawY, w, h);
//			g.drawImage(img, drawX, drawY, g.TOP | g.LEFT);
			GraphicsUtil.drawRegion(g,img,  num * w, 0, w, h, isFlipX?GraphicsUtil.TRANS_MIRROR:0, x, y, g.TOP|g.LEFT);						
		}else{
//			g.setClip(drawX, drawY + num * h, w, h);
//			g.drawImage(img, drawX, drawY, g.TOP | g.LEFT);
			GraphicsUtil.drawRegion(g,img, 0,  num * h, w, h, isFlipX?GraphicsUtil.TRANS_MIRROR:0, x, y, g.TOP|g.LEFT);
		}
		g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
	}
	public static void drawPartImageClips(Graphics g,int x,int y,int clipsX,int clipsY,int clipsW,int clipsH,int flag,Image img,int num,int maxNumber,int rowOrCol,boolean isFlipX){
		g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
		int w;
		int h;
				
		if(rowOrCol == ROW){
			w = img.getWidth() / maxNumber;
			h = img.getHeight();
		}else {
			w = img.getWidth() ;
			h = img.getHeight()/ maxNumber;
		}
		
		 //right
		if (((flag & 8) >> 3) != 0) {
//
			x-=w;
		}
		// buttom
		if (((flag & 32) >> 5) != 0) {
//
			y-=h;
		}
		if(rowOrCol == ROW){
			GraphicsUtil.drawRegion(g,img,  num * w+clipsX, 0+clipsY, clipsW, clipsH, isFlipX?GraphicsUtil.TRANS_MIRROR:0, x, y, g.TOP|g.LEFT);						
		}else{
			GraphicsUtil.drawRegion(g,img, 0+clipsX,  num * h+clipsY, clipsW, clipsH, isFlipX?GraphicsUtil.TRANS_MIRROR:0, x, y, g.TOP|g.LEFT);
		}
		g.setClip(0, 0, Const.WIDTH, Const.HEIGHT);
	}
//	public static void drawPartImage(GraphicsBase gg,Graphics g,int x,int y,int flag,Image img,int num,int maxNumber,int rowOrCol){
//		drawPartImage(gg, g, x,y, flag,img,num, maxNumber, rowOrCol,false);
//	}
//	 ----------------------Sound process-------------------------//
//	static private Player[] _sounds;

	public static final String[] SOUND_RES = {
		"/Sound_0.mid",
		"/Sound_1.mid",
		"/Sound_2.mid",
		"/Sound_3.mid",
		"/cg_bg.mid",
		"/gaichuo.mid"
	};

	private static Vector vec = new Vector();

	
	
	/***********************
	 *    换行字符串转换
	 *********************/
	public static String[] convertRectString(Font font,String storyWord,int w){
		String[] drawStr = null;
		vec.removeAllElements();
		String temp = "";
		for (int i = 0; i < storyWord.length(); i++) {
			temp += storyWord.substring(i, i + 1);
			if (storyWord.charAt(i) == '^'
					|| storyWord.charAt(i) == '\n'
					|| font.stringWidth(temp) > w
							- font.charWidth('安')
					|| i == storyWord.length() - 1) {
				if (storyWord.charAt(i) == '\n' || storyWord.charAt(i) == '^') {
					temp = temp.substring(0, temp.length() - 1);
				}
				vec.addElement(temp);
				temp = "";
			}
		}
		drawStr = new String[vec.size()];
		for (int i = 0; i < drawStr.length; i++) {
			drawStr[i] = (String) vec.elementAt(i);
		}
		return drawStr;
	}

	

	

	public static void drawFrameBeaty(Graphics g,int x,int y,int w,int h,int color){
		g.setColor(color);
		g.drawLine(x+1, y, x+w-1, y);
		g.drawLine(x+1, y+h, x+w-1, y+h);
		g.drawLine(x, y+h-1, x, y+1);
		g.drawLine(x+w, y+h-1, x+w, y+1);
	}
	private static StringBuffer bf = new StringBuffer();	
	public static String replaceString(String from, String to, String source) {
		if (source == null || from == null || to == null) {
			return null;
		}
		bf.delete(0, bf.length());
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			bf.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
			index = -1;
		}
		bf.append(source);
		return bf.toString();
	} 
	
	final public static void fillRect(Graphics g, int RectX, int RectY,
			int RectWidth, int RectHeight, int ARGBColor) {
		if (RectWidth <= 0 || RectHeight <= 0)
			return;
		if ((ARGBColor & 0xff000000) == 0xff000000) {
			g.setColor(ARGBColor);
			g.fillRect(RectX, RectY, RectWidth, RectHeight);
		} else if ((ARGBColor & 0xff000000) != 0x00000000) {
			int[] ARGB = new int[RectWidth * RectHeight];
			ARGB[0] = ARGBColor;
			int TempPos = 1;
			while (TempPos < ARGB.length) {
				int TempLen = TempPos;
				if (TempPos + TempLen > ARGB.length) {
					TempLen = ARGB.length - TempPos;
				}
				System.arraycopy(ARGB, 0, ARGB, TempPos, TempLen);
				TempPos += TempLen;
			}
			g.drawRGB(ARGB, 0, RectWidth, RectX, RectY, RectWidth, RectHeight,
					true);
		}
		
	}
	
	/**
	 * 
	 * 按宽度格式化(分行)字符串. 些方法可包括中英文
	 * 
	 * @param s
	 *            源
	 * @param font
	 *            字体
	 * @param forceW
	 *            强制换行的宽
	 * @param newLineFlag
	 *            强制换行的标识
	 * @return 分行完成的字符串数组
	 */
	private static String[] getSubStrings(String s, Font font, int forceW,
			char newLineFlag) {
		java.util.Vector v = new java.util.Vector();
		try {
			s += newLineFlag;
			int strEnd = 0;
			int strBgin = 0;
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				boolean isNewline = (ch == newLineFlag);
				if (((ch == ' ' || ch == ',') && isLetter(ch)) || isNewline
						|| !isLetter(ch)) {
					strEnd = i;
				}
				boolean out = font.stringWidth(s.substring(strBgin, i + 1)) > forceW;// font.stringWidth()占用一定的CPU资源
				if (out || isNewline) {
					if (out)
						strEnd = i;
					String sub = s.substring(strBgin, strEnd);
					v.addElement(sub);
					if (isNewline) {
						strBgin = strEnd + 1;
					} else {
						strBgin = strEnd;
					}
				}
			}
		} catch (Exception e) {
//			Debug(e, "subS");
		}
		String[] ss = new String[v.size()];
		v.copyInto(ss);
		return ss;
	}// QN

	private static boolean isLetter(char ch) {
		return (short) ch < 255;
	}

	public static int getStringWidth(Font font, String s) {
		return font.stringWidth(s);
	}

	/**
	 * 绘制字符串 如果有需要将会根据宽度自动换行
	 * 
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 * @param anchor
	 * @param color
	 * @param width
	 * @param brFlag
	 * @param font
	 * @return
	 */
	public static int drawString(Graphics g, String s, int x, int y,
			int anchor, int color, int width, char brFlag, Font font) {
		if (font.stringWidth(s) > width) {
			String[] ss = getSubStrings(s, font, width, brFlag);
			if (ss.length <= 0)
				return 0;
			int size = ss.length;
			g.setColor(color);
			for (int i = 0; i < size; i++) {
				g.drawString(ss[i], x, y + i * font.getHeight(), anchor);
			}
			return size;// s_tmp.length;
		} else {
			g.setColor(color);
			g.drawString(s, x, y, anchor);
			return 1;
		}
	}

	/**
	 * 2分法查找元素【需要是有序的数组】
	 * @param a  数组
	 * @param searchkey 关键字
	 * @return
	 */
	private static int  binarySearch(int [] a,int searchkey)
	{
		int leftBound = 0;
		int rightBound = a.length-1;
		int middle ;
		while (true) {
			middle = (leftBound+rightBound)/2;
			if(a[middle] == searchkey)
				return middle;
			if(leftBound>rightBound)
				return -1;
			else
			{
				if(a[middle]<searchkey)
					leftBound++;
				if(a[middle]>searchkey)
					rightBound--;
			}
		}
	}
	

	/**
     * 读取UTF-8 txt
     **/
    public static String readTxt(String fileName) {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream("".getClass().getResourceAsStream(fileName));
            if (dis != null) {
                dis.skip(3);//跳过三个字节的文件头
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int tempInt = -1;
                while ((tempInt = dis.read()) != -1) {
                    bos.write(tempInt);
                }
                byte bytes[] = bos.toByteArray();
                String temp = new String(bytes, "UTF-8");
                temp = temp.trim();
                return temp;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

                
        finally{
           try {
               dis.close();
           } catch (IOException ex) {
               ex.printStackTrace();
           }
            dis = null;
        }
        return null;
    }

}
