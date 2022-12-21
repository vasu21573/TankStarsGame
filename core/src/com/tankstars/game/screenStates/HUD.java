package com.tankstars.game.screenStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer health1;
    private Integer health2;
    private Label countdownLabel;
    private Label health1Label;
    private Label health2Label;
    private Label timeLabel;
    private Label p1Label;
    private Label p2Label;

    public HUD(SpriteBatch sb) {
        worldTimer = 30;
        timeCount = 0;
        health1 = 100;
        health2 = 100;

        viewport = new FitViewport(1200, 600, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        health1Label = new Label(String.format("%06d", health1), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        health2Label = new Label(String.format("%06d", health2), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p1Label = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2Label = new Label("PLAYER 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(p1Label).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.add(p2Label).expandX().padTop(10);
        table.row();
        table.add(health1Label).expandX();
        table.add(countdownLabel).expandX();
        table.add(health2Label).expandX();

        stage.addActor(table);
    }
}