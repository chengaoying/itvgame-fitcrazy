package com.tvgame.game;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.util.GraphicsUtil;

public class Dialog {
	
	/***************************************************************************
	 * 对话
	 **************************************************************************/
	public static final int DIALOG_W = Const.WIDTH;

	public static final int DIALOG_H = (Const.bigFont.getHeight() + 1) * 2 + 10;

	public static final int DIALOG_X = 0;

	public static final int DIALOG_Y = Const.HEIGHT - DIALOG_H;

	private static String[] dialogStringArry;

	public static boolean isShowDialog;

	private static String dialog;

	public static int dialogLines;

	private static int dialogX;

	private static int dialogY;

	private static int dialogWidth;

	private static int dialogHeight;

	private static int curIndex;

	private static int lineNumPerPage;// 一页显示几行

	private static int lineAllNum;// 总共行数

	private static int curLine;// 当前页

	public static Vector vec = new Vector();

	public static final int SPACE = 1;

	// public Vector vec = new Vector();
	public static String dialogName;

	public static int dialogIcon;

	public static int maxShowDialogLenght;

	public static int curShowDialogLenght;

	public static final int SHOW_DIALOG_SPEED = 20;

	public static boolean isOnlyFace;

	public static void initDialog(String name, String str, int icon) {
		initDialog(name, str, icon, DIALOG_X + 11, DIALOG_Y + 5, DIALOG_W - 22,
				DIALOG_H - 10);
	}

	public static void initDialog(String name, String str, int icon, int x,
			int y, int width, int height) {
		dialogX = x;
		dialogY = y;
		isShowDialog = true;
		dialogName = name;
		dialog = str;
		curIndex = 0;
		lineNumPerPage = height / (Const.bigFont.getHeight() + 1);
		dialogHeight = height;// (GameCanvas.font.getHeight() + 1) * (lines +
		// 1) + 5;
		dialogIcon = icon;
		curLine = 0;
		getSomeLinesDialogTrueIfEnd(width);
	}

	public static StringBuffer bufStrBack = new StringBuffer();

	// 红字结构
	public static Vector colorStrVector = new Vector();

	public static final int COLOR_STRING_COL = 0;

	public static final int COLOR_STRING_X = 1;

	public static final int COLOR_STRING_OFFSET = 2;

	public static final int COLOR_STRING_LENGTH = 3;

	public static int colorCol;

	public static int colorX;

	public static int colorOffset;

	public static int colorLength;

	public static boolean isSaveColor;

	public static boolean isContinueSaveColor;

	public static int[][] drawColorArry;

