package Panels;
import UserInfo.FileCheck;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;

import Game.App;

class LeaderChoice extends JPanel {
    private Image backGroundImage;
    public LeaderChoice(CardLayout lcardLayout, JPanel lcardPanel,CardLayout cardLayout,JPanel cardPanel) {
        setLayout(null);
        Font Copperplatefont1 = new Font("Copperplate",Font.BOLD, 30);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);
            
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

        ImageIcon Image=new ImageIcon("lib/LeaderBack.jpg");
        backGroundImage=Image.getImage();
        JLabel heading = new JLabel("LEADERBOARD");
        heading.setBounds(155, 100, 250, 40);
        heading.setForeground(Color.WHITE);
        heading.setFont(Copperplatefont1);
        add(heading);

        ImageIcon icon = new ImageIcon("lib/previous.png");

        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(App.getGuestorlogin()==0)
                    cardLayout.show(cardPanel,"guestDirectedPanel");
                else
                    cardLayout.show(cardPanel,"loginDirectedPanel");
            }
        });
        add(previousButton);

        JButton easy = new JButton("Easy");
        easy.setBounds(190, 200, 120, 40);
        easy.setFont(Copperplatefont2);
        easy.addActionListener(e -> lcardLayout.show(lcardPanel, "Easy"));
        easy.setForeground(Color.WHITE);  
        easy.setBackground(Color.BLACK);   
        easy.setOpaque(true);    
        easy.setBorderPainted(false); 
        add(easy);

        JButton medium = new JButton("Medium");
        medium.setBounds(190, 300, 120, 40);
        medium.setFont(Copperplatefont2);
        medium.setForeground(Color.WHITE);
        medium.setBackground(Color.BLACK);
        medium.setOpaque(true);
        medium.setBorderPainted(false);
        medium.addActionListener(e -> lcardLayout.show(lcardPanel, "Medium"));
        add(medium);

        JButton hard = new JButton("Hard");
        hard.setBounds(190, 400, 120, 40);
        hard.setFont(Copperplatefont2);
        hard.setForeground(Color.WHITE);
        hard.setBackground(Color.BLACK);
        hard.setOpaque(true);
        hard.setBorderPainted(false);
        hard.addActionListener(e -> lcardLayout.show(lcardPanel, "Hard"));
        add(hard);

        setSize(500, 750);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

class EasyBoard extends JPanel {
    private Image backGroundImage;
    private final int scores[]=new int[5];
    private final String names[]=new String[5];
    public EasyBoard(CardLayout lcardLayout, JPanel lcardPanel) {
        setLayout(null); 
        setBackground(Color.BLACK);

        ImageIcon Image=new ImageIcon("lib/LeaderBack.jpg");
        backGroundImage=Image.getImage();

        Font Copperplatefont1 = new Font("Copperplate",Font.BOLD, 30);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);
            
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

        JLabel heading=new JLabel("LEADERBOARD : EASY");
        heading.setBounds(100,100,350,40);
        heading.setForeground(Color.white);
        heading.setFont(Copperplatefont1);
        add(heading);
        FileCheck.easyBoard(names, scores);

        JLabel rank=createColoredLabel("RANK", Color.YELLOW , Copperplatefont2);
        rank.setFont(Copperplatefont2);
        rank.setBounds(50,200,80,40);
        rank.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(rank);

        JLabel name=createColoredLabel("USERNAME",Color.YELLOW , Copperplatefont2);
        name.setFont(Copperplatefont2);
        name.setBounds(129,200,180,40);
        name.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(name);

        JLabel score=createColoredLabel("SCORE",Color.YELLOW , Copperplatefont2) ;
        score.setFont(Copperplatefont2);
        score.setBounds(308,200,130,40);
        score.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(score);
        
        int y=239;
        for(int i=0;i<5;i++){
            JLabel label1=createColoredLabel(Integer.toString(i+1),new Color(0,0,0) , Copperplatefont2);
            label1.setBounds(50,y,80,40);
            label1.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label1.setForeground(Color.WHITE);
            label1.setFont(Copperplatefont2);
            add(label1);

            JLabel label2=createColoredLabel(names[i]==null?"-":names[i],new Color(0,0,0) , Copperplatefont2);
            label2.setBounds(129,y,180,40);
            label2.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label2.setForeground(Color.WHITE);
            label2.setFont(Copperplatefont2);
            add(label2);
        
            JLabel label3=createColoredLabel(names[i]==null?"-":Integer.toString(scores[i]),new Color(0,0,0) , Copperplatefont2);
            label3.setBounds(308,y,130,40);
            label3.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label3.setForeground(Color.WHITE);
            label3.setFont(Copperplatefont2);
            add(label3);

            y+=39;
        }


        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lcardLayout.show(lcardPanel, "Choice");
            }
        });
        
        add(previousButton);
    }

    private JLabel createColoredLabel(String text, Color bgColor , Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bgColor); 
        label.setFont(font);
        //label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        return label;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

class MediumBoard extends JPanel {
    private Image backGroundImage;
    private final int scores[]=new int[5];
    private final String names[]=new String[5];
    public MediumBoard(CardLayout lcardLayout, JPanel lcardPanel) {
        setLayout(null); 
        setBackground(Color.BLACK);

        ImageIcon Image=new ImageIcon("lib/LeaderBack.jpg");
        backGroundImage=Image.getImage();

        Font Copperplatefont1 = new Font("Copperplate",Font.BOLD, 30);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);
            
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

