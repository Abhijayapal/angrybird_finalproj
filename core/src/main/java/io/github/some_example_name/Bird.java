package io.github.some_example_name;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

abstract class Bird {
    private  String type;
    private  float birdhealth;
    private  int speed;
    private  int weight;

    public boolean isReleased=false;
    private Texture birdimage;
    private Body body;
    private float x, y;

    //bonus
    private float currentScale = 1.0f;
    private static final float max = 2.0f;
    private static final float growing_rate = 1.5f;
    private final float inRadius = 0.5f;
    private float currentRadius = inRadius;

    public Bird(String pic, float xcoordinate, float ycoordinate, World world, String type, int power, int weight ){
        this.birdimage = new Texture((Gdx.files.internal(pic)));
        this.type = type;
        this.birdhealth = power;
        // this.speed = speed;
        this.weight = weight;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xcoordinate, ycoordinate);
        this.body = world.createBody(bodyDef);

        circle(inRadius);
        // Start with the body awake
        this.body.setAwake(true);

    }

    //bonus
    private void circle(float radius) {
        if (body.getFixtureList().size > 0) {
            body.destroyFixture(body.getFixtureList().first());
        }

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        body.setLinearDamping(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.42f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;

        this.body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void launch(float forceX, float forceY) {
        if (!isReleased) {
            body.applyLinearImpulse(new Vector2(forceX, forceY), body.getWorldCenter(), true);
            isReleased = true;
            Sound birdShot = Gdx.audio.newSound(Gdx.files.internal("BirdShot.mp3"));
            birdShot.play();
            increaseSize();
        }
    }
    private void increaseSize() {
        currentRadius *= growing_rate;
        if (currentRadius > inRadius * max) {
            currentRadius = inRadius * max;
        }
        circle(currentRadius);

        currentScale = currentRadius / inRadius;
    }


    public float getX() {
        return body.getPosition().x; //body x position
    }

    public float getY() {
        return body.getPosition().y; //boy y position
    }

    public void render(SpriteBatch batch) {
        if (body != null && birdimage != null) {
            Vector2 position = body.getPosition();

            float baseSize = 80; // Original size
            float scaledSize = baseSize * currentScale; //new size
            float offset = scaledSize / 2;

            float birdX = position.x * 30 - offset;
            float birdY = position.y * 30 - offset;
            batch.draw(birdimage, birdX, birdY, scaledSize, scaledSize);
        }
    }
    public Body getBody() {
        return body;
    }

    public Texture getBirdimage() {
        return birdimage;
    }
    public String getType() {
        return type;
    }

    public float getBirdhealth() {
        return birdhealth;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWeight() {
        return weight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBirdhealth(int birdhealth) {
        this.birdhealth = birdhealth;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
