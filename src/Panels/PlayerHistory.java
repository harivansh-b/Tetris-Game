package Panels;
import Game.App;
import UserInfo.FileCheck;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class PlayerHistory extends JPanel {
    String usrname;
    private Image backGroundImage;

    public PlayerHistory(CardLayout cardLayout,JPanel cardPanel) {
        usrname = App.getUsername();
        setLayout(null); 
        setSize(500, 750);
        setBackground(Color.BLACK);

        ImageIcon Image=new ImageIcon("lib/HistoryBack.jpg");
        backGroundImage=Image.getImage();

        JLabel heading = new JLabel("HISTORY");
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

        heading.setFont(Copperplatefont1);
        heading.setForeground(new Color(0,0,139));
        heading.setBounds(158, 25, 200, 40);
        add(heading);

        JLabel diff = createColoredLabel("DIFFICULTY", Color.RED , Copperplatefont2);
        diff.setBounds(140, 200, 190, 40);
        add(diff);

        JLabel scor = createColoredLabel("SCORE", Color.RED , Copperplatefont2);
        scor.setBounds(330, 200, 100, 40);
        add(scor);

        JLabel no = createColoredLabel("NO", Color.RED , Copperplatefont2);
        no.setBounds(70, 200, 70, 40);
        add(no);

        ImageIcon icon = new ImageIcon("lib/previous.png");

        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(App.getGuestorlogin()==0){
                    cardLayout.show(cardPanel,"guestDirectedPanel");
                }
                else{
                    cardLayout.show(cardPanel,"loginDirectedPanel");
                }
            }
        });
        add(previousButton);

        ArrayList<String> history = FileCheck.gameHistory(App.getUsername());
        int y = 240;
        for (int i = 0; i < history.size(); i++) {
            if (!history.get(i).startsWith("Unknown")) {
                String[] arr = history.get(i).split(" ");
                JLabel label1 = createColoredLabel(arr[0], Color.BLUE , Copperplatefont2);
                label1.setBounds(140, y, 190, 50);

                JLabel label2 = createColoredLabel(arr[2], Color.BLUE , Copperplatefont2);
                label2.setBounds(330, y, 100, 50);

                JLabel label3 = createColoredLabel(Integer.toString(i + 1), Color.BLUE , Copperplatefont2);
                label3.setBounds(70, y, 70, 50);

                add(label1);
                add(label2);
                add(label3);

                y += 50; 
            } else {
                break;
            }
        }
        setVisible(true);
    }

    private JLabel createColoredLabel(String text, Color bgColor , Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bgColor); 
        label.setForeground(Color.WHITE);
        label.setFont(font);
        label.setBorder(BorderFactory.createLineBorder(new Color(255,140,0), 2)); 
        return label;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}