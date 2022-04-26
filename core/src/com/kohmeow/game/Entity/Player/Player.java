package com.kohmeow.game.Entity.Player;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class Player extends Sprite {

    private Vector2 velocity; // Movement Vel
    private Direction currentDirection;

    private Animation<TextureRegion> standLeft;
    private Animation<TextureRegion> standRight;
    private Animation<TextureRegion> standUp;
    private Animation<TextureRegion> standDown;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkDown;

    private Array<TextureRegion> walkLeftFrames;
    private Array<TextureRegion> walkRightFrames;
    private Array<TextureRegion> walkUpFrames;
    private Array<TextureRegion> walkDownFrames;
    private Array<TextureRegion> standLeftFrames;
    private Array<TextureRegion> standRightFrames;
    private Array<TextureRegion> standUpFrames;
    private Array<TextureRegion> standDownFrames;

    private Vector2 nextPosition; // Next cord of entity
    private Vector2 currentPosition; // Current cord of entity

    private State state = State.IDLE; // Normal is idle

    private float frameTime;
    private Sprite frameSprite;
    private TextureRegion currentFrame;

    private Texture texture;

    public static Rectangle boundingBox;



    public enum State {
        IDLE,
        WALKING
    }

    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        WALKING_UP,
        WALKING_RIGHT,
        WALKING_DOWN,
        WALKING_LEFT,
        JUMP

    }

    public Player() {
        this.nextPosition = new Vector2();
        this.currentPosition = new Vector2();
        this.boundingBox = new Rectangle();
        this.velocity = new Vector2(2.5f, 2.5f);

        frameTime = 0f;

        currentDirection = Direction.DOWN;

        texture = new Texture("Entity/Player/SpireSheet2.png");

        loadSprite();
        loadAnimations();

    }

    private void loadSprite() {
        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);
        frameSprite = new Sprite(textureFrames[4][0].getTexture(), 0, 0, 64, 64);
        currentFrame = textureFrames[4][0];
    }

    private void loadAnimations() {

        TextureRegion[][] textureFrames = TextureRegion.split(texture, 64, 64);

        walkDownFrames = new Array<TextureRegion>(8);
        walkUpFrames = new Array<TextureRegion>(8);
        walkLeftFrames = new Array<TextureRegion>(8);
        walkRightFrames = new Array<TextureRegion>(8);

        standDownFrames = new Array<TextureRegion>(8);
        standUpFrames = new Array<TextureRegion>(8);
        standLeftFrames = new Array<TextureRegion>(8);
        standRightFrames = new Array<TextureRegion>(8);

        for (int i = 0; i < 8; i++) {
            walkDownFrames.insert(i, textureFrames[0][i]);

        }
        for (int i = 0; i < 8; i++) {
            walkLeftFrames.insert(i, textureFrames[1][i]);
        }
        for (int i = 0; i < 8; i++) {
            walkRightFrames.insert(i, textureFrames[2][i]);
        }

        for (int i = 0; i < 8; i++) {
            walkUpFrames.insert(i, textureFrames[3][i]);
        }

        for (int i = 0; i < 8; i++) {
            standDownFrames.insert(i, textureFrames[4][i]);
        }
        for (int i = 0; i < 8; i++) {
            standLeftFrames.insert(i, textureFrames[5][i]);
        }
        for (int i = 0; i < 8; i++) {
            standRightFrames.insert(i, textureFrames[6][i]);
        }
        for (int i = 0; i < 8; i++) {
            standUpFrames.insert(i, textureFrames[7][i]);
        }

        walkDown = new Animation<TextureRegion>(.1f, walkDownFrames, Animation.PlayMode.LOOP);
        walkUp = new Animation<TextureRegion>(.1f, walkUpFrames, Animation.PlayMode.LOOP);
        walkLeft = new Animation<TextureRegion>(.1f, walkLeftFrames, Animation.PlayMode.LOOP);
        walkRight = new Animation<TextureRegion>(.1f, walkRightFrames, Animation.PlayMode.LOOP);

        standDown = new Animation<TextureRegion>(.1f, standDownFrames, Animation.PlayMode.LOOP);
        standUp = new Animation<TextureRegion>(.1f, standUpFrames, Animation.PlayMode.LOOP);
        standLeft = new Animation<TextureRegion>(.1f, standLeftFrames, Animation.PlayMode.LOOP);
        standRight = new Animation<TextureRegion>(.1f, standRightFrames, Animation.PlayMode.LOOP);

    }

    public void startingPosition(float x, float y) {
        this.currentPosition.set(x, y);
        this.nextPosition.set(x, y);
    }

    public void update(float delta) {
        // System.out.println(String.format("Frame Time :%f Delta :%f", frameTime, delta));
        if (state == State.WALKING){
            frameTime = (frameTime + delta) % 5;
           
        }

        if (state == State.IDLE)
            frameTime = 0;
        boundingBox.set(nextPosition.x + 20, nextPosition.y, 24, 12); // Set BBOX for range of planting action
    }

    public void setState(State state) {
        this.state = state;
    }

    public Sprite getFrameSprite() {
        return frameSprite;
    }

    public TextureRegion getCurrentFrame() {
        return currentFrame;
    }

    public Direction getDirection() {
        return currentDirection;
    }

    public void setCurrentPosition(float x, float y) {
        frameSprite.setX(x);
        frameSprite.setY(y);
        this.currentPosition.x = x;
        this.currentPosition.y = y;
        this.nextPosition.x = x;
        this.nextPosition.y = y;
    }

    public void setCurrentToNext() {
        setCurrentPosition(nextPosition.x, nextPosition.y);
    }

    public static Rectangle getBoundingBox() {
        return boundingBox;
    }

    public static float getPlayerCenterX() {
        return boundingBox.x + boundingBox.width / 2;
    }

    public static float getPlayerCenterY() {
        return boundingBox.y + boundingBox.height / 2;
    }

    public void move(Direction direction, float delta) {

        float x = currentPosition.x;
        float y = currentPosition.y;
       

        switch (direction) {
            case WALKING_DOWN:
                y -= velocity.y;
                
                break;
            case WALKING_UP:
                y += velocity.y;
               
                break;
            case WALKING_LEFT:
                x -= velocity.x;
            
                break;
            case WALKING_RIGHT:
                x += velocity.x;

                break;
            case JUMP:
                y += velocity.y+2;
                x += velocity.x+2;
               
                y += velocity.y-2;
                x += velocity.x-2;
               
                break;

            default:
                break;
        }
        nextPosition.x = x;
        nextPosition.y = y;

        // System.out.println(x + " " + y);
    }

    public void setDirection(Direction direction, float delta) {

        this.currentDirection = direction;
        // System.out.println("CurrentDirection : " + currentDirection);
        switch (currentDirection) {
            case WALKING_DOWN:
                currentFrame = walkDown.getKeyFrame(frameTime);
                
                break;

            case WALKING_UP:
                currentFrame = walkUp.getKeyFrame(frameTime);
              
                break;

            case WALKING_LEFT:
                currentFrame = walkLeft.getKeyFrame(frameTime);
             
                break;
            case WALKING_RIGHT:
                currentFrame = walkRight.getKeyFrame(frameTime);
                
                break;

            case UP:

                currentFrame = standUp.getKeyFrame(frameTime);
                break;
            case DOWN:

                currentFrame = standDown.getKeyFrame(frameTime);
                break;
            case LEFT:

                currentFrame = standLeft.getKeyFrame(frameTime);
                break;
            case RIGHT:

                currentFrame = standRight.getKeyFrame(frameTime);
                break;
            default:
                break;
        }
    }

    public State getState() {
        return this.state;
    }
    public Player getPlayer(){
        return this;
    }
}
