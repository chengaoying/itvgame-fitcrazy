package com.tvgame.actor;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.util.RandomValue;

import com.tvgame.constant.Const;
import com.tvgame.game.Achichment;
import com.tvgame.game.Game;
import com.tvgame.game.Shake;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;

public class Scene {
	/**
	 * 地图总列数
	 */
	public final static int COLUMNS = 6;
	/**
	 * 地图总行数
	 */
	public final static int ROWS = 6;
	/**
	 * 单元块的宽度
	 */
	public final static int CELL_WIDTH = 55;

	private Node nodes[];		//当前nodes
	
	private Node nodes2[];		//上一步的nodes
	
	/**
	 * 当前光标所在的列
	 */
	private int currentCol;
	
	/**
	 * 上一步光标所在的列
	 */
	private int currentCol2;
	
	/**
	 * 当前光标所在的行
	 */
	private int currentRow;
	
	/**
	 * 上一步光标所在的行
	 */
	private int currentRow2;
	
	/**
	 * 场景所在的位置X
	 */
	private int x;
	/**
	 * 场景所在的位置Y
	 */
	private int y;

	/**
	 * 效果动画的列表
	 */
	private Vector cjList = new Vector();
	public void addCJ(SpriteX sprite){
		cjList.addElement(sprite);	
	}
	
	/**
	 * 是否可以后退一步
	 */
	public boolean isCanBackStep;
	
	/**
	 * 
	 * @param type
	 * @param isBigAnimation 是否是大动画
	 */
	public void addCJ(byte type,boolean isBigAnimation){
		if(!isBigAnimation)
		{
			int px = currentCol*55+27;
			int py = currentRow*55+27;
			SpriteX lock = Resources.getSpritex(type);
			lock.setPosition(px+x, py+y);
			lock.setAction((byte)0);
			addCJ(lock);
		}else
		{
			//大动画
			int px = Const.WIDTH_HALF;
			int py = Const.HEIGHT_HALF;
			SpriteX bigSPX = Resources.getSpritex(type);
			bigSPX.setPosition(px, py);
			bigSPX.setAction((byte)0);
			addCJ(bigSPX);
		}
			
	}
	public void drawCJ(Graphics g){
		SpriteX sprite;
		for(int index = 0 ;index<cjList.size();index++){
			sprite =(SpriteX)cjList.elementAt(index);
			sprite.paint(g);
			if(sprite.getFrame() == sprite.frameCount-1){
				cjList.removeElement(sprite);
				continue;
			}
			sprite.nextFrame();
		}
	}
	public Scene() {
		initlize();
	}

	public Node[] getNodes(){
		return nodes;
	}
	
	public Node[] getNodes2(){
		return nodes2;
	}
	
	
	public void initlize() {		
		
		if(UserData.isB_show_teach())
			Game.getInstance().openTeach();
		
		nodes = new Node[COLUMNS * ROWS];
		nodes2 = new Node[COLUMNS * ROWS];
		Resources.loadImage(Resources.IMG_ID_CELL0);
		Resources.loadImage(Resources.IMG_ID_SELECTED);
		Resources.loadImage(Resources.IMG_ID_BUTTON_01);
//		Resources.loadImage(Resources.IMG_ID_BUTTON_TEXT);
		Resources.loadImage(Resources.IMG_ID_BG);	
		Resources.loadImage(Resources.IMG_ID_TUOPAN);
		if(Game.isNewGame){
			System.out.println("new game");
			newScene();
		}else{
			System.out.println("continue game");
			load();
		}
		isCanBackStep = false;
		clearFixData();
	}

	/**
	 * 显示锁
	 */
	public void showLock(){
		addCJ(Resources.SPRITE_daoju_lock,false);
	}
	/**
	 * 只要合体了就播放此动画
	 */
	public void showBombCJ(){
		addCJ(Resources.SPRITE_explode,false);
	}
	
	/**
	 * 显示刷新的动画
	 */
	public void showRefresh_Ani(int x,int y)
	{
		SpriteX ani_propfresh = Resources.getSpritex(Resources.SPRITE_ani_propflash);
		ani_propfresh.setPosition(x, y);
		ani_propfresh.setAction((byte)0);
		addCJ(ani_propfresh);
	}
	
	//大动画之爆炸
//	public void showBIG_BOMB()
//	{
//		addCJ(Resources.SPRITE_big_ani_bomb,true);
//	}
	
//	//大动画魔法球
//	public void showBIG_MOFAQIU()
//	{
//		addCJ(Resources.SPRITE_big_ani_jewel,true);
//	}
	
	
	public void bulidFloor(){
		for (int index = 0; index < nodes.length; index++) {
			nodes[index].bulidFloor();
		}
	}
	public int getWidth() {
		return COLUMNS * CELL_WIDTH;
	}

	public int getHeight() {
		return ROWS * CELL_WIDTH;
	}

	/**
	 * 载入数据
	 */
	public void load() {

		//初始化仓库
		byte data[]= UserData.loadSceneData();
		for (int index = 0; index < nodes.length; index++) {
			if(nodes[index]==null){
				nodes[index] = new Node(index, this);
				nodes2[index] = new Node(index, this);
			}
			if(data[index]>0){
				nodes[index].setActor(createNewActor(data[index]));
				nodes2[index].setActor(createNewActor(data[index]));
			}else{
				nodes[index].setActor(null);
				nodes2[index].setActor(null);
			}
		}
		//nodes2 = nodes;
		//nodesToNodes(nodes2, nodes);
		for (int index = 0; index < nodes.length; index++) {
			nodes[index].bulidFloor();
		}
		setCurActor(UserData.getCurActorType());
//		currentCol = UserData.getcurCol();
//		currentRow = UserData.getCurRow();
		setColRow(UserData.getcurCol(),UserData.getCurRow());
	}

	public boolean isType(int column, int row,byte type) {
		if (column < 0 || column > COLUMNS - 1 || row < 0 || row > ROWS - 1) {
			return false;
		}
		return isType(column + row * COLUMNS,type);
	}
	public boolean isType(int index,byte type) {
		return nodes[index].getActorType()==type;
	}
	public Node getNode(int col,int row){
		if (col < 0 || row > COLUMNS - 1 || row < 0 || row > ROWS - 1) {
			return null;
		}
		return nodes[getIndex(col, row)];
	}
	/**
	 * 是否空地
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public boolean isNoneSpace(int column, int row) {
		if(column==0&&row==0){
			return false;
		}
		if (column < 0 || column > COLUMNS - 1 || row < 0 || row > ROWS - 1) {
			return false;
		}
		return isNoneSpace(column + row * COLUMNS);
	}
	/**
	 * 恐龙特殊处理
	 * @param column
	 * @param row
	 * @return
	 */
	public boolean isNoneSpaceForDinosau(int column, int row) {
		if(column==0&&row==0){
			return false;
		}
		if (column < 0 || column > COLUMNS - 1 || row < 0 || row > ROWS - 1) {
			return false;
		}
	
		return isNoneSpace(column + row * COLUMNS)||getNodeActorType(column + row * COLUMNS)==9;
		}
	/**
	 * 是否是空地
	 */
	public boolean isNoneSpace(int index) {
		return nodes[index].isNoneSpace();
	}

