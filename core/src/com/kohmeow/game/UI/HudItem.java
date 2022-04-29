package com.kohmeow.game.UI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kohmeow.game.Items.Item;
import com.kohmeow.game.resource.ResourceMannager;

public class HudItem {

    private int price;
    private Item currentItem;
    private ResourceMannager rm;
    private BitmapFont font;
    private SpriteBatch batch;
    private Sprite currentPlayerSprite;
    private OrthographicCamera cam;

    public HudItem(SpriteBatch batch, OrthographicCamera camera, BitmapFont font, ResourceMannager rm) {
        this.rm = rm;
        this.cam = camera;

        this.font = font;
        this.batch = batch;
        this.rm = rm;

    }

    public void draw(Sprite currentPlayerSprite, Item currentItem, BitmapFont font) {
        // Draw Item On Player
        font.draw(batch, String.format("%s", currentItem.getName()),
                currentPlayerSprite.getX() + 16,
                currentPlayerSprite.getY() + 96);
        batch.draw(currentItem.getTextureRegion(), currentPlayerSprite.getX() + 16,
                currentPlayerSprite.getY() + 64, 24, 24);
    }
}
