package com.tvgame.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.tvgame.actor.UserData;

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
			System.out.println("保存游戏失败，原因："+e.getMessage());
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
