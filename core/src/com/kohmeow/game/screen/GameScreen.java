package com.kohmeow.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.sprites.Entity;
import com.kohmeow.game.utils.Items;
import com.kohmeow.game.utils.MapLoader;
import com.kohmeow.game.utils.PlayerController;

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

    private MapObject object;

    private Box2DDebugRenderer box2DDebugRenderer;

    private Viewport viewport;

    // Player
    private Entity player;
    private Sprite currentPlayerSprite;
    private TextureRegion currentPlayerFrame;

    private PlayerController controller;

    private Stage stage;


    private MapLoader mapLoader;

    private Items.ItemType currentType;
    private Items currentItem;
    public int intType;

    private Array<Items> items;

    private Texture box;
    private Texture border;

    private Texture bucketTexture;

    private Items bucket;
    private Music music;
    private TextureRegion mouseFrame;
    private Texture mouseCrop;
    private BitmapFont font;

    public GameScreen(KohMeowGame game) {
        this.game = game;

        cam = new OrthographicCamera();
        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());

        map = new TmxMapLoader().load("Base.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);

        cam.zoom = .5f;

        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;

        mapLoader = new MapLoader(this);

        player = new Entity();

        player.startingPosition(mapLoader.getPlayerSpawnPoint().x, mapLoader.getPlayerSpawnPoint().y);
        currentPlayerSprite = player.getFrameSprite();

        items = new Array<Items>(9);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        controller = new PlayerController(this, player);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);
        intType = 0;

        box = new Texture("box.png");
        border = new Texture("border.png");
        bucketTexture = new Texture("bucket.png");
        bucket = new Items(bucketTexture, Items.ItemType.TOOL, Items.Item.BUCKET);

        setMouseCrop(bucket);
        music = KohMeowGame.manager.get("SongForKohMeow.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(.1f);
        music.play();

    }

    public void setMouseCrop(Items item) {
        mouseFrame = item.getTextureRegion();
        currentItem = item;
        currentType = item.getType();

    }

    @Override
    public void show() {

    }

    public Array<Items> getItems() {
        return items;
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        player.update(delta);

        System.out.println(cam.position.x + " " + cam.position.y);

        if (currentPlayerSprite.getX() + (cam.viewportWidth / 2 * cam.zoom) < mapWidth
                && currentPlayerSprite.getX() - (cam.viewportWidth / 2 * cam.zoom) > 0) {
            cam.position.x = Entity.getPlayerCenterX();
        }

        if (currentPlayerSprite.getY() + (cam.viewportHeight / 2 * cam.zoom) < mapHeight
                && currentPlayerSprite.getY() - cam.viewportHeight / 2 * cam.zoom > 0) {
            cam.position.y = Entity.getPlayerCenterY();
        }

        player.setCurrentToNext();

        renderer.render();

        controller.update(delta);

        game.batch.setProjectionMatrix(cam.combined);

        currentPlayerFrame = player.getCurrentFrame();

        renderer.setView(cam);

        game.batch.begin();

        game.batch.draw(currentPlayerFrame, currentPlayerSprite.getX(), currentPlayerSprite.getY());

        for (int i = 0; i < 9; i++) {
             game.batch.draw(box, (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                     cam.position.y - (cam.viewportHeight / 2 * cam.zoom));
             if (i < items.size) {
                 game.batch.draw(items.get(i).getTextureRegion(),
                         (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                         cam.position.y - (cam.viewportHeight / 2 * cam.zoom));
                 if (items.get(i).getType() == Items.ItemType.SEED)
                     font.draw(game.batch, String.format("%d", items.get(i).getNum()),
                         (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2) - 6),
                             cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 12);
                 if (items.get(i).getItem() == currentItem.getItem())
                     game.batch.draw(border, (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                             cam.position.y - (cam.viewportHeight / 2 * cam.zoom));
             }
         }
            
        game.batch.end();
      
    }

    public TiledMap getMap() {
        return map;
    }

    @Override
    public void dispose() {
        stage.dispose();
        border.dispose();
        map.dispose();
        box.dispose();
    }
}
