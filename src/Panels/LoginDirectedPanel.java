package Panels;
import java.io.*;
import Game.App;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginDirectedPanel extends JPanel{

    private Image backGroundImage;

    public LoginDirectedPanel(CardLayout cardLayout,JPanel cardPanel){
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


        ImageIcon Image=new ImageIcon("lib/directedBack.jpg");
        backGroundImage=Image.getImage();

        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.setFont(Copperplatefont);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"loginGuestPanel");
                App.setUsername("");
                App.setGuestorlogin(0);
            }
        });
        add(previousButton);

        JButton leaderBoard=new JButton("Leaderboard");
        leaderBoard.setBounds(135,300,220,50);
        leaderBoard.setFont(Copperplatefont);
        leaderBoard.setForeground(Color.WHITE);
        leaderBoard.setBackground(new Color(86, 84, 88));
        leaderBoard.setOpaque(true);
        leaderBoard.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));        
        leaderBoard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel,"leaderBoardChoicePanel");
            }
        });
        add(leaderBoard);

        JButton newGame=new JButton("New Game");
        newGame.setFont(Copperplatefont);
        newGame.setBounds(135,200,220,50);
        newGame.setForeground(Color.WHITE);
        newGame.setBackground(new Color(86, 84, 88));
        newGame.setOpaque(true);
        newGame.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel,"difficultyPanel");
            }
        });
        add(newGame);

        JButton playerHistory=new JButton("History");
        playerHistory.setBounds(135,400,220,50);
        playerHistory.setFont(Copperplatefont);
        playerHistory.setForeground(Color.WHITE);
        playerHistory.setBackground(new Color(86, 84, 88));
        playerHistory.setOpaque(true);
        playerHistory.setBorder(BorderFactory.createLineBorder(new Color(172, 192, 210), 2));
        playerHistory.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cardLayout.show(cardPanel,"playerHistory");
            }
        });
        add(playerHistory);
        setLayout(null);
        setSize(500,750);
        setVisible(true);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}