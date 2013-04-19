package com.tvgame.ui;

import javax.microedition.lcdui.Graphics;

import cn.ohyeah.stb.key.KeyState;


public interface UiObject {
	 void draw(Graphics g);
	 void update(KeyState key);
}
