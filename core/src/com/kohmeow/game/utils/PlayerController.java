package com.kohmeow.game.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
        System.out.println(String.format("Mouse: Pos (%d,%d)", screenX, screenY));
        
        return false;
    }

    @Override
    public boolean scrolled(float amount, float amount2) {
        screen.intType += amount;
        if (screen.intType > screen.getItems().size - 1)
            screen.intType = 0;
        if (screen.intType < 0)
            screen.intType = screen.getItems().size - 1;

        screen.setMouseCrop(screen.getItems().get(screen.intType));
        return false;
    }

    public void update(float delta) {
        processInput(delta);
    }

    private void processInput(float delta) {
        System.out.println(" Left: " + left + " Right: " + right + " Up: " + up + " Down: " + down);
        System.out.println("State: " + player.getState());
        System.out.println("Direction : " + player.getDirection());
        // System.out.println(String.format("Player: Pos
        // (%d,%d)",player.getX(),player.getY()));
        if (up) {
            player.move(Entity.Direction.WALKING_UP, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.WALKING_UP, delta);
        } else if (down) {
            player.move(Entity.Direction.WALKING_DOWN, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.WALKING_DOWN, delta);
        } else if (right) {
            player.move(Entity.Direction.WALKING_RIGHT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.WALKING_RIGHT, delta);
        } else if (left) {
            player.move(Entity.Direction.WALKING_LEFT, delta);
            player.setState(Entity.State.WALKING);
            player.setDirection(Entity.Direction.WALKING_LEFT, delta);

        } else if (!up && !down && !left && !right) {
            player.setState(Entity.State.IDLE);

            if (player.getDirection() == Entity.Direction.WALKING_UP && player.getState() == Entity.State.IDLE) {
                System.out.println("IDLE UP");
                player.setDirection(Entity.Direction.UP, delta);

            } else if (player.getDirection() == Entity.Direction.WALKING_DOWN
                    && player.getState() == Entity.State.IDLE) {
                System.out.println("IDLE DOWN");
                player.setDirection(Entity.Direction.DOWN, delta);

            } else if (player.getDirection() == Entity.Direction.WALKING_RIGHT
                    && player.getState() == Entity.State.IDLE) {
                System.out.println("IDLE RIGHT");
                player.setDirection(Entity.Direction.RIGHT, delta);

            } else if (player.getDirection() == Entity.Direction.WALKING_LEFT
                    && player.getState() == Entity.State.IDLE) {
                System.out.println("IDLE LEFT");
                player.setDirection(Entity.Direction.LEFT, delta);
            }
        }

    }
}