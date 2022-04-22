package com.kohmeow.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kohmeow.game.screen.GameScreen;

public class MapLoader {

    private Vector2 playerSpawnPoint;
    private TiledMap map;


    public MapLoader(GameScreen screen){

        map = screen.getMap();
        playerSpawnPoint = new Vector2();

        for (MapObject object : map.getLayers().get("Player").getObjects()){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            playerSpawnPoint.set(rectangle.x, rectangle.y);
        }
    }
    public  Vector2 getPlayerSpawnPoint(){
        return  playerSpawnPoint;
    }
}
