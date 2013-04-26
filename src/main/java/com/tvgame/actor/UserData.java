package com.tvgame.actor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import cn.ohyeah.itvgame.model.GameRecord;

import com.tvgame.game.Achichment;
import com.tvgame.game.Game;
import com.tvgame.util.Base64;
import com.tvgame.util.Util;

/**
 * ��ҵ�������Ϣ
 * 
 * @author xiaobai
 * 
 */
public class UserData {
	
	
	//8-28 ������ʯ��С��ʯ �����Ѷ�  
	//ȡǰ36��Ԫ�أ���Ϊ��6X6
	public final static byte [][] defultSceneData ={
		
		{
		 -1,2,0,-1,-1,1,-1,0,1,-1,
		 -1,2,-1,-1,-1,-1,-1,1,-1,-1,
		 0,15,1,-1,0,0,-1,-1,0,-1,
		 -1,-1,0,2,-1,11,1,-1,1,15,
		 -1,0,-1,-1,2,-1,1,-1,-1,-1,
		 -1,15,-1,0,-1,-1,-1,-1,-1,-1
		},
		{
			-1,1,-1,-1,0,-1,-1,2,0,-1,
			1,0,-1,1,15,1,-1,11,-1,0,
			-1,-1,0,-1,0,-1,-1,-1,2,15,
			-1,-1,0,-1,-1,-1,1,-1,-1,15,
			-1,1,-1,-1,1,-1,-1,-1,0,-1,
			-1,-1,-1,-1,-1,2,-1,1,-1,-1
		},
		{
			-1,1,-1,0,-1,2,-1,-1,15,-1,
			-1,0,-1,-1,-1,-1,-1,1,15,-1,
			0,-1,1,-1,-1,-1,-1,1,-1,-1,
			-1,-1,0,-1,1,2,-1,-1,1,11,
			2,-1,0,15,-1,2,-1,-1,-1,-1,
			1,-1,-1,-1,-1,-1,-1,0,-1,-1
		}
		
	};

	/*public static Vector rankList = new Vector();
	public static Vector getRankList() {
		return rankList;
	}

	public static void setRankList(Vector rankList) {
		UserData.rankList = rankList;
	}*/
	/**
	 * ����
	 */
	//public static int mGold = 100;
	/**
	 * ������Ϸ�Ĳ���
	 */
	public static int mStep = 120;
	
	/**
	 * ����
	 */
	public static int mScore = 0;
	
	/**
	 * ��һ���Ļ���
	 */
	public static int mScore2 = 0;
	
	/**
	 * ��ǰ���ڳ�����ID
	 */
	public static int mSceneId;
	/**
	 * ��ŵ�ͼ����
	 */
	public static byte mSceneData[];
	
	/**
	 * ��������������
	 */
	public static byte curActorType;
	
	/**
	 * �Ƿ���ʾ���ֽ�ѧ
	 */
	private static boolean b_show_teach = true;
	
	public static int  curCol;
	public static int  curRow;
	
	/**
	 * �ɾ����ݣ��ϳ���Ӧ����ĸ������ͳɾ���ʾ��Ӧ
	 */
	private static int nums_1;
	private static int nums_2;
	private static int nums_3;
	private static int nums_4;
	private static int nums_5;
	private static int nums_6;
	private static int nums_7;
	private static int nums_8;
	private static int nums_9;
	private static int nums_10;
	private static int nums_11;
	private static int nums_12;
	
	/**
	 * �Ƿ�����Ӧ�ɾ�, ˳���Ӧ
	 */
	public static boolean[][] achi_list = {
		/*0-ͭ��, 1-����, 2-����*/
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
		{false,false,false},
	}; 
	
	public static void updateAchi(byte type){
		switch(type){
		case Actor.TYPE_��ѿ������:
			setNums_1(getNums_1()+1);
			break;
		case Actor.TYPE_��ͷ�ܵ�:
			setNums_2(getNums_2()+1);
			break;
		case Actor.TYPE_��ͷ��ʿ:
			setNums_3(getNums_3()+1);
			break;
		case Actor.TYPE_�߹����:
			setNums_4(getNums_4()+1);
			break;
		case Actor.TYPE_��ʥ���:
			setNums_5(getNums_5()+1);
			break;
		case Actor.TYPE_�������:
			setNums_6(getNums_6()+1);
			break;
		case Actor.TYPE_08:
			setNums_7(getNums_7()+1);
			break;
		case Actor.TYPE_09:
			setNums_8(getNums_8()+1);
			break;
		case Actor.TYPE_С��ʯ:
			setNums_9(getNums_9()+1);
			break;
		case Actor.TYPE_��ʯ:
			setNums_10(getNums_10()+1);
			break;
		case Actor.TYPE_����:
			setNums_11(getNums_11()+1);
			break;
		case Actor.TYPE_��������:
			setNums_12(getNums_12()+1);
			break;
		}
	}
	
