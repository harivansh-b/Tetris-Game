package Game;
import Audios.AudioPlayer;
import Panels.*;
import Tetris.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
class MainPanel extends JPanel {
    public MainPanel() {
        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);  // Use CardLayout for MainPanel

        // Add all the panels to MainPanel
        add(new OpenPagePanel(cardLayout, this), "openPagePanel");
        add(new LoginGuestPanel(cardLayout, this), "loginGuestPanel");
        add(new HowToPlayPanel(cardLayout, this), "howToPlayPanel");
        add(new LoginPanel(cardLayout, this), "loginPanel");
        add(new SignUpPanel(cardLayout, this), "signUpPanel");
        add(new LoginDirectedPanel(cardLayout, this), "loginDirectedPanel");
        add(new GuestDirectedPanel(cardLayout, this), "guestDirectedPanel");
        add(new LeaderBoardChoicePanel(cardLayout, this), "leaderBoardChoicePanel");
        add(new PlayerHistory(cardLayout, this), "playerHistory");
        add(new DifficultyPanel(cardLayout, this), "difficultyPanel");
        add(new TetrisPanel(cardLayout, this), "gamePanel");

        // Display the first panel
        cardLayout.show(this, "openPagePanel");
    }
}

public class App extends JFrame {
    private static String username = "";
    private static int guestorlogin = 0;
    private static int difficulty = 1;
    private static int show=0;

    public App() {
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
        setTitle("Tetris");
        setFont(Copperplatefont);
        add(new MainPanel());  // Add MainPanel to the frame
        setSize(500, 750);  // Set size of the frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the frame on exit
        setVisible(true);  // Make the frame visible
    }

    public static String getUsername(){
        return username;
    }

    public static void setUsername(String usr){
        username=usr;
    }
    
    public static int getGuestorlogin(){
        return guestorlogin;
    }

    public static void setGuestorlogin(int gol){
        guestorlogin=gol;
    }

    public static int getDifficulty(){
        return difficulty;
    }

    public static void setDifficulty(int diff){
        difficulty=diff;
    }
    
    public static int getShow(){
        return show;
    }

    public static void setShow(int s){
        show=s;
    }
    public static void main(String[] args) {
        new App();
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.playTitle();
    }
}
