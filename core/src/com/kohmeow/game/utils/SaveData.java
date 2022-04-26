package com.kohmeow.game.utils;


import com.badlogic.gdx.utils.Array;

public class SaveData {
    private int money;
    private int numsCrops;
    private Array<CropData> cropsData;

    public SaveData() {

    }


    public void setMoney(int amount) {
        this.money = amount;
    }

    public void setCrop(Array<CropData> crops) {
        this.cropsData = crops;
        
        this.numsCrops = crops.size;
    }
}
