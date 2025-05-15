import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    Bird bird;
    ArrayList<Pipe> pipes;

    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    int pipeWidth = 64;
    int pipeHeight = 512;

    int velocityX = -3;
    int velocityY = 0;
    int gravity = 1;

    Timer gameLoop;
    Timer placePipeTimer;

    boolean gameOver = false;
    boolean gameStarted = false;
    double score = 0;

    Random random = new Random();

    public FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load assets
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);
        pipes = new ArrayList<>();

        placePipeTimer = new Timer(1500, e -> placePipes());
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
    }

    void placePipes() {
        int pipeX = boardWidth;
        int pipeY = 0;

        int randomPipeY = pipeY - pipeHeight / 4 - random.nextInt(pipeHeight / 2);
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(pipeX, randomPipeY, pipeWidth, pipeHeight, topPipeImg);
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(pipeX, randomPipeY + pipeHeight + openingSpace, pipeWidth, pipeHeight, bottomPipeImg);
        pipes.add(bottomPipe);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        bird.draw(g);

        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (!gameStarted) {
            g.drawString("Press SPACE to Start", 30, boardHeight / 2 - 50);
        } else if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        for (Pipe pipe : pipes) {
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5;
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
            placePipeTimer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == MouseEvent.BUTTON1) {
            if (!gameStarted) {
                gameStarted = true;
                gameLoop.start();
                placePipeTimer.start();
            }

            velocityY = -9;

            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameStarted = false;
                repaint();
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
