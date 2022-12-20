package com.tankstars.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tankstars.game.screenStates.GameStateManager;
import com.tankstars.game.screenStates.MenuState;

import java.io.IOException;

public class TankStars extends Game {

    private GameStateManager gsm;
    private static SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager(this);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.setCurrentState(new MenuState(gsm));
        gsm.push(gsm.getCurrentState());

    }

    @Override
    public void render () {
        if (gsm.getCurrentState() == null) {
            super.render();
        } else {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // wipes the screen clean and redraws
            gsm.update(Gdx.graphics.getDeltaTime());
            try {
                gsm.render(batch);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
