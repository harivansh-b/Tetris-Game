package Panels;
import Game.App;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginGuestPanel extends JPanel{
    private Image backGroundImage;
    public LoginGuestPanel(CardLayout cardLayout,JPanel cardPanel){


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


        ImageIcon Image=new ImageIcon("lib/LoginOrGuestBack.jpg");
        backGroundImage=Image.getImage();

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"openPagePanel");
            }
        });
        add(previousButton);


        JButton login=new JButton("Login");
        login.setBounds(175,400,130,40);
        login.setFont(Copperplatefont);
        login.setForeground(Color.WHITE);
        login.setBackground(new Color(86, 84, 88));
        login.setOpaque(true);
        login.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel,"loginPanel");
                App.setGuestorlogin(1);
            }
        });
        add(login);

        JButton guest=new JButton("Guest");
        guest.setBounds(175,500,130,40);
        guest.setFont(Copperplatefont);
        guest.setForeground(Color.WHITE);
        guest.setBackground(new Color(86, 84, 88));
        guest.setOpaque(true);
        guest.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        guest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
               cardLayout.show(cardPanel,"guestDirectedPanel");
            }
        });
        add(guest);
        
        setLayout(null);
        setSize(500,750);
        setVisible(true);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}
