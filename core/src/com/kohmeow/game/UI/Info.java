package com.kohmeow.game.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kohmeow.game.resource.ResourceMannager;

public class Info {
    private Texture info;
    private ResourceMannager rm;
    private BitmapFont font_info;
    private SpriteBatch batch;
    private OrthographicCamera cam;

    public Info(SpriteBatch batch, OrthographicCamera camera, BitmapFont font_info) {
        this.rm = new ResourceMannager();
        this.cam = camera;
        this.info = rm.getTexture("UI/info.png");
        this.font_info = font_info;
        this.batch = batch;
    }

    public void draw(int currentDays,int totalDays,int money, String time) {
        batch.draw(info, (cam.position.x) - (cam.viewportWidth / 4),
                (cam.position.y) + (cam.viewportHeight / 3 * (cam.zoom / 2) + 20), 230, 70);

        font_info.draw(batch, String.format(" Days: %d/%d", currentDays, totalDays),
                (cam.position.x) - (cam.viewportWidth / 4) + 30,
                (cam.position.y) + 135);

        font_info.draw(batch, String.format("Money: %d $", money),
                (cam.position.x) - (cam.viewportWidth / 4) + 30,
                (cam.position.y) + 110);

        font_info.draw(batch, String.format("Time: %s", time),
                (cam.position.x) - (cam.viewportWidth / 4) + 115,
                (cam.position.y) + 135);
    }
}
