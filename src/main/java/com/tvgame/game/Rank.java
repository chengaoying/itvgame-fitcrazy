package com.tvgame.game;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;

public class Rank implements UiObject {
	private int rank_index = 0;//

	public void draw(Graphics g) {
		Game.getInstance().drawBackGround(g,"排行榜");
//<<<<<<< .mine
		if(UserData.getRankList() !=null){
			Util.draw3DString(g, "名次", Const.WIDTH_HALF/4+40, 100, 20, 0xffffff, 0x0);
			Util.draw3DString(g, "用户名 ", Const.WIDTH_HALF-40, 100, GraphicsUtil.LEFT_TOP, 0xffffff, 0x0);
			Util.draw3DString(g, "得分", Const.WIDTH_HALF+180, 100, GraphicsUtil.LEFT_TOP, 0xffffff, 0x0);
			String data[];
			for (int i = 0; i < UserData.getRankList().size(); i++) {
				data =(String [])UserData.getRankList().elementAt(i);
//				if(i==achi_item_index){
//					drawAchiItemBg(g);
//				}
				Util.draw3DString(g, data[3], Const.WIDTH_HALF/4+50, 125+(Const.FONT_H+4)*i, 20, 0xffffff, 0x0);
				Util.draw3DString(g, data[0], Const.WIDTH_HALF-60, 125+(Const.FONT_H+4)*i, 20, 0xffffff, 0x0);
				Util.draw3DString(g, data[2], Const.WIDTH_HALF+170, 125+(Const.FONT_H+4)*i, 20, 0xffffff, 0x0);
			}
		}
//=======
//		Achichment.drawAchiItemBg(g, rank_index);
//		int x = Const.WIDTH_HALF>>2;
//		int y = Const.HEIGHT_HALF>>2;
//		for (int i = 0; i < total_item; i++) {
//			com.tvgame.util.Util.draw3DString(g, ""+(i+1), x+140, y+68+33*i, 0, 0x0, 0xffcc00);
//			com.tvgame.util.Util.draw3DString(g, "得分: "+ UserData.getScore(), x+220, y+68+33*i, 0, 0xffffff, 0xff0000);
//		}
//>>>>>>> .r264
	}
	/**
	 * 
	 * 绘制成就选项的蓝色背景
	 * @param g
	 */
	public void drawAchiItemBg(Graphics g)
	{
		for(int index=0;index<20;index++){
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF/4+40+index*10, 125+(Const.FONT_H+4)*rank_index, 20, Resources.IMG_ID_ACHI_ITEMBG);
		}
	}

	public void update(KeyState key) {
    	if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
    	}else if(key.containsAndRemove(KeyCode.UP)){
    		if(UserData.getRankList() !=null){
    			rank_index = rank_index >0?--rank_index:UserData.getRankList().size()-1;
    		}
    	}else if(key.containsAndRemove(KeyCode.DOWN)){
    		if(UserData.getRankList() !=null){
    			rank_index = rank_index <UserData.getRankList().size()-1?++rank_index:0;
    		}
    	}
//    	else if(KeyBoard.isKeyDown(KeyBoard.GMK_UP))
//    		rank_index = rank_index >0?--rank_index:total_item-1;
//    	else if(KeyBoard.isKeyDown(KeyBoard.GMK_DOWN))
//    		rank_index = rank_index <total_item-1?++rank_index:0;
	}

}
