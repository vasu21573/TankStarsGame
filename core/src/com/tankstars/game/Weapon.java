package com.tankstars.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.Map;

public class Weapon {
    private final World world;
    private Body b2body;
    private Texture texture;
    private boolean finished  = false;
    private Vector2 dropped;

//
//    public Weapon(World world) {
//        this.world = world;
//
//    }
private static Map<World,Weapon> instances=new HashMap<>();

    public static Weapon getInstance(World world){
        World key=world;
        if(!instances.containsKey(key)){
            instances.put(key, new Weapon(world));
        }
        return instances.get(key);
    }

    private Weapon(World world) {

        this.world = world;
    }

    public void shoot(Body body, double angle, double dist) {
        texture  = new Texture("missile.png");
        createWeapon(body);
        b2body.applyLinearImpulse(new Vector2((float) (dist / 2 * Math.cos(angle)), (float) (dist / 2 * Math.sin(angle))) , b2body.getWorldCenter(), true);
        finished = false;
        dropped = new Vector2();
    }

    public void createWeapon(Body body) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(body.getPosition().x + 20, body.getPosition().y + 20);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0f);

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData("Weapon");
    }

    public void render() {

    }

    public void dispose() {

    }

    public World getWorld() {
        return world;
    }

    public Body getB2body() {
        return b2body;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Vector2 getDropped() {
        return dropped;
    }

    public void setDropped(Vector2 dropped) {
        this.dropped = dropped;
    }
}