	/**
	 * 当前索引是否为空地
	 */
	public boolean isNoneSpaceCurrentNode() {
		return isNoneSpace(currentCol, currentRow);
	}

	public byte getCurrentNodeActorType() {
		return getNodeActorType(getCurIndex());
	}

	/**
	 * 根据所处的位置判断地面类型 非0就为空地，如果为0就时即为草地
	 * 
	 * @param l
	 * @param h
	 * @return
	 */
	final static int LEFT = 1;
	final static int UP = 1 << 1;
	final static int RIGHT = 1 << 2;
	final static int DOWN = 1 << 3;
	final static int UP_RIGHT = UP | RIGHT;
	final static int UP_LEFT = UP | LEFT;
	final static int UP_DOWN = UP | DOWN;
	final static int LEFT_RIGHT = LEFT | RIGHT;
	final static int LEFT_DOWN = LEFT | DOWN;
	final static int RIGHT_DOWN = RIGHT | DOWN;
	final static int UP_RIGHT_DOWN = UP | RIGHT | DOWN;
	final static int UP_DOWN_LEFT = UP | DOWN | LEFT;
	final static int UP_RIGHT_LEFT = UP | RIGHT | LEFT;
	final static int RIGHT_DOWN_LEFT = RIGHT | DOWN | LEFT;
	final static int UP_RIGHT_DOWN_LEFT = UP | RIGHT | DOWN | LEFT;

	public byte getCellFloorTypeWithPostion(int column, int row) {
		if (!isNoneSpaceForDinosau(column, row)) {
			return 0;
		}
		int temp = 0;
		temp = isNoneSpaceForDinosau(column, row - 1) ? temp | UP : temp;
		temp = isNoneSpaceForDinosau(column, row + 1) ? temp | DOWN : temp;
		temp = isNoneSpaceForDinosau(column - 1, row) ? temp | LEFT : temp;
		temp = isNoneSpaceForDinosau(column + 1, row) ? temp | RIGHT : temp;
		switch (temp) {
		case UP:
			return 5;
		case RIGHT:
			return 11;
		case DOWN:
			return 9;
		case LEFT:
			return 10;
		case UP_RIGHT:
			return 8;
		case UP_LEFT:
			return 6;
		case UP_DOWN:
			return 3;
		case LEFT_RIGHT:
			return 7;
		case LEFT_DOWN:
			return 1;
		case RIGHT_DOWN:
			return 4;
		case UP_RIGHT_DOWN:
			return 14;
		case UP_DOWN_LEFT:
			return 15;
		case UP_RIGHT_LEFT:
			return 12;
		case RIGHT_DOWN_LEFT:
			return 13;
		case UP_RIGHT_DOWN_LEFT:
			return 16;
		default:
			return 2;
		}
	}

	/**
	 * 新地图
	 */
	public void newScene() {
		//初始化仓库
		byte data[]= UserData.getRanSceneData();
//		for (int i = 0; i < data.length; i++) {
//			Logger.debug("data ="+data[i]);
//		}
		for (int index = 0; index < nodes.length; index++) {
			if(nodes[index]==null){
				nodes[index] = new Node(index, this);
				nodes2[index] = new Node(index, this);
			}
			if(data[index]>0){
				nodes[index].setActor(createNewActor(data[index]));
				nodes2[index].setActor(createNewActor(data[index]));
			}else{
				nodes[index].setActor(null);
				nodes2[index].setActor(null);
			}
		}
		//nodes2 = nodes;
		//nodesToNodes(nodes2, nodes);
		for (int index = 0; index < nodes.length; index++) {
			nodes[index].bulidFloor();
		}
		createNewActor(false);
		curActor2 = curActor;
		currentCol = 0;
		currentCol2 = 0;
		currentRow = 0;
		currentRow2 = 0;
	}

	/**
	 * 地图层的绘制
	 */
	public void paint(Graphics g) {  
		drawBorder(g);
		for (int index = 0; index < nodes.length; index++) {
			nodes[index].paintNode(g, x+Shake.shakeX, y+Shake.shakeY);
		}
		drawTopBar(g,x+Shake.shakeX,y+Shake.shakeY);
		drawFitFormula(g,x+Shake.shakeX,y+Shake.shakeY);
		paintFocus(g, x+Shake.shakeX, y+Shake.shakeY);
		Shake.shake_draw();
		drawCJ(g);
		
	    Achichment.getAchByScore();
	}
	public int getCol(){
		return currentCol;
	}
	public int getRow(){
		return currentRow;
	}
	public void setColRow(int col ,int row){
		currentCol= col;
		currentRow =row;
		if(currentCol!=0&&currentRow!=0){
			ckeckSynthesisImp();
		}	
	}
	public void setPostion(int px, int py) {
		x = px;
		y = py;
	}

	/***
	 * 场景更新
	 */
	private int dialog_index = 0;

