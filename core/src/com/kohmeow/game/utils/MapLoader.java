package com.kohmeow.game.utils;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kohmeow.game.screen.MainScreen;


public class MapLoader {
    private Vector2 playerSpawn;
    private TiledMap map;

    public MapLoader(MainScreen screen) {
        map = screen.getMap();
        playerSpawn = new Vector2();


        for (MapObject object : map.getLayers().get("Player_Spawn").getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            playerSpawn.set(rectangle.x, rectangle.y);
        }


    }

    public Vector2 getPlayerSpawn() {
        return playerSpawn;
    }
}
