package com.kohmeow.game.Entity.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.kohmeow.game.crops.TextureRegion;
import com.kohmeow.game.utils.Items;

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
    private TextureRegion dirtFrame;

    public Crop(Items.Item item, float x, float y) {
        this.position = new Vector2(x, y);
        this.growthStage = 0;
        this.age = 0;
        this.isWatered = false;

        centerX = x - 16;
        centerY = y - 16;

        loadinfo(item);

    }

    private void loadinfo(Items.Item type) {
        switch (type) {
            case WHEAT:
                this.growthStageDuration = 3;
                this.price = 10;
                break;
            case POTATO:
                this.growthStageDuration = 5;
                this.price = 25;
                break;
            case CARROT:
                this.growthStageDuration = 4;
                this.price = 20;
                break;
            case CORN:
                this.growthStageDuration = 7;
                this.price = 45;
                break;

            default:
                break;
        }

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

    private Sprite getFrameSprite(){
        return framSprite;
    }

    private int getPrice() {
        return price;
    }

    private int getGrowthStage() {
        return growthStage;
    }

    public void setWatered() {
        isWatered = true;
    }
    public boolean isDead() {
        return isDead;
    }
    public boolean isWatered() {
        return isWatered;
    }

}
