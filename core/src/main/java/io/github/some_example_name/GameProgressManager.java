package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GameProgressManager {
    private LevelProgress levelState;
    private static GameProgressManager ManageInstance;

    private GameProgressManager() {
        loadProgress();
    }
    //only one instance is created
    public static synchronized GameProgressManager getManageInstance() {
        if (ManageInstance == null) {
            ManageInstance = new GameProgressManager();
        }
        return ManageInstance;
    }

    // game level progression state
    public static class LevelProgress {
        public boolean level1Completed;
        public boolean level2Completed;
        public boolean level3Completed;
        public int totalStars; // Optional: track overall performance
    }

    // loading progress data from file
    private void loadProgress() {
        try {
            File progressFile = new File(getProgressFilePath());
            if (!progressFile.exists()) {
                // Initialize default progress if no save exists
                levelState = new LevelProgress();
                return;
            }
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(progressFile)) {
                levelState = gson.fromJson(reader, LevelProgress.class);
            }
        } catch (IOException e) {
            Gdx.app.error("GameProgress", "Error loading progress", e);
            levelState = new LevelProgress(); // Fallback to default
        }
    }

    public void saveProgress() {         //saving current progress to file
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(getProgressFilePath())) {
                gson.toJson(levelState, writer);
            }
        } catch (IOException e) {
            Gdx.app.error("GameProgress", "Error saving progress", e);
        }
    }

    private String getProgressFilePath() {
        return Gdx.files.getLocalStoragePath() + "game_progress.json";
    }

    public boolean isLevelUnlocked(int levelNumber) {
        if (levelState == null) { //if level is unlocked
            loadProgress(); // Ensure progress is loaded
        }

        switch (levelNumber) {
            case 1:
                return true; //because level 1 will always be unlock
            case 2:
                return levelState.level1Completed;
            case 3:
                return levelState.level2Completed;
            default:
                return false;
        }
    }
    public void completeLevel(int levelNumber) {
        if (levelState == null) {
            loadProgress();
        }

        switch (levelNumber) {
            case 1:
                levelState.level1Completed = true;
                break;
            case 2:
                levelState.level2Completed = true;
                break;
            case 3:
                levelState.level3Completed = true;
                break;
        }
        saveProgress();
    }
}
