package com.kohmeow.game.Inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kohmeow.game.resource.ResourceMannager;

public class Item {
    private TextureRegion textureRegion;

    private Item item;
    private String name;
    private String description;
    private Texture texture;
    private int price;
    

    public Item(ResourceMannager rm,String name, String desc, String type, String filepath){
        this.name = name;
        this.description = desc;
        this.texture = rm.getTexture(filepath);
        
    }
    public Item(ResourceMannager rm,String name, String desc, String type, String filepath, int price){
        this.name = name;
        this.description = desc;
        this.price = price;
        this.texture = rm.getTexture(filepath);
        
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public Item getItem() {
        return item;
    }
    public String getName(){
        return name;
    }
    public String getFullDesc(){
        String ret = "";

        ret += description;

        return ret;
    }

}
