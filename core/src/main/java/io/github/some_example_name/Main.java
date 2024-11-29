package io.github.some_example_name;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */

public class Main extends Game {
    private SpriteBatch batch;
    private Texture loadingImage;

    @Override
    public void create() {
        batch = new SpriteBatch();
        loadingImage = new Texture("loading2.jpeg"); // Ensure this image exists in assets

        // Set the loading screen
        setScreen(new Loadingscreen(batch)); // No need to cast

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        loadingImage.dispose();
    }
}
