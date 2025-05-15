import java.awt.Graphics;
import java.awt.Image;

public class Bird {
    public int x, y, width, height;
    public Image img;

    public Bird(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }
}
