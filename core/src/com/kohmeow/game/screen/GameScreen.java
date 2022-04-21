package com.kohmeow.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.sprites.Entity;
import com.kohmeow.game.utils.MapLoader;
import com.kohmeow.game.utils.PlayerController;

public class GameScreen extends ScreenAdapter {
    // Instance Var
    private KohMeowGame game;

    private OrthographicCamera cam; // Camera
    private Viewport gameView;
    private Vector3 tp;

    //Tilemap
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer; // Tiledmap Renderer
    //Private MapLoader loader;
    private int mapWidth;
    private int mapHeight;

    private MapObject object;

    private Entity player;
    private Sprite currentPlayerSprite;
    private TextureRegion currentPlayerFrame;

    private PlayerController controller;

    private MapLoader mapLoader;


    private BitmapFont font;


    public GameScreen(KohMeowGame game) {
        this.game = game;

        cam = new OrthographicCamera();
        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());

        map = new TmxMapLoader().load("Base.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);

        cam.zoom = 1f;

        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;

        mapLoader = new MapLoader(this);


        player = new Entity();

        player.startingPosition(mapLoader.getPlayerSpawnPoint().x, mapLoader.getPlayerSpawnPoint().y);
        currentPlayerSprite = player.getFrameSprite();

        controller = new PlayerController(this, player);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);

    }


    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (currentPlayerSprite.getX() + (cam.viewportWidth / 2 * cam.zoom) < mapWidth &&
                currentPlayerSprite.getX() - (cam.viewportWidth / 2 * cam.zoom) > 0) {
            cam.position.x = player.getPlayerCenterX();
        }

        if (currentPlayerSprite.getY() + cam.viewportHeight / 2 < mapHeight &&
                currentPlayerSprite.getY() - cam.viewportHeight / 2 > 0) {
            cam.position.y = player.getPlayerCenterY();
        }

        cam.update();
        player.update(delta);

        renderer.render();

        controller.update(delta);

        currentPlayerFrame = player.getCurrentFrame();

        game.batch.setProjectionMatrix(cam.combined);

        renderer.setView(cam);
        game.batch.begin();

        game.batch.draw(currentPlayerFrame, currentPlayerSprite.getX(), currentPlayerSprite.getY());

        game.batch.end();
        //box2DDebugRenderer.render(world, camera.combined.scl());
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        map.dispose();
    }
}