	private static boolean getSomeLinesDialogTrueIfEnd(int width) {
		if (dialog == null) {
			return true;
		}
		// showDialogTime = -1;
		boolean isEnd = false;
		dialogWidth = width;
		// dialogLines = lines;
		vec.removeAllElements();
		colorStrVector.removeAllElements();
		isSaveColor = false;
		isContinueSaveColor = false;
		// String temp = "";
		bufStrBack.delete(0, bufStrBack.length());
		if (curIndex >= dialog.length() - 1) {
			isEnd = true;
		}
		for (int i = curIndex; i < dialog.length() /*
													 * && (lines==-1 || lines >
													 * 0)
													 */; i++, curIndex++) {
			// temp += dialog.substring(i, i + 1);
			char curChar = dialog.charAt(i);
			// ----------------------------
			if (curChar == '[') {
				if (!isSaveColor) {
					isSaveColor = true;
					colorCol = vec.size();
					colorX = Const.bigFont.stringWidth(bufStrBack.toString());
					colorOffset = bufStrBack.length();
					continue;
				}
			}
			if (curChar == ']') {
				if (isSaveColor) {
					isSaveColor = false;
					if (isContinueSaveColor) {
						colorLength = bufStrBack.length();
					} else {
						colorLength = bufStrBack.length();
					}

					colorStrVector.addElement(new int[] { colorCol, colorX,
							colorOffset, colorLength });

					if (i < dialog.length() - 1) {
						continue;
					} else {
						if (bufStrBack.length() * Const.FONT_W_CH/* font.stringWidth(bufStrBack.toString()) */> width
								- Const.bigFont.charWidth('汉')
								|| i == dialog.length() - 1 || curChar == '^') {
							vec.addElement(bufStrBack.toString());

							if (isSaveColor) {
								isContinueSaveColor = true;
								colorLength = bufStrBack.length();
								colorStrVector.addElement(new int[] { colorCol,
										colorX, colorOffset, colorLength });

								colorCol = vec.size();
								colorX = 0;
								colorOffset = 0;
							}
							bufStrBack.delete(0, bufStrBack.length());
						}
					}
					continue;

				}
			}
			// -------------------------------
			if (curChar != '^') {
				bufStrBack.append(curChar);
			}

			if (bufStrBack.length() * Const.FONT_W_CH > width - Const.bigFont.charWidth('汉')
					|| i == dialog.length() - 1 || curChar == '^') {
				vec.addElement(bufStrBack.toString());

				if (isSaveColor) {
					isContinueSaveColor = true;
					colorLength = bufStrBack.length();
					colorStrVector.addElement(new int[] { colorCol, colorX,
							colorOffset, colorLength });

					colorCol = vec.size();
					colorX = 0;
					colorOffset = 0;
				}
				bufStrBack.delete(0, bufStrBack.length());
			}
		}
		curIndex++;
		dialogStringArry = null;
		dialogStringArry = new String[vec.size()];
		for (int i = 0; i < dialogStringArry.length; i++) {
			dialogStringArry[i] = (String) vec.elementAt(i);
		}

		drawColorArry = null;
		drawColorArry = new int[colorStrVector.size()][];
		for (int i = 0; i < drawColorArry.length; i++) {

			drawColorArry[i] = (int[]) colorStrVector.elementAt(i);

		}
		lineAllNum = dialogStringArry.length;
		// dialogHeight = (GameCanvas.font.getHeight() + 1)*(lines+1)+45;

		curShowDialogLenght = -20;// 初始化
		maxShowDialogLenght = vec.size() * dialogWidth;

		// ---------------------------------------------------
		colorArry = new int[dialogStringArry.length][];
		for (int i = 0; i < colorArry.length; i++) {
			colorArry[i] = new int[dialogStringArry[i].length()];
		}
		for (int i = 0; i < drawColorArry.length; i++) {
			int offset = drawColorArry[i][COLOR_STRING_OFFSET];
			int length = drawColorArry[i][COLOR_STRING_LENGTH]
			/*- drawColorArry[i][COLOR_STRING_OFFSET]*/;
			for (int j = offset; j < length; j++) {
				colorArry[drawColorArry[i][COLOR_STRING_COL]][j] = 0xff0000;
			}
		}
		// ---------------------------------------------------------

		return isEnd;
	}
	
	public static void drawFrame(Graphics g, int x, int y, int w, int h) {
		final int arc = 4;
		g.setColor(0x030504);
		g.drawRoundRect(x, y, w, h, arc, arc);
		g.setColor(0xcbba9b);
		g.fillRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
		g.setColor(0x2c4031);
		g.drawRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
		g.drawRoundRect(x + 2, y + 2, w - 4, h - 4, arc, arc);
	}

