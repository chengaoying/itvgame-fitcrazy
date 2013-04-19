package com.tvgame.ui;

//import javax.microedition.lcdui.Graphics;
//import javax.microedition.lcdui.Image;
//import javax.microedition.lcdui.Font;
//import java.util.Vector;
//
///**
// * 
// * <p>
// * Title: Script
// * </p>
// * <p>
// * Description: Display charactors on the screen
// * </p>
// * <p>
// * Copyright: Copyright (c) 2006
// * </p>
// * <p>
// * Company: G9
// * </p>
// * 
// * @author Raph
// * @version 1.0.0
// */
//
//public class Text {
//
//	GameCanvas game;
//
//	/** 对话框 x,y,w,h,左上角 */
//	int x;
//
//	int y;
//
//	int w;
//
//	int h;
//
//	/** 文字开始显示的y坐标 */
//	int displayY;
//
//	int width; // 屏幕宽
//
//	int height; // 屏幕高
//
//	int rowNo; // 当前显示的行号
//
//	int rows; // 总共的行数
//
//	int row; // 每次显示的行数
//
//	String[] tempS = null;
//
//	// 已浏览的文字的行的长度
//	int newsDisplayedH;
//
//	// 浏览条的长度
//	int browserBarH;
//
//	// 文字总的行的长度
//	int newsTotalH;
//
//	Image img = null;
//
//	int imgH;
//
//	/** 浏览的速度 */
//	int browseSpeed;
//
//	/** 已经显示的行数 */
//	int displayedRows;
//
//	/** 滚动条开启标志 */
//	boolean isRollBarOn;
//
//	Font f = null;
//
//	int fontColor;
//
//	public Text(int width, int height, boolean b) {
//		this.width = width;
//		this.height = height;
//		isRollBarOn = b;
//	}
//
//	public Text(int width, int height, GameCanvas game, boolean b) {
//		this.width = width;
//		this.height = height;
//		this.game = game;
//		isRollBarOn = b;
//	}
//
//	public void clean() {
//		for (int i = 0; i < tempS.length; i++) {
//			tempS[i] = null;
//		}
//		tempS = null;
//		System.gc();
//	}
//
//	/**
//	 * 
//	 * @param x
//	 *            int 对话框左上脚 x坐标
//	 * @param y
//	 *            int 对话框左上脚 y坐标
//	 * @param w
//	 *            int 对话框宽
//	 * @param h
//	 *            int 对话框高
//	 * @param f
//	 *            Font 字体
//	 * @param str
//	 *            String 显示的文字
//	 * @param img
//	 *            Image 显示的图片
//	 */
//	public void sett(int x, int y, int w, int h, Font f, String str,
//			int fontColor, Image img) {
//		this.w = w;
//		this.x = x;
//		this.y = y;
//		this.h = h;
//
//		this.f = f;
//		this.fontColor = fontColor;
//		browseSpeed = 10;
//		displayY = y + 4;
//		this.img = img;
//		if (img == null) {
//			imgH = 0;
//		} else {
//			imgH = img.getHeight();
//		}
//
//		if (tempS != null) {
//			for (int i = 0; i < tempS.length; i++) {
//				tempS[i] = null;
//			}
//			tempS = null;
//		}
//
//		Vector vStr = new Vector();
//		StringBuffer sb = new StringBuffer(str);
//		int strLen = 0;
//		char ch;
//		rows = 0;
//		int wordW = w - 5 - (w - 5) % f.charWidth('B');
//		StringBuffer tempSb = new StringBuffer();
//		StringBuffer word = new StringBuffer();
//		for (int i = 0; i < sb.length(); i++) {
//			ch = sb.charAt(i);
//			if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
//				word.append(ch);
//			} else {
//				if (word.length() == 0) {
//					word.append(ch);
//				} else {// append a word
//					i--;
//				}
//
//				if (ch == 0x000D) {
//				} else if (ch == 0x000A) {
//					while (wordW - strLen > f.charWidth(' ')) {
//						tempSb.append(' ');
//						strLen += f.charWidth(' ');
//					}
//				} else {
//					if (f.stringWidth(new String(word)) < wordW - strLen) {
//						tempSb.append(word);
//						strLen += f.stringWidth(new String(word));
//						word.delete(0, word.capacity());
//					} else {
//						while (wordW - strLen > f.charWidth(' ')) {
//							tempSb.append(' ');
//							strLen += f.charWidth(' ');
//						}
//					}
//				}
//
//				if (wordW - strLen < f.charWidth('B') || i == sb.length() - 1) {
//					vStr.addElement(new String(tempSb));
//					tempSb.delete(0, tempSb.capacity());
//					strLen = 0;
//					rows++;
//				}
//
//			}
//
//		}
//
//		sb.delete(0, sb.length());
//		sb = null;
//
//		newsTotalH = ((Game.FONT_H + 2) * rows + 4 + imgH + 2);
//		row = (h) / (Game.FONT_H + 2);
//		rowNo = 0;
//
//		browserBarH = h * h / newsTotalH;
//		newsDisplayedH = h;
//
//		tempS = new String[rows];
//		for (int i = 0; i < rows; i++) {
//			tempS[i] = (String) vStr.elementAt(i);
//		}
//		vStr.removeAllElements();
//		vStr = null;
//		System.gc();
//		delay = 4;
//		t = 0;
//		typerRow = 0;
//		typerLen = 0;
//		typerCharID = 0;
//
//	}
//
//	public void set(int x, int y, int w, int h, Font f, String str,
//			int fontColor, Image img) {
//		this.w = w;
//		this.x = x;
//		this.y = y;
//		this.h = h;
//
//		this.f = f;
//		this.fontColor = fontColor;
//		browseSpeed = 10;
//		displayY = y + 4;
//		this.img = img;
//		if (img == null) {
//			imgH = 0;
//		} else {
//			imgH = img.getHeight();
//		}
//
//		if (tempS != null) {
//			for (int i = 0; i < tempS.length; i++) {
//				tempS[i] = null;
//			}
//			tempS = null;
//		}
//
//		Vector vStr = new Vector();
//		StringBuffer sb = new StringBuffer(str);
//		int strLen = 0;
//		char ch;
//		rows = 0;
//		int wordW = w - 5 - (w - 5) % f.charWidth('B');
//		StringBuffer tempSb = new StringBuffer();
//		StringBuffer word = new StringBuffer();
//		for (int i = 0; i < sb.length(); i++) {
//			ch = sb.charAt(i);
//			if (((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
//					&& i < sb.length() - 1) {
//				word.append(ch);
//			} else {
//				if (ch != 0x000D && ch != 0x000A) {
//					if (word.length() == 0) {
//						word.append(ch);
//					} else if (((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
//							&& i == sb.length() - 1) {
//						word.append(ch);
//					} else {
//						i--;
//					}
//				}
//
//				if (ch == 0x000A) {
//					while (wordW - strLen > f.charWidth(' ')) {
//						tempSb.append(' ');
//						strLen += f.charWidth(' ');
//					}
//				} else {
//					if (f.stringWidth(new String(word)) < wordW - strLen) {
//						tempSb.append(word);
//						strLen += f.stringWidth(new String(word));
//						word.delete(0, word.capacity());
//					} else {
//						while (wordW - strLen > f.charWidth(' ')) {
//							tempSb.append(' ');
//							strLen += f.charWidth(' ');
//						}
//					}
//				}
//
//				if (wordW - strLen < f.charWidth('B') || i == sb.length() - 1) {
//					vStr.addElement(new String(tempSb));
//					tempSb.delete(0, tempSb.capacity());
//					strLen = 0;
//					rows++;
//					// check if the next char is 'return'
//					if (i + 1 < sb.length() && sb.charAt(i + 1) == 0x000D) {
//						i += 2;
//					}
//				}
//			}
//		}
//
//		sb.delete(0, sb.length());
//		sb = null;
//
//		newsTotalH = ((Game.FONT_H + 2) * rows + 4 + imgH + 2);
//		row = (h) / (Game.FONT_H + 2);
//		rowNo = 0;
//
//		browserBarH = h * h / newsTotalH;
//		newsDisplayedH = h;
//
//		tempS = new String[rows];
//		for (int i = 0; i < rows; i++) {
//			tempS[i] = (String) vStr.elementAt(i);
//		}
//		vStr.removeAllElements();
//		vStr = null;
//		System.gc();
//
//		delay = 4;
//		t = 0;
//		typerRow = 0;
//		typerLen = 0;
//		typerCharID = 0;
//
//	}
//
//	public void keyPessed(byte keyCode) {
//		switch (keyCode) {
//		case GameCanvas.KEY_UP:
//		case GameCanvas.KEY_NUM2:
//			if (!isRollBarOn) {
//				break;
//			}
//			if (newsDisplayedH > h) {
//				newsDisplayedH -= browseSpeed;
//				displayY += browseSpeed;
//			}
//			break;
//		case GameCanvas.KEY_DOWN:
//		case GameCanvas.KEY_NUM8:
//			if (!isRollBarOn) {
//				break;
//			}
//
//			if (newsDisplayedH < newsTotalH) {
//				newsDisplayedH += browseSpeed;
//				displayY -= browseSpeed;
//			}
//
//			break;
//		// case GameCanvas.KEY_SOFTKEY3:
//		case GameCanvas.KEY_NUM5:
//
//			if (isRollBarOn) {
//				break;
//			}
////
//			// = "+row);
//			if (typerRow < rows) {
//				if (typerRow == rowNo + row) {// 当前页显示完毕
//					rowNo += row;
//					// typerRow = 0;
//				} else {
//					typerRow = rows < rowNo + row ? rows : rowNo + row;
//				}
//				typerLen = 0;
//				typerCharID = 0;
//			} else {
//
//				
//
//				// 处理GameCanvas里面的事件
//				// game.cleanHelp();
//				// game.stageAvailableNum = 0;
//				// game.stage[0] = 0;
//				//
//				//
//				// game.score = 0;
//
//				// game.isMapOver = false;
//				// game.toReadRes = false;
//				// game.setResLoading();
//
//			}
//			// game.keyCode = Utility.KEY_RELEASED;
//			break;
//		case GameCanvas.KEY_SOFT_RETURN:
//			if(GameCanvas.getInst().state==GameCanvas.GAME_HELP){
//				GameCanvas.getInst().initMainMenu(3);
////				GameCanvas.getInst().initMenuPos=4;
//			}else{
//				GameCanvas.getInst().initMainMenu(4);
////				GameCanvas.getInst().initMenuPos=5;
//			}
////			GameCanvas.getInst().state = GameCanvas.GAME_STATE_INIT_MIAN_MENU;
//			GameCanvas.getInst().deletHelp();
//			break;
//
//		}
//	}
//
//	int delay;
//
//	int t;
//
//	int typerRow;
//
//	int typerLen;
//
//	int typerCharID;
//
//	/**
//	 * 打字显示
//	 * 
//	 * @param g
//	 *            Graphics
//	 */
//	public void typer(Graphics g) {
////
////
//		g.setClip(x, y, w, h);
//		g.setFont(f);
//		g.setColor(fontColor);
//		int tempY;
//		for (int i = rowNo; i < rowNo + row; i++) {
//			if (i > rows - 1) {
//				break;
//			}
//			tempY = displayY + (Game.FONT_H + 0) * (i - rowNo);
//			if (typerRow == i) {
//				typerLen += f.charWidth(tempS[i].charAt(typerCharID));
//				g.setClip(x, tempY, typerLen, Game.FONT_H + 0);
//				g.drawString(tempS[i], x, tempY, 20);
//				g.setClip(x, y, w, h);
//
//				typerCharID++;
//				while (typerCharID < tempS[i].length()
//						&& tempS[i].charAt(typerCharID) == ' ') {
//					typerLen += f.charWidth(tempS[i].charAt(typerCharID));
//					typerCharID++;
//				}
//
//				if (typerCharID == tempS[i].length()) {
//					typerRow++;
//					typerLen = 0;
//					typerCharID = 0;
//				}
//				break;
//			} else {
//				g.drawString(tempS[i], x, tempY, 20);
//			}
//		}
//		g.setClip(0, 0, width, height);
//	}
//
//	/**
//	 * 一般显示
//	 * 
//	 * @param g
//	 *            Graphics
//	 */
//	public void render(Graphics g) {
//
//		if (isRollBarOn) {
//			g.setClip(x, y, w, h);
//			g.setColor(0x00ffffff);
//
//			// 浏览进度条
//			g.setColor(0x00B7B8B8);
//			g.fillRect(x + w - 5, y, 4, h);
//			g.setColor(0x00ff0000);
//			int hh = newsDisplayedH * h / newsTotalH;
//			g.fillRect(x + w - 5, y + hh - browserBarH, 4, browserBarH);
//
//			g.setFont(f);
//			g.setColor(fontColor);
//
//			for (int i = 0; i < rows; i++) {
//				g.drawString(tempS[i], x,// x + 4 + 3,
//						displayY + imgH + 2 + (Game.FONT_H + 2) * i, 20);
//			}
//			g.setClip(0, 0, width, height);
//		} else {
//			g.setClip(x, y, w, h);
//			g.setFont(f);
//			g.setColor(fontColor);
//			for (int i = rowNo; i < rowNo + row; i++) {
//				if (i > rows - 1) {
//					break;
//				}
//				g.drawString(tempS[i], x, displayY + (Game.FONT_H + 2)
//						* (i - rowNo), 20);
//			}
//			g.setClip(0, 0, width, height);
//		}
//	}
//}


