package com.kohmeow.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kohmeow.game.KohMeowGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "KohMeow";
		config.width = 800;
		config.height = 600;
		new LwjglApplication(new KohMeowGame(), config);
	}
}
