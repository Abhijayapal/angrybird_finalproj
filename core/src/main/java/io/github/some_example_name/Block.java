package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

abstract class Block {
    private float strength;

    private Texture blockimage;
    private float x;
    private float y;

    public Block(String blockimage, float x, float y, float strength){
        this.blockimage = new Texture((Gdx.files.internal(blockimage)));
        this.x = x;
        this.y = y;
        this.strength = strength;
    }

    public void damageBlocks(String image, int orignialStrength) {
        if(strength<=(float) orignialStrength / 2){
            blockimage = new Texture(String.valueOf(new Texture(Gdx.files.internal(image))));
        }
    }

    public Texture getBlockimage(){
        return blockimage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    // Abstract method to be implemented by subclasses to dispose the body
    public abstract void dispose(World world);

    // Abstract method to be implemented by subclasses to render the block
    public abstract void render(SpriteBatch batch);


}
