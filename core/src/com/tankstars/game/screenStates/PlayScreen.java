package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tankstars.game.MyActor;
import com.tankstars.game.Tank;

public class PlayScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Tank tank;
    private Tank tank2;
    private boolean player1Move;
    private boolean player2move;
    private int move = 0;
    private World weapWorld;
    private World tank2World;
    private boolean turn;
    private Rectangle saveRegion;
    private MoveByAction mba;
    private MyActor bg;
    private MyActor resume;
    private MyActor save;
    private MyActor exit;
    private MyActor sound;
    private Stage stage;
    private boolean set;
    private GameStateManager gsm;
    private Texture pause;
    private Texture gameOver;
    private Texture Exit;
    private Texture h1;
    private Texture h2;

    public PlayScreen(GameStateManager gsm) {
        this.gsm = gsm;
        this.camera = gsm.camera;
        saveRegion = new Rectangle(0, 510, 90, 70);
        pause = new Texture("pause.png");
        gameOver = new Texture("Game_Over_logo.png");
        Exit = new Texture("exit.png");
        h1 = new Texture("Rectangle 1.png");
        h2 = new Texture("Rectangle 2.png");

        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);
        player1Move = true;
        player2move = false;
        turn = true;

        batch = new SpriteBatch();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("TS2_back.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1.5f);
        camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

        world = new World(new Vector2(0, -20f), true);
        weapWorld = new World(new Vector2(0, -30f), true);
        tank2World = new World(new Vector2(0, -20f), true);
        b2dr = new Box2DDebugRenderer();
        tank = new Tank(world, camera, new Vector2(50, 235), weapWorld, "Frame 1.png");
        tank.setMove(true);
        tank2 = new Tank(tank2World, camera, new Vector2(1000, 235), weapWorld, "Frame 2.png");
        tank2.setMove(false);

        mapObject(world);
        mapObject(weapWorld);
        mapObject(tank2World);
//        hud=new HUD(batch);
        bg = new MyActor(-500, 0, 300, 600, "yellow.png");
        resume = new MyActor(-460, 400, 220, 70, "resume.png");
        save = new MyActor(-460, 170, 220, 200, "save.png");
        exit = new MyActor(-460, 70, 220, 70, "exit.png");
        sound = new MyActor(-280, 0, 50, 50, "sound.png");
        set = false;
        addMyActor(stage, bg);
        addMyActor(stage, resume);
        addMyActor(stage, exit);
        addMyActor(stage, save);
        addMyActor(stage, sound);

        Gdx.input.setInputProcessor(stage);
    }

    public void addMyActor(Stage stage, MyActor actor) { // new
        stage.addActor(actor);
    }
    private void mapObject(World world) {
        for (MapObject object : map.getLayers().get(1).getObjects()) {
            Shape shape;

            if (object instanceof PolylineMapObject) {
                shape = createPolyLine((PolylineMapObject) object);
            } else {
                continue;
            }

            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private ChainShape createPolyLine(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVert = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVert.length; i++) {
            worldVert[i] = new Vector2(vertices[i * 2] * 1.5f, vertices[i * 2 + 1] * 1.5f);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVert);
        return cs;
    }

    @Override
    public void show() {

    }

    private void handleInput1() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && move < 5000) {
                world.step(Gdx.graphics.getDeltaTime(), 6, 2);
                tank.getB2body().applyLinearImpulse(new Vector2(1f, 0), tank.getB2body().getWorldCenter(), true);
                if (tank.getB2body().getLinearVelocity().x >= 20f) {
                    world.step(Gdx.graphics.getDeltaTime(), 6, 2);
                    tank.getB2body().setLinearVelocity(20, tank.getB2body().getLinearVelocity().y);
                }
                move += 10;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && move < 5000) {
                world.step(Gdx.graphics.getDeltaTime(), 6, 2);
                tank.getB2body().applyLinearImpulse(new Vector2(-1f, 0), tank.getB2body().getWorldCenter(), true);
                if (tank.getB2body().getLinearVelocity().x <= -20f) {
                    world.step(Gdx.graphics.getDeltaTime(), 6, 2);
                    tank.getB2body().setLinearVelocity(-20f, tank.getB2body().getLinearVelocity().y);
                }
                move += 10;

            } else if (move >= 5000) {
                tank.setMove(false);
                move = 0;
            }
        } if (tank.getWeapon() != null) {
            if (tank.getWeapon().isFinished()) {
                move = 0;
                turn = false;
                tank2.getHealth().hitByWeapon(tank.getWeapon(), tank2);
                tank2.getHealth().hitByWeapon(tank.getWeapon(), tank);
                tank.setWeapon(null);
            }
        }
    }

    private void handleInput2() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            tank2World.step(Gdx.graphics.getDeltaTime(), 6, 2);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && move < 5000) {
                tank2.getB2body().applyLinearImpulse(new Vector2(1f, 0), tank2.getB2body().getWorldCenter(), true);
                if (tank2.getB2body().getLinearVelocity().x >= 20f) {
                    tank2.getB2body().setLinearVelocity(20, tank2.getB2body().getLinearVelocity().y);
                }
                move += 10;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && move < 5000) {
                tank2.getB2body().applyLinearImpulse(new Vector2(-1f, 0), tank2.getB2body().getWorldCenter(), true);
                if (tank2.getB2body().getLinearVelocity().x <= -20f) {
                    tank2.getB2body().setLinearVelocity(-20f, tank2.getB2body().getLinearVelocity().y);
                }
                move += 10;


            } else if (move >= 5000) {
                tank2.setMove(false);
                move = 0;
            }
        } if (tank2.getWeapon() != null) {
            if (tank2.getWeapon().isFinished()) {
                move = 0;
                turn = true;
                tank.getHealth().hitByWeapon(tank2.getWeapon(), tank);
                tank.getHealth().hitByWeapon(tank2.getWeapon(), tank2);
                tank2.setWeapon(null);
            }
        }
    }
    @Override
    public void render(float delta) {
        weapWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
        camera.update();
        mapRenderer.setView(camera);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.render();
        b2drRender();
        batch.begin();
        drawTank(tank, 0);
        drawTank(tank2, 5);
        if (turn) {
            checkSprite(tank);
            tank.handleInput();
            handleInput1();
            tank.operation();
        } else {
            checkSprite(tank2);
            tank2.handleInput();
            handleInput2();
            tank2.operation();
        }
        bringPause();
        batch.draw(pause, 0, 510, 90, 70);
        checkHealth();
        showHealth();
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void drawTank(Tank tank, float y) {

        batch.draw(tank.getTexture(), tank.getB2body().getPosition().x - 10, tank.getB2body().getPosition().y - y, 50, 40);
    }
    public void checkHealth() {
        if (tank.getHealth().getHealth() <= 0 || tank2.getHealth().getHealth() <= 0) {
            batch.draw(gameOver, 200, 150);
            batch.draw(Exit, 400, 70, 220, 70);
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {
                gsm.setCurrentState(new HomeState(gsm));
                gsm.push(gsm.getCurrentState());
            }
        }
    }

    private void b2drRender() {
        b2dr.render(world, camera.combined);
        b2dr.render(weapWorld, camera.combined);
        b2dr.render(tank2World, camera.combined);
    }
    private void checkSprite(Tank tank) {
        if (tank.getWeapon() != null) {
            if (!tank.getWeapon().isFinished())
                batch.draw(tank.getWeapon().getTexture(), tank.getWeapon().getB2body().getPosition().x - 5, tank.getWeapon().getB2body().getPosition().y - 5, 30, 10);
        }
    }
    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        batch.dispose();
        world.dispose();
        map.dispose();
        mapRenderer.dispose();
        b2dr.dispose();
        tank.dispose();
    }

    public void bringPause() {
        if (Gdx.input.justTouched()) {
            Vector2 touch = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (saveRegion.contains(touch)) {

                movePauseScreen(mba, bg);
                movePauseScreen(mba, resume);
                movePauseScreen(mba, exit);
                movePauseScreen(mba, save);
                movePauseScreen(mba, sound);
                set = true;

            } else if (set && Gdx.input.getX() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 70 && 600 - Gdx.input.getY() < 70 + 70) {
                gsm.setCurrentState(new HomeState(gsm));
                gsm.push(gsm.getCurrentState());
            } else if (set && Gdx.input.getY() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 400 && 600 - Gdx.input.getY() < 530) {
                set = false;

                backPauseScreen(mba, bg);
                backPauseScreen(mba, resume);
                backPauseScreen(mba, exit);
                backPauseScreen(mba, save);
                backPauseScreen(mba, sound);

            } else if (set && Gdx.input.getX() > 40 && Gdx.input.getX() < 260 && 600 - Gdx.input.getY() > 170 && 600 - Gdx.input.getY() < 240) {

            }
        }
    }
    public void movePauseScreen(MoveByAction mba, MyActor actor) {
        mba = new MoveByAction();
        mba.setAmount(500f, 0f);
        mba.setDuration(0.5f);
        actor.addAction(mba);
    }
    public void backPauseScreen(MoveByAction mba, MyActor actor) {
        mba = new MoveByAction();
        mba.setAmount(-500f, 0f);
        mba.setDuration(0.5f);
        actor.addAction(mba);
    }

    public void showHealth() {
        batch.draw(h1, 100, 500, 300 * tank.getHealth().getHealth() / 100, 10);
        batch.draw(h2, 800, 500, 300 * tank2.getHealth().getHealth() / 100, 10);
    }
}

