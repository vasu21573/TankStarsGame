package com.tankstars.game.screenStates;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tankstars.game.MyActor;

import java.io.IOException;

public abstract class State extends ApplicationAdapter {

    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected Sound click;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 600);
        mouse = new Vector3();
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));
    }

    public abstract void handleInput() throws InterruptedException, IOException, ClassNotFoundException;
    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch) throws IOException, ClassNotFoundException;
    public abstract void addMyActor(Stage stage, MyActor actor);
    public abstract void dispose();
}
