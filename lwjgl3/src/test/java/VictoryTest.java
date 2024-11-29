import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import io.github.some_example_name.*;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

public class VictoryTest {

    private SpriteBatch batch;
    private ArrayList<Bird> birdQueue;
    private ArrayList<Pig> pigs;
    private Main mainMock;


    public class ConcretePig extends Pig {
        public ConcretePig(String pigimage, float x, float y, float health) {
            super(pigimage, x, y, health);
        }

        @Override
        public void render(SpriteBatch batch) {

        }
    }

    @Before
    public void setUp() {

        batch = mock(SpriteBatch.class);
        mainMock = mock(Main.class);


        birdQueue = new ArrayList<>();
        pigs = new ArrayList<>();
    }

    @Test
    public void testVictoryCondition1_allBirdsUsed_and_allPigsDefeated() {

        birdQueue.clear();


        pigs.clear();

        if (birdQueue.isEmpty() && pigs.isEmpty()) {
            mainMock.setScreen(new Victorystatus(batch));
        }

        verify(mainMock).setScreen(any(Victorystatus.class));
    }

    @Test
    public void testVictoryCondition2_birdsLeft_and_allPigsDefeated() {

        birdQueue.add(new Bird("bird1.png", 0, 0, mock(World.class), "Type1", 100, 10));

        pigs.clear();

        if (!birdQueue.isEmpty() && pigs.isEmpty()) {
            mainMock.setScreen(new Victorystatus(batch));
        }

        verify(mainMock).setScreen(any(Victorystatus.class));
    }

    @Test
    public void testVictoryCondition_noBirds_and_pigsLeft() {

        birdQueue.clear();

        pigs.add(new ConcretePig("pig1.png", 0, 0, 100));

        if (birdQueue.isEmpty() && !pigs.isEmpty()) {
            mainMock.setScreen(new Victorystatus(batch));
        }

        verify(mainMock, never()).setScreen(any(Victorystatus.class));
    }
}
