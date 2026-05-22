import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    public static final int WIDTH = 360;
    public static final int HEIGHT = 640;
    public static final double GRAVITY = 0.55;
    public static final double JUMP_VELOCITY = -11;
    public static final double MAX_DROP_SPEED = 12;
    public static final int MAX_BIRD_ANGLE = 45;

    private static final int BASE_PIPE_SPEED = 4;
    private static final int MIN_PIPE_GAP = 140;
    private static final int MAX_PIPE_GAP = 210;
    private static final int PIPE_INTERVAL_MS = 1400;
    private static final int BACKGROUND_SCROLL_SPEED = 1;

    private enum GameState {
        START,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private GameState state;
    private BufferedImage backgroundImage;
    private BufferedImage birdSprite;
    private BufferedImage topPipeImage;
    private BufferedImage bottomPipeImage;

    private int backgroundOffset;
    private Bird bird;
    private ArrayList<Pipe> pipes;
    private int pipeSpeed;
    private int pipeGap;
    private Timer gameTimer;
    private Timer pipeTimer;
    private final Random random = new Random();
    private final SoundManager soundManager;
    private final ScoreManager scoreManager;
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 36);
    private final Font textFont = new Font("Segoe UI", Font.PLAIN, 22);
    private final Font scoreFont = new Font("Segoe UI", Font.BOLD, 28);

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        soundManager = new SoundManager();
        scoreManager = new ScoreManager();
        pipes = new ArrayList<>();

        loadResources();
        resetGame();

        gameTimer = new Timer(1000 / 60, this);
        gameTimer.start();

        pipeTimer = new Timer(PIPE_INTERVAL_MS, e -> spawnPipe());
        state = GameState.START;
    }

    private void loadResources() {
        birdSprite = loadImage("flappybird.png", Bird.WIDTH, Bird.HEIGHT, new Color(240, 200, 70));
        topPipeImage = loadImage("toppipe.png", Pipe.WIDTH, Pipe.HEIGHT, new Color(20, 150, 40));
        bottomPipeImage = loadImage("bottompipe.png", Pipe.WIDTH, Pipe.HEIGHT, new Color(20, 150, 40));
        backgroundImage = loadImage("flappybirdbg.png", WIDTH, HEIGHT, new Color(20, 120, 200));
    }

    private BufferedImage loadImage(String fileName, int width, int height, Color fallbackColor) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = placeholder.createGraphics();
            g.setColor(fallbackColor);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, width - 1, height - 1);
            g.dispose();
            return placeholder;
        }
    }

    private void resetGame() {
        scoreManager.resetCurrentScore();
        pipeSpeed = BASE_PIPE_SPEED;
        pipeGap = MAX_PIPE_GAP;
        pipes.clear();
        bird = new Bird(birdSprite, WIDTH / 6, HEIGHT / 2);
        backgroundOffset = 0;
    }

    private void startGame() {
        resetGame();
        applyDifficulty();
        state = GameState.RUNNING;
        pipeTimer.start();
    }

    private void pauseGame() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
            pipeTimer.stop();
        } else if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
            pipeTimer.start();
        }
    }

    private void endGame() {
        if (state != GameState.RUNNING) {
            return;
        }
        state = GameState.GAME_OVER;
        pipeTimer.stop();
        scoreManager.updateBestScore();
        soundManager.playCollision();
    }

    private void updateGame() {
        updateBackground();
        bird.update();
        updatePipes();
        if (bird.isOutOfBounds()) {
            endGame();
        }
    }

    private void updateBackground() {
        backgroundOffset -= BACKGROUND_SCROLL_SPEED;
        if (backgroundOffset <= -WIDTH) {
            backgroundOffset = 0;
        }
    }

    private void updatePipes() {
        Iterator<Pipe> iterator = pipes.iterator();
        while (iterator.hasNext()) {
            Pipe pipe = iterator.next();
            pipe.update(pipeSpeed);

            if (pipe.isOffScreen()) {
                iterator.remove();
                continue;
            }

            if (pipe.isTop() && !pipe.isPassed() && bird.getRight() > pipe.getRight()) {
                pipe.setPassed(true);
                scoreManager.addPoint();
                soundManager.playScore();
                applyDifficulty();
            }

            if (pipe.collides(bird)) {
                endGame();
                return;
            }
        }
    }

    private void applyDifficulty() {
        pipeSpeed = BASE_PIPE_SPEED + Math.min(8, scoreManager.getCurrentScore() / 4);
        pipeGap = Math.max(MIN_PIPE_GAP, MAX_PIPE_GAP - scoreManager.getCurrentScore() / 8);
    }

    private void spawnPipe() {
        int openingY = 100 + random.nextInt(HEIGHT - 240 - pipeGap);
        int topPipeY = openingY - Pipe.HEIGHT;
        int bottomPipeY = openingY + pipeGap;

        pipes.add(new Pipe(topPipeImage, WIDTH, topPipeY, true));
        pipes.add(new Pipe(bottomPipeImage, WIDTH, bottomPipeY, false));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawScene(g2);
        g2.dispose();
    }

    private void drawScene(Graphics2D g2) {
        drawBackground(g2);
        drawPipes(g2);
        bird.draw(g2);
        drawScore(g2);
        drawOverlay(g2);
    }

    private void drawBackground(Graphics2D g2) {
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, backgroundOffset, 0, WIDTH, HEIGHT, null);
            g2.drawImage(backgroundImage, backgroundOffset + WIDTH, 0, WIDTH, HEIGHT, null);
        } else {
            g2.setColor(new Color(20, 120, 200));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
        }
    }

    private void drawPipes(Graphics2D g2) {
        for (Pipe pipe : pipes) {
            pipe.draw(g2);
        }
    }

    private void drawScore(Graphics2D g2) {
        g2.setFont(scoreFont);
        g2.setColor(Color.WHITE);
        String scoreText = String.format("%03d", scoreManager.getCurrentScore());
        int textWidth = g2.getFontMetrics().stringWidth(scoreText);
        g2.drawString(scoreText, WIDTH / 2 - textWidth / 2, 40);
    }

    private void drawOverlay(Graphics2D g2) {
        if (state == GameState.START) {
            drawStartScreen(g2);
        } else if (state == GameState.PAUSED) {
            drawPausedOverlay(g2);
        } else if (state == GameState.GAME_OVER) {
            drawGameOverScreen(g2);
        }
    }

    private void drawStartScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setFont(titleFont);
        drawCenteredText(g2, "Flappy Flight", HEIGHT / 2 - 60);
        g2.setFont(textFont);
        drawCenteredText(g2, "Press SPACE to Start", HEIGHT / 2);
        drawCenteredText(g2, "Use P to Pause / Resume", HEIGHT / 2 + 40);
        drawCenteredText(g2, String.format("Best: %d", scoreManager.getBestScore()), HEIGHT / 2 + 90);
    }

    private void drawPausedOverlay(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setFont(titleFont);
        drawCenteredText(g2, "Paused", HEIGHT / 2 - 20);
        g2.setFont(textFont);
        drawCenteredText(g2, "Press P to Resume", HEIGHT / 2 + 30);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setFont(titleFont);
        drawCenteredText(g2, "Game Over", HEIGHT / 2 - 80);
        g2.setFont(scoreFont);
        drawCenteredText(g2, "Score: " + scoreManager.getCurrentScore(), HEIGHT / 2 - 20);
        drawCenteredText(g2, "Best: " + scoreManager.getBestScore(), HEIGHT / 2 + 20);
        g2.setFont(textFont);
        drawCenteredText(g2, "Press SPACE to Restart", HEIGHT / 2 + 70);
        drawCenteredText(g2, "Press P to Pause / Resume while playing", HEIGHT / 2 + 110);
    }

    private void drawCenteredText(Graphics2D g2, String text, int y) {
        int width = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, WIDTH / 2 - width / 2, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == GameState.RUNNING) {
            updateGame();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            if (state == GameState.START) {
                startGame();
                soundManager.playJump();
            } else if (state == GameState.RUNNING) {
                bird.jump();
                soundManager.playJump();
            } else if (state == GameState.GAME_OVER) {
                startGame();
                soundManager.playJump();
            }
        }

        if (code == KeyEvent.VK_P && state != GameState.START) {
            pauseGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used.
    }
}
