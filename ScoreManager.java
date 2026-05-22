import java.io.*;

public class ScoreManager {
    private static final String SCORE_FILENAME = "bestscore.dat";

    private final File scoreFile;
    private int currentScore;
    private int bestScore;

    public ScoreManager() {
        this.scoreFile = new File(SCORE_FILENAME);
        this.currentScore = 0;
        this.bestScore = 0;
        loadBestScore();
    }

    public void resetCurrentScore() {
        currentScore = 0;
    }

    public void addPoint() {
        currentScore++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void updateBestScore() {
        if (currentScore > bestScore) {
            bestScore = currentScore;
            saveBestScore();
        }
    }

    private void loadBestScore() {
        if (!scoreFile.exists()) {
            bestScore = 0;
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line = reader.readLine();
            bestScore = Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            bestScore = 0;
        }
    }

    private void saveBestScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            writer.write(Integer.toString(bestScore));
        } catch (IOException ignored) {
            // If saving fails, just keep the current score in memory.
        }
    }
}
