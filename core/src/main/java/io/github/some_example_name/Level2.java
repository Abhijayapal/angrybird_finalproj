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


public class Level2 implements Screen{

    private static final float BIRD_START_X = 200/30f;
    private static final float BIRD_START_Y = 300/30f;

    private static final float GROUND_Y = 5.2f;

    public List<Block> blocks;
    public List<Pig> pigs;
    private Destruction destructionHandler;

    private final SpriteBatch batch;
    private final Texture level2;
    private final Texture back;
    private final Texture pause;
    private final Texture replay;
    private final Texture play1;
    private final Texture dummybutton,victoryTexture;

    private final World world;
    private Queue<Bird> birdQueue;
    private Bird currentBird;
    private boolean isLevelComplete;

    private Window victoryWindow;

    private Block2 Block11;
    private Block2 Block21;
    private Block2 Block31;
    private Block2 Block41;
    private Block2 Block51;
    private Block2 Block61;

    private Pig1 Pig2;
    private Pig1 Pig3;

    private Slingshot slingshot;


    public Level2(SpriteBatch batch){
        this.batch = batch;
        level2 = new Texture((Gdx.files.internal("level2.png")));
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

        groundBox.setAsBox(Gdx.graphics.getWidth() / 30f, 0.5f);
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

        slingshot = new Slingshot(200, 230);

        birdQueue = new LinkedList<>();

        Redbird redBird = new Redbird(200, 300, world);
        redBird.getBody().setUserData(redBird);
        birdQueue.add(redBird);



        Blackbird blackBird = new Blackbird(100, 300, world);
        blackBird.getBody().setUserData(blackBird); // Set userData
        birdQueue.add(blackBird);



        Bluebird blueBird = new Bluebird(100, 300, world);
        blueBird.getBody().setUserData(blueBird); // Set userData
        birdQueue.add(blueBird);


        currentBird = birdQueue.poll();



        if (currentBird != null && currentBird.getBody() != null) {
            currentBird.getBody().setTransform(200/30f, 300/30f, 0);
            currentBird.getBody().setLinearVelocity(0, 0);
        }

        Block2 Block11 = new Block2(850, 700, world,20,80);
        Block11.getBody().setUserData(Block11);
        blocks.add(Block11);

        Block2 Block21 = new Block2(950, 700, world,20,80);
        Block21.getBody().setUserData(Block21);
        blocks.add(Block21);

        Block2 Block31 = new Block2(900, 780, world,120,20);
        Block31.getBody().setUserData(Block31);
        blocks.add(Block31);

        Block2 Block41 = new Block2(850, 900, world,20,80);
        Block41.getBody().setUserData(Block41);
        blocks.add(Block41);


        Block2 Block51 = new Block2(950, 900, world,20,80);
        Block51.getBody().setUserData(Block51);
        blocks.add(Block51);

        Block2 Block61 = new Block2(900, 1000, world,120,20);
        Block61.getBody().setUserData(Block61);
        blocks.add(Block61);

        Pig1 Pig2 = new Pig1(900, 250, world);
        Pig2.getBody().setUserData(Pig2);
        pigs.add(Pig2);

        Pig1 Pig3 = new Pig1(900, 950, world);
        Pig3.getBody().setUserData(Pig3);
        pigs.add(Pig3);

        createVictoryWindow();

        destructionHandler = new Destruction(world, blocks, pigs);
        world.setContactListener(destructionHandler);
    }

    private void handleInput() {
        if (Gdx.input.justTouched() && currentBird != null && !currentBird.isReleased) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();


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
        batch.draw(level2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        level2.dispose();
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

        Block11.dispose(world);
        Block21.dispose(world);
        Block31.dispose(world);
        Block41.dispose(world);
        Block51.dispose(world);
        Block61.dispose(world);

        Pig2.dispose(world);
        Pig3.dispose(world);

        world.dispose();
    }

}
