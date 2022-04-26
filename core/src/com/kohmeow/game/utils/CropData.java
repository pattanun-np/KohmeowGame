package com.kohmeow.game.utils;

import com.kohmeow.game.Entity.Plants.Crop;

public class CropData {
    public String name;
    public float x, y;
    public int growStage;
    

    public CropData(Crop crop) {
    

        this.name = crop.getName();
        this.x = crop.getCropX();
        this.y = crop.getCropY();
        this.growStage = crop.getGrowthStage();

    }

 

}
