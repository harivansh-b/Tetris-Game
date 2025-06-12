package Panels;

import Game.App;
import UserInfo.FileCheck;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;


public class LoginPanel extends JPanel {
    private Image backgroundImage;
    public LoginPanel(CardLayout cardLayout,JPanel cardPanel) {
        ImageIcon image = new ImageIcon("lib/Login_Signup.png");
        backgroundImage = image.getImage();
        Font Copperplatefont1 = new Font("Copperplate",Font.BOLD, 40);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 40f);
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Copperplatefont1);
            
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(null);
        setSize(500, 750);
        
        JLabel heading = new JLabel("LOGIN");
        heading.setFont(Copperplatefont1);
        heading.setForeground(Color.WHITE);
        heading.setBounds(170, 50, 150, 40);
        add(heading);

        Font Copperplatefont2 = new Font("Copperplate",Font.PLAIN, 20);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont2 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 20f);
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Copperplatefont2);
                        
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        JLabel userLabel = new JLabel("USERNAME:");
        userLabel.setFont(Copperplatefont2);
        userLabel.setBounds(40, 150, 120, 40);
        userLabel.setForeground(Color.WHITE);
        add(userLabel);

        JLabel passLabel = new JLabel("PASSWORD:");
        passLabel.setFont(Copperplatefont2);
        passLabel.setBounds(40, 250, 130, 40);
        passLabel.setForeground(Color.WHITE);
        add(passLabel);

        JTextField username = new JTextField();
        username.setBounds(170, 150, 200, 40);
        username.setFont(Copperplatefont2);
        add(username);

        JPasswordField password = new JPasswordField();
        password.setBounds(170, 250, 200, 40);
        password.setEchoChar('*');
        password.setFont(Copperplatefont2);
        add(password);

        Icon showIcon=new ImageIcon("lib/showicon.jpg");
        Icon hideIcon=new ImageIcon("lib/hideicon.jpg");

        JButton show=new JButton(showIcon);
        show.setBounds(380,255,30,30);
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switch(App.getShow()){
                case 0:{
                        password.setEchoChar((char)0);
                        show.setIcon(hideIcon);
                        App.setShow(1);
                        break;
                    }
                    case 1:{
                        password.setEchoChar('*');
                        show.setIcon(showIcon);
                        App.setShow(0);
                        break;
                    }
                }
            }
        });
        add(show);

        JButton confirm = new JButton("CONFIRM");
        confirm.setFont(Copperplatefont2);
        confirm.setBounds(170, 400, 130, 40);
        confirm.setForeground(Color.WHITE);
        confirm.setBackground(new Color(86, 84, 88));
        confirm.setOpaque(true);
        confirm.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pass = new String(password.getPassword());

                int check = FileCheck.isUser(user, pass);
                switch (check) {
                    case 2:{
                        cardLayout.show(cardPanel,"loginDirectedPanel");
                        App.setUsername(user);
                        break;
                    }
                    case 1: createLabel("INVALID PASSWORD, TRY AGAIN", 1500);break;
                    case 0: createLabel("INVALID USERNAME, TRY AGAIN", 1500); break;
                }
            }
        });
        add(confirm);

        JLabel haveAcc = new JLabel("Don't have an account?");
        haveAcc.setBounds(120, 530, 250, 40);
        haveAcc.setFont(Copperplatefont2.deriveFont(Font.PLAIN, 18f));
        haveAcc.setForeground(Color.WHITE);
        add(haveAcc);

        JLabel signup = new JLabel("SIGN UP");
        signup.setForeground(Color.YELLOW);
        signup.setFont(Copperplatefont2.deriveFont(Font.BOLD, 20f));
        signup.setBounds(290, 530, 100, 40);
        signup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel,"signUpPanel");
            }
        });
        add(signup);

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                App.setGuestorlogin(0);
                App.setShow(0);
                username.setText("");
                password.setText("");
                cardLayout.show(cardPanel,"loginGuestPanel");
            }
        });
        add(previousButton);
    }

    public void createLabel(String labelText, int delay) {
        JLabel lbl = new JLabel(labelText);
        Font font = new Font("Copperplate",Font.PLAIN, 20);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 20f);
            
            // Optional: Register the font with the system
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
                        
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        lbl.setBounds(130, 350, 300, 40);
        lbl.setForeground(Color.RED);
        lbl.setFont(font);
        this.add(lbl);
        this.revalidate();
        this.repaint();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                remove(lbl);
                revalidate();
                repaint();
            }
        }, delay);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

