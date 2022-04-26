package com.kohmeow.game.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;

import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.Entity.Plants.Crop;
import com.kohmeow.game.Entity.Plants.Patch;
import com.kohmeow.game.Entity.Player.Player;
import com.kohmeow.game.resource.ResourceMannager;
import com.kohmeow.game.screen.GameScreen;
import com.kohmeow.game.utils.Crosshair;
import com.kohmeow.game.utils.SaveController;

public class PlayerController implements InputProcessor {

    private Player player;
    private Crop crop;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jump;
    private boolean toggleInventory;
    private ResourceMannager rm;
    private GameScreen screen;
    private int currentIndex;
    private int tempIndex;
    private SaveController SaveController;
    private Vector3 tp;
    private Crosshair crosshair;
    private Patch patch;
    private TiledMapTileLayer plants_zone;
    private boolean isPatch;

    public PlayerController(GameScreen screen, Player player) {
        this.player = player;
        this.screen = screen;
        tp = new Vector3();
        left = false;
        right = false;
        up = false;
        down = false;
        jump = false;
        toggleInventory = false;
        currentIndex = screen.getCurrentIndex();
        tempIndex = currentIndex;

        rm = new ResourceMannager();

        SaveController = new SaveController();

        plants_zone = (TiledMapTileLayer) screen.getMap().getLayers().get("Plants");

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

        if (keycode == Input.Keys.NUM_1) {
            System.out.println("Select" + screen.getItems().get(0).getName());
            screen.setSelectedItem(screen.getItems().get(0));
        }

        if (keycode == Input.Keys.NUM_2) {
            System.out.println("Select" + screen.getItems().get(1).getName());
            screen.setSelectedItem(screen.getItems().get(1));
        }

        if (keycode == Input.Keys.NUM_3) {
            System.out.println("Select" + screen.getItems().get(2).getName());
            screen.setSelectedItem(screen.getItems().get(2));
        }

        if (keycode == Input.Keys.NUM_4) {
            System.out.println("Select" + screen.getItems().get(3).getName());
            screen.setSelectedItem(screen.getItems().get(3));
        }

        if (keycode == Input.Keys.NUM_5) {
            System.out.println("Select" + screen.getItems().get(4).getName());
            screen.setSelectedItem(screen.getItems().get(4));
        }

        if (keycode == Input.Keys.NUM_6) {
            System.out.println("Select" + screen.getItems().get(5).getName());
            screen.setSelectedItem(screen.getItems().get(5));
        }

        if (keycode == Input.Keys.NUM_7) {
            System.out.println("Select" + screen.getItems().get(6).getName());
            screen.setSelectedItem(screen.getItems().get(6));
        }
        if (keycode == Input.Keys.NUM_8) {
            System.out.println("Select" + screen.getItems().get(7).getName());
            screen.setSelectedItem(screen.getItems().get(7));
        }
        if (keycode == Input.Keys.NUM_9) {
            System.out.println("Select" + screen.getItems().get(8).getName());
            screen.setSelectedItem(screen.getItems().get(8));
        }

        if (keycode == Input.Keys.E) {
            System.out.println("Toggle Inventory");
            this.toggleInventory = true;

        }
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
        if (keycode == Input.Keys.E) {
            System.out.println("Toggle Inventory");
            this.toggleInventory = false;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        switch (character) {
            case 'S':
                screen.GameSave();
                return true;
            default:
                return false;

        }

    }

    /**
     * 
     * @param coords
     * @return isPatch is click area is patch;
     */
    public boolean isClickOnPatch(Vector3 coords) {
        Boolean isClick = false;
        for (int i = 0; i < screen.numPatch; i++) {
            isClick = screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y);

        }

        return isClick;
    }

    /**
     * 
     * @param coords
     */
    public void createPatch(Vector3 coords) {
        TiledMapTileLayer ground = (TiledMapTileLayer) screen.getMap().getLayers().get("Ground");

        TiledMapTileLayer.Cell cell = ground.getCell(Math.round(coords.x * KohMeowGame.UNIT_SCALE),
                Math.round(coords.y * KohMeowGame.UNIT_SCALE));
        

        if(cell != null){
            if(cell.getTile().getId() == 99){
                patch = new Patch(coords.x, coords.y,"grass");
                screen.addPatch(patch);
                rm.dirtSfx.play(.3f);
            }
            if(cell.getTile().getId() == 110){
                patch = new Patch(coords.x, coords.y,"dirt");
                screen.addPatch(patch);
                rm.dirtSfx.play(.3f);
            }
        }

        
    }

