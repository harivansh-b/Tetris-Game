package Panels;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class OpenPagePanel extends JPanel {
    private Image backGroundImage;
    public OpenPagePanel(CardLayout cardLayout,JPanel cardPanel) {
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

        ImageIcon Image=new ImageIcon("lib/OpenPageBack.jpg");
        backGroundImage=Image.getImage();

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.setFont(Copperplatefont);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(previousButton);

        JButton playButton = new JButton("Play");
        playButton.setFont(Copperplatefont);
        playButton.setBounds(135, 250, 220, 50);
        playButton.setForeground(Color.WHITE);
        playButton.setBackground(new Color(86, 84, 88));
        playButton.setOpaque(true);
        playButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"loginGuestPanel");
            }
        });
        add(playButton);

        JButton howToPlayButton = new JButton("How to Play");
        howToPlayButton.setBounds(135, 350, 220, 50);
        howToPlayButton.setFont(Copperplatefont);
        howToPlayButton.setForeground(Color.WHITE);
        howToPlayButton.setBackground(new Color(86, 84, 88));
        howToPlayButton.setOpaque(true);
        howToPlayButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        howToPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"howToPlayPanel");
            }
        });
        add(howToPlayButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(135, 450, 220, 50);
        exitButton.setFont(Copperplatefont);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(86, 84, 88));
        exitButton.setOpaque(true);
        exitButton.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitButton);

        setLayout(null);
        setSize(500, 750);
        setVisible(true);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}

