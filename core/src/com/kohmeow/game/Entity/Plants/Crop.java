package com.kohmeow.game.Entity.Plants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kohmeow.game.resource.ResourceMannager;

public class Crop extends Sprite {

    private Vector2 position;
    private int growthStage;
    private int growthStageDuration;
    private int age;
    private int daysNotWatered;
    private int price;
    private boolean isDead;
    private boolean isWatered;
    private float centerX, centerY;
    private Texture texture;
    private Sprite framSprite;
    private TextureRegion currentFrame;
    private TextureRegion[][] textureFrames;
    private Array<TextureRegion> cropFrames;
    public TextureRegion dirtFrame;

    private ResourceMannager rm;

    private JsonValue cropInfo;
    private JsonReader jsonReader;
    private JsonValue info;
    private Sprite frameSprite;

    public Crop(String name, float x, float y) {
        this.jsonReader = new JsonReader();

        this.position = new Vector2(x, y);
        this.growthStage = 0;
        this.age = 0;
        this.isWatered = false;
        

        centerX = x - 16;
        centerY = y - 16;
        texture = new Texture("Items/Plants2.png");

        textureFrames = TextureRegion.split(texture, 32, 64);
        frameSprite = new Sprite(textureFrames[0][0], 0, 0, 32, 32);
        frameSprite.setX(Math.round(centerX / 32) * 32);
        frameSprite.setY(Math.round(centerY / 32) * 32);

        dirtFrame = textureFrames[5][2];

        loadinfo(name);

    }

    private void loadinfo(String name) {
        cropFrames = new Array<TextureRegion>(5);


        JsonValue cropInfo = jsonReader.parse(Gdx.files.internal("Items/Crop.json"));

        info = cropInfo.get(name);

        this.growthStageDuration = info.getInt("growthStageDuration");
        this.price = info.getInt("price");

       

        for (int i = 0; i < 5; i++)
            cropFrames.insert(i, textureFrames[i][0]);
        currentFrame = cropFrames.get(0);

    }

    public void addDay() {
        if (isWatered) {
            daysNotWatered = 0;
            this.age++;
        }
        if (!isWatered && growthStage != 3) {
            daysNotWatered++;
        }
        if (daysNotWatered == 2) {
            isDead = true;
        }
        this.isWatered = false;
        checkGrowth();

    }

    private void checkGrowth() {
        if (age % growthStageDuration == 0 && growthStage != 3) {
            growthStage++;
            this.currentFrame = cropFrames.get(growthStage);
        }
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public int getPrice() {
        return price;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isWatered() {
        return isWatered;
    }

}