	public static void drawGameDialog(Graphics g) {
		final int x = dialogX;
		final int y = dialogY - 1;
		// if(isAuto){
		// curShowDialogLenght = SHOW_DIALOG_SPEED;
		// }
		if (curShowDialogLenght <= maxShowDialogLenght) {
			curShowDialogLenght += SHOW_DIALOG_SPEED;
		}
		// else
		// if(showDialogTime == -1){
		// showDialogTime = 0;
		// }
		// isLeft = namePos[curdialogPos];
		if (dialogIcon != -1) {
			Image icon = Resources.loadDialogImage(dialogIcon);
			g.drawImage(icon, Const.WIDTH,
					Const.HEIGHT- 40/* g.getFont().getHeight()*2 */, g.RIGHT
							| g.BOTTOM);
		}

		// drawFrame(g, DIALOG_X, DIALOG_Y, DIALOG_W, DIALOG_H);
		drawUITipFrame(g, DIALOG_X + 7, DIALOG_Y, DIALOG_W - 14, DIALOG_H - 7);

		// debug
		int nameW = Const.bigFont.stringWidth(dialogName);
		int nameOffset = 0;
//#if MEMORY_1M
		nameOffset = 10;
//#else 
		nameOffset = 0;
//#endif
//		drawUITipFrame(g, DIALOG_X + 7, DIALOG_Y - font.getHeight() - 18 + nameOffset,
//				nameW + 12, font.getHeight() + 8);
		drawNameFrame(g,  DIALOG_X, DIALOG_Y-/*Res.UI_BUTTON_PNG_H*/30);
		g.setColor(0xffffff);
		g.drawString(dialogName, DIALOG_X + (/*Res.UI_NAME_BG_PNG_W*/40), DIALOG_Y - Const.bigFont.getHeight()
			 + nameOffset, g.HCENTER | g.TOP);
		

		// Image frame =
		// (Image)ActorController.getRes(ActorController.FILE_DUIHUAKUANG_PNG);
		// gg.drawRegion(frame, 0, 0, frame.getWidth(), frame.getHeight(),
		// isLeft?gg.TRANS_NONE:gg.TRANS_MIRROR , 0, y-20, g.TOP|g.LEFT);
		// if ( isLeft ) {
		// Util.drawPartImage(gg, g, 17, y - 10, g.TOP|g.LEFT,
		// name,scene.getNameIdByName( scene.playerName), 6, Util.COL);
		// } else {
		// Util.drawPartImage(gg, g, GameCanvas.CAMERA_W - 17-name.getWidth(),
		// y-10,
		// g.TOP|g.LEFT, name, scene.getNameIdByName(scene.enemyName), 6,
		// Util.COL);
		// }

		/*
		 * g.setColor(0x0); for (int i = 0; i < dialogStringArry.length; i++) {
		 * g.setClip(x, y + i * (font.getHeight() + 1), curShowDialogLenght - i *
		 * dialogWidth, font.getHeight()+1); g.drawString(dialogStringArry[i],
		 * x, y + (i) (font.getHeight() + 1), g.TOP | g.LEFT); }
		 * 
		 * g.setColor(0xff0000); for (int i = 0; i < drawColorArry.length; i++) {
		 * g.setClip(x, y + drawColorArry[i][COLOR_STRING_COL]*
		 * (font.getHeight() + 1), curShowDialogLenght -
		 * drawColorArry[i][COLOR_STRING_COL]* dialogWidth, font.getHeight()+1);
		 * 
		 * 
		 * String str = dialogStringArry[drawColorArry[i][COLOR_STRING_COL]];
		 * int offset = drawColorArry[i][COLOR_STRING_OFFSET]; int length =
		 * drawColorArry[i][COLOR_STRING_LENGTH]-drawColorArry[i][COLOR_STRING_OFFSET];
		 * g.drawSubstring(str, offset, length,
		 * x+drawColorArry[i][COLOR_STRING_X], y +
		 * (drawColorArry[i][COLOR_STRING_COL])* (font.getHeight() + 1), g.TOP |
		 * g.LEFT); }
		 */

		// new
		for (int i = curLine; i < curLine + lineNumPerPage; i++) {
			if (i >= dialogStringArry.length) {
				break;
			}
			g.setClip(x, y + (i % lineNumPerPage)
							* (Const.bigFont.getHeight() + 1), curShowDialogLenght - i
							* dialogWidth, Const.bigFont.getHeight() + 1);
			// g.drawString(dialogStringArry[i], x, y + (i % lineNumPerPage)
			// * (font.getHeight() + 1), g.TOP | g.LEFT);
			drawColorString(g, dialogStringArry[i], colorArry[i], x, y
					+ (i % lineNumPerPage) * (Const.bigFont.getHeight() + 1), g.TOP
					| g.LEFT);
		}

		// g.setColor(0x0);
		// for (int i = curLine; i < curLine + lineNumPerPage; i++) {
		// if (i >= dialogStringArry.length) {
		// break;
		// }
		// g.setClip(x, y + (i % lineNumPerPage) * (font.getHeight() + 1),
		// curShowDialogLenght - i * dialogWidth, font.getHeight() + 1);
		// g.drawString(dialogStringArry[i], x, y + (i % lineNumPerPage)
		// * (font.getHeight() + 1), g.TOP | g.LEFT);
		// }
		//
		// g.setColor(0xff0000);
		// for (int i = 0; i < drawColorArry.length; i++) {
		// if (drawColorArry[i][COLOR_STRING_COL] < curLine
		// || drawColorArry[i][COLOR_STRING_COL] >= curLine + lineNumPerPage) {
		// continue;
		// }
		// g.setClip(x, y + (drawColorArry[i][COLOR_STRING_COL] %
		// lineNumPerPage)
		// * (font.getHeight() + 1), curShowDialogLenght
		// - drawColorArry[i][COLOR_STRING_COL] * dialogWidth,
		// font.getHeight() + 1);
		//
		// String str = dialogStringArry[drawColorArry[i][COLOR_STRING_COL]];
		// int offset = drawColorArry[i][COLOR_STRING_OFFSET];
		// int length = drawColorArry[i][COLOR_STRING_LENGTH]
		// - drawColorArry[i][COLOR_STRING_OFFSET];
		// g.drawSubstring(str, offset, length,
		// x + drawColorArry[i][COLOR_STRING_X], y
		// + (drawColorArry[i][COLOR_STRING_COL] % lineNumPerPage)
		// * (font.getHeight() + 1), g.TOP | g.LEFT);
		//
		// }

	}

