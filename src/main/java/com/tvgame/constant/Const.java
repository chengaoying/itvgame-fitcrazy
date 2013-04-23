package com.tvgame.constant;

import javax.microedition.lcdui.Font;

/***
 * ����豸��صĳ���(��Ļ�Ŀ��|��ֵ|����)
 * 
 * @author xiaobai
 * 
 */
public class Const {


	
	
	
	//=======================��Ļ����=====================
 
	public static final int WIDTH = 640;

	public static final int HEIGHT = 530;

	/**
	 *  ��Ļ��һ��,��Ϊ�������ٳ����е�����
	 */
	public static final int WIDTH_HALF = WIDTH >> 1;

	public static final int HEIGHT_HALF = HEIGHT >> 1;
	// ====================���峣��========================
	public static Font smallFont = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

	public static Font bigFont = Font.getFont(Font.FACE_SYSTEM,
			Font.STYLE_BOLD, Font.SIZE_LARGE);

	public static Font font = bigFont;

	public static final int FONT_W_CH = font.charWidth('��');// 14

	public static final int FONT_W_EG = font.charWidth('A');// 9

	public static final int FONT_H = font.getHeight();//23

	public static final int FONT_H_HALF = FONT_H>>1;
	public static final String help_str = "ÿ�ν���Ļ���Ͻ�ˢ�³�������Ʒ�ŵ���ͼ��ĸ����У���3������3�����ϵĵ���ͬ�������Ʒ����һ�𼴿��������壬�����ͻ��ɸ���һ������Ʒ��\n��Ϸ�У�ÿһ��ݣ�ÿһ�ù�ľ��ÿһֻ���������ź�����塣��Ҫѧ���ø��ֵ�����������Ĵ��䣬������ɺ��㽫���һ�ʷ��Ľ��������Ͱɣ�\n��Ϸ�а������ּ�[9]�鿴������";//ÿ�ν���Ļ���Ͻ�ˢ�³�������Ʒ�ŵ���ͼ��ĸ����У���3������3�����ϵĵ���ͬ�������Ʒ����һ�𼴿��������壬�����ͻ��ɸ���һ������Ʒ����Ϸ�У��㻹����ʹ��ը��ը�ٵ�ǰ�㲻��Ҫ����Ʒ��Ҳ����ʹ��ħ������������ǰ��ͼ�ϵĶ����Ʒ��λ�á��ɰ���С��������ϲ���ڵ�ͼ�������������㣬������������ĳ���׶γ�������Ϸ��ͼ��ǵ���ֻ���û�Ѳ����������ǡ�\n��Ϸ�а������ּ�9������ʱ������Ϸ�̳ǡ�

	public static final int COMMAND_OK = 0;
	public static final int COMMAND_BACK = 1;
	
	
	/** ��Ч��ַ */
	public static final String PIG_WAV = "/soundeffect/pig.wav";

	/** ��Ч��ַ */
	public static final String DINOSAUR_WAV = "/soundeffect/dinosaur.wav";
	
}
