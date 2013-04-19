package com.tvgame.actor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

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
public class CopyOfUserData {
	
	
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
		CopyOfUserData.rankList = rankList;
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
	public static int mScore=0;
	
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

	public static void setScore(int score) {
		mScore = score;
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
	public static void loadData(String data[]) {
		if(data != null&&data.length==3){
			Logger.debug("�����ͼ���ݳɹ�");
			try {
				ByteArrayInputStream ous=	new ByteArrayInputStream(Base64.decode(data[0]));
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
				setScore(dou.readInt());
				//����
				setStep(dou.readInt());
				
				setB_show_teach(dou.readBoolean());
				dou.close();
				ous.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void saveSceneData2Server() {
		if(Game.getInstance().getScene()==null){
			Logger.debug("�����ͼ����ʧ��,����δ��ʼ��");
			return ;
		}
		String temp []={buildSavaData(),"",""};
		//CommonMain.doDataSave(temp);
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
	public static String buildSavaData(){
		ByteArrayOutputStream ous =	new ByteArrayOutputStream();
		DataOutputStream dou = new DataOutputStream(ous);
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
			dou.writeInt(getScore());
			//����
			dou.writeInt(getStep());
			dou.writeBoolean(b_show_teach);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte data[] = ous.toByteArray();
		try {
			dou.flush();
			ous.flush();
			dou.close();
			ous.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Base64.encode(data);
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
		CopyOfUserData.b_show_teach = b_show_teach;
	}

	public static boolean isB_show_teach() {
		return b_show_teach;
	}
	
	
}
