package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

public class Teach implements UiObject{

	private int teach_index = 0;
	public Teach() {
	}
	public void draw(Graphics g) {
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_TEACH_1+teach_index);
//		if(teach_index<=2)
//		{
//		g.drawRegion(Resources.loadImage(Resources.IMG_ID_TEACH_BUTTON), 0, 0, 126, 21, 0,Const.WIDTH_HALF-126,Const.HEIGHT-110,20);
//		g.drawRegion(Resources.loadImage(Resources.IMG_ID_TEACH_BUTTON), 126, 0, 126, 21, 0,Const.WIDTH_HALF,Const.HEIGHT-110,20);
//		}
//		else {
//			g.drawRegion(Resources.loadImage(Resources.IMG_ID_TEACH_BUTTON), 0, 0, 126, 21, 0,Const.WIDTH_HALF,Const.HEIGHT-110,20);
//		
//		}
	}

	public void update(KeyState key) {
		//退出教学
//		if(KeyBoard.isKeyDown(KeyBoard.GMK_0)){
//			Game.getInstance().popStack();
//			releaseTeachRes();
//			UserData.setB_show_teach(false);
//		}else 
			if(key.containsAndRemove(KeyCode.OK)){//下一页
			if(teach_index>=0&&teach_index<=2)
				teach_index++;
			else
			{
				Game.getInstance().popStack();
				UserData.setB_show_teach(false);
				releaseTeachRes();
			}
		}
	}
	
	public void releaseTeachRes()
	{
		Resources.releaseImage(Resources.IMG_ID_TEACH_1);
		Resources.releaseImage(Resources.IMG_ID_TEACH_2);
		Resources.releaseImage(Resources.IMG_ID_TEACH_3);
		Resources.releaseImage(Resources.IMG_ID_TEACH_4);
	}

}
