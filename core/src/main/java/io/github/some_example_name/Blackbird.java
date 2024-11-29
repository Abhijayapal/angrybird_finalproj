package io.github.some_example_name;

import com.badlogic.gdx.physics.box2d.World;

public class Blackbird extends Bird{
    private static String birdpic = "blackbird.png";

    public Blackbird(float x, float y, World world){
        super(birdpic, x, y, world, "blackbird", 350, 250);
    }
}
