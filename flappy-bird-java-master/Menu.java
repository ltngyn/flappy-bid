import java.awt.*;

import javax.swing.*;

public class Menu extends JPanel {
    private JButton startButton;
    private JButton helpButton;
    private JButton quitButton;
    private JPanel helpPanel;
    private JButton helpCloseButton;

    public Menu(JFrame parentFrame) {
        setLayout(null);
        setPreferredSize(new Dimension(360, 640));

        //Layer
        JLayeredPane layer = new JLayeredPane();
        layer.setBounds(0, 0, 360, 640);
        add(layer);

        // Background
        JLabel background = new JLabel(new ImageIcon("flappybirdbg.png"));
        background.setBounds(0, 0, 360, 640);
        background.setLayout(null);
        layer.add(background, Integer.valueOf(1));

        //Cloud 
        JLabel cloud = new JLabel(new ImageIcon("cloud.png"));
        cloud.setBounds(0, -80, 360, 640);
        // cloud.setLayout(null);
        layer.add(cloud, Integer.valueOf(2));

        // Title
        JLabel title = new JLabel("FLAPPY BIRD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(50, 50, 300, 50);
        layer.add(title, Integer.valueOf(3));

        // Help panel
        helpPanel = new JPanel();
        helpPanel.setBounds(55, 160, 245, 400);
        helpPanel.setBackground(new Color(198, 234, 242));
        helpPanel.setVisible(false);
        layer.add(helpPanel, Integer.valueOf(7));
        
        // Start button
        startButton = new JButton("START");
        startButton.setBounds(90, 320, 180, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusable(false);
        layer.add(startButton, Integer.valueOf(4));

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

        // Help button
        helpButton = new JButton("HELP");
        helpButton.setBounds(90, 380, 180, 50);
        helpButton.setFont(new Font("Arial", Font.BOLD, 30));
        helpButton.setFocusable(false);
        layer.add(helpButton, Integer.valueOf(5));

        helpButton.addActionListener(e -> {
            helpPanel.setVisible(true);
            helpCloseButton.setVisible(true);
            helpButton.setEnabled(false);
            startButton.setEnabled(false);
            quitButton.setEnabled(false);
        });

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
            startButton.setEnabled(true);
            quitButton.setEnabled(true);
        });

        // Quit button
        quitButton = new JButton("QUIT");
        quitButton.setBounds(90, 440, 180, 50);
        quitButton.setFont(new Font("Arial", Font.BOLD, 30));
        quitButton.setFocusable(false);
        layer.add(quitButton, Integer.valueOf(6));

        quitButton.addActionListener(e -> System.exit(0));
        
    }
}
