package com.tvgame.midlet;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/*
 import com.cgc.jme.lib.main.CommonMain;
 */
import com.tvgame.game.Game;

public class FitCrazyMIDlet extends MIDlet {
	
	private static FitCrazyMIDlet instance;

	public FitCrazyMIDlet() {
		instance = this;
	}

	public static FitCrazyMIDlet getInstance() {
		return instance;
	}

	protected void pauseApp() {}
	
	public void destroyApp(boolean arg0) throws MIDletStateChangeException {}
	
	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(Game.getInstance());
		new Thread(Game.getInstance()).start();
	}
}
