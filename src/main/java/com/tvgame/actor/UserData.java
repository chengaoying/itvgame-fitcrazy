package com.tvgame.actor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import cn.ohyeah.itvgame.model.GameRecord;

import com.tvgame.constant.Const;
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
	
	/*��ߵ÷�*/
	public static int maxScore = 0;
	
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
	public static int[] nums = new int[12];
	
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
	
	/**
	 * ��Ӧ�ĳɾ��Ƿ���ʾ��
	 */
	public static boolean[][] achi_show = {
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
			nums[0]++;
			break;
		case Actor.TYPE_��ͷ�ܵ�:
			nums[1]++;
			break;
		case Actor.TYPE_��ͷ��ʿ:
			nums[2]++;
			break;
		case Actor.TYPE_�߹����:
			nums[3]++;
			break;
		case Actor.TYPE_��ʥ���:
			nums[4]++;
			break;
		case Actor.TYPE_�������:
			nums[5]++;
			break;
		case Actor.TYPE_08:
			nums[6]++;
			break;
		case Actor.TYPE_09:
			nums[7]++;
			break;
		case Actor.TYPE_С��ʯ:
			nums[8]++;
			break;
		case Actor.TYPE_��ʯ:
			nums[9]++;
			break;
		case Actor.TYPE_����:
			nums[10]++;
			break;
		case Actor.TYPE_��������:
			nums[11]++;
			break;
		}
		
		checkAchi();
	}
	
	private static void checkAchi(){
		for(int i=0;i<nums.length;i++){
			if(nums[i] >= Integer.parseInt(Const.achi_info[i][2][1])){
				achi_list[i][0] = true;
				achi_list[i][1] = true;
				achi_list[i][2] = true;
			}else if(nums[i] >= Integer.parseInt(Const.achi_info[i][1][1])){
				achi_list[i][0] = true;
				achi_list[i][1] = true;
			}else if(nums[i] >= Integer.parseInt(Const.achi_info[i][0][1])){
				achi_list[i][0] = true;
			}
		}
	}
	
	
	public static void printAchi(){
		System.out.println("---------------------------");
		for(int j=0;j<nums.length;j++){
			System.out.println("nums["+j+"]="+nums[j]);
		}
		System.out.println("---------------------------");
		for(int i=0;i<12;i++){
			System.out.println("��óɾ�"+i+"���,����ͭ,��,��:" + achi_list[i][0] + ", " + achi_list[i][1] + ", " + achi_list[i][2]);
		}
		System.out.println("---------------------------");
		for(int i=0;i<12;i++){
			System.out.println("�ɾ�"+i+"�Ƿ���ʾ,����ͭ,��,��:" + achi_show[i][0] + ", " + achi_show[i][1] + ", " + achi_show[i][2]);
		}
		System.out.println("---------------------------");
	}
	
	public static int getMaxScore() {
		return maxScore;
	}

	public static void setMaxScore(int maxScore) {
		UserData.maxScore = maxScore;
	}

	public static int[] getNums() {
		return nums;
	}

	public static void setNums(int[] nums) {
		UserData.nums = nums;
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

				//����
				setStep(dou.readInt());
				setB_show_teach(dou.readBoolean());
				setScore(gr.getScores());
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
		
		for(int i = 0 ;i<achi_list.length;i++){
			achi_list[i][0] = false;
			achi_list[i][1] = false;
			achi_list[i][2] = false;
		}
		for(int i = 0 ;i<achi_show.length;i++){
			achi_list[i][0] = false;
			achi_list[i][1] = false;
			achi_list[i][2] = false;
		}
		for(int i = 0 ;i<nums.length;i++){
			nums[i] = 0;
		}
		
		//����
		setScore(0);
		//����2
		setScore2(0);
		
		//setMaxScore(0);
		
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
		if(maxScore < mScore){
			maxScore = mScore;
		}
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

			//����
			dou.writeInt(getStep());
			dou.writeBoolean(b_show_teach);
			
			printInfo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void printInfo(){
		System.out.println(Scene.COLUMNS*Scene.ROWS);
		//System.out.println(getCurrentSceneData());
		System.out.println(Game.getInstance().getScene().getCol());
		System.out.println(Game.getInstance().getScene().getRow());
		System.out.println(Game.getInstance().getScene().curActor.getType());
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