	public void updata(KeyState key) {
		{
			// 光标控制
			if (key.containsAndRemove(KeyCode.UP)) {
				currentRow = currentRow > 0 ? (currentRow - 1) : ROWS - 1;
				ckeckSynthesisImp();
			} else if (key.containsAndRemove(KeyCode.RIGHT)) {
				currentCol = currentCol < COLUMNS - 1 ? (currentCol + 1) : 0;
				ckeckSynthesisImp();
			} else if (key.containsAndRemove(KeyCode.DOWN)) {
				currentRow = currentRow < ROWS - 1 ? (currentRow + 1) : 0;
				ckeckSynthesisImp();
			} else if (key.containsAndRemove(KeyCode.LEFT)) {
				currentCol = currentCol > 0 ? (currentCol - 1) : COLUMNS - 1;
				ckeckSynthesisImp();
			} else if (key.containsAndRemove(KeyCode.OK)) {
				doKeyFire();
			} else if (key.containsAndRemove(KeyCode.NUM0)|| key.containsAndRemove(KeyCode.BACK)) {
				timespace = time;  
				Game.getInstance().pm.sysProps();
				Game.getInstance().openSysMenu();
			} 
			// 付费道具 快捷键
			else if (key.containsAndRemove(KeyCode.NUM1)) {
				int nums = Game.getInstance().pm.getPropNumsById(127);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_炸弹){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_炸弹);
						//Game.getInstance().showTip("兑换成功！");
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(127);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
					
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM2)) {
				int nums = Game.getInstance().pm.getPropNumsById(128);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_魔法棒){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_魔法棒);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(128);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM3)) {
				int nums = Game.getInstance().pm.getPropNumsById(129);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_火把){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_火把);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(129);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM4)) {
				int nums = Game.getInstance().pm.getPropNumsById(130);
				if (nums > 0) {
					if(UserData.getStep()<150){
						//UserData.addStep(150);
						UserData.setStep(150);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(130);
					}else{
						Game.getInstance().showTip("步数已达到上限值");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM5)) {
				int nums = Game.getInstance().pm.getPropNumsById(131);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_发芽的种子){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_发芽的种子);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(131);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM6)) {
				int nums = Game.getInstance().pm.getPropNumsById(132);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_菜头弟弟){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_菜头弟弟);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(132);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM7)) {
				int nums = Game.getInstance().pm.getPropNumsById(133);
				if (nums > 0) {
					if(curActor.getType() != Actor.TYPE_菜头勇士){
						Game.getInstance().getScene().setCurActor(Actor.TYPE_菜头勇士);
						ckeckSynthesisImp();
						Game.getInstance().pm.reducePropNum(133);
					}else{
						Game.getInstance().showTip("使用失败，右上角框中已有该道具");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM8)) {
				int nums = Game.getInstance().pm.getPropNumsById(134);
				if (nums > 0) {
					//nodes = nodes2;
					if(isCanBackStep == true){
						nodesToNodes(nodes, nodes2);
						curActor = curActor2;
						UserData.setScore(UserData.mScore2);
						if(UserData.getStep()<150){
							UserData.setStep(UserData.getStep()+1);
						}
						ckeckSynthesisImp();
						isCanBackStep = false;
						Game.getInstance().pm.reducePropNum(134);
					}else{
						Game.getInstance().showTip("只能后退一步");
					}
				} else {
					entryShop();
				}
			} else if (key.containsAndRemove(KeyCode.NUM9)) {
				entryShop();
			}
		}
		rewardStep();
	}

	private void entryShop() {
		//Game.getInstance().showTip("道具不够，请到商城购买");
		Game.getInstance().pm.sysProps();
		Game.getInstance().openShop();
	}

	/**
	 * 检查是否可以合体（如果可以合体的话）
	 */
	static Vector nodelist = new Vector();
	static Vector totalNodeList = new Vector();

	public Node getNode(int index) {
		return nodes[index];
	}
	//合体以后的道具类型
	private byte synthesisType;
	/**
	 * 按键控制后检查合体
	 */
	public void ckeckSynthesisImp() {
		if(curActor.getType()!=Actor.TYPE_魔法棒){
			synthesisType = _ckeckSynthesis(currentCol,currentRow,curActor.getType(),true);
		}else{
			byte type;
			for(int i =0 ;i<All_Synthesis_Item_Type.length;i++){
				type = All_Synthesis_Item_Type[i];
				synthesisType = _ckeckSynthesis(currentCol, currentRow, type, true);
				if(totalNodeList.size()>0){
					break;
				}
			}
		}
		checkFixData();
	}
	public byte _ckeckSynthesis(int column, int row, byte actorType,boolean flag) {
		
		Node node;
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorStopSynthesis();// 停止动画
		}
		totalNodeList.removeAllElements();
		if (column == 0 && row == 0) {
			//仓库区不检测
			return -1;
		}
		if(flag&&!getNode(getIndex(column, row)).isNoneSpace()){
			//非空地不检测
			return -1;
		}
		if(actorType>Actor.TYPE_超级宝箱){
			//非合体道具不参加检测
			return -1;
		}
        if(actorType==Actor.TYPE_09){
			//发光城堡不参加合体
			return -1;
		}
		byte type = ckeckSynthesis(column, row,actorType);		
		System.out.println("check----- "+"列"+column +"行"+row+"类型"+actorType);
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorReadySynthesis(node.getColumn(),node.getRow(),column,row);// 准备合体现实合体动画
//			return type;
		}
		return type;
	}
	/**
	 * 合体检测
	 */
