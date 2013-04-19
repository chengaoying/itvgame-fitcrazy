package com.tvgame.ui;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

/**
 * 目前用语存放数组
 * @author An Zhiqiang
 * @version 1.0
 */
public class GameSaveTool {
	
	private String rmsName;
	private byte[] _database;

	public GameSaveTool(String name) {
		rmsName = name;
	}
	static private byte[] fourbytes;
	static public int readInt(InputStream is) throws IOException {
		is.read(fourbytes);
		return readInt(fourbytes, 0, 4);
	}

	static public int readInt(byte[] b, int off, int num) {
		int res = 0;
		for (int i = num - 1; i >= 0; i--) {
			res <<= 8;
			if (i == num - 1) {
				res |= b[i + off];
			} else {
				res |= b[i + off] & 0x00ff;
			}
		}
		return res;
	}
	public int[] readRecord() {
		if(_database == null){
			return null;
		}
		int[] record = new int[_database.length/4];
		for(int i = 0;i<_database.length;i+=4){
			record[i/4] = readInt(_database, i, 4);
		}
		return record;
	}
	public int readRecord(int id) {
		if(_database == null){
			return 0;
		}
		return readInt(_database, id, 4);
	}
	public void writeRecord(int[] data) {
		_database = new byte[data.length*4];
		for(int i = 0; i < _database.length; i+=4){
			_database[i] = (byte) ((data[i/4] & 0x0FF));
			_database[i + 1] = (byte) ((data[i/4] >>> 8) & 0x0FF);
			_database[i + 2] = (byte) ((data[i/4] >>> 16) & 0x0FF);
			_database[i + 3] = (byte) ((data[i/4] >>> 24) & 0x0FF);
		}
	}
	
	static public final int C_OPERATOR_READ = 0;
	static public final int C_OPERATOR_WRITE = 1;
	static public final int C_OPERATOR_CLEAR = 2;
	static public final int C_OPERATOR_DELETE = 3;
	public void loadGameReocrd() {
		manageRecordDoc(C_OPERATOR_READ);
	}

	public void saveGameRecord() {
		manageRecordDoc(C_OPERATOR_WRITE);
	}

	public void clearGameRecord() {
		manageRecordDoc(C_OPERATOR_CLEAR);
	}
	
	public void deleteGameRecord(){
		manageRecordDoc(C_OPERATOR_DELETE);
	}
	
	public boolean isHaveRecord(){
		try {
			RecordStore recStore = RecordStore.openRecordStore(
					rmsName, true);
			RecordEnumeration recEnum = recStore.enumerateRecords(null, null,
					false);
			return recEnum.hasNextElement();
		} catch (RecordStoreFullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecordStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void manageRecordDoc(int op) {
//		if(op==C_OPERATOR_READ){
//			if(_database==null){
//				return;
//			}
//		}
		
		try {
			if (op == C_OPERATOR_CLEAR) {
				for (int j = 0; j < _database.length; j++) {
						_database[j] = 0;
				}
				op = C_OPERATOR_WRITE;
			}

			RecordStore recStore = RecordStore.openRecordStore(
					rmsName, true);
			RecordEnumeration recEnum = recStore.enumerateRecords(null, null,
					false);

			if (recEnum.hasNextElement()) {
				int recID = recEnum.nextRecordId();
//
				if (op == C_OPERATOR_READ) {
					_database = recStore.getRecord(recID);
				} else if (op == C_OPERATOR_WRITE) {
					recStore.deleteRecord(recID);
//					recStore.setRecord(recID, _database, 0, _databaseSize);
					recStore.addRecord(_database, 0, _database.length);
				}else if(op == C_OPERATOR_DELETE){
					recStore.deleteRecord(recID);
				}
			} 
			else {
				if(op == C_OPERATOR_WRITE)
					recStore.addRecord(_database, 0, _database.length);
			}
			recEnum.destroy();
			recStore.closeRecordStore();
			recStore = null;
			recEnum = null;

		} catch (Exception e) {
		}
	}
}