    /**
     * @param coords
     */
    public void removePatch(Vector3 coords) {
        System.out.println("Remove Patch");
        screen.removeCrop(IndexOfPatch(coords));
        screen.removePatch(IndexOfPatch(coords));
        rm.dirtSfx.play();
    }

    public int IndexOfPatch(Vector3 coords) {
        for (int i = 0; i < screen.numPatch; i++) {
            patch = screen.patchs.get(i).contains(coords.x, coords.y);
            if (patch != null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @params coords Verctor3
     *
     */
    public void waterPatch(Vector3 coords) {
        for (int i = 0; i < screen.numPatch; i++) {
            if (screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                patch = screen.patchs.get(i).getPatch();
                if (patch != null) {
                    if (!patch.isWatered()) {
                        patch.setWatered();
                        rm.waterSfx.play();

                    }
                }
            }
        }

    }

    /**
     * 
     * @param coords
     */
    public void plant(String name, Vector3 coords) {
        Patch patch = null;
        for (int i = 0; i < screen.numPatch; i++) {
            if (screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                patch = screen.patchs.get(i).getPatch();
                if (patch != null) {
                    if (patch.IsEmpty()) {
                        if(screen.getNumSeed(name)>0){
                            Crop crop = new Crop(name, coords.x, coords.y);
                            patch.Plant(crop);
                            screen.addCrop(crop);
                            screen.removeSeed(name);
                            rm.dirtSfx.play();
                        }
                        
                    }
                }
            }
        }

    }

    /**
     * 
     * @param coords
     * @return
     */
    public boolean IsPlantsabled(Vector3 coords) {
        TiledMapTileLayer ground = (TiledMapTileLayer) screen.getMap().getLayers().get("Ground");

        TiledMapTileLayer.Cell cell = ground.getCell(Math.round(coords.x * KohMeowGame.UNIT_SCALE),
                Math.round(coords.y * KohMeowGame.UNIT_SCALE));
        if (cell != null) {
            // System.out.println("Cell: " + cell.getTile().getId());
            if (cell.getTile().getId() == 99) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 coords = screen.getCam().unproject(tp.set(screenX, screenY, 0));
        String currentItemType = screen.currentItem.getType();
        String currentItem = screen.currentItem.getName();
        TiledMapTileLayer ground = (TiledMapTileLayer) screen.getMap().getLayers().get("Ground");

        TiledMapTileLayer.Cell cell = ground.getCell(Math.round(coords.x * KohMeowGame.UNIT_SCALE),
                Math.round(coords.y * KohMeowGame.UNIT_SCALE));
        
        System.out.println(cell.getTile().getId());

        if (Math.abs(Player.getPlayerCenterX() - coords.x) < 30
                && Math.abs(Player.getPlayerCenterY() - coords.y) < 30) {

            if (button == Input.Buttons.LEFT) {
                switch (currentItemType) {
                    case "plants_seed":
                        plant(currentItem, coords);

                        break;
                    case "tools":
                        if (currentItem.equals("WaterPot")) {
                            waterPatch(coords);

                        }
                        if (currentItem.equals("Shovel")) {

                            if (!isClickOnPatch(coords)) {
                                createPatch(coords);
                            }

                        }
                        break;

                    default:
                        break;

                }
                return true;
            }
            // if (button == Input.Buttons.RIGHT) {
            // switch (currentItem) {
            // case "Shovel":
            // if (isClickOnPatch(coords)) {
            // if (isEmptyPatch(coords)) {
            // if (screen.numPatch > 0 && screen.numCrops > 0) {
            // removePatch(coords);
            // return true;
            // }
            // return false;
            // }
            // }
            // break;

            // default:
            // return false;

            // }

            // }
        }
        // for (int i = 0; i < screen.numCrops; i++) {
        // if
        // (screen.getCrops().get(i).getFrameSprite().getBoundingRectangle().contains(coords.x,
        // coords.y)) {
        // if (screen.currentItem.getName() == "WaterPot") {
        // if (screen.patchs.get(j).isWatered()) {
        // screen.getCrops().get(i).setWatered(true);
        // // rm.waterSfx.play();
        // }

        // }
        // if (screen.getCrops().get(i).getGrowthStage() == 3) {

        // screen.getCrops().removeIndex(i);

        // screen.addMoney(2);

        // screen.numCrops--;

        // return false;
        // }

        // } else {
        // return false;
        // }
        // }

        // System.out.println(tileSet.putTile(id, tile););

        // System.out.println(cell != null);

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
        Vector3 coords = screen.getCam().unproject(tp.set(screenX, screenY, 0));

        if (!isClickOnPatch(coords)) {
            screen.SetmousePos(coords);
        }

        return false;
    }

    @Override
    public boolean scrolled(float amount, float amount2) {
        // System.out.println(String.format("Scroll: (%f,%f)", amount, amount2));
        // System.out.println("currentIndex: " + screen.getCurrentIndex());
        // System.out.println("tempIndex: " + tempIndex);

        if (amount2 == 1) {

            if (tempIndex < 8) {
                tempIndex += 1;
            } else {
                tempIndex = 0;
            }

        } else if (amount2 == -1) {
            if (tempIndex > 0) {
                tempIndex -= 1;
            } else {
                tempIndex = 8;
            }

        }

        currentIndex = tempIndex;

        screen.setCurrentIndex(currentIndex);
        screen.setSelectedItem(screen.getItems().get(currentIndex));
        System.out.println("Select: " + screen.getItems().get(currentIndex).getName());

        return false;
    }

    public void update(float delta) {
        processInput(delta);
    }

    private void processInput(float delta) {
        // System.out.println(" Left: " + left + " Right: " + right + " Up: " + up +
        // "Down: " + down);
        // System.out.println("State: " + player.getState());
        // System.out.println("Direction : " + player.getDirection());
        // System.out.println(String.format("Player: Pos (%d,%d)", player.getX(),
        // player.getY()));
        if (up) {
            player.move(Player.Direction.WALKING_UP, delta);
            player.setState(Player.State.WALKING);
            player.setDirection(Player.Direction.WALKING_UP, delta);
        } else if (down) {
            player.move(Player.Direction.WALKING_DOWN, delta);
            player.setState(Player.State.WALKING);
            player.setDirection(Player.Direction.WALKING_DOWN, delta);
        } else if (right) {
            player.move(Player.Direction.WALKING_RIGHT, delta);
            player.setState(Player.State.WALKING);
            player.setDirection(Player.Direction.WALKING_RIGHT, delta);
        } else if (left) {
            player.move(Player.Direction.WALKING_LEFT, delta);
            player.setState(Player.State.WALKING);
            player.setDirection(Player.Direction.WALKING_LEFT, delta);
        } else if (jump) {
            player.move(Player.Direction.JUMP, delta);
            player.setState(Player.State.IDLE);

        } else if (!up && !down && !left && !right) {
            player.setState(Player.State.IDLE);

            if (player.getDirection() == Player.Direction.WALKING_UP && player.getState() == Player.State.IDLE) {
                // System.out.println("IDLE UP");
                player.setDirection(Player.Direction.UP, delta);

            } else if (player.getDirection() == Player.Direction.WALKING_DOWN
                    && player.getState() == Player.State.IDLE) {
                // System.out.println("IDLE DOWN");
                player.setDirection(Player.Direction.DOWN, delta);

            } else if (player.getDirection() == Player.Direction.WALKING_RIGHT
                    && player.getState() == Player.State.IDLE) {
                // System.out.println("IDLE RIGHT");
                player.setDirection(Player.Direction.RIGHT, delta);

            } else if (player.getDirection() == Player.Direction.WALKING_LEFT
                    && player.getState() == Player.State.IDLE) {
                // System.out.println("IDLE LEFT");
                player.setDirection(Player.Direction.LEFT, delta);
            }
        }

    }
}