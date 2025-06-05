import java.awt.Graphics;
import java.awt.Image;

public class Pipe extends GameObject implements Updatable{
    public boolean passed = false;
    private float velocityX = -3;

    public Pipe(int x, int y, int width, int height, Image img) {
        super(x, y, width, height, img);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    @Override
    public void update() {
        x += velocityX;
    }
}