//	public byte _ckeckSynthesis() {
//		Node node;
//		for (int index = 0; index < totalNodeList.size(); index++) {
//			node = (Node) totalNodeList.elementAt(index);
//			node.setActorStopSynthesis();// 停止动画
//		}
//		totalNodeList.removeAllElements();
//		if (currentRow == 0 && currentCol == 0) {
//			//仓库区不检测
//			return -1;
//		}
//		if(!getCurNode().isNoneSpace()){
//			//非空地不检测
//			return -1;
//		}
//		if(curActor.getType()>10){
//			//非合体道具不参加检测
//			return -1;
//		}
//		byte type = ckeckSynthesis(curActor.getType());		
//		for (int index = 0; index < totalNodeList.size(); index++) {
//			node = (Node) totalNodeList.elementAt(index);
//			node.setActorReadySynthesis();// 准备合体现实合体动画
////			return type;
//		}
//		return type;
//	}

	public byte ckeckSynthesis(int column, int row,byte actorType) {
		nodelist.removeAllElements();
		ckeckSynthesis(column, row, actorType,false);
		if(actorType == Actor.TYPE_09){
			if (nodelist.size() >2) {
				copyNodes2TotalNodes(nodelist);
				return actorType;
			}
		}else if(actorType == Actor.TYPE_恐龙){
			if (nodelist.size() >0) {
				copyNodes2TotalNodes(nodelist);
			}
			return actorType;
		}else{
			if (nodelist.size() >2) {
				copyNodes2TotalNodes(nodelist);
				return ckeckSynthesis(column,row,(byte) (actorType + 1));
			}
		}
		return actorType;
	}

	public void ckeckSynthesis(int column, int row, byte actorType,boolean flag) {
		if (column < 0 || column > Scene.COLUMNS - 1 || row < 0
				|| row > Scene.ROWS - 1) {
			return;
		}
		// 仓库不参加遍历
		if (column == 0 && row == 0) {
			return;
		}
		int index = getIndex(column, row);

		if (flag &&getNodeActorType(index) != actorType) {
			return;
		}
		if (nodelist.contains(getNode(index))) {

			return;
		}
		nodelist.addElement(getNode(index));
		// 左边
		ckeckSynthesis(column - 1, row, actorType,true);
		// 右边
		ckeckSynthesis(column + 1, row, actorType,true);
		// 下边
		ckeckSynthesis(column, row + 1, actorType,true);
		// 上边
		ckeckSynthesis(column, row - 1, actorType,true);

	}

	private void copyNodes2TotalNodes(Vector v) {
		for (int i = 0; i < v.size(); i++) {
			totalNodeList.addElement(v.elementAt(i));
		}
	}
	public int getCurIndex(){
		return getIndex(currentCol, currentRow);
	}
	public int getIndex(int column, int row) {
		return column + row * COLUMNS;
	}

	public byte getNodeActorType(int index) {
		return nodes[index].getActorType();
	}
	public Node getCurNode(){
		return nodes[getCurIndex()];
	}
	
	private static final int type_null = -1;
	private static final int type_normal = 0;
	private static final int type_fit = 1;
	private static final int type_dinosaur = 2;
	/**
	 * 
	 * @param actor
	 * @param type 事件类型  0:正常摆放, 1:合体
	 */
	public void setCurNodeActor(Actor actor, int type){
		if(type == type_normal){
			nodesToNodes(nodes2, nodes);
			curActor2 = actor;
		}
		getCurNode().setActor(actor);
		getCurNode().setFloorType((byte)0);// 非空地
		bulidFloor();
	}
	/**
	 * 检查四周是否有空地
	 * @param col
	 * @param row
	 * @return
	 */
	public boolean hasNoneSpaceWithAround(int col,int row){
		return 	(isNoneSpace(col, row - 1)||
				   isNoneSpace(col, row + 1)||
				   isNoneSpace(col - 1, row)||
				   isNoneSpace(col + 1, row));
	}
	public boolean hasTypeWithAround(int col,int row,byte type){
		return 	(isType(col, row - 1,type)||
				 isType(col, row + 1,type)||
				 isType(col - 1, row,type)||
				 isType(col + 1, row,type));
	}
	/**
	 * 检测生成道具的合体
	 */
	public void checkItem(int _col,int _row,byte type){
		byte newtype = _ckeckSynthesis(_col,_row,type,false);
		if(totalNodeList.size()>0){
			//合体
			Node node;
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				node.setNullSpace();
			}
			getNode(getIndex(_col, _row)).setActor(createNewActor(newtype));
			getNode(getIndex(_col, _row)).bulidFloor();
			addScoreWithFit();
		}
	}
	
	public void checkKonglong(int _col,int _row){
		System.out.println("检查恐龙 col ="+_col+"row ="+_row);
		if(!hasNoneSpaceWithAround(_col, _row)&&!hasTypeWithAround(_col, _row,Actor.TYPE_恐龙)){
			if(!(_col==0&&_row==0))
				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_石碑));
			checkItem(_col, _row,Actor.TYPE_石碑);
			
//			try {
//				Game.getInstance().musicMasterKL.player.setMediaTime(0);
//				Game.getInstance().musicMasterKL.player.start();
//			} catch (IllegalStateException e) {
//				e.printStackTrace();
//			} catch (MediaException e) {
//				e.printStackTrace();
//			}
			System.out.println("aaaaaaaaaaaa");
			return;
		}
		_ckeckSynthesis(_col,_row,Actor.TYPE_恐龙,false);
		if(totalNodeList.size()>0){
			//检查是否可以移动
			Node node;
			int column,row;
			//检查
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				column = node.getColumn();
				row  = node.getRow();
				if(hasNoneSpaceWithAround(column, row ))
				{
					return;
				}
			}
