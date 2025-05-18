import java.awt.Graphics;
import java.awt.Image;

public class Bird {
    public int x, y, width, height;
    public Image img;
    public double velocityY = 0;
    public double gravity = 0.8;

    private double idleTime = 0; // used for animation
    private int startY; // original position to bob around


    public Bird(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.startY = y;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    public void idleUpdate() {
        idleTime += 0.1; // adjust for speed
        y = startY + (int)(5 * Math.sin(idleTime)); // 5px up/down swing
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
