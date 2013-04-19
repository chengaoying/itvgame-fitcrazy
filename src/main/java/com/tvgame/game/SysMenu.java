package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;

import com.tvgame.actor.Resources;
import com.tvgame.actor.Scene;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;


//暂停菜单
public class SysMenu implements UiObject {
	int selectIndex;
	private String game_pause_str[] ={"继续游戏","游戏帮助","成就列表","回主菜单"};
	public SysMenu(){

	}
	public void draw(Graphics g) {
		Game.getInstance().drawBoader(g, Const.WIDTH_HALF>>2, Const.HEIGHT_HALF>>2,480,384);
    	Util.draw3DString(g, "暂停", Const.WIDTH_HALF - Const.FONT_W_CH, Const.HEIGHT_HALF/4+5, 20, 0x0, 0xffffff);
		int px = Const.WIDTH_HALF, py;
    	for(int i = 0;i<game_pause_str.length;i++){
    		py = 160+i*60;
    		GraphicsUtil.drawImage(g, px, py, GraphicsUtil.HCENTER_TOP, Resources.IMG_ID_BUTTON_01);
    		Util.draw3DString(g, game_pause_str[i], px-40, py+5, 0, 0x0, 0xffffff);
    	}
    	GraphicsUtil.drawImage(g, px-80, 160+selectIndex*60, GraphicsUtil.HCENTER_TOP, Resources.IMG_ID_MAINMENU_ITEM_HAND);
		
	}

	public void update(KeyState key) {
		if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
			switch(selectIndex){
			case 0:
				//返回游戏
				break;
			case 1:
				//帮助
				Game.getInstance().openHelp();
				break;
			case 2:
				Game.getInstance().openAchichment();
				break;
			case 3://返回主菜单的时保存且提交游戏分数
				//保存游戏 
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("是否保存游戏记录!");
				if(pc.popup() == 0){
					SaveGameRecord record = new SaveGameRecord(Game.getInstance());
					record.saveRecord();
					PopupText pt = UIResource.getInstance().buildDefaultPopupText();
					pt.setText("保存成功!");
					pt.popup();
				}
				Game.getInstance().backMainMenu();
				break;
			}
		}else if(key.containsAndRemove(KeyCode.UP)){
			selectIndex = selectIndex>0?--selectIndex:game_pause_str.length-1;
		}else if(key.containsAndRemove(KeyCode.DOWN)){
			selectIndex = selectIndex<game_pause_str.length-1?++selectIndex:0;
		}
	}

}
