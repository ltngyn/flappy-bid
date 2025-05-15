import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

        // App icon
        ImageIcon icon = new ImageIcon("flappybird.png");
        frame.setIconImage(icon.getImage());

        // Background image label
        JLabel label = new JLabel();
        ImageIcon image = new ImageIcon("flappybirdbg.png");
        label.setIcon(image);
        label.setBounds(0, 0, boardWidth, boardHeight); // Full background
        label.setLayout(null); // Allow placing components over label

        // Title text
        JLabel title = new JLabel("FLAPPY BIRD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setBounds(50, 50, 300, 50); // Position on screen

        // Start button
        JButton startButton = new JButton("START");
        startButton.setBounds(90, 300, 180, 50); // Adjust position & size
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(false);

        //Quit button
        JButton endButton = new JButton("QUIT");
        endButton.setBounds(90, 380, 180, 50);
        endButton.setFont(new Font("Arial", Font.BOLD, 30));
        endButton.setFocusPainted(false);
        endButton.setBorderPainted(false);
        endButton.setOpaque(false);

        // Add components in proper order
        label.add(title);
        label.add(startButton);
        label.add(endButton);
        frame.setContentPane(label); // Set background as content pane

        frame.setVisible(true);
    }
}
