package com.tvgame.actor;

public final class Logger {
	public static boolean DEBUG = true;
	public static void debug(String msg){
		if(DEBUG){
			System.out.println(msg);
		}
	}
}
