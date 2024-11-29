package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Loadingscreen implements Screen {
    private final SpriteBatch batch;
    private final Texture loadingImage;

    private float loadingProgress = 0;

    public Loadingscreen(SpriteBatch batch) {
        this.batch = batch;
        this.loadingImage = new Texture(Gdx.files.internal("loading2.jpeg"));
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(loadingImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        loadingProgress += delta;
        if (loadingProgress >= 1) {
            ((Main) Gdx.app.getApplicationListener()).setScreen(new Menu(batch)); // Transition to Menu screen
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        loadingImage.dispose();
    }
}
