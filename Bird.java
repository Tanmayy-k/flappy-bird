import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bird {
    public static final int WIDTH = 34;
    public static final int HEIGHT = 24;

    private final BufferedImage sprite;
    private double x;
    private double y;
    private double velocityY;
    private double angle;
    private int animationTick;

    public Bird(BufferedImage sprite, int startX, int startY) {
        this.sprite = sprite;
        this.x = startX;
        this.y = startY;
        this.velocityY = 0;
        this.angle = 0;
        this.animationTick = 0;
    }

    public void update() {
        velocityY = Math.min(velocityY + GamePanel.GRAVITY, GamePanel.MAX_DROP_SPEED);
        y += velocityY;
        setRotationAngle();
        animationTick++;
    }

    public void jump() {
        velocityY = GamePanel.JUMP_VELOCITY;
        animationTick = 0;
    }

    public void reset(int startX, int startY) {
        x = startX;
        y = startY;
        velocityY = 0;
        angle = 0;
        animationTick = 0;
    }

    public boolean isOutOfBounds() {
        return y < 0 || y + HEIGHT >= GamePanel.HEIGHT;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
    }

    public int getX() {
        return (int) Math.round(x);
    }

    public int getY() {
        return (int) Math.round(y);
    }

    public int getRight() {
        return getX() + WIDTH;
    }

    private void setRotationAngle() {
        angle = -velocityY * 3;
        angle = Math.max(-GamePanel.MAX_BIRD_ANGLE, Math.min(GamePanel.MAX_BIRD_ANGLE, angle));
    }

    public void draw(Graphics2D g) {
        double centerX = getX() + WIDTH / 2.0;
        double centerY = getY() + HEIGHT / 2.0;
        double flapScale = 1.0 + Math.sin(animationTick * 0.25) * 0.04;

        AffineTransform transform = g.getTransform();
        g.translate(centerX, centerY);
        g.rotate(Math.toRadians(angle));
        g.scale(flapScale, flapScale);
        g.drawImage(sprite, -WIDTH / 2, -HEIGHT / 2, WIDTH, HEIGHT, null);
        g.setTransform(transform);
    }
}
