package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.ui.TextView;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

public class Shop implements UiObject{

	private int index;
	private int x_index, y_index;
	
	int x = 145, y = 82;
	
	public void draw(Graphics g) {
		g.drawImage(Resources.loadImage(Resources.IMG_ID_SHOP), 0, 0, 20);
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_BOTBAR);
		
		if(x_index<2){
			GraphicsUtil.drawRect(g, 79+(x_index*x), 185+(y_index*y), 59, 59, 3, 0xffff00);
		}else{
			GraphicsUtil.drawRect(g, 414, 389, 105, 44, 3, 0xff0000);
		}
		
		for(int i=0;i<8;i++){
			//TextView.showMultiLineText(g, text, gap, i, i, w, h)
		}
	}

	public void update(KeyState key) {
		if(key.containsAndRemove(KeyCode.UP)){
			if(x_index != 2){
				y_index = (y_index+4-1)%4;
			}
		}else if(key.containsAndRemove(KeyCode.DOWN)){
			if(x_index != 2){
				y_index = (y_index+1)%4;
			}
		}else if(key.containsAndRemove(KeyCode.LEFT)){
			//x_index = (x_index+3-1)%3;
			if(x_index > 0){
				x_index--;
			}
		}else if(key.containsAndRemove(KeyCode.RIGHT)){
			//x_index = (x_index+1)%3;
			if(x_index < 2){
				x_index++;
			}
		}else if(key.containsAndRemove(KeyCode.OK)){
			index = getIndex();
			if(index == 9){
				Recharge recharge = new Recharge(Game.getInstance());
				recharge.recharge();
			}else{
				int propId = Game.pm.props[index].getPropId();
				//System.out.println("propId:"+propId);
				//System.out.println("propPrice:"+Game.pm.getPriceById(propId));
				//System.out.println("propName:"+Game.pm.getNameById(propId));
				Game.pm.buyProp(propId, 0);
			}
			
		}else if(key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			Game.popStack();
		}
		getIndex();
	}
	
	private int getIndex(){
		if(x_index == 2){
			return 9;
		}else if(y_index == 0){
			if(x_index == 0){
				return 0;
			}else{
				return 1;
			}
		}else if(y_index == 1){
			if(x_index == 0){
				return 2;
			}else{
				return 3;
			}
		}else if(y_index == 2){
			if(x_index == 0){
				return 4;
			}else{
				return 5;
			}
		}else if(y_index == 3){
			if(x_index == 0){
				return 6;
			}else{
				return 7;
			}
		}
		return -1;
	}
}
