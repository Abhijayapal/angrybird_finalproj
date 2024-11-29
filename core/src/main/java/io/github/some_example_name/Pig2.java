package io.github.some_example_name;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Pig2 extends Pig {
    private Body body;
    private float width = 65;
    private float height = 65;

    public Pig2(float x, float y, World world) {
        super("pig2.png", x, y, 800);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 30f, y / 30f);
        bodyDef.fixedRotation = false;
        bodyDef.allowSleep = true;

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 60f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.01f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.filter.categoryBits = 0x0004;
        fixtureDef.filter.maskBits = 0x0001 | 0x0002;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void render(SpriteBatch batch) {
        if (body != null) {

            Vector2 position = body.getPosition();
            float renderX = position.x * 30f - width / 2;
            float renderY = position.y * 30f - height / 2;
            float rotation = (float) Math.toDegrees(body.getAngle());

            batch.draw(getPigimage(), renderX, renderY,
                width / 2, height / 2,
                width, height,
                1f, 1f,
                rotation);
        }
    }

    public Body getBody() {
        return body;
    }

    public void dispose(World world) {
        if (body != null) {
            world.destroyBody(body);
            body = null;
        }
    }
}
