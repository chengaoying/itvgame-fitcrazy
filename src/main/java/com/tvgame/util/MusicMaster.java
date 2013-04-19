package com.tvgame.util;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public final class MusicMaster {
	public Player player;

	private VolumeControl vc;

	private byte volume = 100;

	public boolean soundPlaying;

	public String musicURL;

	/**
	 * 设置声音文件，现在只允许mid格式
	 * 
	 * @param musicURL
	 */
	public void setSound(String musicURL) {
		try {
			InputStream is = this.getClass().getResourceAsStream(musicURL);
			this.musicURL = musicURL;
			player = Manager.createPlayer(is, "audio/wav");

			player.realize();
			player.setLoopCount(-1);
			player.prefetch();
			setVolume(volume);

			is.close();
			is = null;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 设置播放次数，-1为永久循环播放
	 * 
	 * @param loopCount
	 */
	public void setLoopCount(int loopCount) {
		if (player != null) {
			player.setLoopCount(loopCount);
		}
	}

	/**
	 * 设置音量0-100
	 * 
	 * @param v
	 */
	public void setVolume(int v) {
		volume = (byte) v;
		if (vc == null) {
			if (player != null) {
				vc = (VolumeControl) player.getControl("VolumeControl");
			}
		}
		if (vc != null)
			vc.setLevel(this.volume);
	}

	/**
	 * 开始播放
	 */
	public void playSound() {
		if (player != null && !soundPlaying) {
			try {
				player.start();
				player.setMediaTime(0);
			} catch (MediaException e1) {
				e1.printStackTrace();
			}
			soundPlaying = true;
		}
	}

	/**
	 * 停止播放
	 */
	public void stopSound() {
		if (player != null && soundPlaying) {
			try {
				player.stop();
			} catch (MediaException e1) {
				e1.printStackTrace();
			}
			soundPlaying = false;
		}
	}

	/**
	 * 释放资源
	 */
	public void close() {
		if (player != null) {
			player.deallocate();
//			if (Tools.allowMusic) {
				player.close();
//			}
			player = null;
		}
		System.gc();
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}
