package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.MyActor;

import java.util.ArrayList;

public class SelectState extends State{

    private Texture bg;
    private Sprite tank3 = new Sprite(new Texture("Frame 1.png"));
    private Sprite tank2 = new Sprite(new Texture("Helios.png"));
    private Sprite tank1 = new Sprite(new Texture("Toxic.png"));
    private Stage stage;
    private Viewport viewport;
    private BitmapFont font;
    private boolean player1 = false;
    private boolean player2 = false;
    private Texture select;
    private Rectangle rectangle;
    private Texture purple;
    private Texture wep1;
    private Texture wep2;
    private Texture wep3;
    private Texture arrow;
    private Rectangle arrowRegion;
    private ArrayList<Sprite> tankList = new ArrayList<>();
    private int turn = 0;

    public SelectState(GameStateManager gsm) {
        super(gsm);
        tankList.add(tank1);
        tankList.add(tank2);
        tankList.add(tank3);
        bg = new Texture("yellow.png");
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        font = new BitmapFont();
        select = new Texture("select.png");
        rectangle = new Rectangle();
        purple = new Texture("purple.png");
        wep1 = new Texture("wep1.png");
        wep2 = new Texture("wep2.png");
        wep3 = new Texture("wep3.png");
        arrow = new Texture("arrow.png");
        arrowRegion = new Rectangle();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (!player1) {

                if(rectangle.contains(touch)){
                    click.play(0.5f);
                    player1 = true;
                }
            }
            else if (player1 && !player2) {
                if(rectangle.contains(touch)) {
                    player2 = true;
                    click.play(0.5f);
                    gsm.pop();
                    gsm.setCurrentState(null);
                    gsm.game.setScreen(new PlayScreen(gsm));
                }
            } if (arrowRegion.contains(touch)) {
                if (turn == 0) {
                    turn = 1;
                } else if (turn == 1) {
                    turn = 2;
                } else if (turn == 2) {
                    turn = 0;
                }
            }
        }
    }
    @Override
    public void update(float dt) {

    }
    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(bg, 0, 0, 1200, 600);
        font.setColor(Color.WHITE);
        font.getData().setScale(5, 5);
        if (player1) {
            font.draw(batch, "PLAYER 2", tank1.getX() + 100, 550);
        } else {
            font.draw(batch, "PLAYER 1", tank1.getX() + 100, 550);
        }
        batch.draw(select, tank1.getX() + 100, 30, select.getWidth() / 4f, select.getHeight() / 7f + 10);
        batch.draw(purple, 750,0, purple.getWidth(), 600);
        rectangle.setPosition(tank1.getX() + 140, 30);
        rectangle.setSize(select.getWidth() / 4f, select.getHeight() / 7f + 10);
        batch.draw(wep1, 800, 350, wep1.getWidth() / 2f, wep1.getHeight() / 2f);
        batch.draw(wep2, 1000, 350, wep2.getWidth() / 2f, wep2.getHeight() / 2f);
        batch.draw(wep3, 900, 200, wep3.getWidth() / 2f, wep3.getHeight() / 2f);
        batch.draw(arrow, 570, 250, 100, 100);
        arrowRegion.setPosition(570, 250);
        arrowRegion.setSize(100, 100);
        if (turn == 0) {
            batch.draw(tank1, 0, 100, 500, 370);
        } else if (turn == 1) {
            batch.draw(tank2, 30, 20, 500, 570);
        } else if (turn == 2) {
            batch.draw(tank3, 20, 140, 500, 270);
        }
        stage.draw();
        handleInput();
        batch.end();
    }

    @Override
    public void addMyActor(Stage stage, MyActor actor) {
        stage.addActor(actor);
    }

    @Override
    public void dispose() {

        bg.dispose();
        stage.dispose();
        font.dispose();
        select.dispose();
        wep1.dispose();
        wep2.dispose();
        wep3.dispose();
    }
}

