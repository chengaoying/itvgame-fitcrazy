package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;

import com.tvgame.actor.Resources;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.TextView;

public class Shop implements UiObject{

	private int index;
	private int x_index, y_index;
	
	int x = 145, y = 82;
	int mapx, mapy;
	int startx = 190, starty = 188;
	int offx = 137, offy = 81;
	
	public void draw(Graphics g) {
		g.drawImage(Resources.loadImage(Resources.IMG_ID_SHOP), 0, 0, 20);
		GraphicsUtil.drawImage(g, Const.WIDTH_HALF, Const.HEIGHT, GraphicsUtil.HCENTER_BOTTOM, Resources.IMG_ID_BOTBAR);
		
		if(x_index<2){
			GraphicsUtil.drawRect(g, 79+(x_index*x), 185+(y_index*y), 59, 59, 3, 0xffff00);
		}else{
			GraphicsUtil.drawRect(g, 414, 389, 105, 44, 3, 0xff0000);
		}
		g.setColor(0x000000);
		for(int i=0;i<Game.pm.props.length;i++){
			String price = String.valueOf(Game.pm.props[i].getPrice()); 
			String nums = String.valueOf(Game.pm.props[i].getNums()); 
			getMapXY(i);
			g.drawString(price, mapx, mapy, 20);
			g.drawString(nums, mapx, mapy+20, 20);
		}
		Game.getInstance().setFont(15, true, g);
		if(index < 9){
			String des = Game.pm.props[index].getDesc();
			String price = String.valueOf(Game.pm.props[index].getPrice()); 
			String str = des+"，价格："+price+Game.getInstance().getEngineService().getExpendAmountUnit();
			TextView.showMultiLineText(g, str, 2, 413, 243, 120, 125);
		}
		g.drawString(Game.getInstance().getEngineService().getExpendAmountUnit()+"数:"
				+Game.getInstance().getEngineService().getBalance(), 412, 349, 20);
		Game.getInstance().setDefaultFont(g);
	}
	
	private void getMapXY(int i){
		switch(i){
		case 0:
			mapx = startx;
			mapy = starty;
			break;
		case 1:
			mapx = startx + offx;
			mapy = starty;
			break;
		case 2:
			mapx = startx;
			mapy = starty + offy;
			break;
		case 3:
			mapx = startx + offx;
			mapy = starty + offy;
			break;
		case 4:
			mapx = startx;
			mapy = starty + 2*offy;
			break;
		case 5:
			mapx = startx + offx;
			mapy = starty + 2*offy;
			break;
		case 6:
			mapx = startx;
			mapy = starty + 3*offy;
			break;
		case 7:
			mapx = startx + offx;
			mapy = starty + 3*offy;
			break;
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
				PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
				pc.setText("确定要购买吗?");
				if(pc.popup()==0){
					Game.pm.buyProp(propId, 1);
				}
			}
			
		}else if(key.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			Game.getInstance().pm.sysProps();
			Game.popStack();
		}
		index = getIndex();
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
