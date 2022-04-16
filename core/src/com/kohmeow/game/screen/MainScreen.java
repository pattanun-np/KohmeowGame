package com.kohmeow.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.sprites.Entity;
import com.kohmeow.game.utils.MapLoader;

public class MainScreen implements Screen {
    private KohMeowGame game; // Instance Var

    private OrthographicCamera cam; /// Define Camera Top down;
    private Viewport gameVeiw;
    private Vector3 tp;


    // Var for tiled map
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;
    private MapLoader loader;
    private int mapWidth;
    private  int mapHeight;
    private MapObject object;


    // Var for player
    private Entity player;
    private TextureRegion currentPlayerFrame;
    private Sprite currentPlayerSprite;
    private  PlayerController controller;
    private ShapeRenderer shapeRenderer;


    // Clock Var
    private GameTimeClock clock;
    private Timer_ timer;
    private  int currentDays;
    




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
