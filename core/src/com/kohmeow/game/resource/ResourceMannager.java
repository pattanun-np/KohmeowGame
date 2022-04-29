package com.kohmeow.game.resource;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;

public class ResourceMannager {
    public AssetManager assetManager;

    private JsonReader jsonReader;

    public Music musicTheme;
    public Sound dirtSfx, waterSfx;

    public ResourceMannager() {
        assetManager = new AssetManager();
        jsonReader = new JsonReader();

        assetManager.load("Items/Items.png", Texture.class);
        assetManager.load("Entity/DirtPatch/DirtPatch.png", Texture.class);
        assetManager.load("Entity/Player/SpireSheet3.png", Texture.class);
        assetManager.load("Entity/Plants/SpriteSheetVeg.png", Texture.class);

        assetManager.load("UI/Box.png", Texture.class);
        assetManager.load("UI/Crosshair.gif", Texture.class);
        assetManager.load("UI/info.png", Texture.class);

        // assetManager.load("Items/grassPatch.png", Texture.class);

        // assetManager.load("Sound/Player/WalkOnGrass.mp3", Sound.class);
        assetManager.load("Sound/sfx/watering.mp3", Sound.class);
        assetManager.load("Sound/sfx/dirt.mp3", Sound.class);

        assetManager.load("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);
        // manager.load("font/small_letters_font.fnt",BitmapFont.class);

        assetManager.finishLoading();

        musicTheme = assetManager.get("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);

        dirtSfx = assetManager.get("Sound/sfx/dirt.mp3", Sound.class);
        waterSfx = assetManager.get("Sound/sfx/watering.mp3", Sound.class);
    }

    /**
     * 
     * @param fpath
     * @return Texture
     *
     */
    public Texture getTexture(String fpath) {
        return assetManager.get(fpath, Texture.class);

    }
    /**
     * 
     * @param fpath
     * @return TextureRegion[][]
     *
     */
    public TextureRegion[][] getTextureRegion(String fpath) {
        TextureRegion[][] textureFrames = TextureRegion.split(getTexture(fpath), 32, 32);

        return textureFrames;
    }

    /**
     * 
     * @param fpath
     * @param w
     * @param h
     * @return TextureRegion[][]
     */
    public TextureRegion[][] getTextureRegion(String fpath, int w, int h) {
        TextureRegion[][] textureFrames = TextureRegion.split(getTexture(fpath), w, h);

        return textureFrames;
    }

    public void dispose() {
        assetManager.dispose();

    }

}
