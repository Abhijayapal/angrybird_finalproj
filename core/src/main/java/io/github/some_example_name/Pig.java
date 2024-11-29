package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract class Pig {
    private float health;
    private String color;
    private TextureRegion pigimage;
    private float x;
    private float y;

    public Pig(String pigimage, float x, float y, float health){
        this.pigimage = new TextureRegion(new Texture(Gdx.files.internal(pigimage)));
        this.x = x;
        this.y = y;
        this.health = health;
    }

    public TextureRegion getPigimage() {
        return pigimage;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }
    public abstract void render(SpriteBatch batch);

    public void damagePigs(String image, int originalHealth) {
        if(health<= (float) originalHealth /2){
            pigimage = new TextureRegion(new Texture(Gdx.files.internal(image)));
        }
    }
}
