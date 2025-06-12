package Tetris;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class TetrisPanel extends JPanel {
    private static GameArea gameArea = new GameArea();;
    
    public TetrisPanel(CardLayout cardLayout,JPanel cardPanel) {
        setLayout(null);
        initializeComponents(cardLayout,cardPanel);
    }

    private void initializeComponents(CardLayout cardLayout,JPanel cardPanel) {
        // Background Image Panel
        Image backgroundImage = new ImageIcon(("lib/GameBack.jpeg")).getImage();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, 500, 750); // Set size as needed
        add(backgroundPanel);

        // Game Area
        
        backgroundPanel.add(gameArea);

        Font Copperplatefont = new Font("Copperplate",Font.BOLD,15);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 20f );
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Copperplatefont);
            
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // Next Block Label
        JLabel nextBlockLabel = new JLabel("<html><font color = WHITE><b>NEXT BLOCK :</b></font></html>");
        nextBlockLabel.setFont(Copperplatefont);
        nextBlockLabel.setBounds(350, 130, 250, 100);
        backgroundPanel.add(nextBlockLabel);
        backgroundPanel.add(gameArea.nextBlockPanel);

        // Score Label
        gameArea.scoreLabel.setBounds(350, 350, 100, 30); 
        backgroundPanel.add(gameArea.scoreLabel);

        // Level Cleared Label
        gameArea.levelLabel.setBounds(350, 400, 150, 30); 
        backgroundPanel.add(gameArea.levelLabel);

        // Previous Button
        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
    
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameArea.stopGame();
                cardLayout.show(cardPanel,"difficultyPanel");
            }
        });
        backgroundPanel.add(previousButton);
        // Play Button
        JButton playButton = createButton("lib/play.png", 310, 500, e -> gameArea.play());
        backgroundPanel.add(playButton);

        // Pause Button
        JButton pauseButton = createButton("lib/pause.jpg", 375, 500, e -> gameArea.pause());
        backgroundPanel.add(pauseButton);

        // Refresh Button
        JButton refreshButton = createButton("lib/refresh.png", 440, 500, e -> gameArea.refresh(gameArea));
        backgroundPanel.add(refreshButton);

        add(backgroundPanel);

    }

    private JButton createButton(String iconPath, int x, int y, ActionListener action) {
        ImageIcon icon = new ImageIcon(iconPath);
        JButton button = new JButton(icon);
        button.setBounds(x, y, 30, 30);
        button.addActionListener(action);
        return button;
    }

    public static void startingGame(){
        // Start the game
        gameArea.startGame(gameArea);
    }

    public static void exitingGame(){
        // Exit the game
        gameArea.stopGame();
    }
}
