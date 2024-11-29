package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu implements Screen {
    private final SpriteBatch batch;
    private final Texture background;
    private final Texture play;
    private final Texture setting;
    private final Texture settingpage;
    private final Texture quit;
    private final Sound backgroundMusic;

    public Menu(SpriteBatch batch) {

        this.batch = batch;

        background = new Texture((Gdx.files.internal("background.jpeg")));
        play = new Texture((Gdx.files.internal("play.png")));
        setting = new Texture((Gdx.files.internal("setting.png")));
        quit = new Texture((Gdx.files.internal("quit.png")));
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("m1.mp3"));
        settingpage = new Texture((Gdx.files.internal("settingpage.png")));

    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float width = 200;
        float height = 150;
        float X = 510;
        float Y = 310;

        float width1 = 80;
        float height1 = 80;
        float X1 = 10;
        float Y1 = 10;

        batch.draw(play, X, Y, width, height);
        batch.draw(setting,X1, Y1, width1, height1);
        batch.draw(quit, 100, 10, 80, 80);

        batch.end();

        if(Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (clickX >= X && clickX <= X + width && clickY >= Y && clickY <= Y + height) {
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Levels(batch));
            }
            else if(clickX >= X1 && clickX <= X1 + width1 && clickY >= Y1 && clickY <= Y1 + height1){
               ((Main) Gdx.app.getApplicationListener()).setScreen(new Settings(batch));
            }
            else if(clickX>=100 && clickX<=(100 + 80) && clickY>=10 && clickY<=(10+80)){
                Gdx.app.exit();
            }
        }
    }
    @Override
    public void show() {
        backgroundMusic.play();
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
            background.dispose();
            backgroundMusic.dispose();
        }
}


