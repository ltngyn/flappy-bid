import java.awt.Graphics;
import java.awt.Image;

public class Bird extends GameObject {
    public double velocityY = 0;
    public double gravity = 0.8;

    private double idleTime = 0;
    private int startY;

    public Bird(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
        this.startY = y;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public void idleUpdate() {
        idleTime += 0.1;
        y = startY + (int)(5 * Math.sin(idleTime));
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;
        y = Math.max(y, 0);
    }

    public void jump() {
        velocityY = -9;
    }

    public void reset(int startY) {
        y = startY;
        velocityY = 0;
    }
}
