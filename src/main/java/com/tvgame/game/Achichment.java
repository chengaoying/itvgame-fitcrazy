package com.tvgame.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

//import com.sun.j2me.global.Resource;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.TextView;

public class Achichment implements UiObject {
	
	private int pageSize = 4;
	private int pageCount = 3;
	private int pageIndex;
	private boolean isRight;

	// 根据玩家分数获得开启相应的成就
	public static void getAchByScore() {
		/*
		 * int score = UserData.getScore();
		 * if(score>=award_range[0]&&score<award_range[1])//达到2500且小于4000 {
		 * if(!achi_arrived[0]) { achi_arrived[0]=true; // UserData.addGold(8);
		 * achi_which = 0; Game.getInstance().push2Stack(new Achi_GameTip());
		 * return; } }
		 */
		for(int i=0;i<UserData.achi_list.length;i++){
			for(int j=0;j<UserData.achi_list[i].length;j++){
				if(UserData.achi_list[i][j] && !UserData.achi_show[i][j]){
					Game.getInstance().push2Stack(new Achi_GameTip(i,Integer.parseInt(Const.achi_info[i][j][2])));
					UserData.achi_show[i][j] = true;
					int id = Integer.parseInt(Const.achi_info[i][j][2]);
					for(int k=0;k<Const.achi_props[id].length;k++){
						Game.getInstance().pm.addPropNum(Const.achi_props[id][k]);
					}
					Game.getInstance().pm.printInfo();
				}
			}
		}
	}

	public void draw(Graphics g) {
		GraphicsUtil.drawImage(g, 0, 0, 20, Resources.IMG_ID_ACHI_BG);
		
		Image info_bg = Resources.loadImage(Resources.IMG_ID_ACHI_INFO_BG);
		int info_bgW = info_bg.getWidth(), info_bgH = info_bg.getHeight();
		int info_bgX = Const.WIDTH/2 - info_bgW/2, info_bgY = 95;
		int index = pageIndex*pageSize;
		for(int i=0;i<4;i++){
			GraphicsUtil.drawImage(g, info_bgX, info_bgY, 20, info_bg);
			
			//String achi_img = Resources.getImageName(Integer.parseInt(Const.achi_info[pageIndex*4+i][0][3]));
			Image achi = Resources.getImage(Integer.parseInt(Const.achi_info[index+i][0][3]));
			int achiW = achi.getWidth(), achiH = achi.getHeight();
			int achiX = info_bgX + 12 + (66/2-achiW/2), achiY = info_bgY + 13 + (66/2 - achiH/2);
			GraphicsUtil.drawImage(g, achiX, achiY, 20, achi);
			
			String achi_name = Const.achi_info[index+i][0][0];
			String achi_desc = "";
			int num;
			int medalX = info_bgX + 282, medalY = info_bgY + 2;
			if(UserData.achi_list[i+index][2]){
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
				medalX += 65;
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_SILVER_MEDAL);
				medalX += 65;
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_GOLD_MEDAL);
				achi_desc = "完成";
			}else if(UserData.achi_list[i+index][1]){
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
				medalX += 65;
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_SILVER_MEDAL);
				medalX += 65;
				num = Integer.parseInt(Const.achi_info[index+i][2][1]) - UserData.getNums()[index+i];
				achi_desc = "还差"+num+Const.achi_info[index+i][2][4]+Const.achi_info[i][0][5];
			}else if(UserData.achi_list[i+index][0]){
				GraphicsUtil.drawImage(g, medalX, medalY, 20, Resources.IMG_ID_ACHI_BRONZE_MEDAL);
				medalX += 65;
				num = Integer.parseInt(Const.achi_info[index+i][1][1]) - UserData.getNums()[index+i];
				achi_desc = "还差"+num+Const.achi_info[index+i][1][4]+Const.achi_info[i][0][5];
			}else{
				num = Integer.parseInt(Const.achi_info[index+i][0][1]) - UserData.getNums()[index+i];
				achi_desc = "还差"+num+Const.achi_info[index+i][0][4]+Const.achi_info[i][0][5];
			}
			
			int achi_nameX = info_bgX + 100, achi_nameY = info_bgY + 10;
			int achi_descX = info_bgX + 83, achi_descY = info_bgY + 40;
			Game.getInstance().setFont(25, true, g);
			g.setColor(0xffffff);
			g.drawString(achi_name, achi_nameX+2, achi_nameY-1, 20);
			g.setColor(0x000000);
			g.drawString(achi_name, achi_nameX, achi_nameY, 20);
			
			Game.getInstance().setFont(10, false, g);
			TextView.showMultiLineText(g, achi_desc, 2, achi_descX, achi_descY, 180, 45);
			
			info_bgY += info_bgH;
		}
		
		Image left_1 = Resources.loadImage(Resources.IMG_ID_ACHI_LEFT_1);
		Image left_2 = Resources.loadImage(Resources.IMG_ID_ACHI_LEFT_2);
		Image right_1 = Resources.loadImage(Resources.IMG_ID_ACHI_RIGHT_1);
		Image right_2 = Resources.loadImage(Resources.IMG_ID_ACHI_RIGHT_2);
		int w = right_1.getWidth(), h = right_1.getHeight();
		int leftX = 3, rigthX = Const.WIDTH - w - 3;
		int leftY = Const.HEIGHT/2 - h/2;
		if(isRight){
			GraphicsUtil.drawRect(g, rigthX, leftY, w, h, 3, 0xff0000);
		}else{
			GraphicsUtil.drawRect(g, leftX, leftY, w, h, 3, 0xff0000);
		}
		if(pageIndex<=0){
			GraphicsUtil.drawImage(g, leftX, leftY, 20, left_2);
			GraphicsUtil.drawImage(g, rigthX, leftY, 20, right_1);
		}else if(pageIndex>=pageCount-1){
			GraphicsUtil.drawImage(g, leftX, leftY, 20, left_1);
			GraphicsUtil.drawImage(g, rigthX, leftY, 20, right_2);
		}else{
			GraphicsUtil.drawImage(g, leftX, leftY, 20, left_1);
			GraphicsUtil.drawImage(g, rigthX, leftY, 20, right_1);
		}
		
		Image main_title = Resources.loadImage(Resources.IMG_ID_ACHI_MAIN_TITLE);
		int titleX = Const.WIDTH/2 - main_title.getWidth()/2, titleY = 5;
		GraphicsUtil.drawImage(g, titleX, titleY, 20, main_title);
		
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_BOTBAR);
	}

	public void update(KeyState key) {
		if (key.containsAndRemove(KeyCode.OK)) {
			Game.getInstance().popStack();
		}else if(key.containsAndRemove(KeyCode.LEFT)){
			isRight = false;
			if(pageIndex > 0){
				pageIndex --;
			}
		}else if(key.containsAndRemove(KeyCode.RIGHT)){
			isRight = true;
			if(pageIndex < pageCount-1){
				pageIndex++;
			}
		}else if(key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			Game.getInstance().popStack();
		}else if(key.containsAndRemove(KeyCode.NUM9)){
			Game.getInstance().openShop();
		}
	}
}
