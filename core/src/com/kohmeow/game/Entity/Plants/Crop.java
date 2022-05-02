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
    private String name;
    private boolean isDead;
    private boolean isWatered;
    private float centerX, centerY;
    private Texture texture;
    private Sprite framSprite;
    private TextureRegion currentFrame;
    private TextureRegion[][] textureFrames;
    private Array<TextureRegion> cropFrames;
    public TextureRegion dirtFrame;
    public int ID;

    private ResourceMannager rm;

    private JsonValue cropInfo;
    private JsonReader jsonReader;
    private Sprite frameSprite;
    private String ReturnProduct;
    private int ReturnAmount;

    public Crop(String name, float x, float y,int ID) {

        this.rm = new ResourceMannager();

        this.jsonReader = new JsonReader();

        this.position = new Vector2(x, y);
        this.growthStage = 0;
        this.age = 0;
        this.ID = ID;

        this.isWatered = false;
        this.name = name;

        centerX = x - 16;
        centerY = y - 16;

        cropInfo = jsonReader.parse(Gdx.files.internal("Items/Items.json"));
        textureFrames = rm.getTextureRegion("Entity/Plants/SpriteSheetVeg.png", 32, 64);

        frameSprite = new Sprite(textureFrames[0][0], 0, 0, 32, 32);
        frameSprite.setX(Math.round(centerX / 32) * 32);
        frameSprite.setY(Math.round(centerY / 32) * 32);
 
        loadinfo(name);

    }

    private void loadinfo(String name) {
        cropFrames = new Array<TextureRegion>(4);

        JsonValue info = cropInfo.get("plants_seed");

        growthStageDuration = info.get(name).getInt("growthStageDuration");

        price = info.get(name).getInt("price");

        this.ReturnProduct = info.get(name).getString("productName");
        this.ReturnAmount = info.get(name).getInt("return_amount");



        int y = info.get(name).get("growImage").getInt("y");

        for (int i = 0; i < 4; i++)
            cropFrames.insert(i, textureFrames[y][i]);
        currentFrame = cropFrames.get(0);
        // System.out.println(String.format("%s:%d", name, growthStageDuration));
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
    
        checkGrowth();


        System.out.println(String.format("Crop ID: %d Age: %d", ID, age));


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
        if(isWatered)
            System.out.println(String.format("Crop ID: %d Watered", ID));
        
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public String getName() {
        return name;
    }

    public float getCropX() {
        return position.x;
    }

    public float getCropY() {
        return position.y;
    }

    public String getReturn() {
        return ReturnProduct;
    }

    public int getRetrunAmount() {
        return ReturnAmount;
    }
    public int getID(){
        return ID;
    }
}
