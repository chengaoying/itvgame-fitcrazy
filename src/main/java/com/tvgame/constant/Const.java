package com.tvgame.constant;

import javax.microedition.lcdui.Font;

/***
 * 存放设备相关的常量(屏幕的宽高|键值|字体)
 * 
 * @author xiaobai
 * 
 */
public class Const {


	
	
	
	//=======================屏幕属性=====================
 
	public static final int WIDTH = 640;

	public static final int HEIGHT = 530;

	/**
	 *  屏幕的一半,定为常量减少程序中的运算
	 */
	public static final int WIDTH_HALF = WIDTH >> 1;

	public static final int HEIGHT_HALF = HEIGHT >> 1;
	// ====================字体常量========================
	public static Font smallFont = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

	public static Font bigFont = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_BOLD, Font.SIZE_LARGE);

	public static Font font = bigFont;

	public static final int FONT_W_CH = font.charWidth('中');// 14

	public static final int FONT_W_EG = font.charWidth('A');// 9

	public static final int FONT_H = font.getHeight();//23

	public static final int FONT_H_HALF = FONT_H>>1;
	public static final String help_str = "每次将屏幕右上角刷新出来的物品放到地图里的格子中，有3个包括3个以上的的相同级别的物品连在一起即可引发合体，合体后就会变成更高一级的物品。\n游戏中，每一株草，每一棵灌木，每一只恐龙都哭着喊求合体。你要学会用各种道具来建造你的村落，建造完成后，你将获得一笔丰厚的奖励。加油吧！\n游戏中按下数字键[9]查看帮助。";//每次将屏幕右上角刷新出来的物品放到地图里的格子中，有3个包括3个以上的的相同级别的物品连在一起即可引发合体，合体后就会变成更高一级的物品。游戏中，你还可以使用炸弹炸毁当前你不想要的物品，也可以使用魔法珠来交换当前地图上的多个物品的位置。可爱的小恐龙常常喜欢在地图上漫步来干扰你，另外飞天猪会在某个阶段出现在游戏地图里，记得你只能用火把才能烧死他们。\n游戏中按下数字键9可以随时进入游戏商城。

	public static final int COMMAND_OK = 0;
	public static final int COMMAND_BACK = 1;
	
	
	/** 音效地址 */
	public static final String PIG_WAV = "/soundeffect/pig.wav";

	/** 音效地址 */
	public static final String DINOSAUR_WAV = "/soundeffect/dinosaur.wav";
	
}
