package com.tankstars.game.screenStates;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tankstars.game.Tank;


public class WorldContactListener implements ContactListener {

    private Tank tank;
    public WorldContactListener(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void beginContact(Contact contact) {

        tank.weaponShot.finished = true;
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
