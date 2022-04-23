package com.kohmeow.game.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


public class ResourceMannager {
    public AssetManager assetManager;

    private JsonReader jsonReader;


    public Music musicTheme;


    public ResourceMannager(){
        assetManager = new AssetManager();
        jsonReader = new JsonReader();

    
        assetManager.load("Items/Carrot.png", Texture.class);
        assetManager.load("Items/Potato.png", Texture.class);
        assetManager.load("Items/Corn.png", Texture.class);
        // assetManager.load("Items/grassPatch.png", Texture.class);

        assetManager.load("Sound/Player/WalkOnGrass.mp3",Sound.class);
        assetManager.load("Sound/Player/PouringWater.mp3",Sound.class);
 
        assetManager.load("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);
        // manager.load("font/small_letters_font.fnt",BitmapFont.class);

        assetManager.finishLoading();

        musicTheme = assetManager.get("music/Leaning On the Everlasting Arms - Zachariah Hickman.mp3", Music.class);

    }
    private void loadItems(){
        JsonValue itemPool = jsonReader.parse(Gdx.files.internal("items/items.json"));
    }

    public Texture getTexture(String fpath){
        return assetManager.get(fpath, Texture.class);
        
    }
    

    public void dispose(){
        assetManager.dispose();
    }
    
    
}
