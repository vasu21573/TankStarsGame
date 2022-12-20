package com.tankstars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class MyActor extends Actor {

    private Sprite sprite;

    public MyActor(int x, int y, int width, int height, String image) {

        sprite = new Sprite(new Texture(image));
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }

    public MyActor(String image) {
        sprite = new Sprite(new Texture(image));
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }
    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Sprite getSprite() {
        return sprite;
    }
}