	public static void printAchi(){
		System.out.println("---------------------------");
		System.out.println("nums_1:"+nums_1);
		System.out.println("nums_2:"+nums_2);
		System.out.println("nums_3:"+nums_3);
		System.out.println("nums_4:"+nums_4);
		System.out.println("nums_5:"+nums_5);
		System.out.println("nums_6:"+nums_6);
		System.out.println("nums_7:"+nums_7);
		System.out.println("nums_8:"+nums_8);
		System.out.println("nums_9:"+nums_9);
		System.out.println("nums_10:"+nums_10);
		System.out.println("nums_11:"+nums_11);
		System.out.println("nums_12:"+nums_12);
		System.out.println("---------------------------");
		for(int i=0;i<12;i++){
			System.out.println("��óɾ�"+i+"���,����ͭ,��,��:" + achi_list[i][0] + ", " + achi_list[i][1] + ", " + achi_list[i][2]);
		}
		System.out.println("---------------------------");
	}
	
	public static int getNums_1() {
		return nums_1;
	}
	public static void setNums_1(int nums_1) {
		UserData.nums_1 = nums_1;
	}
	public static int getNums_2() {
		return nums_2;
	}
	public static void setNums_2(int nums_2) {
		UserData.nums_2 = nums_2;
	}
	public static int getNums_3() {
		return nums_3;
	}
	public static void setNums_3(int nums_3) {
		UserData.nums_3 = nums_3;
	}
	public static int getNums_4() {
		return nums_4;
	}
	public static void setNums_4(int nums_4) {
		UserData.nums_4 = nums_4;
	}
	public static int getNums_5() {
		return nums_5;
	}
	public static void setNums_5(int nums_5) {
		UserData.nums_5 = nums_5;
	}
	public static int getNums_6() {
		return nums_6;
	}
	public static void setNums_6(int nums_6) {
		UserData.nums_6 = nums_6;
	}
	public static int getNums_7() {
		return nums_7;
	}
	public static void setNums_7(int nums_7) {
		UserData.nums_7 = nums_7;
	}
	public static int getNums_8() {
		return nums_8;
	}
	public static void setNums_8(int nums_8) {
		UserData.nums_8 = nums_8;
	}
	public static int getNums_9() {
		return nums_9;
	}
	public static void setNums_9(int nums_9) {
		UserData.nums_9 = nums_9;
	}
	public static int getNums_10() {
		return nums_10;
	}
	public static void setNums_10(int nums_10) {
		UserData.nums_10 = nums_10;
	}
	public static int getNums_11() {
		return nums_11;
	}
	public static void setNums_11(int nums_11) {
		UserData.nums_11 = nums_11;
	}
	public static int getNums_12() {
		return nums_12;
	}
	public static void setNums_12(int nums_12) {
		UserData.nums_12 = nums_12;
	}
	public static int getcurCol() {
		return curCol;
	}
	public static int getCurRow() {
		return curRow;
	}
	/*public static int getGold() {
		return mGold;
	}
	

	public static void setGold(int gold) {
		mGold = gold;
	}*/

	public static int getStep() {
		return mStep;
	}

	public static void setStep(int step) {
		mStep = step;
	}
	public static byte getCurActorType() {
		return curActorType;
	}
	public static int getScore() {
		return mScore;
	}

	public static int getScore2() {
		return mScore2;
	}
	
	public static void setScore(int score) {
		mScore = score;
	}
	
	public static void setScore2(int score){
		mScore2 = score;
	}

	public static byte[] getSceneData() {
		return mSceneData;
	}

	public static void setSceneData(byte[] sceneData) {
		mSceneData = sceneData;
	}

