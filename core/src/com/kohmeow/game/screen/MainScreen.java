package com.kohmeow.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.sprites.Entity;
import com.kohmeow.game.utils.GameTimeClock;
import com.kohmeow.game.utils.MapLoader;
import com.kohmeow.game.utils.PlayerController;
import com.kohmeow.game.utils.Timer_;

public class MainScreen implements Screen {
    private KohMeowGame game; // Instance Var

    private OrthographicCamera cam; /// Define Camera Top down;
    private Viewport gameView;
    private Vector3 tp;


    // Var for tiled map
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;
    private MapLoader loader;
    private int mapWidth;
    private int mapHeight;
    private MapObject object;


    // Var for player
    private Entity player;
    private TextureRegion currentPlayerFrame;
    private Sprite currentPlayerSprite;
    private PlayerController controller;
    private ShapeRenderer shapeRenderer;

    private Viewport viewport;

    private TextureRegion[][] textureFrames;


    private Music music;


    // Clock Var
    private GameTimeClock clock;
    private Timer_ timer;
    private int currentDays;

    public MainScreen(KohMeowGame game) {
        this.game = game;

        cam = new OrthographicCamera();

        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());

        map = new TmxMapLoader().load("Base.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        renderer.setView(cam);

        loader = new MapLoader(this);
        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) *32;

        player = new Entity();
        player.startingPosition(loader.getPlayerSpawn().x, loader.getPlayerSpawn().y);
        currentPlayerSprite = player.getFrameSprite();

        controller = new PlayerController(this, player);
        shapeRenderer = new ShapeRenderer();
        cam.zoom = .5f;

        tp = new Vector3();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);
//
//        music = KohMeowGame.manager.get("KohMeow.wav", Music.class);
//        music.setLooping(true);
//        music.setVolume(.1f);
//        music.play();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (currentPlayerSprite.getX() + (cam.viewportWidth / 2 * cam.zoom) < mapWidth &&
                currentPlayerSprite.getX() - (cam.viewportWidth / 2 * cam.zoom) > 0)
            cam.position.x = player.getPlayerCenterX();

        if (currentPlayerSprite.getY() + cam.viewportHeight / 2 < mapHeight &&
                currentPlayerSprite.getY() - cam.viewportHeight / 2 > 0)
            cam.position.y = player.getPlayerCenterY();

        cam.update();
        player.update(delta);

        renderer.render();

        controller.update(delta);
        game.batch.setProjectionMatrix(cam.combined);
        currentPlayerFrame = player.getCurrentFrame();

        renderer.setView(cam);
        game.batch.begin();

        game.batch.draw(currentPlayerFrame,
                currentPlayerSprite.getX(),
                currentPlayerSprite.getY());
        game.batch.end();
        shapeRenderer.setProjectionMatrix(cam.combined);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

//        Gdx.gl20.glDisable(GL20.GL_BLEND);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


    }


    public TiledMap getMap(){
        return map;
    }

    public OrthographicCamera getCam(){
        return cam;
    }

    public KohMeowGame getGame(){
        return game;
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
         map.dispose();

    }
}
