package com.tankstars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Weapon {
    public World world;
    public Body b2body;
    public Texture texture;
    public boolean finished  = false;
    public Weapon(World world) {
        this.world = world;

    }

    public void shoot(Body body, double angle, double dist) {
        texture  = new Texture("missile.png");
        createWeapon(body);
        b2body.applyLinearImpulse(new Vector2((float) (dist / 2 * Math.cos(angle)), (float) (dist / 2 * Math.sin(angle))) , b2body.getWorldCenter(), true);
        finished = false;
    }

    public void createWeapon(Body body) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(body.getPosition().x + 20, body.getPosition().y + 20);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.1f);


        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData("Weapon");

    }

    public void render() {

    }

    public void dispose() {

    }
}
