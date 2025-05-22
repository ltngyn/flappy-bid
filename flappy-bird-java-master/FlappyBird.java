import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener, MouseListener{
    int boardWidth = 360;
    int boardHeight = 640;

    Image movingBackgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    JLabel countdownLabel;

    boolean pause = false;
    JPanel pausePanel;
    JButton continueButton, restartButton, menuButton, quitButton;

    JPanel resultPanel;
    JButton resultRestartButton, resultMenuButton;

    Bird bird;
    ArrayList<Pipe> pipes;

    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    int bgX1 = 0;
    int bgX2 = boardWidth;
    float bgScrollSpeed = 1f;

    int cloudX1, cloudX2;
    int cloudY = 100; // adjust vertically
    int cloudWidth = 128;
    int cloudHeight = 64;
    float cloudScrollSpeed = 0.5f;

    int pipeWidth = 64;
    int pipeHeight = 512;

    float velocityX = -3;
    float velocityY = 0;
    double gravity = 0.8;

    Timer gameLoop;
    Timer placePipeTimer;

    boolean gameOver = false;
    boolean gameStarted = false;
    double score = 0;

    Random random = new Random();

    public FlappyBird(JFrame gameFrame) {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // Load assets
        movingBackgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdX, birdY, birdWidth, birdHeight, birdImg);
        pipes = new ArrayList<>();

        placePipeTimer = new Timer(1500, e -> placePipes());
        placePipeTimer.start();

        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();
   

        pausePanel = new JPanel();
        pausePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        pausePanel.setBackground(new Color(0, 0, 0, 200)); // semi-transparent black
        pausePanel.setBounds(80, 250, 200, 150); // position in the middle of the game
        pausePanel.setVisible(false);
        pausePanel.setOpaque(true);

        continueButton = new JButton("Continue");
        restartButton = new JButton("Restart");
        menuButton = new JButton("Main Menu");
        quitButton = new JButton("Quit");

        // Set button size
        Dimension buttonSize = new Dimension(150, 25);
        continueButton.setPreferredSize(buttonSize);
        restartButton.setPreferredSize(buttonSize);
        menuButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 36));
        countdownLabel.setForeground(Color.WHITE);
        countdownLabel.setVisible(false);
        countdownLabel.setBounds(0, 200, boardWidth, 50); // Centered horizontally
        add(countdownLabel); // Add to the FlappyBird panel
    
        pausePanel.add(continueButton);
        pausePanel.add(restartButton);
        pausePanel.add(menuButton);
        pausePanel.add(quitButton);

        // Add buttons’ functionality
        continueButton.addActionListener(e -> {
            if (gameStarted) {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithCountdown();
                }
            } else {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithoutCountdown();
                }
            }    
        });


        restartButton.addActionListener(e -> {
            pause = false;
            pausePanel.setVisible(false);
            gameOver = false;
            gameStarted = false;
            pipes.clear();
            score = 0;
            bird.reset(birdY);
            repaint();
        });

        menuButton.addActionListener(e -> {
            // Stop timers and clean up listeners
            cleanUp();

            // Remove the game panel from the frame
            gameFrame.getContentPane().removeAll();

            // Add the Menu panel to the same frame
            gameFrame.add(new Menu(gameFrame));

            // Refresh the frame to show the menu
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        resultPanel = new JPanel();
        resultPanel.setLayout(null);
        resultPanel.setBackground(new Color(0, 190, 23)); // semi-transparent black
        resultPanel.setBounds(45, 150, 280, 220); // position in the middle of the game
        resultPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        resultPanel.setVisible(false);
        resultPanel.setOpaque(true);

        resultRestartButton = new JButton();
        resultRestartButton.setBounds( 145, 180, 125, 30);
        resultRestartButton.setLayout(null);
        resultRestartButton.setFocusable(false);
        resultPanel.add(resultRestartButton);

        resultMenuButton = new JButton();
        resultMenuButton.setBounds(10, 180, 125, 30);
        resultMenuButton.setLayout(null);
        resultMenuButton.setFocusable(false);
        resultPanel.add(resultMenuButton);


        // Use null layout to position pause panel freely
        setLayout(null);
        
        add(pausePanel);
        add(resultPanel);
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
        g.drawImage(movingBackgroundImg, bgX1, 0, boardWidth, boardHeight, null);
        g.drawImage(movingBackgroundImg, bgX2, 0, boardWidth, boardHeight, null);

        // g.drawImage(movingBackgroundImg, 0, 0, boardWidth, boardHeight, null);

        bird.draw(g);

        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (!gameStarted) {
            g.drawString("READY!!!", 120, boardHeight / 2 - 50);
        } else if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 45);
        } else {
            g.drawString(String.valueOf((int) score), 10, 45);
        }
    }

    public void move() {
        if (gameStarted) {
            bird.update(); // Only update bird when game has started

            for (Pipe pipe : pipes) {
                pipe.update(); // move the pipe

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

            // Background scroll
            bgX1 -= bgScrollSpeed;
            bgX2 -= bgScrollSpeed;

            if (bgX1 + boardWidth <= 0) {
                bgX1 = bgX2 + boardWidth;
            }
            if (bgX2 + boardWidth <= 0) {
                bgX2 = bgX1 + boardWidth;
            }
        } else {
            bird.idleUpdate();
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
        if (!pause) {
            move();
            repaint(); 
            if (gameOver) {
                gameLoop.stop();
                placePipeTimer.stop();
                resultPanel.setVisible(true);
            }           
        }
    }

    //Press SPACE to play 
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
                gameLoop.start();
                placePipeTimer.start();
                
            }

            bird.jump();

            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameStarted = false;
                resultPanel.setVisible(false);
                repaint();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameOver) {
            if (gameStarted) {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithCountdown();
                }
            } else {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithoutCountdown();
                }
            }    
        }
    }

    //Left mouse to play
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (!gameStarted) {
                gameStarted = true;
                gameLoop.start();
                placePipeTimer.start();
            }

            bird.jump();

            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameStarted = false;
                resultPanel.setVisible(false);
                repaint();
            }
        }
        if (e.getButton() == MouseEvent.BUTTON3 && !gameOver) {
            if (gameStarted) {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithCountdown();
                }
            } else {
                pause = !pause;

                if (pause) {
                    pauseGame();
                } else {
                    resumeGameWithoutCountdown();
                }
            }    
        }
    }

    public void pauseGame() {
        gameLoop.stop();
        placePipeTimer.stop();
        pausePanel.setVisible(true);
    }

    public void resumeGameWithCountdown() {
        continueButton.setEnabled(false);
        restartButton.setEnabled(false);
        menuButton.setEnabled(false);
        quitButton.setEnabled(false);
        pausePanel.setVisible(false);

        countdownLabel.setText("3");
        countdownLabel.setVisible(true);
        final int[] count = {3};
        Timer countdownTimer = new Timer(1000, null);
        countdownTimer.addActionListener(evt -> {
            count[0]--;
            if (count[0] > 0) {
                countdownLabel.setText(String.valueOf(count[0]));
            } else {
                // Countdown finished, resume game
                countdownTimer.stop();
                countdownLabel.setVisible(false);
                // pausePanel.setVisible(false);
                pause = false;
                gameLoop.start();
                placePipeTimer.start();
                continueButton.setEnabled(true);
                restartButton.setEnabled(true);
                menuButton.setEnabled(true);
                quitButton.setEnabled(true);
            }
        });
        countdownTimer.setInitialDelay(1000); // start after 1 sec
        countdownTimer.start();
    }

    public void resumeGameWithoutCountdown() {
        gameLoop.start();
        placePipeTimer.start();
        pausePanel.setVisible(false);
    }

    public void cleanUp() {
        gameLoop.stop();
        placePipeTimer.stop();
        removeKeyListener(this);
        removeMouseListener(this);
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
