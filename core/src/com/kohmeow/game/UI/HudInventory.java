package com.kohmeow.game.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.kohmeow.game.Items.Item;
import com.kohmeow.game.resource.ResourceMannager;

/**
 * 
 */
public class HudInventory {
    private Texture box;
    private Texture border;
    private ResourceMannager rm;
    private BitmapFont font;
    private SpriteBatch batch;
    private OrthographicCamera cam;

    /**
     * 
     * @param batch
     * @param camera
     * @param font_info
     * @param rm
     */
    public HudInventory (SpriteBatch batch, OrthographicCamera camera, BitmapFont font, ResourceMannager rm) {
        this.rm = rm;
        this.cam = camera;

        this.font = font;
        this.batch = batch;


        this.box = rm.getTexture("UI/Box.png");
        this.border = rm.getTexture("UI/Crosshair.gif");
    }

    /**
     * 
     * @param items
     * @param currentItem
     * @param font
     */
    public void draw(Array<Item> items, Item currentItem, BitmapFont font){
        for (int i = 0; i < items.size; i++) {

            batch.draw(box, (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                    cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 10);

            // System.out.println(items.get(i).getName() + " " + items.get(i).getType() + "
            // " + items.get(i).getNum());
            batch.draw(items.get(i).getTextureRegion(), (cam.position.x + 32 * i) -
                    (cam.viewportWidth / 2 * (cam.zoom / 2)),
                    cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 10);

            if (items.get(i).getType() == "plants_product" || items.get(i).getType() == "plants_seed")
                font.draw(batch, String.format("x%d", items.get(i).getNum()),
                        (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2) - 6) + 5,
                        cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 23);
            if (items.get(i).getItem() == currentItem.getItem()) {
                batch.draw(border, (cam.position.x + 32 * i) - (cam.viewportWidth / 2 * (cam.zoom / 2)),
                        cam.position.y - (cam.viewportHeight / 2 * cam.zoom) + 10);
            }

        }
    }
}
