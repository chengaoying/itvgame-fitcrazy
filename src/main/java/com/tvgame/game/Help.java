package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.TextView;
import com.tvgame.util.Util;
/**
 * °ïÖú
 * @author xiaobai
 *
 */
public class Help implements UiObject {

	public void draw(Graphics g) {
		Game.getInstance().drawBackGround(g,"°ïÖú");
		//Util.drawString(g, Const.help_str, Const.WIDTH_HALF/4+40, Const.HEIGHT_HALF/4+60, 20, 0, 400, '\n', Const.bigFont);
		g.setColor(0x000000);
		TextView.showMultiLineText(g, Const.help_str, 2, Const.WIDTH_HALF/4+40, Const.HEIGHT_HALF/4+60, 405, 265);
	}

	public void update(KeyState key) {
    	if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
    	}else if(key.containsAndRemove(KeyCode.NUM9)){
			Game.getInstance().openShop();
		}
	}

}