	/***************************************************************************
	 * 提示框
	 **************************************************************************/
	public static void drawUITipFrame(Graphics g, int x, int y, int w, int h) {
		g.setColor(0xffdf8c);
		g.fillRect(x, y, w, h);
		drawPlaneCorner(g, Resources.loadDialogImage(Resources.IMG_ID_UI_KUANG_02_CORNER_PNG),
				Resources.loadDialogImage(Resources.IMG_ID_UI_KUANG_02_SIDE_1_PNG),Resources.loadDialogImage(Resources.IMG_ID_UI_KUANG_02_SIDE_2_PNG),
				x, y, w, h);
	}
	
	/***************************************************************************
	 * 绘制名字的框
	 **************************************************************************/
	public static void drawNameFrame(Graphics g, int x, int y) {
		Image imageBg =  Resources.loadDialogImage(Resources.IMG_ID_UI_NAME_BG_PNG);
//		Image imageName = loadImage(Res.UI_NAME_PNG);
		g.drawImage(imageBg, x, y,  g.TOP
				| g.LEFT);
//		g.drawImage(imageName, x+(Res.UI_NAME_BG_PNG_W>>1), y+(Res.UI_NAME_BG_PNG_H>>1),  g.HCENTER
//				| g.VCENTER);
	}
	

