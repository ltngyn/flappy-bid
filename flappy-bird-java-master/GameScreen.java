import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public abstract class GameScreen extends JPanel implements KeyListener{
    protected JFrame frame;

    public GameScreen(JFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(360, 640));
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
    }

    public abstract void setupUI(); // Each screen must define its own UI
}
