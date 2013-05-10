package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.Util;
/**
 * ����
 * @author xiaobai
 *
 */
public class Help implements UiObject {

	public void draw(Graphics g) {
		Game.getInstance().drawBackGround(g,"����");
		Util.drawString(g, Const.help_str, Const.WIDTH_HALF/4+40, Const.HEIGHT_HALF/4+60, 20, 0, 400, '\n', Const.bigFont);
	}

	public void update(KeyState key) {
    	if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
    	}else if(key.containsAndRemove(KeyCode.NUM9)){
			Game.getInstance().openShop();
		}
	}

}
