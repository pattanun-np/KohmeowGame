package com.kohmeow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.kohmeow.game.Entity.Plants.Crop;

public class SaveController {
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private String filePath;
    private Preferences prefs;
    private String data;
    private Json json;
    public FileHandle file;
    public Array cropList;

    public SaveController() {
        json = new Json();


        filePath = "bin/save.json";
        file = Gdx.files.local(filePath);

        

    }

    public void saveGame(int money, Array<Crop> crops) {
        cropList = new Array<>();
        System.out.println("Saving...");

        
        SaveData saveData = new SaveData();

        saveData.setMoney(money);

        for (int i = 0; i < crops.size; i++){
            cropList.add(new CropData(crops.get(i)));
        }
        saveData.setCrop(cropList);
        json.setOutputType(OutputType.json);

        System.out.println(json.prettyPrint(saveData));
        json.setElementType(SaveData.class, "cropsData", CropData.class);
        
        file.writeString(json.prettyPrint(saveData), false);
        System.out.println("Saved !");
    }

    public static void loadGame() {

    }

}
