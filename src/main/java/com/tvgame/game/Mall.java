package com.tvgame.game;

import javax.microedition.lcdui.Graphics;
/*
import com.cgc.jme.lib.a.c;
import com.cgc.jme.lib.main.CommonMain;
*/
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.sun.midp.io.Util;
import com.tvgame.actor.Actor;
import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

public class Mall implements UiObject {
	public static int payId;
	private boolean tab_money = true;
	private int mall_money_index = 0; // ��ֵ���±�
	private int mall_change_index = 0; // �������±�
	private final static String mall_money_shuoming[] = { "5Ԫ = 500 ����", "10Ԫ = 1200 ����", };
	private final static int consumptionGold[] = { 100, 150, 10, 20, 50, 80,120 };
	private final static byte buyActorTypes[] = { Actor.TYPE_ը��, Actor.TYPE_ħ����, Actor.TYPE_����, Actor.TYPE_��ѿ������, Actor.TYPE_��ͷ�ܵ�, Actor.TYPE_���,-1
		};

	private int mall_change_type[] = { 17, 19, 0, 1, 2, 18, -1 };// ��ӦͼƬ�ϵ�id��IMG_ID_S_ITEM_ICON_ALL��

	private String mall_change_shuoming[] = { "ը��", "ħ����", "�ݶ�", "��ľ",
			"��ľ", "���", "250����" };

	private int mall_change_needGold[] = { 100, 150, 10, 20, 50, 80, 120 };

	public void draw(Graphics g) {
		
		g.setClip(0, 0,Const.WIDTH,Const.HEIGHT);
		g.fillRect(Const.WIDTH_HALF>>2, Const.HEIGHT_HALF>>2,480,384);
		Game.getInstance().drawBackGround(g, "", Const.COMMAND_BACK);
		int x = Const.WIDTH_HALF >> 2;
		int y = Const.HEIGHT_HALF >> 2;
		if (tab_money) {//��ֵ
//			GraphicsUtil.drawImage(g, x + 5, y + 5, 20,
//					Resources.IMG_ID_MALLTAB);
			
			GraphicsUtil.drawRegion(g, Resources
					.loadImage(Resources.IMG_ID_MALLTAB), 0, 0, 84, 24, 0,
					x + 5 , y + 5, 20);
			com.tvgame.util.Util.draw3DString(g, "��ֵ", x + 30, y + 8, 0,
					0xffffff, 0x0);
//			com.tvgame.util.Util.draw3DString(g, "�һ�", x + 110, y + 8, 0, 0x0,
//					0xffffff);
			drawMallItem_��ֵ����(g);
			for (int i = 0; i < 2; i++) {
				GraphicsUtil.drawImage(g, x + 70, y + 68 + 30 * i, 20,
						Resources.IMG_ID_S_ITEM_ICON);
				com.tvgame.util.Util.draw3DString(g, "����", x + 140, y + 68 + 30
						* i, 0, 0x0, 0xffcc00);
				com.tvgame.util.Util.draw3DString(g, mall_money_shuoming[i],
						x + 220, y + 68 + 30 * i, 0, 0xffffff, 0xff0000);
				GraphicsUtil.drawImage(g, x + 410, y + 68 + 30 * i, 20,
						Resources.IMG_ID_MALL_BUTTON_BG);// ��ֵbutton����ͼ
				GraphicsUtil.drawRegion(g, Resources
						.loadImage(Resources.IMG_ID_MALL_MONEY_CHANGE_TEXT),
						30, 0, 30, 14, 0, x + 420, y + 72 + 30 * i, 20);
			}
			GraphicsUtil.drawImage(g, x + (Game.getInstance().iClock % 12), y
					+ 68 + 30 * mall_money_index, 20,
					Resources.IMG_ID_MAINMENU_ITEM_HAND);
		} else {//�һ�
//			GraphicsUtil.drawRegion(g, Resources
//					.loadImage(Resources.IMG_ID_MALLTAB), 84, 0, 84, 24, 0,
//					x + 5, y + 5, 20);
			GraphicsUtil.drawRegion(g, Resources
					.loadImage(Resources.IMG_ID_MALLTAB), 0, 0, 84, 24, 0,
					x + 5 , y + 5, 20);
//			com.tvgame.util.Util.draw3DString(g, "��ֵ", x + 30, y + 8, 0, 0x0,
//					0xffffff);
			com.tvgame.util.Util.draw3DString(g, "�һ�", x + 30, y + 8, 0,
					0xffffff, 0x0);

			drawMallItem_�һ�����(g);
			for (int i = 0; i < mall_change_type.length; i++) {
				if (mall_change_type[i] != -1)//�����̵����ͼ��
					GraphicsUtil.drawFrame(g, mall_change_type[i], Resources
							.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL),
							x + 100, y + 60 + 35 * i, GraphicsUtil.TRANS_NONE,
							GraphicsUtil.LEFT_TOP, 31, 31);

				com.tvgame.util.Util.draw3DString(g, mall_change_shuoming[i],
						x + 180, y + 68 + 35 * i, 0, 0xffffff, 0x0);

				GraphicsUtil.drawImage(g, x + 280, y + 68 + 35 * i, 20,
						Resources.IMG_ID_S_ITEM_ICON);// ���ňD��
				com.tvgame.util.Util.draw3DString(g, mall_change_needGold[i]
						+ "", x + 320, y + 68 + 35 * i, 0, 0xffffff, 0x0);
				GraphicsUtil.drawImage(g, x + 390, y + 68 + 35 * i, 20,
						Resources.IMG_ID_MALL_BUTTON_BG);// �һ�button����ͼ
				GraphicsUtil.drawRegion(g, Resources
						.loadImage(Resources.IMG_ID_MALL_MONEY_CHANGE_TEXT), 0,
						0, 30, 14, 0, x + 400, y + 72 + 35 * i, 20);
			}

			GraphicsUtil.drawImage(g, x + (Game.getInstance().iClock % 12), y
					+ 68 + 35 * mall_change_index, 20,
					Resources.IMG_ID_MAINMENU_ITEM_HAND);
		}

