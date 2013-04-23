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

	public static Vector rankList = new Vector();
	public static Vector getRankList() {
		return rankList;
	}

	public static void setRankList(Vector rankList) {
		UserData.rankList = rankList;
	}
	/**
	 * ����
	 */
	public static int mGold = 100;
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
	public static int getcurCol() {
		return curCol;
	}
	public static int getCurRow() {
		return curRow;
	}
	public static int getGold() {
		return mGold;
	}
	

	public static void setGold(int gold) {
		mGold = gold;
	}

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
				Achichment.achi_arrived = achichment;
				//���
				setGold(dou.readInt());
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
		Achichment.achi_arrived = achichment;
		
		//���
		setGold(100);
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
	public static void addGold(int gold) {
		mGold += gold;
	}

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
	public static void consumptionGold(int gold){
		mGold -=gold;
	}
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
			boolean achichment[] = Achichment.getAchi_arrived();
			dou.writeByte(achichment.length);
			for(int i =0 ;i<achichment.length;i++){
				dou.writeBoolean(achichment[i]);
			}
			//���
			dou.writeInt(getGold());
			//����
			//dou.writeInt(getScore());
			//����
			dou.writeInt(getStep());
			dou.writeBoolean(b_show_teach);
			
			printInfo(achichment);
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
		System.out.println(getGold());
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
