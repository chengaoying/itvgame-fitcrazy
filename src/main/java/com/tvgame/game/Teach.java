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

public class Teach implements UiObject{

	private int teach_index = 0;
	private int arrowsIndex;
	public Teach() {
	}
	public void draw(Graphics g) {
		//GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT_HALF, GraphicsUtil.HCENTER_VCENTER, Resources.IMG_ID_TEACH_1+teach_index);
		int ax, ay = 109;
		switch(teach_index){
		case 0:
			Image bg = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG0);
			int bgx = Const.WIDTH_HALF - bg.getWidth()/2, bgy = Const.HEIGHT_HALF - bg.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx, bgy, 20, bg);
			GraphicsUtil.drawImage(g, bgx, bgy, 20, Resources.IMG_ID_GUIDE_INFO2);
			break;
		case 1:
			Image bg1 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG8);
			int bgx1 = Const.WIDTH_HALF - bg1.getWidth()/2, bgy1 = Const.HEIGHT_HALF - bg1.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx1, bgy1, 20, bg1);
			break;
		case 2:
			if(arrowsIndex<12){
				ax = 217;
				arrowsIndex++;
			}else{
				ax = 227;
				arrowsIndex = 0;
			}
			Image dialog = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG1);
			int x = Const.WIDTH_HALF - dialog.getWidth()/2, y = Const.HEIGHT_HALF - dialog.getHeight()/2;
			GraphicsUtil.drawImage(g, x, y, 20, dialog);
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 3:
			Image tb = Resources.loadImage(Resources.IMG_ID_GUIDE_TEXTBOX1);
			int tbx = 217, tby = 109;
			GraphicsUtil.drawImage(g, tbx, tby, 20, tb);
			int x1 = tbx+20, y1 = tby+2;
			GraphicsUtil.drawImage(g, x1, y1, 20, Resources.IMG_ID_GUIDE_INFO1);
			break;
		case 4:
			if(arrowsIndex<12){
				ax = 130;
				arrowsIndex++;
			}else{
				ax = 140;
				arrowsIndex = 0;
			}
			ay = 132;
			Image bg2 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG5);
			int bgx2 = Const.WIDTH_HALF - bg2.getWidth()/2, bgy2 = Const.HEIGHT_HALF - bg2.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx2, bgy2, 20, bg2);
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 5:
			if(arrowsIndex<12){
				ax = 130;
				arrowsIndex++;
			}else{
				ax = 140;
				arrowsIndex = 0;
			}
			ay = 132;
			Image bg3 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG4);
			int bgx3 = Const.WIDTH_HALF - bg3.getWidth()/2, bgy3 = Const.HEIGHT_HALF - bg3.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx3, bgy3, 20, bg3);
			
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 6:
			if(arrowsIndex<12){
				ax = 130;
				arrowsIndex++;
			}else{
				ax = 140;
				arrowsIndex = 0;
			}
			ay = 210;
			
			Image bg4 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG2);
			int bgx4 = Const.WIDTH_HALF - bg4.getWidth()/2, bgy4 = Const.HEIGHT_HALF - bg4.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx4, bgy4, 20, bg4);
			
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 7:
			if(arrowsIndex<12){
				ax = 130;
				arrowsIndex++;
			}else{
				ax = 140;
				arrowsIndex = 0;
			}
			ay = 210;
			
			Image bg5 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG3);
			int bgx5 = Const.WIDTH_HALF - bg5.getWidth()/2, bgy5 = Const.HEIGHT_HALF - bg5.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx5, bgy5, 20, bg5);
			
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 8:
			if(arrowsIndex<12){
				ax = 130;
				arrowsIndex++;
			}else{
				ax = 140;
				arrowsIndex = 0;
			}
			ay = 290;
			
			Image bg6 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG6);
			int bgx6 = Const.WIDTH_HALF - bg6.getWidth()/2, bgy6 = Const.HEIGHT_HALF - bg6.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx6, bgy6, 20, bg6);
			
			GraphicsUtil.drawImage(g, ax, ay, 20, Resources.IMG_ID_GUIDE_ARROWS);
			break;
		case 9:
			Image bg7 = Resources.loadImage(Resources.IMG_ID_GUIDE_DIALOG7);
			int bgx7 = Const.WIDTH_HALF - bg7.getWidth()/2, bgy7 = Const.HEIGHT_HALF - bg7.getHeight()/2;
			GraphicsUtil.drawImage(g, bgx7, bgy7, 20, bg7);
			break;
		}
		
	}

	public void update(KeyState key) {
		if (key.containsAndRemove(KeyCode.OK)) {
			if (teach_index >= 0 && teach_index <= 8)
				teach_index++;
			else {
				Game.getInstance().popStack();
				UserData.setB_show_teach(false);
				clear();
			}
		}else if(key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			teach_index = 9;
		}else if(key.containsAndRemove(KeyCode.NUM9)){
			Game.getInstance().openShop();
		}
	}
	
	private void clear(){
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG0);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_INFO2);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG8);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG1);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_ARROWS);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_TEXTBOX1);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_INFO1);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG5);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG4);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG2);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG3);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG6);
		Resources.releaseImage(Resources.IMG_ID_GUIDE_DIALOG7);
	}
	
	public void releaseTeachRes()
	{
		Resources.releaseImage(Resources.IMG_ID_TEACH_1);
		Resources.releaseImage(Resources.IMG_ID_TEACH_2);
		Resources.releaseImage(Resources.IMG_ID_TEACH_3);
		Resources.releaseImage(Resources.IMG_ID_TEACH_4);
	}

}
