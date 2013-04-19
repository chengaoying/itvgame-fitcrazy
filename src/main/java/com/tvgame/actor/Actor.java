package com.tvgame.actor;

import javax.microedition.lcdui.Graphics;


public class Actor {

	
    //（一）种子 发芽的种子 菜头弟弟 菜头勇士 蔬果骑兵 神圣骑兵 究极骑兵()
    //（二）恐龙 石碑 小化石  大化石 宝箱 超级宝箱
	
	
   ////////////////////注意：
	//原有的线索1的合成路线现在修改为完全照搬三合重镇的线索1
	
	
    public static final byte TYPE_空地 = -1;

    /**
     * 种子
     */
    public static final byte TYPE_种子 = Resources.SPRITE_item_01;
    /**
     * 发芽的种子
     */
    public static final byte TYPE_发芽的种子  = Resources.SPRITE_item_02;
    /**
     * 菜头弟弟
     */
    public static final byte TYPE_菜头弟弟 = Resources.SPRITE_item_03;
    /**
     * 菜头勇士
     */
    public static final byte TYPE_菜头勇士  = Resources.SPRITE_item_04;
     /**
     * 菜头骑兵
     */
    public static final byte TYPE_蔬果骑兵  = Resources.SPRITE_item_05;
    /**
     * 神圣骑兵
     */
    public static final byte TYPE_神圣骑兵 = Resources.SPRITE_item_06;
    
    /**
     * 究极骑兵
     */
    public static final byte TYPE_究极骑兵  = Resources.SPRITE_item_07;
    
    public static final byte TYPE_08  = Resources.SPRITE_item_08;
    
    public static final byte TYPE_09  = Resources.SPRITE_item_09;
   
    //（二）恐龙 石碑 小化石  大化石 宝箱 超级宝箱
    /**
     * 恐龙
     */
    public static final byte TYPE_恐龙  = Resources.SPRITE_item_10;
    
    /**
     * 石碑
     */
    public static final byte TYPE_石碑= Resources.SPRITE_item_11;
    
    /**
    * 小化石
    */
    public static final byte TYPE_小化石 = Resources.SPRITE_item_12;
    /**
    * 大化石
    */
    public static final byte TYPE_大化石 = Resources.SPRITE_item_13;
    
    /**
     * 宝箱
     */
    public static final byte TYPE_宝箱= Resources.SPRITE_item_14;
     /**
     * 超级宝箱
     */
    public static final byte TYPE_超级宝箱  = Resources.SPRITE_item_15;
    public static final byte TYPE_16  = Resources.SPRITE_item_16;
    //特殊道具
    /**
     * 炸弹
     */
    public static final byte TYPE_炸弹 = Resources.SPRITE_BOMB;
    /**
     * 魔法棒
     */
    public static final byte TYPE_魔法棒 = Resources.SPRITE_jewel;
   /**
     * 飞天猪
     */
    public static final byte TYPE_飞天猪 = Resources.SPRITE_pig;
    /**
     * 火把
     */
    public static final byte TYPE_火把 = Resources.SPRITE_fire;

    /**
     * 动画
     */
    private SpriteX sprite;
    /**
     * 当前演员的类型
     */
    private byte actorType;

    private Actor(){

    }
    public Actor(byte type){
        sprite = Resources.getSpritex(type);
        actorType = type;
    }
    public byte getType(){
        return actorType;
    }
    /**
     * 根据类型初始化
     */
    private void initlize(byte type){
        actorType = type;
    }
    /**
     * 绘制
     */
    public void paint(Graphics g){
        sprite.paint(g);
    }
    /**
     * 指定位置的绘制
     */
    public void paint(Graphics g,int x,int y){
       sprite.paint(g, x, y);
    }
    /**
     * 克隆
     */
    public Actor clone(){
        Actor a = new Actor();
        a.sprite = new SpriteX(sprite);
        a.actorType = actorType;
        return a;
    }
    /**
     * 设置位置
     */
    public void setPostion(int px,int py){
        sprite.setPosition(px, py);
    }

    /***
     * 设置动画ACTION
     */
    public void setAction(byte actionIndex){
        sprite.setAction(actionIndex);
    }
    /**
     * 更新绘制
     */
    public void updata(){
        sprite.nextFrame();
    }
    //=====================================================AI==========================================
    /**
     * AI
     */
    public void ai(){
        switch(actorType){

        }
    }
    /**
     * 恐龙AI
     * 随机移动到相连的一块，不能斜着移动
     * 在一定的范围内受到引诱道具的影响
     */
    public void dinosaurAI(){


    }
    /**
     * 飞天龙AI
     * 随机移动到任意一块空地
     * 在一定的范围内受到引诱道具的影响
     */
    public void flyDinosaurAI(){


    }
    /**
     * 合体AI
     */
    public void synthesisAI(){


    }
    public int getX(){
        return sprite.getX();
    }
    public int getY(){
        return sprite.getY();  
    }
    
    /**
     * 一开始刷新出来的道具
     */
    public void refreshProp()
    {
    	
    	
    }
}
