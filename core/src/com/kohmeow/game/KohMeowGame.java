package com.kohmeow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kohmeow.game.screen.MainScreen;

public class KohMeowGame extends Game {

	public static final float UNIT_SCALE = 1/32f;
	public SpriteBatch batch;
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
//		manager.load("KohMeow.wav", Music.class);
		manager.finishLoading();
		setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
