package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block1 extends Block{

    private static final String blockpic = "block.png";
    private final Body body;


    public Block1(float x, float y, World world) {
        super(blockpic, x, y, 650);

        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 30f, y / 30f);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(45 / 30f, 40 / 30f);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.009f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.01f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
        float x = position.x * 30f - 45;
        float y = position.y * 30f - 40;

        batch.draw(getBlockimage(), x, y, 90, 80);
    }

    @Override
    public void dispose(World world) {
        world.destroyBody(body);
    }
    public Body getBody() {
        return body;
    }
}

