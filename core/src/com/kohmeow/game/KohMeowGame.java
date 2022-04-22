package com.kohmeow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kohmeow.game.screen.GameScreen;

public class KohMeowGame extends Game {


    public static final float UNIT_SCALE = 1/32f;

    public SpriteBatch batch;
    //So, sprite batching is just any system that lets you draw multiple sprites
    // at once and hopefully gain some efficiency from it

    public static AssetManager manager;

    // AssetManager is Class to help
	@Override
	public void create () {
        batch = new SpriteBatch();
        manager = new AssetManager();

        manager.finishLoading();

        setScreen(new GameScreen(this));
	}

    @Override
    public void render(){
        super.render();
    }


	

}
