package com.tvgame.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.TextView;

/**
 * 游戏中弹出成就
 * @author 
 */
public class Achi_GameTip implements UiObject{
	
	private int i;
	private long sTime, eTime ;
	private int propId;
	
	public Achi_GameTip(int i, int j){
		this.i = i;
		this.propId = j;
		sTime = System.currentTimeMillis()/3000;
	}

	public void draw(Graphics g) {
		//Game.getInstance().drawBackGround(g, "");
		Image info_bg = Resources.loadImage(Resources.IMG_ID_ACHI_INFO_BG);
		int info_bgW = info_bg.getWidth(), info_bgH = info_bg.getHeight();
		int info_bgX = Const.WIDTH_HALF - info_bgW/2, info_bgY = Const.HEIGHT_HALF - info_bgH/2;
		
		GraphicsUtil.drawImage(g, info_bgX, info_bgY, 20, info_bg);
		
		//String achi_img = Resources.getImageName(Integer.parseInt(Const.achi_info[pageIndex*4+i][0][3]));
		Image achi = Resources.getImage(Integer.parseInt(Const.achi_info[i][0][3]));
		int achiW = achi.getWidth(), achiH = achi.getHeight();
		int achiX = info_bgX + 12 + (66/2-achiW/2), achiY = info_bgY + 13 + (66/2 - achiH/2);
		GraphicsUtil.drawImage(g, achiX, achiY, 20, achi);
		
		String achi_name = Const.achi_info[i][0][0];
		String achi_desc = "恭喜你获得了一个"+Game.getInstance().pm.getPropById(propId).getName()+"，";
		int num;
		int medalX = info_bgX + 282, medalY = info_bgY + 2;
		if(UserData.achi_list[i][2]){
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
			medalX += 65;
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_SILVER_MEDAL);
			medalX += 65;
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_GOLD_MEDAL);
			achi_desc = "完成";
		}else if(UserData.achi_list[i][1]){
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
			medalX += 65;
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_SILVER_MEDAL);
			medalX += 65;
			num = Integer.parseInt(Const.achi_info[i][2][1]) - UserData.getNums()[i];
			achi_desc += "还差"+num+Const.achi_info[i][2][4];
		}else if(UserData.achi_list[i][0]){
			GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
			medalX += 65;
			num = Integer.parseInt(Const.achi_info[i][1][1]) - UserData.getNums()[i];
			achi_desc += "还差"+num+Const.achi_info[i][1][4];
		}else{
			num = Integer.parseInt(Const.achi_info[i][0][1]) - UserData.getNums()[i];
			achi_desc = "还差"+num+Const.achi_info[i][0][4];
		}
		
		int achi_nameX = info_bgX + 100, achi_nameY = info_bgY + 10;
		int achi_descX = info_bgX + 83, achi_descY = info_bgY + 40;
		Game.getInstance().setFont(25, true, g);
		g.setColor(0xffffff);
		g.drawString(achi_name, achi_nameX+2, achi_nameY-1, 20);
		g.setColor(0x000000);
		g.drawString(achi_name, achi_nameX, achi_nameY, 20);
		
		Game.getInstance().setFont(10, false, g);
		TextView.showMultiLineText(g, achi_desc, 2, achi_descX, achi_descY, 200, 45);
		
		info_bgY += info_bgH;
	}

	public void update(KeyState key) {
		eTime = System.currentTimeMillis()/3000;
		if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
		}
		if(eTime - sTime >= 3){
			Game.getInstance().popStack();
		}
	}

}
