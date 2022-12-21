package com.tankstars.game.screenStates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tankstars.game.MyActor;

import java.io.IOException;

public abstract class State extends ApplicationAdapter {

    public OrthographicCamera camera = new OrthographicCamera();;
    protected GameStateManager gsm;
    protected Sound click;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        camera.setToOrtho(false, 1200, 600);
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
        gsm.camera = camera;
    }

    public abstract void handleInput() throws InterruptedException, IOException, ClassNotFoundException;
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch) throws IOException, ClassNotFoundException;
    public abstract void addMyActor(Stage stage, MyActor actor);
    public abstract void dispose();
}
