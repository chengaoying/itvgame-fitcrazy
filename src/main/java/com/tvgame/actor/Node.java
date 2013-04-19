package com.tvgame.actor;

import javax.microedition.lcdui.Graphics;

import com.tvgame.util.GraphicsUtil;

/**
 * 地图的节点
 * @author liushiming
 */
public class Node {

    /**
     * 地图节点所在的索引
     */
    private int index;
    /**
     * 地表的类型
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
     * 绘制地表
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
        	//托盘区 (仓库)
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
     * 是否是空地
     */
    public boolean isNoneSpace() {
//        return floorType != 0&& actor==null;
        return actor==null;
    }
    /**
     * 地表的类型
     */
    public byte getFloorType() {
        return floorType;
    }
    
    public final static int READY_PROP_STAND = 99;//静止的状态
    public final static int READY_PROP_UP = 100;//即将放置的道具位于已存在的道具正上方
    public final static int READY_PROP_DOWN = 101;
    public final static int READY_PROP_LEFT = 102;
    public final static int READY_PROP_RIGHT  = 103;
    public final static int READY_PROP_LU  = 104;
    public final static int READY_PROP_RU  =  105;
    public final static int READY_PROP_LD  =  106;
    public final static int READY_PROP_RD  =  107;
    /**
     * 
     * @param nodeCol  节点列
     * @param nodeRow  节点行
     * @param col光标列
     * @param row 光标行
     * @param direction 跳动方向
     */
    public void setActorReadySynthesis(int nodeCol,int nodeRow,int col,int row){
    	int direction = -1;
    	if(nodeCol == col && nodeRow > row )//光标在正上
           direction = READY_PROP_DOWN;
    	if(nodeCol == col && nodeRow < row)//正下
    		direction = READY_PROP_UP;
    	
    	if(nodeRow == row && nodeCol > col)//正左
    		direction = READY_PROP_LEFT;
    	if(nodeRow == row && nodeCol < col)//正右
    		direction = READY_PROP_RIGHT;
    	
    	if(nodeCol > col && nodeRow > row)//左上
    		direction = READY_PROP_LU;
    	if(nodeCol  < col && nodeRow > row)//右上
    		direction = READY_PROP_RU;
    	if(nodeCol > col && nodeRow < row) //左下
    		direction = READY_PROP_LD;
    	if(nodeCol < col && nodeRow < row) //右下
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
     * 静止的状态
     */
    public void setActorStopSynthesis(){
        if(actor != null){
             actor.setAction((byte)0);
         }
     }
    /**
     * 测试用
     * @param type
     */
    public void setFloorType(byte type){
    	floorType = type;
    }
    /**
     * 更具当前块的状态更新 地表
     */
    public void bulidFloor(){
        floorType = scene.getCellFloorTypeWithPostion(index%Scene.COLUMNS, index/Scene.COLUMNS);
    }
    /**
     * 设置当前为空地
     */
    public void setNullSpace(){
    	setActor(null);
    }
}
