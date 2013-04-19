package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

/**
 * ��Ϸ�е����ɾ�
 * @author 
 */
public class Achi_GameTip implements UiObject{

	public void draw(Graphics g) {
		Game.getInstance().drawBackGround(g, "");
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT/3, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_ACHI_GAME_SHOWTIP);
		com.tvgame.util.Util.draw3DString(g, "["+Achichment.�ɾ�����[Achichment.achi_which]+"]"+"   �ɾ��Ѿ���ɣ�", Const.WIDTH/3+10, Const.HEIGHT_HALF, 0, 0xffffff, 0x0);
		com.tvgame.util.Util.draw3DString(g, "�������  "+Achichment.show_gold[Achichment.achi_which], (Const.WIDTH-4*Const.FONT_W_CH)/2, Const.HEIGHT_HALF+60, 0, 0xffffff, 0xff0000);
	}

	public void update(KeyState key) {
		if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
		}
	}

}
