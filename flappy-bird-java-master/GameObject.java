import java.awt.Graphics;
import java.awt.Image;

public abstract class GameObject {
    public int x, y, width, height;
    public Image img;

    public GameObject(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
    }

    public abstract void draw(Graphics g);
}
