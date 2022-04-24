package com.kohmeow.game.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class ResourceMannager {
    public AssetManager assetManager;

    private JsonReader jsonReader;

    public Music musicTheme;
    public Sound dirtSfx;

    public ResourceMannager() {
        assetManager = new AssetManager();
        jsonReader = new JsonReader();

        assetManager.load("Items/Items.png", Texture.class);        
        

        assetManager.load("UI/Box.png", Texture.class);
        assetManager.load("UI/Crosshair.gif", Texture.class);

        assetManager.load("Items/tools/WaterPot.png", Texture.class);

        // assetManager.load("Items/grassPatch.png", Texture.class);

        assetManager.load("Sound/Player/WalkOnGrass.mp3", Sound.class);
        assetManager.load("Sound/Player/PouringWater.mp3", Sound.class);
        assetManager.load("Sound/dirt.mp3", Sound.class);

        assetManager.load("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);
        // manager.load("font/small_letters_font.fnt",BitmapFont.class);

        assetManager.finishLoading();

        musicTheme = assetManager.get("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);

        dirtSfx = assetManager.get("Sound/dirt.mp3", Sound.class);
    }

    public Texture getTexture(String fpath) {
        return assetManager.get(fpath, Texture.class);

    }
    public TextureRegion[][] getTextureRegion(String fpath) {
        TextureRegion[][] textureFrames = TextureRegion.split(getTexture(fpath), 32, 32);

        return  textureFrames;
    }

    public void dispose() {
        assetManager.dispose();

    }

}
