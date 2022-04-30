package com.kohmeow.game.Controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.kohmeow.game.KohMeowGame;
import com.kohmeow.game.Entity.Plants.Crop;
import com.kohmeow.game.Entity.Plants.Patch;
import com.kohmeow.game.Entity.Player.Player;
import com.kohmeow.game.resource.ResourceMannager;
import com.kohmeow.game.screen.GameScreen;

import com.kohmeow.game.utils.SaveController;

public class PlayerController implements InputProcessor {

    private Player player;
    private Crop crop;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jump;
    private boolean wateringLeft, wateringRight;
    private boolean digLeft, digRight;

    private boolean toggleInventory;
    private ResourceMannager rm;
    private GameScreen screen;
    private int currentIndex;
    private int tempIndex;
    private SaveController SaveController;
    private Vector3 tp;

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
        wateringLeft = false;
        wateringRight = false;
        digLeft = false;
        digRight = false;

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
     */
    public void createPatch(Vector3 coords) {
        boolean overlaps = false;
        TiledMapTileLayer ground = (TiledMapTileLayer) screen.getMap().getLayers().get("Ground");

        TiledMapTileLayer.Cell cell = ground.getCell(Math.round(coords.x * KohMeowGame.UNIT_SCALE),
                Math.round(coords.y * KohMeowGame.UNIT_SCALE));

        for (int i = 0; i < screen.numPatch; i++) {
            overlaps = screen.patchs.get(i).getFrameSprite().getBoundingRectangle()
                    .overlaps(new Rectangle(coords.x, coords.y, 32 / 2, 32 / 2));
        }
        System.out.println(String.format("Debug: Is Overlaps %b", overlaps));
        if (cell != null && overlaps == false) {

            if (cell.getTile().getId() == 99) {
                patch = new Patch(coords.x, coords.y, "grass");
                screen.addPatch(patch);
                rm.dirtSfx.play(.3f);

                // digLeft = false;
                // digRight = false;
            } else if (cell.getTile().getId() == 110) {
                patch = new Patch(coords.x, coords.y, "dirt");
                screen.addPatch(patch);
                rm.dirtSfx.play(.3f);
                // digLeft = false;
                // digRight = false;
            }

        }

    }

  

    public void waterPatch(Vector3 coords) {
        Patch patch = null;
        for (int i = 0; i < screen.numPatch; i++) {
            if (screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                patch = screen.patchs.get(i).getPatch();
                System.out.println(patch);
                if (patch != null && !patch.getWatered() && (!wateringLeft || !wateringRight)) {
                    patch.setWatered();
                    rm.waterSfx.play();
                }
            }
        }

    }

    /**
     * 
     * @param name
     * @param coords
     */
    public void plant(String name, Vector3 coords) {
        Patch patch = null;
        for (int i = 0; i < screen.numPatch; i++) {
            if (screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                patch = screen.patchs.get(i).getPatch();
                if (patch != null) {
                    if (patch.IsEmpty()) {
                        if (screen.getNumSeed(name) > 0) {
                            Crop crop = new Crop(name, coords.x, coords.y, screen.numCrops);
                            patch.Plant(crop, screen.numCrops);
                            screen.addCrop(crop);
                            screen.removeSeed(name);
                            rm.dirtSfx.play();
                        }

                    }
                }
            }
        }

    }

    public void havest(Vector3 coords) {
        Patch patch = null;

        for (int i = 0; i < screen.numPatch; i++) {
            if (screen.patchs.get(i).getFrameSprite().getBoundingRectangle().contains(coords.x, coords.y)) {
                patch = screen.patchs.get(i).getPatch();
                if (patch != null && !patch.IsEmpty()) {

                    String plantName = patch.getCrop().getName();
                    int plantID = patch.getCropID();
                    int return_amount = patch.getCrop().getRetrunAmount();
                    String return_product = patch.getCrop().getReturn();
                    int growthStage = patch.getCrop().getGrowthStage();

                    if (growthStage == 3) {

                        screen.removeCrop(plantID, return_product, return_amount);
                        screen.addProduct(return_product, return_amount, plantName);
                        patch.unPlant();
                        rm.dirtSfx.play();
                    }

                }
            }
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

        if (Math.abs(Player.getPlayerCenterX() - coords.x) < 100
                && Math.abs(Player.getPlayerCenterY() - coords.y) < 100) {

            if (button == Input.Buttons.LEFT) {
                switch (currentItemType) {
                    case "plants_seed":
                        plant(currentItem, coords);

                        break;
                    case "tools":
                        if (currentItem.equals("WaterPot")) {
                            if (player.getDirection() == Player.Direction.RIGHT) {
                                wateringRight = true;
                                waterPatch(coords);
                            } else if (player.getDirection() == Player.Direction.LEFT) {
                                wateringLeft = true;
                                waterPatch(coords);
                            }

                        }
                        if (currentItem.equals("Shovel")) {
                            if (player.getDirection() == Player.Direction.RIGHT) {
                                digRight = true;
                                createPatch(coords);
                            } else if (player.getDirection() == Player.Direction.LEFT) {
                                digLeft = true;
                                createPatch(coords);
                            }

                        }
                        if (currentItem.equals("Sickle")) {
                            havest(coords);
                        }
                        break;

                    default:
                        break;

                }
                return true;
            }
            

        }

        return false;

    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        wateringLeft = false;
        wateringRight = false;

        digLeft = false;
        digRight = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 coords = screen.getCam().unproject(tp.set(screenX, screenY, 0));

        screen.SetmousePos(coords);

        return false;
    }

    @Override
    public boolean scrolled(float amount, float amount2) {
        // System.out.println(String.format("Scroll: (%f,%f)", amount, amount2));
        // System.out.println("currentIndex: " + screen.getCurrentIndex());
        // System.out.println("tempIndex: " + tempIndex);

        if (amount2 == 1) {

            if (tempIndex < 10) {
                tempIndex += 1;
            } else {
                tempIndex = 0;
            }

        } else if (amount2 == -1) {
            if (tempIndex > 0) {
                tempIndex -= 1;
            } else {
                tempIndex = 10;
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

        } else if (wateringLeft) {
            player.setState(Player.State.WATERING);
            player.setDirection(Player.Direction.WATERING_LEFT, delta);

        } else if (wateringRight) {
            player.setState(Player.State.WATERING);
            player.setDirection(Player.Direction.WATERING_RIGHT, delta);
        } else if (digLeft) {
            player.setState(Player.State.DIGGING);
            player.setDirection(Player.Direction.DIG_LEFT, delta);
        } else if (digRight) {
            player.setState(Player.State.DIGGING);
            player.setDirection(Player.Direction.DIG_RIGHT, delta);

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