//			if(totalNodeList.size()>2){
//				for (int index = 0; index < totalNodeList.size(); index++) {
//					node = (Node) totalNodeList.elementAt(index);
//					node.setNullSpace();
//				}
//				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_小化石));
//				//检查小化石是否可以合成
//				checkItem(_col, _row,Actor.TYPE_小化石);
//			}else{
				for (int index = 0; index < totalNodeList.size(); index++) {
					node = (Node) totalNodeList.elementAt(index);
					node.setActor(createNewActor(Actor.TYPE_石碑));
//					try {
//						Game.getInstance().musicMasterKL.player.setMediaTime(0);
//						Game.getInstance().musicMasterKL.player.start();
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (MediaException e) {
//						e.printStackTrace();
//					}
					System.out.println("bbbbbbbbbbbbb");
				}
//				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_石碑));
				//检查石碑是过可以合成
				checkItem(_col, _row,Actor.TYPE_石碑);
				System.out.println("ccccccccccccccccc");
				
//			}
		}
	}
	/**
	 * 确认键
	 */
	public void doKeyFire() {
		if (currentRow == 0 && currentCol == 0) {
			// 托盘区的操作
			actorSaveToStore();
			isCanBackStep = true;
		} else {
			// 合体类道具
			// 炸弹
			// 火把
			// 恐龙
			// 猪
			// 魔法棒子
			if(UserData.getStep()<1){
				//Game.getInstance().openMall();
				Game.getInstance().showTip("步数已用完,你可以购买步数或等30秒系统自动赠送");
				return ;
			}
			boolean bOperate = false;
			byte currentActorType = curActor.getType();
			Logger.debug("currentActorType:"+currentActorType);		
			switch (getCurrentNodeActorType()) {
			case Actor.TYPE_空地:
				if(currentActorType != Actor.TYPE_炸弹/*&&currentActorType != Actor.TYPE_火把*/){
					switch(currentActorType){
						case Actor.TYPE_火把:
							doHuoBa_Logic();
							break;
						case Actor.TYPE_魔法棒:
							doMofa_Logic();
//							//大动画
//							showBIG_MOFAQIU();
							break;
						case Actor.TYPE_飞天猪:
							setCurNodeActor(curActor, type_normal);
							break;
						case Actor.TYPE_恐龙:
							do_Logic();
							break;
						default:
							if(totalNodeList.size()>0){
								//合体
								nodesToNodes(nodes2, nodes);
								Node node;
								for (int index = 0; index < totalNodeList.size(); index++) {
									node = (Node) totalNodeList.elementAt(index);
									curActor2 = node.getActor();
									node.setNullSpace();
								}
								setCurNodeActor(createNewActor(synthesisType), type_fit);
								UserData.updateAchi(synthesisType);
								UserData.printAchi();
								addScoreWithFit();
							}else{
								//正常放置
								setCurNodeActor(curActor, type_normal);
								//判断四个方向是否有恐龙
//								int _column = getCurNode().getColumn();
//								int _row  = getCurNode().getRow();
//								if(_row > 0 &&getNode(getIndex(_column, _row - 1)).getActorType()==Actor.TYPE_恐龙){
//									checkKonglong(_column, _row - 1);
//								}
//								if(_row < ROWS-1&&getNode(getIndex(_column, _row + 1)).getActorType()==Actor.TYPE_恐龙){
//									checkKonglong(_column, _row +1);
//								}
//								if(_column>0&&getNode(getIndex(_column-1, _row )).getActorType()==Actor.TYPE_恐龙){
//									checkKonglong(_column-1, _row);
//								}
//								if(_column <COLUMNS-1&&getNode(getIndex(_column+1, _row )).getActorType()==Actor.TYPE_恐龙){
//									checkKonglong(_column+1, _row);
//								}
							}
							check恐龙(getCurNode());
						break;
					}
					createNewActor();
					bOperate = true;
				}
				isCanBackStep = true;
				break;
			case Actor.TYPE_超级宝箱:
				getCurNode().setNullSpace();
				bOperate = true;
				isCanBackStep = true;
				break;
			case Actor.TYPE_宝箱:
				getCurNode().setNullSpace();
				bOperate = true;
				isCanBackStep = true;
				break;
			case Actor.TYPE_飞天猪:
				switch(currentActorType){
				case Actor.TYPE_火把:
					getCurNode().setNullSpace(); //要生成烤猪
					createNewActor();
					bOperate = true;
					isCanBackStep = true;
					//播放猪叫
//					try {
//						Game.getInstance().musicMasterPIG.player.setMediaTime(0);
//						Game.getInstance().musicMasterPIG.player.start();
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (MediaException e) {
//						e.printStackTrace();
//					}
					break;
				}
				break;
			case Actor.TYPE_恐龙:
			case Actor.TYPE_发芽的种子:
			case Actor.TYPE_小化石:
			case Actor.TYPE_大化石:
			case Actor.TYPE_石碑:
			case Actor.TYPE_神圣骑兵:
			case Actor.TYPE_种子:	
			case Actor.TYPE_究极骑兵:
			case Actor.TYPE_菜头勇士:
			case Actor.TYPE_菜头弟弟:
			case Actor.TYPE_蔬果骑兵:
			case Actor.TYPE_08:
			case Actor.TYPE_09:
			case Actor.TYPE_16:
				switch(currentActorType){
				case Actor.TYPE_炸弹:
					getCurNode().setNullSpace();
					createNewActor();
					addScoreWithBomb();
					Shake.shake_start(Shake.SHAKE_TYPE_NORMAL_ATTACK,1, true);
					showBombCJ();
					//大动画
//					showBIG_BOMB();
					bOperate = true;
					isCanBackStep = true;
					break;
				}
				break;
			}
			if(bOperate){
				恐龙_Move();
				飞天猪_Fly();
				addScoreWithPut();
				if(isGameOver()){
					Game.getInstance().openGameEnd();
				}
			}else{
				//改变合成公式
				setFormulaType(getCurrentNodeActorType());
				showLock();
			}
		}
		bulidFloor();
	}
	public Vector konglongList = new Vector();
	//飞天猪使用
	public Vector kongdiList = new Vector();
	/**
	 * 恐龙移动
	 */
	public void 恐龙_Move(){
		konglongList.removeAllElements();
//		kongdiList.removeAllElements();
		Node node;
		for (int index = 1; index < nodes.length; index++) {
			node = nodes[index];
			if(node.getActorType()== Actor.TYPE_恐龙&&node!=getCurNode()){
				node.getActor().setAction((byte)0);
				if(hasNoneSpaceWithAround(node.getColumn(), node.getRow())){
					konglongList.addElement(node);
				}
			}
		}
		if(konglongList.size() ==0){
			return ;
		}
		int ran0 = Util.getRandom(0, konglongList.size()-1);
		node = (Node)konglongList.elementAt(ran0);
		getAroundNoneSpace(node,konglongList);
		if(konglongList.size() ==0){
			return ;
		}
		ran0 = Util.getRandom(0, konglongList.size()-1);
		Node node_kd = (Node)konglongList.elementAt(ran0);
		node_kd.setActor(node.getActor());
		node_kd.bulidFloor();
		node.setNullSpace();
		
	}
	/***
	 * 飞天猪飞行
	 */
	public void 飞天猪_Fly(){
		konglongList.removeAllElements();
		kongdiList.removeAllElements();
		Node node;
		for (int index = 1; index < nodes.length; index++) {
			node = nodes[index];
			if(node.getActorType()== Actor.TYPE_飞天猪&&node!=getCurNode()){
				konglongList.addElement(node);
			}else if(node.isNoneSpace()&&node!=getCurNode()){
				kongdiList.addElement(node);
			}
		}
		if(konglongList.size() ==0||kongdiList.size()==0){
			return ;
		}
		int ran_pig = Util.getRandom(0, konglongList.size()-1);
		int ran_none = Util.getRandom(0, kongdiList.size()-1);
		node = (Node)konglongList.elementAt(ran_pig);
		Node node_none = (Node)kongdiList.elementAt(ran_none);
		node_none.setActor(node.getActor());
		node_none.bulidFloor();
		node.setNullSpace();
		check恐龙(node_none);
		
	}
	//获取四周的空地
	public void getAroundNoneSpace(Node node ,Vector v){
		v.removeAllElements();
		int col = node.getColumn();
		int row = node.getRow();
		if(isNoneSpace(col, row-1)){
			v.addElement(getNode(col, row-1));
		}
		if(isNoneSpace(col, row+1)){
			v.addElement(getNode(col, row+1));	
		}
		if(isNoneSpace(col+1, row)){
			v.addElement(getNode(col+1, row));
		}
		if(isNoneSpace(col-1, row)){
			v.addElement(getNode(col-1, row));
		}
	}
	
	/**
	 *火把放在空地上
	 */
	public void doHuoBa_Logic()
	{
		setCurNodeActor(createNewActor(Actor.TYPE_16),type_normal);
		Logger.debug("火把变成岩石");
		check恐龙(getCurNode());
	}
	
	/**
	 *魔法珠逻辑
	 */
	public void doMofa_Logic() {
		if(totalNodeList.size()>0){
			//合体
			Node node;
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				node.setNullSpace();
			}
			setCurNodeActor(createNewActor(synthesisType), type_fit);
		
			addScoreWithFit();
		}else{
			setCurNodeActor(createNewActor(Actor.TYPE_16), type_normal);
			
			Logger.debug("魔法棒子变成岩石");
		}	
		check恐龙(getCurNode());
	}
	public void check恐龙(Node node){
		//判断四个方向是否有恐龙
		int _column = node.getColumn();
		int _row  = node.getRow();
		if(_row > 0 &&getNode(getIndex(_column, _row - 1)).getActorType()==Actor.TYPE_恐龙){
			checkKonglong(_column, _row - 1);
		}
		if(_row < ROWS-1&&getNode(getIndex(_column, _row + 1)).getActorType()==Actor.TYPE_恐龙){
			checkKonglong(_column, _row +1);
		}
		if(_column>0&&getNode(getIndex(_column-1, _row )).getActorType()==Actor.TYPE_恐龙){
			checkKonglong(_column-1, _row);
		}
		if(_column <COLUMNS-1&&getNode(getIndex(_column+1, _row )).getActorType()==Actor.TYPE_恐龙){
			checkKonglong(_column+1, _row);
		}
	}
	/***
	 * 恐龙放置逻辑
	 */
	public void do_Logic() {
		if(hasNoneSpaceWithAround(currentCol, currentRow)&&!hasTypeWithAround(currentCol, currentRow,Actor.TYPE_恐龙)){
			setCurNodeActor(curActor,type_normal);
//			createNewActor();								
			return ;
		}
		if(totalNodeList.size()>0){
			//检查是否可以移动
			Node node;
			int column,row;
			//检查
			setCurNodeActor(curActor,type_dinosaur);
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				column = node.getColumn();
				row  = node.getRow();
				if(hasNoneSpaceWithAround(column, row ))
				{
					setCurNodeActor(curActor,type_dinosaur);
//					createNewActor();
					return ;
				}
			}
			setCurNodeActor(null,type_null);//测试
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				node.setActor(createNewActor(Actor.TYPE_石碑));
			}
