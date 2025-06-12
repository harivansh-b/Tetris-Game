package Panels;
import Tetris.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import Game.App;
public class DifficultyPanel extends JPanel {
    private Image backGroundImage;
    public DifficultyPanel(CardLayout cardLayout,JPanel cardPanel) {
        Font Copperplatefont = new Font("Copperplate",Font.PLAIN,30);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 30f);
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Copperplatefont);
            
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        ImageIcon Image=new ImageIcon("lib/DifficultyBack.jpg");
        backGroundImage=Image.getImage();

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.setFont(Copperplatefont);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(App.getGuestorlogin()==0){
                    cardLayout.show(cardPanel,"guestDirectedPanel");
                }
                else{
                    cardLayout.show(cardPanel,"loginDirectedPanel");
                }
                
            }
        });
        add(previousButton);

        JButton easyButton = new JButton("Easy");
        easyButton.setFont(Copperplatefont);
        easyButton.setBounds(135, 200, 220, 50);
        easyButton.setForeground(Color.WHITE);
        easyButton.setBackground(new Color(86, 84, 88));
        easyButton.setOpaque(true);
       //easyButton.setBorderPainted(false);
        easyButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.setDifficulty(1);
                cardLayout.show(cardPanel,"gamePanel");
                TetrisPanel.startingGame();
                
            }
        });
        add(easyButton);

        JButton mediumButton = new JButton("Medium");
        mediumButton.setBounds(135, 300, 220, 50);
        mediumButton.setFont(Copperplatefont);
        mediumButton.setForeground(Color.WHITE);
        mediumButton.setBackground(new Color(86, 84, 88));
        mediumButton.setOpaque(true);
       //easyButton.setBorderPainted(false);
       mediumButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.setDifficulty(2);
                cardLayout.show(cardPanel,"gamePanel");
                TetrisPanel.startingGame();
                
            }
        });
        add(mediumButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setBounds(135, 400, 220, 50);
        hardButton.setFont(Copperplatefont);
        hardButton.setForeground(Color.WHITE);
        hardButton.setBackground(new Color(86, 84, 88));
        hardButton.setOpaque(true);
        hardButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.setDifficulty(3);
                cardLayout.show(cardPanel,"gamePanel");
                TetrisPanel.startingGame();
            }
        });
        add(hardButton);

        setLayout(null);
        setSize(500, 750);
        setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
