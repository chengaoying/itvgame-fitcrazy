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
	public static final String help_str = "每次将屏幕右上角刷新出来的物品放到地图里的格子中，有3个包括3个以上的的相同级别的物品连在一起即可引发合体，合体后就会变成更高一级的物品。游戏中，每一株草，每一棵灌木，每一只恐龙都哭着喊求合体。你要学会用各种道具来建造你的村落，建造完成后，你将获得一笔丰厚的奖励。加油吧!";

	public static final int COMMAND_OK = 0;
	public static final int COMMAND_BACK = 1;
	
	
	/** 音效地址 */
	public static final String PIG_WAV = "/soundeffect/pig.wav";

	/** 音效地址 */
	public static final String DINOSAUR_WAV = "/soundeffect/dinosaur.wav";
	
	/*成就奖励的道具列表*/
	public static int achi_props[][] = {
		{74},
		{75},
		{75,74},
		{75},
		{76},
		{76,75},
		{76},
		{70},
		{70,76},
		{70},
		{71,77},
		{70,71,77},
		{70},
		{71,70},
		{70,71,70},
		{70,70},
		{71,71},
		{70,71,70,71},
		{70,71},
		{71,71,70,70},
		{70,71,70,71,72,72},
		{70,71,72},
		{70,71,72,70,71,72},
		{70,71,72,70,71,72,77,77},
		{70},
		{71,77},
		{70,71,77},
		{70,70},
		{71,71},
		{70,71,70,71},
		{70,71},
		{71,70,71,70},
		{70,71,70,71,72,72},
		{70,71,72},
		{70,71,72,70,71,72},
		{70,71,72,70,71,72,77,77},
	};
	/*public static int achi_props[][] = {
		{132},
		{131},
		{132,131},
		{131},
		{134},
		{134,131},
		{134},
		{127},
		{127,134},
		{127},
		{128,133},
		{127,128,133},
		{127},
		{128,127},
		{127,128,127},
		{127,127},
		{128,128},
		{127,128,127,128},
		{127,128},
		{128,128,127,127},
		{127,128,127,128,129,129},
		{127,128,129},
		{127,128,129,127,128,129},
		{127,128,129,127,128,129,133,133},
		{127},
		{128,133},
		{127,128,133},
		{127,127},
		{128,128},
		{127,128,127,128},
		{127,128},
		{128,127,128,127},
		{127,128,127,128,129,129},
		{127,128,129},
		{127,128,129,127,128,129},
		{127,128,129,127,128,129,133,133},
	};*/
	
	/**
	 * 成就信息
	 */
	public static String achi_info[][][] = {
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成灌木", "2"/*"50"*/, "0", "1", /*"还需200"*/"个达到铜牌成就","，奖励1个灌木"},
			{"合成灌木", "5"/*"500"*/, "1", "1", "个达到银牌成就","，奖励1个树木"},
			{"合成灌木", "7"/*"1000"*/, "2", "1", "个达到金牌成就","，奖励1个灌木+1个树木"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成树木", "30", "3", "2", /*"还需200"*/"个达到铜牌成就","，奖励1个树木"},
			{"合成树木", "300", "4", "2", "个达到银牌成就","，奖励1个草棚"},
			{"合成树木", "800", "5", "2", "个达到金牌成就","，奖励1个树木+1个草棚"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成草棚", "25", "6", "3", /*"还需200"*/"个达到铜牌成就","，奖励1个草棚"},
			{"合成草棚", "250", "7", "3", "个达到银牌成就","，奖励1个炸弹"},
			{"合成草棚", "500", "8", "3", "个达到金牌成就","，奖励1个草棚+1个炸弹"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成砖房", "20", "9", "4", /*"还需200"*/"个达到铜牌成就","，奖励1个炸弹"},
			{"合成砖房", "200", "10", "4", "个达到银牌成就","，奖励1个魔法珠+1个后退一步"},
			{"合成砖房", "400", "11", "4", "个达到金牌成就","，奖励1个炸弹+1个魔法珠+1个后退一步"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成公寓", "15", "12", "5", /*"还需200"*/"个达到铜牌成就","，奖励1个炸弹"},
			{"合成公寓", "150", "13", "5", "个达到银牌成就","，奖励1个魔法珠+1个炸弹"},
			{"合成公寓", "300", "14", "5", "个达到金牌成就","，奖励1个魔法珠+2个炸弹"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成城堡", "10", "15", "6", /*"还需200"*/"个达到铜牌成就","，奖励2个炸弹"},
			{"合成城堡", "100", "16", "6", "个达到银牌成就","，奖励2个魔法珠"},
			{"合成城堡", "250", "17", "6", "个达到金牌成就","，奖励2个炸弹+2个魔法珠"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成空中花园", "5", "18", "7", /*"还需200"*/"个达到铜牌成就","，奖励1个魔法珠+1个炸弹"},
			{"合成空中花园", "50", "19", "7", "个达到银牌成就","，奖励2个炸弹+2个魔法珠"},
			{"合成空中花园", "100", "20", "7", "个达到金牌成就","，奖励2个魔法珠+2个炸弹+2个火把"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成空中堡垒", "1", "21", "8", /*"还需200"*/"个达到铜牌成就","，奖励1个魔法珠+1个炸弹+1个火把"},
			{"合成空中堡垒", "25", "22", "8", "个达到银牌成就","，奖励2个炸弹+2个魔法珠+2个火把"},
			{"合成空中堡垒", "50", "23", "8", "个达到金牌成就","，奖励2个炸弹+2个魔法珠+2个火把+2个后退一步"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成石头", "20", "24", "11", /*"还需200"*/"个达到铜牌成就","，奖励1个炸弹"},
			{"合成石头", "200", "25", "11", "个达到银牌成就","，奖励1个魔法珠+1个后退一步"},
			{"合成石头", "400", "26", "11", "个达到金牌成就","，奖励1个炸弹+1个魔法珠+1个后退一步"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成化石", "10", "27", "12", /*"还需200"*/"个达到铜牌成就","，奖励2个炸弹"},
			{"合成化石", "100", "28", "12", "个达到银牌成就","，奖励2个魔法珠"},
			{"合成化石", "250", "29", "12", "个达到金牌成就","，奖励2个炸弹+2个魔法珠"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成宝箱", "5", "30", "13", /*"还需200"*/"个达到铜牌成就","，奖励1个炸弹+1个魔法珠"},
			{"合成宝箱", "50", "31", "13", "个达到银牌成就","，奖励2个魔法珠+2个魔法珠"},
			{"合成宝箱", "100", "32", "13", "个达到金牌成就","，奖励2个炸弹+2个魔法珠+2个火把"},
		},
		{
			/*0-成就名称, 1-成就条件, 2-奖品id, 3-成就id, 4-描述*/
			{"合成大宝箱", "1", "33", "14", /*"还需200"*/"个达到铜牌成就","，奖励1魔法珠+1个炸弹+1个火把"},
			{"合成大宝箱", "25", "34", "14", "个达到银牌成就","，奖励2个炸弹+2个魔法珠+2个火把"},
			{"合成大宝箱", "50", "35", "14", "个达到金牌成就","，奖励2个炸弹+2个魔法珠+2个火把+2个后退一步"},
		},
	};
	
}
