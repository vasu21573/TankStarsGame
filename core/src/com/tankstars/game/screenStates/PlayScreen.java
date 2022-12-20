package com.tankstars.game.screenStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
    private HUD hud;

    public PlayScreen(GameStateManager gsm) {

        player1Move = true;
        player2move = false;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 600, camera);
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
        tank2 = new Tank(tank2World, camera, new Vector2(1000, 250), weapWorld, "Frame 2.png");

        mapObject(world);
        mapObject(weapWorld);
        mapObject(tank2World);
        hud=new HUD(batch);
        weapWorld.setContactListener(new WorldContactListener(tank));
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

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && player1Move) {
            world.step(Gdx.graphics.getDeltaTime(), 6, 2);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) &&  move < 7000) {
                tank.b2body.applyLinearImpulse(new Vector2(1f, 0), tank.b2body.getWorldCenter(), true);
                if (tank.b2body.getLinearVelocity().x >= 20f) {
                    tank.b2body.setLinearVelocity(20, tank.b2body.getLinearVelocity().y);
                }
                move += 10;
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) &&  move < 7000) {
                tank.b2body.applyLinearImpulse(new Vector2(-1f, 0), tank.b2body.getWorldCenter(), true);
                if (tank.b2body.getLinearVelocity().x <= -20f) {
                    tank.b2body.setLinearVelocity(-20f, tank.b2body.getLinearVelocity().y);
                }
                move += 10;

            } else if (move >= 7000) {
                player1Move = false;
                player2move = true;
                move = 0;
            }
        }
    }

    private void handleInput2() {
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && player2move) {
            tank2World.step(Gdx.graphics.getDeltaTime(), 6, 2);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && move < 7000) {
                tank2.b2body.applyLinearImpulse(new Vector2(1f, 0), tank2.b2body.getWorldCenter(), true);
                if (tank2.b2body.getLinearVelocity().x >= 20f) {
                    tank2.b2body.setLinearVelocity(20, tank2.b2body.getLinearVelocity().y);
                }
                move += 10;
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && move < 7000) {
                tank2.b2body.applyLinearImpulse(new Vector2(-1f, 0), tank2.b2body.getWorldCenter(), true);
                if (tank2.b2body.getLinearVelocity().x <= -20f) {
                    tank2.b2body.setLinearVelocity(-20f, tank2.b2body.getLinearVelocity().y);
                }
                move += 10;

            } else if (move >= 7000) {
                player2move = false;
                player1Move = true;
                move = 0;
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
        b2dr.render(world, camera.combined);
        b2dr.render(weapWorld, camera.combined);
        b2dr.render(tank2World, camera.combined);
        batch.begin();
        batch.draw(tank.texture, tank.b2body.getPosition().x - 10, tank.b2body.getPosition().y, 50, 40);
        batch.draw(tank2.texture, tank2.b2body.getPosition().x - 10, tank2.b2body.getPosition().y, 50, 40);


        if (tank.weaponShot != null) {
            if (!tank.weaponShot.finished)
                batch.draw(tank.weaponShot.texture, tank.weaponShot.b2body.getPosition().x - 5, tank.weaponShot.b2body.getPosition().y - 5, 40, 20);
        }
        batch.end();
        tank.handleInput();
//        tank2.handleInput();
        handleInput1();
        handleInput2();
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
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
}
