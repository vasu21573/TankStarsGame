package com.tankstars.game.screenStates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tankstars.game.Weapon;

import java.util.HashMap;
import java.util.Map;


public class WorldContactListener implements ContactListener {

    private Weapon weapon;
    private static Map<Weapon, WorldContactListener> instance = new HashMap<>();
//    public WorldContactListener(Weapon weapon) {
//        this.weapon = weapon;
//    }
    private WorldContactListener(Weapon weapon) {
    this.weapon = weapon;
}
    public static WorldContactListener getInstance(Weapon weapon) {
        Weapon key = weapon;
        if (!instance.containsKey(key)) {
            instance.put(key, new WorldContactListener(weapon));
        }
        return instance.get(key);
    }

    @Override
    public void beginContact(Contact contact) {

        weapon.setFinished(true);
        weapon.setDropped(new Vector2(weapon.getB2body().getPosition().x, weapon.getB2body().getPosition().y));
        weapon.getB2body().setGravityScale(0);
        weapon.getB2body().applyLinearImpulse(new Vector2(0, 1000f), weapon.getB2body().getWorldCenter(), false);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
