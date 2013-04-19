package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Actor;
import com.tvgame.actor.Resources;
import com.tvgame.actor.Scene;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;

public class GameEnd implements UiObject {
	public static String des = "祝贺您!\n经过多年的努力,阿兰妮部落的子民在您的精心照顾下,成功击退了来袭的恐龙,从此他们过上了幸福而又快乐的生活!阿兰妮部落的子民为了报答您,为您献上龙币来表达他们的感谢之情!";
	byte count[];

	public GameEnd() {
		count = Game.getInstance().getScene().getEndItemCount();
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int px = Const.WIDTH_HALF >> 2;
		int py = Const.HEIGHT_HALF >> 2;
		int width = 480;
		int height = 384;
		Game.getInstance().drawBoader(g, px, py, width, height);
		Util.draw3DString(g, "建造完成", px + 20, (py) + 5, 20, 0x0, 0xffffff);
		Util.draw3DString(g, "得分", px + width - 130, py + 5, 20, 0x0, 0xffffff);
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_02),
				String.valueOf(UserData.getScore()), "0123456789", px + width
						- 120 + (Const.FONT_W_CH << 1), (py) + 9, 9, 16,
				GraphicsUtil.TRANS_NONE, 1, 1, "0");
		//
		Util.drawString(g, des, px + 40, py + 60, 20, 0, 400, '\n',
				Const.bigFont);
		// 绘制奖励金币
		drawX(g, px + 40, py + 200, Actor.TYPE_神圣骑兵, count[0] * 5);
		drawX(g, px - 200 + width, py + 200, Actor.TYPE_究极骑兵, count[1] * 10);
		drawX(g, px + 40, py + 250, Actor.TYPE_08, count[2] * 20);
		drawX(g, px - 200 + width, py + 250, Actor.TYPE_09, count[3] * 30);
		drawX(g, px +40, py + 300, Actor.TYPE_超级宝箱, count[3] * 50);
		
		//总计
		Util.draw3DString(g, "总计：", px + width - 150 , py + height -80 +10 -Const.FONT_H_HALF, GraphicsUtil.RIGHT_TOP, 0x0, 0xffffff);
		drawAddGold(g, px + width -150,py + height -80, count[0] * 5 + count[1] * 10 + count[2] * 20 + count[3] * 30+count[4]*50);
		//确定键
    	Game.drawButton(g, px +(width>>1) , py + height-18);
	}

	public static void drawX(Graphics g, int px, int py, byte type, int gold) {
		GraphicsUtil.drawFrame(g, type, Resources
				.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL), px, py,
				GraphicsUtil.TRANS_NONE, GraphicsUtil.LEFT_TOP, 31, 31);
		drawAddGold(g,px + 50,py + 5,gold);
	}
	public static void drawAddGold(Graphics g, int px, int py, int gold){
		GraphicsUtil.drawImage(g, px , py , GraphicsUtil.LEFT_TOP,
				Resources.IMG_ID_S_ITEM_ICON);
		GraphicsUtil.drawFrame(g, 1, Resources
				.loadImage(Resources.IMG_ID_NO_SYMBO_01), px + 30, py + 6,
				GraphicsUtil.TRANS_NONE, GraphicsUtil.LEFT_TOP, 8, 8);
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_01),
				String.valueOf(gold), "0123456789", px + 42, py + 2, 9, 16,
				GraphicsUtil.TRANS_NONE, 1, 1, "0");
	}
	public void update(KeyState key) {
		// TODO Auto-generated method stub
		if (key.containsAndRemove(KeyCode.OK)) {
			UserData.addGold(count[0] * 5);
			UserData.addGold(count[1] * 10);
			UserData.addGold(count[2] * 20);
			UserData.addGold(count[3] * 30);
			UserData.addGold(count[4] * 50);
			//重新开始游戏
			Game.getInstance().getScene().newScene();
			//新游戏分数清零
			UserData.setScore(0);
			Game.getInstance().popStack();
			
		}
	}
	// 出现1个菜头勇士 奖励5龙币
	// 出现1个蔬果骑兵 奖励10龙币
	// 出现1个 神圣骑兵 奖励20 龙币
	// 出现1个 究极骑兵 奖励50龙币
	// 奖励效果叠加
}
