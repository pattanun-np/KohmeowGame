package com.kohmeow.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.kohmeow.game.KohMeowGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kohmeow.game.resource.ResourceMannager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ManuScreen implements Screen {

 private KohMeowGame game;
 private Viewport viewPort;
 private Stage mainStage;
 private Stage howtoStage;
 private Skin skin;
 private Texture background;
 private Table table;

 private ResourceMannager rm;
 private Music music;

 public ManuScreen (final KohMeowGame game) {
  this.game = game;
  viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
  mainStage = new Stage(viewPort, game.batch);
  howtoStage = new Stage(viewPort, game.batch);
  Gdx.input.setInputProcessor(mainStage);
  background = new Texture("UI/BG.png");

  rm = new ResourceMannager();
  music = rm.mainMenu;
  music.setLooping(true);
  music.setVolume(.2f);
  music.play();

  Texture startbTexture = new Texture(Gdx.files.internal("UI/bStart.png"));
  Texture startbTexturePressed = new Texture(Gdx.files.internal("UI/bStartPress.png"));
  ImageButton startButton = new ImageButton(
          new TextureRegionDrawable(new TextureRegion(startbTexture)),
          new TextureRegionDrawable(new TextureRegion(startbTexturePressed))
  );

  Texture howtobTexture = new Texture(Gdx.files.internal("UI/bHow2play.png"));
  Texture howtobTexturePressed = new Texture(Gdx.files.internal("UI/bHow2playPress.png"));
  ImageButton howtoButton = new ImageButton(
          new TextureRegionDrawable(new TextureRegion(howtobTexture)),
          new TextureRegionDrawable(new TextureRegion(howtobTexturePressed))
  );

  Texture backbTexture = new Texture(Gdx.files.internal("UI/back.png"));
  Texture backbTexturePressed = new Texture(Gdx.files.internal("UI/backpress.png"));
  ImageButton backButton = new ImageButton(
          new TextureRegionDrawable(new TextureRegion(backbTexture)),
          new TextureRegionDrawable(new TextureRegion(backbTexturePressed))
  );

  startButton.setPosition(396, 286);
  howtoButton.setPosition(214,94);

  final Table table = new Table();
  mainStage.addActor(table);
  table.center();
  table.setFillParent(true);
  table.addActor(startButton);
  table.addActor(howtoButton);

  Texture howtoplayT = new Texture(Gdx.files.internal("UI/Info.png"));
  Image howtoplayinfo = new Image(howtoplayT);
  final Table howtoplay = new Table();
  howtoplayinfo.setPosition(58,62);
  backButton.setPosition(1092,534);
  howtoStage.addActor(howtoplay);
  howtoplay.center();
  howtoplay.setFillParent(true);
  howtoplay.addActor(howtoplayinfo);
  howtoplay.addActor(backButton);
  howtoplay.setVisible(false);

  howtoButton.addListener(new InputListener() {
   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
    table.setVisible(false);
    howtoplay.setVisible(true);
    Gdx.input.setInputProcessor(howtoStage);
    return true;
   }
  });
  backButton.addListener(new InputListener() {
   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
    table.setVisible(true);
    howtoplay.setVisible(false);
    Gdx.input.setInputProcessor(mainStage);

    return true;
   }
  });
  startButton.addListener(new InputListener() {
   public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
    game.setScreen(new GameScreen(game));
    dispose();
    return true;
   }

  });






 }


 @Override
 public void show() {

 }

 @Override
 public void render(float delta) {

  Gdx.gl.glClearColor(0, 0, 0, 1);
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  game.batch.begin();
  game.batch.draw(background, 0, 0, 1280, 720);
  game.batch.end();
  mainStage.act(delta);
  howtoStage.act(delta);
  howtoStage.draw();
  mainStage.draw();
  viewPort.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

 }

 @Override
 public void resize(int width, int height) {

 }

 @Override
 public void pause() {

 }

 @Override
 public void resume() {

 }

 @Override
 public void hide() {

 }

 @Override
 public void dispose() {
  background.dispose();
  mainStage.dispose();
  howtoStage.dispose();
  music.dispose();
 }
}
