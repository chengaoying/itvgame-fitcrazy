package com.tvgame.actor;

import javax.microedition.lcdui.Graphics;


public class Actor {

	
    //��һ������ ��ѿ������ ��ͷ�ܵ� ��ͷ��ʿ �߹���� ��ʥ��� �������()
    //���������� ʯ�� С��ʯ  ��ʯ ���� ��������
	
	
   ////////////////////ע�⣺
	//ԭ�е�����1�ĺϳ�·�������޸�Ϊ��ȫ�հ��������������1
	
	
    public static final byte TYPE_�յ� = -1;

    /**
     * ����
     */
    public static final byte TYPE_���� = Resources.SPRITE_item_01;
    /**
     * ��ѿ������
     */
    public static final byte TYPE_��ѿ������  = Resources.SPRITE_item_02;
    /**
     * ��ͷ�ܵ�
     */
    public static final byte TYPE_��ͷ�ܵ� = Resources.SPRITE_item_03;
    /**
     * ��ͷ��ʿ
     */
    public static final byte TYPE_��ͷ��ʿ  = Resources.SPRITE_item_04;
     /**
     * ��ͷ���
     */
    public static final byte TYPE_�߹����  = Resources.SPRITE_item_05;
    /**
     * ��ʥ���
     */
    public static final byte TYPE_��ʥ��� = Resources.SPRITE_item_06;
    
    /**
     * �������
     */
    public static final byte TYPE_�������  = Resources.SPRITE_item_07;
    
    public static final byte TYPE_08  = Resources.SPRITE_item_08;
    
    public static final byte TYPE_09  = Resources.SPRITE_item_09;
   
    //���������� ʯ�� С��ʯ  ��ʯ ���� ��������
    /**
     * ����
     */
    public static final byte TYPE_����  = Resources.SPRITE_item_10;
    
    /**
     * ʯ��
     */
    public static final byte TYPE_ʯ��= Resources.SPRITE_item_11;
    
    /**
    * С��ʯ
    */
    public static final byte TYPE_С��ʯ = Resources.SPRITE_item_12;
    /**
    * ��ʯ
    */
    public static final byte TYPE_��ʯ = Resources.SPRITE_item_13;
    
    /**
     * ����
     */
    public static final byte TYPE_����= Resources.SPRITE_item_14;
     /**
     * ��������
     */
    public static final byte TYPE_��������  = Resources.SPRITE_item_15;
    public static final byte TYPE_16  = Resources.SPRITE_item_16;
    //�������
    /**
     * ը��
     */
    public static final byte TYPE_ը�� = Resources.SPRITE_BOMB;
    /**
     * ħ����
     */
    public static final byte TYPE_ħ���� = Resources.SPRITE_jewel;
   /**
     * ������
     */
    public static final byte TYPE_������ = Resources.SPRITE_pig;
    /**
     * ���
     */
    public static final byte TYPE_��� = Resources.SPRITE_fire;

    /**
     * ����
     */
    private SpriteX sprite;
    /**
     * ��ǰ��Ա������
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
     * �������ͳ�ʼ��
     */
    private void initlize(byte type){
        actorType = type;
    }
    /**
     * ����
     */
    public void paint(Graphics g){
        sprite.paint(g);
    }
    /**
     * ָ��λ�õĻ���
     */
    public void paint(Graphics g,int x,int y){
       sprite.paint(g, x, y);
    }
    /**
     * ��¡
     */
    public Actor clone(){
        Actor a = new Actor();
        a.sprite = new SpriteX(sprite);
        a.actorType = actorType;
        return a;
    }
    /**
     * ����λ��
     */
    public void setPostion(int px,int py){
        sprite.setPosition(px, py);
    }

    /***
     * ���ö���ACTION
     */
    public void setAction(byte actionIndex){
        sprite.setAction(actionIndex);
    }
    /**
     * ���»���
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
     * ����AI
     * ����ƶ���������һ�飬����б���ƶ�
     * ��һ���ķ�Χ���ܵ����յ��ߵ�Ӱ��
     */
    public void dinosaurAI(){


    }
    /**
     * ������AI
     * ����ƶ�������һ��յ�
     * ��һ���ķ�Χ���ܵ����յ��ߵ�Ӱ��
     */
    public void flyDinosaurAI(){


    }
    /**
     * ����AI
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
     * һ��ʼˢ�³����ĵ���
     */
    public void refreshProp()
    {
    	
    	
    }
}
