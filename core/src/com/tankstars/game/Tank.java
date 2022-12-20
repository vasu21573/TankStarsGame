package com.tankstars.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;

public class Tank extends Sprite {

    private World world;
    private OrthographicCamera camera;
    public Texture texture;
    public Body b2body;
    private Health health = new Health(100, 50);
    private Weapon weapon;
    private float fuel;
    Vector2 pos;
    public Weapon weaponShot;

    public Tank(World world, OrthographicCamera camera, Vector2 pos, World weapWorld, String frame) {
        this.camera = camera;
        this.world = world;
        texture = new Texture(frame);
        this.pos = pos;
        defineTank();
        weapon = new Weapon(weapWorld);
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

    public void selectWeapon() {

    }
    public void dispose() {

        texture.dispose();
    }

    public void handleInput() {

//        if (weaponShot == null) {
            Vector2 cursor = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector3 coo = camera.unproject(new Vector3(cursor, 0));
            if (Gdx.input.justTouched()) {
                Vector2 tankPos = new Vector2(b2body.getPosition().x, b2body.getPosition().y);
                double dist = tankPos.dst(new Vector2(coo.x, coo.y));
                double angle = Math.atan2((600 - (coo.y - tankPos.y)), (coo.x - tankPos.x));
                if (coo.y < tankPos.y) {
                    angle = -1 * angle;
                }
                weapon.shoot(b2body, angle, dist);
                weaponShot = weapon;
//            }
        }
    }
}
