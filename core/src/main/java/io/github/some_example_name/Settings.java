package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Settings implements Screen {
    private final SpriteBatch batch;
    private final Texture settingpage;
    private final Texture cross;

    public Settings(SpriteBatch batch){
        this.batch = batch;

        settingpage = new Texture((Gdx.files.internal("settingpage.png")));
        cross = new Texture((Gdx.files.internal("cross.png")));
    }

    @Override
    public void render(float delta){
        batch.begin();
        batch.draw(settingpage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(cross, 350, 460, 50, 50);
        batch.end();

        if(Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(clickX>=350 && clickX<=(350+50) && clickY>=460 && clickY<=(460+50)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Menu(batch));
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       batch.dispose();
       settingpage.dispose();
       cross.dispose();
    }
}
