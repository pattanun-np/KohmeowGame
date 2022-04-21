package com.kohmeow.game.utils;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.kohmeow.game.crops.Crop;
import com.kohmeow.game.screen.GameScreen;
import com.kohmeow.game.sprites.Entity;


public class PlayerController implements InputProcessor {

    private Entity player;
    private Crop crop;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private GameScreen screen;
    Vector3 tp;


    public PlayerController(GameScreen screen, Entity player) {
        this.player = player;
        this.screen = screen;
        tp = new Vector3();
        left = false;
        right = false;
        up = false;
        down = false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
            this.left = true;
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W)
            this.up = true;
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
            this.down = true;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
            this.right = true;

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A)
            this.left = false;
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W)
            this.up = false;
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S)
            this.down = false;
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D)
            this.right = false;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amount, float amount2) {
//        screen.intType += amount;
//        if (screen.intType > screen.getItems().size - 1)
//            screen.intType = 0;
//        if (screen.intType < 0)
//            screen.intType = screen.getItems().size - 1;
//        screen.setMouseCrop(screen.getItems().get(screen.intType));
        return false;
    }

    public void update(float delta) {
        processInput(delta);
    }

    private void processInput(float delta) {
        if (up) {
            player.move(Entity.Direction.UP, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.UP, delta);
        } else if (down) {
            player.move(Entity.Direction.DOWN, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.DOWN, delta);
        } else if (right) {
            player.move(Entity.Direction.RIGHT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.RIGHT, delta);
        } else if (left) {
            player.move(Entity.Direction.LEFT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.LEFT, delta);
        } else {
            player.setState(Entity.State.IDLE);
            player.setDirection(player.getDirection(), delta);
        }
    }
}