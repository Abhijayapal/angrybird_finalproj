package io.github.some_example_name;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Pig1 extends Pig {
    private Body body;
    private float width = 65;
    private float height = 65;

    // loading the pig image and set position
    public Pig1(float x, float y, World world) {
        super("pig1.png", x, y, 450);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 30f, y / 30f);
        bodyDef.fixedRotation = false;
        bodyDef.allowSleep = true;
        //this will  allow body to sleep when not moving

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 60f); // adjusting radius

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.filter.categoryBits = 0x0004;
        fixtureDef.filter.maskBits = 0x0001 | 0x0002;

        body.createFixture(fixtureDef);
        shape.dispose(); // disposing of the shape as its no longer needed
    }

    @Override
    public void render(SpriteBatch batch) {
        if (body != null) {
            // Get current position from the physics body
            Vector2 position = body.getPosition();
            float renderX = position.x * 30f - width / 2; //position
            float renderY = position.y * 30f - height / 2;
            float rotation = (float) Math.toDegrees(body.getAngle()); //angle in degrees

            batch.draw(getPigimage(), renderX, renderY,
                width / 2, height / 2,  // Rotation origin
                width, height,
                1f, 1f,  // Scale
                rotation);
        }
    }
    public Body getBody() {
        return body;
    }
    public void dispose(World world) {
        if (body != null) {
            world.destroyBody(body);  // clearing the body reference
            body = null;
        }
    }
}
