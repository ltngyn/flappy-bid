import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Menu extends GameScreen{
    private JFrame frame;

    private JButton startButton;
    private JButton helpButton;
    private JButton quitButton;
    private JPanel helpPanel;
    private JButton helpCloseButton;
    private boolean help = false;

    public Menu(JFrame frame) {
        this.frame = frame;
        super(frame);
        setupUI();
    }

    //Load UI
    @Override
    public void setupUI() {
        //Layer
        JLayeredPane layer = new JLayeredPane();
        layer.setBounds(0, 0, 360, 640);
        add(layer);

        // Background
        JLabel background = new JLabel(new ImageIcon("flappybirdbg.png"));
        background.setBounds(0, 0, 360, 640);
        background.setLayout(null);
        layer.add(background, Integer.valueOf(1));

        // Two cloud layers for seamless looping
        JLabel cloud1 = new JLabel(new ImageIcon("cloud.png"));
        cloud1.setBounds(0, -80, 360, 640);
        layer.add(cloud1, Integer.valueOf(2));

        JLabel cloud2 = new JLabel(new ImageIcon("cloud.png"));
        cloud2.setBounds(360, -80, 360, 640);
        layer.add(cloud2, Integer.valueOf(2));

        // Timer to move both clouds leftward for a seamless scroll
        Timer cloudTimer = new Timer(20, e -> {
            int speed = 1;

            cloud1.setLocation(cloud1.getX() - speed, cloud1.getY());
            cloud2.setLocation(cloud2.getX() - speed, cloud2.getY());

            // Reset positions when a cloud moves off screen
            if (cloud1.getX() + 360 <= 0) {
                cloud1.setLocation(cloud2.getX() + 360, cloud1.getY());
            }
            if (cloud2.getX() + 360 <= 0) {
                cloud2.setLocation(cloud1.getX() + 360, cloud2.getY());
            }
        });
        cloudTimer.start();

        // Title
        JLabel title = new JLabel("FLAPPY BIRD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(50, 50, 300, 50);
        layer.add(title, Integer.valueOf(3));
        
        // Start button
        startButton = new JButton("START");
        startButton.setBounds(90, 320, 180, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusable(false);
        startButton.setEnabled(true);
        layer.add(startButton, Integer.valueOf(4));

        // Start button action
        startButton.addActionListener(e -> {
            launchGame();
        });

        // Help button
        helpButton = new JButton("HELP");
        helpButton.setBounds(90, 380, 180, 50);
        helpButton.setFont(new Font("Arial", Font.BOLD, 30));
        helpButton.setFocusable(false);
        layer.add(helpButton, Integer.valueOf(5));

        helpButton.addActionListener(e -> {
            help = true;
            helpPanel.setVisible(true);
            helpCloseButton.setVisible(true);
            helpButton.setEnabled(false);
            // startButton.setEnabled(false);
            quitButton.setEnabled(false);
        });

        // Help panel
        helpPanel = new JPanel();
        helpPanel.setBounds(55, 160, 245, 400);
        helpPanel.setBackground(new Color(198, 234, 242));
        helpPanel.setVisible(false);
        helpPanel.setLayout(null); // Important for absolute positioning
        layer.add(helpPanel, Integer.valueOf(7));

        // Help panel title
        JLabel helpTittle = new JLabel("TUTORIAL");
        helpTittle.setFont(new Font("Arial", Font.ITALIC, 29));
        helpTittle.setBounds(40, 10, 200, 40); // Now positioned manually
        helpPanel.add(helpTittle);

        // Font and color
        Font font = new Font("Arial", Font.PLAIN, 23);
        Color color = new Color(26, 57, 100);

        // Help text
        String[] helpLine = {
            "Press SPACE or",
            "LEFT MOUSE ",
            "BUTTON to PLAY.",
            "ESC or RIGHT.",
            "MOUSE BUTTON",
            "to PAUSE."
        };

        // Add text labels
        for (int i = 0; i < helpLine.length; i++) {
            JLabel text = new JLabel(helpLine[i]);
            text.setFont(font);
            text.setForeground(color);
            text.setBounds(20, 60 + i * 40, 225, 30);
            helpPanel.add(text);
        }

        // Close help panel button 
        helpCloseButton = new JButton(new ImageIcon("xCloseIcon.png"));
        helpCloseButton.setBounds(265, 165, 30, 30);
        helpCloseButton.setFocusable(false);
        helpCloseButton.setBackground(new Color(250, 55, 55)); 
        helpCloseButton.setVisible(false);  
        layer.add(helpCloseButton, Integer.valueOf(8));

        helpCloseButton.addActionListener(e -> {
            helpPanel.setVisible(false);
            helpCloseButton.setVisible(false);
            helpButton.setEnabled(true);
            // startButton.setEnabled(true);
            quitButton.setEnabled(true);
            help = false;
        });

        // Quit button
        quitButton = new JButton("QUIT");
        quitButton.setBounds(90, 440, 180, 50);
        quitButton.setFont(new Font("Arial", Font.BOLD, 30));
        quitButton.setFocusable(false);
        layer.add(quitButton, Integer.valueOf(6));

        quitButton.addActionListener(e -> System.exit(0));
    }

    public void launchGame() {
        frame.dispose();

        JFrame gameFrame = new JFrame("Flappy Bird");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.add(new FlappyBird(gameFrame));
        gameFrame.pack();   
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if (help) {
                helpPanel.setVisible(false);
                helpCloseButton.setVisible(false);
                helpButton.setEnabled(true);
                // startButton.setEnabled(true);
                quitButton.setEnabled(true);
                help = false;
            } else {
                System.exit(0);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(!help) {
                launchGame();
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
