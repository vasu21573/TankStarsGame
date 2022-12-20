package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.MyActor;
import com.tankstars.game.Tank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayState extends State {


    private Texture map;
    private Rectangle saveRegion;
    private Viewport viewport;
    private Stage stage;
    private MyActor bg;
    private MyActor resume;
    private MyActor save;
    private MyActor exit;
    private MyActor sound;
    private boolean set;
    private Tank tank1;
    private Tank tank2;
    private MoveByAction mba;

    public PlayState(GameStateManager gsm, Tank tank1, Tank tank2) {
        super(gsm);
        this.tank1 = tank1;
        this.tank2 = tank2;
        initialise();
    }

    public PlayState(GameStateManager gsm) {
        super(gsm);
        initialise();
    }

    @Override
    public void handleInput() throws IOException {

        if (Gdx.input.justTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (saveRegion.contains(touch)) {

                movePauseScreen(mba, bg);
                movePauseScreen(mba, resume);
                movePauseScreen(mba, exit);
                movePauseScreen(mba, save);
                movePauseScreen(mba, sound);
                set = true;

            } else if (set && Gdx.input.getX() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 70 && 600 - Gdx.input.getY() < 70 + 70) {
                click.play(0.5f);
                gsm.setCurrentState(new HomeState(gsm));
                gsm.push(gsm.getCurrentState());
            } else if (set && Gdx.input.getY() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 400 && 600 - Gdx.input.getY() < 530) {
                click.play(0.5f);
                set = false;

                backPauseScreen(mba, bg);
                backPauseScreen(mba, resume);
                backPauseScreen(mba, exit);
                backPauseScreen(mba, save);
                backPauseScreen(mba, sound);

            } else if (set && Gdx.input.getX() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 170 && 600 - Gdx.input.getY() < 370) {

                File f = new File("saved.txt");
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(tank1);
                oos.writeObject(tank2);
                oos.close();
                fos.close();
                gsm.setCurrentState(new HomeState(gsm));
                gsm.set(gsm.getCurrentState());
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) throws IOException {
        batch.begin();
        batch.draw(map, -50, 0, 1300, 600);
        handleInput();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void addMyActor(Stage stage, MyActor actor) { // new
        stage.addActor(actor);
    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
    }

    public void initialise() {

        map = new Texture("play.jpg");
        saveRegion = new Rectangle(0, 510, 90, 70);
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        bg = new MyActor(-500, 0, 300, 600, "yellow.png");
        resume = new MyActor(-460, 400, 220, 70, "resume.png");
        save = new MyActor(-460, 170, 220, 200, "save.png");
        exit = new MyActor(-460, 70, 220, 70, "exit.png");
        sound = new MyActor(-280, 0, 50, 50, "sound.png");
        set = false;
        addMyActor(stage, bg);
        addMyActor(stage, resume);
        addMyActor(stage, exit);
        addMyActor(stage, save);
        addMyActor(stage, sound);

        Gdx.input.setInputProcessor(stage);
    }

    public void movePauseScreen(MoveByAction mba, MyActor actor) {
        mba = new MoveByAction();
        mba.setAmount(500f, 0f);
        mba.setDuration(0.5f);
        actor.addAction(mba);
    }

    public void backPauseScreen(MoveByAction mba, MyActor actor) {
        mba = new MoveByAction();
        mba.setAmount(-500f, 0f);
        mba.setDuration(0.5f);
        actor.addAction(mba);
    }
}
