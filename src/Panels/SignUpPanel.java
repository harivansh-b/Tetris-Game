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

import Exceptions.FileReadException;

public class SignUpPanel extends JPanel {
    private Image backgroundImage;
    public SignUpPanel(CardLayout cardLayout, JPanel cardPanel) {
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

        JLabel heading = new JLabel("SIGN UP");
        heading.setFont(Copperplatefont1);
        heading.setBounds(180, 50, 190, 40);
        heading.setForeground(Color.WHITE);
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
        userLabel.setBounds(20, 150, 120, 40);
        userLabel.setForeground(Color.WHITE);
        add(userLabel);

        JLabel passLabel = new JLabel("PASSWORD:");
        passLabel.setFont(Copperplatefont2);
        passLabel.setBounds(20, 250, 130, 40);
        passLabel.setForeground(Color.WHITE);
        add(passLabel);

        JLabel rety = new JLabel("RE-ENTER PASSWORD:");
        rety.setFont(Copperplatefont2);
        rety.setBounds(20, 350, 200, 40);
        rety.setForeground(Color.WHITE);
        add(rety);

        JTextField username = new JTextField();
        username.setBounds(220, 150, 200, 40);
        username.setFont(Copperplatefont2);
        add(username);

        JPasswordField password = new JPasswordField();
        password.setBounds(220, 250, 200, 40);
        password.setEchoChar('*');
        add(password);

        JPasswordField retype = new JPasswordField();
        retype.setBounds(220, 350, 200, 40);
        retype.setEchoChar('*');
        add(retype);

        JPanel instructionsPanel = new JPanel(new GridLayout(0, 1));
        instructionsPanel.setBounds(45, 450, 405, 107);
        add(instructionsPanel);

        String[] instructions = {
            "• Username must be unique",
            "• Username should not have whitespaces",
            "• Password length must be between 8 to 16 characters",
            "• Password must have at least one uppercase letter",
            "• Password must have at least one lowercase letter",
            "• Password must have at least one digit",
            "• Password must have at least one special character (e.g @,*,...)"
        };
        for (String instruction : instructions) {
            JLabel label = new JLabel(instruction);
            label.setFont(Copperplatefont2.deriveFont(Font.PLAIN , 15f));
            instructionsPanel.add(label);
        }

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginPanel");
                App.setUsername("");
            }
        });
        add(previousButton);

        JButton confirm = new JButton("CONFIRM");
        confirm.setFont(Copperplatefont2);
        confirm.setBounds(170, 600, 130, 40);
        confirm.setForeground(Color.WHITE);
        confirm.setBackground(new Color(86, 84, 88));
        confirm.setOpaque(true);
        confirm.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                String pass = new String(password.getPassword());
                String retyped = new String(retype.getPassword());
                try{
                    if (user.equals("") || user.contains(" ")) {
                        createLabel("username cannot have whitespaces", 1500);
                    } 
                    else if (FileCheck.isPresent(user)) {
                        createLabel("username already exists", 1700);
                    } 
                    else if (!FileCheck.passCriteria(pass)) {
                        createLabel("password criteria not met", 1700);
                    } 
                    else if (!pass.equals(retyped)) {
                        createLabel("Re-entered password doesn't match", 1700);
                    } 
                    else {
                        String check = FileCheck.signUp(user, pass);
                        createLabel(check, 1700);
                        username.setText("");
                        password.setText("");
                        retype.setText("");
                        App.setUsername(user);
                        App.setGuestorlogin(1);
                        if (check.equals("Sign up successful")) {
                            new Timer().schedule(new TimerTask() {
                                public void run() {
                                    cardLayout.show(cardPanel, "loginDirectedPanel");
                                }
                            }, 2000);
                        }
                    }
                }
                catch(FileReadException f){
                    System.out.println(e);
                }
            }
        });
        add(confirm);
    }

    public void createLabel(String labelText, int delay) {
        JLabel lbl = new JLabel(labelText);
        lbl.setBounds(100, 400, 300, 40);
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("copperplate", Font.ITALIC, 16));
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
