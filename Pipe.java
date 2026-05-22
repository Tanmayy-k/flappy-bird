import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 512;

    private final BufferedImage sprite;
    private final boolean top;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private boolean passed;

    public Pipe(BufferedImage sprite, int x, int y, boolean top) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.top = top;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.passed = false;
    }

    public void update(int speed) {
        x -= speed;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }

    public boolean collides(Bird bird) {
        return getBounds().intersects(bird.getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public int getX() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
