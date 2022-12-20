package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tankstars.game.MyActor;

public class MenuState extends State {

    private Texture backgroundTexture;
    private Texture playButton;
    private Rectangle bounds;
    private Sound click;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        backgroundTexture = new Texture("op.png");
        playButton = new Texture("start.png");
        bounds = new Rectangle();
        click = Gdx.audio.newSound(Gdx.files.internal("click.mp3"));

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            if (checkClick(Gdx.input.getX(), Gdx.input.getY(), bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight())) {
                click.play(0.5f);
                gsm.setCurrentState(new HomeState(gsm));
                gsm.set(gsm.getCurrentState());
            }
        }
    }

    @Override
    public void update(float dt) {


    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 1200, 600);
        batch.draw(playButton, 460, 100, playButton.getWidth() / 1.5f, playButton.getHeight() / 2f);
        bounds.set(460, 100, playButton.getWidth() / 1.5f, playButton.getHeight() / 2f);
        handleInput();
        batch.end();
    }

    @Override
    public void addMyActor(Stage stage, MyActor actor) { // new

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        playButton.dispose();
    }
    public boolean checkClick(int Xclick, int Yclick, float x, float y, float width, float height) { // new
        return Xclick > x && Xclick < x + width && 600 - Yclick > y && 600 - Yclick < y + height;
    }
}

