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
	public static Game game;

	public FitCrazyMIDlet() {
		instance = this;
	}

	protected void pauseApp() {

	}

	public void destroyApp(boolean arg0) throws MIDletStateChangeException {
		//exit();
	}

	public void exit() {
		game = null;
		notifyDestroyed();
	}

	public static FitCrazyMIDlet getInstance() {
		return instance;
	}

	protected void startApp() throws MIDletStateChangeException {
		game = new Game(this);
		Display.getDisplay(this).setCurrent(game);
		new Thread(game).start();
	}
}
