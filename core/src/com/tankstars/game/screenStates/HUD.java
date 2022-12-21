package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    private Label v_1v1;

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

        Image HealthImg = new Image(new Texture(Gdx.files.internal("Rectangle 1.png")));
        Image HealthImg2 = new Image(new Texture(Gdx.files.internal("Rectangle 1.png")));
        Image RecImg = new Image(new Texture(Gdx.files.internal("Rectangle 2.png")));
        Image RecImg2 = new Image(new Texture(Gdx.files.internal("Rectangle 2.png")));
        p1Label = new Label("PLAYER 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        p2Label = new Label("PLAYER 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        v_1v1 = new Label("1v1",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(p1Label).expandX().padTop(10);
        table.add(p2Label).expandX().padTop(10);

        table.row();
        table.add(HealthImg).width(100).height(10);
        table.add(HealthImg2).width(100).height(10);

        table.row();
        table.add(RecImg).width(100).height(10);
        table.add(v_1v1).expandX();
        table.add(RecImg2).width(100).height(10);

        stage.addActor(table);
    }

    public void render() {

    }
}