package com.kohmeow.game.desktop;

// import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kohmeow.game.KohMeowGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "KohMeow";
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = false;
		config.resizable = false;

		// config.addIcon("icontest.png", Files.FileType.Internal);
		new LwjglApplication(new KohMeowGame(), config);
	}
}