	/**
	 * 绘制带有材质的边框 任意大小
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public static void drawPlaneCorner(Graphics g,Image corner,Image verticalSide,Image horizonSide, int x, int y, int w, int h) {
		//#if MEMORY_1M
		g.setColor(0x0);
		g.drawRect(x, y, w, h);
		
//		g.setColor(0xff0000);
//		g.drawRect(x, y, w-1, h-1);
		
		//#else
//		Image img = loadImage(Res.UI_KUANG_02_CORNER_PNG);
		
		int offsetx = 4;
		int offsety = 4;
		GraphicsUtil.drawRegion(g, corner, 0, 0, corner.getWidth(), corner.getHeight(),
				GraphicsUtil.TRANS_NONE, x - offsetx, y - offsety, g.TOP | g.LEFT);
		GraphicsUtil.drawRegion(g, corner, 0, 0, corner.getWidth(), corner.getHeight(),
				GraphicsUtil.TRANS_MIRROR, x + w + offsetx, y - offsety, g.TOP
						| g.RIGHT);

		GraphicsUtil.drawRegion(g, corner, 0, 0, corner.getWidth(), corner.getHeight(),
				GraphicsUtil.TRANS_MIRROR_ROT180, x - offsetx, y + h + offsety,
				g.BOTTOM | g.LEFT);
		GraphicsUtil.drawRegion(g, corner, 0, 0, corner.getWidth(), corner.getHeight(),
				GraphicsUtil.TRANS_ROT180, x + w + offsetx, y + h + offsety, g.BOTTOM
						| g.RIGHT);
		int rx = g.getClipX();
		int ry = g.getClipY();
		int rw = g.getClipWidth();
		int rh = g.getClipHeight();
		
//		Image side_1 = loadImage(Res.UI_KUANG_02_SIDE_1_PNG);

		g.setClip(x - verticalSide.getWidth(), y + verticalSide.getHeight() - offsety,
				verticalSide.getWidth(), h - 2 * (verticalSide.getHeight() - offsety));
		for (int i = 0; i < h / verticalSide.getHeight() + 1; i++) {
			GraphicsUtil.drawRegion(g, verticalSide, 0, 0, verticalSide.getWidth(), verticalSide.getHeight(), 0,
					x,
					y + 10 - offsety + i * verticalSide.getHeight(),
					g.TOP | g.RIGHT);
		}

		g.setClip(x + w, y + verticalSide.getHeight() - offsety, verticalSide.getWidth(), h
				- 2 * (verticalSide.getHeight() - offsety));
		for (int i = 0; i < h / verticalSide.getHeight() + 1; i++) {
			GraphicsUtil.drawRegion(g, verticalSide, 0, 0, verticalSide.getWidth(), verticalSide.getHeight(),
					GraphicsUtil.TRANS_ROT180, x + w, y + verticalSide.getHeight() - offsety
							+ i * verticalSide.getHeight(), g.TOP | g.LEFT);
		}
		
//		Image side_2 = loadImage(Res.UI_KUANG_02_SIDE_2_PNG);

		g.setClip(x + horizonSide.getWidth() - offsetx, y - horizonSide.getHeight(), w
				- 2 * (horizonSide.getWidth() - offsetx), horizonSide.getHeight());
		for (int i = 0; i < w / horizonSide.getWidth() + 1; i++) {
			GraphicsUtil.drawRegion(g, horizonSide, 0, 0, horizonSide.getWidth(), horizonSide.getHeight(), 0,
					x + horizonSide.getWidth() - offsetx + i * horizonSide.getWidth(),
					y, g.BOTTOM | g.LEFT);
		}

		g.setClip(x + horizonSide.getWidth() - offsetx, y + h, w - 2
				* (horizonSide.getWidth() - offsetx), horizonSide.getHeight());
		for (int i = 0; i < w / horizonSide.getWidth() + 1; i++) {
			GraphicsUtil.drawRegion(g, horizonSide, 0, 0, horizonSide.getWidth(), horizonSide.getHeight(),
					GraphicsUtil.TRANS_ROT180, x + horizonSide.getWidth() - offsetx + i
							* horizonSide.getWidth(), y + h, g.TOP | g.LEFT);
		}
		g.setClip(rx, ry, rw, rh);
		//#endif
	}
	
	
	public static int[][] colorArry;

	public static void drawColorString(Graphics g, String str, int[] color,
			int x, int y, int anch) {
		int curColor = -1;
		char curChar;
		for (int i = 0; i < str.length(); i++) {
			if (curColor != color[i]) {
				curColor = color[i];
				g.setColor(curColor);
			}
			g.drawChar(str.charAt(i), x + i * Const.FONT_W_CH, y, g.TOP | g.LEFT);
		}
	}

	public static void updateGameDialog(KeyState key) {
    		if(key.containsAndRemove(KeyCode.OK)){
    			dialogConfirm();
    		}
	}

	private static void dialogConfirm() {
		if (curShowDialogLenght <= maxShowDialogLenght) {
			curShowDialogLenght = maxShowDialogLenght + 1;
		} else {
			curLine += lineNumPerPage;
			if (curLine >= lineAllNum) {
				isShowDialog = false;
			}
		}
	}
	
	//滚动文字-------------------------------------------
	private static int getScrollWordLenght(String str)
	{
		if(str.equals("")||str == null)
			return -1;
		
		int 非汉字计数器 = 0;
		for (int i = 0; i < str.length(); i++) {
			
			char c = str.charAt(i);
			if(c>='0'&&c<='9')
				非汉字计数器++;
		}
	
		return (str.length()-非汉字计数器)*Const.FONT_W_CH+非汉字计数器*Const.FONT_W_EG;
	}
	private static int scorll_offx = 0;
	private static int scroll_x;
	private static int scroll_y;
	private static int scroll_clipX ;
	private static int scroll_clipY ;
	
	private static void setScrollWord(int x,int y)
	{
		//清空延迟器
		scorll_offx = 0;
		scroll_clipX = x;
		scroll_clipY = y;
		scroll_x = x;
		scroll_y = y;
	}
	
	private static void drawScrollWord(Graphics g,String str,int solidW,int color)
	{
		int clipx = g.getClipX();
		int clipy = g.getClipY();
		int clipw = g.getClipWidth();
		int cliph = g.getClipHeight();
		
		
		int getLen = getScrollWordLenght(str);
		if(getLen!= -1 && solidW < getLen)
		{
			g.setClip(scroll_clipX, scroll_clipY, solidW, Const.FONT_H);
			if (scorll_offx<=getLen) {
				
				g.setColor(color);
				g.drawString(str, scroll_x, scroll_y, 20);
				scorll_offx+=2;
				if(scorll_offx >24)
					scroll_x-= 2;
//					System.out.println("scorll_offx ="+scorll_offx);
			}else
			{
				scorll_offx = 0;
				scroll_x = scroll_clipX;
				scroll_y = scroll_clipY;
			}
		}
		else
		{
			g.setColor(color);
			g.drawString(str, scroll_x, scroll_y, 20);
		}
		
		g.setClip(clipx, clipy, clipw, cliph);
	}

	
}
