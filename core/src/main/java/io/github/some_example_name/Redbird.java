package io.github.some_example_name;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class Redbird extends Bird{
    private static String birdpic = "redbird.png";

    public Redbird(float x, float y, World world){
        super(birdpic, x, y, world, "redbird", 270, 200);
    }
}
