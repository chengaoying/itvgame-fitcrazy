package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

/*
import com.cgc.jme.lib.main.CommonMain;
*/
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;
/***
 * 
 * 暂时不用。。。。。。。。。。。。。。。。。。。。。。。。。。
 * @author xiaobai
 *
 */
public class Payment implements UiObject {
	
	
	final static int price[][]={{5,1800},{10,4000}};
	public int mId;
	int width = 352;
	int height = 288;
	int x= (Const.WIDTH-width)>>1;
	int y= (Const.HEIGHT-height)>>1;
	public Payment(int id){
		mId =  id;
	}
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

		Game.getInstance().drawBoader(g, x, y,width,height);
		Util.draw3DString(g, "确定购买吗?", Const.WIDTH_HALF, y + 5,GraphicsUtil.HCENTER_TOP,
			0xffffff, 0x0);
		Util.drawString(g, "购买"+price[mId][0]+"龙币将花费"+price[mId][1]+"元", x+20, y+60, 20, 0, width-40, '\n', Const.bigFont);
	}

	public void update(KeyState key) {
		// TODO Auto-generated method stub
		if(key.containsAndRemove(KeyCode.OK)){

//			CommonMain.doPayment(mId==0?"99880050":"99880062",mId==0?CommonMain.PAYMENT_TYPE_BILL:CommonMain.PAYMENT_TYPE_SCORE_TELECOM);
			Game.getInstance().popStack();
//			Game.getInstance().showLoading();
		}
		
	}

}
