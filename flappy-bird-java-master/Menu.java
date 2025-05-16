import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Menu {
    public static void main(String[] args) {
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(boardWidth, boardHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); // IMPORTANT for absolute positioning

        //use to layer everything in order
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, boardWidth, boardHeight);


        // App icon
        ImageIcon icon = new ImageIcon("flappybird.png");
        frame.setIconImage(icon.getImage());

        // Background image label
        JLabel background = new JLabel();
        ImageIcon image = new ImageIcon("flappybirdbg.png");
        background.setIcon(image);
        background.setBounds(0, 0, boardWidth, boardHeight); // Full background
        background.setLayout(null); // Allow placing components over label

        // Title text
        JLabel title = new JLabel("FLAPPY BIRD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(50, 50, 300, 50); // Position on screen

        //Help screen
        JPanel helpPanel = new JPanel();
        helpPanel.setBounds(72, 160, 210, 400); // Increased size for visibility
        helpPanel.setBackground(Color.GRAY);
        helpPanel.setVisible(false);

        // Start button
        JButton startButton = new JButton("START");
        startButton.setBounds(90, 320, 180, 50); // Adjust position & size
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusable(false);        
        // startButton.setFocusPainted(false);
        // startButton.setBorderPainted(true);
        // startButton.setOpaque(false);


        //Help button
        JButton helpButton = new JButton("HELP");
        helpButton.setBounds(90, 380, 180, 50);
        helpButton.setFont(new Font("Arial", Font.BOLD, 30));
        helpButton.setFocusable(false);
        
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                helpPanel.setVisible(true);
            }
        }); 

        

        //Quit button
        JButton endButton = new JButton("QUIT");
        endButton.setBounds(90, 440, 180, 50);
        endButton.setFont(new Font("Arial", Font.BOLD, 30));
        endButton.setFocusable(false);
        //endButton.setEnabled(false); //this disable a button

        endButton.addActionListener(e -> System.exit(0));
        
        layeredPane.add(background, Integer.valueOf(1));
        layeredPane.add(title, Integer.valueOf(2));
        layeredPane.add(startButton, Integer.valueOf(3));
        layeredPane.add(helpButton, Integer.valueOf(4));
        layeredPane.add(endButton, Integer.valueOf(5));
        layeredPane.add(helpPanel, Integer.valueOf(6));

        frame.add(layeredPane);

        frame.setVisible(true);
    }

}
