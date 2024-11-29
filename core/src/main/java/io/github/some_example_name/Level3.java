package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Level3 implements Screen{
    private static final float BIRD_START_X = 200/30f;
    private static final float BIRD_START_Y = 300/30f;

    private static final float GROUND_Y = 5f;

    public List<Block> blocks;
    public List<Pig> pigs;
    private Destruction destructionHandler;

    private final SpriteBatch batch;
    private final Texture level3;
    private final Texture back;
    private final Texture pause;
    private final Texture replay;
    private final Texture play1;
    private final Texture dummybutton,victoryTexture;;

    private final World world;
    private Queue<Bird> birdQueue;
    private Bird currentBird;
    private boolean isLevelComplete;

    private Window victoryWindow;

    private Block2 Block31;
    private Block2 Block32;
    private Block1 Block33;
    private Block1 Block34;
    private Block2 Block35;
    private Block2 Block36;
    private Block2 Block37;
    private Block1 Block38;
    private Block1 Block39;
    private Block2 Block40;

    private Pig1 Pig4;
    private Pig2 Pig5;
    private Pig2 Pig6;

    private Slingshot slingshot;

    public Level3(SpriteBatch batch){

        this.batch = batch;
        level3 = new Texture((Gdx.files.internal("level3.png")));
        back = new Texture((Gdx.files.internal("back.png")));
        pause = new Texture((Gdx.files.internal("pause.png")));
        play1 = new Texture((Gdx.files.internal("play1.png")));
        replay = new Texture((Gdx.files.internal("replay.png")));
        dummybutton = new Texture((Gdx.files.internal("dummy.png")));
        victoryTexture = new Texture(Gdx.files.internal("victorypage.png"));
        blocks = new ArrayList<>();
        pigs = new ArrayList<>();
        isLevelComplete=false;

        world = new World(new Vector2(0, -9.8f), true);

        BodyDef groundBodyDef = new BodyDef();
        BodyDef wallDef = new BodyDef();

        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        wallDef.type = BodyDef.BodyType.StaticBody;

        groundBodyDef.position.set(0, GROUND_Y+2.3f);
        wallDef.position.set(Gdx.graphics.getWidth() / 30f + 1f, GROUND_Y + 2.3f);

        Body groundBody = world.createBody(groundBodyDef);
        Body verticalWall = world.createBody(wallDef);

        PolygonShape groundBox = new PolygonShape();
        PolygonShape vertical = new PolygonShape();

        groundBox.setAsBox(Gdx.graphics.getWidth() / 30f, 0.2f);
        vertical.setAsBox(2f, Gdx.graphics.getHeight() / 30f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        groundBody.createFixture(fixtureDef);
        groundBox.dispose();

        FixtureDef wallFixture = new FixtureDef();
        wallFixture.shape = vertical;
        wallFixture.friction = 0.3f;
        wallFixture.restitution = 0.1f;
        verticalWall.createFixture(wallFixture);
        vertical.dispose();

        slingshot = new Slingshot(200, 210);

        birdQueue = new LinkedList<>();

        Redbird redBird = new Redbird(200, 300, world);
        redBird.getBody().setUserData(redBird);
        birdQueue.add(redBird);


        Blackbird blackBird = new Blackbird(100, 300, world);
        blackBird.getBody().setUserData(blackBird);
        birdQueue.add(blackBird);


        Bluebird blueBird = new Bluebird(100, 300, world);
        blueBird.getBody().setUserData(blueBird);
        birdQueue.add(blueBird);


        currentBird = birdQueue.poll();



        if (currentBird != null && currentBird.getBody() != null) {
            currentBird.getBody().setTransform(200/30f, 300/30f, 0);
            currentBird.getBody().setLinearVelocity(0, 0);
        }

        Block2 Block31 = new Block2(850, 700, world,30,80);
        Block31.getBody().setUserData(Block31);
        blocks.add(Block31);

        Block2 Block32 = new Block2(1000, 700, world,30,80);
        Block32.getBody().setUserData(Block32);
        blocks.add(Block32);

        Block1 Block33 = new Block1(845, 780, world);
        Block33.getBody().setUserData(Block33);
        blocks.add(Block33);

        Block1 Block34 = new Block1(995, 780, world);
        Block34.getBody().setUserData(Block34);
        blocks.add(Block34);

        Block2 Block35 = new Block2(850, 850, world,30,80);
        Block35.getBody().setUserData(Block35);
        blocks.add(Block35);

        Block2 Block36 = new Block2(1000, 850, world,30,80);
        Block36.getBody().setUserData(Block36);
        blocks.add(Block36);

        Block2 Block37=new Block2(920, 925, world, 220, 20);
        Block37.getBody().setUserData(Block37);
        blocks.add(Block37);

        Block1 Block38 = new Block1(840, 950, world);
        Block38.getBody().setUserData(Block38);
        blocks.add(Block38);

        Block1 Block39 = new Block1(1015, 950, world);
        Block39.getBody().setUserData(Block39);
        blocks.add(Block39);

        Block2 Block40 = new Block2(920, 1000, world,250,20);
        Block40.getBody().setUserData(Block40);
        blocks.add(Block40);


        Pig1 Pig4 = new Pig1(900, 250, world);
        Pig4.getBody().setUserData(Pig4);
        pigs.add(Pig4);

        Pig2 Pig5 = new Pig2(900, 935, world);
        Pig5.getBody().setUserData(Pig5);
        pigs.add(Pig5);



        createVictoryWindow();
        destructionHandler = new Destruction(world, blocks, pigs);
        world.setContactListener(destructionHandler);

    }



    private void handleInput() {
        if (Gdx.input.justTouched() && currentBird != null && !currentBird.isReleased) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            ]
            float forceX = (currentBird.getX() - touchX/175f) * 1.1f;
            float forceY = (currentBird.getY() - touchY/200f) * 1.1f;

            currentBird.launch(forceX, forceY);
        }
    }
    private boolean isBirdInactive(Bird bird) {
        if (bird == null || bird.getBody() == null) return true;

        Body body = bird.getBody();
        Vector2 position = body.getPosition();
        Vector2 velocity = body.getLinearVelocity();


        return position.y < -10 || position.x < -10 || position.x > Gdx.graphics.getWidth();
    }

    public void remove() {
        if (currentBird != null && currentBird.isReleased) {
            Vector2 velocity = currentBird.getBody().getLinearVelocity();


            if (velocity.len2() < 0.07f ||
                currentBird.getX() < -10 ||
                currentBird.getX() > Gdx.graphics.getWidth()/30f ||
                currentBird.getY() < -10) {


                world.destroyBody(currentBird.getBody());
                currentBird = null;  // Clear the reference


                if (!birdQueue.isEmpty()) {
                    currentBird = birdQueue.poll();


                    if (currentBird != null) {

                        currentBird.getBody().setTransform(BIRD_START_X, BIRD_START_Y, 0);
                        currentBird.getBody().setLinearVelocity(0, 0);
                        currentBird.getBody().setAngularVelocity(0);
                        currentBird.isReleased = false;


                        currentBird.getBody().setAwake(true);
                    }
                }
            }
        }
    }

    public void createVictoryWindow(){
        Texture victoryTexture = new Texture("victorypage.png");
        TextureRegionDrawable victoryDrawable = new TextureRegionDrawable(victoryTexture);

        Window.WindowStyle winWindowStyle = new Window.WindowStyle();
        winWindowStyle.titleFont = new BitmapFont();
        winWindowStyle.titleFontColor = Color.WHITE;
        winWindowStyle.background = victoryDrawable;

        victoryWindow = new Window("", winWindowStyle);
        victoryWindow.setVisible(false);

        victoryWindow.setSize(470, 500);
        victoryWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - victoryWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - victoryWindow.getHeight() / 2f
        );
    }
    private void showVictoryPage() {
        if (!isLevelComplete) {
            isLevelComplete = true;

            Gdx.app.log("Victory", "You have won the level!");

            victoryWindow.setVisible(true);
            victoryWindow.toFront();

            batch.begin();
            batch.draw(victoryTexture, 200, 200, 400, 300);
            batch.end();
        }
    }

    @Override
    public void render(float delta){
        world.step(1 / 60f, 6, 2);
        destructionHandler.cleanupDestroyedBodies();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        BitmapFont font = new BitmapFont();
        batch.draw(level3, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(Color.BLACK);
        font.draw(batch, "High speed", 280, 500);
        font.draw(batch, "High speed , more bounce", 150, 300);
        font.draw(batch, "medium speed", 450, 500);
        font.draw(batch, "medium speed, more bounce", 450, 300);
        font.draw(batch, "low speed", 750, 500);
        font.draw(batch, "low speed, more bounce", 750, 300);


        for (Block block : blocks) {
            if (block.getStrength() > 0) {
                block.render(batch);
            }
        }

        for (Pig pig : pigs) {
            if (pig.getHealth() > 0) {
                pig.render(batch);
            }
        }

        if (currentBird != null) {


            if (isBirdInactive(currentBird)) {

                Body oldBody = currentBird.getBody();
                currentBird = birdQueue.poll();

                if (oldBody != null) {
                    world.destroyBody(oldBody);
                }

                if (currentBird != null) {

                    currentBird.getBody().setTransform(200/30f, 300/30f, 0);
                    currentBird.getBody().setLinearVelocity(0, 0);
                }
            } else {
                currentBird.render(batch);
            }
        }

        batch.draw(back, 50, 600, 80, 80);
        batch.draw(pause, 150, 600, 80, 80);
        batch.draw(play1, 245, 590, 95, 95);
        batch.draw(replay, 330, 595, 90, 90);
        batch.draw(dummybutton, 420, 600, 80, 80);



        Slingshot.render(batch);


        batch.end();
        if (!isLevelComplete && pigs.isEmpty()) {

            ((Main) Gdx.app.getApplicationListener()).setScreen(new Victorystatus(batch));

        }
        handleInput();
        remove();

        if(Gdx.input.justTouched()){
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(clickX>=50 && clickX <= (50+80) && clickY>=600 && clickY<=(600+80)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Levels(batch));
            }
            else if(clickX>=330 && clickX<=(330+90) && clickY>=595 && clickY<=(595+90)) {
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level3(batch));
            }
            else if((clickX>=420 && clickX <= (420+80) && clickY>=600 && clickY<=(600+80))){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Victorystatus(batch));
            }
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {

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
        level3.dispose();
        back.dispose();
        pause.dispose();
        replay.dispose();
        play1.dispose();
        dummybutton.dispose();
        victoryTexture.dispose();

        if (currentBird != null) {
            Body body = currentBird.getBody();
            if (body != null) {
                world.destroyBody(body);
            }
        }

        while (!birdQueue.isEmpty()) {
            Redbird bird = (io.github.some_example_name.Redbird) birdQueue.poll();
            if (bird != null && bird.getBody() != null) {
                world.destroyBody(bird.getBody());
            }
        }

        Block31.dispose(world);
        Block32.dispose(world);
        Block33.dispose(world);
        Block34.dispose(world);
        Block35.dispose(world);

        Pig4.dispose(world);
        Pig5.dispose(world);

        world.dispose();
    }
}
