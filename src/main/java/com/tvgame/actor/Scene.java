package com.tvgame.actor;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyCode;
import cn.ohyeah.stb.key.KeyState;

import com.tvgame.constant.Const;
import com.tvgame.game.Achichment;
import com.tvgame.game.Game;
import com.tvgame.game.Shake;
import com.tvgame.util.GraphicsUtil;
import com.tvgame.util.Util;

public class Scene {
	/**
	 * ��ͼ������
	 */
	public final static int COLUMNS = 6;
	/**
	 * ��ͼ������
	 */
	public final static int ROWS = 6;
	/**
	 * ��Ԫ��Ŀ��
	 */
	public final static int CELL_WIDTH = 55;

	private Node nodes[];
	/**
	 * ������ڵ���
	 */
	private int currentCol;
	/**
	 * ������ڵ���
	 */
	private int currentRow;
	/**
	 * �������ڵ�λ��X
	 */
	private int x;
	/**
	 * �������ڵ�λ��Y
	 */
	private int y;

	/**
	 * Ч���������б�
	 */
	private Vector cjList = new Vector();
	public void addCJ(SpriteX sprite){
		cjList.addElement(sprite);	
	}
	
	/**
	 * 
	 * @param type
	 * @param isBigAnimation �Ƿ��Ǵ󶯻�
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
			//�󶯻�
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
	public void initlize() {		
		
		if(UserData.isB_show_teach())
			Game.getInstance().openTeach();
		
		nodes = new Node[COLUMNS * ROWS];
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
		clearFixData();
	}

	/**
	 * ��ʾ��
	 */
	public void showLock(){
		addCJ(Resources.SPRITE_daoju_lock,false);
	}
	/**
	 * ֻҪ�����˾Ͳ��Ŵ˶���
	 */
	public void showBombCJ(){
		addCJ(Resources.SPRITE_explode,false);
	}
	
	/**
	 * ��ʾˢ�µĶ���
	 */
	public void showRefresh_Ani(int x,int y)
	{
		SpriteX ani_propfresh = Resources.getSpritex(Resources.SPRITE_ani_propflash);
		ani_propfresh.setPosition(x, y);
		ani_propfresh.setAction((byte)0);
		addCJ(ani_propfresh);
	}
	
