package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Victorystatus implements Screen{
    private final SpriteBatch batch;
    private final Texture victorypage;
    private final Texture cross;
    private final Texture next;
    private final Texture replay;

    public Victorystatus(SpriteBatch batch){
        this.batch = batch;
        next = new Texture(Gdx.files.internal("play1.png"));
        replay = new Texture(Gdx.files.internal("replay.png"));

        victorypage = new Texture((Gdx.files.internal("victoryypage.png")));
        cross = new Texture((Gdx.files.internal("cross.png")));
    }

    @Override
    public void render(float delta){

        batch.begin();
        batch.draw(victorypage, 400, 90, 400, 500);
        batch.draw(cross, 400, 500, 60, 60);
        batch.draw(next, 500, 130, 80, 80);
        batch.draw(replay, 620, 130, 80, 80);
        batch.end();

        if(Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if(clickX>=620 && clickX<=(620+80) && clickY>=130 && clickY<=(130+80)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level1(batch));
            }
            if(clickX>=500 && clickX<=(500+80) && clickY>=130 && clickY<=(130+80)){
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level2(batch));
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
        victorypage.dispose();
        cross.dispose();
        next.dispose();
        replay.dispose();
    }




}
