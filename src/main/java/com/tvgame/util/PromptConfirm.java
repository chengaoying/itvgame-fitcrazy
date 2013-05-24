package com.tvgame.util;

import javax.microedition.lcdui.Image;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.game.Game;

import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.ui.DrawUtil;

public class PromptConfirm {

	/**
	 * 确认框
	 */
	private boolean running;
	private int menuIndex;
	int x = 145, y = 82;
	int mapx, mapy;
	int startx = 190, starty = 188;
	int offx = 137, offy = 81;
	private int index, x_index, y_index;
	public PromptConfirm(int index, int x_index, int y_index){
		this.index = index;
		this.x_index = x_index;
		this.y_index = y_index;
	}
	
	public int processShop(){
		running = true;
		try {
			//KeyState keyState = Game.getInstance().getKeyState();
			while (running) {
				//handleConfirm(keyState);
				if (running) {
					long t1 = System.currentTimeMillis();
					showConfirm(Game.getInstance().getSGraphics());
					Game.getInstance().flushGraphics();
					System.gc();
					int sleepTime = (int)(125-(System.currentTimeMillis()-t1));
					if (sleepTime <= 0) {
						Thread.sleep(0);
					}
					else {
						Thread.sleep(sleepTime);
					}
					running = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			clear();
		}
		return menuIndex;
	}

	private void showConfirm(SGraphics g) {
		Image bg = Resources.loadImage(Resources.IMG_ID_SHOP);
		Image botbar = Resources.loadImage(Resources.IMG_ID_BOTBAR);
		g.drawImage(bg, 0, 0, 20);
		g.drawImage(botbar, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM);
		if(x_index<2){
			//GraphicsUtil.drawRect(g, 79+(x_index*x), 185+(y_index*y), 59, 59, 3, 0xffff00);
			DrawUtil.drawRect(g, 79+(x_index*x), 185+(y_index*y), 59, 59, 3, 0xffff00);
		}else{
			//GraphicsUtil.drawRect(g, 414, 389, 105, 44, 3, 0xff0000);
			DrawUtil.drawRect(g, 414, 389, 105, 44, 3, 0xff0000);
		}
		g.setColor(0x000000);
		for(int i=0;i<Game.pm.props.length;i++){
			String price = String.valueOf(Game.pm.props[i].getPrice()); 
			String nums = String.valueOf(Game.pm.props[i].getNums()); 
			getMapXY(i);
			g.drawString(price, mapx, mapy, 20);
			g.drawString(nums, mapx, mapy+20, 20);
		}
		Game.getInstance().setFont(15, false);
		if(index < 9){
			String des = Game.pm.props[index].getDesc();
			String price = String.valueOf(Game.pm.props[index].getPrice()); 
			String str = des+"，价格："+price+Game.getInstance().getEngineService().getExpendAmountUnit();
			//TextView.showMultiLineText(g, str, 2, 413, 243, 120, 125);
			cn.ohyeah.stb.ui.TextView.showMultiLineText(g, str, 2, 413, 243, 120, 125);
		}
		g.drawString(Game.getInstance().getEngineService().getExpendAmountUnit()+"数:"
				+Game.getInstance().getEngineService().getBalance(), 412, 349, 20);
		Game.getInstance().setDefaultFont();
	}
	
	private void getMapXY(int i){
		switch(i){
		case 0:
			mapx = startx;
			mapy = starty;
			break;
		case 1:
			mapx = startx;
			mapy = starty + offy;
			break;
		case 2:
			mapx = startx;
			mapy = starty + 2*offy;
			break;
		case 3:
			mapx = startx;
			mapy = starty + 3*offy;
			break;
		case 4:
			mapx = startx + offx;
			mapy = starty;
			break;
		case 5:
			mapx = startx + offx;
			mapy = starty + offy;
			break;
		case 6:
			mapx = startx + offx;
			mapy = starty + 2*offy;
			break;
		case 7:
			mapx = startx + offx;
			mapy = starty + 3*offy;
			break;
		}
	} 
	
	/*private void handleConfirm(KeyState keyState) {
		if (keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)) {
			running = false;
			menuIndex = 0;
		}else if(keyState.containsAndRemove(KeyCode.UP)){
			menuIndex = (menuIndex+4-1)%4;
		}else if(keyState.containsAndRemove(KeyCode.DOWN)){
			menuIndex = (menuIndex+1)%4;
		}else if(keyState.containsAndRemove(KeyCode.OK)){
			running = false;
		}
	}*/

	private void clear() {
		
	}
}
