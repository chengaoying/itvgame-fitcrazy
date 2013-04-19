package com.tvgame.actor;

import javax.microedition.lcdui.Graphics;

import com.tvgame.util.GraphicsUtil;

/**
 * ��ͼ�Ľڵ�
 * @author liushiming
 */
public class Node {

    /**
     * ��ͼ�ڵ����ڵ�����
     */
    private int index;
    /**
     * �ر������
     */
    private byte floorType;
 
    private Actor actor;

    private Scene scene;
    public Node(int index,Scene scene) {
        this.index = index;
        this.scene = scene;
    }
    public void setActor(Actor actor){
           this.actor = actor;
    }
    public byte getActorType(){
        return actor==null?-1:actor.getType();
    }
    public Actor getActor(){
        return actor;
    }
    public int getRow(){
    	return index / Scene.COLUMNS;
    }
    public int getColumn(){
    	return index % Scene.COLUMNS;
    }
    /**
     * ���Ƶر�
     */
    public void paintNode(Graphics g, int x, int y) {
        int px = (index % Scene.COLUMNS)*Scene.CELL_WIDTH + x;
        int py = (index / Scene.COLUMNS)*Scene.CELL_WIDTH + y;
//        Logger.debug("index:"+index+" px:"+px+"  py:"+py);
        if(index!=0){
//            if(floorType != 16)
            	Resources.drawRegion(g,Resources.loadImage(Resources.IMG_ID_CELL0), px, py, Scene.CELL_WIDTH, Scene.CELL_WIDTH, floorType*Scene.CELL_WIDTH, 0);
            if(actor!=null){
            	GraphicsUtil.drawImage(g, px+27, py+27, GraphicsUtil.HCENTER_VCENTER,Resources.IMG_ID_SHADOW);
                actor.paint(g, px+27, py+27);
                actor.updata();
            }
        }else{
        	//������ (�ֿ�)
        	Resources.drawRegion(g,Resources.loadImage(Resources.IMG_ID_CELL0), px, py, Scene.CELL_WIDTH, Scene.CELL_WIDTH, floorType*Scene.CELL_WIDTH, 0);
        	GraphicsUtil.drawImage(g, px+27, py+27, GraphicsUtil.HCENTER_VCENTER,Resources.IMG_ID_TUOPAN);
        	if(actor != null){
        		actor.paint(g, px+27, py+27);
        	}
        }

    }
//    public void paintActor(Graphics g, int x, int y) {
//        int px = index % Scene.COLUMNS + x;
//        int py = index / Scene.ROWS + y;
//        if(actor!=null){
//            actor.paint(g, px, py);
//        }
//    }
    /**
     * �Ƿ��ǿյ�
     */
    public boolean isNoneSpace() {
//        return floorType != 0&& actor==null;
        return actor==null;
    }
    /**
     * �ر������
     */
    public byte getFloorType() {
        return floorType;
    }
    
    public final static int READY_PROP_STAND = 99;//��ֹ��״̬
    public final static int READY_PROP_UP = 100;//�������õĵ���λ���Ѵ��ڵĵ������Ϸ�
    public final static int READY_PROP_DOWN = 101;
    public final static int READY_PROP_LEFT = 102;
    public final static int READY_PROP_RIGHT  = 103;
    public final static int READY_PROP_LU  = 104;
    public final static int READY_PROP_RU  =  105;
    public final static int READY_PROP_LD  =  106;
    public final static int READY_PROP_RD  =  107;
    /**
     * 
     * @param nodeCol  �ڵ���
     * @param nodeRow  �ڵ���
     * @param col�����
     * @param row �����
     * @param direction ��������
     */
    public void setActorReadySynthesis(int nodeCol,int nodeRow,int col,int row){
    	int direction = -1;
    	if(nodeCol == col && nodeRow > row )//���������
           direction = READY_PROP_DOWN;
    	if(nodeCol == col && nodeRow < row)//����
    		direction = READY_PROP_UP;
    	
    	if(nodeRow == row && nodeCol > col)//����
    		direction = READY_PROP_LEFT;
    	if(nodeRow == row && nodeCol < col)//����
    		direction = READY_PROP_RIGHT;
    	
    	if(nodeCol > col && nodeRow > row)//����
    		direction = READY_PROP_LU;
    	if(nodeCol  < col && nodeRow > row)//����
    		direction = READY_PROP_RU;
    	if(nodeCol > col && nodeRow < row) //����
    		direction = READY_PROP_LD;
    	if(nodeCol < col && nodeRow < row) //����
    		direction = READY_PROP_RD;
    	
    	 if(actor != null){
			switch (direction) {
			case READY_PROP_UP:
				actor.setAction((byte) 2);
				break;
			case READY_PROP_DOWN:
				actor.setAction((byte) 1);
				break;
			case READY_PROP_LEFT:
				actor.setAction((byte) 3);
				break;
			case READY_PROP_RIGHT:
				actor.setAction((byte) 4);
				break;
			case READY_PROP_LU:
				actor.setAction((byte) 5);
				break;
			case READY_PROP_RU:
				actor.setAction((byte) 6);
				break;
			case READY_PROP_LD:
				actor.setAction((byte) 7);
				break;
			case READY_PROP_RD:
				actor.setAction((byte) 8);
				break;
			}
         }
    }
    
    
    /**
     * ��ֹ��״̬
     */
    public void setActorStopSynthesis(){
        if(actor != null){
             actor.setAction((byte)0);
         }
     }
    /**
     * ������
     * @param type
     */
    public void setFloorType(byte type){
    	floorType = type;
    }
    /**
     * ���ߵ�ǰ���״̬���� �ر�
     */
    public void bulidFloor(){
        floorType = scene.getCellFloorTypeWithPostion(index%Scene.COLUMNS, index/Scene.COLUMNS);
    }
    /**
     * ���õ�ǰΪ�յ�
     */
    public void setNullSpace(){
    	setActor(null);
    }
}
