package com.kohmeow.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;


public class Crosshair extends Sprite {
    private float centerX, centerY;

    private TextureRegion currentFrame;
    private TextureRegion[][] textureFrames;
    private Array<TextureRegion> crosshairFrames;
    private Animation<TextureRegion> crosshairAnimation;
    private float frameTime;

    private Texture texture;
    private Sprite frameSprite;

    public Crosshair(float x, float y){

        crosshairFrames = new Array<TextureRegion>(12);

        centerX = x - 16;
        centerY = y - 16;

        

        texture = new Texture("UI/Crosshair2.png");
        textureFrames = TextureRegion.split(texture, 32, 32);

        frameSprite = new Sprite(textureFrames[0][0], 0, 0, 32, 32);
        frameSprite.setX(Math.round(centerX / 32) * 32);
        frameSprite.setY(Math.round(centerY / 32) * 32);

        for (int i = 0; i < 11 ; i++) 
            crosshairFrames.insert(i, textureFrames[0][i]);

        crosshairAnimation =  new Animation<TextureRegion>(.05f, crosshairFrames, Animation.PlayMode.LOOP);
        
      
        currentFrame = crosshairAnimation.getKeyFrame(frameTime);
    }
    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }
    public TextureRegion playAnimation(float delta) {
        return currentFrame = crosshairAnimation.getKeyFrame(frameTime);
    }
    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public void update(float delta) {
        // System.out.println(String.format("Frame Time :%f Delta :%f", frameTime, delta));
      
        frameTime = (frameTime + delta) % 5;

    }
}
