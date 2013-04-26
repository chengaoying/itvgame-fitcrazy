package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.TextView;

public class Rank implements UiObject {

	public void draw(Graphics g) {
		g.drawImage(Resources.loadImage(Resources.IMG_ID_RANKING), 0, 0, 20);
		String myRanking = "";
		String myScore = "";
		if(Game.getInstance().rankList != null && Game.getInstance().rankList.length > 0){
			Game.getInstance().setFont(15, true, g);
			g.setColor(0x000000);
			int idW = 204, idH = 25, scoreW = 145, scoreH = 25;
			int idX = 180, idY = 183, scoreX = 384, scoreY = 185;
			for(int i=0;i<Game.getInstance().rankList.length;i++){
				String id = Game.getInstance().rankList[i].getUserId();
				String score = String.valueOf(Game.getInstance().rankList[i].getScores());
				TextView.showSingleLineText(g, id, idX, idY+(i*idH), idW, idH, 1);
				TextView.showSingleLineText(g, score, scoreX, scoreY+(i*scoreH), scoreW, scoreH, 1);
				if(Game.getInstance().rankList[i].getUserId().equals(Game.getInstance().getEngineService().getUserId())){
					myRanking = String.valueOf(Game.getInstance().rankList[i].getRanking());
					myScore = String.valueOf(Game.getInstance().rankList[i].getScores());
				}
				g.drawString(myRanking, 304, 450, 20);
				g.drawString(myScore, 483, 450, 20);
			}
			Game.getInstance().setDefaultFont(g);
		}
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_BOTBAR);
	}

	public void update(KeyState key) {
    	if(key.containsAndRemove(KeyCode.OK)){
			Game.getInstance().popStack();
    	}else if(key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
    		Game.getInstance().popStack();
    	}
	}

}
