package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Levels implements Screen{
    private final SpriteBatch batch;
    private final Texture levelsBackground;
    private final Texture button1;
    private final Texture button2;
    private final Texture button3;
    private final Texture back;
    private final Texture lockedButton; // New texture for locked levels
    GameProgressManager progressManager;

    public Levels(SpriteBatch batch){
        this.batch = batch;
        progressManager = GameProgressManager.getInstance();

        levelsBackground = new Texture((Gdx.files.internal("levels.png")));
        button1 = new Texture((Gdx.files.internal("button1.png")));
        button2 = new Texture((Gdx.files.internal("button2.png")));
        button3 = new Texture((Gdx.files.internal("button3.png")));
        back = new Texture((Gdx.files.internal("back.png")));
        lockedButton = new Texture((Gdx.files.internal("save.png")));
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(levelsBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(button1, 350, 360, 100, 100); //level1 unlocked always

        // Level 2 - check if Level 1 is completed
        if (progressManager.isLevelUnlocked(2)) {
            batch.draw(button2, 540, 350, 125, 125);
        } else {
            batch.draw(lockedButton, 540, 350, 125, 125);
        }

        //batch.draw(button2, 540, 350, 125, 125);
        //batch.draw(button3, 750, 360, 95, 95);
        //batch.draw(back, 50, 600, 80, 80);

        // Level 3 - check if Level 2 is completed
        if (progressManager.isLevelUnlocked(3)) {
            batch.draw(button3, 750, 360, 95, 95);
        } else {
            batch.draw(lockedButton, 750, 360, 95, 95);
        }

        batch.draw(back, 50, 600, 80, 80);
        batch.end();

        handleInput();

//        if(Gdx.input.justTouched()){
//            float clickX = Gdx.input.getX();
//            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();
//
//            if(clickX>=350 && clickX<= (350+100) && clickY>=360 && clickY<=(360+100)){
//
//                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level1(batch));
//            }
//            else if (clickX>=540 && clickX<= (540+125) && clickY>=350 && clickY<=(350+125)) {
//                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level2(batch));
//            }
//            else if(clickX>=750 && clickX<= (750+95) && clickY>=360 && clickY<=(360+95)){
//                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level3(batch));
//            }
//
//            else if(clickX>=50 && clickX <= (50+80) && clickY>=600 && clickY<=(600+80)){
//                ((Main) Gdx.app.getApplicationListener()).setScreen(new Menu(batch));
//            }
//        }
    }
    private void handleInput() {
        if (Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Level 1 - Always accessible
            if (clickX >= 350 && clickX <= (350 + 100) && clickY >= 360 && clickY <= (360 + 100)) {
                ((Main) Gdx.app.getApplicationListener()).setScreen(new Level1(batch));
            }

            // Level 2 - Check if unlocked
            else if (clickX >= 540 && clickX <= (540 + 125) && clickY >= 350 && clickY <= (350 + 125)) {
                if (progressManager.isLevelUnlocked(2)) {
                    ((Main) Gdx.app.getApplicationListener()).setScreen(new Level2(batch));
                }
            }

            // Level 3 - Check if unlocked
            else if (clickX >= 750 && clickX <= (750 + 95) && clickY >= 360 && clickY <= (360 + 95)) {
                if (progressManager.isLevelUnlocked(3)) {
                    ((Main) Gdx.app.getApplicationListener()).setScreen(new Level3(batch));
                }
            }

            // Back button
            else if (clickX >= 50 && clickX <= (50 + 80) && clickY >= 600 && clickY <= (600 + 80)) {
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
        levelsBackground.dispose();
        button1.dispose();
        button2.dispose();
        button3.dispose();
        back.dispose();
    }

}
