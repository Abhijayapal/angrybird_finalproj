package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;

public class Bluebird extends Bird{
    private static String birdpic = "bluebird.png";

    public Bluebird(float x, float y, World world){
        super(birdpic, x, y, world, "bluebird", 300, 150);
    }
}
