package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Slingshot {
    private SpriteBatch batch;
    private static float x;
    private static float y;
    private static Texture slingshoimage;


    public Slingshot(float x, float y){

        slingshoimage = new Texture((Gdx.files.internal("Slingshot.png")));

        this.x = x;
        this.y = y;

    }
    public static void render(SpriteBatch batch){
        batch.draw(slingshoimage, x, y, 80, 80);
    }



}

//package io.github.some_example_name;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector2;
//
//public class Slingshot {
//    private static float x;
//    private static float y;
//    private static Texture slingshotImage;
//    private Bird currentBird;  // Track the bird on the slingshot
//    private boolean isDragging = false;
//    private static Vector2 initialBirdPosition;  // Initial position of the bird on slingshot
//
//    public Slingshot(float x, float y) {
//        slingshotImage = new Texture(Gdx.files.internal("Slingshot.png"));
//        this.x = x;
//        this.y = y;
//    }
//
//    // Set the bird that is placed on the slingshot
//    // Set the bird that is placed on the slingshot
//    public void setBird(Bird bird) {
//        this.currentBird = bird;
//        if (bird != null) {
//            initialBirdPosition = new Vector2(bird.getX(), bird.getY());
//        }
//    }
//    // Get the bird on the slingshot
//    public Bird getCurrentBird() {
//        return this.currentBird;
//    }
//
//    public static void render(SpriteBatch batch) {
//        batch.draw(slingshotImage, x, y, 80, 80);
//    }
//
//    // Handle dragging of the bird
//    public void updateBirdPosition() {
//        if (currentBird != null && isDragging) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Convert to screen coordinates
//            currentBird.getBody().setTransform(touchX / 30f, touchY / 30f, 0);
//        }
//    }
//
//    // Handle mouse/touch input for slingshot
//    public void handleInput() {
//        if (Gdx.input.isTouched()) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Convert to screen coordinates
//
//            // Check if touch is within the slingshot area
//            if (touchX >= x && touchX <= x + 80 && touchY >= y && touchY <= y + 80) {
//                // Start dragging the bird
//                isDragging = true;
//                if (currentBird != null) {
//                    initialBirdPosition.set(currentBird.getX(), currentBird.getY());
//                }
//            }
//        } else if (isDragging) {
//            // Release the bird and launch
//            launchBird();
//            isDragging = false;
//        }
//    }
//
//    // Apply force to launch the bird when released
//    private void launchBird() {
//        if (currentBird != null) {
//            float touchX = Gdx.input.getX();
//            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();  // Convert to screen coordinates
//
//            // Calculate force based on distance from slingshot
//            float forceX = (x - touchX) * 2f; // Multiply by a factor to control force
//            float forceY = (y - touchY) * 2f;
//
//            currentBird.launch(forceX, forceY);
//        }
//    }
//}