		int show_money_x = (Const.WIDTH_HALF / 4) + 50;// ��ʾ��������
		int show_money_y = (Const.HEIGHT_HALF / 4 + 384 - 26);// ��ʾ��������
		GraphicsUtil.drawImage(g, show_money_x, show_money_y, 20,
				Resources.IMG_ID_S_ITEM_ICON);
		g.setColor(0x101a10);
		g.fillRect(show_money_x + 40, show_money_y, 80, 20);
		g.setColor(0xffffff);
		g.drawString(UserData.getGold() + "", show_money_x + 70, show_money_y,
				20);

	}

	public void drawMallItem_��ֵ����(Graphics g) {
		for (int i = 0; i < 20; i++) {
			GraphicsUtil.drawImage(g, (Const.WIDTH_HALF >> 2) + 24 * i,
					(Const.HEIGHT >> 2) + 30 * mall_money_index, 20,
					Resources.IMG_ID_ACHI_ITEMBG);
		}
	}

	public void drawMallItem_�һ�����(Graphics g) {
		for (int i = 0; i < 20; i++) {
			GraphicsUtil.drawImage(g, (Const.WIDTH_HALF >> 2) + 24 * i,
					(Const.HEIGHT >> 2) + 35 * mall_change_index, 20,
					Resources.IMG_ID_ACHI_ITEMBG);
		}
	}
	public static void doPaySucess(){
		switch(payId){
		case 0:
			UserData.addGold(500);
			break;
		case 1:
			UserData.addGold(1200);
			break;	
		}
	}
	public void update(KeyState key) {
		if (key.containsAndRemove(KeyCode.OK)) {
			if (tab_money) {
				switch (mall_money_index) {
				case 0:
					// ��ֵ1
//					Game.getInstance().push2Stack(new Payment(5));
					payId = 0;
//					CommonMain.doPayment("99880287",CommonMain.PAYMENT_TYPE_BILL);
					System.out.println("1111111111111111111111111");
					break;
				case 1:
					// ��ֵ2
					payId = 1;
//					CommonMain.doPayment("99880288",CommonMain.PAYMENT_TYPE_BILL);
					System.out.println("222222222222222222222222222");
					break;
				}
			} else {
				if (mall_change_index<6) {
					if (UserData.getGold() >= 	consumptionGold[mall_change_index]) {
						UserData.consumptionGold(	consumptionGold[mall_change_index]);
						Game.getInstance().getScene()
								.setCurActor(buyActorTypes[mall_change_index]);
						Game.getInstance().popStack();
					} else {
						Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
						tab_money = true;
					}
				}else if(mall_change_index==6){
					if (UserData.getGold() >= 	consumptionGold[mall_change_index]) {
						UserData.consumptionGold(	consumptionGold[mall_change_index]);
						UserData.addStep(250);
						Game.getInstance().popStack();
					} else {
						tab_money = true;
						Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
					}
				} 
			}
		} else if (key.containsAndRemove(KeyCode.NUM0)||key.containsAndRemove(KeyCode.BACK)) {
			Game.getInstance().popStack();
		} 
		else if (key.containsAndRemove(KeyCode.UP)) {
			if (tab_money)
				mall_money_index = mall_money_index > 0 ? --mall_money_index
						: 1;
			else
				mall_change_index = mall_change_index > 0 ? --mall_change_index
						: 6;
		} else if (key.containsAndRemove(KeyCode.DOWN)) {
			if (tab_money)
				mall_money_index = mall_money_index < 1 ? ++mall_money_index
						: 0;
			else
				mall_change_index = mall_change_index < 6 ? ++mall_change_index
						: 0;
		}

	}

}
