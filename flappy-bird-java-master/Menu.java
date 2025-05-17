import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JPanel {
    private JButton startButton;
    private JButton helpButton;
    private JButton quitButton;
    private JPanel helpPanel;

    public Menu(JFrame parentFrame) {
        setLayout(null);
        setPreferredSize(new Dimension(360, 640));

        // Background
        JLabel background = new JLabel(new ImageIcon("flappybirdbg.png"));
        background.setBounds(0, 0, 360, 640);
        background.setLayout(null);
        add(background);

        // Title
        JLabel title = new JLabel("FLAPPY BIRD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(50, 50, 300, 50);
        background.add(title);

        // Help panel
        helpPanel = new JPanel();
        helpPanel.setBounds(72, 160, 210, 400);
        helpPanel.setBackground(Color.GRAY);
        helpPanel.setVisible(false);
        background.add(helpPanel);

        // Start button
        startButton = new JButton("START");
        startButton.setBounds(90, 320, 180, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusable(false);
        background.add(startButton);

        // Help button
        helpButton = new JButton("HELP");
        helpButton.setBounds(90, 380, 180, 50);
        helpButton.setFont(new Font("Arial", Font.BOLD, 30));
        helpButton.setFocusable(false);
        background.add(helpButton);

        helpButton.addActionListener(e -> {
            helpPanel.setVisible(true);
            helpButton.setEnabled(false);
            startButton.setEnabled(false);
            quitButton.setEnabled(false);
        });

        // Quit button
        quitButton = new JButton("QUIT");
        quitButton.setBounds(90, 440, 180, 50);
        quitButton.setFont(new Font("Arial", Font.BOLD, 30));
        quitButton.setFocusable(false);
        background.add(quitButton);

        quitButton.addActionListener(e -> System.exit(0));

        // Start button action
        startButton.addActionListener(e -> {
            parentFrame.dispose(); // Close menu window

            JFrame gameFrame = new JFrame("Flappy Bird");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setResizable(false);
            gameFrame.add(new FlappyBird());
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
        });
    }
}
