package com.tvgame.game;

import javax.microedition.lcdui.Graphics;

//import com.sun.j2me.global.Resource;
import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.actor.Resources;
import com.tvgame.actor.UserData;
import com.tvgame.constant.Const;
import com.tvgame.ui.UiObject;
import com.tvgame.util.GraphicsUtil;

public class Achichment implements UiObject {
	private int achi_item_index  = 0;//�ɾ��б������
  public  static final String �ɾ�����[] = {
	  "��������","С�гɾ�","�ٽ�����","����һ��",
	  "����ר��","��������","Ψ�Ҷ���"
  };
  
  public static boolean test_achi_arrived[] = {false,false,false,false,false,false,false};
  public static boolean achi_arrived[] ={false,false,false,false,false,false,false};
  public static int [] show_gold = {20,30,45,70,100,140,200};//ÿ�׶ν���������
  /*public final static int [] award_range = {5000,6500,20000,21500,35000,36500,//5
	  48000,49500,60000,61500,75000,//10
	  76500,90000//12
	  };*///�����ķ�����Χ
  public final static int [] award_range = {500,650,2000,2150,3500,3650,//5
	  4800,4950,6000,6150,7500,//10
	  7650,9000//12
	  };
  public static int achi_which = -1;
  //��ȡ��ǰ��õĳɾ�
  public static boolean[] getAchi_arrived(){
	 return  achi_arrived;
  }
  //������ҷ�����ÿ�����Ӧ�ĳɾ�
  public static void getAchByScore()  {
	 int score = UserData.getScore();
//	 System.err.println("score ="+score);
	  if(score>=award_range[0]&&score<award_range[1])//�ﵽ2500��С��4000
	  {
		  if(!achi_arrived[0])
		  {
			  achi_arrived[0]=true;
			 // UserData.addGold(8);
			  achi_which = 0;
			  Game.getInstance().push2Stack(new Achi_GameTip());
			  return;
		  }
	  }
	  else if(score>=award_range[2]&&score<award_range[3])//[4000,5500)
	  {
		  if(!achi_arrived[1])
		  {
			  achi_arrived[1]=true;
			  //UserData.addGold(16);
			  achi_which = 1;
			  Game.getInstance().push2Stack(new Achi_GameTip());
			  return;
		  }  
		  
	  }
	  else if(score>=award_range[4]&&score<award_range[5])//[5500,7000)
	  {
		  if(!achi_arrived[2]){
			  achi_arrived[2]=true;
			 // UserData.addGold(25);
			  achi_which = 2;
			  Game.getInstance().push2Stack(new Achi_GameTip());
			  return;
		  }
		  
	  }
	  else if(score>=award_range[6]&&score<award_range[7])//[7000,8500)
	  {  if(!achi_arrived[3]){
		  achi_arrived[3]=true;
		  //UserData.addGold(40);
		  achi_which = 3;
		  Game.getInstance().push2Stack(new Achi_GameTip());
		  return;
	  }
	  }
	  else if(score>=award_range[8]&&score<award_range[9])//[8500,10000)
	  {
		  if(!achi_arrived[4]){
			  achi_arrived[4]=true;
			 // UserData.addGold(60);
			  achi_which = 4;
			  Game.getInstance().push2Stack(new Achi_GameTip());
			  return;
		  }
	  }
	  else if(score>=award_range[10]&&score<award_range[11])//[10000,11500)
	  {
		  if(!achi_arrived[5]){
			  achi_arrived[5]=true;
			  //UserData.addGold(80);
			  achi_which = 5;
			  Game.getInstance().push2Stack(new Achi_GameTip());
			  return;
		  }
	  }
	  else if(score>=award_range[12])//[11500---)
	  {
		  if(!achi_arrived[6]){
		  achi_arrived[6]=true;
		 // UserData.addGold(100);
		  achi_which = 6;
		  Game.getInstance().push2Stack(new Achi_GameTip());
		  return;
		  }
	  }
  }
  

public void draw(Graphics g) {
	Game.getInstance().drawBackGround(g,"�ɾ�");
	
	int x = (Const.WIDTH_HALF>>3)+(480-�ɾ�����[0].length()*Const.FONT_W_CH)/4;
	int y= (Const.HEIGHT>>2);
	drawAchiItemBg(g,achi_item_index);
	for (int i = 0; i < �ɾ�����.length; i++) {
		com.tvgame.util.Util.draw3DString(g, �ɾ�����[i], x, y+(Const.FONT_H+10)*i, 20, 0xffffff, 0x0);
		
		if(i<�ɾ�����.length-1)
		{
		com.tvgame.util.Util.draw3DString(g,"[",x+110,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
		com.tvgame.util.Util.draw3DString(g, ""+award_range[2*i], x+130, y+(Const.FONT_H+10)*i, 20, 0xffffff, 0x0);
		com.tvgame.util.Util.draw3DString(g,"-",x+190,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
		com.tvgame.util.Util.draw3DString(g, ""+award_range[2*i+1], x+210, y+(Const.FONT_H+10)*i, 20, 0xffffff, 0x0);
		com.tvgame.util.Util.draw3DString(g,"��",x+270,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
		com.tvgame.util.Util.draw3DString(g,"]",x+290,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
		}
		else{
			com.tvgame.util.Util.draw3DString(g,"[",x+110,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
			com.tvgame.util.Util.draw3DString(g,""+award_range[12]+"+",x+130,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
			com.tvgame.util.Util.draw3DString(g,"��",x+270,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
			com.tvgame.util.Util.draw3DString(g,"]",x+290,y+(Const.FONT_H+10)*i,20,0xffffff,0x0);
		}
		if(achi_arrived[i])
		{
//			GraphicsUtil.drawImage(g, (Const.WIDTH_HALF>>2)+400,  y+(Const.FONT_H+10)*i+4, 20,	 Resources.IMG_ID_ACHI_ON);
			
			com.tvgame.util.Util.draw3DString(g,"���Ѵ�ɡ�",x+320,y+(Const.FONT_H+10)*i,20,0x00ff00,0xffffff);
		}
		else
		{
			com.tvgame.util.Util.draw3DString(g,"��δ��ɡ�",x+320,y+(Const.FONT_H+10)*i,20,0xff0000,0xffffff);
//			GraphicsUtil.drawImage(g, (Const.WIDTH_HALF>>2)+400,  y+(Const.FONT_H+10)*i+4, 20,	 Resources.IMG_ID_ACHI_OFF);
		}
	}
}

/**
 * 
 * ���Ƴɾ�ѡ�����ɫ����
 * @param g
 */
public static final void drawAchiItemBg(Graphics g,int index)
{
	for (int i = 0; i < 20; i++) {
			GraphicsUtil.drawImage(g, (Const.WIDTH_HALF>>2)+24*i, (Const.HEIGHT>>2)+(Const.FONT_H+10)*index, 20, Resources.IMG_ID_ACHI_ITEMBG);
	}
}

public void update(KeyState key) {
	// TODO Auto-generated method stub
	if(key.containsAndRemove(KeyCode.OK)){
		Game.getInstance().popStack();
	}
	else if(key.containsAndRemove(KeyCode.UP))
		achi_item_index = achi_item_index >0?--achi_item_index:6;
	else if(key.containsAndRemove(KeyCode.DOWN))
		achi_item_index = achi_item_index <6?++achi_item_index:0;
}
}
