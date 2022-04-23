package com.kohmeow.game.screen;

import java.util.ResourceBundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.Controller.PlayerController;
import com.kohmeow.game.Entity.Player.Player;
import com.kohmeow.game.Entity.Plants.Crop;
import com.kohmeow.game.resource.ResourceMannager;
import com.kohmeow.game.utils.MapLoader;

public class GameScreen extends ScreenAdapter {
    // Instance Var
    private KohMeowGame game;

    private OrthographicCamera cam; // Camera
    private Viewport gameView;
    private Vector3 tp;

    // Tilemap
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer; // Tiledmap Renderer
    // Private MapLoader loader;
    private int mapWidth;
    private int mapHeight;
    private TextureRegion[][] textureFrames;
    private MapObject object;

    private Viewport viewport;

    // Player
    private Player player;
    private Sprite currentPlayerSprite;
    private TextureRegion currentPlayerFrame;

    private PlayerController controller;

    private Stage stage;

    private MapLoader mapLoader;

    private Texture box;
    private Texture border;
    public int numCrops;

    private Music music;
    public Array<Crop> crops;
    private TextureRegion mouseFrame;
    private Texture mouseCrop;
    private BitmapFont font;

    private ResourceMannager rm;

    public Object currentItem;

    public Object currentType;

    public GameScreen(KohMeowGame game) {
        this.game = game;

        cam = new OrthographicCamera();
        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());

        map = new TmxMapLoader().load("Map/Base.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);

        cam.zoom = .5f;

        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;

        mapLoader = new MapLoader(this);

        player = new Player();

        mouseCrop = new Texture("Items/Plants.png");
        textureFrames = TextureRegion.split(mouseCrop, 32, 32);

        crops = new Array<Crop>();

        player.startingPosition(mapLoader.getPlayerSpawnPoint().x, mapLoader.getPlayerSpawnPoint().y);

        currentPlayerSprite = player.getFrameSprite();

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());

        stage = new Stage(viewport, game.batch);

        controller = new PlayerController(this, player);

        rm = new ResourceMannager();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);

        music = rm.musicTheme;
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

    }

    @Override
    public void show() {

    }

    public boolean checkCollision(Rectangle boundingBox, MapLayer objectLayer) {
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                if (boundingBox.overlaps(rectangle)) {
                    System.out.println("Collision");
                    System.out.println(boundingBox.getX() + "," + boundingBox.getY());
                    System.out.println(rectangle.getX() + "," + rectangle.getY());
                    System.out.println("Check is player over stack: " + (boundingBox.getY() > rectangle.getY()));

                    return true;
                }
            }

        }
        return false;
    }

    public boolean isCollision(Rectangle boundingBox) {
        MapLayer objectLayer = map.getLayers().get("Collision");

        return checkCollision(boundingBox, objectLayer);
    }

    @Override
    public void render(float delta) {

        // System.out.println("Player X: " + player.getX() + " Y: " + player.getY());
        System.out.println("Num Crops: " + numCrops);

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        cam.update();
        player.update(delta);

        if (currentPlayerSprite.getX() + (cam.viewportWidth / 2 * cam.zoom) < mapWidth
                && currentPlayerSprite.getX() - (cam.viewportWidth / 2 * cam.zoom) > 0) {
            cam.position.x = Player.getPlayerCenterX();
        }

        if (currentPlayerSprite.getY() + (cam.viewportHeight / 2 * cam.zoom) < mapHeight
                && currentPlayerSprite.getY() - cam.viewportHeight / 2 * cam.zoom > 0) {
            cam.position.y = Player.getPlayerCenterY();
        }
        if (!isCollision(Player.getBoundingBox()))
            player.setCurrentToNext();

        controller.update(delta);

        game.batch.setProjectionMatrix(cam.combined);

        currentPlayerFrame = player.getCurrentFrame();

        renderer.setView(cam);

        game.batch.begin();

        for (int i = 0; i < numCrops; i++) {

            game.batch.draw(textureFrames[0][1], crops.get(i).getFrameSprite().getX(),
                    crops.get(i).getFrameSprite().getY() - 6);

            game.batch.draw(crops.get(i).getCurrentFrame(), crops.get(i).getFrameSprite().getX(),
                    crops.get(i).getFrameSprite().getY());

        }
        game.batch.draw(currentPlayerFrame, currentPlayerSprite.getX(), currentPlayerSprite.getY());

        game.batch.end();

        Matrix4 mat = cam.combined.cpy();
        mat.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glDisable(GL20.GL_BLEND);
        game.batch.setProjectionMatrix(mat);

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public TiledMap getMap() {
        return map;
    }

    public void addCrop(com.kohmeow.game.Entity.Plants.Crop crop) {
        crops.add(crop);
    }

    public Array<Crop> getCrops() {
        return crops;
    }

    @Override
    public void dispose() {
        stage.dispose();
        border.dispose();
        map.dispose();
        box.dispose();
        music.dispose();
    }
}
