package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.audio.Sound;

public class Destruction implements ContactListener {
    private World world;
    private List<Block> blocks;
    private List<Pig> pigs;
    private List<Body> bodiesToDestroy; // List to track bodies that need to be destroyed

    public Destruction(World world, List<Block> blocks, List<Pig> pigs) {
        this.world = world;
        this.blocks = blocks;
        this.pigs = pigs;
        this.bodiesToDestroy = new ArrayList<>();
    }
    @Override
    public void beginContact(Contact contact) {
        try {
            Fixture fixture1 = contact.getFixtureA();
            Fixture fixture2 = contact.getFixtureB();

            // Null checks for fixtures and their bodies
            if (fixture1 == null || fixture2 == null ||
                fixture1.getBody() == null || fixture2.getBody() == null) {
                return;
            }

            handleCollision(fixture1, fixture2);
            handleCollision(fixture2, fixture1);
        } catch (Exception e) {
            System.out.println("collision handling error: " + e.getMessage());
        }
    }

    private void handleCollision(Fixture birdFixture, Fixture otherFixture) {
        try {
            Object birdUserData = birdFixture.getBody().getUserData();
            Object otherUserData = otherFixture.getBody().getUserData();

            //Check for null user data
            if (birdUserData == null || otherUserData == null) {
                return;
            }

            // handles bird collision
            if (birdUserData instanceof Bird) {
                Bird bird = (Bird) birdUserData;

                // handles block collision
                if (otherUserData instanceof Block) {
                    handleBlockCollision(bird, (Block) otherUserData, otherFixture.getBody());
                }
                // handles pig collision
                else if (otherUserData instanceof Pig) {
                    handlePigCollision(bird, (Pig) otherUserData, otherFixture.getBody());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleBlockCollision(Bird bird, Block block, Body blockBody) {
        float newStrength = block.getStrength() - bird.getBirdhealth();
        block.setStrength((int) newStrength);

        Sound blockshot = Gdx.audio.newSound(Gdx.files.internal("blockshot.mp3"));
        blockshot.play();

        if(block instanceof Block1){
            block.damageBlocks("DamagedBlock.png", 650);
        }
        else if(block instanceof Block2) {
            block.damageBlocks("DamagedBlock.png", 700);
        }

        if (block.getStrength() <= 0) {
            // Queue the body for destruction instead of destroying immediately
            bodiesToDestroy.add(blockBody);
            blocks.remove(block);
        }
    }

    public void handlePigCollision(Bird bird, Pig pig, Body pigBody) {
        float newHealth = pig.getHealth() - bird.getBirdhealth();
        pig.setHealth((int) newHealth);

        Sound pigshot = Gdx.audio.newSound(Gdx.files.internal("pigshot.mp3"));
        pigshot.play();

        if(pig instanceof Pig1) {
            pig.damagePigs("deadpig1.png", 450);
        }
        else if (pig instanceof Pig2) {
            pig.damagePigs("deadpig2.png", 800);
        }

        if (pig.getHealth() <= 0) {
            // Queue the body for destruction instead of destroying immediately
            bodiesToDestroy.add(pigBody);
            pigs.remove(pig);
        }
    }

    public void cleanupDestroyedBodies() {
        // Safely remove all queued bodies
        for (Body body : bodiesToDestroy) {
            if (body != null && !world.isLocked()) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