	//�󶯻�֮��ը
//	public void showBIG_BOMB()
//	{
//		addCJ(Resources.SPRITE_big_ani_bomb,true);
//	}
	
//	//�󶯻�ħ����
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
	 * ��������
	 */
	public void load() {

		//��ʼ���ֿ�
		byte data[]= UserData.loadSceneData();
		for (int index = 0; index < nodes.length; index++) {
			if(nodes[index]==null){
				nodes[index] = new Node(index, this);
			}
			if(data[index]>0){
				nodes[index].setActor(createNewActor(data[index]));
			}else{
				nodes[index].setActor(null);
			}
		}
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
	 * �Ƿ�յ�
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
	 * �������⴦��
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
	 * �Ƿ��ǿյ�
	 */
	public boolean isNoneSpace(int index) {
		return nodes[index].isNoneSpace();
	}

	/**
	 * ��ǰ�����Ƿ�Ϊ�յ�
	 */
	public boolean isNoneSpaceCurrentNode() {
		return isNoneSpace(currentCol, currentRow);
	}

	public byte getCurrentNodeActorType() {
		return getNodeActorType(getCurIndex());
	}

	/**
	 * ����������λ���жϵ������� ��0��Ϊ�յأ����Ϊ0��ʱ��Ϊ�ݵ�
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
	 * �µ�ͼ
	 */
	public void newScene() {
		//��ʼ���ֿ�
		byte data[]= UserData.getRanSceneData();
//		for (int i = 0; i < data.length; i++) {
//			Logger.debug("data ="+data[i]);
//		}
		for (int index = 0; index < nodes.length; index++) {
			if(nodes[index]==null){
				nodes[index] = new Node(index, this);
			}
			if(data[index]>0){
				nodes[index].setActor(createNewActor(data[index]));
			}else{
				nodes[index].setActor(null);
			}
		}
		for (int index = 0; index < nodes.length; index++) {
			nodes[index].bulidFloor();
		}
		createNewActor(false);
		currentCol = 0;
		currentRow = 0;
	}

	/**
	 * ��ͼ��Ļ���
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
	 * ��������
	 */
	private int dialog_index = 0;
	public void updata(KeyState key) {
		//-----------------------------ȥ������Ի�---------------------------------//
//		if(Dialog.isShowDialog)
//		{
//			 if (KeyBoard.isKeyDown(KeyBoard.GMK_SELECT)) {
//				 if(dialog_index==0)
//				 { 
//					 System.out.println("1111111111");
//					 Dialog.initDialog("������","�ȿȣ����ǲ������죬�ҵĲ�������еģ����ҵ���ʯ�����������������˰�������������������䣡",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 1)
//				 { 
//						 Dialog.initDialog("С����","��������Ҳ��Խ��̫ңԶ�˰ɣ�",Resources.IMG_ID_DIALOG_ROLE);}
//				 else if(dialog_index == 2)
//				 { 
//						 Dialog.initDialog("������","�Ұ����ٻ�����������������ͷ�Ե��˰��Ҵ����ҵĲ��䣬����������һ��֮������֮���ˣ�����Ȩ������ҵ�����,�������ǲ������������Ȼ���жԸ��ɶ�Ŀ��������Ҳ����Ϊ�������Ĳ����ʱ���Ҿͻ������ȥ����������Դ��߾���������������֮��ҡ�",Resources.IMG_ID_DIALOG_ROLE2);}
//				 else if(dialog_index == 3)
//				 {
//					 Dialog.initDialog("������","���жԸ��ɶ�Ŀ��������Ҳ����Ϊ�������Ĳ����ʱ���Ҿͻ������ȥ����������Դ��߾���������������֮��ҡ�",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 4)
//				 { 
//					 Dialog.initDialog("С����","��Ϊ��·��Ů���ĺ��ˣ��ҵ�ȻҪΪ����ΰ��ļ������⣬Ϊ�����Ǽ�����������Ҿ����ɽ�!",Resources.IMG_ID_DIALOG_ROLE);
//				 }	
//				 else if(dialog_index == 5)
//				 { 
//					 Dialog.initDialog("������","�õģ����ҽ�����ι������ǵ������ɡ�",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 6)
//				 {
//					 Dialog.initDialog("������","ÿ�ν���Ļ[���Ͻ�]ˢ�³�������Ʒ�ŵ� �����ݵظ����У���[3��]����[3������]�ĵ���ͬ�������Ʒ����һ�𼴿��������壬�����ͻ��ɸ���һ������Ʒ��",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 7)
//				 {
//					 Dialog.initDialog("������","��Ϸ�У��㻹����ʹ��[ը��]�ݻٵ�ǰ�㲻��Ҫ����Ʒ��Ҳ����ʹ��[ħ����]��������ǰ��ͼ�ϵĶ����Ʒ��λ�á�",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 8)
//				 {
//					 Dialog.initDialog("������","[����]����ϲ���ڵ�ͼ�������������㣬�������޴�����ʱ�򣬾ͻ���[ʯ��]������[������]����ĳ���ض��׶γ�������Ϸ��ͼ��ǵ���ֻ����[���]�����������ǡ�",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 9)
//				 {
//					 Dialog.initDialog("������","����[������]����ĳ���ض��׶γ�������Ϸ��ͼ��ǵ���ֻ����[���]�����������ǡ���Ϸ�а���[���ּ�9]��ʱ������Ϸ�̳ǡ�",Resources.IMG_ID_DIALOG_ROLE2);
//				 }
//				 else if(dialog_index == 10)
//				 {
//					 Dialog.isShowDialog =false;
//				 }
//				 dialog_index++;
//			}
//		}
//		else
		{
		// ������		
		if (/*KeyBoard.isKeyRepeated(KeyBoard.GMK_UP)||*/key.containsAndRemove(KeyCode.UP)) {
			currentRow = currentRow > 0 ?( currentRow-1) : ROWS - 1;
			ckeckSynthesisImp();
		}else if (/*KeyBoard.isKeyRepeated(KeyBoard.GMK_RIGHT)||*/key.containsAndRemove(KeyCode.RIGHT)){
			currentCol = currentCol < COLUMNS - 1 ?( currentCol+1) : 0;
			ckeckSynthesisImp();
		}else if (/*KeyBoard.isKeyRepeated(KeyBoard.GMK_DOWN)||*/key.containsAndRemove(KeyCode.DOWN)) {
			currentRow = currentRow < ROWS - 1 ? (currentRow+1) : 0;
			ckeckSynthesisImp();
		}else if (/*KeyBoard.isKeyRepeated(KeyBoard.GMK_LEFT)||*/key.containsAndRemove(KeyCode.LEFT)) {
			currentCol = currentCol > 0 ? (currentCol-1) : COLUMNS - 1;
			ckeckSynthesisImp();
		} else if (key.containsAndRemove(KeyCode.OK)) {
			doKeyFire();
		}else if (key.containsAndRemove(KeyCode.NUM0)||key.containsAndRemove(KeyCode.BACK)) {
			//2012-11-22��Ϸ��ʹ��0�����߷��ؼ� �����Դ���Ϸ�˵�
			Game.getInstance().openSysMenu();
//			Game.getInstance().backMainMenu();
		} 
		else if (key.containsAndRemove(KeyCode.NUM9)) {
//			Game.getInstance().openMall();
		} 
		else if(key.containsAndRemove(KeyCode.NUM7)){
//			Game.getInstance().openGameEnd();
//			println();
			Game.getInstance().openTeach();
			//������
//			CommonMain.doGameBegin();
			}
		//���ѵ��� ��ݼ�
		else if(key.containsAndRemove(KeyCode.NUM1)) 
		{
			if (UserData.getGold() >= 	100 ) {
				UserData.consumptionGold(100);
				Game.getInstance().getScene().setCurActor(Actor.TYPE_ը��);
				Game.getInstance().showTip("�һ��ɹ���");
				ckeckSynthesisImp();
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		}
		else if(key.containsAndRemove(KeyCode.NUM2)) 
		{
			if (UserData.getGold() >= 	130 ) {
				UserData.consumptionGold(130);
				Game.getInstance().getScene().setCurActor(Actor.TYPE_ħ����);
				Game.getInstance().showTip("�һ��ɹ���");
				ckeckSynthesisImp();
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		
		}
		else if(key.containsAndRemove(KeyCode.NUM3)) 
		{
			if (UserData.getGold() >= 	80 ) {
				UserData.consumptionGold(80);
				Game.getInstance().getScene().setCurActor(Actor.TYPE_���);
				Game.getInstance().showTip("�һ��ɹ���");
				ckeckSynthesisImp();
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		}
		else if(key.containsAndRemove(KeyCode.NUM4)) 
		{
			if (UserData.getGold() >= 	120 ) {
				UserData.consumptionGold(120);
				UserData.addStep(250);
				Game.getInstance().showTip("�һ��ɹ���");
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		
		}
		else if(key.containsAndRemove(KeyCode.NUM5)) 
		{
			if (UserData.getGold() >= 	50 ) {
				UserData.consumptionGold(50);
				Game.getInstance().getScene().setCurActor(Actor.TYPE_��ͷ�ܵ�);
				Game.getInstance().showTip("�һ��ɹ���");
				ckeckSynthesisImp();
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		}
		else if(key.containsAndRemove(KeyCode.NUM6)) 
		{
			if (UserData.getGold() >= 	20 ) {
				UserData.consumptionGold(20);
				Game.getInstance().getScene().setCurActor(Actor.TYPE_��ѿ������);
				Game.getInstance().showTip("�һ��ɹ���");
				ckeckSynthesisImp();
			} else {
				Game.getInstance().openMall();
				Game.getInstance().showTip("���Ҳ��㣬�һ�ʧ�ܡ����ȳ�ֵ��");
			}
		}
		
		
		}
		
		rewardStep();
	}

	/**
	 * ����Ƿ���Ժ��壨������Ժ���Ļ���
	 * 
	 */
	static Vector nodelist = new Vector();
	static Vector totalNodeList = new Vector();

	public Node getNode(int index) {
		return nodes[index];
	}
	//�����Ժ�ĵ�������
	private byte synthesisType;
	/**
	 * �������ƺ������
	 */
	public void ckeckSynthesisImp() {
		if(curActor.getType()!=Actor.TYPE_ħ����){
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
		/*Node node;
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorStopSynthesis();// ֹͣ����
		}
		totalNodeList.removeAllElements();
		if (column == 0 && row == 0) {
			//�ֿ��������
			return -1;
		}
		if(flag&&!getNode(getIndex(column, row)).isNoneSpace()){
			//�ǿյز����
			return -1;
		}
		if(actorType>Actor.TYPE_��������){
			//�Ǻ�����߲��μӼ��
			return -1;
		}
		byte type = ckeckSynthesis(column, row,actorType);		
		System.out.println("check----- "+"��"+column +"��"+row+"����"+actorType);
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorReadySynthesis(node.getColumn(),node.getRow(),column,row);// ׼��������ʵ���嶯��
//			return type;
		}
		return type;*/
		
		Node node;
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorStopSynthesis();// ֹͣ����
		}
		totalNodeList.removeAllElements();
		if (column == 0 && row == 0) {
			//�ֿ��������
			return -1;
		}
		if(flag&&!getNode(getIndex(column, row)).isNoneSpace()){
			//�ǿյز����
			return -1;
		}
		if(actorType>Actor.TYPE_��������){
			//�Ǻ�����߲��μӼ��
			return -1;
		}
        if(actorType==Actor.TYPE_09){
			//����Ǳ����μӺ���
			return -1;
		}
		byte type = ckeckSynthesis(column, row,actorType);		
		System.out.println("check----- "+"��"+column +"��"+row+"����"+actorType);
		for (int index = 0; index < totalNodeList.size(); index++) {
			node = (Node) totalNodeList.elementAt(index);
			node.setActorReadySynthesis(node.getColumn(),node.getRow(),column,row);// ׼��������ʵ���嶯��
//			return type;
		}
		return type;
	}
	/**
	 * ������
	 */
//	public byte _ckeckSynthesis() {
//		Node node;
//		for (int index = 0; index < totalNodeList.size(); index++) {
//			node = (Node) totalNodeList.elementAt(index);
//			node.setActorStopSynthesis();// ֹͣ����
//		}
//		totalNodeList.removeAllElements();
//		if (currentRow == 0 && currentCol == 0) {
//			//�ֿ��������
//			return -1;
//		}
//		if(!getCurNode().isNoneSpace()){
//			//�ǿյز����
//			return -1;
//		}
//		if(curActor.getType()>10){
//			//�Ǻ�����߲��μӼ��
//			return -1;
//		}
//		byte type = ckeckSynthesis(curActor.getType());		
//		for (int index = 0; index < totalNodeList.size(); index++) {
//			node = (Node) totalNodeList.elementAt(index);
//			node.setActorReadySynthesis();// ׼��������ʵ���嶯��
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
		}else if(actorType == Actor.TYPE_����){
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
		// �ֿⲻ�μӱ���
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
		// ���
		ckeckSynthesis(column - 1, row, actorType,true);
		// �ұ�
		ckeckSynthesis(column + 1, row, actorType,true);
		// �±�
		ckeckSynthesis(column, row + 1, actorType,true);
		// �ϱ�
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
	public void setCurNodeActor(Actor actor){
		getCurNode().setActor(actor);
		getCurNode().setFloorType((byte)0);// �ǿյ�
		bulidFloor();
	}
	/**
	 * ��������Ƿ��пյ�
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
	 * ������ɵ��ߵĺ���
	 */
	public void checkItem(int _col,int _row,byte type){
		byte newtype = _ckeckSynthesis(_col,_row,type,false);
		if(totalNodeList.size()>0){
			//����
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
		System.out.println("������ col ="+_col+"row ="+_row);
		if(!hasNoneSpaceWithAround(_col, _row)&&!hasTypeWithAround(_col, _row,Actor.TYPE_����)){
			if(!(_col==0&&_row==0))
				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_ʯ��));
			checkItem(_col, _row,Actor.TYPE_ʯ��);
			
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
		_ckeckSynthesis(_col,_row,Actor.TYPE_����,false);
		if(totalNodeList.size()>0){
			//����Ƿ�����ƶ�
			Node node;
			int column,row;
			//���
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
//				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_С��ʯ));
//				//���С��ʯ�Ƿ���Ժϳ�
//				checkItem(_col, _row,Actor.TYPE_С��ʯ);
//			}else{
				for (int index = 0; index < totalNodeList.size(); index++) {
					node = (Node) totalNodeList.elementAt(index);
					node.setActor(createNewActor(Actor.TYPE_ʯ��));
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
//				getNode(getIndex(_col, _row)).setActor(createNewActor(Actor.TYPE_ʯ��));
				//���ʯ���ǹ����Ժϳ�
				checkItem(_col, _row,Actor.TYPE_ʯ��);
				System.out.println("ccccccccccccccccc");
				
//			}
		}
	}
	/**
	 * ȷ�ϼ�
	 */
	public void doKeyFire() {
		if (currentRow == 0 && currentCol == 0) {
			// �������Ĳ���
			actorSaveToStore();
		} else {
			// ���������
			// ը��
			// ���
			// ����
			// ��
			// ħ������
			if(UserData.getStep()<1){
				Game.getInstance().openMall();
				Game.getInstance().showTip("�����Ѿ�����,�����ĵȴ�,ϵͳ�����Զ����������Ĳ������߳�ֵ��������");
				return ;
			}
			boolean bOperate = false;
			byte currentActorType = curActor.getType();
			Logger.debug("currentActorType:"+currentActorType);		
			switch (getCurrentNodeActorType()) {
			case Actor.TYPE_�յ�:
				if(currentActorType != Actor.TYPE_ը��/*&&currentActorType != Actor.TYPE_���*/){
					switch(currentActorType){
						case Actor.TYPE_���:
							doHuoBa_Logic();
							break;
						case Actor.TYPE_ħ����:
							doMofa_Logic();
//							//�󶯻�
//							showBIG_MOFAQIU();
							break;
						case Actor.TYPE_������:
							setCurNodeActor(curActor);
							break;
						case Actor.TYPE_����:
							do_Logic();
							break;
						default:
							if(totalNodeList.size()>0){
								//����
								Node node;
								for (int index = 0; index < totalNodeList.size(); index++) {
									node = (Node) totalNodeList.elementAt(index);
									node.setNullSpace();
								}
								setCurNodeActor(createNewActor(synthesisType));
								addScoreWithFit();
							}else{
								//��������
								setCurNodeActor(curActor);
								//�ж��ĸ������Ƿ��п���
//								int _column = getCurNode().getColumn();
//								int _row  = getCurNode().getRow();
//								if(_row > 0 &&getNode(getIndex(_column, _row - 1)).getActorType()==Actor.TYPE_����){
//									checkKonglong(_column, _row - 1);
//								}
//								if(_row < ROWS-1&&getNode(getIndex(_column, _row + 1)).getActorType()==Actor.TYPE_����){
//									checkKonglong(_column, _row +1);
//								}
//								if(_column>0&&getNode(getIndex(_column-1, _row )).getActorType()==Actor.TYPE_����){
//									checkKonglong(_column-1, _row);
//								}
//								if(_column <COLUMNS-1&&getNode(getIndex(_column+1, _row )).getActorType()==Actor.TYPE_����){
//									checkKonglong(_column+1, _row);
//								}
							}
							check����(getCurNode());
						break;
					}
					createNewActor();
					bOperate = true;
				}
				break;
			case Actor.TYPE_��������:
				getCurNode().setNullSpace();
				bOperate = true;
				break;
			case Actor.TYPE_����:
				getCurNode().setNullSpace();
				bOperate = true;
				break;
			case Actor.TYPE_������:
				switch(currentActorType){
				case Actor.TYPE_���:
					getCurNode().setNullSpace(); //Ҫ���ɿ���
					createNewActor();
					bOperate = true;
					//�������
//					try {
//						Game.getInstance().musicMasterPIG.player.setMediaTime(0);
//						Game.getInstance().musicMasterPIG.player.start();
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (MediaException e) {
//						e.printStackTrace();
//					}
					System.out.println("ddddddddddddddd");
					break;
				}
				break;
			case Actor.TYPE_����:
			case Actor.TYPE_��ѿ������:
			case Actor.TYPE_С��ʯ:
			case Actor.TYPE_��ʯ:
			case Actor.TYPE_ʯ��:
			case Actor.TYPE_��ʥ���:
			case Actor.TYPE_����:	
			case Actor.TYPE_�������:
			case Actor.TYPE_��ͷ��ʿ:
			case Actor.TYPE_��ͷ�ܵ�:
			case Actor.TYPE_�߹����:
			case Actor.TYPE_08:
			case Actor.TYPE_09:
			case Actor.TYPE_16:
				switch(currentActorType){
				case Actor.TYPE_ը��:
					getCurNode().setNullSpace();
					createNewActor();
					addScoreWithBomb();
					Shake.shake_start(Shake.SHAKE_TYPE_NORMAL_ATTACK,1, true);
					showBombCJ();
					//�󶯻�
//					showBIG_BOMB();
					System.out.println("11111111111111111111");
					bOperate = true;
					break;
				}
				break;
			}
			if(bOperate){
				����_Move();
				������_Fly();
				addScoreWithPut();
				if(isGameOver()){
					Game.getInstance().openGameEnd();
				}
			}else{
				//�ı�ϳɹ�ʽ
				setFormulaType(getCurrentNodeActorType());
				showLock();
			}
			bulidFloor();
		}
	}
	public Vector konglongList = new Vector();
	//������ʹ��
	public Vector kongdiList = new Vector();
	/**
	 * �����ƶ�
	 */
	public void ����_Move(){
		konglongList.removeAllElements();
//		kongdiList.removeAllElements();
		Node node;
		for (int index = 1; index < nodes.length; index++) {
			node = nodes[index];
			if(node.getActorType()== Actor.TYPE_����&&node!=getCurNode()){
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
	 * ���������
	 */
	public void ������_Fly(){
		konglongList.removeAllElements();
		kongdiList.removeAllElements();
		Node node;
		for (int index = 1; index < nodes.length; index++) {
			node = nodes[index];
			if(node.getActorType()== Actor.TYPE_������&&node!=getCurNode()){
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
		check����(node_none);
		
	}
	//��ȡ���ܵĿյ�
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
	 *��ѷ��ڿյ���
	 */
	public void doHuoBa_Logic()
	{
		setCurNodeActor(createNewActor(Actor.TYPE_16));
		Logger.debug("��ѱ����ʯ");
		check����(getCurNode());
	}
	
	/**
	 *ħ�����߼�
	 */
	public void doMofa_Logic() {
		if(totalNodeList.size()>0){
			//����
			Node node;
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				node.setNullSpace();
			}
			setCurNodeActor(createNewActor(synthesisType));
		
			addScoreWithFit();
		}else{
			setCurNodeActor(createNewActor(Actor.TYPE_16));
			
			Logger.debug("ħ�����ӱ����ʯ");
		}	
		check����(getCurNode());
	}
	public void check����(Node node){
		//�ж��ĸ������Ƿ��п���
		int _column = node.getColumn();
		int _row  = node.getRow();
		if(_row > 0 &&getNode(getIndex(_column, _row - 1)).getActorType()==Actor.TYPE_����){
			checkKonglong(_column, _row - 1);
		}
		if(_row < ROWS-1&&getNode(getIndex(_column, _row + 1)).getActorType()==Actor.TYPE_����){
			checkKonglong(_column, _row +1);
		}
		if(_column>0&&getNode(getIndex(_column-1, _row )).getActorType()==Actor.TYPE_����){
			checkKonglong(_column-1, _row);
		}
		if(_column <COLUMNS-1&&getNode(getIndex(_column+1, _row )).getActorType()==Actor.TYPE_����){
			checkKonglong(_column+1, _row);
		}
	}
	/***
	 * ���������߼�
	 */
	public void do_Logic() {
		if(hasNoneSpaceWithAround(currentCol, currentRow)&&!hasTypeWithAround(currentCol, currentRow,Actor.TYPE_����)){
			setCurNodeActor(curActor);
//			createNewActor();								
			return ;
		}
		if(totalNodeList.size()>0){
			//����Ƿ�����ƶ�
			Node node;
			int column,row;
			//���
			setCurNodeActor(curActor);
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				column = node.getColumn();
				row  = node.getRow();
				if(hasNoneSpaceWithAround(column, row ))
				{
					setCurNodeActor(curActor);
//					createNewActor();
					return ;
				}
			}
			setCurNodeActor(null);//����
			for (int index = 0; index < totalNodeList.size(); index++) {
				node = (Node) totalNodeList.elementAt(index);
				node.setActor(createNewActor(Actor.TYPE_ʯ��));
			}
//				setCurNodeActor(createNewActor(Actor.TYPE_ʯ��));
			checkItem(currentCol, currentRow,Actor.TYPE_ʯ��);

		}else{
			//��������
			setCurNodeActor(curActor);
		}
	}
	/**
	 * ��ȡ���ܵĵ�Ԫ��
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
	 * ���
	 */

	/**
	 * �ݴ浽�ֿ⣬����ֿ��Ѿ����ڵĻ�������ǰ���滻����
	 */
	private void actorSaveToStore() {
		if (nodes[0].getActorType() == -1) {
			nodes[0].setActor(curActor);
			createNewActor(false);
		} else {
			Actor temp = nodes[0].getActor();
			nodes[0].setActor(curActor);
			curActor = temp;
		}
	}

	/**
	 * ���ƽ���
	 */
	private void paintFocus(Graphics g, int x, int y) {
		int dx = x + currentCol * CELL_WIDTH-7;
		int dy = y + currentRow * CELL_WIDTH-7;
		GraphicsUtil.drawImage(g, dx, dy, GraphicsUtil.LEFT_TOP, Resources.IMG_ID_SELECTED);
	}
//	/**
//	 * ���Ƶײ�����ʾ ��7�������̵� 9�������̳�
//	 * 
//	 */
//	private void drawBottomButton(Graphics g,int x,int y){
//		int py = y + getHeight() + 30;
//		int px = x + getWidth() - 305;  
////		drawButton(g,px,py,0);
//		drawButton(g,px+160,py,1);
//	}
//	/**
//	 * ���ư���
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
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getGold()), "0123456789x", px+55, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 4,"x");
		px+= 138;
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getScore()), "0123456789x", px+55, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 5,"x");
		px+= 147;
		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO), String.valueOf(UserData.getStep()), "0123456789x", px+63, py+4, 15, 27, GraphicsUtil.TRANS_NONE, 1, 4,"x");
		px+= 156;
		//��ʾ��ǰ�ɲ����ĵ���
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
	 * �����ɵ�
	 */
	Actor curActor;
	/**
	 * �����ɵ�ACTOR
	 * @return
	 */
	public void createNewActor(boolean flag){
//		if(Util.getRandom(0, 9)==5){
//			curActor = new Actor(Actor.TYPE_������);
//			//�����µľͿ�ʼ���
//			if(flag){
//				ckeckSynthesisImp();
//				UserData.moveStep();
//			}
//			return;
//		}
		
		// ����Ҫ������� �ۺϲ߻�������
		byte itemType = getItemType();
		//����ͬ��ˢ��2�����
		if(nodes[0].getActorType()==Actor.TYPE_���){
			while(itemType == Actor.TYPE_���){
				itemType = getItemType();
			}
		}else if(nodes[0].getActorType()==Actor.TYPE_ը��){
			while(itemType == Actor.TYPE_ը��){
				itemType = getItemType();
			}
		}
		//����������ѣ����жϳ������Ƿ��з�����
		if(itemType==Actor.TYPE_���){
			if(!isHaveItem(Actor.TYPE_������)){
				while(itemType==Actor.TYPE_���){
					itemType = getItemType();
				}
			}
		}
		curActor = new Actor(itemType);
		//�����µľͿ�ʼ���
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
	}
	public Actor createNewActor(byte actorType){
		Logger.debug("createNewActor:" + actorType);
		return new Actor(actorType);
	}
	
	//���пɺϳɵ��ߵ�����
	public final static byte All_Synthesis_Item_Type[]={

		Actor.TYPE_��������
		
		,Actor.TYPE_����

		,Actor.TYPE_��ʯ
		
		,Actor.TYPE_С��ʯ

		,Actor.TYPE_ʯ��

		,Actor.TYPE_09

		,Actor.TYPE_08

		,Actor.TYPE_�������

		,Actor.TYPE_��ʥ���

		,Actor.TYPE_�߹����

		,Actor.TYPE_��ͷ��ʿ

		,Actor.TYPE_��ͷ�ܵ�
	
		,Actor.TYPE_��ѿ������
		,Actor.TYPE_����
	};
//	�ϳɵ�����һ����60%������������30%��������ĸ���10%��
//	�ϳ���ģ�
//	��������1������ˢ��ǰ3�׵ĵ��ߣ���һ�׵ĸ���70%���ڶ��׵ĸ���20%�������׵ĸ���10%��
//	��������2�������ˢ����2�׵ĵ��ߣ���һ�׵ĸ���60%���ڶ��׵ĸ���40%
//
//	������ģ�
//	�����ֹ�����ĵ���ʱ�򣬸���Ϊ��ħ�����ը����50%��
//	������������ˢ�µĵ��������ѳ�����ˢ�µĵ������
	private final static byte T_LINE_ONE = 0;  //����һ
	private final static byte T_LINE_TWO = 1;  //������
 	private final static byte T_SPECIAL = 2;   //����
 	private final static byte T_SPECIAL_SPECIAL = 3;   //����
 	private final static byte line_Container [] = {
 			T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,	
 			T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,T_LINE_ONE,
 			T_LINE_ONE,T_LINE_ONE,
 			T_LINE_TWO,T_LINE_TWO,T_LINE_TWO,T_LINE_TWO,
 			T_SPECIAL,T_SPECIAL_SPECIAL};
 	//ϴ��
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
	//��ȡ����
	public static byte getLine(){
		shuffle(line_Container);
		return line_Container[Util.getRandom(0,line_Container.length-1)];		
	}
	public final static byte line_one_Container [] ={Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_��ѿ������,Actor.TYPE_��ѿ������,Actor.TYPE_��ͷ�ܵ�};
	public final static byte line_two_Container [] ={Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_����,Actor.TYPE_С��ʯ,Actor.TYPE_С��ʯ,};
//	public final static byte line_two_Container [] ={Actor.TYPE_��ͷ�ܵ�,Actor.TYPE_��ѿ������,Actor.TYPE_С��ʯ,Actor.TYPE_ʯ��};
	public final static byte special_Container []  ={Actor.TYPE_ը��,Actor.TYPE_ħ����};
	public final static byte special_special_Container []  ={Actor.TYPE_������,Actor.TYPE_������,Actor.TYPE_������,Actor.TYPE_���,Actor.TYPE_���};
	//��ȡ����һ
	public static byte getLineOneItem(){
		shuffle(line_one_Container);
		return line_one_Container[Util.getRandom(0,line_one_Container.length-1)];		

	}
	//��ȡ������
	public static byte getLineTwoItem(){
		shuffle(line_two_Container);
		return line_two_Container[Util.getRandom(0,line_two_Container.length-1)];		

	}
	//��ȡ�������
	public static byte getSpecialItem(){
		shuffle(special_Container);
		return special_Container[Util.getRandom(0,special_Container.length-1)];		

	}
	//��ȡ�������
	public static byte getSpecialSpecialItem(){
		shuffle(special_Container);
		return special_special_Container[Util.getRandom(0,special_special_Container.length-1)];		

	}
	/**
	 * ��ȡ�漴�ĵ���
	 * @return
	 */
	public byte getItemType(){
		byte t_line = getLine();
		//����С��20000��ʱ�򲻳��ַ�����ͻ��
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
	 * ��ͼ���Ƿ���Ī�е���
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
	//====================================���ּ���
	//	ÿ������1������ ����ȡ rand(20,30��֮�䡣
	//	ÿ����1�Σ����ּ�rand(45-55)
	//	ÿʹ��1��ը�������ּ�100.
	//���û�÷���
	public void addScoreWithPut(){
		UserData.addScore(Util.getRandom(15,25));
	}
	//�ϳɻ�÷���
	public void addScoreWithFit(){
		UserData.addScore(Util.getRandom(45, 55));
	}
	//ը����÷���
	public void addScoreWithBomb(){
		UserData.addScore(100);
	}
	/**
	 * 
	 */
	public long lastRewardTime = System.currentTimeMillis();
	/**
	 * ÿ2���ӻظ�����
	 */
	private void rewardStep(){
		long curTime = System.currentTimeMillis();
		if(curTime-lastRewardTime>120000){
			UserData.addStep(3);
			lastRewardTime = curTime;
		}
	}
	/**
	 * �ϳɹ�ʽ����
	 */
    private byte tempFixData[][] = new byte[4][2];
    
    //����ϳɹ�ʽ����
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
    	//��������������
    	for(int i = 0;i<tempFixData.length;i++){
    		if(tempFixData[i][0]==-1){
    			tempFixData[i][0] = type;
    			tempFixData[i][1]++;
    			return ;
    		}
    	}
    }
    private void checkFixData(){
    	if(totalNodeList.size()>0&&synthesisType!=Actor.TYPE_����){
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
	 * ��ʾ�ϳɹ�ʽ������
	 */

	private void setFormulaType(byte newType){
		if(newType > -1 && newType < Resources.SPRITE_item_16&&newType!=Resources.SPRITE_item_09&&newType!=Resources.SPRITE_item_10&&newType!=Resources.SPRITE_item_11){	
			clearFixData();
			setFixCount(newType);
		}
	}
	/**
	 * ���ƺϳɹ�ʽ
	 */
	private void drawFitFormula(Graphics g,int x,int  y){
//		Logger.debug("mFormulaType:"+mFormulaType);
		int px = x +20 ;
		int py = y +getHeight() + 45;
//		GraphicsUtil.drawString(g, Resources.loadImage(Resources.IMG_ID_NO_02), "3", "0123456789", px, py, 9, 16, GraphicsUtil.TRANS_NONE, 1, 1,"0");
//		GraphicsUtil.drawFrame(g, 0,  Resources.loadImage(Resources.IMG_ID_NO_SYMBO_02), px + 51, py+8, GraphicsUtil.TRANS_NONE, GraphicsUtil.HCENTER_VCENTER, 8, 8);
		//����ͼ��
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
		//���ƺϳɵĵ���
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
	 * �жϵ�ǰ��Ϸ�Ƿ����
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
			if(nodes[index].getActorType()==Actor.TYPE_��ʥ���){
				itemCount[0]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_�������){
				itemCount[1]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_08){
				itemCount[2]++;
			}else if(nodes[index].getActorType()==Actor.TYPE_09){
				itemCount[3]++;
			}
			else if(nodes[index].getActorType()==Actor.TYPE_��������){
				itemCount[4]++;
			}
		}
		return itemCount;
	}
	//���Ը���
	public static void main(String arg[]){
		int count =0;
		while(count < 20){
//			System.out.println(getLine());
			count++;
		}
	}
	//��������ͼ����
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
