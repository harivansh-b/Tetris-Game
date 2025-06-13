package Panels;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HowToPlayPanel extends JPanel {
    private Image backgroundImage;

    public HowToPlayPanel(CardLayout cardLayout, JPanel cardPanel) {
        // Load background image
        ImageIcon image = new ImageIcon("lib/HowToPlay.jpg");
        backgroundImage = image.getImage();

        // Set layout and size first
        setLayout(null);
        setPreferredSize(new Dimension(500, 750));

        // Load previous icon and create button
        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.setBorderPainted(false);
        previousButton.setContentAreaFilled(false);
        previousButton.setFocusPainted(false);
        previousButton.setOpaque(false);

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"openPagePanel");
            }
        });

        // Add button after layout is set
        add(previousButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
