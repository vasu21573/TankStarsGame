package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.MyActor;
import com.tankstars.game.Tank;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class HomeState extends State{

    private Texture bg;
    private MyActor gameButton;
    private MyActor resumeButton;
    private MyActor exitButton;
    private Texture purple;
    private Stage stage;
    private Viewport viewport;
    private MyActor tank;
    public HomeState(GameStateManager gsm) {
        super(gsm);
        bg = new Texture("bg.png");
        purple = new Texture("purple.png");
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        tank = new MyActor(-600, -10, 600, 600, "Helios.png");
        gameButton = new MyActor(825, 400, 250, 80, "newGame.png");
        resumeButton = new MyActor(825, 250, 250, 80, "resume.png");
        exitButton = new MyActor(825, 100, 250, 80, "quit.png");
        addMyActor(stage, gameButton);
        addMyActor(stage, resumeButton);
        addMyActor(stage, exitButton);
        addMyActor(stage, tank);
        stage.setKeyboardFocus(tank);
        Gdx.input.setInputProcessor(stage);
        MoveByAction mba = new MoveByAction();
        mba.setAmount(700f, 0f);
        mba.setDuration(1f);

        tank.addAction(mba);
    }

    @Override
    public void handleInput() throws IOException, ClassNotFoundException {
        if (Gdx.input.justTouched()) {
            if (checkClick(Gdx.input.getX(), Gdx.input.getY(), gameButton.getX(), gameButton.getY(), gameButton.getWidth(), gameButton.getHeight())) {
                click.play(0.5f);
                gsm.setCurrentState(new SelectState(gsm));
                gsm.set(gsm.getCurrentState());
            }
            if (checkClick(Gdx.input.getX(), Gdx.input.getY(), exitButton.getX(), exitButton.getY(), exitButton.getWidth(), exitButton.getHeight())) {
                Gdx.app.exit();
            } if (checkClick(Gdx.input.getX(), Gdx.input.getY(), resumeButton.getX(), resumeButton.getY(), resumeButton.getWidth(),  resumeButton.getHeight())) {
                click.play(0.5f);
                FileInputStream fis = new FileInputStream("saved.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);
                Tank tank1 = (Tank) ois.readObject();
                Tank tank2 = (Tank) ois.readObject();
                fis.close();
                ois.close();
                gsm.setCurrentState((new PlayState(gsm, tank1, tank2)));
                gsm.set(gsm.getCurrentState());
            }
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) throws IOException, ClassNotFoundException {

        batch.begin();
        batch.draw(bg, 0, 0, 750, 600);
        batch.draw(purple, 750, 0, 600, 1200);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        handleInput();
    }

    @Override
    public void dispose() {

        purple.dispose();
        stage.dispose();
        bg.dispose();
        purple.dispose();
    }

    @Override
    public void addMyActor(Stage stage, MyActor actor) { // new
        stage.addActor(actor);
    }

    public boolean checkClick(int Xclick, int Yclick, float x, float y, float width, float height) { // new
        return Xclick > x && Xclick < x + width && 600 - Yclick > y && 600 - Yclick < y + height;
    }
}

