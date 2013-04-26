package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

/**
 * 游戏中弹出成就
 * @author 
 */
public class Achi_GameTip implements UiObject{

	public void draw(Graphics g) {
		Game.getInstance().drawBackGround(g, "");
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT/3, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_ACHI_GAME_SHOWTIP);
	}

	public void update(KeyState key) {
		if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
		}
	}

}
