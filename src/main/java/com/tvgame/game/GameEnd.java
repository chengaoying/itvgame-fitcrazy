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
import com.tvgame.util.TextView;
import com.tvgame.util.Util;

public class GameEnd implements UiObject {
	public static String des = "祝贺您!\n经过多年的努力,阿兰妮部落的子民在您的精心照顾下,成功击退了来袭的恐龙,从此他们过上了幸福而又快乐的生活!\n合成的物品列表：";
	//byte count[];

	public GameEnd() {
		//count = Game.getInstance().getScene().getEndItemCount();
		rewardProps();
	}

	private String rewardInfo[][] = {
			{"0","1000","1个灌木"},
			{"1","5000","1个树木+1个灌木"},
			{"2","10000","1个树木+1个灌木+1个小房子"},
			{"3","25000","1个魔法珠+1个炸弹"},
			{"4","50000","1个炸弹+1个魔法珠+1个火把"},
			{"5","100000","1个魔法珠+1个炸弹+1个火把+1个后退一步"},
	};
	
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

		//Util.drawString(g, des, px + 40, py + 60, 20, 0, 400, '\n',Const.bigFont);
		int col = g.getColor();
		g.setColor(0x000000);
		TextView.showMultiLineText(g, des, 2, px + 40, py + 60, 400, 80);
		
		GraphicsUtil.drawImage(g, px + 40, py + 150, 20, Resources.IMG_ID_GAMEEND_ITEMLIST);
		int offx = px+107, offy = py+167;
		for(int i=0;i<=UserData.getNums().length-1;i++){
			if(i==4 || i==8){
				offx = px + 107;
				offy += 47;
			}
			GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_01),
					String.valueOf(UserData.getNums()[i]), "0123456789", offx, offy, 9, 16,
					GraphicsUtil.TRANS_NONE, 1, 1, "0");
			offx += 94;
		}
		
		String str = "抱歉，分数太低，没有任何奖励!";
		if(rewardIndex >= 0){
			str = "恭喜你，你的分数达到了"+rewardInfo[rewardIndex][1]+"，获得道具："+rewardInfo[rewardIndex][2];
		}
		//TextView.showSingleLineText(g, str, px + 40, py+300);
		TextView.showMultiLineText(g, str, 2, px + 40, py+300, 400, 80);
		g.setColor(col);
		//确定键
    	Game.drawButton(g, px +(width>>1) , py + height-18);
	}

	int rewardIndex = -1;
	private void rewardProps(){
		int scores = UserData.getScore();
		if(scores >= 100000){
			Game.getInstance().pm.addPropNum(127);
			Game.getInstance().pm.addPropNum(128);
			Game.getInstance().pm.addPropNum(129);
			Game.getInstance().pm.addPropNum(133);
			rewardIndex = 5;
		}else if(scores < 100000 && scores >= 50000){
			Game.getInstance().pm.addPropNum(127);
			Game.getInstance().pm.addPropNum(128);
			Game.getInstance().pm.addPropNum(129);
			rewardIndex = 4;
		}else if(scores < 50000 && scores >= 25000){
			Game.getInstance().pm.addPropNum(127);
			Game.getInstance().pm.addPropNum(128);
			rewardIndex = 3;
		}else if(scores < 25000 && scores >= 10000){
			Game.getInstance().pm.addPropNum(131);
			Game.getInstance().pm.addPropNum(132);
			Game.getInstance().pm.addPropNum(134);
			rewardIndex = 2;
		}else if(scores < 10000 && scores >= 5000){
			Game.getInstance().pm.addPropNum(131);
			Game.getInstance().pm.addPropNum(132);
			rewardIndex = 1;
		}else if(scores < 5000 && scores >= 1000){
			Game.getInstance().pm.addPropNum(132);
			rewardIndex = 0;
		}else{
			rewardIndex = -1;
		}
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
			//重新开始游戏
			//Game.getInstance().getScene().newScene();
			//新游戏分数清零
			//UserData.setScore(0);
			Game.getInstance().popStack();
			//rewardProps();
			SaveGameRecord record = new SaveGameRecord(Game.getInstance());
			record.saveAttainment();  //保存成就
			Game.getInstance().pm.sysProps();
			Game.getInstance().backMainMenu();
		}
	}
}