	/**
	 * ��������
	 */
	public static void loadData(GameRecord gr) {
		//if(data != null&&data.length==3){
			Logger.debug("������Ϸ��¼");
			try {
				ByteArrayInputStream ous=	new ByteArrayInputStream(gr.getData());
				DataInputStream dou = new DataInputStream(ous);				
				mSceneData  = new byte[dou.readByte()];
				dou.read(mSceneData);
				curCol = dou.readByte();
				curRow = dou.readByte();
				curActorType = dou.readByte();
				//�ɾ�����
				boolean achichment[] = new boolean[dou.readByte()];
				for(int i = 0 ;i<achichment.length;i++){
					achichment[i] = dou.readBoolean();
				}
				//Achichment.achi_arrived = achichment;
				//���
				//setGold(dou.readInt());
				//����
				setScore(gr.getScores());
				//����2
				setScore2(gr.getScores());
				//����
				setStep(dou.readInt());
				
				setB_show_teach(dou.readBoolean());
				//printInfo(achichment);
				dou.close();
				ous.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
	}
	
	//�������¿�ʼ��Ϸ������
	public static void loadData(){
		mSceneData = getRanSceneData();
		curCol = 0;
		curRow = 0;
		curActorType = 0;
		boolean achichment[] = new boolean[7];
		for(int i = 0 ;i<achichment.length;i++){
			achichment[i] = false;
		}
		//Achichment.achi_arrived = achichment;
		
		//���
		//setGold(100);
		//����
		setScore(0);
		//����2
		setScore2(0);
		//����
		setStep(120);

		setB_show_teach(true);
	}
	
	/**
	 * ��ý��
	 * 
	 * @param gold
	 */
	/*public static void addGold(int gold) {
		mGold += gold;
	}*/

//	/**
//	 * ��ò���
//	 * 
//	 * @param mGold
//	 */
	public static void addStep(int step) {
		mStep += step;
	}

	public static void moveStep() {
		if( mStep>0){
			mStep -= 1;
		}
	}

	public static void addScore(int score) {
		mScore2 = mScore;
		mScore += score;
	}
	/*public static void consumptionGold(int gold){
		mGold -=gold;
	}*/
	/***
	 * ��ȡ����᳡��
	 * @return
	 */
	public static byte [] getRanSceneData(){
		return defultSceneData[Util.getRandom(0, defultSceneData.length-1)];
	}
	/***
	 * �����ͼ����
	 * @return
	 */
	public static byte [] loadSceneData(){
		if(mSceneData == null){
			mSceneData = defultSceneData[Util.getRandom(0, defultSceneData.length-1)];
		}
		return mSceneData;
	}
	/***
	 * �к��������Լ������õĵ�������
	 */
	public static void buildSavaData(DataOutputStream dou){
		try {
			//��ͼ����
			dou.writeByte(Scene.COLUMNS*Scene.ROWS);
			dou.write(getCurrentSceneData());
			dou.writeByte(Game.getInstance().getScene().getCol());
			dou.writeByte(Game.getInstance().getScene().getRow());
			dou.writeByte(Game.getInstance().getScene().curActor.getType());
			//�ɾ�����
			/*boolean achichment[] = Achichment.getAchi_arrived();
			dou.writeByte(achichment.length);
			for(int i =0 ;i<achichment.length;i++){
				dou.writeBoolean(achichment[i]);
			}*/
			//���
			//dou.writeInt(getGold());
			//����
			//dou.writeInt(getScore());
			//����
			dou.writeInt(getStep());
			dou.writeBoolean(b_show_teach);
			
			//printInfo(achichment);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return Base64.encode(data);
	}
	
	private static void printInfo(boolean[] achichment){
		System.out.println(Scene.COLUMNS*Scene.ROWS);
		//System.out.println(getCurrentSceneData());
		System.out.println(Game.getInstance().getScene().getCol());
		System.out.println(Game.getInstance().getScene().getRow());
		System.out.println(Game.getInstance().getScene().curActor.getType());
		for(int i =0 ;i<achichment.length;i++){
			System.out.println(achichment[i]);
		}
		//System.out.println(getGold());
		System.out.println(getScore());
		System.out.println(getStep());
		System.out.println(b_show_teach);
	}
	
	//�����ر�����
	public static byte[] getCurrentSceneData(){
		Node  nodes[] = Game.getInstance().getScene().getNodes();
		//��ͼ����
		byte data[] = new byte[nodes.length];
		for(int index =0;index<data.length;index++){
			data[index]= nodes[index].getActorType();
		}
		return data;	
	}

	public static void setB_show_teach(boolean b_show_teach) {
		UserData.b_show_teach = b_show_teach;
	}

	public static boolean isB_show_teach() {
		return b_show_teach;
	}
	
	
}