//				setCurNodeActor(createNewActor(Actor.TYPE_石碑));
			checkItem(currentCol, currentRow,Actor.TYPE_石碑);
			
		}else{
			//正常放置
			setCurNodeActor(curActor,type_dinosaur);
		}
	}
	/**
	 * 获取四周的单元块
	 * @param node
	 * @return
	 */
	public Node[] getAroundNode(Node node){
		Node _node[]= new Node[4];
		int col = node.getColumn();
		int row = node.getRow();
		_node[0]= getNode(col, row-1);
		_node[1]= getNode(col, row+1);	
		_node[2]= getNode(col-1, row);	
		_node[3]= getNode(col+1, row);	
		return _node;
	}
	/**
	 * 火把
	 */

	/**
	 * 暂存到仓库，如果仓库已经存在的话，把以前的替换出来
	 */
	private void actorSaveToStore() {
		//nodes2 = nodes;
		nodesToNodes(nodes2, nodes);
		if (nodes[0].getActorType() == -1) {
			nodes[0].setActor(curActor);
			curActor2 = curActor;
			createNewActor(false);
		} else {
			Actor temp = nodes[0].getActor();
			nodes[0].setActor(curActor);
			curActor2 = curActor;
			curActor = temp;
		}
	}
	
	/**
	 * 将ns2的所有节点赋给ns(不能用ns = ns2)
	 * @param ns
	 * @param ns2
	 */
	private void nodesToNodes(Node[] ns, Node[] ns2){
		for(int i=0;i<ns2.length;i++){
			ns[i].setActor(ns2[i].getActor()); 
			ns[i].setFloorType(ns2[i].getFloorType());
			ns[i].setActorStopSynthesis();
		}
	}

	/**
	 * 绘制焦点
	 */
	private void paintFocus(Graphics g, int x, int y) {
		int dx = x + currentCol * CELL_WIDTH-7;
		int dy = y + currentRow * CELL_WIDTH-7;
		GraphicsUtil.drawImage(g, dx, dy, GraphicsUtil.LEFT_TOP, Resources.IMG_ID_SELECTED);
	}
//	/**
//	 * 绘制底部的提示 和7键进入商店 9键进入商城
//	 * 
//	 */
//	private void drawBottomButton(Graphics g,int x,int y){
//		int py = y + getHeight() + 30;
//		int px = x + getWidth() - 305;  
////		drawButton(g,px,py,0);
//		drawButton(g,px+160,py,1);
//	}
//	/**
//	 * 绘制按键
//	 * @param g
//	 * @param x
//	 * @param y
//	 * @param index
//	 */
//	public static void drawButton(Graphics  g ,int x, int y,int index){
//		GraphicsUtil.drawImage(g, x, y, GraphicsUtil.LEFT_TOP,Resources.IMG_ID_BUTTON_01);
//		GraphicsUtil.drawFrame(g, index, Resources.loadImage(Resources.IMG_ID_BUTTON_TEXT), x+74, y+19, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 108, 20);
//	}
	
	private void drawTopBar(Graphics g,int x, int y){
		int px = x-100;
		int py = y-78;
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getMaxScore()), "0123456789x", px+85, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 5,"x");
		px+= 138;
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getScore()), "0123456789x", px+87, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 5,"x");
		px+= 147;
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getStep()), "0123456789x", px+83, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 3,"x");
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(30-Scene.time/1000), "0123456789x", px+145, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 2,"x");
		px+= 195;
		//显示当前可操作的道具
		curActor.paint(g, px+47, py+17);
