package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tankstars.game.TankStars;

import java.io.IOException;
import java.util.Stack;

public class GameStateManager {

    public TankStars game;
    private Stack<State> states;
    private State currentState;

    public GameStateManager(TankStars game) {
        this.states = new Stack<>();
        this.game = game;
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State state) {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(Gdx.graphics.getDeltaTime());
    }
    public void render(SpriteBatch batch) throws InterruptedException, IOException, ClassNotFoundException {

        states.peek().render(batch);
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }
}
