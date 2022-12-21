package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.tankstars.game.screenStates.WorldContactListener;

public class Tank extends Sprite {

    private World world;
    private OrthographicCamera camera;
    private Texture texture;
    private Body b2body;
    private Health health = new Health(100, 50);
    private Weapon weapon;
    private float fuel;
    private Vector2 pos;
    private World weapworld;
    private SpriteBatch batch;
    private Texture dot;

    public Tank(World world, OrthographicCamera camera, Vector2 pos, World weapWorld, String frame) {
        this.camera = camera;
        dot = new Texture("Ellipse.png");
        this.world = world;
        texture = new Texture(frame);
        this.pos = pos;
        defineTank();
        this.weapworld = weapWorld;
        batch = new SpriteBatch();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void defineTank() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x, pos.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1);

        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef);
    }

    private class Health {

        private float health;
        private float shield;

        public Health(float health, float shield) {
            this.health = health;
            this.shield = shield;
        }

        public void hitByWeapon(Weapon weapon, Vector2 position, Tank tank) {

        }
    }

    public void hitTank(Tank tank) {

    }
    public void dispose() {

        texture.dispose();
        batch.dispose();
    }

    public void handleInput() {

            Vector2 cursor = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector3 coo = camera.unproject(new Vector3(cursor, 0));
            if (Gdx.input.justTouched()) {
                Vector2 tankPos = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
                double dist = tankPos.dst(new Vector2(coo.x, coo.y));
                double angle = Math.atan2((600 - (coo.y - tankPos.y)), (coo.x - tankPos.x));
                if (coo.y < tankPos.y) {
                    angle = -1 * angle;
                }
//                weapon = new Weapon(weapworld);
                weapon = Weapon.getInstance(weapworld);
                weapon.getWorld().setContactListener(new WorldContactListener(weapon));
                weapon.shoot(b2body, angle, dist);
            }
        }

    public Body getB2body() {
        return b2body;
    }

    public Vector2 getPos() {
        return pos;
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    public double getDotHeight(double x, double x0, double x1, double y0, double y1){
        return (x-x0) * (x-x0-2*x1) * ((-1)*(y1)/(x1)) + (y0);
    }

    public void operation(){
        Vector3 vec=camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
        double x0=b2body.getPosition().x;
        double y0=b2body.getPosition().y;
        double x1=vec.x;
        double y1=vec.y;
        double diff=(x1-x0)/10;
        for(double i=x0; i<x1; i+=diff){
            double yd=getDotHeight(i,x0,x1,y0,y1)/1000;
            batch.begin();
            batch.draw(dot, (float)i, (float)(yd+y0), 5, 5);
            batch.end();
        }
    }
}
