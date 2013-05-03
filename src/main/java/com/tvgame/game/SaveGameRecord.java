package com.tvgame.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.tvgame.actor.UserData;
import cn.ohyeah.itvgame.model.GameAttainment;
import cn.ohyeah.itvgame.model.GameRecord;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.util.DateUtil;

public class SaveGameRecord {
	
	private Game game;
	private int recordId;
	private int attainmentId;
	
	public static boolean result;   	//是否有游戏记录
	
	public SaveGameRecord(Game g){
		this.game = g;
		Date date = new Date(game.getEngineService().getCurrentTime().getTime());
	    int year = DateUtil.getYear(date);
		int month = DateUtil.getMonth(date);
	    recordId = year*100+(month+1);
	    attainmentId = year*100+(month+1);
	}
	
	/**
	 * 保存成就
	 */
	public void saveAttainment(){
		byte record[];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		ServiceWrapper sw = game.getServiceWrapper();
		try {
			
			//成就数据
			for(int i = 0 ;i<UserData.achi_list.length;i++){
				for(int j=0;j<UserData.achi_list[i].length;j++){
					dout.writeBoolean(UserData.achi_list[i][j]);
				}
			}
			for(int i = 0 ;i<UserData.achi_show.length;i++){
				for(int j=0;j<UserData.achi_show[i].length;j++){
					dout.writeBoolean(UserData.achi_show[i][j]);
				}
			}
			for(int i = 0 ;i<UserData.nums.length;i++){
				dout.writeInt(UserData.nums[i]);
			}
			record = bout.toByteArray();
			GameAttainment ga = sw.readAttainment(attainmentId);
			GameAttainment attainment = new GameAttainment();
			if(ga != null && ga.getScores() >= UserData.getMaxScore()){
				attainment.setScores(ga.getScores());
			}else{
				attainment.setScores(UserData.getMaxScore());
			}
			attainment.setAttainmentId(attainmentId);
			attainment.setRemark("游戏成就");
			attainment.setData(record);
			sw.saveAttainment(attainment);
			UserData.printAchi();
		} catch (Exception e) {
			System.out.println("保存游戏成就失败，原因："+e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					bout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean loadAttainment(){
		ServiceWrapper sw = game.getServiceWrapper();
		GameAttainment ga = sw.readAttainment(attainmentId);
		if(!sw.isServiceSuccessful() || ga==null){
			return false;
		}
		ByteArrayInputStream ous=	new ByteArrayInputStream(ga.getData());
		DataInputStream dou = new DataInputStream(ous);	
		try {
			//成就数据
			for(int i = 0 ;i<UserData.achi_list.length;i++){
				for(int j=0;j<UserData.achi_list[i].length;j++){
					UserData.achi_list[i][j] = dou.readBoolean();
				}
			}
			for(int i = 0 ;i<UserData.achi_show.length;i++){
				for(int j=0;j<UserData.achi_show[i].length;j++){
					UserData.achi_show[i][j] = dou.readBoolean();
				}
			}
			for(int i = 0 ;i<UserData.nums.length;i++){
				UserData.nums[i] = dou.readInt();
			}
			UserData.setMaxScore(ga.getScores());
			System.out.println("maxScore:"+UserData.maxScore);
			UserData.printAchi();
		}catch(Exception e){
			System.out.println("读取游戏成就失败，原因："+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				ous.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					dou.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/*保存游戏记录*/
	public void saveRecord(){
		byte record[];
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		ServiceWrapper sw = game.getServiceWrapper();
		try {
			UserData.buildSavaData(dout);
			record = bout.toByteArray();
			GameRecord gameRecord = new GameRecord();
			gameRecord.setData(record);
			gameRecord.setScores(UserData.getScore());
			//gameRecord.setPlayDuration(StateGame.scores2);
			gameRecord.setRemark("游戏存档");
			gameRecord.setRecordId(recordId);
			sw.saveRecord(gameRecord);
		} catch (Exception e) {
			System.out.println("保存游戏记录失败，原因："+e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				dout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally{
				try {
					bout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean loadRecord(){
		ServiceWrapper sw = game.getServiceWrapper();
		GameRecord gameRecord = sw.readRecord(recordId);
		if(!sw.isServiceSuccessful() || gameRecord==null){
			return result = false;
		}

		UserData.loadData(gameRecord);
		return result = true;
	}

}
