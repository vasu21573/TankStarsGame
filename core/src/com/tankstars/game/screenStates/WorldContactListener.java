package com.tankstars.game.screenStates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.tankstars.game.Weapon;


public class WorldContactListener implements ContactListener {

    private Weapon weapon;
    public WorldContactListener(Weapon weapon) {
        this.weapon = weapon;
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
