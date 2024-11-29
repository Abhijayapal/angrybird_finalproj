package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Loosestatus implements Screen{
    private final SpriteBatch batch;
    private final Texture loosepage;
    private final Texture cross;

    private final Texture replay;

    public Loosestatus(SpriteBatch batch){
        this.batch = batch;

        replay = new Texture(Gdx.files.internal("replay.png"));

        loosepage = new Texture((Gdx.files.internal("loosepage.png")));
        cross = new Texture((Gdx.files.internal("cross.png")));
    }

    @Override
    public void render(float delta){

        batch.begin();
        batch.draw(loosepage, 400, 90, 400, 500);
        batch.draw(cross, 400, 500, 60, 60);
        batch.draw(replay, 620, 130, 80, 80);
        batch.end();

        if(Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(clickX>=620 && clickX<=(620+80) && clickY>=130 && clickY<=(130+80)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level1(batch));
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
        loosepage.dispose();
        cross.dispose();
        replay.dispose();
    }




}
