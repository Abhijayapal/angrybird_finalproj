package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block2 extends Block {
    private static final String blockpic = "block.png";
    private final Body body;
    private float height;
    private float width;

    public Block2(float x, float y, World world, float width, float height) {
        super(blockpic, x, y, 700);
        this.width = width;
        this.height = height;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / 30f, y / 30f);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width/2) / 30f, (height/2) / 30f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.068f;
        fixtureDef.friction = 0.07f;
        fixtureDef.restitution = 0.11f;

        // Attach shape to the body
        body.createFixture(fixtureDef);

        // Dispose the shape
        shape.dispose();


    }


    @Override
    public void render(SpriteBatch batch) {
        Vector2 position = body.getPosition();
        float x = position.x * 30f - (width / 2);
        float y = position.y * 30f - (height / 2);

        batch.draw(getBlockimage(), x, y, width, height);
    }


    @Override
    public void dispose(World world) {
        world.destroyBody(body);
    }

    public Body getBody() {
        return body;
    }
}