import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.game.Game;

/**
 *
 * <p>Title: Text</p>
 * <p>Description: Display charactors on the screen</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: G9</p>
 * @author Raph
 * @version 1.0.0
 */
public class Text {

    public int displayX,  displayY,  displayW,  displayH;
    int x, y;
    int delay, t, typerRow, typerLen, typerCharID;
    int curRowID;
    int rowNum;
    int rowW;
    int rowID;
    String[] content = null;

    //已显示的文本长度
    int textDisplayedH;

    //滚动条长度
    int scrollBarH;

    //文本总长度
    int textTotalH;
    /**文本滚动速度*/
    int scrollSpeed;
    /**显示的行*/
    int displayedRows;
    /**文本滚动标志*/
    boolean isTextRollable;
    boolean isScrollBarViewable;
    private Font f;
    private int fontColor;
    private int charW,  charH;
    AdvancedGraphics ag;

    public Text() {
    }


    private void clean() {
        try {

            if (content != null) {
                for (int i = 0; i < content.length; i++) {
                    content[i] = null;
                }
                content = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.gc();
        }
    }

    public int setContent(int displayX, int displayY, int displayW, int displayH, Font f, String str, int fontColor,
            boolean isTextRollable, boolean isScrollBarViewable) {
        clean();
        this.displayX = displayX;
        this.displayY = displayY;

        this.displayW = displayW;
        this.displayH = displayH;
        this.f = f;
        this.fontColor = fontColor;

        y = displayY + 4;

        this.isTextRollable = isTextRollable;
        this.isScrollBarViewable = isScrollBarViewable;

        charW = f.charWidth('媛');
        charH = Const.FONT_H + 2;

        scrollSpeed = charH;

        if (isScrollBarViewable) {
            rowW = displayW - 5 - (displayW - 5) % charW;
            x = displayX + ((displayW - 5) % charW >> 1);
        } else {
            rowW = displayW - displayW % charW;
            x = displayX + ((displayW % charW) >> 1);
        }

        rowNum = 0;
        textTotalH = 0;

        content = cutString(str, -1);
        rowNum += content.length;

        textTotalH += charH * content.length + charH;

        rowID = displayH / charH;
        curRowID = 0;

        scrollBarH = displayH * displayH / textTotalH;
        textDisplayedH = displayH;

        delay = 4;
        t = 0;
        typerRow = 0;
        typerLen = 0;
        typerCharID = 0;

        return textTotalH;

    }

    public void addContent(String str) {
        String[] temp1,temp2 = null;
        temp1 = cutString(content[content.length-1] + str, -1);
        temp2 = new String[content.length + temp1.length - 1];
        System.arraycopy(content, 0, temp2, 0, content.length - 1);
        System.arraycopy(temp1, 0, temp2, content.length-1, temp1.length);
        content = new String[temp2.length];
        System.arraycopy(temp2, 0, content, 0, temp2.length);
        
        rowNum = content.length;
        textTotalH = charH * content.length + charH;
    }

    private String[] cutString(String str, int forcedRowNum) {

        String[] strCutted = null;
        int len = str.length();
        char ch;

        StringBuffer row = new StringBuffer();
        int tempRowNum = 0;

        //temp string container
        Vector vStr = new Vector();
        for (int i = 0; i < len; i++) {
            ch = str.charAt(i);
            if (ch == '\r') {
                vStr.addElement(row.toString());
                row.delete(0, row.capacity());
                tempRowNum++;
                i++;
            } else {
                row.append(ch);
                if (i == len - 1 //                    ||sb.charAt(i+1)==0x000D
                        //                    ||f.stringWidth(row.toString()) + f.charWidth(sb.charAt(i+1)) > rowW
                        ) {
                    vStr.addElement(row.toString());
                    row.delete(0, row.capacity());
                    tempRowNum++;

                } else if (str.charAt(i + 1) != '\r') {
                    if (f.stringWidth(row.toString()) + f.charWidth(str.charAt(i + 1)) > rowW) {
                        vStr.addElement(row.toString());
                        row.delete(0, row.capacity());
                        tempRowNum++;
                    }
                } else {
                    vStr.addElement(row.toString());
                    row.delete(0, row.capacity());
                    tempRowNum++;
                    i += 2;
                }
            }
            if (tempRowNum == forcedRowNum) {
                break;
            }
        }

        strCutted = new String[tempRowNum];
        for (int i = 0; i < tempRowNum; i++) {
            strCutted[i] = (String) vStr.elementAt(i);
        }

        vStr.removeAllElements();
        vStr = null;

        row.delete(0, row.length());
        row = null;

//        System.gc();

        return strCutted;
    }

    public void setScrollSpeed(int s) {
        scrollSpeed = s;
    }

    public boolean isCanUp(){
    	return textDisplayedH > displayH;
    }

    public boolean isCanDown(){
    	return textDisplayedH < textTotalH;
    }


    public void keyProcess(KeyState key) {
    	if(key.containsAndRemove(KeyCode.UP)){
    		if (!isTextRollable) {
				return;
			}
			if (textDisplayedH > displayH) {
				textDisplayedH -= scrollSpeed;
				y += scrollSpeed;
			}
    	}else if(key.containsAndRemove(KeyCode.DOWN)){
    		if (!isTextRollable) {
				return;
			}
			if (textDisplayedH < textTotalH) {
				textDisplayedH += scrollSpeed;
				y -= scrollSpeed;
			}
    	}else if(key.containsAndRemove(KeyCode.OK)){

    		
    	}
	}

    /**
	 * @param g
	 *            Graphics
	 */
    public void typer(Graphics g) {
        g.setClip(displayX, displayY, displayW, displayH);
        g.setFont(f);
        g.setColor(fontColor);
        int tempY;
        for (int i = curRowID; i < curRowID + rowID; i++) {
            if (i > rowNum - 1) {
                break;
            }
            tempY = displayY + (Const.FONT_H + 0) * (i - curRowID);
            if (typerRow == i) {
                typerLen += f.charWidth(content[i].charAt(typerCharID));
                g.setClip(x, tempY, typerLen, Const.FONT_H + 0);
                g.drawString(content[i], x, tempY, 20);
                g.setClip(displayX, displayY, displayW, displayH);
                typerCharID++;
                while (typerCharID < content[i].length() &&
                        content[i].charAt(typerCharID) == ' ') {
                    typerLen += f.charWidth(content[i].charAt(typerCharID));
                    typerCharID++;
                }
                if (typerCharID == content[i].length()) {
                    typerRow++;
                    typerLen = 0;
                    typerCharID = 0;
                }
                break;
            } else {
                g.drawString(content[i], x, tempY, 20);
            }
        }
        g.setClip(displayX, displayY, displayW, displayH);
    }

    /**
     * @param g Graphics
     */
    public void paint(Graphics g) {
        g.setColor(fontColor);
        g.setFont(f);
        g.setClip(displayX, displayY+1, displayW, displayH);
        ag = AdvancedGraphics.getInstance(g);
        if (isTextRollable) {
            if (isCanDown() || isCanUp()/*isScrollBarViewable*/) {
            	g.setColor(0xffffff);
                g.fillRect(displayX + displayW - 4, displayY, 4, displayH);
                g.setColor(fontColor);
                int hh = textDisplayedH * displayH / textTotalH;
                g.fillRect(displayX + displayW - 4, displayY + hh - scrollBarH, 4, scrollBarH);
            }
            //content
            g.setColor(fontColor);
            for (int i = 0; i < content.length; i++) {
            	int tempY = y + charH * i;
            	if(tempY<displayY+1-(charH + 2) || tempY>displayY+1+displayH+10){
            		continue;
            	}
                ag.drawString(content[i], x, tempY, 20);
            }
        } else {
            g.setFont(f);
            g.setColor(fontColor);
            for (int i = curRowID; i < curRowID + rowID; i++) {
                if (i > rowNum - 1) {
                    break;
                }
                g.drawString(content[i], x,
                        displayY +
                        (Const.FONT_H + 2) * (i - curRowID), 20);
            }
        }
        g.setClip(displayX, displayY+1, displayW, displayH);
    }

  
}

