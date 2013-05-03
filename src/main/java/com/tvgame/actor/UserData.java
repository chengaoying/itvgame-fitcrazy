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
 * 玩家的所有信息
 * 
 * @author xiaobai
 * 
 */
public class UserData {
	
	
	//8-28 增加岩石和小化石 增加难度  
	//取前36个元素，因为是6X6
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
	 * 龙币
	 */
	//public static int mGold = 100;
	/**
	 * 当局游戏的步数
	 */
	public static int mStep = 120;
	
	/**
	 * 积分
	 */
	public static int mScore = 0;
	
	/**
	 * 上一步的积分
	 */
	public static int mScore2 = 0;
	
	/*最高得分*/
	public static int maxScore = 0;
	
	/**
	 * 当前所在场景的ID
	 */
	public static int mSceneId;
	/**
	 * 存放地图数据
	 */
	public static byte mSceneData[];
	
	/**
	 * 道具生成器类型
	 */
	public static byte curActorType;
	
	/**
	 * 是否显示新手教学
	 */
	private static boolean b_show_teach = true;
	
	public static int  curCol;
	public static int  curRow;
	
	/**
	 * 成就数据，合成相应物体的个数，和成就显示对应
	 */
	public static int[] nums = new int[12];
	
	/**
	 * 是否获得相应成就, 顺序对应
	 */
	public static boolean[][] achi_list = {
		/*0-铜牌, 1-银牌, 2-金牌*/
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
	 * 相应的成就是否显示过
	 */
	public static boolean[][] achi_show = {
		/*0-铜牌, 1-银牌, 2-金牌*/
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
		case Actor.TYPE_发芽的种子:
			nums[0]++;
			break;
		case Actor.TYPE_菜头弟弟:
			nums[1]++;
			break;
		case Actor.TYPE_菜头勇士:
			nums[2]++;
			break;
		case Actor.TYPE_蔬果骑兵:
			nums[3]++;
			break;
		case Actor.TYPE_神圣骑兵:
			nums[4]++;
			break;
		case Actor.TYPE_究极骑兵:
			nums[5]++;
			break;
		case Actor.TYPE_08:
			nums[6]++;
			break;
		case Actor.TYPE_09:
			nums[7]++;
			break;
		case Actor.TYPE_小化石:
			nums[8]++;
			break;
		case Actor.TYPE_大化石:
			nums[9]++;
			break;
		case Actor.TYPE_宝箱:
			nums[10]++;
			break;
		case Actor.TYPE_超级宝箱:
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
			System.out.println("获得成就"+i+"情况,依次铜,银,金:" + achi_list[i][0] + ", " + achi_list[i][1] + ", " + achi_list[i][2]);
		}
		System.out.println("---------------------------");
		for(int i=0;i<12;i++){
			System.out.println("成就"+i+"是否显示,依次铜,银,金:" + achi_show[i][0] + ", " + achi_show[i][1] + ", " + achi_show[i][2]);
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
	 * 载入数据
	 */
	public static void loadData(GameRecord gr) {
		//if(data != null&&data.length==3){
			Logger.debug("加载游戏记录");
			try {
				ByteArrayInputStream ous=	new ByteArrayInputStream(gr.getData());
				DataInputStream dou = new DataInputStream(ous);				
				mSceneData  = new byte[dou.readByte()];
				dou.read(mSceneData);
				curCol = dou.readByte();
				curRow = dou.readByte();
				curActorType = dou.readByte();

				//步数
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
	
	//载入重新开始游戏的数据
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
		
		//积分
		setScore(0);
		//积分2
		setScore2(0);
		
		//setMaxScore(0);
		
		//步数
		setStep(120);

		setB_show_teach(true);
	}
	
	/**
	 * 获得金币
	 * 
	 * @param gold
	 */
	/*public static void addGold(int gold) {
		mGold += gold;
	}*/

//	/**
//	 * 获得步数
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
	 * 获取随机会场景
	 * @return
	 */
	public static byte [] getRanSceneData(){
		return defultSceneData[Util.getRandom(0, defultSceneData.length-1)];
	}
	/***
	 * 载入地图数据
	 * @return
	 */
	public static byte [] loadSceneData(){
		if(mSceneData == null){
			mSceneData = defultSceneData[Util.getRandom(0, defultSceneData.length-1)];
		}
		return mSceneData;
	}
	/***
	 * 行和列数据以及带放置的道具类型
	 */
	public static void buildSavaData(DataOutputStream dou){
		try {
			//地图数据
			dou.writeByte(Scene.COLUMNS*Scene.ROWS);
			dou.write(getCurrentSceneData());
			dou.writeByte(Game.getInstance().getScene().getCol());
			dou.writeByte(Game.getInstance().getScene().getRow());
			dou.writeByte(Game.getInstance().getScene().curActor.getType());

			//步数
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
	
	//场景地表数据
	public static byte[] getCurrentSceneData(){
		Node  nodes[] = Game.getInstance().getScene().getNodes();
		//地图数据
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