//		curActor.updata();
	}
	private void drawBorder(Graphics g){

		GraphicsUtil.drawImage(g, 0+Shake.shakeX, 0+Shake.shakeY, GraphicsUtil.LEFT_TOP, Resources.IMG_ID_GAME_BG1);
		GraphicsUtil.drawImage(g, 0+Shake.shakeX, 102+Shake.shakeY, GraphicsUtil.LEFT_TOP, Resources.IMG_ID_GAME_BG2);
		GraphicsUtil.drawImage(g, Const.WIDTH+Shake.shakeX, 102+Shake.shakeY, GraphicsUtil.RIGHT_TOP, Resources.IMG_ID_GAME_BG3);
		GraphicsUtil.drawImage(g, 0+Shake.shakeX, Const.HEIGHT+Shake.shakeY, GraphicsUtil.LEFT_BOTTOM, Resources.IMG_ID_GAME_BG4);		
	}
	
	/***
	 * 新生成的
	 */
	Actor curActor;
	
	/**
	 * 上一步生成的actor
	 */
	Actor curActor2;
	
	/**
	 * 新生成的ACTOR
	 * @return
	 */
	public void createNewActor(boolean flag){
//		if(Util.getRandom(0, 9)==5){
//			curActor = new Actor(Actor.TYPE_飞天猪);
//			//创建新的就开始检测
//			if(flag){
//				ckeckSynthesisImp();
//				UserData.moveStep();
//			}
//			return;
//		}
		
		// 这里要随机生成 综合策划的需求
		byte itemType = getItemType();
		//处理不同是刷新2个火把
		if(nodes[0].getActorType()==Actor.TYPE_火把){
			while(itemType == Actor.TYPE_火把){
				itemType = getItemType();
			}
		}else if(nodes[0].getActorType()==Actor.TYPE_炸弹){
			while(itemType == Actor.TYPE_炸弹){
				itemType = getItemType();
			}
		}
		//如果随机到火把，就判断场景里是否有飞天猪
		if(itemType==Actor.TYPE_火把){
			if(!isHaveItem(Actor.TYPE_飞天猪)){
				while(itemType==Actor.TYPE_火把){
					itemType = getItemType();
				}
			}
		}
		curActor = new Actor(itemType);
		//curActor2 = curActor;
		//创建新的就开始检测
		if(flag){
			ckeckSynthesisImp();
			UserData.moveStep();
			System.out.println("1111111111111");
		}
	}
	public void createNewActor(){
		createNewActor(true);
	}
	public void setCurActor(byte actorType){
		curActor = new Actor(actorType);
		//curActor2 = new Actor(actorType);
		curActor2 = curActor;
	}
	public Actor createNewActor(byte actorType){
		Logger.debug("createNewActor:" + actorType);
		return new Actor(actorType);
	}
	
	//所有可合成道具的类型
	public final static byte All_Synthesis_Item_Type[]={

		Actor.TYPE_超级宝箱
		
		,Actor.TYPE_宝箱

		,Actor.TYPE_大化石
		
		,Actor.TYPE_小化石

		,Actor.TYPE_石碑

		,Actor.TYPE_09

		,Actor.TYPE_08

		,Actor.TYPE_究极骑兵

		,Actor.TYPE_神圣骑兵

		,Actor.TYPE_蔬果骑兵

		,Actor.TYPE_菜头勇士

		,Actor.TYPE_菜头弟弟
	
		,Actor.TYPE_发芽的种子
		,Actor.TYPE_种子
	};
//	合成的线索一概率60%；线索二概率30%；工具类的概率10%。
//	合成类的：
//	对于线索1，最能刷出前3阶的道具，第一阶的概率70%，第二阶的概率20%，第三阶的概率10%；
//	对于线索2，最多能刷出第2阶的道具，第一阶的概率60%；第二阶的概率40%
//
//	工具类的：
//	当出现工具类的道具时候，概率为：魔法珠和炸弹各50%。
//	飞天猪不出现在刷新的道具栏里。火把出现在刷新的道具栏里。
	private final static byte T_LINE_ONE = 0;  //线索一
	private final static byte T_LINE_TWO = 1;  //线索二
 	private final static byte T_SPECIAL = 2;   //特殊
 	private final static byte T_SPECIAL_SPECIAL = 3;   //特殊
 	private final static byte line_Container [] = {
 			T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,	
 			T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,
 			T_LINE_ONE,T_LINE_ONE,
 			T_LINE_TWO,T_LINE_TWO,T_LINE_TWO,T_LINE_TWO,
 			T_SPECIAL,T_SPECIAL_SPECIAL};
 	//洗牌
 	public static void shuffle (byte [] arry){
		byte temp;
		int ran1;
		int ran2;
		int count = 0;
		int shuffleCount = arry.length<<1;
		while(count < shuffleCount){
			ran1 = Util.getRandom(0,arry.length-1);
			ran2 = Util.getRandom(0,arry.length-1);
			temp = arry[ran1];
			arry[ran1] = arry[ran2];
			arry[ran2] = temp;
			count++;
		}
 	}
	//获取线索
	public static byte getLine(){
		shuffle(line_Container);
		return line_Container[Util.getRandom(0,line_Container.length-1)];		
	}
	public final static byte line_one_Container [] ={Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_种子,Actor.TYPE_发芽的种子,Actor.TYPE_发芽的种子,Actor.TYPE_菜头弟弟};
	public final static byte line_two_Container [] ={Actor.TYPE_恐龙,Actor.TYPE_恐龙,Actor.TYPE_恐龙,Actor.TYPE_小化石,Actor.TYPE_小化石,};
