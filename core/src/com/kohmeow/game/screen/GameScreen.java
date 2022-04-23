package com.kohmeow.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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
import com.kohmeow.game.utils.Items;
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

    private MapObject object;

    private Viewport viewport;

    // Player
    private Player player;
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

    private Texture bucketTexture, cornTexture, carrotTexture, potatoTexture, artichokeTexture, gourdTexture, pepperTexture, hoeTexture, tomatoTexture, wheatTexture;

    Texture emptyTexture;

    private Items bucket, corn, carrot, potato,empty;

    private Music music;
    private TextureRegion mouseFrame;
    private Texture mouseCrop;
    private BitmapFont font;

    public GameScreen(KohMeowGame game) {
        this.game = game;

        cam = new OrthographicCamera();
        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());

        map = new TmxMapLoader().load("Map/Base.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);

        cam.zoom = .6f;

        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;

        mapLoader = new MapLoader(this);

        player = new Player();

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

        Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        box = new Texture("UI/box.png");
        border = new Texture("UI/border.png");
        bucketTexture = new Texture("Items/bucket.png");
        cornTexture = new Texture("Items/Corn.png");
        carrotTexture = new Texture("Items/Carrot.png");
        potatoTexture = new Texture("Items/Potato.png");
        emptyTexture = new Texture(pixmap,false);

        bucket = new Items(bucketTexture, Items.ItemType.TOOL, Items.Item.BUCKET);
        corn = new Items(cornTexture, Items.ItemType.SEED, Items.Item.CORN);
        carrot = new Items(carrotTexture, Items.ItemType.SEED, Items.Item.CARROT);
        potato = new Items(potatoTexture, Items.ItemType.SEED, Items.Item.POTATO);
  

        int capacity = 9;
        items = new Array<Items>(capacity);

        items.add(bucket);
        items.add(corn);
        items.add(carrot);
        items.add(potato);
        
        setSelectedItem(potato);

        music = KohMeowGame.manager.get("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(.05f);
        // music.play();

    }

    public void setSelectedItem(Items item) {
        System.out.println("setSelectedItem"+item);
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

    public int getTobarSize() {

        return this.items.size;

    }

    public boolean isCollision(Rectangle boundingBox) {
        MapLayer objectLayer = map.getLayers().get("Collision");

        return checkCollision(boundingBox, objectLayer);
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
            cam.position.x = Player.getPlayerCenterX();
        }

        if (currentPlayerSprite.getY() + (cam.viewportHeight / 2 * cam.zoom) < mapHeight
                && currentPlayerSprite.getY() - cam.viewportHeight / 2 * cam.zoom > 0) {
            cam.position.y = Player.getPlayerCenterY();
        }
        if (!isCollision(Player.getBoundingBox()))
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
                if (items.get(i).getType() == Items.ItemType.SEED){}
                    // font.draw(game.batch, String.format("%d", items.get(i).getNum()),
                    // (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2) - 6),
                    // cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 12);
                if (items.get(i).getItem() == currentItem.getItem())
                        game.batch.draw(border, (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                                cam.position.y - (cam.viewportHeight / 2 * cam.zoom));
            }
        }

        game.batch.end();

        Matrix4 mat = cam.combined.cpy();
        mat.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glDisable(GL20.GL_BLEND);
        game.batch.setProjectionMatrix(mat);

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameView.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        music.dispose();
    }
}