        JLabel heading=new JLabel("LEADERBOARD : MEDIUM");
        heading.setBounds(90,100,400,40);
        heading.setForeground(Color.white);
        heading.setFont(Copperplatefont1);
        add(heading);
        FileCheck.mediumBoard(names, scores);

        JLabel rank=createColoredLabel("RANK", Color.YELLOW , Copperplatefont2);
        rank.setBounds(50,200,80,40);
        rank.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(rank);

        JLabel name=createColoredLabel("USERNAME",Color.YELLOW , Copperplatefont2);
        name.setBounds(129,200,180,40);
        name.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(name);

        JLabel score=createColoredLabel("SCORE",Color.YELLOW , Copperplatefont2);
        score.setBounds(308,200,130,40);
        score.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(score);
        
        int y=239;
        for(int i=0;i<5;i++){
            JLabel label1=createColoredLabel(Integer.toString(i+1),new Color(0,0,0) , Copperplatefont2);
            label1.setBounds(50,y,80,40);
            label1.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label1.setForeground(Color.WHITE);
            add(label1);

            JLabel label2=createColoredLabel(names[i]==null?"-":names[i],new Color(0,0,0) , Copperplatefont2);
            label2.setBounds(129,y,180,40);
            label2.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label2.setForeground(Color.WHITE);
            add(label2);
        
            JLabel label3=createColoredLabel(names[i]==null?"-":Integer.toString(scores[i]),new Color(0,0,0) , Copperplatefont2);
            label3.setBounds(308,y,130,40);
            label3.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label3.setForeground(Color.WHITE);
            add(label3);

            y+=39;
        }


        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lcardLayout.show(lcardPanel, "Choice");
            }
        });
        
        add(previousButton);
    }

    private JLabel createColoredLabel(String text, Color bgColor , Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bgColor); 
        label.setFont(font);
        //label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        return label;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

class HardBoard extends JPanel {
    private Image backGroundImage;
    private final int scores[]=new int[5];
    private final String names[]=new String[5];
    public HardBoard(CardLayout lcardLayout, JPanel lcardPanel) {
        setLayout(null); 
        setBackground(Color.BLACK);

        ImageIcon Image=new ImageIcon("lib/LeaderBack.jpg");
        backGroundImage=Image.getImage();

        Font Copperplatefont1 = new Font("Copperplate",Font.BOLD, 30);
        try {
            // Load the font file (use correct relative or absolute path)
            File fontFile = new File("fonts/Balthazar-Regular.ttf");
            Copperplatefont1 = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);
            
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

        JLabel heading=new JLabel("LEADERBOARD : HARD");
        heading.setBounds(100,100,350,40);
        heading.setForeground(Color.white);
        heading.setFont(Copperplatefont1);
        add(heading);
        FileCheck.hardBoard(names, scores);

        JLabel rank=createColoredLabel("RANK", Color.YELLOW , Copperplatefont2);
        rank.setBounds(50,200,80,40);
        rank.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(rank);

        JLabel name=createColoredLabel("USERNAME",Color.YELLOW , Copperplatefont2);
        name.setBounds(129,200,180,40);
        name.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(name);

        JLabel score=createColoredLabel("SCORE",Color.YELLOW , Copperplatefont2);
        score.setBounds(308,200,130,40);
        score.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        add(score);
        
        int y=239;
        for(int i=0;i<5;i++){
            JLabel label1=createColoredLabel(Integer.toString(i+1),new Color(0,0,0) , Copperplatefont2);
            label1.setBounds(50,y,80,40);
            label1.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label1.setForeground(Color.WHITE);
            add(label1);

            JLabel label2=createColoredLabel(names[i]==null?"-":names[i],new Color(0,0,0) , Copperplatefont2);
            label2.setBounds(129,y,180,40);
            label2.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label2.setForeground(Color.WHITE);
            add(label2);
        
            JLabel label3=createColoredLabel(names[i]==null?"-":Integer.toString(scores[i]),new Color(0,0,0) , Copperplatefont2);
            label3.setBounds(308,y,130,40);
            label3.setBorder(BorderFactory.createLineBorder(Color.white, 2));
            label3.setForeground(Color.WHITE);
            add(label3);

            y+=39;
        }


        ImageIcon icon = new ImageIcon("lib/previous.png");
        JButton previousButton = new JButton(icon);
        previousButton.setBounds(10, 10, 30, 30);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lcardLayout.show(lcardPanel, "Choice");
            }
        });
        
        add(previousButton);
    }

    private JLabel createColoredLabel(String text, Color bgColor , Font font) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(bgColor); 
        label.setFont(font);
        //label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
        return label;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
public class LeaderBoardChoicePanel extends JPanel {
    public LeaderBoardChoicePanel(CardLayout cardLayout,JPanel cardPanel) {
        CardLayout lcardLayout = new CardLayout();
        setLayout(lcardLayout);
        JPanel lcardPanel = this;


        LeaderChoice choice = new LeaderChoice(lcardLayout, lcardPanel,cardLayout,cardPanel);
        EasyBoard easy = new EasyBoard(lcardLayout, lcardPanel);
        MediumBoard medium = new MediumBoard(lcardLayout, lcardPanel);
        HardBoard hard = new HardBoard(lcardLayout, lcardPanel);

        lcardPanel.add(choice, "Choice");
        lcardPanel.add(easy, "Easy");
        lcardPanel.add(medium, "Medium");
        lcardPanel.add(hard, "Hard");
    }
}