//	public final static byte line_two_Container [] ={Actor.TYPE_菜头弟弟,Actor.TYPE_发芽的种子,Actor.TYPE_小化石,Actor.TYPE_石碑};
	public final static byte special_Container []  ={Actor.TYPE_炸弹,Actor.TYPE_魔法棒};
	public final static byte special_special_Container []  ={Actor.TYPE_飞天猪,Actor.TYPE_飞天猪,Actor.TYPE_飞天猪,Actor.TYPE_火把,Actor.TYPE_火把};
	//获取线索一
	public static byte getLineOneItem(){
		shuffle(line_one_Container);
		return line_one_Container[Util.getRandom(0,line_one_Container.length-1)];		

	}
	//获取线索二
	public static byte getLineTwoItem(){
		shuffle(line_two_Container);
		return line_two_Container[Util.getRandom(0,line_two_Container.length-1)];		

	}
	//获取特殊道具
	public static byte getSpecialItem(){
		shuffle(special_Container);
		return special_Container[Util.getRandom(0,special_Container.length-1)];		

	}
	//获取特殊道具
	public static byte getSpecialSpecialItem(){
		shuffle(special_Container);
		return special_special_Container[Util.getRandom(0,special_special_Container.length-1)];		

	}
	/**
	 * 获取随即的道具
	 * @return
	 */
	public byte getItemType(){
		byte t_line = getLine();
		//积分小于20000的时候不出现飞天猪和火把
		if(t_line == T_SPECIAL_SPECIAL){
			if(UserData.getScore()<20000){
				while(t_line == T_SPECIAL_SPECIAL){
					t_line = getLine();
				}
			}
		}
			
		if(t_line == T_LINE_ONE ){
			return getLineOneItem();
		}else if(t_line == T_LINE_TWO ){
			return getLineTwoItem();
		}else if(t_line == T_SPECIAL ){
			return getSpecialItem();
		}
		else{
			return getSpecialSpecialItem();
		}
	}
	/**
	 * 地图中是否含有莫中道具
	 * @param type
	 * @return
	 */
	private boolean isHaveItem(byte type){
		for(int index= 0;index<nodes.length;index++){
			if(nodes[index].getActorType()==type){
				return true;
			}
		}
		return false;
	}
	//====================================积分计算
	//	每当放置1个道具 积分取 rand(20,30）之间。
	//	每消除1次，积分加rand(45-55)
	//	每使用1次炸弹，积分加100.
	//放置获得分数
	public void addScoreWithPut(){
		//UserData.addScore(Util.getRandom(15,25));
		UserData.addScore(RandomValue.getRandInt(15, 25));
	}
	//合成获得分数
	public void addScoreWithFit(){
		//UserData.addScore(Util.getRandom(45, 55));
		UserData.addScore(RandomValue.getRandInt(45, 55));
	}
	//炸弹获得分数
	public void addScoreWithBomb(){
		UserData.addScore(100);
	}
	/**
	 * 
	 */
	public static long lastRewardTime = System.currentTimeMillis();
	public long curTime;
	public static long time, timespace;
	/**
	 * 每30秒回复一步
	 */
	private void rewardStep(){
		curTime = System.currentTimeMillis();
		time = curTime-lastRewardTime;
		if(time>30000){
			if(UserData.getStep()+1 < 150){
				UserData.addStep(1);
			}else{
				UserData.setStep(150);
			}
			lastRewardTime = curTime;
		}
	}
	/**
	 * 合成公式数据
	 */
    private byte tempFixData[][] = new byte[4][2];
    
    //清除合成公式数据
    private void clearFixData(){
    	for(int i = 0;i<tempFixData.length;i++){    	
    			tempFixData[i][0] = -1;
    			tempFixData[i][1] = 0;
    	}
    }
    private byte getFixType(){
    	byte type = -1;
    	for(int i = 0;i<tempFixData.length;i++){    	
			if(type<tempFixData[i][0]){
				type = tempFixData[i][0];
			}			
    	}
    	type+=1;
    	return type;
    }
    private void addFixCount(byte type){    	
    	for(int i = 0;i<tempFixData.length;i++){
    		if(tempFixData[i][0]==type){
    			tempFixData[i][1]++;
    			return ;
    		}
    	}
    	//不存在重先生成
    	for(int i = 0;i<tempFixData.length;i++){
    		if(tempFixData[i][0]==-1){
    			tempFixData[i][0] = type;
    			tempFixData[i][1]++;
    			return ;
    		}
    	}
    }
    private void checkFixData(){
    	if(totalNodeList.size()>0&&synthesisType!=Actor.TYPE_恐龙){
    		clearFixData();
    		for(int i =0;i<totalNodeList.size();i++){
    			Node node = (Node)totalNodeList.elementAt(i);
    			addFixCount(node.getActorType());
    			
    		}
    	}
    }
    private void setFixCount(byte type){    	
    	tempFixData[0][0] = type;
    	tempFixData[0][1] = 3;
    }
	/**
	 * 显示合成公式的类型
	 */

	private void setFormulaType(byte newType){
		if(newType > -1 && newType < Resources.SPRITE_item_16&&newType!=Resources.SPRITE_item_09&&newType!=Resources.SPRITE_item_10&&newType!=Resources.SPRITE_item_11){	
			clearFixData();
			setFixCount(newType);
		}
	}
	/**
	 * 绘制合成公式
	 */
	private void drawFitFormula(Graphics g,int x,int  y){
//		Logger.debug("mFormulaType:"+mFormulaType);
		int px = x +20 ;
		int py = y +getHeight() + 40;
//		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_02), "3", "0123456789", px, py, 9, 16, GraphicsUtil.TRANS_NONE, 1, 1,"0");
//		GraphicsUtil.drawFrame(g, 0,  Resources.loadImage(Resources.IMG_ID_NO_SYMBO_02), px + 51, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 8, 8);
		//道具图标
//		GraphicsUtil.drawFrame(g, mFormulaType,  Resources.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL), px + 27, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 31, 31);
//		GraphicsUtil.drawFrame(g, mFormulaType+1,  Resources.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL), px + 76, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 31, 31);
		int count = 0;
		for(int i=0;i<tempFixData.length;i++){
			if(tempFixData[i][0]==-1){
				break ;
			}
			if(i>0){
				GraphicsUtil.drawFrame(g, 1,  Resources.loadImage(Resources.IMG_ID_NO_SYMBO_02), px, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 8, 8);
				px+=10;
			}
			drawItem(g,px,py,tempFixData[i][1],tempFixData[i][0]);
			px+=51;
			count++;
		}
		//绘制合成的道具
		if(count>0){
			GraphicsUtil.drawFrame(g, 0,  Resources.loadImage(Resources.IMG_ID_NO_SYMBO_02), px , py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 8, 8);
			GraphicsUtil.drawFrame(g, getFixType(),  Resources.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL), px +25, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 31, 31);
		}
    }
	private void drawItem(Graphics g,int x, int y,int num,int type){
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_02), String.valueOf(num), "0123456789", x, y, 9, 16, GraphicsUtil.TRANS_NONE, 1, 1,"0");
		GraphicsUtil.drawFrame(g, type,  Resources.loadImage(Resources.IMG_ID_S_ITEM_ICON_ALL), x + 27, y+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 31, 31);
	}
	/**
	 * 判断当前游戏是否结束
	 * @return
	 */
	private boolean isGameOver(){
		for (int index = 1; index < nodes.length; index++) {
			if(nodes[index].isNoneSpace()){
				return false;
			}
		}
		return true;
	}
	private byte itemCount[];
	public byte[] getEndItemCount(){
		if(itemCount == null){
			itemCount = new byte[5];
		}
		for (int index = 1; index < nodes.length; index++) {
			if(nodes[index].getActorType()==Actor.TYPE_神圣骑兵){
				itemCount[0]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_究极骑兵){
				itemCount[1]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_08){
				itemCount[2]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_09){
				itemCount[3]++;
			}
			else if(nodes[index].getActorType()==Actor.TYPE_超级宝箱){
				itemCount[4]++;
			}
		}
		return itemCount;
	}
	//测试概率
	public static void main(String arg[]){
		int count =0;
		while(count < 20){
//			System.out.println(getLine());
			count++;
		}
	}
	//输出数组地图数组
	public void println(){
		System.out.print("byte [] scene ={");
		for (int index = 0; index < nodes.length; index++) {
			if(index>0){
				System.out.print(",");
			}
//			System.out.print(nodes[index].getActorType());
		}
		System.out.print("};");
			
	}
	byte [] scene ={0,-1,0,-1,-1,7,-1,-1,-1,-1,-1,2,-1,1,-1,-1,1,-1,-1,-1,0,-1,-1,-1,9,-1,-1,7,8,1,-1,8,-1,2,-1,8,-1,-1,8,-1,-1,7,7,-1,0,-1,0,7,-1,-1,0,-1,7,-1,-1,0,-1,8,-1,};
}
