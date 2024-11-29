package io.github.some_example_name;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Level1 implements Screen {
    //coordinates or starting position in game world
    //divided by 30->common in box2d
    private static final float BIRD_START_X = 200/30f;  // Starting X position in world coordinates
    private static final float BIRD_START_Y = 300/30f;  // Starting Y position in world coordinates


    //one unit above the bottom
    private static final float GROUND_Y = 1f;

    public List<Block> blocks;
    public List<Pig> pigs;
    private Destruction destructionHandler;

    //for rendering
    //texture->images
    private final SpriteBatch batch;
    private final Texture level1;
    private final Texture back;
    private final Texture pause;
    private final Texture replay;
    private final Texture play1;
    private final Texture dummybutton, victoryTexture;



    //box2d physics world
    //currentbird->the bird we are currently playing with
    private final World world;
    private Queue<Bird> birdQueue;
    private Bird currentBird;
    private boolean isLevelComplete;

    private Window victoryWindow;


    //private Redbird Redbird;

    //    represent five instances of a class named Block1
    private Block1 Block1;
    private Block1 Block2;
    private Block1 Block3;
    private Block1 Block4;
    private Block1 Block5;

    private Pig1 Pig1;

    private Slingshot slingshot;

    public Level1(SpriteBatch batch){

        this.batch = batch;
        level1 = new Texture(Gdx.files.internal("level1.png"));
        back = new Texture(Gdx.files.internal("back.png"));
        pause = new Texture(Gdx.files.internal("pause.png"));
        play1 = new Texture(Gdx.files.internal("play1.png"));
        replay = new Texture(Gdx.files.internal("replay.png"));
        dummybutton = new Texture(Gdx.files.internal("dummy.png"));
        victoryTexture = new Texture(Gdx.files.internal("victorypage.png"));
        blocks = new ArrayList<>();
        pigs = new ArrayList<>();
        isLevelComplete=false;

        // Realistic Earth gravity -> -9.8
        world = new World(new Vector2(0, -9.8f), true);

        // Ground and vertical wall setup
        BodyDef groundBodyDef = new BodyDef();
        BodyDef wallDef = new BodyDef();

        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        wallDef.type = BodyDef.BodyType.StaticBody;

        // Position the ground body at GROUND_Y + 2.3 units above the bottom of the world.
        groundBodyDef.position.set(0, GROUND_Y + 2.3f);
        wallDef.position.set(Gdx.graphics.getWidth() / 30f + 1f, GROUND_Y + 2.3f); // Adjust X for the vertical wall

        // Create the bodies for ground and wall
        Body groundBody = world.createBody(groundBodyDef);
        Body verticalWall = world.createBody(wallDef);

        // Ground shape (box) and vertical wall shape
        PolygonShape groundBox = new PolygonShape();
        PolygonShape vertical = new PolygonShape();

        // Define the size of the ground and vertical wall shapes
        groundBox.setAsBox(Gdx.graphics.getWidth() / 30f, 0.5f);
        vertical.setAsBox(2f, Gdx.graphics.getHeight() / 30f); // Height of the wall covering screen height

        // Fixture for ground
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundBox;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        groundBody.createFixture(fixtureDef);
        groundBox.dispose();

        // Fixture for vertical wall
        FixtureDef wallFixture = new FixtureDef();
        wallFixture.shape = vertical;
        wallFixture.friction = 0.3f;
        wallFixture.restitution = 0.1f;
        verticalWall.createFixture(wallFixture);
        vertical.dispose();

        slingshot = new Slingshot(200, 115);

        birdQueue = new LinkedList<>();
        //birdQueue.add(new Redbird(200, 300, world));
        Redbird redBird = new Redbird(200, 300, world);
        redBird.getBody().setUserData(redBird); // Set userData for collision handling
        birdQueue.add(redBird);

        //birdQueue.add(new Blackbird(100, 300, world));
        Blackbird blackBird = new Blackbird(100, 300, world);
        blackBird.getBody().setUserData(blackBird); // Set userData
        birdQueue.add(blackBird);

        //birdQueue.add(new Bluebird(100, 300, world));
        Bluebird blueBird = new Bluebird(100, 300, world);
        blueBird.getBody().setUserData(blueBird); // Set userData
        birdQueue.add(blueBird);


        currentBird = birdQueue.poll();

        // Set initial position for first bird
        if (currentBird != null && currentBird.getBody() != null) {
            currentBird.getBody().setTransform(BIRD_START_X, BIRD_START_Y, 0);
            currentBird.getBody().setLinearVelocity(0, 0);
        }

        Block1 block1 = new Block1(800, 100, world);
        block1.getBody().setUserData(block1); // Set userData for collision handling
        blocks.add(block1);

        Block1 block2 = new Block1(800, 163, world);
        block2.getBody().setUserData(block2); // Set userData
        blocks.add(block2);

        Block1 block3 = new Block1(860, 100, world);
        block3.getBody().setUserData(block3); // Set userData
        blocks.add(block3);

        Block1 block4 = new Block1(920, 100, world);
        block4.getBody().setUserData(block4); // Set userData
        blocks.add(block4);

        Block1 block5 = new Block1(920, 163, world);
        block5.getBody().setUserData(block5); // Set userData
        blocks.add(block5);

// set up pig and setting userData
        Pig1 pig1 = new Pig1(865, 165, world);
        pig1.getBody().setUserData(pig1); // Set userData for collision handling
        pigs.add(pig1);

        createVictoryWindow();

        destructionHandler = new Destruction(world, blocks, pigs);
        world.setContactListener(destructionHandler);
    }

    private void handleInput() {
        if (Gdx.input.justTouched() && currentBird != null && !currentBird.isReleased) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Reduce force for better control
            float forceX = (currentBird.getX() - touchX/175f) * 2f;
            float forceY = (currentBird.getY() - touchY/200f) * 2f;

            currentBird.launch(forceX, forceY);
        }
    }


    private boolean isBirdInactive(Bird bird) {
        if (bird == null || bird.getBody() == null) return true;

        Body body = bird.getBody();
        Vector2 position = body.getPosition();
        Vector2 velocity = body.getLinearVelocity();

        // Only consider the bird inactive if it's way off screen
        return position.y < -10 || position.x < -10 || position.x > Gdx.graphics.getWidth();
    }


    public void remove() {
        if (currentBird != null && currentBird.isReleased) {
            Vector2 velocity = currentBird.getBody().getLinearVelocity();

            // Check if bird is nearly stopped or out of bounds
            if (velocity.len2() < 0.07f ||
                currentBird.getX() < -10 ||
                currentBird.getX() > Gdx.graphics.getWidth()/30f ||
                currentBird.getY() < -10) {

                // Destroy current bird's body
                world.destroyBody(currentBird.getBody());
                currentBird = null;  // Clear the reference

                // Get next bird from queue
                if (!birdQueue.isEmpty()) {
                    currentBird = birdQueue.poll();

                    // Position the new bird correctly
                    if (currentBird != null) {
                        // Reset position and velocity
                        currentBird.getBody().setTransform(BIRD_START_X, BIRD_START_Y, 0);
                        currentBird.getBody().setLinearVelocity(0, 0);
                        currentBird.getBody().setAngularVelocity(0);
                        currentBird.isReleased = false;

                        // Make sure the body is awake
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
        winWindowStyle.titleFont = new BitmapFont();//give me a font for here;
        winWindowStyle.titleFontColor = Color.WHITE;
        winWindowStyle.background = victoryDrawable;

        victoryWindow = new Window("", winWindowStyle);
        victoryWindow.setVisible(false);
        // Set the window size and position to center it on the screen
        victoryWindow.setSize(470, 500);
        victoryWindow.setPosition(
            Gdx.graphics.getWidth() / 2f - victoryWindow.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - victoryWindow.getHeight() / 2f
        );
    }

    @Override
    public void render(float delta){
        world.step(1 / 60f, 6, 2);
        destructionHandler.cleanupDestroyedBodies();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        BitmapFont font = new BitmapFont();
        batch.draw(level1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.setColor(Color.BLACK);
        font.draw(batch, "High speed", 150, 500);
        font.draw(batch, "High speed , more bounce", 150, 200);
        font.draw(batch, "medium speed", 450, 500);
        font.draw(batch, "medium speed, more bounce", 450, 200);
        font.draw(batch, "low speed", 750, 500);
        font.draw(batch, "low speed, more bounce", 750, 200);


        // Render blocks if their strength > 0
        for (Block block : blocks) { //blocks is a list of all Block1 objects
            if (block.getStrength() > 0) {
                block.render(batch);
            }
        }
        // Render pigs if their health > 0
        for (Pig pig : pigs) { //pigs is a list of all Pig1 objects
            if (pig.getHealth() > 0) {
                pig.render(batch);
            }
        }

        // Render and handle bird
        if (currentBird != null) {
            if (isBirdInactive(currentBird)) {
                // Destroy the old bird's body and set the next bird in the queue
                Body oldBody = currentBird.getBody();
                currentBird = birdQueue.poll();

                if (oldBody != null) {
                    world.destroyBody(oldBody);
                }

                if (currentBird != null) {
                    // Position new bird
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

        if (birdQueue.isEmpty() && pigs.isEmpty()) {
            // Level completed successfully
            GameProgressManager.getManageInstance().completeLevel(1);
            ((Main) Gdx.app.getApplicationListener()).setScreen(new Victorystatus(batch));
        } else if (currentBird == null && birdQueue.isEmpty() && !pigs.isEmpty()) {
            // Level failed
            ((Main) Gdx.app.getApplicationListener()).setScreen(new Loosestatus(batch));
        }


        handleInput();
        remove();


        if(Gdx.input.justTouched()){ //buttons input

            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(clickX>=50 && clickX <= (50+80) && clickY>=600 && clickY<=(600+80)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Levels(batch));
            }
            else if(clickX>=330 && clickX<=(330+90) && clickY>=595 && clickY<=(595+90)) {
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level1(batch));
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
        level1.dispose();
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
            Redbird bird = (Redbird) birdQueue.poll();
            if (bird != null && bird.getBody() != null) {
                world.destroyBody(bird.getBody());
            }
        }

        Block1.dispose(world);
        Block2.dispose(world);
        Block3.dispose(world);
        Block4.dispose(world);
        Block5.dispose(world);

        Pig1.dispose(world);

        world.dispose();
    }

}
