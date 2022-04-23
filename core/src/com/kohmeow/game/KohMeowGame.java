package com.kohmeow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.*;
// import com.badlogic.gdx.graphics.g2d.BitmapFont; 
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
        
        System.out.println("GameScreen");
        System.out.println("Loading Assets");
        System.out.println("-------------------------------------");
        manager.load("Sound/Player/WalkOnGrass.mp3",Sound.class);
        manager.load("Sound/Player/PouringWater.mp3",Sound.class);
        manager.load("music/SongForKohMeow.mp3", Music.class);
        manager.load("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);
        // manager.load("font/small_letters_font.fnt",BitmapFont.class);

        manager.finishLoading();

        setScreen(new GameScreen(this));
	}

    @Override
    public void render(){
        super.render();
    }


	

}
